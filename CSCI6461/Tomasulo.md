- float point add/multiply
- 

Implement all instructions except for 

- CHK
- Floating Point/Vector operations
- Trap
- program1



backend

- instruction
  - jump
  - add/multiply
  - rotation
  - IN/OUT?
    - return in another single(waiting for input)
    - press enter from virtual keyboard will remove this signal
    - 
  - 

- cache/memory

frontend

- show cache

compiler/assembler

- support method up to rotation

program 1:

- Program 1: A program that reads 20 numbers (integers) from the keyboard, prints the numbers to the console printer, requests a number from the user, and searches the 20 numbers read in for the number closest to the number entered by the user. Print the number entered by the user and the number closest to that number. Your numbers should not be 1…10, but distributed over the range of 0 … 65,535. Therefore, as you read a character in, you need to check it is a digit, convert it to a number, and assemble the integer.
- 10
- 2023
- 2424
- error

check integer in assembling language?

- use IN command to input
- for reg0 in 1-21:
  - for input not [enter]:
    - wait for keyboard input into reg1    (IN command)
    - convert single char to int(ascii 48--57 -> 0-9) (add command)
    - multiply target reg(reg2) by 10(mul command)
    - add current int from temp register(reg1) into target register(reg2)
  - save register(reg2) into memory after user hit enter(ascii 13)
- for reg0 in 1-20:
  - reg2 memory compare
  - save current difference into reg1
  - reg2 - currentmemory [reg0] -> reg3（abs)
  - if reg3< reg1
    - reg1 = reg3
    - currentmemory [reg0] -> memory[min_adrr]
- memory[min_adrr]
  - for out
  - out
  - out

Program 2: A program that reads a set of a paragraph of 6 sentences from a file into memory. It prints the sentences on the console printer. It then asks the user for a word. It searches the paragraph to see if it contains the word. If so, it prints out the word, the sentence number, and the word number in the sentence.

