import __main__
import random
from models.Weapons.Pistol import *
from models.Weapons.Uzi import *

class Player:
    def __init__(self, name):
        self.name = name
        x, y = -1, -1
        while (x == -1 and y == -1) or (__main__.map[y][x] == 1):
            x = random.randint(0, len(__main__.map[0])-1)
            y = random.randint(0, len(__main__.map)-1)
        self.x, self.y = x + 0.5, y + 0.5
        print('[INFO][' + self.name + '] appear at ({}, {}) '.format(self.x, self.y))

        self.forward = False
        self.backward = False
        self.left = False
        self.right = False
        self.fire = False
        self.alreadyFire = False
        self.fire_pos = False

        self.life = 0
        self.maxlife = 0

        self.weapons = [Pistol(), Uzi()]
        self.selected = 0
        self.init()

        self.udpdateClientWeapons()
        self.updateClientLife()
        self.updateClientMaxlife()

        __main__.players.append(self.name)

    def init(self):
        pass

    #GETTERS

    def getPos(self):
        return (self.x, self.y)

    #GAME POS

    def move(self):
        tickrates, map = __main__.tickrates,  __main__.map
        x, y = self.x, self.y
        distance = self.speed / tickrates
        if self.forward:
              if y - distance >= 1:
                  floatx = x-int(x)
                  floaty = y-int(y)
                  if floatx < 0.5:
                      l = map[int(y)-1][int(x)-1] != 1
                      t = map[int(y)-1][int(x)] != 1
                  elif floatx > 0.5:
                      t = map[int(y)-1][int(x)-1] != 1
                      r = map[int(y)-1][int(x)+1] != 1
                  else:
                      t = map[int(y)-1][int(x)] != 1

                  if (floatx < 0.5 and l and t) or (floatx > 0.5 and t and r) or (floatx == 0.5 and t):
                      y -= distance
                  elif floaty != 0.5:
                      if y - distance <= int(y)-1:
                          y = y - distance
                      else:
                          y = int(y) + 0.5
              else:
                  if y - distance >= 0.5:
                      y = y - distance
                  else:
                      y = 0.5

        if self.backward:
            if y + distance <= len(map) - 1:
                floatx = x-int(x)
                floaty = y-int(y)
                if floatx < 0.5:
                    l = map[int(y)+1][int(x)-1] != 1
                    t = map[int(y)+1][int(x)] != 1
                elif floatx > 0.5:
                    t = map[int(y)+1][int(x)-1] != 1
                    r = map[int(y)+1][int(x)+1] != 1
                else:
                    t = map[int(y)+1][int(x)] != 1

                if (floatx < 0.5 and l and t) or (floatx > 0.5 and t and r) or (floatx == 0.5 and t):
                    y += distance
                elif floaty != 0.5:
                    if y + distance <= int(y)-1:
                        y = y + distance
                    else:
                        y = int(y) + 0.5
            else:
                if y + distance <= len(map) - 0.5:
                    y = y + distance
                else:
                    y = len(map) - 0.5

        if self.left:
              if x - distance >= 1:
                  floatx = x-int(x)
                  floaty = y-int(y)
                  if floaty < 0.5:
                      l = map[int(y)-1][int(x)-1] != 1
                      t = map[int(y)][int(x)-1] != 1
                  elif floaty > 0.5:
                      t = map[int(y)][int(x)-1] != 1
                      r = map[int(y)+1][int(x)-1] != 1
                  else:
                      t = map[int(y)][int(x)-1] != 1

                  if (floaty < 0.5 and l and t) or (floaty > 0.5 and t and r) or (floaty == 0.5 and t):
                      x -= distance
                  elif floatx != 0.5:
                      if x - distance <= int(x)-1:
                          x = x - distance
                      else:
                          x = int(x) + 0.5
              else:
                  if x - distance >= 0.5:
                      x = x - distance
                  else:
                      x = 0.5

        if self.right:
              if x + distance <= len(map[0]) - 1:
                  floatx = x-int(x)
                  floaty = y-int(y)
                  if floaty < 0.5:
                      l = map[int(y)-1][int(x)+1] != 1
                      t = map[int(y)][int(x)+1] != 1
                  elif floaty > 0.5:
                      t = map[int(y)][int(x)+1] != 1
                      r = map[int(y)+1][int(x)+1] != 1
                  else:
                      t = map[int(y)][int(x)+1] != 1

                  if (floaty < 0.5 and l and t) or (floaty > 0.5 and t and r) or (floaty == 0.5 and t):
                      x += distance
                  elif floatx != 0.5:
                      if x + distance <= int(x)+1:
                          x = x + distance
                      else:
                          x = int(x) + 0.5
              else:
                  if x + distance <= len(map[0]) - 0.5:
                      x = x + distance
                  else:
                      x = len(map[0]) - 0.5

        if 0 < x and x < 0.5:
            x = 0.5
        if 0 < y and y < 0.5:
            y = 0.5

        self.x, self.y = x, y

    def canShot(self, pos_fire, pos_player, throughWalls = False):
        map = __main__.map
        pos1, pos2 = pos_fire
        pos3, pos4 = pos_player

        if throughWalls:
            return True
        else:
            a, b = self.x, self.y
            i = 0
            if a == pos1:
                i = 1 if pos2 > b else -1
                while 0 < b and b < len(map):
                    if map[int(a)][int(b)] == 1:
                        return False
                        break
                    elif ((a-pos3)**2 + (b-pos4)**2)**0.5 <= 0.5:
                        return True
                        break
                    b += i
            else:
                c = (b-pos2)/(a-pos1)
                k = b-c*a
                if -1 < c and c <1:
                    o = 0.25 if pos1 > a else -0.25
                else:
                    o = 0.25 if pos2 > b else -0.25

                while (0 < a and a < len(map[0]) and 0 < b and b < len(map)):
                    if map[int(b)][int(a)] == 1:
                        return False
                        break
                    elif ((a-pos3)**2 + (b-pos4)**2)**0.5 <= 0.5:
                        return True
                        break
                    if -1 < c and c <1:
                        a += o
                        b = c * a + k
                    else:
                        b += o
                        a = (b-k)/c

    def fireHandler(self):
        if self.fire and self.weapons[self.selected].charger is not 0:
            dmg = self.weapons[self.selected].damages

            if self.weapons[self.selected].cadence > __main__.tickrates:
                bullets = int(self.weapons[self.selected].cadence / __main__.tickrates)
                dmg = dmg * bullets
                self.shot(dmg, bullets)
            elif (__main__.gamemode.t % int(__main__.tickrates/self.weapons[self.selected].cadence) == 0 and __main__.gamemode.t != 0) or not self.alreadyFire:
                self.shot(dmg)
        elif self.weapons[self.selected].charger == 0 and self.weapons[self.selected].bullets != 0:
            self.reload()

    def shot(self, dmg, i = 1):
        self.weapons[self.selected].shot(i)
        self.alreadyFire = True
        self.udpdateClientWeapons()

        list_player_damages = []
        for player in list(__main__.players):
            if player != self.name and self.canShot(self.fire_pos, __main__.clients[player][1].player.getPos(), self.weapons[self.selected].throughWalls):
                list_player_damages.append(player)

        if list_player_damages != []:
            if len(list_player_damages) == 1 or self.weapons[self.selected].throughPlayers:
                for player in list_player_damages:
                    __main__.clients[player][1].player.damages(dmg)
                    __main__.clients[player][1].player.updateClientLife()
            else:
                player_damaged = ''
                dist = -1
                for player in list_player_damages:
                    distemp = dist > ((self.x-player.x)**2+(self.y-player.y)**2)**0.5
                    if dist == -1 or dist > distemp:
                        player_damaged = player
                        dist = distemp

                __main__.clients[player_damaged][1].player.damages(dmg)
                __main__.clients[player_damaged][1].player.updateClientLife()

    #PLAYER DATA

    def selectWeapon(self, weapon):
        weapons = []
        for wp in self.weapons:
            weapons.append(wp.name)
        if weapon in weapons:
            self.selected = weapons.index(weapon)
            if self.weapons[self.selected].charger == 0:
                self.weapons[self.selected].reload_begin = time.time()
        self.udpdateClientWeapons()

    def reload(self):
        if self.weapons[self.selected].reload():
            self.udpdateClientWeapons()

    def isAlive(self):
        return self.life > 0;

    def damages(self, i):
        self.life -= i
        if self.life < 0:
            self.life = 0

        self.updateClientLife()

        return self.isAlive()

    #UPDATE CLIENT

    def updateClientPlayersPos(self):
        players_pos = [self.getPos()]
        for player in list(__main__.players):
            if player != self.name and self.canShot(__main__.clients[player][1].player.getPos(), __main__.clients[player][1].player.getPos()):
                players_pos.append(__main__.clients[player][1].player.getPos())
        __main__.send.sendto(self.name, __main__.server.compressPlayersPos(players_pos))

    def udpdateClientWeapons(self):
        weapons_compressed = 'wps'
        for weapon in self.weapons:
            weapons_compressed += weapon.name + ',' + str(weapon.getBullets()) + ' / ' + str(weapon.bullets) + ';'
        __main__.send.sendto(self.name, weapons_compressed)

    def updateClientLife(self):
        __main__.send.sendto(self.name, 'life' + str(self.life))

    def updateClientMaxlife(self):
        __main__.send.sendto(self.name, 'mlif' + str(self.maxlife))
