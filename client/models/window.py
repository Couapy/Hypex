"""This is the window module for game client."""

# Import pygame
try:
    import pygame
except Exception:
    print("Pygame is required, please install by `pip install pygame`")
    import sys
    sys.exit()
# Import views
import models.views.createmap
import models.views.connection
import models.views.home
import models.views.game
import models.views.loadgame


class Window:
    """Window object."""

    def __init__(self):
        """Initialise the window."""
        # Constants
        self.running = True
        self.width = 720
        self.height = 480

        # Pygame initialisation
        pygame.init()
        self.window = pygame.display.set_mode(
            (self.width, self.height),
            pygame.RESIZABLE
        )
        pygame.display.set_caption("Hypex2")
        icon = pygame.image.load("client/images/icon.png").convert_alpha()
        pygame.display.set_icon(icon)

        # Keybinding
        self.keys = {
            'forward': pygame.K_z,
            'backward': pygame.K_s,
            'left': pygame.K_q,
            'right': pygame.K_d,
            'cancel': pygame.K_ESCAPE
        }

        # Set the views
        self.view = None
        self.views = {
            "home": models.views.home.HomeView(self),
            "create.map": models.views.createmap.CreateMapView(self),
            "connection": models.views.connection.ConnectionView(self),
            "loadgame": models.views.loadgame.LoadGameView(self),
            "game": models.views.game.GameView(self),
        }
        self.selectView("connection")

    def run(self):
        """Events listener."""
        while self.running:
            self.view.eventHandler(pygame.event.get())
            # Update the window
            self.view.displayHandler()
            try:
                pygame.display.flip()
            except Exception as e:
                print(e)
        print("[INFO] Window closed")

    def stop(self):
        """Stop this thread."""
        self.running = False

    def resize(self, dim):
        """Resize the window."""
        if dim != (self.width, self.height):
            print("[INFO] New dimensions : ", dim)
        self.width, self.height = dim
        return pygame.display.set_mode(dim, pygame.RESIZABLE)

    def selectView(self, view):
        """Switch to an another view."""
        if view in self.views.keys():
            pygame.mouse.set_cursor(*pygame.cursors.arrow)
            self.views[view].select()
            self.view = self.views[view]
