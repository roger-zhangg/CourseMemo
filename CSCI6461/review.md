# tips

- 1 page of cheat sheet

- diagram is provided

- 2hour

- Tomasulo algorithm for exam

- How does the Tomasulo algorithm accomplish register renaming
  - Use reservation stations to hold instructions and operands for instruction to execute 
  - decouple register from operands
  - create dependency chain between reservation stations.

- Explain how the Tomasulo hardware makes maximum use of execution unit hardware, that is, keeps the functional units busy.
  - maintains a set of instructions that have been issued and marks the reservation as having all need operands. Function units execute any of the instructions that are ready.

- How does a reorder buffer preserve exception behavior?
  - reorder buffer instructions can be executed out of order, but they are committed in order if an instruction causes an exception, the exception is raised when the instruction is committed.

- register renaming

- What type of cache coherency is used in massively parallel architectures and why?

  - Directory based, these scales better as all memory accesses does not compete on the same bus.

- b) In a snooping protocol, why are write misses always put on the bus?

  - let other processor determinate if they have a copy and can invalidate their copy.

- Explain why vector processors are much more efficient than compiler optimizations of loops and dynamic scheduling in scalar architectures.

  - A single vector instruction (SIMD) operates an multiple data elements, in pipelined method.
  - One instruction fetch acts as a loop

- Briefly explain how vector units can accomplish forwarding? 

  - Vector unites can be chained so that results from previous vector instructions are provoked directly to the subsequent instructions in parallel with filling in contents of result vectors

-  How does a branch target buffer eliminate invalid stage executions in pipeline?

  - It provides, for a branch instruction, the next predicted pc value at the end of the if cycle of the branch instruction. 
  - branch prediction buffer
  - branch target buffer

- Why does a 4 state (2 bit) branch predictor do much better than a 2 state (1) branch predictor?

  - Need two consecutive prediction
  - tobe more correct than single step prediction.
  - It takes two consecutive mis predicts to change the prediction.

- What penalties do we pay for incorrect predictions with a reorder buffer?

  - We need to flush the instruction loaded(loaded and executed) below the incorrect prediction happens.
  - flush computational unites
  - restart from the correct branch target

- Loop unroll -> standard five stage mips architecture

  - loop:  ld r6, 0(r1)

    ld r2, 0(r8)

    add r6, r6, r2

    st r6, 0(r1)

    daddui r1,r1, #-8

    bne r5,r1, loop

  - firstly expand the loop before daddui

  - reorder the loop

  - calculate the cycle in five stage mips

- a. What basic concept or concepts throughout the memory hierarchy dictate where copies of memory should be kept?

  - temporal and spatial locality with respect to the current instuctions that are being executed

- b. What happens to cache performance when the number of blocks in a direct mapped cache are reduced?

  - conflict miss increases

- c. For b above, how does this affect other optimizations in instruction scheduling and execution units?

  - more cache miss means more stalls to wait for cache load.

- Why are directory protocols dominant in machines with large numbers of processors?

  - scale better
    memory traffic is distributed across direct transfer
    directory protocols work with distributed memory and an interconnect they can provide multiple connections and transfers to directorys.

- 