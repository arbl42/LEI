x=[1 2 3 4 5 6 7 8];
f=[-0.05 -0.09 0.1 0.2 0.15 0.1 0.6 1.1]

%c0=1; %para o modelo 1
%c0=[1 1]; %para o modelo 2
c0=[1 1 1]; %para os restantes modelos


[c,S]=lsqcurvefit('MQ_npMP',c0,x,f)
xaux=1:0.1:9;

plot(x,f,'or')
hold on;

yaux=MQ_npMP(c,xaux);
plot(xaux,yaux,'g');

