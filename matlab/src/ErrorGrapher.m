classdef ErrorGrapher < handle
		
	properties
		errors = [];
	end


	methods
		function output = addError(this, outputs,targets)
			this.errors = [this.errors; targets-outputs];
		end

		function output = graph(this, epochs)
			x = 1:epochs;
			y = 0.5*sum(this.errors(x,:).^2,2);
			scatter(x,y)
			output = y;
		end
	end
end


