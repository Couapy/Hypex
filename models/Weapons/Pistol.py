from models.Weapon import *

class Pistol(Weapon):
    def init(self):
        self.name = 'PISTOL'
        self.cadence = 2
        self.reload_time = 2
        self.damages = 5
        self.bullets = 12
        self.charger_size = 12
        self.charger = 12
        self.maxBullets = 48
