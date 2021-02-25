# Network protocol

This is the definition of the network protocol.

## Nomenclature

First of all, each package of data send to the server, or to the client, must follow this nomenclature :

```text
header:body\n
```

So as you can see, each package of data represent a line in a stream.

## Table of contents

- [Network protocol](#network-protocol)
  - [Nomenclature](#nomenclature)
  - [Table of contents](#table-of-contents)
    - [Fire](#fire)
    - [Name](#name)
    - [Map](#map)
    - [Move](#move)
    - [Team](#team)
    - [Configuration](#configuration)
    - [Weapon](#weapon)
  - [Example](#example)

### Fire

The `FIRE` instruction indicates if the player is firing.
There are two arguments, booleans, the first is for primary firing, and the
second for special firing.

```text
FIRE:off;off
FIRE:on;off
FIRE:on;on
```

### Name

The `NAME` instruction indicates to the server the name of the player.
Name must contains only letters, dash, underscore and numbers.

For the server-side, no arguments are required to ask to the client the player name.

```md
# Server
NAME:

# Client
NAME:albator
NAME:Couapy
NAME:My_Name-23
```

### Map

The `NAME` instruction allow to send a map to the client.

Please refer to [MAPS.md](../../core/docs/MAPS.md) for map data.

To respect the current nomenclature, line returns must replaced by comma `+`.

```text
MAP:<map data>
```

### Move

The `MOVE` instruction indicates what moves the player is doing.
The directions are separated by `;`

```md
# move forward
MOVE:f

# move in all directions (forward, backward, left, right)
# order isn't important
MOVE:f;b;l;r

# if the player isn't moving
MOVE:
```

### Team

`TEAM` instruction is usefull for selecting a team from its index.

```text
TEAM:0
TEAM:1

```

### Configuration

`CONFIG` instruction is to ask informations about each other.

Informations returned :

<!-- TODO: Define what to share -->
* version
* refreshrate
* game informations

```md
# Server
<!-- Ask -->
CONFIG:
<!-- Answer -->
CONFIG:0.1.O,...

# Client
<!-- Ask -->
CONFIG:
<!-- Answer -->
CONFIG:0.1.O,...
```

### Weapon

`WEAPON` instruction is usefull for selecting a weapon from its index in the player hand.

```text
WEAPON:0
WEAPON:1
WEAPON:2
WEAPON:3
```

## Example

`C>` represent the client side communication, and `S>` for the server side.

```text
C> NAME:albator
C> MOVE:f
C> MOVE:f;b;l;r
C> MOVE:
C> FIRE:on
C> FIRE:off


[...]

C> BYE
```
