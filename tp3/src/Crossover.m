
	%{
		>>> Crossover Class:

			Permite recombinar una sub-población para generar nuevos
			individuos y extender las capacidades de la población y la
			exploración del espacio de búsqueda.
	%}

	classdef Crossover < handle

		properties (Access = protected)

			% Propiedades...

			config;

		end

		methods (Access = public)

			% Constructor:
			function this = Crossover(config)

				this.config = config;

			end

			function population = recombine(this, selected)

				population = {};

				descendants = {};

				if this.config.replacement == 1
					populationLimit = this.config.population;
				else
					populationLimit = this.config.selection;
				end

					while length(population) < populationLimit

						parent1 = selected{randi(length(selected))};
						parent2 = selected{randi(length(selected))};

						if rand < this.config.crossoverProbability

							% elijo 2 padres al azar y los cruzo

							switch this.config.crossoverMethod
								case 'singlepoint'
									descendants = this.singlePointCross(parent1,parent2);
								case 'twopoint'
									descendants = this.twoPointCross(parent1,parent2);
								case 'uniform'
									descendants = this.uniformCross(parent1,parent2);
								case 'anular'
									descendants = this.anularCross(parent1,parent2);
							end

						else

							% los padres pasan a ser hijos

							descendants{end+1} = parent1;

							descendants{end+1} = parent2;

						end

						population{end+1} = descendants{1};

						if length(population) < this.config.population
							population{end+1} = descendants{2};
						end

					end

					return;

			end

			function descendant = singlePointCross(this,parent1,parent2)

				descendant = {};

				point = randi([1 5]);

				descendant{1} = Chromosome([parent1.genes(1:point) parent2.genes(point+1:6)]);
				descendant{2} = Chromosome([parent2.genes(1:point) parent1.genes(point+1:6)]);

			end

			function descendant = twoPointCross(this,parent1,parent2)

				descendant = {};

				point1 = randi([1 4]);
				point2 = randi([point1+1 5]);

				descendant{1} = Chromosome([parent1.genes(1:point1) parent2.genes(point1+1:point2) parent1.genes(point2+1:6)]);
				descendant{2} = Chromosome([parent2.genes(1:point1) parent1.genes(point1+1:point2) parent2.genes(point2+1:6)]);

			end

			function descendant = anularCross(this,parent1,parent2)

				descendant = {};
				L = 6;

				point1 = randi([1 L]);
				l = randi([1 L/2]);
				point2 = mod(point1 + l,L);

				if point2 == 0
					point2 = L;
				end

				g1 = parent1.genes;
				g2 = parent2.genes;

				if point1 < point2

					g1(point1:point2) = g2(point1:point2);

					g1(point1:point2) = parent1.genes(point1:point2);

				end

				if (point1 > point2)
					g1(point1:6) = g2(point1:6);
					g1(1:point2) = g2(1:point2);
					g2(point1:6) = parent1.genes(point1:6);
					g2(1:point2) = parent1.genes(1:point2);
				end

				descendant{1} = Chromosome(g1);
				descendant{2} = Chromosome(g2);
			end

			function descendant = uniformCross(this,parent1,parent2)

				p = 0.5;

				descendant = {};
				g1 = parent1.genes;
				g2 = parent2.genes;

				for i = 1:6

					if not ( g1(i) == g2(i) )

						if (rand < p)

							% Intercambio genes

							aux = g1(i);

							g1(i) = g2(i);

							g2(i) = aux;

						end

					end

				end

				descendant{1} = Chromosome(g1);
				descendant{2} = Chromosome(g2);

			end
		end
	end
