x=[1 2 3 4 5 6 7 8];
f=[-0.05 -0.09 0.1 0.2 0.15 0.1 0.6 1.1] %dados da amostra

%Comandos
plot(x,f,'or') %desenha os pontos
xaux=1:0.1:9;
hold on;

%Polinomio de 1ºgrau
[p1,s1]=polyfit(x,f,1);
(s1.normr)^2 %avaliaçao do modelo
yaux=polyval(p1,xaux);
plot(xaux,yaux,'b')

%Polinomio de 2ºgrau
[p2,s2]=polyfit(x,f,2);
(s2.normr)^2 %avaliaçao do modelo
hold on;
yaux=polyval(p2,xaux);
plot(xaux,yaux,'g')

%Polinomio de 3ºgrau
[p3,s3]=polyfit(x,f,3);
(s3.normr)^2 %avaliaçao do modelo
hold on;
yaux=polyval(p3,xaux);
plot(xaux,yaux,'r')

%Polinomio de 4ºgrau
[p4,s4]=polyfit(x,f,4);
(s4.normr)^2 %avaliaçao do modelo
hold on;
yaux=polyval(p4,xaux);
plot(xaux,yaux,'y')

%Polinomio de 5ºgrau
[p5,s5]=polyfit(x,f,5);
(s5.normr)^2 %avaliaçao do modelo
hold on;
yaux=polyval(p5,xaux);
plot(xaux,yaux,'k')

%Polinomio de 6ºgrau
[p6,s6]=polyfit(x,f,6);
(s6.normr)^2 %avaliaçao do modelo
hold on;
yaux=polyval(p6,xaux);
plot(xaux,yaux,'m')