
	%{
		>>> Cut-Off Class:

			Especifica el criterio de detenci??n para el ciclo de vida del
			algoritmo gen??tico. El criterio debe recibir la informaci??n
			necesaria para determinar su aserci??n.
	%}

	classdef CutOff < handle

		properties (Access = protected)

			% Configuraci??n:
			config;
			lastFitness;
			lastPopulation;
			contentAssertCounter = 0;

			% N??mero de generaci??n:
			generation = 0;
		end

		methods (Access = public)

			% Constructor:
			function this = CutOff(config)

				% TODO: Completar...
				this.config = config;
				this.lastFitness = 0;
			end

			% Determina si se debe correr otra generaci??n:
			function stop = assert(this, population, fitness)

				this.generation = this.generation + 1;

				%{
				TODO: Completar...Una parte relevante de la poblacion no cambia de
									generacion en generacion (Estructura).
				%}

				stop = this.generation > this.config.generations || max(fitness(:,1))>this.config.cutOffThreshold;
				if (this.config.contentAssert)
					stop = stop || this.contentAssert(fitness);
				end
				if (this.config.structAssert && this.generation > 1) %Primera generacion no se controla
					stop = stop || this.structAssert(population);
					if (stop)
						keyboard();
					end
				end
				this.lastFitness = max(fitness(:,1));
				this.lastPopulation = population;
			end

			function stop = contentAssert(this, fitness)
				peak = max(fitness(:,1))<=this.lastFitness;
				if (peak)
					this.contentAssertCounter = this.contentAssertCounter + 1;
				else
					this.contentAssertCounter = 0;
				end
				stop = this.contentAssertCounter >= this.config.contentAssertSteps;
			end

			function stop = structAssert(this,population)
				coincidences = arrayfun(@isequal,[population{:}],[this.lastPopulation{:}]);
				stop = (sum(coincidences)/size(population,2) > this.config.structAssertRatio);
			end

			function this = setPopulation(this, population)
				this.lastPopulation=population;
			end

		end
	end
