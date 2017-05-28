
	%{
		>>> Generator Class:

			Permite generar una población nueva para inicializar el algoritmo
			genético. Generar una nueva población implica crear N cromosomas.

			Además, permite manipular las opciones de reemplazo, las cuáles
			son aplicadas en cada ciclo de evolución.
	%}

	classdef Generator < handle

		properties (Access = protected)

			% Configuración original:
			config;
		end

		methods (Access = public)

			% Constructor:
			function this = Generator(config)

				this.config = config;
			end

			% Genera la población inicial:
			function population = init(this)

				% TODO: Completar...
				population = {};
			end

			% Genera los índices de la siguiente población:
			function [old, new] = replace(this, oldSize, newSize)

				% IMPORTANTE: se devuelven los índices seleccionados de la
				%	población original y de la nueva. En cualquier caso, se
				%	debe verificar:
				%
				%		size(old) + size(new) = size(oldSize)

				% TODO: Completar...
				old = 1:oldSize;
				new = [];
			end
		end
	end
