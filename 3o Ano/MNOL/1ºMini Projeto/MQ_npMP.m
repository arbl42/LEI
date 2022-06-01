function [m] = MQ_npMP(c,x)
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here

%m=c(1)*exp(x); %-- modelo 1 (S=0.0970)
%m=c(1)*sin(x)+c(2)*exp(x); %-- modelo 2 (S=0.0754)
m=c(1)*sin(x)+c(2)*sin(2*x)+c(3)*exp(x); %-- modelo 3 (S=0.0417)
%m=c(1)*x.^2.*cos(x.^2/2)+c(2)*sin(2*x)+c(3)*exp(x); %-- modelo 4 (S=0.0535)
%m=c(1)*cos(x.^2/2)+c(2).*x.*sin(2*x)+c(3)*exp(x); %-- modelo 5 (S=0.0500)
%m=c(1)*sin(2*x)+c(2)*sin(4*x)+c(3)*exp(x); %-- modelo 6 (S=0.0638)
end

