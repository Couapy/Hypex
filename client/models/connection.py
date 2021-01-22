"""This is the connection module for the game client."""

from threading import Thread
import socket
import time
import random
import string
from models.game import Game


class Connection(Thread):
    """This is my connection object."""

    def __init__(self, window):
        """Connect to the server."""
        Thread.__init__(self)
        self.window = window
        self.running = False
        self.success = False
        self.failed = False

        # Connection constants
        self.host = "localhost"
        self.port = 12864
        self.timeout = 30
        self.lenrcv = 2048
        self.attemps = 5

        # Prepare connection
        self.con = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

        self.incomplete_message = ''

        # Game data
        self.game = Game()

    def connect(self):
        """Connect the app to a server."""
        attemp = 0
        while attemp < self.attemps and self.success is False and self.running:
            attemp += 1
            self.success = False
            self.failed = False
            try:
                self.con = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                self.con.connect((self.host, self.port))
                self.con.settimeout(self.timeout)
                self.con.setblocking(False)
            except Exception:
                print(f"[ERROR] Connection failed to {self.host}:{self.port}")
                self.failed = True
            else:
                if not self.running:
                    self.con.close()
                    return False
                else:
                    print(f"[INFO] Connected to {self.host}:{self.port}")
                    self.success = True
                    return True
            time.sleep(1)

        self.running = False
        return False

    def run(self):
        """Thread function."""
        print("[INFO] Connection started")
        self.running = True
        self.connect()
        while self.window.running and self.running:
            try:
                message = self.con.recv(self.lenrcv).decode()
                if message != "":
                    self.messageHandler(message)
            except BlockingIOError:
                pass
            except ConnectionResetError:
                print("[ERROR] Connection closed by host")
                self.running = False
            except ConnectionAbortedError:
                print("[ERROR] Connection closed by host")
                self.running = False
        try:
            self.con.send(b'bye')
        except Exception:
            pass
        print("[INFO] Connection closed")

    def stop(self):
        """Stop the thread."""
        self.con.close()
        self.running = False

    def messageHandler(self, received):
        """Manage message : class split and deal with messages."""
        lenrcv = self.lenrcv
        if len(received) == lenrcv and received[lenrcv-1:lenrcv] != ':':
            pass
        else:
            messages = received.split(':')
            for message in messages:
                if message == "HELLO":
                    pass
                elif message == "NAME":
                    self.con.send(self.randomName().encode())
                    # TEMP: Remove to replace with the user's pseudo
                else:
                    print(message)

    def randomName(self, stringLength=6):  # TEMP: This is temporary
        """Generate a random string of fixed length."""
        name = ''.join(
            random.choices(
                string.ascii_lowercase,
                k=stringLength
            )
        )
        return name.capitalize()
