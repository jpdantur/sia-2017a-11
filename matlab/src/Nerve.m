
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
			function run(state)

				% Timer global y tiempo acumulado:
				globalTic = tic;

				% Obtener y mostrar configuración:
				config = Configurator.load(Nerve.CONFIGURATION_FILENAME);
				learningRate = config.learningRate;
				Logger.logConfiguration(config);

				% Construir el perceptrón:
				perceptron = Perceptron(config);

				if nargin == 1
					state = loadjson(state);
					perceptron.setWeights(state);
				end

				% Pre-procesamiento:
				config.instances = config.processor...
					.transform(config.instances);
				config.targets = config.processor...
					.transform(config.targets);

				% Generador de conjuntos:
				generator = Generator(config);

				trainingErrors = [];
				testingErrors = [];
				steps = 0;

				for epoch = 1:config.epochs
					learningRate = perceptron.getLearningRate();

					% Generar muestras:
					[trainingInstances, trainingTargets, ...
					testingInstances, testingTargets] = generator.getSample();

					perceptron.backupWeights();

					% ---------------------------------------------------------

					% Entrenar la red neuronal:
					trainTic = tic;
					results = perceptron.train(trainingInstances, trainingTargets);
					trainingTime = toc(trainTic);

					% Computa el error de entrenamiento actual:
					trainingError = Nerve.E(trainingTargets, results);

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
						trainingError, ...
						testingError, ...
						learningRate);

					if epoch > 1
						if trainingError < trainingErrors(end)
							steps = steps + 1;
							if steps >= config.minSteps
								perceptron.increaseLearningRate();
							end
						elseif trainingError > trainingErrors(end)
							perceptron.decreaseLearningRate();
							steps = 0;
							perceptron.undo();
						end
					end

					trainingErrors(1, end + 1) = trainingError;
					testingErrors(1, end + 1) = testingError;

					% Stopping-criterion:
					if testingError < config.error

						% Post-procesamiento:
						testingInstances = config.processor...
							.restore(testingInstances);
						testingTargets = config.processor...
							.restore(testingTargets);
						predictions = config.processor...
							.restore(predictions);
						break;
					end
					perceptron.addNoise();
				end

				% Mostrar tiempo de ejecución final:
				Logger.logExecutionTime(toc(globalTic));

				% Graficar curvas de error:
				if true == config.graphError
					figure;
					plot(1:size(trainingErrors, 2), trainingErrors, 'color', 'r');
					hold on;
					plot(1:size(testingErrors, 2), testingErrors, 'color', 'b');
					title('Graph of Training Error and Testing Error');
					xlabel('Epochs');
					ylabel('Error');
					legend('Training Error', 'Testing Error');
				end

				% Graficar aproximación final:
				if true == config.graph
					OutputGrapher.surfacePlot(config,perceptron);
				end

				% Almacenar el estado del perceptrón:
				weights = perceptron.getAllWeights();
                savejson('', weights, config.output);
			end
		end

		methods (Static, Access = private)

			% Computa la función de costo:
			function error = E(targets, data)

				error = 0.5 * immse(targets, data);
			end
		end
	end
