
	%{
		>>> Fitness Class:

			Se encarga de evaluar el índice de adaptación para cada cromosoma
			de la población. Además provee métodos de interés sobre este
			índice, para el resto de las clases.
	%}

	classdef Fitness < handle

		properties (Access = protected)

			% La configuración utilizada:
			config;

			% Modificadores de clase (vector fila):
			classMods = [];

			% Desempeño por clase (vector fila):
			classPerformance = [];
		end

		methods

			% Constructor:
			function this = Fitness(config)

				this.config = config;

				this.classMods = [config.itemStrength, ...
					config.itemAgility, ...
					config.itemExpertise, ...
					config.itemResistance, ...
					config.itemHealth];

				this.classPerformance = [config.attackDefenseRate, ...
					1 - config.attackDefenseRate];
			end

			% Computa el índice de adaptación del cromosoma:
			function fitness = getFitness(this, chromosome)

				stats = sum(chromosome.getStats(), 1) .* this.classMods;
				stats = [100, 1, 0.6, 1, 100] .* tanh(0.01 * stats);

				height = chromosome.getHeight();
				attack = (stats(2) + stats(3)) * stats(1) * Fitness.attackMod(height);
				defense = (stats(4) + stats(3)) * stats(5) * Fitness.defenseMod(height);

				fitness = this.classPerformance * [attack; defense];
			end

			% Computa la adaptación de toda la población:
			function fitness = getGlobalFitness(this, population)

				fitness = [0, 0, 0];

				% Índice de adaptación:
				for k = 1:size(population)
					fitness(k, 1) = this.getFitness(population{k});
				end

				% Adaptación relativa:
				fitness(:, 2) = fitness(:, 1) / sum(fitness(:, 1));

				% Adaptación acumulada:
				fitness(:, 3) = cumsum(fitness(:, 2));
			end
		end

		methods (Static, Access = protected)

			% Modificador de ataque:
			function modifier = attackMod(height)

				modifier = 0.5 - (3 * height - 5)^4 + (3 * height - 5)^2 + 0.5 * height;
			end

			% Modificador de defensa:
			function modifier = defenseMod(height)

				modifier = 2 + (3 * height - 5)^4 - (3 * height - 5)^2 - 0.5 * height;
			end
		end
	end
