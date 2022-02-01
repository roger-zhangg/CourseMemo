

## L3

●If you have multiple samples from the same patient, make sure no patient data is split across training-holdout

●Check for features that are highly correlated and drop one of them

●Perform dimensionality reduction via a Principal Component Analysis (PCA)



## L4

bias reduced, variance is increased as model complexity grows.

●**Random Forests**: use random samples drawn with replacement from the training dataset, and use a random subset of features to split at each node

●**Bagging**: use random subsets of the original training dataset to train multiple models, then average their results

●**Boosting**: train a series of weak models; at each step, reweight the training samples to give more weight to the incorrect predictions from the previous step. 