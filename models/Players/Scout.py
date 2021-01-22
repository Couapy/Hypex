from models.Player import *

class Scout(Player):
    def init(self):
        self.type = 'SCOUT'
        self.life = 50
        self.maxlife = 50
        self.speed = 24
