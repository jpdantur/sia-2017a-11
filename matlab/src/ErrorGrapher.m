classdef ErrorGrapher < handle
		
	properties (Access = protected)
		errors = {}
	end


	methods
		function output = addError(outputs,targets)
			errors = [errors targets-outputs];
		end

		function output = graph(epochs)
			x = 1:epochs;
			y = 0.5*sum(errors{x}.^2);
			plot(x,y);
		end
	end
end


