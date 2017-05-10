
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

			% Nombre de las funciones:
			transferNames;

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

			% Proporción de instancias de entrenamiento:
			trainRatio = 0.75;

			% Momento de inercia de aprendizaje:
			momentum = 0;

			% Inyección de ruido:
			patternNoise = 0;
			weightNoise = 0;

			% Probabilidad de inyectar ruido en patrones:
			injectionProbability = 0;

			% Vanishing Gradient Problem:
			vanishingLimit = 0;

			% Graficar predicción final:
			graph = false;

			% Granularidad al graficar:
			granularity;

			% Graficar curvas de error:
			graphError = false;

			% Incremento para learning rate adaptativo:
			learningRateIncrement = 0.0000;

			% Decremento para learning rate adaptativo:
			learningRateDecrement = 0.0000;

			% Cantidad de pasos para adaptar el learning rate:
			minSteps = 0;

			% Pre/Post-procesamiento:
			processor;

			% Utilizar conjuntos de entrenamiento y testeo disjuntos:
			disjoint = true;
		end

		methods

			% Constructor:
			function this = Configurator(configuration)

				this.learningRate = configuration.learningRate;
				this.beta = configuration.beta;
				this.inputs = configuration.inputs;
				this.layerSizes = configuration.layerSizes;
				this.transfers = TransferFactory.generate(configuration);
				this.transferNames = configuration.transfers;
				this.problem = configuration.problem;
				this.epochs = configuration.epochs;
				this.error = configuration.error;
				this.trainRatio = configuration.trainRatio;
				this.momentum = configuration.momentum;
				this.injectionProbability = configuration.injectionProbability;
				this.patternNoise = configuration.patternNoise;
				this.weightNoise = configuration.weightNoise;
				this.vanishingLimit = configuration.vanishingLimit;
				this.graph = configuration.graph;
				this.graphError = configuration.graphError;
				this.learningRateIncrement = configuration.learningRateIncrement;
				this.learningRateDecrement = configuration.learningRateDecrement;
				this.minSteps = configuration.minSteps;
				this.processor = Processor(configuration.processor);
				this.granularity = configuration.granularity;
				this.disjoint = configuration.disjoint;

				% Cargar especificación del problema:
				specification = importdata(this.problem);
				this.instances = specification.data(:, 1:this.inputs);
				this.targets = specification.data(:, 1 + this.inputs:end);
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
