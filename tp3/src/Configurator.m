
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

			attackDefenseRate = 0.1;

			crossoverMethod = 'singlepoint';

			crossoverProbability = 0.6;

			generations = 100;

			generationalGap = 1;

			itemStrength = 1.1;

			itemAgility = 0.8;

			itemExpertise = 0.8;

			itemResistance = 1.1;

			itemHealth = 1.1;

			mutationProbability = 0.01;

			population = 100;

			replacementMethod = [1, 2];

			replacementMethodRate = 0.5;

			selection = 5;

			selectionMethod;

			selectionMethodRate = 0.5;

			temperature = 1.0;

			tournamentSubset = 1;

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
				this.attackDefenseRate = config.attackDefenseRate;
				this.crossoverMethod = config.crossoverMethod;
				this.crossoverProbability = config.crossoverProbability;
				this.generations = config.generations;
				this.generationalGap = config.generationalGap;
				this.itemStrength = config.itemStrength;
				this.itemAgility = config.itemAgility;
				this.itemExpertise = config.itemExpertise;
				this.itemResistance = config.itemResistance;
				this.itemHealth = config.itemHealth;
				this.mutationProbability = config.mutationProbability;
				this.population = config.population;
				this.replacementMethod = config.replacementMethod;
				this.replacementMethodRate = config.replacementMethodRate;
				this.selection = config.selection;
				this.selectionMethod = config.selectionMethod;
				this.selectionMethodRate = config.selectionMethodRate;
				this.temperature = config.temperature;
				this.tournamentSubset = config.tournamentSubset;
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

				data = readtable(filename, ...
					'ReadVariableNames', true, ...
					'FileType', 'text', ...
					'Delimiter', '\t');
			end
		end
	end
