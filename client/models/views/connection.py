"""This is the view to connect to a server."""

import pygame
from models.view import View
from models.connection import Connection


class ConnectionView(View):
    """View for server connection."""

    def __init__(self, window):
        """Initialise this view."""
        self.window = window
        self.connection = None
        self.surface = window.window

        self.background = pygame.Rect(
            0,
            0,
            self.window.width,
            self.window.height
        )

        font = pygame.font.SysFont("comicsansms", 72)
        self.text = font.render("Connexion en cours...", True, (255, 255, 255))

    def select(self):
        """Init view when selected."""
        # Start Connection
        self.connection = Connection(self.window)
        self.connection.start()

    def eventHandler(self, events):
        """Listen to events."""
        if not self.connection.running and self.connection.failed:
            self.window.selectView("home")
        if self.connection.success:
            self.window.views["loadgame"].con = self.connection
            self.window.views["loadgame"].data = self.connection.game
            self.window.selectView("loadgame")

        for event in events:
            if event.type == pygame.QUIT:
                self.window.stop()
            elif event.type == pygame.KEYDOWN:
                if event.key == pygame.K_ESCAPE:
                    self.window.selectView('home')
                    self.connection.running = False

    def displayHandler(self):
        """Render the view."""
        pygame.draw.rect(self.surface, (0, 0, 0), self.background)
        self.surface.blit(self.text, (25, 25))
