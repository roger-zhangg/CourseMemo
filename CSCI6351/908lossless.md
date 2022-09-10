## Huffman coding

- Build the tree
  - ![image-20220908112502825](908lossless.assets/image-20220908112502825.png)

- how to cal entropy
  - ![image-20220908101542277](908lossless.assets/image-20220908101542277.png)
- how to cal stream bitrate
  - char_prob * char_bit_lenght
- If all char_prob are power of 2, H=BR, or BR>H
- Huffman Table
  - header
  - pre-define
- Prefix property
  - no word is prefix of another

## Block Huffman

pack bits to higher length and build tree

## Run-Length encoding(RLE)

Need a protocol aaaabbb->(a,4),(b,3)

- 1: Fix length
  - Redundancy
- 2: Split Run length
- Huffman the symbols
- 

## Golomb -> implement of RLE

