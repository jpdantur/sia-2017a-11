
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

			% Selecciona los siguientes individuos:
			function population = recombine(this, population)

				selected = population;

				population = {};

				% IMPORTANTE: este método no debe modificar los cromosomas en
				%	'population', sino copiar el contenido y generar nuevos
				%	cromosomas.

				% TODO: Completar...

				for i = 1:length(selected)-1

					if rand < this.config.crossoverProbability

						parent1 = selected{i};

						parent2 = selected{i+1};

						descendant = this.pointCross(parent1,parent2,randi(1,5)); %% TODO Parametrizar o random?

						population{end+1} = descendant;

					end

				end

			end

			function descendant = pointCross(this,parent1,parent2,point)

				descendant = Chromosome([parent1.genes(1:point) parent2.genes(point+1:6)]); %%TODO Reemplazar 6 por end

			end

			function descendant = twopointCross(this,parent1,parent2,points)

				%descendant = Chromosome([parent1.genes(1:points(1)) parent2.genes(1,points(1):points(2)) [parent1.genes(points(2):end)])

			end
		end
	end
