
	%{
		>>> Configurator Class:

			Permite obtener la configuración del sistema desde un archivo en
			formato JSON, y provee métodos para acceder a ella.

			El parser de JSON se obtuvo desde el repositorio (@link), y se
			corresponde con el paquete de funciones JSON-Lab 1.5, para Matlab.

			@link https://github.com/fangq/jsonlab/releases
	%}

	classdef Configurator < handle

		properties (Access = public)

			% JSON original:
			json;

			output;

			attackDefenseRate = 0.1;

			crossoverMethod = 'singlepoint';

			crossoverProbability = 0.6;

			generations = 100;

			itemStrength = 1.1;

			itemAgility = 0.8;

			itemExpertise = 0.8;

			itemResistance = 1.1;

			itemHealth = 1.1;

			mutationProbability = 0.01;

			population = 100;

			replacement = 2;

			replacementMethod = ['elite','roulette'];

			replacementMethodRate = 0.5;

			selection = 5;

			selectionMethod = ['elite','roulette'];

			selectionMethodRate = 0.5;

			temperature = 1.0;

			graphRateLimit = true;

			graphFitness = true;

			cutOffThreshold;

			tempReductionRate;

			contentAssert = false;

			contentAssertSteps = 5;

			structAssert = false;

			structAssertRatio = 1;

		end

		methods

			% Constructor:
			function this = Configurator(config, lazyness)

				global data;

				if lazyness == false
					% Cargar set de datos...
					data{1} = Configurator.loadTSV(config.armors);
					data{2} = Configurator.loadTSV(config.boots);
					data{3} = Configurator.loadTSV(config.gauntlets);
					data{4} = Configurator.loadTSV(config.helmets);
					data{5} = Configurator.loadTSV(config.weapons);
				end

				% Configuración normal...
				this.json = config;
				this.output = config.output;
				this.attackDefenseRate = config.attackDefenseRate;
				this.crossoverMethod = config.crossoverMethod;
				this.crossoverProbability = config.crossoverProbability;
				this.generations = config.generations;
				this.itemStrength = config.itemStrength;
				this.itemAgility = config.itemAgility;
				this.itemExpertise = config.itemExpertise;
				this.itemResistance = config.itemResistance;
				this.itemHealth = config.itemHealth;
				this.mutationProbability = config.mutationProbability;
				this.population = config.population;
				this.replacement = config.replacement;
				this.replacementMethod = config.replacementMethod;
				this.replacementMethodRate = config.replacementMethodRate;
				this.selection = config.selection;
				this.selectionMethod = config.selectionMethod;
				this.selectionMethodRate = config.selectionMethodRate;
				this.temperature = config.temperature;
				this.graphRateLimit = config.graphRateLimit;
				this.graphFitness = config.graphFitness;
				this.cutOffThreshold = config.cutOffThreshold;
				this.tempReductionRate = config.tempReductionRate;
				this.contentAssert = config.contentAssert;
				this.contentAssertSteps = config.contentAssertSteps;
				this.structAssert = config.structAssert;
				this.structAssertRatio = config.structAssertRatio;

				if this.selection > this.population
					error('El parametro selection debe ser menor o igual a population')
				end

			end
		end

		methods (Static)

			% Fábrica de configuradores:
			function configurator = load(filename, lazyness)

				addpath(genpath('../lib/jsonlab-1.5'));
				config = loadjson(filename);
				configurator = Configurator(config, lazyness);
			end

			function data = loadTSV(filename)

				data = table2array(readtable(filename, ...
					'ReadVariableNames', true, ...
					'FileType', 'text', ...
					'Delimiter', '\t'));
			end
		end
	end
