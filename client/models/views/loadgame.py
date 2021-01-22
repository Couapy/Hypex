"""This is the view to load data from the server server."""

import pygame
from models.view import View


class LoadGameView(View):
    """View for game loading."""

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
        self.text = font.render("Chargement en cours..", True, (255, 255, 255))

    def select(self):
        """Init view when selected."""
        pass

    def eventHandler(self, events):
        """Listen to events."""
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
