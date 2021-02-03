# Communication Protocol

All instructions are in uppercase. Instructions and arguments are separated by `:`.
Arguments are separated by `;`.

## Instuctions

The following instructions are available :

* [FIRE](#fire)
* [NAME](#name)
* [MOVE](#move)
* [SELECT](#select)
* [TEAM](#team)

### Fire

The `FIRE` instruction indicates if the player is firing.
There are two arguments, booleans, the first is for primary firing, and the
second for special firing.

```text
FIRE:off;off
FIRE:on;off
FIRE:on;on
```

### Hello

The `HELLO` instruction is only to engage the dialog between both parts.

```text
HELLO
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

### Select

`SELECT` instruction is usefull for selecting a weapon from its index in the player hand.

```text
SELECT:0
SELECT:1
SELECT:2
SELECT:3
```

### Team

`TEAM` instruction is usefull for selecting a team from its index.

```text
TEAM:0
TEAM:1

```

### Version

`CONFIGURATION` instruction is to ask informations about each other.

Informations returned :

<!-- TODO: Define what to share -->
* version
* refreshrate
* game informations

```md
# Server
<!-- Ask -->
CONFIGURATION:
<!-- Answer -->
CONFIGURATION:0.1.O,...

# Client
<!-- Ask -->
CONFIGURATION:
<!-- Answer -->
CONFIGURATION:0.1.O,...
```

## Example

`C>` represent the client side communication, and `S>` for the server side.

```text
C> HELLO
S> NAME:
C> NAME:albator
S> 
C> MOVE:f
C> MOVE:f;b;l;r
C> MOVE:
C> FIRE:on
C> FIRE:off


[...]

C> 
S> 
C> BYE
```
