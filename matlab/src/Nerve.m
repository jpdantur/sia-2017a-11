
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

				% Obtener y mostrar configuración (sin ';'):
				config = Configurator.load(Nerve.CONFIGURATION_FILENAME)

				% Construir el perceptrón:
				perceptron = Perceptron( ...
					config.inputs, ...
					config.layerSizes, ...
					config.transfers, ...
					config.learningRate);

				% Pre-procesamiento (para 'bits'):
				config.instances = 2 * config.instances - 1;
				config.targets = 2 * config.targets - 1;

				% Cantidad de patrones disponibles:
				patterns = size(config.instances, 1);
				trainSize = round(patterns * config.trainRatio);

				for k = 1:config.epochs

					trainIndexes = randperm(patterns, trainSize);
					testIndexes = setdiff(1:patterns, trainIndexes);

					trainingInstances = config.instances(trainIndexes, :);
					trainingTargets = config.targets(trainIndexes, :);
					testingInstances = config.instances(testIndexes, :);
					testingTargets = config.targets(testIndexes, :);

					% ---------------------------------------------------------
					tic;

					% Entrenar la red neuronal:
					perceptron.train(trainingInstances, trainingTargets);

					toc;
					tic;

					% Predecir los patrones de entrada:
					predictions = perceptron.predict(testingInstances);

					toc;

					% Resultados:
					%[0, sum(predictions == -1), sum(testingTargets == -1)]
					%[1, sum(predictions == +1), sum(testingTargets == +1)]
					%sum(predictions == testingTargets)
					[config.error, Nerve.E(testingTargets, predictions)]
					% ---------------------------------------------------------

					% Stopping-criterion:
					if Nerve.E(testingTargets, predictions) < config.error, break; end
				end
			end
		end

		methods (Static, Access = private)

			% Computa la función de costo:
			function error = E(targets, predictions)

				error = 0.5 * immse(targets, predictions);
			end
		end
	end
