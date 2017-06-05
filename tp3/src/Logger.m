
	%{
		>>> Logger Class:

			Permite manipular los mensajes en consola de forma centralizada,
			lo que evita ofuscar el resto de algoritmos.
	%}

	classdef Logger

		methods (Static, Access = public)

			% Muestra la configuración global:
			function logConfiguration(config)

				global data;

				fprintf('\n');
				fprintf('\t           Armors Dataset : ''%s''\t[%ix%i]\n', config.json.armors, size(data{1}));
				fprintf('\t            Boots Dataset : ''%s''\t\t[%ix%i]\n', config.json.boots, size(data{2}));
				fprintf('\t        Gauntlets Dataset : ''%s''\t[%ix%i]\n', config.json.gauntlets, size(data{3}));
				fprintf('\t          Helmets Dataset : ''%s''\t\t[%ix%i]\n', config.json.helmets, size(data{4}));
				fprintf('\t          Weapons Dataset : ''%s''\t\t[%ix%i]\n', config.json.weapons, size(data{5}));

				fprintf('\n');
				fprintf('\t         JSON Output File : ''%s''\n', config.output);

				fprintf('\n');
				fprintf('\t      Attack/Defense Rate : %.1f\n', config.attackDefenseRate);

				fprintf('\n');
				fprintf('\t           Class Strength : %.2f\n', config.itemStrength);
				fprintf('\t            Class Agility : %.2f\n', config.itemAgility);
				fprintf('\t          Class Expertise : %.2f\n', config.itemExpertise);
				fprintf('\t         Class Resistance : %.2f\n', config.itemResistance);
				fprintf('\t             Class Health : %.2f\n', config.itemHealth);

				fprintf('\n');
				fprintf('\t               Population : %d\n', config.population);
				fprintf('\t              Generations : %d\n', config.generations);
				fprintf('\t        Cut-off Threshold : %d\n', config.cutOffThreshold);
				fprintf('\t   Content-based Cut-off? : %s\n', Logger.boolToString(config.contentAssert));
				fprintf('\t            Content Steps : %d\n', config.contentAssertSteps);
				fprintf('\t    Struct-based Cut-off? : %s\n', Logger.boolToString(config.structAssert));
				fprintf('\t             Struct Ratio : %.3f\n', config.structAssertRatio);

				fprintf('\n');
				fprintf('\t  Sub-population Size (k) : %d\n', config.selection);
				fprintf('\t        Selection Methods : %s\n', Logger.stringsToString(config.selectionMethod){1});
				fprintf('\t    Selection Method Rate : %.3f\n', config.selectionMethodRate);
				fprintf('\tBoltzmann Temperature (T) : %d\n', config.temperature);
				fprintf('\t     Temp. Reduction Rate : %d\n', config.tempReductionRate);

				fprintf('\n');
				fprintf('\t    Crossover Probability : %.3f\n', config.crossoverProbability);
				fprintf('\t         Crossover Method : %s\n', config.crossoverMethod);

				fprintf('\n');
				fprintf('\t     Mutation Probability : %.3f\n', config.mutationProbability);

				fprintf('\n');
				fprintf('\t       Replacement Method : %d\n', config.replacement);
				fprintf('\t      Replacement Methods : %s\n', Logger.stringsToString(config.replacementMethod){1});
				fprintf('\t  Replacement Method Rate : %.3f\n', config.replacementMethodRate);

				fprintf('\n');
				fprintf('\t           Graph Fitness? : %s\n', Logger.boolToString(config.graphFitness));
				fprintf('\t               Limit FPS? : %s\n', Logger.boolToString(config.graphRateLimit));
				fprintf('\n');
			end

			% Mostrar el resultado final:
			function logResult(population, fitness)

				[maxFitness, index] = max(fitness(:, 1));
				stats = population{index}.getFullStats();

				format long;
				Cromosoma = table(...
					stats(:, 1), stats(:, 2), stats(:, 3), stats(:, 4), stats(:, 5), stats(:, 6), ...
					'RowNames', {'Armor', 'Boots', 'Gauntlet', 'Helmet', 'Weapon'}, ...
					'VariableNames', {'ID', 'Strength', 'Agility', 'Expertise', 'Resistance', 'Health'})
				format short;

				fprintf('\n');
				fprintf('Adaptacion maxima final: %.6f\n', maxFitness);
				fprintf('Altura final: %.6f\n', population{index}.getHeight());
			end

			% Mostrar el tiempo de carga de datos y configuración:
			function logLoadingTime(time, lazyness)

				if lazyness == true
					fprintf('\n\tUtilizando el antiguo set de datos...\n');
				else
					fprintf('\n\tNuevo set de datos cargado...\n');
				end
				fprintf('\tConfiguracion instalada en %.6f segundos.\n\n', time);
			end

			% Mostrar el tiempo final de ejecución:
			function logExecutionTime(time)

				fprintf('Tiempo de ejecucion: %.6f segundos.\n', time);
			end

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
		end
	end
