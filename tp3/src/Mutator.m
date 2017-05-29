
	%{
		>>> Mutator Class:

			Aplica el proceso de mutación genética sobre los cromosomas de la
			sub-población seleccionada.
	%}

	classdef Mutator < handle

		properties (Access = protected)

			config;
		end

		methods (Access = public)

			% Constructor:
			function this = Mutator(config)

				this.config = config;
			end

			% Selecciona los siguientes individuos:
			function population = mutate(this, population)

				% IMPORTANTE: este método puede modificar libremente los
				%	cromosomas en 'population', ya que no son los originales
				%	(son copias), y se espera que la mutación los modifique.

				%Genes es vector fila o vector columna?
				%Not tested
				for n = 1:length(population)
					population{n}.genes(1:5) = mod((population{n}.genes(1:5) + binornd(1,config.mutationProbability,5,1).*randi([1 9],5,1)),10);
					if (rand<config.mutationProbability)
						population{n}.genes(6) = 1.3 + rand * (2.0-1.3);
					end
				end

			end
		end
	end
