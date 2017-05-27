
	%{
		>>> Selector Class:

			El método de selección de individuos para evolucionar a la especie
			actual hacia la adaptación objetivo.
	%}

	classdef Selector < handle

		properties (Access = protected)

			% Propiedades...
		end

		methods (Access = public)

			% Constructor:
			function this = Selector(config)

				% TODO: Completar...
			end

			% Selecciona los siguientes individuos:
			function indexes = select(this, fitness)

				% TODO: Completar...
				indexes = [];
			end
		end
	end
