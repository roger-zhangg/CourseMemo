- Quantizer
  - n-level -> split into n intervals
- Uniform
  - ![image-20221025210847262](MidtermReview.assets/image-20221025210847262.png)
- Non-uniform
  - q: binary search
  - deq: use r_i
- Semi uniform
  - <img src="MidtermReview.assets/image-20221025211036478.png" alt="image-20221025211036478" style="zoom:80%;" />

- Max LLOYD
  - NON-UNIFORM
  - Initial the value as uniform q
  - loop
    - ri= average in [di,di+1)
    - di = (ri-1+ri)/2
  - until di doesn't change much
  - 

