from models.Player import *

class Tank(Player):
    def init(self):
        self.type = 'TANK'
        self.life = 150
        self.maxlife = 150
        self.speed = 8
