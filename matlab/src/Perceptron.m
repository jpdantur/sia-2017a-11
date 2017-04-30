
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
			function this = train(this, instances, targets)

				% Por cada instancia:
				for k = 1:size(instances, 1)

					% Propagar cambios:
					this.propagate(instances(k, :));

					% Corregir aprendizaje:
					this.backpropagate(targets(k));
				end
			end

			% Predictor:
			function prediction = predict(this, instances)

				% Por cada instancia:
				for k = 1:size(instances, 1)

					% Propagar cambios:
					prediction(k) = this.propagate(instances(k, :));
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

				% %%%%
				% La descripción está en las filminas 15, 16 y 17 de la teórica...
				%
				% (!!!) Este método está totalmente incompleto, obviamente. (!!!)
				%

				% Última capa:
				k = size(this.network, 2);

				% Este 'delta' depende de 'target':
				delta = this.network{k} ...
					.outerDelta(target, this.outputs{k + 1})

				% En orden inverso:
				for k = size(this.network, 2):-1:2

					% outputs(1) es la entrada original ...
					% outputs(k > 1) es la salida de la capa (k - 1) ...
					%
					% Dudas...
					%
					%	¿El delta de una capa interna se computa con los pesos
					%	de la capa superior?
					%

					delta = this.network{k - 1} ...
						.innerDelta(delta, this.outputs{k}, ...
							this.network{k}.getInnerWeights())
				end
			end
		end
	end
