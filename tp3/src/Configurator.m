
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

			json;
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
