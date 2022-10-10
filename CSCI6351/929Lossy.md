## Quantizer

- Control the loss
- Scalar quantizer
  - Mostly used
  - reconstruction is middle(adjustable)
  - interval can be chosen equal or not equal
  - uniform quantizer
    - define by $d_0,d_n,n$
    - $i=\lfloor\frac{x-d_0}{\Delta}\rfloor$
  - Non-uniform quantizer
  - semi-uniform quantizer
  - Max-LLoyd quantizers
    - di init evenly
    - ri average in each interval
    - cal $d_i = \frac{r_{i+1}-r_i}{2}$
    - 
- Vector quantizer
  - block by block

## Transform

## Entropy Coder

