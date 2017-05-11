
	%{
		>>> Logger Class:

			Permite manipular los mensajes en consola de forma centralizada,
			lo que evita ofuscar los algoritmos de entrenamiento.
	%}

	classdef Logger

		methods (Static, Access = public)

			% Convierte un valor booleano en una cadena:
			function string = boolToString(boolean)

				if boolean == 0, string = 'false';
				else string = 'true'; end
			end

			% Representa un vector de cadenas como única cadena:
			function string = stringsToString(strings)

				string = '[';
				last = size(strings, 2);

				for k = 1:last
					if k == last
						string = strcat(string, strings{k}, ']');
					else
						string = strcat(string, strings{k}, {', '});
					end
				end
			end

			% Representa un vector numérico como cadena:
			function string = vectorToString(vector)

				string = '[';
				last = size(vector, 2);

				for k = 1:last
					if k == last
						string = strcat(string, num2str(vector(k)), ']');
					else
						string = strcat(string, num2str(vector(k)), {', '});
					end
				end
			end

			% Muestra la configuración global:
			function logConfiguration(config)

				fprintf('\n');
				fprintf('\t                          Dataset : ''%s''\n', config.problem);
				fprintf('\t                           Output : ''%s''\n', config.output);
				fprintf('\t                       Instancias : %i\n', size(config.instances, 1));
				fprintf('\t                      Predictores : %i\n', size(config.instances, 2) + size(config.targets, 2));
				fprintf('\t                Pre-procesamiento : ''%s''\n', config.processor.getName());
				fprintf('\t      Porcentaje de entrenamiento : %.6f %c\n', 100 * config.trainRatio, '%');
				fprintf('\t           Porcentaje de muestreo : %.6f %c\n', 100 * config.sampleRatio, '%');
				fprintf('\t        Usar conjuntos disjuntos? : %s\n', Logger.boolToString(config.disjoint));
				fprintf('\t             Error maximo deseado : %.6f\n', config.error);
				fprintf('\t                           Epochs : %i\n\n', config.epochs);

				fprintf('\t             Cantidad de entradas : %i\n', config.inputs);
				fprintf('\t              Cantidad de salidas : %i\n', config.layerSizes(end));
				fprintf('\t                Neuronas por capa : %s\n', Logger.vectorToString(config.layerSizes){1});
				fprintf('\t                   Transferencias : %s\n', Logger.stringsToString(config.transferNames){1});
				fprintf('\t                     Sigmoid Beta : %.6f\n\n', config.beta);

				fprintf('\t   Probabilidad de inyectar ruido : %.6f\n', config.injectionProbability);
				fprintf('\tInterferencia max. sobre patrones : %.6f\n', config.patternNoise);
				fprintf('\tInterferencia inicial sobre pesos : %.6f\n\n', config.weightNoise);

				fprintf('\t       Vanishing Gradient Problem : %.6f\n', config.vanishingLimit);
				fprintf('\t           Momento de aprendizaje : %.6f\n', config.momentum);
				fprintf('\t            Learning-rate inicial : %.6f\n', config.learningRate);
				fprintf('\t     Incremento del learning-rate : %.6f\n', config.learningRateDecrement);
				fprintf('\t     Decremento del learning-rate : %.6f\n', config.learningRateIncrement);
				fprintf('\t            Ventana de adaptacion : %i\n\n', config.minSteps);

				fprintf('\t     Graficar aproximacion final? : %s\n', Logger.boolToString(config.graph));
				fprintf('\t        Graficar curvas de error? : %s\n', Logger.boolToString(config.graphError));
				fprintf('\t                     Granularidad : %.3f\n', config.granularity);
				fprintf('\n');
			end

			% Imprime los resultados de una época:
			function logEpoch(epoch, config, trainingTime, testingTime, totalTime, trainingError, testingError, learningRate)

				fprintf('Epoca %i -> (Train: %.3f seg., Test: %.3f seg., Total: %.3f seg., Train Error: %.6f, Test Error: %.6f, Learn Rate: %.6f)\n', ...
					epoch, trainingTime, testingTime, totalTime, trainingError, testingError, learningRate);

				if (testingError < config.error)
					fprintf('\nLa red fue entrenada con exito!\n');
					fprintf('Error final: %.6f\n', testingError);
				elseif (config.epochs <= epoch)
					fprintf('\nLa red no alcanzo el limite maximo de error (%.6f).\n', config.error);
					fprintf('Error final: %.6f\n', testingError);
				end
			end

			% Mostrar el tiempo final de ejecución:
			function logExecutionTime(time)

				fprintf('Tiempo de ejecucion: %.6f segundos.\n', time);
			end
		end
	end
