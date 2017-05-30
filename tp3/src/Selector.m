
	%{
		>>> Selector Class:

			El método de selección de individuos para evolucionar a la especie
			actual hacia la adaptación objetivo.
	%}

	classdef Selector < handle

		properties (Access = protected)

			% Propiedades...

			config;

			selectionMethods = [];

			selectionRate;

		end

		methods (Access = public)

			% Constructor:
			function this = Selector(config)

				this.config = config;
				this.selectionMethods = this.config.selectionMethod;
				this.selectionRate = config.selectionMethodRate;

			end

			% Selecciona los siguientes individuos:
			function indexes = select(this, fitness)

				% TODO: Porcentaje de k para cada selección

				indexes = [];

				for k = 1:length(this.selectionMethods)

					switch this.selectionMethods{k}

						case 'elite'
							indexes = [indexes eliteSelection(this,fitness)];
						case 'roulette'
							indexes = [indexes rouletteSelection(this,fitness)];
						case 'universal'
							indexes = [indexes universalSelection(this,fitness)];
						case 'deterministicTournament'
							indexes = [indexes deterministicTournamentSelection(this,fitness)];
						case 'probabilisticTournament'
							indexes = [indexes probabilisticTournamentSelection(this,fitness)];
						case 'bolzmann'
							%indexes = [indexes bolzmannSelection(this,fitness)];
						case 'ranking'
							%indexes = [indexes rankingSelection(this,fitness)];
						otherwise
							indexes = [];
					end

				end

			end

			function indexes = eliteSelection (this,fitness)

				[sortedFitness,sortingIndexes] = sortrows(fitness(:,1),-1);

				indexes = sortingIndexes(1:this.config.selection);

				indexes = indexes';

			end

			function indexes = rouletteSelection (this,fitness)

				r = rand(this.config.selection,1);

				indexes = [];

				for i = 1:length(r)

					for j = 2:size(fitness,1)

						if(r(i) < fitness(1,3))
							indexes(end+1) = 1;
							break;
						end

						if (r(i) > fitness(j-1,3) && r(i) < fitness(j,3))

							indexes(end+1) = j;

							break;

						end

					end

				end
			end

			function indexes = universalSelection (this,fitness)

				uni = rand;

				r = [];

				for j = 1:this.config.selection;
					r(end+1) = (uni + j - 1)/this.config.selection;
				end

				indexes = [];

				for i = 1:length(r)

					for j = 2:size(fitness,1)

						if(r(i) < fitness(1,3))
							indexes(end+1) = 1;
							break;
						end

						if (r(i) > fitness(j-1,3) && r(i) < fitness(j,3))

							indexes(end+1) = j;

							break;

						end

					end

				end

			end

			function indexes = deterministicTournamentSelection (this,fitness)

				indexes = [];

				for i = 1:this.config.selection

					[fitnessValue index] = datasample(fitness(:,1), ...
						this.config.tournamentSubset ,'Replace',false); % Obtiene subconjunto sin reemplazo

					a = [fitnessValue index'];

					b = sortrows(a,-1);

					indexes(end+1) = b(1,2);

				end

			end

			function indexes = probabilisticTournamentSelection (this,fitness) %TODO revisar

				indexes = [];

				r = rand;

				for i = 1:this.config.selection

					[fitnessValue index] = datasample(fitness(:,1), ...
						2 ,'Replace',false); % Obtiene subconjunto sin reemplazo

					a = [fitnessValue index'];

					b = sortrows(a,-1);

					if (r > 0.75)
						indexes(end+1) = b(1,2);
					else
						indexes(end+1) = b(end,2);
					end

				end

			end
		end
	end
