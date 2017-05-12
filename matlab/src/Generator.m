
	%{
		>>> Generator Class:

			Permite generar conjuntos de instancias de entrenamiento y testeo
			en función de un set de datos de origen. Los conjuntos pueden ser,
			opcionalmente, disjuntos o no.
	%}

	classdef Generator < handle

		properties (Access = protected)

			% Conjuntos de entrenamiento y testeo:
			trainSet;
			testSet;

			% Objetivos de entrenamiento y testeo:
			trainTargets;
			testTargets;

			% Los sets completos (desordenados):
			instanceJoin;
			targetJoin;

			% Usar conjuntos disjuntos?:
			disjoint = true;

			% Relación de entrenamiento:
			trainRatio = 0.5;

			% Porcentaje de muestreo:
			sampleRatio = 0.5;

			patternInjectionProbability = 0;
			patternNoise = 0;
		end

		methods

			% Constructor:
			function this = Generator(configuration)

				this.disjoint = configuration.disjoint;
				this.trainRatio = configuration.trainRatio;
				this.sampleRatio = configuration.sampleRatio;

				% Genera 2 conjuntos disjuntos, siempre:
				[trainIndexes, testIndexes] = Generator.getIndexes( ...
					size(configuration.instances, 1), ...
					this.trainRatio);

				this.trainSet = configuration.instances(trainIndexes, :);
				this.testSet = configuration.instances(testIndexes, :);

				this.trainTargets = configuration.targets(trainIndexes, :);
				this.testTargets = configuration.targets(testIndexes, :);

				this.instanceJoin = [this.trainSet; this.testSet];
				this.targetJoin = [this.trainTargets; this.testTargets];

				this.patternNoise = configuration.patternNoise;
				this.patternInjectionProbability = configuration.patternInjectionProbability;
			end

			% Genera una muestra de entrenamiento/testeo:
			function [trainInstances, trainTargets, ...
				testInstances, testTargets] = getSample(this)

				if true == this.disjoint

					[trainInstances, trainTargets, ~] = Generator...
						.getSingleSample( ...
							this.trainSet, this.trainTargets, this.sampleRatio, this.patternNoise, this.patternInjectionProbability);

					[testInstances, testTargets, ~] = Generator...
						.getSingleSample( ...
							this.testSet, this.testTargets, this.sampleRatio, this.patternNoise, this.patternInjectionProbability);
				else

					[trainInstances, trainTargets, remain] = Generator...
						.getSingleSample( ...
							this.instanceJoin, this.targetJoin, this.trainRatio, this.patternNoise, this.patternInjectionProbability);

					testInstances = this.instanceJoin(remain, :);
					testTargets = this.targetJoin(remain, :);
				end
			end
		end

		methods (Static, Access = protected)

			% Computa una selección aleatoria de índices:
			function [trainIndexes, testIndexes] = getIndexes(instances, ratio)

				trainSize = round(instances * ratio);
				trainIndexes = randperm(instances, trainSize);
				testIndexes = setdiff(1:instances, trainIndexes);
			end

			function [instances, targets, remainingIndexes] = getSingleSample( ...
				sampleSet, sampleTargets, ratio, patternNoise, patternInjectionProbability)

				[indexes, remainingIndexes] = Generator.getIndexes( ...
					size(sampleSet, 1), ...
					ratio);
				keyboard();
				instances = sampleSet(indexes, :) + repmat(binornd(1,patternInjectionProbability,size(indexes,2),1) * patternNoise,1,size(sampleSet,2));
				targets = sampleTargets(indexes, :);
			end
		end
	end
