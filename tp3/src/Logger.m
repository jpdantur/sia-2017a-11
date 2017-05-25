
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
				fprintf('\t    Armors Dataset : ''%s''\t[%ix%i]\n', config.json.armors, size(data{1}));
				fprintf('\t     Boots Dataset : ''%s''\t\t[%ix%i]\n', config.json.boots, size(data{2}));
				fprintf('\t Gauntlets Dataset : ''%s''\t[%ix%i]\n', config.json.gauntlets, size(data{3}));
				fprintf('\t   Helmets Dataset : ''%s''\t[%ix%i]\n', config.json.helmets, size(data{4}));
				fprintf('\t   Weapons Dataset : ''%s''\t\t[%ix%i]\n', config.json.weapons, size(data{5}));
				fprintf('\t   Attack Defense Rate : %.1f\n', config.attackDefenseRate);
				fprintf('\t   Crossover Method : %s\n', config.crossoverMethod);
				fprintf('\t   Crossover Probability : %.3f\n', config.crossoverProbability);
				fprintf('\t   Generational Gap (G) : %.3f\n', config.generationalGap);
				fprintf('\t   Generations : %d\n', config.generations);
				fprintf('\t   Mutation Probability : %.3f\n', config.mutationProbability);
				fprintf('\t   Population : %d\n', config.population);
				fprintf('\t   Replacement Method : %s\n', Logger.vectorToString(config.replacementMethod){1});
				fprintf('\t   Replacement Method Rate : %.3f\n', config.replacementMethodRate);
				fprintf('\t   Selection Method : %s\n', Logger.stringsToString(config.selectionMethod){1});
				fprintf('\t   Selection Method Rate : %.3f\n', config.selectionMethodRate);
				fprintf('\n');
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
