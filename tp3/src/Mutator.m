
	%{
		>>> Mutator Class:

			Aplica el proceso de mutación genética sobre los cromosomas de la
			sub-población seleccionada.
	%}

	classdef Mutator < handle

		properties (Access = protected)

			% Propiedades...
		end

		methods (Access = public)

			% Constructor:
			function this = Mutator(config)

				% TODO: Completar...
			end

			% Selecciona los siguientes individuos:
			function population = mutate(this, population)

				% TODO: Completar...
				population = {};
			end
		end
	end
