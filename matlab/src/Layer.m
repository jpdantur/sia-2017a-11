
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

			% Función de activación:
			transfer;
		end

		methods

			% Constructor:
			function this = Layer(inputs, neurons, transfer)

				% Estado inicial:
				this.weights = rand(inputs, neurons) - 0.5;
				this.transfer = transfer;
			end

			% Computar la salida de la capa:
			function output = fire(this, input)

				output = this.transfer{1, 1}(input * this.weights);
			end

			% Computa el parámetro 'delta' de salida:
			function delta = outerDelta(this, target, output)

				delta = this.transfer{1, 2}(output) * (target - output);
			end

			% Computa el parámetro 'delta' interno:
			function delta = innerDelta(this, delta, output, weights)

				delta = this.transfer{1, 2}(output) * (weights * delta);
			end

			% Devuelve los pesos para 'back-propagation':
			function weights = getInnerWeights(this)

				weights = this.weights(1:end - 1, :);
			end
		end
	end