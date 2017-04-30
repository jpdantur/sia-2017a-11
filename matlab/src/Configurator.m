
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

			% Tasa de aprendizaje:
			learningRate = 0.1;

			% Beta-value:
			beta = 1;

			% Cantidad de entradas:
			inputs = 1;

			% Arquitectura de la red:
			layerSizes = [1];

			% Funciones de activación por capa:
			transfers = {@sign, @(x) 1};

			% Instancias del problema:
			problem = '';

			% Cantidad de entrenamientos:
			epochs = 1;

			% Error máximo admitido:
			error = 1;

			% Predictores:
			instances = [];

			% Objetivos:
			targets = [];
		end

		methods

			% Constructor:
			function this = Configurator(configuration)

				this.learningRate = configuration.learningRate;
				this.beta = configuration.beta;
				this.inputs = configuration.inputs;
				this.layerSizes = configuration.layerSizes;
				this.transfers = TransferFactory.generate(configuration);
				this.problem = configuration.problem;
				this.epochs = configuration.epochs;
				this.error = configuration.error;

				% Cargar especificación del problema:
				specification = importdata(this.problem);
				this.instances = specification.data(:, 1:end - 1);
				this.targets = specification.data(:, end);
			end
		end

		methods (Static)

			% Fábrica de configuradores:
			function configurator = load(filename)

				addpath(genpath('../lib/jsonlab-1.5'));
				configuration = loadjson(filename);
				configurator = Configurator(configuration);
			end
		end
	end
