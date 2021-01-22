import time

class Weapon:
    def __init__(self):
        self.name = ''
        self.cadence = 0 #shot/s
        self.reload_time = 0 #reload time in second
        self.damages = 0 #damages / shot
        self.charger_size = 0 #in 1 charger
        self.charger = 0 #in THE charger
        self.bullets = 0 #bullets none in charger
        self.maxBullets = 0 #max bullets

        self.reload_begin = 0

        self.throughWalls = False
        self.throughPlayers = False
        self.init()

    def init():
        pass

    def getDamages(self):
        return self.damages

    def shot(self, i = 1):
        self.charger -= i
        if self.charger == 0:
            self.reload_begin = time.time()
        return self.charger != 0

    def reload(self):
        if self.reload_begin + self.reload_time <= time.time():
            self.reload_begin = 0
            if self.charger_size < self.bullets:
                self.charger = self.charger_size
                self.bullets -= self.charger_size
            else:
                self.charger, self.bullets = self.bullets, self.charger
            return True
        else:
            return False

    def getBullets(self):
        return self.charger
