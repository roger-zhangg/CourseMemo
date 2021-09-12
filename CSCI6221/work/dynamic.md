```bash
## the global var
dynamicVar=0
## funcSmall just output the var in
funcSmall () { echo "in funcSmall: $dynamicVar"; }
## funcBig declears a local version the same name of global var
## and calls funcSmall
funcBig () { local dynamicVar=1; funcSmall; }
## calls to funcBig, will call funcSamll, which should be affected by dynamic scoping and output 1
funcBig
## calls funcSmall directly, should read the global var and output 0
funcSmall
```

This demonstration of dynamic scoping is achieved using bash

====================plain text blow===========================

dynamicVar=0

funcSmall () { echo "in funcSmall: $dynamicVar"; }

funcBig () { local dynamicVar=1; funcSmall; }

funcBig

funcSmall

