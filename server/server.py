"""This file is the game server launcher."""

from models.server import Server
from models.command import Command

# Initialisation
server = Server()
command = Command(server)

# Start the threads
server.start()
command.start()

# Wait the threads are stoped
server.join()
command.join()
