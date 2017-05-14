
	%{
		>>> OutputGrapher Class:

			Grafica ScatterPlots de tres dimensiones			
	%}

	classdef OutputGrapher < handle

		methods (Static)

			function surfacePlot(config,perceptron)
				% Imprimir el terreno aprendido:
					baseX = min(config.instances(:,1));
					baseY = min(config.instances(:,2));
					topX = max(config.instances(:,1));
					topY = max(config.instances(:,2));

					dotsX = baseX:config.granularity:topX;
					dotsY = baseY:config.granularity:topY;

					[X, Y] = meshgrid(dotsX,dotsY);
					for r = 1:size(Y, 1)
						for c = 1:size(X, 2)
							Z(r, c) = perceptron.predict([X(r, c), Y(r, c)]);
						end
					end

					figure;
					surf(X, Y, Z);
					title('Prediction');

					hold on;

					% Deja los puntos para que se distinga la interpolación:
					scatter3( ...
						config.instances(:, 1), ...
						config.instances(:, 2), ...
						config.targets(:, 1), ...
						'.', ...
						'SizeData', 100, ...
						'MarkerEdgeColor', 'r', ...
						'MarkerFaceColor', 'r');
					
					% Imprimir el gráfico original:
					F = scatteredInterpolant( ...
						config.instances(:, 1), ...
						config.instances(:, 2), ...
						config.targets(:, 1));

					Z = F(X, Y);
					figure;
					surf(X, Y, Z);
					title('Interpolation');
					
					hold on;

					% Deja los puntos para que se distinga la interpolación:
					scatter3( ...
						config.instances(:, 1), ...
						config.instances(:, 2), ...
						config.targets(:, 1), ...
						'.', ...
						'SizeData', 100, ...
						'MarkerEdgeColor', 'r', ...
						'MarkerFaceColor', 'r');
				end

		end
	end

