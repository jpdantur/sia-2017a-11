
	%{
		>>> TransferFactoy Class:

			Permite generar funciones de activación de manera dinámica, lo
			cual simplifica el hecho de que las funciones pueden requerir una
			parametrización diferente en cada caso.

			Adicionalmente, genera las derivadas de cada función,
			parametrizando las mismas de forma tal que puedan describirse en
			función de su primitiva.
	%}

	classdef TransferFactory

		methods (Static)

			% Permite generar las funciones de activación:
			function activations = generate(configuration)

				names = configuration.transfers;

				for k = 1:size(names, 2)
					switch names{k}

						case 'heaviside'
							activations{k, 1} = @(x) 0 <= x;
							activations{k, 2} = @(x) 1;
						case 'tanh'
							beta = configuration.beta;
							activations{k, 1} = @(x) tanh(beta * x);
							activations{k, 2} = @(x) beta * (1 - x .^ 2);
						case 'sigmoid'
							beta = configuration.beta;
							activations{k, 1} = @(x) sigmf(x, [2 * beta, 0]);
							activations{k, 2} = @(x) 2 * beta * x * (1 - x);
						case 'linear'
							activations{k, 1} = @(x) x;
							activations{k, 2} = @(x) 1;
						case 'sign'
							activations{k, 1} = @sign;
							activations{k, 2} = @(x) 1;
						otherwise
							activations{k, 1} = @(x) 0;
							activations{k, 2} = @(x) 0;
					end
				end
			end
		end
	end
