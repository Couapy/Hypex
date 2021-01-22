"""This is the firs view for the game client."""

import pygame
from models.view import View
from models.views.elements.button import Button


class HomeView(View):
    """Home view."""

    def __init__(self, window):
        """Initialise this view."""
        self.window = window
        self.surface = window.window
        self.keys = {}
        self.pos = (0, 0)

        # View elements
        self.background = pygame.Rect(0, 0, 0, 0)
        self.background_color = (12, 34, 56)

        # Buttons
        self.buttons = [
            Button(
                "Jouer",
                lambda: self.window.selectView("connection")
            ),
            Button(
                "Créer une carte",
                lambda: self.window.selectView("create.map")
            ),
            Button(
                "Quitter",
                self.window.stop
            )
        ]

        height = (self.window.height - (len(self.buttons) * 50 - 10)) / 2
        i = 0
        for button in self.buttons:
            button.pos = ((self.window.width-height)/2, height + i * 50)
            button.dim = (200, 40)
            button.update()
            i += 1

    def select(self):
        """Init view when selected."""
        self.background = pygame.Rect(
            0,
            0,
            self.window.width,
            self.window.height
        )

    def eventHandler(self, events):
        """Listen to events."""
        for event in events:
            if event.type == pygame.QUIT:
                self.window.stop()
            elif event.type == pygame.MOUSEMOTION:
                self.mouseMotionHandler(event)
            elif event.type == pygame.VIDEORESIZE:
                self.window.resize(event.size)
                self.select()
            elif event.type == pygame.MOUSEBUTTONDOWN:
                self.mouseEventHandler(event)

    def mouseMotionHandler(self, event):
        """Manage the mouse motion."""
        self.pos = event.pos
        for button in self.buttons:
            if button.hover(event.pos):
                pygame.mouse.set_cursor(*pygame.cursors.diamond)
                return True
        pygame.mouse.set_cursor(*pygame.cursors.arrow)

    def mouseEventHandler(self, event):
        """Manage the mouse event."""
        for button in self.buttons:
            button.onClick(event)

    def displayHandler(self):
        """Render the view."""
        pygame.draw.rect(self.surface, self.background_color, self.background)

        for button in self.buttons:
            button.draw(self.surface)

    def launch(self):
        """Launch a game."""
        self.window.selectView("connection")
