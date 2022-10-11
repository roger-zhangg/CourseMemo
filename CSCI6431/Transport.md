## Multi/Demultiplexing

- Multiplex
- Demultiplex though socket
- Use header to keep track
- 

### RDT

- send NAK for failed checksum

- sender retransmit NAKed packets

- stop and wait until ack

- RDT 2.0 corrupt packet

- RDT 2.1 deal with corrupt ACK/NAK

- RDT2.2 remove NAK
  - Use ack from previous message to show nak for current message

- RDT3.0 deal with loss(stop and wait)

  - retransmit if ask is lost(timeout)

  - receiver must reply with seq

- RDT3.0(pipelined protocols)
  - increase utilization
- Go-Back-N sender
  - ACK(N) ack all packets before N(cumulative ACK)

- Selective repeat
  - Ack for each packet
  - buffer all out of order packets

### Sequence number

Initial sequence number

cumulative ack, previous ack lost doesn't effect later transmit

### Flow control

Receiver tell sender to slow down for full queues

RcvBuffer is shown to sender

### TCP 3-way handshake

- Sender

  - Listen

  - synsent

  - estab

- Server

  - Listen
  - SYN rcvd
  - ESTAB

- Start transmiting when all ESTAB

### Closing 4 way handshake

FIN

ACKFIN

ACK