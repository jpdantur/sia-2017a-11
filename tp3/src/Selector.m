
	%{
		>>> Selector Class:

			El método de selección de individuos para evolucionar a la especie
			actual hacia la adaptación objetivo.
	%}

	classdef Selector < handle

		properties (Access = protected)

			% Propiedades...

			config;
			temperature;

		end

		methods (Access = public)

			% Constructor:
			function this = Selector(config)

				this.config = config;
				this.temperature = config.temperature;

			end

			% Selecciona los siguientes individuos:
			function indexes = select(this, selectionType, selection, fitness)

				indexes = [];

				if strcmp(selectionType,'selection')
					selectionMethods = this.config.selectionMethod;
					selectionRate = this.config.selectionMethodRate;
				elseif strcmp(selectionType,'replacement')
					selectionMethods = this.config.replacementMethod;
					selectionRate = this.config.replacementMethodRate;
				end

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
						case 'boltzmann'
							indexes = [indexes boltzmannSelection(this,fitness,selections(k))];
						case 'ranking'
							indexes = [indexes rankingSelection(this,fitness,selections(k))];
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

				indexes = zeros(1,selection);

				for i = 1:length(r)

					for j = 2:size(fitness,1)

						if(r(i) < fitness(1,3))
							indexes(i) = 1;
							break;
						end

						if (r(i) > fitness(j-1,3) && r(i) < fitness(j,3))

							indexes(i) = j;

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

				indexes = zeros(1,selection);

				for i = 1:length(r)

					for j = 2:size(fitness,1)

						if(r(i) < fitness(1,3))
							indexes(i) = 1;
							break;
						end

						if (r(i) > fitness(j-1,3) && r(i) < fitness(j,3))

							indexes(i) = j;

							break;

						end

					end

				end

			end

			function indexes = deterministicTournamentSelection (this,fitness,selection)

				indexes = zeros(1,selection);

				subSet = 2; % Tamaño del subconjunto

				for i = 1:selection

					[fitnessValue, index] = datasample(fitness(:,1), ...
						subSet ,'Replace',false); % Obtiene subconjunto sin reemplazo

					a = [fitnessValue index'];

					b = sortrows(a,-1); % Orden descendente

					indexes(i) = b(1,2);

				end

			end

			function indexes = probabilisticTournamentSelection (this,fitness,selection)

				indexes = zeros(1,selection);

				subSet = 2; % Tamaño del subconjunto

				for i = 1:selection


					[fitnessValue, index] = datasample(fitness(:,1), ...
						subSet ,'Replace',false); % Obtiene subconjunto sin reemplazo

					r = rand; % elijo un número al azar

					subSetFitness = [fitnessValue index']; % Obtengo el fitness y el índice de cada uno

					subSetFitness = sortrows(subSetFitness,-1); % Ordeno descendentemente segun el fitness

					if (r < 0.75)
						indexes(i) = subSetFitness(1,2); % elijo el índice del de mejor fitness
					else
						indexes(i) = subSetFitness(end,2); % elijo el índice del de peor fitness
					end

				end

			end

			function indexes = boltzmannSelection(this,fitness,selection)
				indexes = [];
				pressures = exp(fitness(:,1)/this.temperature);
				expValues = pressures/mean(pressures);
				relativeExpValues = expValues/sum(expValues);
				cumValues = cumsum(relativeExpValues);

				for i = 1:selection
					indexes(end+1) = sum(cumValues<rand)+1;
				end
			end
			function indexes = rankingSelection(this,fitness,selection)

				[sortedFitness,sortingIndexes] = sortrows(fitness(:,1),-1);

				sortedFitness(:,1) = fliplr(1:size(fitness,1));

				% Adaptación relativa:
				sortedFitness(:, 2) = sortedFitness(:, 1) / sum(sortedFitness(:, 1));

				% Adaptación acumulada:
				sortedFitness(:, 3) = cumsum(sortedFitness(:, 2));

				% Setear id
				sortedFitness(:,4) = sortingIndexes;

				id = sortedFitness(:,4)';

				indexes = id(this.rouletteSelection(sortedFitness,selection));
			end
			function this = updateTemperature(this)
				this.temperature = this.temperature - this.config.tempReductionRate*this.temperature;
			end
		end
	end
