from models.Player import *

class Soldier(Player):
    def init(self):
        self.type = 'SOLDIER'
        self.life = 100
        self.maxlife = 100
        self.speed = 16
