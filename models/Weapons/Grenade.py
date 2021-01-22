from models.Weapon import *

class Grenade(Weapon):
    def init(self):
        self.name = 'GRENADE'
        self.cadence = 1
        self.damages = 1
        self.bullets = 1
        self.charger = 1
        self.maxBullets = 1
