function [J grad] = nnCostFunction(nn_params, ...
                                   input_layer_size, ...
                                   hidden_layer_size, ...
                                   num_labels, ...
                                   X, y, lambda)
%NNCOSTFUNCTION Implements the neural network cost function for a two layer
%neural network which performs classification
%   [J grad] = NNCOSTFUNCTON(nn_params, hidden_layer_size, num_labels, ...
%   X, y, lambda) computes the cost and gradient of the neural network. The
%   parameters for the neural network are "unrolled" into the vector
%   nn_params and need to be converted back into the weight matrices. 
% 
%   The returned parameter grad should be a "unrolled" vector of the
%   partial derivatives of the neural network.
%

% Reshape nn_params back into the parameters Theta1 and Theta2, the weight matrices
% for our 2 layer neural network
Theta1 = reshape(nn_params(1:hidden_layer_size * (input_layer_size + 1)), ...
                 hidden_layer_size, (input_layer_size + 1));

Theta2 = reshape(nn_params((1 + (hidden_layer_size * (input_layer_size + 1))):end), ...
                 num_labels, (hidden_layer_size + 1));

% Setup some useful variables
m = size(X, 1);
n=size(X,2);
         
% You need to return the following variables correctly 
J = 0;
Theta1_grad = zeros(size(Theta1));
Theta2_grad = zeros(size(Theta2));

% ====================== YOUR CODE HERE ======================
% Instructions: You should complete the code by working through the
%               following parts.
%
% Part 1: Feedforward the neural network and return the cost in the
%         variable J. After implementing Part 1, you can verify that your
%         cost function computation is correct by verifying the cost
%         computed in ex4.m
%
% Part 2: Implement the backpropagation algorithm to compute the gradients
%         Theta1_grad and Theta2_grad. You should return the partial derivatives of
%         the cost function with respect to Theta1 and Theta2 in Theta1_grad and
%         Theta2_grad, respectively. After implementing Part 2, you can check
%         that your implementation is correct by running checkNNGradients
%
%         Note: The vector y passed into the function is a vector of labels
%               containing values from 1..K. You need to map this vector into a 
%               binary vector of 1's and 0's to be used with the neural network
%               cost function.
%
%         Hint: We recommend implementing backpropagation using a for-loop
%               over the training examples if you are implementing it for the 
%               first time.
%
% Part 3: Implement regularization with the cost function and gradients.
%
%         Hint: You can implement this around the code for
%               backpropagation. That is, you can compute the gradients for
%               the regularization separately and then add them to Theta1_grad
%               and Theta2_grad from Part 2.
%
y_mat=[];   %y_mat: m*num_labels. each row is the example y value of (1..num_labels) represented in a 'binary' form
for i=1:m
    y_vec=zeros(1,num_labels);
    y_vec(y(i))=1;
    y_mat=[y_mat;y_vec];
end

X=[ones(m,1) X];    %add a column of ones to X

%use forward propagation to compute the output layer a3, aka H(x).  size: m*num_labels
z2=X*transpose(Theta1);
a2=sigmoid(z2);

m2=size(a2,1);
a2=[ones(m2,1), a2]; %add a column of ones to a2
                      %  %a2: m*26
z3=a2*transpose(Theta2);
a3=sigmoid(z3);  %a3: m*num_labels  a3=h(X)


for i=1:m
    for k=1:num_labels
        Part1=log(a3(i,k))*y_mat(i,k)*(-1);
        Part2=log((1-a3(i,k)))*(1-y_mat(i,k));
        J=J+Part1-Part2;
    end
end

J=J/m;

% ------------Add the regularization term-------------------------------
part1=0;
for j=1:size(Theta1,1)
    for k=2:size(Theta1,2)
        part1=part1+Theta1(j,k)*Theta1(j,k);
    end
end

part2=0;
for j=1:size(Theta2,1)
    for k=2:size(Theta2,2)
        part2=part2+Theta2(j,k)*Theta2(j,k);
    end
end
J=J+(part1+part2)*lambda/m/2;


% ------------Part II Back Prop-------------------------------
DT2=zeros(size(Theta2));
DT1=zeros(size(Theta1));
for i=1:m
    %step 1: forward prop
    %a3: m*num_labels. a3(i,:) is the y values of example i
    %y_mat: m*num_labels.
    delta_3=a3(i,:)-y_mat(i,:);
    delta_3=delta_3'; % tranposed for vectorized calculation. now it is 10*1
    z2i=z2(i,:);  
    z2i=z2i'; 
    z2i=[1;z2i]; %26*1 vector
    delta_2=transpose(Theta2)*delta_3.*sigmoidGradient(z2i);
    temp1=delta_3*a2(i,:);
    DT2=DT2+delta_3*a2(i,:); %DT2: 10*26
    delta_2=delta_2(2:end);  %delta_2 is now 25*1
    a1=X(i,:);  %a1: 1*(n+1)
    DT1=DT1+delta_2*a1; %DT1: 25*401
end
Theta1_grad=DT1/m;
Theta2_grad=DT2/m;



% ==============Part III Regularization ===================================



Theta1_grad(:, 2:end) = Theta1_grad(:, 2:end) + lambda / m * Theta1(:, 2:end);
Theta2_grad(:, 2:end) = Theta2_grad(:, 2:end) + lambda / m * Theta2(:, 2:end);

% Unroll gradients
grad = [Theta1_grad(:) ; Theta2_grad(:)];

end







