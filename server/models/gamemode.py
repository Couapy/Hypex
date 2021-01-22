"""This file contains the game module."""

import time
from threading import Thread, RLock
from models.player import Player


class GameMode(Thread):
    """Game Object."""

    """This object manage players, weapons and moves"""

    def __init__(self, server):
        """Game initialisation."""
        Thread.__init__(self)
        # Server Info
        self.server = server
        self.tickrates = 64
        self.timetick = 1 / self.tickrates
        self.instant_tickrates = 0

        # Game Info
        self.players = []
        self.nb_player = 0
        self.score = (0, 0)
        self.status = "warmup"  # Set to game for no warmup
        self.round_time = time.time()
        self.allow_shoot = True
        self.allow_move = True

        # Rules
        self.max_players = None
        self.max_rounds = None
        self.instant_respawn = None  # Bool
        self.time_warmup = None  # In seconds
        self.time_per_round = None  # In seconds
        self.time_pre_round = None  # In seconds

        # Lock
        self.lock = RLock()

    def run(self):
        """Thread function."""
        tickrates = 0
        tickbegin = time.time()
        while self.server.running:
            time_entry = time.time()

            # Switch phases
            self.phaseHandler()
            # reload weapons
            for player in self.players:
                player.reloadHandler()
            # Move players
            for player in self.players:
                player.move()
            # Shoot players
            for player in self.players:
                player.shoot()
            # Update clients
            for player in self.players:
                player.update()

            tickrates += 1
            if time.time() >= tickbegin + 1:
                if tickrates <= 0.8 * self.tickrates:
                    message = "[WARNING] The server is overloaded, {} < {}"
                    print(message.format(tickrates, self.tickrates))
                self.instant_tickrates = tickrates
                tickrates = 0
                tickbegin = time.time()

            if (time.time() - time_entry < self.timetick):
                while self.timetick > (time.time()-time_entry):
                    pass

    def addPlayer(self, name, connection, infos):
        """Add a player to the match."""
        self.nb_player += 1

        player = Player(self, connection, infos, name)
        player.start()
        with self.lock:
            self.players.append(player)
        if self.instant_respawn:
            player.spawn()

        print("[INFO] New player : {}".format(name))

    def removePlayer(self, player):
        """Remove a player from the list."""
        with self.lock:
            try:
                self.players.remove(player)
                self.nb_player -= 1
            except ValueError:
                pass

    def phaseHandler(self):
        """This method handle the phase of the game."""

        """ There is 4 phases in a game. """
        """ * warmup """
        """ * pre-round -> buying time """
        """ * round -> playing time """
        """ * end -> end of the game """

        if self.status == 'warmup':
            if self.time_warmup + self.round_time <= time.time():
                self.status == 'preround'
                self.round_time = time.time()
                for player in self.players:
                    player.spawn()
                self.allow_move = False
                self.allow_shoot = False
        elif self.status == 'preround':
            if self.time_pre_round + self.round_time <= time.time():
                self.status == 'round'
                self.round_time = time.time()
                self.allow_move = True
                self.allow_shoot = True
        elif self.status == 'round':
            if self.time_per_round + self.round_time <= time.time():
                # ++ round
                if self.max_rounds in self.score:
                    self.status = 'end'
                else:
                    self.status == 'preround'
                    self.round_time = time.time()
                    for player in self.players:
                        player.spawn()
                self.allow_move = False
                self.allow_shoot = False
        else:  # End of the game
            pass
