
	%{
		>>> Main Entry Point:

			La clase principal, la cual permite desplegar un algoritmo
			genético para optimizar una función determinada.
	%}

	classdef Genetic

		properties (Constant, Access = public)

			% Archivo de configuración:
			CONFIGURATION_FILENAME = '../config.json';
		end

		methods (Static)

			% Función principal:
			function run()

				global data config;

				% Timer global y tiempo acumulado:
				globalTic = tic;

				% Mostrar configuración:
				Logger.logConfiguration(config);

				% -------------------------------------------------------------

				% ...
				% Implementar...
				% ...

				% -------------------------------------------------------------

				% Mostrar tiempo de ejecución final:
				Logger.logExecutionTime(toc(globalTic));
			end

			% Cargar configuración (con o sin datos):
			function load(lazyness)

				global data config;
				configTic = tic;

				config = Configurator.load(...
					Genetic.CONFIGURATION_FILENAME, ...
					lazyness);

				Logger.logLoadingTime(toc(configTic), lazyness);
			end

			% Eliminar la configuración y el set de datos:
			function reset()

				clear global data;
				clear global config;
			end
		end
	end
