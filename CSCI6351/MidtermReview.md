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
- Transform
  - ![image-20221026180818444](MidtermReview.assets/image-20221026180818444.png)
  - decorrelated data: less blurring, less loss of patterns.

- ![image-20221026181226606](MidtermReview.assets/image-20221026181226606.png)
- ![image-20221026182128581](MidtermReview.assets/image-20221026182128581.png)
- ![image-20221026182144569](MidtermReview.assets/image-20221026182144569.png)
- ![image-20221026183738187](MidtermReview.assets/image-20221026183738187.png)
- Lossless
  - DPCM
    - ![image-20221026184625201](MidtermReview.assets/image-20221026184625201.png)
    - ![image-20221026190050026](MidtermReview.assets/image-20221026190050026.png)
    - 

  - Gray codes
    - make adjacent binary only change 1 number
    - ![image-20221026191042962](MidtermReview.assets/image-20221026191042962.png)
    - ![image-20221026191151898](MidtermReview.assets/image-20221026191151898.png)
    - ![image-20221026192057506](MidtermReview.assets/image-20221026192057506.png)


![image-20221026192104305](MidtermReview.assets/image-20221026192104305.png)

![image-20221026200728900](MidtermReview.assets/image-20221026200728900.png)

![image-20221026200747973](MidtermReview.assets/image-20221026200747973.png)

- decode-> stop at ceil(-log delta) = t
- LZ compression
- ![image-20221026201617051](MidtermReview.assets/image-20221026201617051.png)
- ![image-20221026201626288](MidtermReview.assets/image-20221026201626288.png)
- ![image-20221026201636127](MidtermReview.assets/image-20221026201636127.png)

![image-20221026201805755](MidtermReview.assets/image-20221026201805755.png)

![image-20221026201820180](MidtermReview.assets/image-20221026201820180.png)

![image-20221026201831648](MidtermReview.assets/image-20221026201831648.png)

![image-20221026202019458](MidtermReview.assets/image-20221026202019458.png)

![image-20221026202034988](MidtermReview.assets/image-20221026202034988.png)

![image-20221026202326436](MidtermReview.assets/image-20221026202326436.png)

![image-20221026202431989](MidtermReview.assets/image-20221026202431989.png)

![image-20221026202648073](MidtermReview.assets/image-20221026202648073.png)

![image-20221026202749408](MidtermReview.assets/image-20221026202749408.png)

![image-20221026203057487](MidtermReview.assets/image-20221026203057487.png)

![image-20221026202826571](MidtermReview.assets/image-20221026202826571.png)

![image-20221026203147656](MidtermReview.assets/image-20221026203147656.png)

![image-20221026203227786](MidtermReview.assets/image-20221026203227786.png)
