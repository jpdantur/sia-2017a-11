
	%{
		>>> Layer Class:

			Permite crear una capa de neuronas para construir una red
			neuronal. Debido a que es más simple manipular la capa completa de
			forma matricial, se optó por utilizar el concepto de capa en lugar
			del de neurona como unidad.

			La implementación de la capa es genérica, y por lo tanto no tiene
			en cuenta la presencia del umbral de entrada.
	%}

	classdef Layer < handle

		properties (Access = protected)

			% Matriz de pesos:
			weights = [];

			previousWeights = [];

			previousVariation = [];

			% Función de activación:
			transfer;

			% Vanishing Gradient Limit:
			vanishingLimit = 0;

			% Variación de pesos:
			variation = [];
		end

		methods

			% Constructor:
			function this = Layer(inputs, neurons, transfer, vanishingLimit)

				% Estado inicial:
				this.weights = rand(inputs, neurons) - 0.5;
				this.transfer = transfer;
				this.vanishingLimit = vanishingLimit;
				this.variation = zeros(inputs, neurons);
			end

			% Computar la salida de la capa:
			function output = fire(this, input)

				output = this.transfer{1, 1}(input * this.weights);
			end

			% Actualizar pesos:
			function this = update(this, weights)

				this.variation = weights;
				this.weights = this.weights + weights;
			end

			function this = backupWeights(this)
				this.previousWeights = this.weights;
				this.previousVariation = this.variation;
			end

			function this = undo(this)
				this.weights = this.previousWeights;
				this.variation = this.previousVariation;
			end

			% Computa el parámetro 'delta' de salida:
			function delta = outerDelta(this, target, output)

				delta = (this.transfer{1, 2}(output) + this.vanishingLimit) .* (target - output);
			end

			% Computa el parámetro 'delta' interno:
			function delta = innerDelta(this, delta, output, weights)

				delta = (this.transfer{1, 2}(output) + this.vanishingLimit) .* (delta * weights);
			end

			% Devuelve los pesos para 'back-propagation':
			function weights = getInnerWeights(this)

				weights = this.weights(1:end - 1, :)';
			end

			% Devuelte todos los pesos:
			function weights = getWeights(this)

				weights = this.weights;
			end

			function setWeights(this,weights)

				this.weights=weights;
			end

			% Devuelve la variacion que generó los pesos actuales
			function variation = getVariation(this)

				variation = this.variation;
			end
		end
	end
