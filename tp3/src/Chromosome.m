
	%{
		>>> Chromosome Class:

			Representa el código genético completo de un inidividuo de la
			población en un momento determinado. Para el problema modelado, el
			cromosoma posee los siguientes genes:

				<Armor, Boot, Gauntlet, Helmet, Weapon, Height>

			A su vez, cada uno de los genes se codifica como un número
			natural mayor o igual a 0, a excepción de la altura, la cual se
			codifica como un número real en el intervalo [1.3; 2.0].
	%}

	classdef Chromosome < handle

		properties (Access = public)

			% El vector de código genético:
			genes = [];
		end

		methods

			% Constructor:
			function this = Chromosome(genes)

				this.genes = genes;
			end

			% Obtener la matriz de estados:
			function stats = getStats(this)

				global data;

				stats(1, :) = data{1}(this.genes(1), 2:end);
				stats(2, :) = data{2}(this.genes(2), 2:end);
				stats(3, :) = data{3}(this.genes(3), 2:end);
				stats(4, :) = data{4}(this.genes(4), 2:end);
				stats(5, :) = data{5}(this.genes(5), 2:end);

				% Hay que cambiar esto!!!
				stats = table2array(stats);
			end

			% Devuelve el gen de altura:
			function height = getHeight(this)

				height = this.genes(6);
			end
		end
	end
