
	%{
		>>> Cut-Off Class:

			Especifica el criterio de detención para el ciclo de vida del
			algoritmo genético. El criterio debe recibir la información
			necesaria para determinar su aserción.
	%}

	classdef CutOff < handle

		properties (Access = protected)

			% Configuración:
			config;

			% Número de generación:
			generation = 0;
		end

		methods (Access = public)

			% Constructor:
			function this = CutOff(config)

				% TODO: Completar...
				this.config = config;
			end

			% Determina si se debe correr otra generación:
			function stop = assert(this, population, fitness)

				this.generation = this.generation + 1;

				% TODO: Completar...

				stop = this.generation > this.config.generations;
			end
		end
	end
