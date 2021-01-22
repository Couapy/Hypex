from models.Weapon import *

class Uzi(Weapon):
    def init(self):
        self.name = 'UZI'
        self.cadence = 16
        self.reload_time = 3
        self.damages = 5
        self.bullets = 30
        self.charger_size = 30
        self.charger = 30
        self.maxBullets = 120
