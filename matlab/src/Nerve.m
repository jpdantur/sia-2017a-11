
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

				tic;

				% Entrenar la red neuronal:
				perceptron.train(config.instances, config.targets);

				toc;
				tic;

				% Predecir las mismas instancias:
				targets = perceptron.predict(config.instances);

				toc;

				% Resultados:
				[0, sum(targets == -1), sum(config.targets == -1)]
				[1, sum(targets == +1), sum(config.targets == +1)]
				sum(targets == config.targets)
			end
		end
	end
