## Exercise 1

- n! tours

## Exercise 2

$-\infin$

## Exercise3

$y=\begin{cases}1\ \ \ \ x<0\\-1\ x>0\end{cases}$

## Exercise4

$y=0$

- It is continuous, not differentiable.

## Exercise5

x=0, x=1

## Exercise 6

- O(MN)
- best M=6, N=4
  - result = 2.5003

## Exercise 7

![image-20221128160017973](work.assets/image-20221128160017973.png)

## Exercise 8

M*N evaluations

## Exercise 9

$M*N^2$ Evaluations, $M*N^n$ evaluations.

## Exercise 10

Bracketing search: x1=4.691358024691359 x2=3.2098765432098757 numFuncEvals=138

## Exercise 11:

N=0 bestx=5.555555555555558 bestf=3.2149641975308683 prevBestf=3.2149641975308683

## Exercise 12:

c is a weighted average of a and b. So c must be in range [a,b].

## Exercise 13:

d-a=rb-ra = r(b-a)

$\frac{d-a}{b-a} = r$

## Exercise 14:

fd could be 0.

## Exercise 15:

c=0.0 c=10.0 bestf=2.5841 numEvals=0

## Exercise 16:

- around 100 iters

- step become smaller

- ```
  X:2.484 ,afx:-1.484
  X:3.3744 ,afx:-0.8904000000000001
  X:3.90864 ,afx:-0.5342399999999999
  X:4.229184 ,afx:-0.32054399999999994
  X:4.4215104 ,afx:-0.19232639999999998
  X:4.5369062399999995 ,afx:-0.11539584000000006
  X:4.606143744 ,afx:-0.06923750400000017
  X:4.6476862464 ,afx:-0.041542502400000104
  X:4.67261174784 ,afx:-0.02492550143999992
  X:4.687567048704 ,afx:-0.01495530086399981
  X:4.6965402292224 ,afx:-0.008973180518399815
  X:4.70192413753344 ,afx:-0.005383908311040031
  X:4.705154482520064 ,afx:-0.0032303449866240897
  ```

- The function doesn't converge at $\alpha$=1, it overshoots over the optimal

- The program can't normally function at $\alpha$=10

## Exercise 17:

![image-20221128163631540](work.assets/image-20221128163631540.png)

- Yes, it converge at 2.6
- It is also 0.

## Exercise 18

Yes, simply f(x)=sin(x)

## Exercise 19:

If s is too large, then the derivative is not very precises and can cause over shoot problem or even miss the global minimum.  

## Exercise 20:

This won't work because it will force x1 and x2 both to move even if the x1 or x2 is already at it's optimal value.



## Exercise 21:

$f_1^{'}=2*(x_1-4.71)+4(x_1-4.71)*(x_2-3.2)^2$

$f_2^{'}=2*(x_2-3.2)+4(x_1-4.71)^2*(x_2-3.2)$

## Exercise 22:

$f_1^{'} = \frac{1}{\mu_1}$

$f_2^{'} = \frac{1}{\mu_2}$

## Exercise 23:

We will be calculating the gradient based on the new x1, which is not the correct gradient at the moment.

## Exercise 24:

```
Gradient descent: after n=542 iterations: x1=4.704951324783355 x2=3.1949999982061064
```

## Exercise 25:

X around 0.3 provides best performance

## Exercise 26:

It can't find a low derivative, 1000 samples are used.

## Exercise 27:

$a_n=\frac{1}{n}$

## Exercise 28:

Yes, it works. 

## Exercise 29:

Gradient descent: x*=0.3070053072136658 f(x*)=1.2150337526719595

Yest, it's about the value produced in exercise 28.
