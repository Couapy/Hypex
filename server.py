import socket, sys, os, re, threading, json, time
from models.Player import *
from models.Players.Soldier import *
from models.Players.Scout import *
from models.Players.Tank import *
from models.Map import *

#GAMEMODES
class DeathMatch():
    def __init__(self):
        self.t = 0

    def do(self, i):
        a = time.time()
        #MOVE
        for player in list(players):
            clients[player][1].player.move()

        #UPDATE PLAYER POS
        for player in list(players):
            clients[player][1].player.updateClientPlayersPos()

        #FIRE
        for player in list(players):
            clients[player][1].player.fireHandler()

        #tirs LENT (rockets et mines)

        self.t += 1
        if self.t > tickrates:
            self.t = 0

        b = int((time.time()-a)*1000)
        if b > 2 * int(timeTick * 1000):
            print('OVERLOAD: ' + str(rate))

    def connect(self, name):
        pass

    def death(self, name):
        pass

#CONNEXIONS HANDLER
class ThreadConnexions(threading.Thread):
    def __init__(self, connexion):
        threading.Thread.__init__(self)
        self.connexion = connexion

    def run(self):
        global clients
        while RUN:
            while maxplayers > len(clients):
                try:
                    client, adresse = self.connexion.accept()
                except Exception as e:
                    pass
                else:
                    thread = ThreadClient(client, adresse)
                    thread.start()
                    clients[thread.getName()] = [client, thread]
            time.sleep(0.1)

        print("[INFO] Thread connexion STOP")

    def stop(self):
        connexion = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        connexion.connect(('localhost', PORT))

class ThreadServer(threading.Thread):
    def __init__(self):
        threading.Thread.__init__(self)
        print('[SERVER] SERVER STARTED')

    def run(self):
        global rate
        sec = time.time()
        i = 0
        time1 = time.time()

        while RUN:
            while players != [] and RUN:
                time0 = time.time()

                if (time.time() <= sec + 1):
                    i += 1
                else:
                    rate = i
                    sec = time.time()
                    i = 0
                    send.sendto(CLIENTS, 'time' + str(time.time()))

                gamemode.do(i)

                time1 = time.time()
                if (time1-time0 < timeTick):
                    while timeTick > (time.time()-time0):
                        pass
            rate = 0
            time.sleep(0.1)
        print("[INFO] Thread server STOP")

    def compressPlayersPos(self, positions):
        resultat = 'pos'
        for pos in positions:
            resultat += str(pos[0]) + ',' + str(pos[1]) + ';'
        return resultat

class ThreadCommand(threading.Thread):
    def __init__(self):
        threading.Thread.__init__(self)

    def run(self):
        global RUN, tickrates, timeTick, map
        commands = ['stop', 'clear', 'clients', 'rate', r"^rate ", 'kickall', r"^say ", r"^map ", "players"]
        while RUN:
            message = input("")
            command = False
            i = 0
            while i <= len(commands)-1 and command == False:
                if re.match(commands[i], message):
                    command = True
                i +=1

            if message == 'stop':
                    RUN = False
                    for client in list(clients.values()):
                        client[0].close()
                    connexions.stop()
                    os.system('cls')
            elif message == 'clear':
                    os.system('cls')
            elif message == 'clients':
                    if clients != {}:
                        for client in list(clients.keys()):
                            print("--{}".format(client))
                    else:
                        print('[SERVER] ALONE')
            elif message == 'rate':
                    print('[SERVER] RATE = {} :: REAL = {}'.format(tickrates, rate))
            elif re.match(r"^rate ", message):
                    try:
                        tickrates = int(message[5:])
                        timeTick = 1 / tickrates
                    except:
                        print('[INFO] ERROR')
                    else:
                        send.sendto(CLIENTS, 'rate' + str(tickrates))
                        print('[SERVER] RATE set to ' + str(tickrates))
            elif message == 'kickall':
                    for name in list(clients.keys()):
                        clients[name][0].close()
                    print('[SERVER] ALONE')
            elif re.match(r"^say ", message):
                    send.sendto(CLIENTS, 'info[ADMIN] ' + message[4:])
            elif re.match(r"map ", message):
                    args = message[4:].split(' ')
                    if len(args) == 3:
                        map[int(args[0])][int(args[1])] = int(args[2])
                    else:
                        print('[INFO] ERROR')
            elif message == 'newmap':
                map = Map.gen()
                map_compressed = 'map['
                for line in map:
                    for case in line:
                        map_compressed += str(case)
                    map_compressed += ';'
                map_compressed += ']'
                send.sendto(CLIENTS, map_compressed)
            elif message == 'players':
                if players != []:
                    for player in players:
                        print('--' + player)
                else:
                    print('[INFO] no players')
            else:
                print('[INFO] Unknow command sorry :/')
        print("[INFO] Thread command STOP")

class ThreadClient(threading.Thread):
    def __init__(self, client, adresse):
        threading.Thread.__init__(self)
        self.client = client
        self.adresse = adresse
        self.RUN = True
        self.selected = False
        self.player = None

        self.msg_uncomplete = ''

    def run(self):
        global players
        name = self.getName()
        print(">>> {} -- {}:{} connected".format(name, self.adresse[0], self.adresse[1]))
        message = ">>> {} connected".format(name)
        try:
            #INFO CONNECTED TO ALL
            send.sendto(CLIENTS, 'info' + message)
            message = 'players'
            for client in list(clients.values()):
                message += client[1].getName() + ';'
            send.sendto(CLIENTS, message)

            #INFO RATE TO CLIENT
            send.sendto(self.getName(), 'rate' + str(tickrates))

            #MAP UPDATE CLIENT
            map_compressed = 'map['
            for line in map:
                for case in line:
                    map_compressed += str(case)
                map_compressed += ';'
            map_compressed += ']'
            send.sendto(self.getName(), map_compressed)
            send.sendto(self.getName(), 'siz' + str(Map.getSizeDisplay()))
        except Exception as e:
            print(e)
            self.stop()

        while RUN and self.RUN:
            try:
                lenrcv = self.client.recv(6)
                message = self.client.recv(int(lenrcv.decode()[1:]))
                if message:
                    self.decode(message.decode())
                else:
                    self.stop()
            except Exception as e:
                self.stop()

        print("<<< {} connection lost".format(name))

    def decode(self, message):
        if message[:13] == "select_class ":
            selected = message.split(' ')[1]
            if selected in ('Soldier', 'Scout', 'Tank'):
                self.selected = selected
                if selected == 'Soldier':
                    self.player = Soldier(self.getName())
                elif selected == 'Scout':
                    self.player = Scout(self.getName())
                elif selected == 'Tank':
                    self.player = Tank(self.getName())
                print('[INFO][' + self.getName() + '] set ' + self.selected)
        elif message[:5] == 'move ':
            message = message.split(' ')
            direction = message[1]
            value = message[2] == 'down'
            if direction == 'forward':
                self.player.forward = value
            if direction == 'backward':
                self.player.backward = value
            if direction == 'left':
                self.player.left = value
            if direction == 'right':
                self.player.right = value
        elif message[:4] == 'fire':
            message = message[4:]
            pos = message.split(',')
            if message == 'off':
                self.player.fire = False
            else:
                if not self.player.fire:
                    self.player.alreadyFire = False
                self.player.fire = True
                self.player.fire_pos = (float(pos[0]), float(pos[1]))
        elif message[:2] == 'wp':
            self.player.selectWeapon(message[2:])
        else:
            print("{}> {}".format(self.getName(), message))
            send.sendto(CLIENTS, self.getName() + " : " + message)

    def stop(self):
        self.RUN = False
        name = self.getName()
        del clients[name]
        if name in players:
            players.remove(name)
        self.client.close()
        send.sendto(CLIENTS, "<<< {} connection lost".format(name))

class ThreadSend(threading.Thread):
    def __init__(self):
        threading.Thread.__init__(self)
        self.queue = {}

    def run(self):
        while RUN:
            while clients != {}:
                for client in list(self.queue.keys()):
                    if self.queue[client] != '':
                        self.send(client, self.queue[client])
                        self.queue[client] = ''
            time.sleep(0.1)
        print('[INFO] thread send stop')

    def sendto(self, client, message):
        bytes = 'b' + '0' * (5 - len(str(len(message)))) + str(len(message))
        message = bytes + message
        if client == CLIENTS:
            for client in list(clients.keys()):
                if client in list(self.queue.keys()):
                    self.queue[client] += message
                else:
                    self.queue[client] = message
        elif client == PLAYERS:
            for client in list(players):
                if client in list(self.queue.keys()):
                    self.queue[client] += message
                else:
                    self.queue[client] = message
        else:
            if client in list(self.queue.keys()):
                self.queue[client] += message
            else:
                self.queue[client] = message

    def send(self, client, message):
        try:
            clients[client][0].send(message.encode())
        except Exception as e:
            return False
        else:
            return True

#CONSTANTS
CLIENTS = 123
PLAYERS = 456

#GAME
Map = Map()
map = Map.gen()
mode = 'DeathMatch'
gamemode = DeathMatch()

#SERVER PARAMS
tickrates = 64
timeTick = 1 / tickrates
rate = 0
maxplayers = 8
lenrcv = 8192

#CONNEXIONS SOCKETS
HOST = ''
PORT = 12864
lenght = 2048
RUN = True

try:
    connexion = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    connexion.bind((HOST, PORT))
    connexion.listen(maxplayers)
    #connexion.settimeout(None)
except Exception as e:
    print('[ERROR] port inacessible')
else:
    clients = {}
    players = []

    #SERVER START
    send = ThreadSend()
    send.start()
    server = ThreadServer()
    server.start()
    command = ThreadCommand()
    command.start()
    connexions = ThreadConnexions(connexion)
    connexions.start()

    #END
    server.join()
    command.join()
    connexions.join()

    print('>>> - <<< SERVER CLOSED >>> - <<<')
os.system('pause')
