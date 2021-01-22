"""This is the game view for the client."""

import pygame
from models.view import View


class GameView(View):
    """This is the game view."""

    def __init__(self, window):
        """Initialise the view."""
        self.window = window
        self.surface = window.window
        self.con = None
        self.data = None

        # Vue elements
        self.font20 = pygame.font.SysFont("Consolas", 20)
        self.bullets_image = pygame.image.load("client/images/bullets.png")
        self.bullets_image.convert_alpha()
        self.background_color = (0, 37, 59)
        self.background_color = (0, 0, 0)
        self.map = pygame.Surface((2000, 2000))
        self.map.fill((255, 255, 255))

        # HUD
        self.hud_pos = (0, 0)
        self.hud = self.generateHUD()

    def generateHUD(self):
        """Generate HUD."""
        self.hud_pos = (0, self.window.height-25)

        # HUD
        hud = pygame.Surface((self.window.width, 25))
        hud.fill((0, 0, 0))
        hud.set_alpha(160)

        # life & armor
        slifemax = pygame.Surface((100, 20))
        vertical = pygame.Surface((8, 20))
        horizontal = pygame.Surface((20, 8))

        slifemax.fill((128, 128, 128))
        vertical.fill((255, 255, 255))
        horizontal.fill((255, 255, 255))

        hud.blit(slifemax, (5, 3))
        hud.blit(horizontal, (110, 9))
        hud.blit(vertical, (116, 3))

        # weapons & ammo

        return hud

    def renderHUD(self, hud):
        """Render HUD with data."""
        life = pygame.Surface((100*(self.data.life/self.data.maxlife), 20))
        life.fill((255, 255, 255))
        hud.blit(life, (5, 3))

        # Weapons
        weapon_bullets = self.font20.render(
            self.data.weapon,
            True,
            (255, 255, 255)
        )
        if self.data.weapon_bullets == 0:
            text = 'RELOADING   ' + self.data.weapon
            pygame.mouse.set_cursor(*pygame.cursors.diamond)
        else:
            text = f"{self.data.weapon_bullets}/{self.data.weapon_charger}"
            pygame.mouse.set_cursor(*pygame.cursors.broken_x)
        weapon = self.font20.render(text, 1, (255, 255, 255))

        # Get sizes
        weapon_bullets_rect = weapon_bullets.get_size()
        bullets_image_rect = self.bullets_image.get_size()
        weapon_rect = weapon.get_size()

        # Get positions
        weapon_bullets_pos = (
            self.window.width-weapon_bullets.get_size()[0]-5,
            (25-weapon_bullets.get_size()[1])/2 + 2
        )
        weapon_pos = (
            self.window.width-weapon_bullets_rect[0]-bullets_image_rect[0]
            - weapon_rect[0]-15,
            (25-weapon_rect[1])/2 + 2
        )
        bullets_image_pos = (
            self.window.width-weapon_bullets_rect[0]-bullets_image_rect[0]-10,
            (25-bullets_image_rect[1])/2
        )

        # Display elements on hud
        hud.blit(weapon_bullets, weapon_bullets_pos)
        hud.blit(weapon, weapon_pos)
        hud.blit(self.bullets_image, bullets_image_pos)

        return hud

    def select(self):
        """Select the view."""
        self.hud = self.generateHUD()

    def eventHandler(self, events):
        """Manage the pygame events."""
        for event in events:
            if event.type == pygame.QUIT:
                self.window.stop()
            elif event.type == pygame.VIDEORESIZE:
                self.window.resize(event.size)
                self.select()
            # elif event.type == pygame.MOUSEMOTION:
            #     self.mouseMotionHandler(event)
            # elif event.type == pygame.MOUSEBUTTONDOWN:
            #     self.mouseHandler(event)
            # elif event.type == pygame.KEYDOWN:
            #     self.keys[event.key] = True
            # elif event.type == pygame.KEYUP:
            #     self.keys[event.key] = False

    def displayHandler(self):
        """Manage the display surface."""
        if self.data is not None:
            # Draw background
            self.surface.fill(self.background_color)
            # Draw Map
            self.surface.blit(self.map, (0, 0))
            # Draw hud
            # self.surface.blit(self.hud, self.hud_pos)
            self.surface.blit(self.renderHUD(self.hud.copy()), self.hud_pos)
            # Draw player counter + score

            # Draw scoreboard

            # Draw FPS counter
