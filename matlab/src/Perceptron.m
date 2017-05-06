
	%{
		>>> Perceptron Class:

			Representa una red neuronal completa y genérica multicapa, para la
			cual se especifica la arquitectura de la misma, y las funciones de
			activación correspondientes en cada capa.

			Las subclases proveen los algoritmos necesarios para entrenar la
			red, y generar una predicción basada en nuevos patrones.

			Adicionalmente, es importante recalcar que:

				> Cada capa puede diferir en la cantidad de neuronas.
				> Cada capa posee su propia función de activación.
				> Existe una salida por cada neurona en la capa final.
				> La cantidad de entradas es arbitraria.
				> Los umbrales se manipulan automáticamente.
	%}

	classdef Perceptron < handle

		properties (Access = protected)

			% La red neuronal:
			network = {};

			% La tasa de aprendizaje:
			learningRate;

			% El vector de salidas:
			outputs = {};
		end

		methods

			% Constructor:
			function this = Perceptron( ...
				inputs, layerSizes, transfers, learningRate)

				% Estado inicial:
				this.learningRate = learningRate;

				% Agregar el tamaño de la capa de entrada y los umbrales:
				inputSizes = [inputs, layerSizes(1:end - 1)] + 1;

				% Construir la red neuronal:
				for k = 1:size(layerSizes, 2)
					this.network{k} = Layer( ...
						inputSizes(k), ...
						layerSizes(k), ...
						transfers(k, :));
				end
			end

			% Entrenar la red neuronal:
			function result = train(this, instances, targets)

				% Por cada instancia:
				for k = 1:size(instances, 1)

					% Propagar cambios:
					result(k, :) = this.propagate(instances(k, :));

					% Corregir aprendizaje:
					this.backpropagate(targets(k, :));
				end
			end

			% Predictor:
			function prediction = predict(this, instances)

				% Por cada instancia:
				for k = 1:size(instances, 1)

					% Propagar cambios:
					prediction(k, :) = this.propagate(instances(k, :));
				end
			end
		end

		methods (Access = protected)

			% Propagar el patrón de entrada:
			function output = propagate(this, input)

				this.outputs{1} = input;
				for k = 1:size(this.network, 2)

					this.outputs{k + 1} = this.network{k} ...
						.fire([this.outputs{k}, -1]);
				end
				output = this.outputs{end};
			end

			% Aplicar 'back-propagation':
			function this = backpropagate(this, target)

				% Computar 'deltas':
				deltas = this.getDeltas(target);

				% Actualizar pesos para cada capa:
				for k = 1:size(this.network, 2)

					% Agregar umbrales:
					V = [this.outputs{k}, -1];

					this.network{k}.update( ...
						this.learningRate * V' * deltas{k});
				end
			end

			% Computa los 'delta' para 'back-propagation':
			function deltas = getDeltas(this, target)

				% Última capa:
				k = size(this.network, 2);

				% Este 'delta' depende de 'target':
				deltas{k} = this.network{k} ...
					.outerDelta(target, this.outputs{k + 1});

				% En orden inverso:
				for k = size(this.network, 2):-1:2

					deltas{k - 1} = this.network{k - 1} ...
						.innerDelta(deltas{k}, this.outputs{k}, ...
							this.network{k}.getInnerWeights());
				end
			end
		end
	end
