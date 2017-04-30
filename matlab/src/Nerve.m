
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

				% Entrenar la red neuronal:
				perceptron.train(config.instances, config.targets);

				% Predecir las mismas instancias:
				targets = perceptron.predict(config.instances);
			end
		end
	end
