# Distributed Ring-Based Leader Election using Chang-Roberts Protocol

---

## Overview

This project implements a **distributed leader election protocol** using **Java RMI** (Remote Method Invocation) based on the **Chang-Roberts algorithm**. The system simulates a ring network of nodes where each node can participate in an election to determine the leader among them.

---

## Objective

To implement a leader election protocol that fulfills the following requirements:

---

### Exercise 1: Communication and Electing a Leader

- **Ring Topology**: Nodes must be connected in a unidirectional ring structure.
- **Restricted Communication**: Nodes must not directly invoke methods on arbitrary nodes. All communication must be limited to adjacent nodes, maintaining ring integrity.
- **Group-Dependent Design**: The implementation approach is open-ended, allowing teams to choose their design as long as the protocol requirements are met.
- **Leader Announcement**: Once the leader is elected, the leader must inform all other participants in the ring.

---

## Features

- Dynamic node registration into the ring network.
- Automatic ring formation by the server during client registration.
- Implementation of the **Chang-Roberts election algorithm**, where:
  - The node with the highest ID is elected.
  - Election messages are passed around the ring.
  - The leader announces itself to all participants once elected.
- Fully remote communication using Java RMI interfaces.

---

## Components

- `ClientCallbackInterface.java`: Defines remote methods for message passing between clients.
- `RingNodeClient.java`: Implements the client node capable of joining the ring and participating in leader election.
- `RingNodeImpl.java`: Implements the server logic to manage the ring and register clients.
- `RingNodeInterface.java`: Interface for the server’s remote methods.
- `RingNodeServer.java`: Starts the RMI registry and binds the ring management service.

---

## How It Works

1. The server starts and awaits clients.
2. Each client, upon startup, registers itself and is placed in the ring.
3. The server maintains the ring order and assigns `nextNode` references.
4. Any client can initiate the leader election by typing `start`.
5. Election messages circulate around the ring.
6. The node with the highest ID declares itself as leader.
7. The leader sends an announcement around the ring to inform all other nodes.

---

## How to Run

1. Build the program.
2. Right click on RingNodeServer to run RingNodeServer.
3. Right click on RingNodeClient to run the RingNodeClient. Enter a unique a ID for the client. Repeat this step to create multiple clients.
4. On one client type "start". This will begin the election using Chang-Roberts protocol.

---

##Requirements

Java 8 or later
Apache Netbeans
