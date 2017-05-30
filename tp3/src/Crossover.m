
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

				% TODO cambiar criterio de elección de padres

				for i = 1:length(selected)-1

					if rand < this.config.crossoverProbability

						parent1 = selected{i};

						parent2 = selected{i+1};

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

						population{end+1} = descendants{1};
						population{end+1} = descendants{2};

					end

                end

			end

			function descendant = singlePointCross(this,parent1,parent2)

				descendant = {};

				point = randi([1 5]);

				descendant{1} = Chromosome([parent1.genes(1:point) parent2.genes(point+1:6)]); %TODO Reemplazar 6 por end
				descendant{2} = Chromosome([parent2.genes(1:point) parent1.genes(point+1:6)]);

			end

			function descendant = twoPointCross(this,parent1,parent2)

				descendant = {};

				point1 = randi([1 4]);
				point2 = randi([point1+1 5]);

				descendant{1} = Chromosome([parent1.genes(1:point1) parent2.genes(point1+1:point2) parent1.genes(point2+1:end)]);
				descendant{2} = Chromosome([parent2.genes(1:point1) parent1.genes(point1+1:point2) parent2.genes(point2+1:end)]);

			end

			function descendant = anularCross(this,parent1,parent2)
				% Se elige un locus y luego un segmento,
				% hacia la derecha, de longitud l en el intervalo [1, L/2], donde L es la longitud del cromosoma.
				% TODO

				descendant = {};
			end

			function descendant = uniformCross(this,parent1,parent2)

				p = 0.5;

				descendant = {};
				g1 = parent1.genes;
				g2 = parent2.genes;

				for i = 1:6

					if not ( g1(i) == g2(i) )

						if (rand < p)

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
