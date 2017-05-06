
	%{
		>>> Nerve® Entry Point:

			La clase principal, la cual permite generar una red neuronal para
			resolver problemas de aproximación de funciones de forma genérica.
	%}

	classdef Nerve

		properties (Constant, Access = public)

			% Archivo de configuración:
			CONFIGURATION_FILENAME = '../nerve.json';
		end

		methods (Static)

			% Función principal:
			function run()

				% Timer global y tiempo acumulado:
				globalTic = tic;

				% Obtener y mostrar configuración:
				config = Configurator.load(Nerve.CONFIGURATION_FILENAME);
				Logger.logConfiguration(config);

				% Construir el perceptrón:
				perceptron = Perceptron( ...
					config.inputs, ...
					config.layerSizes, ...
					config.transfers, ...
					config.learningRate);

				% Pre-procesamiento (para 'bits'):
				config.instances = 2 * config.instances - 1;
				config.targets = 2 * config.targets - 1;

				for epoch = 1:config.epochs

					[trainIndexes, testIndexes] = Nerve ...
						.getIndexes( ...
							size(config.instances, 1), config.trainRatio);

					[trainingInstances, trainingTargets] = Nerve ...
						.getSubset(config, trainIndexes);

					[testingInstances, testingTargets] = Nerve ...
						.getSubset(config, testIndexes);

					% ---------------------------------------------------------

					% Entrenar la red neuronal:
					trainTic = tic;
					perceptron.train(trainingInstances, trainingTargets);
					trainingTime = toc(trainTic);

					% Predecir los patrones de entrada:
					testTic = tic;
					predictions = perceptron.predict(testingInstances);
					testingTime = toc(testTic);

					% Computa el error de testeo actual:
					testingError = Nerve.E(testingTargets, predictions);

					% Mostrar resultados de la época:
					Logger.logEpoch( ...
						epoch, ...
						config, ...
						trainingTime, ...
						testingTime, ...
						toc(globalTic), ...
						testingError);

					% Stopping-criterion:
					if testingError < config.error, break; end
				end

				% Mostrar tiempo de ejecución final:
				Logger.logExecutionTime(toc(globalTic));

				if (config.graph)

					grapher = OutputGrapher(trainingInstances,trainingTargets);

					grapher.graphCompare;					
				end
			end
		end

		methods (Static, Access = private)

			% Computa la función de costo:
			function error = E(targets, predictions)

				error = 0.5 * immse(targets, predictions);
			end

			% Computa una selección aleatoria de índices:
			function [train, test] = getIndexes(instances, trainRatio)

				trainSize = round(instances * trainRatio);
				train = randperm(instances, trainSize);
				test = setdiff(1:instances, train);
			end

			% Genera sub-conjuntos de instancias:
			function [instances, targets] = getSubset(config, indexes)

				instances = config.instances(indexes, :);
				targets = config.targets(indexes, :);
			end
		end
	end
