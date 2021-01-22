import pygame, time, threading

map = [
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,0,1,1,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,1,0,1,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,1,0,1,0,0,0,1,0,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,1,0,1,1,0,1,0,0,0,0,1,0],
    [0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,1,0,0,1,0,1,0,0,0,0,0,0,0,1,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,1,0,0,0,0,0,0,0,1,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,1,1,1,0,1,1,1,1,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,1,1,1,1,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,1,1,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0]
]
weapons = {
    'PISTOL': '12 / 24',
    'UZI': '30 / 90',
    'PIOU PIOU': '12 / 24'
}
wp_selected = list(weapons.keys())[0]
life = 75
life_max = 100

def draw():
    pygame.draw.rect(display, (255, 255, 255), (0, 0, width, height))
    for a in range(0, len(map[0])):
        for b in range(0, len(map)):
            case = map[b][a]
            a1 = a*size
            b1 = b*size
            a2 = size
            b2 = size
            if case == 1:
                pygame.draw.rect(display, (0, 0, 0, 128), (a1, b1, a2, b2))
    pygame.draw.circle(display, color, (int(x*size), int(y*size)), int(size/2))

    for pos in players:
        if canShot(pos):
            pygame.draw.circle(display, (0, 255, 0), (int(pos[0]*size), int(pos[1]*size)), int(size/2))

    if fire:
        Dofire()

def HUD():
    hud = pygame.Surface((width, 25))
    hud.fill((0,0,0))
    hud.set_alpha(160)

    #LIFE
    slifemax = pygame.Surface((100, 20))
    slifemax.fill((128,128,128))
    slife = pygame.Surface((100*(life/life_max), 20))
    slife.fill((255,255,255))
    hud.blit(slifemax, (5, 3))
    hud.blit(slife, (5, 3))
    vertical = pygame.Surface((8, 20))
    vertical.fill((255,255,255))
    horizontal = pygame.Surface((20, 8))
    horizontal.fill((255,255,255))
    hud.blit(horizontal, (110, 9))
    hud.blit(vertical, (116, 3))

    #Weapons
    weapon_bullets = font20.render(weapons[wp_selected], 1, (255, 255, 255))
    weapon = font20.render(wp_selected, 1, (255, 255, 255))
    bullets = pygame.image.load("../images/bullets.png").convert_alpha()
    hud.blit(weapon_bullets, (width-weapon_bullets.get_size()[0]-5, (25-weapon_bullets.get_size()[1])/2))
    hud.blit(bullets, (width-weapon_bullets.get_size()[0]-bullets.get_size()[0]-10, (25-bullets.get_size()[1])/2))
    hud.blit(weapon, (width-weapon_bullets.get_size()[0]-bullets.get_size()[0]-weapon.get_size()[0]-15, (25-weapon.get_size()[1])/2))
    display.blit(hud, (0, height-25))

def Dofire():
    case = []
    a, b = x, y
    i = 0
    if x == mouse_pos[0]:
        i = 1 if mouse_pos[1] > y else -1
        while 0 < b and b < len(map):
            if map[int(a)][int(b)] == 1 or ((int(a), int(b)) != (x, y) and (int(a), int(b)) in players):
                pygame.draw.line(display, (255, 0, 0), (a*size, b*size), (int(x*size), int(y*size)), 2)
                break
            b += i
        if not (0 < b and b < len(map)):
            pygame.draw.line(display, (255, 0, 0), (a*size, b*size), (int(x*size), int(y*size)), 2)
    else:
        c = (y-mouse_pos[1])/(x-mouse_pos[0])
        k = y-c*x
        if -1 < c and c <1:
            o = 0.25 if mouse_pos[0] > x else -0.25
        else:
            o = 0.25 if mouse_pos[1] > y else -0.25

        while (0 < a and a < len(map[0]) and 0 < b and b < len(map)) and case == []:
            if map[int(b)][int(a)] == 1 or ((int(a), int(b)) != (x, y) and (int(a), int(b)) in players):
                pygame.draw.line(display, (255, 0, 0), (a*size, (c*a+k)*size), (int(x*size), int(y*size)), 2)
                break
            if -1 < c and c <1:
                a += o
                b = c * a + k
            else:
                b += o
                a = (b-k)/c
        if not (0 < a and a < len(map[0]) and 0 < b and b < len(map)):
            pygame.draw.line(display, (255, 0, 0), (a*size, b*size), (int(x*size), int(y*size)), 2)

def canShot(pos):
    pos1, pos2 = pos
    a, b = x, y
    i = 0
    if x == pos1:
        i = 1 if pos2 > y else -1
        while 0 < b and b < len(map):
            if map[int(a)][int(b)] == 1:
                return False
                break
            elif ((a-pos1)**2 + (b-pos2)**2)**0.5 <= 0.5:
                return True
                break
            b += i
    else:
        c = (y-pos2)/(x-pos1)
        k = y-c*x
        if -1 < c and c <1:
            o = 0.25 if pos1 > x else -0.25
        else:
            o = 0.25 if pos2 > y else -0.25

        while (0 < a and a < len(map[0]) and 0 < b and b < len(map)):
            if map[int(b)][int(a)] == 1:
                return False
                break
            elif ((a-pos1)**2 + (b-pos2)**2)**0.5 <= 0.5:
                return True
                break
            if -1 < c and c <1:
                a += o
                b = c * a + k
            else:
                b += o
                a = (b-k)/c

def updatePlayerPos():
    global x, y
    distance = vitesse / tickrates

    if forward:
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

    if backward:
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

    if left:
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

    if right:
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

def updateTrans(key, value):
    global forward, backward, left, right
    if key == pygame.K_w:
        forward = value
    if key == pygame.K_s:
        backward = value
    if key == pygame.K_a:
        left = value
    if key == pygame.K_d:
        right = value

def death():
    global RUN, CLOSED, overlay
    overlay = True
    def Draw():
        #DRAW
        background = pygame.Surface((width, height))
        background.fill((255,255,255))

        #TEXT
        font30 = pygame.font.Font(None, 30)
        font40 = pygame.font.Font(None, 40)
        font40.set_underline(True)

        title = font40.render("Vous êtes mort", 1, (0, 0, 0))
        instructions = font30.render("Des instructions ici", 1, (0, 0, 0))

        background.blit(title, (20, 10))
        background.blit(instructions, (20, 30))

        death = pygame.image.load("../images/death.png").convert_alpha()
        background.blit(death, (width-279, height-360))
        return background

    background = Draw()
    while RUN:
        display.blit(background, (0,0))

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                RUN = False
                RUN_SETTINGS = False
                CLOSED = True
            elif event.type == pygame.MOUSEBUTTONDOWN:
                pass
            elif event.type == pygame.KEYDOWN:
                pass

        pygame.display.flip()
    overlay = False

def events():
    global RUN, width, height, size, color, vitesse, fire, mouse_pos, wp_selected
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            RUN = False
        if event.type == pygame.KEYUP:
            if event.key == pygame.K_SPACE:
                if color == (0, 0, 0):
                    color = (255, 255, 255)
                else:
                    color = (0, 37, 59)
            if event.key == pygame.K_ESCAPE:
                width = 720
                height = 480
                size = 20
                pygame.display.set_mode((width, height))
            if event.key == pygame.K_f:
                width = 1280
                height = 720
                size = 30
                pygame.display.set_mode((width, height), pygame.FULLSCREEN)
            if event.key == pygame.K_g:
                vitesse = 64
            if event.key == pygame.K_h:
                vitesse = 32
            if event.key == pygame.K_j:
                vitesse = 16
            if event.key == pygame.K_k:
                vitesse = 8
            if event.key == pygame.K_l:
                vitesse = 4
            if event.key == pygame.K_SPACE:
                death()

            updateTrans(event.key, False)
        if event.type == pygame.KEYDOWN:
            updateTrans(event.key, True)
        if event.type == pygame.MOUSEBUTTONDOWN:
            if event.button == 1:
                fire = True
            elif event.button == 4:
                index = list(weapons.keys()).index(wp_selected)
                lweapons = list(weapons.keys())
                if index == 0:
                    wp_selected = lweapons[len(lweapons)-1]
                else:
                    wp_selected = lweapons[index-1]
            elif event.button == 5:
                index = list(weapons.keys()).index(wp_selected)
                lweapons = list(weapons.keys())
                if index == len(lweapons)-1:
                    wp_selected = lweapons[0]
                else:
                    wp_selected = lweapons[index+1]
        if event.type == pygame.MOUSEBUTTONUP and event.button == 1:
            fire = False
        if event.type == pygame.MOUSEMOTION:
            mouse_pos = (event.pos[0]/size, event.pos[1]/size)

pygame.init()

width = 854
height = 480
size = 20

display = pygame.display.set_mode((width, height))
pygame.display.set_caption("Brouillon")
font20 = pygame.font.Font(None, 20)
RUN = True

#player
x = 5.5
y = 7.5
mouse_pos = (0, 0)
players = [(15, 15), (5, 20)]

forward = False
backward = False
left = False
right = False
fire = False

vitesse = 32
tickrates = 128

color = (0, 37, 59, 64)

while RUN:
    time.sleep(1/tickrates)
    draw()
    HUD()
    pygame.display.flip()
    events()
    updatePlayerPos()
pygame.quit()
