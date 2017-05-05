
	%{
		>>> OutputGrapher Class:

			Grafica ScatterPlots de tres dimensiones			
	%}

	classdef OutputGrapher < handle

		properties (Access = protected)
			x1;
			x2;
			z;			
		end

		methods
			% Constructor (O método estático??)
			function this = OutputGrapher(instances,targets)
				this.x1=instances(:,1);
				this.x2=instances(:,2);
				this.z=targets;
			end			

			function graphSimple()
				scatter3(x1,x2,z);
			end

			function graphCompare(outputs)
				scatter3(x1,x2,z);
				hold on;
				scatter3(x1,x2,outputs);	
			end
		end
	end

