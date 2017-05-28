
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
				[ATM, DEM] = Fitness.mods(height);

				attack = (stats(2) + stats(3)) * stats(1) * ATM;
				defense = (stats(4) + stats(3)) * stats(5) * DEM;

				fitness = this.classPerformance * [attack; defense];
			end

			% Computa la adaptación de toda la población:
			function fitness = getGlobalFitness(this, population)

				% Inicializar:
				fitness = zeros(size(population, 2), 3);

				% Índice de adaptación:
				for k = 1:size(population, 2)
					fitness(k, 1) = this.getFitness(population{k});
				end

				% Adaptación relativa y acumulada:
				fitness = Fitness.fillFitness(fitness);
			end

			% Computar la adaptación global, de forma parcial:
			function fitness = updateFitness(this, ...
				globalFitness, old, subPopulation, new)

				fitness(:, 1) = globalFitness(old, 1);
				for k = 1:size(new, 2)

					fitness(end + 1, 1) = this.getFitness(...
						subPopulation{new(k)});
				end

				% Adaptación relativa y acumulada:
				fitness = Fitness.fillFitness(fitness);
			end
		end

		methods (Static, Access = protected)

			% Modificadores de ataque/defensa:
			function [ATM, DEM] = mods(h)

				% Horner's Rule (parcial):
				a = h * (9 * h - 30) + 25;
				b = - (a * a) + a + 0.5 * h;
				ATM = 0.5 + b;
				DEM = 2 - b;
			end

			% Computar adaptación relativa y acumulada:
			function fitness = fillFitness(fitness)

				% Adaptación relativa:
				fitness(:, 2) = fitness(:, 1) / sum(fitness(:, 1));

				% Adaptación acumulada:
				fitness(:, 3) = cumsum(fitness(:, 2));
			end
		end
	end
