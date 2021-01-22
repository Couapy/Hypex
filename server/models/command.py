"""Command module."""

from threading import Thread
import os


class Command(Thread):
    """Command Object."""

    def __init__(self, server):
        """Initialise Command instance."""
        Thread.__init__(self)
        self.server = server
        self.game = server.game
        self.running = True

    def run(self):
        """Command handler."""
        while self.server.running and self.running:
            cmd = input()
            if cmd == "stop":
                self._stop()
            elif cmd == "help":
                self._help()
            elif cmd == "players":
                self._players(cmd)
            elif cmd[:4] == 'kick':
                self._kick(cmd)
            elif cmd[:4] == 'heal':
                self._heal(cmd)
            elif cmd == "tickrates":
                self._tickrates()
            elif cmd == "clear":
                self._clear()
            else:
                print("Unknow command. If you want help, type \"help\"")

    def _stop(self):
        self.running = False
        self.server.stop()

    def _help(self):

        help = "    * stop              Stop the server\n"
        help += "   * players           Show all players\n"
        help += "   * kick {name}       Kick a player\n"
        help += "   * heal {name}       Heal a player\n"
        help += "   * tickrates         Display the tickrates\n"
        help += "   * clear             Clear the console\n"
        print(help)

    def _players(self, cmd):
        if len(self.game.players) != 0:
            print("Player list :")
            for player in self.game.players:
                print(player.name)
        else:
            print("No player.")

    def _kick(self, cmd):
        pseudo = cmd[5:]
        for player in self.game.players:
            if pseudo == player.name:
                player.disconnect()
                return True
        print("\"{}\" not found".format(pseudo))

    def _heal(self, cmd):
        pseudo = cmd[5:]
        for player in self.game.players:
            if pseudo == player.name:
                player.life = player.maxlife

    def _tickrates(self):
        print("Configurated on : ", self.server.game.tickrates)
        print("Instant : ", self.server.game.timetick)
        print("Tickrates : ", self.server.game.instant_tickrates)

    def _clear(self):
        if os.name == "posix":
            os.system("clear")
        else:
            os.system("cls")
