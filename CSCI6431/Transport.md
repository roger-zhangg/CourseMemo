## Multi/Demultiplexing

- Multiplex
- Demultiplex though socket
- Use header to keep track
- 

### RDT

send NAK for failed checksum

sender retransmit NAKed packets

stop and wait until ack

RDT 2.0 corrupt packet

RDT 2.1 deal with corrupt ACK/NAK

RDT2.2 remove NAK

Use ack from previous message to show nak for current message

RDT3.0 deal with loss