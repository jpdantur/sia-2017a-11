
	%{
		>>> Grapher Class:

			Gestiona los gráficos de forma centralizada, y provee mencanismos
			para dibujar en tiempo real (streaming).
	%}

	classdef Grapher < handle

		properties (Access = public)

			% La configuración original:
			config;

			% Generation X-Axis:
			x = 0;

			% Curvas de 'fitness':
			fitness;
			fitnessAverage;

			% Handler de la curva de 'fitness':
			fitnessHandler;
		end

		methods

			% Constructor:
			function this = Grapher(config)

				this.config = config;

				if config.graphFitness == true

					% Debería variar el máximo estimado?
					attackRate = config.attackDefenseRate;
					defenseRate = 1 - attackRate;
					estimatedFitness = 1 + round(160 * (attackRate * 1.61 + defenseRate * 1.71));

					this.fitnessHandler = figure(...
						'Name', 'Curvas de Adaptacion Maxima y Media', ...
						'NumberTitle', 'off');

					this.fitnessAverage = animatedline;
					this.fitnessAverage.Color = [0, 0.5, 1];
					this.fitnessAverage.LineStyle = '-';
					this.fitnessAverage.LineWidth = 1.0;
					this.fitnessAverage.DisplayName = 'Adaptacion Media';

					this.fitness = animatedline;
					this.fitness.Color = [0.1216, 0.6275, 0.2745];
					this.fitness.LineStyle = '-';
					this.fitness.LineWidth = 1.0;
					this.fitness.DisplayName = 'Adaptacion Maxima';

					title('Curvas de Adaptacion Maxima y Media');
					axis([0, config.generations, 0, inf]);
					legend('show');

					xlabel('Generacion', ...
						'FontSize', 12, ...
						'FontWeight', 'bold', ...
						'Color', [0, 0, 0]);

					ylabel('Adaptacion', ...
						'FontSize', 12, ...
						'FontWeight', 'bold', ...
						'Color', [0, 0, 0]);
				end
			end

			% Agregar un punto a la curva de 'fitness':
			function this = addFitness(this, fitness)

				if this.config.graphFitness == true

					% Agregar una generación:
					figure(this.fitnessHandler);
					maxFitness = max(fitness(:, 1));

					% +20% de margen superior:
					axis([0, this.config.generations, 0, maxFitness * 1.2]);

					this.fitness.addpoints(this.x, maxFitness);
					this.fitnessAverage.addpoints(this.x, mean(fitness(:, 1)));
					this.x = this.x + 1;

					% Actualizar con o sin limitación:
					if this.config.graphRateLimit == true, drawnow limitrate;
					else drawnow; end
				end
			end
		end
	end
