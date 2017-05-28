
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

				% Generador de poblaciones:
				generator = Generator(config);

				% Calculador de adaptación:
				fitness = Fitness(config);

				% Stopping-criterion:
				cutOff = CutOff(config);

				% Método de selección natural:
				selector = Selector(config);

				% Método de cruce/apareamiento:
				crossover = Crossover(config);

				% Mutador genético:
				mutator = Mutator(config);

				% -------------------------------------------------------------

				% Generar población inicial:
				population = generator.init();

				% Computar el 'fitness' global inicial:
				globalFitness = fitness.getGlobalFitness(population);

				while cutOff.assert(population, globalFitness) == false

					% Seleccionar individuos de prueba:
					indexes = selector.select(globalFitness);

					% Recombinar y generar descendientes:
					subPopulation = crossover.recombine(population(indexes));

					% Mutar el código genético de la sub-población:
					subPopulation = mutator.mutate(subPopulation);

					% Obtener índices de reemplazo:
					[old, new] = generator.replace(...
						size(population), ...
						size(subPopulation));

					% Generar nueva población:
					population = [population(old), subPopulation(new)];

					% Evaluar el nuevo fitness... (OPTIMIZAR!!!)
					globalFitness = fitness.getGlobalFitness(population);
					% globalFitness = fitness.updateFitness(...
					%	globalFitness, old, subPopulation, new);
				end

				% Computar el resultado final!!!
				% Gráficos, etc.

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
