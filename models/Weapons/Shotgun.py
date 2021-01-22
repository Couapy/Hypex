from models.Weapon import *

class Shotgun(Weapon):
    def init(self):
        self.name = 'SHOTGUN'
        self.cadence = 1
        self.damages = 1
        self.bullets = 1
        self.charger = 1
        self.maxBullets = 1
