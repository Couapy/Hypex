"""This is the Player object."""

from threading import Thread
from math import cos, sin
from models.weapons.pistol import Pistol


class Player(Thread):
    """Object Player."""

    def __init__(self, game, connection, infos, name):
        """Player initialisation."""
        super().__init__()

        # Connection
        self.con = connection
        self.infos = infos
        self.connected = True
        self.game = game

        # Infos
        self.name = name
        self.alive = False
        self.life = 100
        self.maxlife = 100
        self.posx = 0
        self.posy = 0
        self.lookangle = 0  # RADIANS
        self.velocity = 16

        # Weapons
        self.weapons = [Pistol()]
        self.weapon_selected = 0
        self.reloading = False

        # Actions recorded
        self.forward = False
        self.backward = False
        self.left = False
        self.right = False
        self.fire = False

    def spawn(self):
        """Spawn the player on the map."""
        pass

    # Connection section
    def update(self):
        """Send data to the client."""
        # player pos
        # ennemies pos
        # weapon information (list, selected)
        # info (life, name, maxlife, lookangle)
        pass

    def run(self):
        """Receive the data from the client."""
        while self.game.server.running and self.connected:
            try:
                received = self.con.recv(self.game.server.lenrcv).decode()
                self.messageHandler(received)
            except ConnectionResetError:
                self.disconnect()
            except ConnectionAbortedError:
                self.disconnect()

    def messageHandler(self, received):
        """Manage the messages received from the client."""
        messages = received.split(":")
        for message in messages:
            if message[:1] == "+" or message[:1] == "-":
                value = message[:1] == "+"
                direction = message[1:]
                if direction == "forward":
                    self.forward = value
                elif direction == "backward":
                    self.backward = value
                elif direction == "left":
                    self.left = value
                elif direction == "right":
                    self.right = value
            elif message == "bye":
                self.disconnect()
            else:
                pass

    def disconnect(self):
        """Disconnect the client."""
        if self.connected:
            self.con.close()
            self.connected = False
            self.game.removePlayer(self)
            message = "[INFO] Connection reset from {}:{} > Player {}"
            print(message.format(self.infos[0], self.infos[1], self.name))

    # Actions section
    def shoot(self):
        """Shoot the weapon."""
        if self.game.allow_shoot:
            self.weapons[self.weapon_selected].shoot()

    def reload(self):
        """Reload the weapon."""
        self.reloading = True
    
    def reloadHandler(self):
        """Reload handler for the weapon."""
        if self.reloading:
            self.weapons[self.weapon_selected].reload()

    def move(self):
        """Move the player."""
        if self.game.allow_move:
            velocity = self.velocity / self.game.tickrates

            velocity_x = velocity * cos(self.lookangle)
            velocity_y = velocity * sin(self.lookangle)

            if (self.forward):
                self.posx -= velocity_x
            if (self.backward):
                self.posx += velocity_x
            if (self.left):
                self.posx -= velocity_y
            if (self.right):
                self.posx += velocity_y

    def selectWeapon(self):
        """Select a weapon."""
        pass
