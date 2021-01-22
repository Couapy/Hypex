"""This is the view for create maps."""

import pygame
import time
from models.view import View
from math import sqrt


class CreateMapView(View):
    """View for create map."""

    def __init__(self, window):
        """Initialise this view."""
        self.window = window
        self.surface = window.window
        self.keys = {}
        self.pos = (0, 0)

        # View elements
        self.line_color = (255, 255, 255)
        self.background = pygame.Rect(0, 0, 0, 0)
        self.background_color = (12, 34, 56)

        # Map elements
        self.map_dimension = (1920, 1080)

        self.scale = 1
        self.scale_step = 0.1
        self.map_pos = (20, 20)
        self.map_pos_step = 20

        self.map_elements = []
        self.last_plot = (-1, -1)

        self.map = self.drawMap()

    def select(self):
        """Init view when selected."""
        self.background = pygame.Rect(
            0,
            0,
            self.window.width,
            self.window.height
        )
        self.help()

    def drawMap(self):
        """Draw the map surface."""
        map = pygame.Surface(self.map_dimension)
        map.fill((59, 0, 37))

        for line in self.map_elements:
            pygame.draw.line(
                map,
                self.line_color,
                line[0],
                line[1],
                2
            )

        return map

    def eventHandler(self, events):
        """Listen to events."""
        for event in events:
            if event.type == pygame.QUIT:
                self.window.stop()
            elif event.type == pygame.VIDEORESIZE:
                self.window.resize(event.size)
                self.select()
            elif event.type == pygame.MOUSEMOTION:
                self.mouseMotionHandler(event)
            elif event.type == pygame.MOUSEBUTTONDOWN:
                self.mouseHandler(event)
            elif event.type == pygame.KEYDOWN:
                self.keys[event.key] = True
            elif event.type == pygame.KEYUP:
                self.keys[event.key] = False

    def convertPos(self, event):
        """Convert the event pos to map pos."""
        pos = list(event.pos)
        map_pos = list(self.map_pos)
        pos[0] -= map_pos[0]
        pos[1] -= map_pos[1]

        pos[0] = int(pos[0] / self.scale)
        pos[1] = int(pos[1] / self.scale)

        return tuple(pos)

    def mouseMotionHandler(self, event):
        """Manage the mouse motion."""
        pos = list(self.convertPos(event))
        shift_key = pygame.K_LSHIFT
        shift = shift_key in self.keys.keys() and self.keys[shift_key]
        if shift and self.last_plot != (-1, -1):
            last_plot = list(self.last_plot)
            tmp = [
                abs(pos[0] - last_plot[0]),
                abs(pos[1] - last_plot[1])
            ]
            if tmp[0] > tmp[1]:
                pos = (pos[0], last_plot[1])
            else:
                pos = (last_plot[0], pos[1])

        ctrl_key = pygame.K_LCTRL
        ctrl = ctrl_key in self.keys.keys() and self.keys[ctrl_key]
        if ctrl and self.last_plot != (-1, -1):
            nearest_plot = self.nearestPlot(pos)
            if nearest_plot != (-1, -1):
                pos = nearest_plot
        self.pos = pos

    def clickHandler(self, event):
        """Click handler."""
        # Select the position on the map
        pos = list(event.pos)
        dim = list(self.map_dimension)
        if pos[0] < 0 or pos[1] < 0:
            return False
        elif pos[0] > dim[0] or pos[1] > dim[1]:
            return False

        if self.last_plot != (-1, -1):
            line = [self.last_plot, self.pos]
            self.map_elements.append(line)
            self.map = self.drawMap()
            self.last_plot = self.pos
        else:
            self.last_plot = self.pos
            pygame.draw.circle(self.map, self.line_color, self.last_plot, 3)

    def zoomPlus(self):
        """Zoom the map."""
        self.scale += self.scale_step
        if self.scale >= 20:
            self.scale = 20

    def zoomMinus(self):
        """Un-Zoom the map."""
        self.scale -= self.scale_step
        if self.scale <= 0:
            self.scale = self.scale_step

    def mouseHandler(self, event):
        """Manage the key down events."""
        # 1 left click
        # 2 middle click
        # 3 right click
        # 4 wheel down
        # 5 wheel up
        # 6 back
        # 7 forward
        if event.button == 1:
            self.clickHandler(event)
        elif event.button == 4:
            self.zoomPlus()
        elif event.button == 5:
            self.zoomMinus()
        elif event.button == 2:
            self.last_plot = self.nearestPlot(self.pos, 70)

    def nearestPlot(self, pos, limit=50):
        """Return the nearestPlot from a pos."""
        pos = list(pos)
        nearest_plot = (-1, -1)
        nearest_distance = -1
        for line in self.map_elements:
            for plot in line:
                x, y = plot
                distance = sqrt((y-pos[1])**2 + (x-pos[0])**2)
                if nearest_distance == -1 or distance < nearest_distance:
                    nearest_plot = plot
                    nearest_distance = distance
        if nearest_distance >= limit:
            return (-1, -1)
        else:
            return nearest_plot

    def displayHandler(self):
        """Render the view."""
        # Draw background
        pygame.draw.rect(self.surface, self.background_color, self.background)

        # Draw map
        map = self.map.copy()
        pygame.draw.circle(map, (0, 255, 0), self.last_plot, 2)
        if self.last_plot != (-1, -1):
            pygame.draw.line(map, (255, 0, 0), self.last_plot, self.pos, 2)
        map = pygame.transform.rotozoom(map, 0, self.scale)
        self.surface.blit(map, self.map_pos)

        self.keyManager()

        time.sleep(0.01)

    def keyManager(self):
        """Manage the key events."""
        pos = list(self.map_pos)

        keys = self.keys.keys()
        forward = self.window.keys['forward']
        backward = self.window.keys['backward']
        left = self.window.keys['left']
        right = self.window.keys['right']
        cancel = self.window.keys['cancel']

        if forward in keys and self.keys[forward]:
            pos[1] += self.map_pos_step
        if backward in keys and self.keys[backward]:
            pos[1] -= self.map_pos_step
        if left in keys and self.keys[left]:
            pos[0] += self.map_pos_step
        if right in keys and self.keys[right]:
            pos[0] -= self.map_pos_step
        if cancel in keys and self.keys[cancel]:
            if self.last_plot != (-1, -1):
                self.last_plot = (-1, -1)
                self.map = self.drawMap()
                time.sleep(0.5)
            else:
                self.keys = {}
                self.window.selectView("home")
        if pygame.K_DELETE in keys and self.keys[pygame.K_DELETE]:
            self.deleteLastPlot()
        if pygame.K_e in keys and self.keys[pygame.K_e]:
            print("[EXPORT] Map :\n")
            print("self.dimension = ", self.map_dimension)
            print("self.lines = ", self.map_elements)
            pygame.image.save(self.map, 'export.png')
            time.sleep(0.5)

        map_rect = self.map.get_rect()
        map_w, map_h = map_rect.width, map_rect.height

        map_w *= self.scale
        map_h *= self.scale

        if map_w + pos[0] < 20:
            pos[0] = -map_w + 20
        elif pos[0] >= self.window.width - 20:
            pos[0] = self.window.width - 20
        if map_h + pos[1] < 20:
            pos[1] = -map_h + 20
        elif pos[1] >= self.window.height - 20:
            pos[1] = self.window.height - 20

        self.map_pos = tuple(pos)

    def deleteLastPlot(self):
        """Delete the last plot."""
        if self.last_plot != (-1, -1):
            print("> ", self.last_plot)
            lines = self.map_elements.copy()
            for line in lines:
                if self.last_plot in line:
                    self.map_elements.remove(line)
            self.map = self.drawMap()
            self.last_plot = (-1, -1)

    def help(self):
        """Display help."""
        print("""HELP:
        - left click    draw lines
        - middle click  select plot (distance < 70px)
        - left shift    horitonlize or verticalize
        - left ctrl     magnetize the points
        - del           delete selected plot and lines related to
        - e             export in console the map
        - z             move up the map
        - q             move left the map
        - s             move down the map
        - d             move right the map
        - esc           cancel the selection
        """)
