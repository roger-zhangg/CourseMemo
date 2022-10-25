### **Exercise 1**

Flip 0: 4913
Flip 1: 5087

### Exercise 2

Flip 0: 3936
Flip 1: 6064

### Exercise 3

Pr[Exactly 2 h in 3 flips]=0.432851  theory=0.432

### Exercise 4

- What is the probability that, in 3 flips, the third and only the third flip results in heads?
  - Pr[only 3rd is heads]=0.095991  theory=0.096
- What is the probability that, with an unlimited number of flips, you need at least three flips to see heads for the first time?
  - Pr[need at least 3 flips]=0.159825  theory=0.16
- Why are these two probabilities different?
  - The second condition doesn't require the third flip to be head.

### Exercise 5

Pr[odd+even]=0.249831  theory=0.25

### Exercise 6:

- Pr[c1=club given c2=club]=0.23604529756646053  theory=0.23529411764705882
- Pr[c1=diamond given c2=club]=0.2500602361256124  theory=0.2549019607843137

### Exercise 7:

- heads
- tails
- {heads,tails}
- {none}

### Exercise 8:

2^n possible events

### Exercise 9:

- 6 probabilities
- 36 probabilities for the sample space is 36

### Exercise 10:

- Axiom 1: Pr[Ω]=1
- Axiom 2: 0≤Pr[A]≤1 for any event A.
- So Pr[A′]=1−Pr[A]

### Exercsie 11:

- Using a couple of numbers, like (1,50), (2,40), The size should be  52*51 = 2652
- The subset shoud be the ({1...13},{1...13}) where the two number are not the same.
- The cardinality is 13*12 = 156
- prob = 156/2652 = 0.058
- The subset should be ({1...13},{1...52}), where two numbers are not the same.
  -  the cardinality = 13*51 = 663
  - The prob = 663/2652 = 0.25

### Exercise 12:

The sample space is unlimited, for the coin flip may never result in head.

### Exercise 13:

$P[at\ least\ one\ head]$ = 1- $P[all\ tails]$ = $\frac{7}{8}$

$P[all heads] = \frac{1}{8}$

$P[allhead\ and\ at\ least\ one\ head]=\frac{1}{8}$

$P[allheads|at\ least\ one\ head]  = \frac{1}{7}$

### Exerceise 14:

Total sample space = ({1...52},{1...52} : (i,j) : i!=j}. = 52*51 samples

event second is club = ({1...52},{1...13}) where first and second is not the same. total 51*13 samples.

$P[first\ is\ club]=\frac{51 * 13}{51* 52}$

event $first\ is\ club\ and\ second\ is\ club$ is ({1...13},{1...13}) where first and second is not the same. total 13*12 samples

$P[first\ is\ club\ and\ second\ is\ club]= \frac{13*12}{51*52}$

$P[first\ is\ club\ and\ second\ is\ club|first\ is\ club] = \frac{12}{51}$

### Exercise 15:

C1 = [1...13], cardinalities  =13. 

C2 = [1...13], cardinalities  =13.

### Exercise 16:

Pr[D1] = $\frac{13}{52}$

Pr[D1|C2] = Pr[C2|D1]Pr[D1]/Pr[C2] = (13/51)*(13/52)/(13/52) = $\frac{13}{51}$

### Exercise 17:

Pr[A|B] = 3/51

Pr[B] = 4/52

Pr[B'] = 48/52

Pr[A|B'] = 4/51

Pr[A] = $\frac{3*4}{51*52}+\frac{4*48}{51*52} = \frac{4}{52}$ 

### Exercise 18:

P[S] = 0.05

P[S'] = 0.95

P[T|S] = 0.99

P[T|S'] = 0.03

P[T] = $0.99*0.05 + 0.95*0.03$ = 0.078

P[S|T]= P[T|S]*P[S]/P[T] = $0.99 *0.05/0.078$ = 0.63

P[S'|T]= P[T|S']*P[S']/P[T] = $0.03 *0.95/0.078$ = 0.365

```
False p:0.36590856466592914
True p:0.6340914353340709
```



 ### Exercise 19:

- Pr[A > 1.0] = 0.367855
  Pr[A > 0.5] = 0.606511

- Pr[0.5<A<1]
- No they are not, dot in continous space doesn't make sense
- Yes

### Exercise 20:

```
Pr[A > 1.0] = 0.500319
Pr[A > 0.5] = 0.750251
```

### Exercise 21:

Uniform

```
Pr[A > 1.0] = 0.500319
Pr[A > 0.5] = 0.750251
Pr[A > 1|A > 0.5] = 0.6668688212344934
```

Exp

```
Pr[A > 1.0] = 0.367855
Pr[A > 0.5] = 0.606511
Pr[A > 1|A > 0.5] = 0.6065100220770934
```

For Exponential, the Pr[A>1|A>0.5] is very close to Pr[A>0.5]

### Exercise 22:

Exp

```
Avg[A > 1.0] = 1.9980611014979077
Avg[A > 0.5] = 1.4989925090470337
```

Uniform

```
Avg[A > 1.0] = 1.4993524154833202
Avg[A > 0.5] = 1.249670823273795
```

### Exercise 23:

The code below will always count one more bus than it actually should be.

For example, if the first but is later than my arrival time, the numBuses given by the following code will be 1 instead of 0.

### Exercise 24:

See `BusStopExample2.java`

prob: 0.9701

### Exercise 25:

- Exponential
  - avg wait at 10 min: 1.0145516006133788
    avg wait at 20 min: 1.0144012348073337
- Uniform
  - avg wait at 10 min: 0.6677589130029824
    avg wait at  20 min: 0.6577483290960779
- So the average wait time doen't depend on the time of arrival
- Both Uniform and Exponential arrival doesn't change for waiting 20 minute or 10 minute.
- The average wait time for exponential interval is the same at 10 minute and 20 minute. Which is not very intuitive.

### Exercise 26:

`Pr[c2=1]=0.500163  Pr[c2=1|c1=1]=0.7494849748531853`

### Exercise 27:

- My intuition tell me these events are not independent. For 0.5 is a small period. So If a bus arrives in period 0,0.5. Then the bus will not be very likely to arrive at 0.5,1.
- See `BusStopExample4.java`
- exp
  - Pr[B2]: 0.3075
    Pr[B2|B1] 0.0947
- uniform
  - Pr[B2]: 0.2897
    Pr[B2|B1] 0.0569
- We can see that in both exp and uniform interval, the B2 given B1 is both smaller than only B2.

### Exercise 28:

Defect by A = P[A] * P[AD] = 1.2%

Defect by B = P[B] * P[BD] = 2.1%

Defect by C = P[C] * P[CD] = 4%

Defected prob = P[D] = 1.2+2.1+4 = 7.3%

So probability by A given defected = P[A|D] = 1.2/7.3 = 16.4%





