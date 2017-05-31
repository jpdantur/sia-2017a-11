
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

				% Inicializar el PRNG:
				rng shuffle;

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

				% Graficador de curvas:
				grapher = Grapher(config);

				% -------------------------------------------------------------

				% Generar población inicial:
				population = generator.init();

				% Computar el 'fitness' global inicial:
				globalFitness = fitness.getGlobalFitness(population);

				% Graficar curva de 'fitness':
				grapher.addFitness(globalFitness);

				while cutOff.assert(population, globalFitness) == false

					% Seleccionar individuos de prueba:
					indexes = selector.select(config.selection,...
						config.selectionMethod, config.selectionMethodRate, globalFitness);

					% Recombinar y generar descendientes:
					subPopulation = crossover.recombine(population(indexes));

					% Mutar el código genético de la sub-población:
					subPopulation = mutator.mutate(subPopulation);

					% Obtener índices de reemplazo:
					[old new] = generator.replace(selector,fitness,population,subPopulation,globalFitness);

					% Generar nueva población:
					population = [population(old), subPopulation(new)];

					% Actualizar el 'fitness':
					globalFitness = fitness.updateFitness(...
						globalFitness, old, subPopulation, new);

					% Graficar curva de 'fitness':
					grapher.addFitness(globalFitness);
				end

				% -------------------------------------------------------------

				% Mostrar el resultado final:
				Logger.logResult(population, globalFitness);

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
