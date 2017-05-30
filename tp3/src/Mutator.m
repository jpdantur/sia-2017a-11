
	%{
		>>> Mutator Class:

			Aplica el proceso de mutaci??n gen??tica sobre los cromosomas de la
			sub-poblaci??n seleccionada.
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

				% IMPORTANTE: este m??todo puede modificar libremente los
				%	cromosomas en 'population', ya que no son los originales
				%	(son copias), y se espera que la mutaci??n los modifique.


				for n = 1:length(population)
					population{n}.genes(1:5) = mod(((population{n}.genes(1:5)-1) + binornd(1,this.config.mutationProbability,1,5).*randi([1 9],1,5)),10)+1;
					if (rand<this.config.mutationProbability)
						population{n}.genes(6) = 1.3 + rand * (2.0-1.3);
					end
				end

			end
		end
	end
