
	%{
		>>> Selector Class:

			El método de selección de individuos para evolucionar a la especie
			actual hacia la adaptación objetivo.
	%}

	classdef Selector < handle

		properties (Access = protected)

			% Propiedades...

			config;

		end

		methods (Access = public)

			% Constructor:
			function this = Selector(config)

				this.config = config;

			end

			% Selecciona los siguientes individuos:
			function indexes = select(this, selection, selectionMethods, selectionRate, fitness)

				indexes = [];

				rate = floor(selection*selectionRate);

				selections = [rate selection-rate];

				for k = 1:length(selectionMethods)

					switch selectionMethods{k}

						case 'elite'
							indexes = [indexes eliteSelection(this,fitness,selections(k)) ];
						case 'roulette'
							indexes = [indexes rouletteSelection(this,fitness,selections(k)) ];
						case 'universal'
							indexes = [indexes universalSelection(this,fitness,selections(k)) ];
						case 'deterministicTournament'
							indexes = [indexes deterministicTournamentSelection(this,fitness,selections(k)) ];
						case 'probabilisticTournament'
							indexes = [indexes probabilisticTournamentSelection(this,fitness,selections(k)) ];
						case 'bolzmann'
							%indexes = [indexes bolzmannSelection(this,fitness)];
						case 'ranking'
							%indexes = [indexes rankingSelection(this,fitness)];
						otherwise
							indexes = [];
					end

				end

			end

			function indexes = eliteSelection (this,fitness,selection)

				[sortedFitness,sortingIndexes] = sortrows(fitness(:,1),-1);

				if size(fitness,1)>=selection

					indexes = sortingIndexes(1:selection);

				else

					indexes = rouletteSelection(fitness,selection);
					return;

				end

				indexes = indexes';

			end

			function indexes = rouletteSelection (this,fitness,selection)

				r = rand(selection,1);

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

			function indexes = universalSelection (this,fitness,selection)

				uni = rand;

				r = zeros(1,selection);

				for j = 1:selection
					r(j) = (uni + j - 1)/(selection);
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

			function indexes = deterministicTournamentSelection (this,fitness,selection)

				indexes = [];

				for i = 1:selection

					[fitnessValue, index] = datasample(fitness(:,1), ...
						this.config.tournamentSubset ,'Replace',false); % Obtiene subconjunto sin reemplazo

					a = [fitnessValue index'];

					b = sortrows(a,-1);

					indexes(end+1) = b(1,2);

				end

			end

			function indexes = probabilisticTournamentSelection (this,fitness,selection) %TODO revisar

				indexes = [];

				r = rand;

				for i = 1:selection

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
