"""This file contains the button object."""

import pygame


class Button:
    """This is the button object for pygame interfaces."""

    def __init__(self, text, callback):
        """Initialise the button."""
        self.text = text
        self.callback = callback

        self.dim = (0, 0)
        self.pos = (0, 0)
        self.color = (200, 200, 200)
        self.font_color = (50, 50, 50)

        self.button = pygame.Surface(self.dim)
        self.font = pygame.font.SysFont("Consolas", 24)

    def update(self):
        """Generate the button."""
        width, height = self.dim

        # Write the text
        text = self.font.render(self.text, True, self.font_color)
        x1, y1, x2, y2 = text.get_rect()

        # Generate the frame
        if self.dim == (0, 0):
            self.dim = (x2, y2)
        self.button = pygame.Surface(self.dim)
        self.button.fill(self.color)

        # Write the text on the surface
        self.button.blit(
            text,
            ((width-x2)/2, (height-y2)/2)
        )

    def hover(self, pos):
        """Return if the user is hovering the button."""
        x, y = pos
        px, py = self.pos
        width, height = self.dim
        if px < x and x < px + width and py < y and y < py + height:
            return True
        else:
            return False

    def onClick(self, event):
        """Call the callback function if the user click on this button."""
        if self.hover(event.pos):
            self.callback()

    def draw(self, surface):
        """Draw the button on a surface."""
        surface.blit(self.button, self.pos)
