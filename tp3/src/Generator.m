
	%{
		>>> Generator Class:

			Permite generar una población nueva para inicializar el algoritmo
			genético. Generar una nueva población implica crear N cromosomas.

			Además, permite manipular las opciones de reemplazo, las cuáles
			son aplicadas en cada ciclo de evolución.
	%}

	classdef Generator < handle

		properties (Access = protected)

			% Configuración original:
			config;
		end

		methods (Access = public)

			% Constructor:
			function this = Generator(config)

				this.config = config;
			end

			% Genera la población inicial:
			function population = init(this)

				global data;

				population = cell(1,this.config.population);

				for N = 1:this.config.population

					genes(1, 1 : size(data{1} , 2)) = randi([1 size(data{1},1)]);

					genes(6) = 1.3 + rand * (2.0-1.3);

					chromosome = Chromosome(genes);

					population{N} = chromosome;
				end

			end

			% Genera los índices de la siguiente población:
			function [old, new] = replace(this,selector,fitness,population,subPopulation,globalFitness)

				% IMPORTANTE: se devuelven los índices seleccionados de la
				%	población original y de la nueva. En cualquier caso, se
				%	debe verificar:
				%
				%		size(old) + size(new) = size(oldSize)

				%

				populationSize = this.config.population;

				subPopulationFitness = fitness.getGlobalFitness(subPopulation);
				subPopulationSize = size(subPopulation,2);

				if this.config.replacement == 1

					old = [];
					new = [1:subPopulationSize];

				end

				if this.config.replacement == 2

					previousGeneration = populationSize - subPopulationSize; % N-k de la generación anterior

					old = selector.select(previousGeneration, ...
							this.config.replacementMethod, this.config.replacementMethodRate, globalFitness);

					new = [1:subPopulationSize]; % k nuevos hijos
				end

				if this.config.replacement == 3

					previousGeneration = populationSize - subPopulationSize; % N-k de la generación anterior

					oldindexes = selector.select(previousGeneration , ...
							this.config.replacementMethod, this.config.replacementMethodRate, globalFitness);

					globalFitness = [globalFitness;fitness.getGlobalFitness(subPopulation)];

					indexes = selector.select(subPopulationSize, ...
							this.config.replacementMethod, this.config.replacementMethodRate, globalFitness);

					old = [oldindexes indexes(find(indexes <= length(population)))];
					new = indexes(find(indexes > length(population)))-length(population);

				end

				if not (size(old,2) + size(new,2) == this.config.population)
					disp('ERROR EN REEMPLAZO');
				end
			end
		end
	end
