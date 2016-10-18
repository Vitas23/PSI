
input = [0 0; 0 1; 1 0; 1 1];
numIn = 4;
desired_out = [0;1;1;1];
bias = -1;
coeff = 0.7;
rand('state',sum(100*clock));
weights = -1*2.*rand(3,1);



iterations = 10;
for i = 1:iterations
     out = zeros(4,1);
     for j = 1:numIn
          y = bias*weights(1,1)+...
               input(j,1)*weights(2,1)+input(j,2)*weights(3,1);
          out(j) = 1/(1+exp(-y));
          delta = desired_out(j)-out(j);
          weights(1,1) = weights(1,1)+coeff*bias*delta;
          weights(2,1) = weights(2,1)+coeff*input(j,1)*delta;
          weights(3,1) = weights(3,1)+coeff*input(j,2)*delta;
     end
end

disp('After 10 iterations (red)');
disp('weights:');
disp(weights);
disp('outputs:');
disp(out);
iterations = 100;
for i = 1:iterations
     out100 = zeros(4,1);
     for j = 1:numIn
          y = bias*weights(1,1)+...
               input(j,1)*weights(2,1)+input(j,2)*weights(3,1);
          out100(j) = 1/(1+exp(-y));
          delta = desired_out(j)-out100(j);
          weights(1,1) = weights(1,1)+coeff*bias*delta;
          weights(2,1) = weights(2,1)+coeff*input(j,1)*delta;
          weights(3,1) = weights(3,1)+coeff*input(j,2)*delta;
     end
end

disp('After 100 iterations (green)');
disp('weights:');
disp(weights);
disp('outputs:');
disp(out100);
iterations = 1000;
for i = 1:iterations
     out1000 = zeros(4,1);
     for j = 1:numIn
          y = bias*weights(1,1)+...
               input(j,1)*weights(2,1)+input(j,2)*weights(3,1);
          out1000(j) = 1/(1+exp(-y));
          delta = desired_out(j)-out1000(j);
          weights(1,1) = weights(1,1)+coeff*bias*delta;
          weights(2,1) = weights(2,1)+coeff*input(j,1)*delta;
          weights(3,1) = weights(3,1)+coeff*input(j,2)*delta;
     end
end

x=desired_out;
plot(x,out,'r*',x,out100,'g*',x,out1000,'b*');
disp('After 1000 iterations (blue)');
disp('weights:');
disp(weights);
disp('outputs:');
disp(out1000);
