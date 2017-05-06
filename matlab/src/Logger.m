
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
			function logEpoch(epoch, config, trainingTime, testingTime, totalTime, error)

				fprintf('Epoca %i -> (Train: %.3f seg., Test: %.3f seg., Total: %.3f seg., Error: %.6f)\n', ...
					epoch, trainingTime, testingTime, totalTime, error);

				if (error < config.error)
					fprintf('La red fue entrenada con exito!\n');
					fprintf('Error final: %.6f\n', error);
				elseif (config.epochs <= epoch)
					fprintf('La red no alcanzo el limite maximo de error (%.6f).\n', ...
						config.error)
					fprintf('Error final: %.6f\n', error);
				end
			end

			% Mostrar el tiempo final de ejecución:
			function logExecutionTime(time)

				fprintf('Tiempo de ejecucion: %.6f segundos.\n', time);
			end
		end
	end
