
	%{
		>>> Crossover Class:

			Permite recombinar una sub-población para generar nuevos
			individuos y extender las capacidades de la población y la
			exploración del espacio de búsqueda.
	%}

	classdef Crossover < handle

		properties (Access = protected)

			% Propiedades...
		end

		methods (Access = public)

			% Constructor:
			function this = Crossover(config)

				% TODO: Completar...
			end

			% Selecciona los siguientes individuos:
			function population = recombine(this, population)

				% IMPORTANTE: este método no debe modificar los cromosomas en
				%	'population', sino copiar el contenido y generar nuevos
				%	cromosomas.

				% TODO: Completar...
				population = {};
			end
		end
	end
