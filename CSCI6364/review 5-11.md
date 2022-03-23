# L6

- decision tree good at axis aligned boundary
- regression good at tilted (oblique) decision boundary
- train linear model-> find best slope and interception for y=wx+b
- more feature-> more wx
- only used for binary classification
- add higher-order polynomial (x^2) feature to draw curve
- loss function -> MSE
  - how close is the guess? 
  - Average loss across all samples
  - $$MSE = \frac{1}{n}\sum(y-\hat{y})^2$$
    - y-y -> error on y axis
- 0-1 loss 
  - step function
    - not convex
    - not smooth -> bad for optimize(small change lead to large loss)
  - log loss
    - easy to optimize(gradient descent)
- penetration and regularization
  - reduce features needed
  - penetrate large weight
  - advantage:
    - less likely to overfit
    - reduce the impact of highly correlated feature
- 

# L7 gradient descend

- margin of each prediction
  - see how confident the model is about the result
  - larger margin means more away from 0, gives more confidence to result
  - loss is defined on margin(z)
  - find the local minimum of loss using gradient descend(d/dx) -> like use acceleration to velocity
- avoid overshoot by smaller step size
- 



# L8 Neural Nets

- Activation function
  - Relu(faster, allow dead node)
  - Sigmoid
- parameters of NN are updated after each iteration(running through entire batch)
- Need to scale, normalize feature
- Training time depends on margin between classes
- noise affect the training process
- can self learn what feature is important, what's not
- Back propagation -> find how much each weight of each node in previous layer contributed to a mistake in prediction
- weight -> input -> activation -> cost
- Training
  - million/billion weights
- more powerful(more feature, more layer)
  - likely to overfit
  - local optima
- momentum
  - use velocity accumulated from past gradients to choose future step size(faster for local optima)
- optimizing
  - batch train, update after each batch
  - scale all features
  - regularization for high weight
- *vanishing gradient problem*
  - gradient in lower layer are too small
- 



# L10 Deep learning CNN

- cnn -> mimic human vision
- feature map
  - 
- Feature learning stage
  - convolution+relu
  - pooling
- Classification stage
  - Flatten
  - fully connected
  - softmax(all add up to 1) to result
- Dataset augmentation
  - flip, rotate, color jitter, transform image
  - make more data
- Transfer learning
  - low level of image classification is same
  - start with pretrained weight to gain train speed.

# SVM, Kernel, KNN

- svm
  - find maximum margin in hyperplane
  - find the supporting vector using Lagrangian function
    - the splitting hyperplane will change if supporting vectors are removed.
  - splitting is easy for linearly separable data
    - else, try kernel
- Kernel intuition
  - use functions to transfer non-linearly data in 2D to linear-3D data
  - choose friendly conversion function
    - this step is done manually
- KNN
  - scale(regulate) features is important
  - 

# Markov model & Reinforcement learning

- run on time series

- future only depend on past through present(only depend on current state)

- Maximize reward over time

- find optimal action for each state

- POMDP -> state not directly observed

- reinforcement learning

  - •Exploration

    	Taking actions to learn about problem structure

    •Exploitation

    	Taking action to maximize reward

    •Balancing exploration vs. exploitation

    	Finite time horizon (or discounted infinite)

    	Actions taken for exploitation still provide information

# Recommendation

content filtering

collaboration filtering

