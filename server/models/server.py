"""This file contains the server module."""

from models.gamemodes import deathmatch
from threading import Thread
import socket


class Server(Thread):
    """Server object."""

    """This object receive connections from clients and accept them."""
    """Then each connection is converted into Player object to be add"""
    """to the game"""

    def __init__(self):
        """Server initialisation."""
        # Super init
        Thread.__init__(self)

        # Game
        # TODO: Select gamemode
        self.game = deathmatch.DeathMatch(self)
        self.running = True

        # Server Socket
        self.host = ''
        self.port = 12864
        self.timeout = 5
        self.lenrcv = 2048
        try:
            self.con = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            self.con.bind((self.host, self.port))
            self.con.listen(self.game.max_players)
            self.con.settimeout(self.timeout)
        except Exception as e:
            print(e)
            self.running = False

    def run(self):
        """Thread function."""
        if self.running:
            print("[INFO] Server started")
            self.game.start()
            while self.running:
                try:
                    connection, infos = self.con.accept()
                    if self.accept(connection, infos):
                        connection.send(b"NAME:")
                        name = connection.recv(self.lenrcv).decode()
                        self.game.addPlayer(name, connection, infos)
                except socket.error:
                    pass
                except ConnectionAbortedError:
                    pass
            self.game.join()
        else:
            print("[INFO] Abort launch")
        self.stop()

    def stop(self):
        """Engage the stop procedure."""
        if self.running:
            self.running = False
            for player in self.game.players:
                player.disconnect()
            self.con.setblocking(False)
            self.con.close()
            print("[INFO] Connection thread stopped")

    def accept(self, connection, infos):
        """Return if the connection has been accepted."""
        if self.game.max_players == self.game.nb_player:
            message = "[INFO] Connection refused from {}:{} > maxplayer is reached"
            print(message.format(infos[0], infos[1]))
            connection.close()
            return False
        else:
            message = "[INFO] Connection accepted from {}:{}"
            connection.send(b"HELLO:")
            print(message.format(infos[0], infos[1]))
            return True
