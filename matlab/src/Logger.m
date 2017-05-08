
	%{
		>>> Logger Class:

			Permite manipular los mensajes en consola de forma centralizada,
			lo que evita ofuscar los algoritmos de entrenamiento.
	%}

	classdef Logger

		methods (Static, Access = public)

			% Muestra la configuración global:
			function logConfiguration(config)

				% Falta mejorar!!!
				config
			end

			% Imprime los resultados de una época:
			function logEpoch(epoch, config, trainingTime, testingTime, totalTime, trainingError, testingError, learningRate)

				fprintf('Epoca %i -> (Train: %.3f seg., Test: %.3f seg., Total: %.3f seg., Train Error: %.6f, Test Error: %.6f, Learn Rate: %.6f)\n', ...
					epoch, trainingTime, testingTime, totalTime, trainingError, testingError, learningRate);

				if (testingError < config.error)
					fprintf('La red fue entrenada con exito!\n');
					fprintf('Error final: %.6f\n', testingError);
				elseif (config.epochs <= epoch)
					fprintf('La red no alcanzo el limite maximo de error (%.6f).\n', config.error);
					fprintf('Error final: %.6f\n', testingError);
				end
			end

			% Mostrar el tiempo final de ejecución:
			function logExecutionTime(time)

				fprintf('Tiempo de ejecucion: %.6f segundos.\n', time);
			end
		end
	end
