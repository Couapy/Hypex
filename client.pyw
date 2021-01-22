import socket, sys, os, threading, pygame, json, time
os.system('cls')

class ThreadConnexion(threading.Thread):
    def __init__(self):
        threading.Thread.__init__(self)

    def run(self):
        global RUN, CLOSED, connected, connexion
        connected = False
        while not CLOSED:
            time.sleep(0.1)
            while not connected and not CLOSED:
                connexion = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                try:
                    connexion.connect((host, port))
                except:
                    pass
                else:
                    connected = True
                    RUN = True
                    print("[INFO] CONNECTED")
                    rcv = ThreadRcv(connexion)
                    rcv.start()
        print('[INFO] Connexion STOP')

class ThreadRcv(threading.Thread):
    def __init__(self, connexion):
        threading.Thread.__init__(self)
        self.connexion = connexion
        self.msg_uncomplete = ''

    def run(self):
        global RUN, CLOSED
        while RUN:
            try:
                message = self.connexion.recv(2048).decode()
                if self.msg_uncomplete != '':
                    message = self.msg_uncomplete + message
                    self.msg_uncomplete = ''
                while message != '':
                    lenrcv = int(message[1:6])
                    if len(message[6:]) >= lenrcv:
                        message_temp = message[6:6+lenrcv]
                        message = message[6+lenrcv:]
                        if message_temp:
                            self.decode(message_temp)
                        else:
                            self.abort()
                    else:
                        self.msg_uncomplete += message
                        break
            except Exception as e:
                self.abort()

    def decode(self, message):
        global tickrates, players_pos, map, retard, players, life, maxlife, weapons, wp_selected, size
        #GAME DATA
        if message[:3] == 'pos':
            players_pos = []
            for pos in message[3:].split(';'):
                if pos != '':
                    x, y = pos.split(',')
                    players_pos.append((float(x), float(y)))
        elif message[:4] == 'life':
            life = int(message[4:])
        elif message[:4] == 'mlif':
            maxlife = int(message[4:])
        elif message[:4] == 'time':
            retard = int((time.time() - float(message[4:])) * 1000)
        elif message[:7] == 'players':
            players = []
            for player in message[7:].split(';'):
                players.append(player)
        elif message[:3] == 'wps':
            message = message[3:]
            wps = message.split(';')
            weapons = {}
            for wp in wps:
                if wp:
                    infos = wp.split(',')
                    weapons[infos[0]] = infos[1]
            if not wp_selected in list(weapons.keys()):
                wp_selected = list(weapons.keys())[0]
        elif message[:3] == 'map':
            map_compressed = message[4:-1]
            lines = map_compressed.split(';')
            map = []
            for line in lines:
                if line != '':
                    array = []
                    for case in line:
                        array.append(int(case))
                    map.append(array)
            print('[INFO] map updated')
        elif message[:3] == 'siz':
            size = int(message[3:])
            print('[INFO] size display updated to ' + str(size))

        #SERVER DATA
        elif message[:4] == 'rate':
            tickrates = int(message[4:])
            print('[RATE] set to {}'.format(tickrates))
        elif message[:4] == 'info':
            print(message[4:])

    def abort(self):
        global RUN, connected
        print(">>> - <<< CONNECTION LOST >>> - <<<")
        print()
        self.connexion.close()
        RUN = False
        connected = False

class ThreadDisplay(threading.Thread):
    def __init__(self):
        threading.Thread.__init__(self)
        self.map = []
        self.size = size
        self.players_pos = {}
        self.score = False
        self.fps = 0

        self.background = None
        self.map_background = None
        self.genBackground()
        self.font20 = pygame.font.Font(None, 20)

        self.background_back = pygame.Surface((width, height))
        self.background_back.fill((0,0,0))

        self.wait_background = pygame.Surface((width, height))
        self.wait_background.fill((0,0,0))
        info = self.font20.render('En attente des instructions du serveur...', 1, (255, 255, 255))
        self.wait_background.blit(info, (20, 20))

        #HUD
        self.bullets = pygame.image.load("images/bullets.png").convert_alpha()
        self.genHUD()

    def run(self):
        i = 0
        sec = time.time()
        while not CLOSED and RUN:
            while connected and not CLOSED and RUN:
                try:
                    if not overlay:
                        if life != 0:
                            if players_pos == {}:
                                display.blit(self.wait_background, (0, 0))
                            else:
                                self.background = self.map_background.copy()
                                if self.map != map or self.size != size:
                                    self.map = map
                                    self.size = size
                                    self.genBackground()
                                self.updatePlayerPos()
                                self.drawMap()
                                self.drawHUD()
                                if score:
                                    self.drawScores()
                            pygame.display.flip()
                        else:
                            self.death()
                except Exception as e:
                    print(e)

                i += 1
                if sec + 1 <= time.time():
                    sec = time.time()
                    self.fps = i
                    i = 0
            time.sleep(0.1)
        print('[INFO] Display STOP')

    def genBackground(self):
        if map == []:
            self.background = pygame.Surface((width, height))
            self.background.fill((255,255,255))
        else:
            self.background = pygame.Surface((size*len(map[0]), size*len(map)))
            self.background.fill((255,255,255))
            case = pygame.Surface((size, size))
            case.fill((0,0,0))
            if map != []:
                for x in range(0, len(map[0])):
                    for y in range(0, len(map)):
                        if map[y][x] == 1:
                            self.background.blit(case, (x*size, y*size))

        self.map_background = self.background.copy()

    def drawMap(self):
        global edge
        x, y = players_pos[0]
        a, b = x*size, y*size
        j, k = 0, 0
        q, s, d, f = a-width/2, b-height/2, a+width/2, b+height/2
        edge = [-1, -1]

        if q < 0:
            q = 0
            d = width
            j = (width/2)-a
            edge[0] = -j
        if s < 0:
            s = 0
            f = height
            k = (height/2)-b
            edge[1] = -k
        edge[0] = q if edge[0] == -1 else edge[0]
        edge[1] = s if edge[1] == -1 else edge[1]

        display.blit(self.background_back, (0,0))
        display.blit(self.background, (j, k), (q, s, d, f))

    def genHUD(self):
        hud = pygame.Surface((width, 25))
        hud.fill((0,0,0))
        hud.set_alpha(160)

        #LIFE
        slifemax = pygame.Surface((100, 20))
        slifemax.fill((128,128,128))
        hud.blit(slifemax, (5, 3))
        vertical = pygame.Surface((8, 20))
        vertical.fill((255,255,255))
        horizontal = pygame.Surface((20, 8))
        horizontal.fill((255,255,255))
        hud.blit(horizontal, (110, 9))
        hud.blit(vertical, (116, 3))

        self.hud = hud

    def drawHUD(self):
        hud_copy = self.hud.copy()

        slife = pygame.Surface((100*(life/maxlife), 20))
        slife.fill((255,255,255))
        hud_copy.blit(slife, (5, 3))

        #Weapons
        weapon_bullets = font20.render(weapons[wp_selected], 1, (255, 255, 255))
        if weapons[wp_selected][:1] == '0':
            weapon = font20.render('RELOADING   ' + wp_selected, 1, (255, 255, 255))
            pygame.mouse.set_cursor(*pygame.cursors.diamond)
        else:
            weapon = font20.render(wp_selected, 1, (255, 255, 255))
            pygame.mouse.set_cursor(*pygame.cursors.broken_x)

        hud_copy.blit(weapon_bullets, (width-weapon_bullets.get_size()[0]-5, (25-weapon_bullets.get_size()[1])/2))
        hud_copy.blit(self.bullets, (width-weapon_bullets.get_size()[0]-self.bullets.get_size()[0]-10, (25-self.bullets.get_size()[1])/2))
        hud_copy.blit(weapon, (width-weapon_bullets.get_size()[0]-self.bullets.get_size()[0]-weapon.get_size()[0]-15, (25-weapon.get_size()[1])/2))
        display.blit(hud_copy, (0, height-25))

    def updatePlayerPos(self):
        i = 0
        for player in players_pos:
            i += 1
            x, y = player[0], player[1]
            if i == 1:
                pygame.draw.circle(self.background, (16, 32, 64), (int(x*size), int(y*size)), int(size/2))
            else:
                pygame.draw.circle(self.background, (0, 0, 0), (int(x*size), int(y*size)), int(size/2))

        fire_pos = [(edge[0]+pos[0])/size, (edge[1]+pos[1])/size]
        if fire:
            pygame.draw.circle(self.background, (32, 64, 128), (int(fire_pos[0]*size), int(fire_pos[1]*size)), int(size/2))

    def drawScores(self):
        scores = pygame.Surface((width/2, 10 + 20*(len(players)-1)))
        scores.fill((0,0,0))
        i=0
        for player in players:
            scores.blit(self.font20.render(player, 1, (255, 255, 255)), (5, 10 + i*20))
            i += 1

        retard_view = self.font20.render(str(retard) + 'ms', 1, (37, 59, 0))
        fps = self.font20.render('FPS: ' + str(self.fps), 1, (37, 59, 0))
        display.blit(retard_view, (710 - retard_view.get_size()[0], 10))
        display.blit(fps, (10, 10))
        display.blit(scores, ((width-width/2)/2, 5))

    def death(self):
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
            instructions = font30.render("En attente du serveur...", 1, (0, 0, 0))

            background.blit(title, (20, 10))
            background.blit(instructions, (20, 50))

            death = pygame.image.load("images/death.png").convert_alpha()
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

def Waiter():
    global RUN, CLOSED
    # Fill background
    background = pygame.Surface(display.get_size())
    background = background.convert()
    background.fill((250, 250, 250))

    # Display some text
    font = pygame.font.Font(None, 36)
    text = font.render("En attente de connexion avec le serveur...", 1, (10, 10, 10))
    background.blit(text, (10, 10))

    # Event loop
    while not connected and RUN and not CLOSED:
        display.blit(background, (0, 0))
        pygame.display.flip()
        for evt in pygame.event.get():
            if evt.type == pygame.QUIT:
                RUN = False
                CLOSED = True
                pygame.quit()

    background.fill((250, 250, 250))
    font = pygame.font.Font(None, 36)
    text = font.render("Connecté", 1, (10, 10, 10))
    textpos = text.get_rect()
    textpos.centerx = 0
    textpos.centery = 50
    background.blit(text, (10, 10))

    # Blit everything to the screen
    display.blit(background, (0, 0))
    pygame.display.flip()

def selector():
    global RUN, CLOSED, selected, overlay
    overlay = True
    x, y = 0, 0
    #DRAW
    background = pygame.Surface((width, height))
    background.fill((255,255,255))

    #images
    soldier = pygame.image.load("images/soldier.png").convert_alpha()
    scout = pygame.image.load("images/scout.png").convert_alpha()
    tank = pygame.image.load("images/tank.png").convert_alpha()
    settings_icon = pygame.image.load("images/settings.png").convert_alpha()

    white_space = (width-3*soldier.get_size()[0])/4
    class_space = soldier.get_size()[0]
    height_class = (height-soldier.get_size()[1])/2
    background.blit(soldier, (white_space, height_class))
    background.blit(scout, (2*white_space + class_space, height_class))
    background.blit(tank, (3*white_space + 2*class_space, height_class))
    background.blit(settings_icon, (width-settings_icon.get_size()[0]-15, 15))

    while selected == 'None' and RUN:
        display.blit(background, (0,0))

        if height_class < y and y < height_class + soldier.get_size()[1]:
            if white_space < x and x < white_space + class_space:
                pygame.draw.lines(display, (0,0,0), False, [(white_space, height_class), (white_space + class_space, height_class), (white_space + class_space, height_class + soldier.get_size()[1]), (white_space, height_class + soldier.get_size()[1]), (white_space, height_class)], 2)
            elif 2*white_space + class_space < x and x < 2*white_space + 2*class_space:
                pygame.draw.lines(display, (0,0,0), False, [(2*white_space + class_space, height_class), (2*white_space + 2*class_space, height_class), (2*white_space + 2*class_space, height_class + soldier.get_size()[1]), (2*white_space + class_space, height_class + soldier.get_size()[1]), (2*white_space + class_space, height_class)], 2)
            elif 3*white_space + 2*class_space < x and x < 3*white_space + 3*class_space:
                pygame.draw.lines(display, (0,0,0), False, [(3*white_space + 2*class_space, height_class), (3*white_space + 3*class_space, height_class), (3*white_space + 3*class_space, height_class + soldier.get_size()[1]), (3*white_space + 2*class_space, height_class + soldier.get_size()[1]), (3*white_space + 2*class_space, height_class)], 2)
        elif 15 < y and y < 15 + settings_icon.get_size()[1] and width-settings_icon.get_size()[0]-15 < x and x < width-15:
            pygame.draw.lines(display, (0,0,0), False, [(width - settings_icon.get_size()[0] - 15, 15), (width - 15, 15), (width - 15, 15 + settings_icon.get_size()[1]), (width - settings_icon.get_size()[0] - 15, 15 + settings_icon.get_size()[1]), (width - settings_icon.get_size()[0] - 15, 15)], 1)

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                RUN = False
                CLOSED = True
            elif event.type == pygame.MOUSEMOTION:
                x, y = event.pos
            elif event.type == pygame.MOUSEBUTTONDOWN:
                a, b = event.pos
                if height_class < b and b < height_class + soldier.get_size()[1]:
                    if white_space < a and a < white_space + class_space:
                        selected = 0
                    elif 2*white_space + class_space < x and x < 2*white_space + 2*class_space:
                        selected = 1
                    elif 3*white_space + 2*class_space < x and x < 3*white_space + 3*class_space:
                        selected = 2
                elif 15 < y and y < 15 + settings_icon.get_size()[1] and width-settings_icon.get_size()[0]-15 < x and x < width-15:
                    settings()
                    overlay = True
            elif event.type == pygame.KEYDOWN:
                if event.unicode in ('1', '2', '3'):
                    selected = int(event.unicode) - 1
        pygame.display.flip()

    if selected != 'None':
        classes = ['Soldier', 'Scout', 'Tank']
        send('select_class ' + classes[selected])
        print('[SELECT_CLASS] ' + classes[selected])
    overlay = False

def settings():
    global RUN, CLOSED, overlay
    overlay = True
    RUN_SETTINGS = True
    x, y = 0, 0
    def Draw():
        #DRAW
        background = pygame.Surface((width, height))
        background.fill((255,255,255))

        #TEXT
        font30 = pygame.font.Font(None, 30)
        font40 = pygame.font.Font(None, 40)
        font40.set_underline(True)

        title = font40.render("Paramètres", 1, (0, 0, 0))
        forward = font30.render("Avancer", 1, (0, 0, 0))
        backward = font30.render("Reculer", 1, (0, 0, 0))
        left = font30.render("Gauche", 1, (0, 0, 0))
        right = font30.render("Droite", 1, (0, 0, 0))
        players = font30.render("Afficher les joueurs", 1, (0, 0, 0))

        background.blit(title, (20, 10))

        back = pygame.image.load("images/back.png").convert_alpha()
        background.blit(back, (width-79, 15))

        settings_keys = [(forward, "forward"), (backward, "backward"), (left, "left"), (right, "right"), (players, "players")]

        i = 0
        size = 0
        for text in settings_keys:
            text = text[0]
            if text.get_size()[0] > size:
                size = text.get_size()[0]
            background.blit(text, (20, 50 + i*30))
            i += 1

        i = 0
        for key in keys.values():
            text = font30.render(key[0], 1, (0, 0, 0))
            background.blit(text, (40 + size, 50 + i*30))
            i += 1
        return background

    background = Draw()
    configurate = False
    configurate_key = ''
    while RUN_SETTINGS and RUN:
        display.blit(background, (0,0))

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                RUN = False
                RUN_SETTINGS = False
                CLOSED = True
            elif event.type == pygame.MOUSEBUTTONDOWN:
                x, y = event.pos
                if width-79 < x and x < width-15 and 15 < y and y < 79:
                    RUN_SETTINGS = False
            if event.type == pygame.KEYDOWN and event.key == pygame.K_ESCAPE:
                RUN_SETTINGS = False
            elif event.type == pygame.KEYDOWN:
                if configurate:
                    if ((event.unicode, event.key) in list(keys.values())) == False:
                        if event.key == pygame.K_TAB:
                            event.unicode = 'TAB'
                        keys[configurate] = (event.unicode, event.key)
                        configurate = False
                        configurate_key = ''
                else:
                    if event.key == pygame.K_TAB:
                        event.unicode = 'TAB'
                    if (event.unicode, event.key) in keys.values():
                        configurate = list(keys.keys())[list(keys.values()).index((event.unicode, event.key))]
                        configurate_key = keys[configurate]
                        keys[configurate] = ('_', 0)
                background = Draw()

        pygame.display.flip()
    overlay = False

def send(message):
    bytes = 'b' + '0' * (5 - len(str(len(message)))) + str(len(message))
    try:
        connexion.send(bytes.encode())
        connexion.send(message.encode())
    except Exception as e:
        return False
    else:
        return True

#MAP DATA
map = []
players_pos = {}
players = []

#PLAYER DATA
selected = 'None'
x = 0
y = 0
keys = {
    'forward': ('z', 119),
    'backward': ('s', 115),
    'left': ('q', 97),
    'right': ('d', 100),
    'players': ('TAB', pygame.K_TAB),
}
weapons = {
    'HAND': '1 / 0'
}
wp_selected = list(weapons.keys())[0]
life = 1
maxlife = 100
fire = False
pos = (-1,-1)

#DISPLAY SETTINGS
width = 854
height = 480
size = 20
overlay = True
score = True
edge = (-1, -1)

pygame.init()
display = pygame.display.set_mode((width, height))
pygame.display.set_caption("Rush my aim")
pygame.display.set_icon(pygame.image.load("images/icon.png").convert_alpha())
font20 = pygame.font.Font(None, 20)

#CONNECTION
host = 'ext.litweby.ovh'
port = 12864
connected = False
tickrates = 64
retard = 0

RUN = True
CLOSED = False
connexion = None

ThreadConnexion = ThreadConnexion()
ThreadConnexion.start()
ThreadDisplay = ThreadDisplay()
ThreadDisplay.start()

while not CLOSED:
    pygame.mouse.set_cursor(*pygame.cursors.tri_left)

    RUN = True
    CLOSED = False
    print("[INFO] Waiting for connection with the server ...")
    try:
        Waiter()
        selector()
    except:
        pass

    pygame.mouse.set_cursor(*pygame.cursors.broken_x)

    while connected and RUN:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                CLOSED = True
                RUN = False
                connexion.close()
                pygame.quit()
            elif event.type == pygame.MOUSEMOTION:
                pos = event.pos
                if fire:
                    fire_pos = [(edge[0]+pos[0])/size, (edge[1]+pos[1])/size]
                    send('fire' + str(fire_pos[0]) + ',' + str(fire_pos[1]))
            elif event.type == pygame.MOUSEBUTTONDOWN:
                if event.button == 1:
                    pos = event.pos
                    fire = True
                    fire_pos = [(edge[0]+pos[0])/size, (edge[1]+pos[1])/size]
                    send('fire' + str(fire_pos[0]) + ',' + str(fire_pos[1]))
                elif event.button == 4:
                    index = list(weapons.keys()).index(wp_selected)
                    lweapons = list(weapons.keys())
                    if index == 0:
                        wp_selected = lweapons[len(lweapons)-1]
                    else:
                        wp_selected = lweapons[index-1]
                    send('wp' + wp_selected)
                elif event.button == 5:
                    index = list(weapons.keys()).index(wp_selected)
                    lweapons = list(weapons.keys())
                    if index == len(lweapons)-1:
                        wp_selected = lweapons[0]
                    else:
                        wp_selected = lweapons[index+1]
                    send('wp' + wp_selected)
            elif event.type == pygame.MOUSEBUTTONUP and event.button == 1:
                fire = False
                send('fireoff')
            elif event.type == pygame.KEYDOWN:
                if event.key == pygame.K_ESCAPE:
                    settings()
                elif event.key == keys['players'][1]:
                    score = True
                elif (event.unicode, event.key) in keys.values():
                    send('move ' + list(keys.keys())[list(keys.values()).index((event.unicode, event.key))] + ' down')
            elif event.type == pygame.KEYUP:
                if event.key == keys['players'][1]:
                    score = False
                else:
                    for key in keys.values():
                        if event.key == key[1]:
                            send('move ' + list(keys.keys())[list(keys.values()).index(key)] + ' up')
    selected = 'None'

pygame.quit()
