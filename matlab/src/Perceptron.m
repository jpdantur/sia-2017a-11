
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

			% El momento de inercia de aprendizaje:
			momentum;

			% Activador del momento de inercia:
			momentumEnabled = 1;

			% Tasas de cambio para LR variable:
			learningRateIncrement;
			learningRateDecrement;
		end

		methods

			% Constructor:
			function this = Perceptron(config)

				% Estado inicial:
				this.learningRate = config.learningRate;
				this.momentum = config.momentum;
				this.learningRateIncrement = config.learningRateIncrement;
				this.learningRateDecrement = config.learningRateDecrement;

				% Agregar el tamaño de la capa de entrada y los umbrales:
				inputSizes = [config.inputs, config.layerSizes(1:end - 1)] + 1;

				% Construir la red neuronal:
				for k = 1:size(config.layerSizes, 2)
					this.network{k} = Layer( ...
						inputSizes(k), ...
						config.layerSizes(k), ...
						config.transfers(k, :), ...
						config.vanishingLimit);
				end
			end

			function this = backupWeights(this)
				for k = 1:size(this.network,2)
					this.network{k}.backupWeights();
				end
			end


			function this = undo(this)
				for k = 1:size(this.network,2)
					this.network{k}.undo();
				end
			end

			function this = increaseLearningRate(this)
				this.learningRate = this.learningRate + this.learningRateIncrement;

				this.momentumEnabled = 1;
			end

			function this = decreaseLearningRate(this)
				this.learningRate = this.learningRate - this.learningRateDecrement*this.learningRate;
				this.momentumEnabled = 0;
			end

			function ret = getLearningRate(this)
				ret = this.learningRate;
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

			function weights=getAllWeights(this)

				weights = {};

				for k = 1:size(this.network,2)
					weights{end+1} = this.network{k}.getWeights;
				end

			end

			function setWeights(this,state)

				for k = 1:size(this.network,2)
					this.network{k}.setWeights(state{k});
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
					
					this.network{k}.update(this.learningRate * V' * deltas{k} + ...
						this.momentum * this.momentumEnabled * this.network{k}.getVariation);
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
