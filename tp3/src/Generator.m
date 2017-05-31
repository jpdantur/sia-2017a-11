
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

				if this.config.replacement == 1

					old = [];
					new = selector.select(populationSize,...  % Toda la población va a ser nueva
							this.config.replacementMethod, this.config.replacementMethodRate, subPopulationFitness);

				end

				if this.config.replacement == 2

					previousGeneration = floor(populationSize*(1-this.config.generationalGap));
					newGeneration = populationSize-previousGeneration;

					old = selector.select(previousGeneration, ...
							this.config.replacementMethod, this.config.replacementMethodRate, globalFitness);

					new = selector.select(newGeneration, ...
							this.config.replacementMethod, this.config.replacementMethodRate, subPopulationFitness);
				end

				if this.config.replacement == 3

					previousGeneration = floor(populationSize*(1-this.config.generationalGap));
					newGeneration = populationSize-previousGeneration;

					oldindexes = selector.select(previousGeneration , ...
							this.config.replacementMethod, this.config.replacementMethodRate, globalFitness);

					globalFitness = [globalFitness;fitness.getGlobalFitness(subPopulation)];

					indexes = selector.select(newGeneration, ...
							this.config.replacementMethod, this.config.replacementMethodRate, globalFitness);

					old = [oldindexes indexes(find(indexes <= length(population)))];
					new = indexes(find(indexes > length(population)))-length(population);

				end
			end
		end
	end
