# liner regression

y = wx+B

- w -> slope
- b -> intercept
- x -> feature
- y -> target
- w,b -> weights
- optimization -> finding the best weight



Use MSE(mean squared error) as loss of the model

$$ MSE = \frac{1}{n} \sum (y-\hat{y})^2 $$



- minimize the loss
- $$ \min\ L(w,b) = \min\ \sum{N}{n=1}(yn(W^{T}X~{n}+b) <0)$$

- only focus on getting the right answer -> not variance



## logistic regression

Approximating 0-1 loss

- -log(h(x)) - > a good approx
- minimize the loss as well as variance?
- allowing for optimiaztion
- sigmoid curve(log)

# sigmoid function

soft-max -> add all feature up to 0-1

$$ S(x) = \frac{1}{1+e^{-x}} $$



regularization:

- choose penaty function for the model
- give reduction to high weighted features
- ElasticNet: combine L1,L2