import pygame, time

width, height = 854, 480
RUN = True

pygame.init()
display = pygame.display.set_mode((width, height))
pygame.display.set_caption("Brouillon 2")
font20 = pygame.font.Font(None, 20)
font30 = pygame.font.Font(None, 30)
font40 = pygame.font.Font(None, 40)
max_option_width = 0

def Draw():
    global max_option_width
    def connexion():
        pass
    def settings():
        pass
    def credits():
        pass

    background = pygame.Surface((width, height))
    background.fill((255,255,255))

    title_bar = pygame.Surface((width, 40))
    title_bar.fill((0, 0, 0))
    background.blit(title_bar, (0, 0))

    title = font40.render("Rush my aim", 1, (255, 255, 255))
    background.blit(title, (42, 7))

    icon = pygame.image.load("../images/icon.png").convert_alpha()
    icon = pygame.transform.scale(icon, (28, 28))
    background.blit(icon, (7, 7))

    options = {
        'Connexion': connexion,
        #'stats': stats,
        #'inventory': inventory,
        'Settings': settings,
        'Credits': credits,
    }

    i = 0
    for option in options.keys():
        option_text = font40.render(option, 1, (0, 0, 0))
        background.blit(option_text, (5, 45 + i * 40))
        max_option_width = option_text.get_size()[0] if option_text.get_size()[0] > max_option_width else max_option_width
        i += 1
    i = 0
    for option in options.keys():
        pygame.draw.line(background, (0, 0, 0), (0,  78+i*40), (max_option_width + 10, 78+i*40), 1)
        i += 1

    pygame.draw.line(background, (0, 0, 0), (max_option_width + 10, 40), (max_option_width + 10, height), 1)

    return background

background = Draw()
pygame.mouse.set_cursor(*pygame.cursors.broken_x)

block = False

while RUN:
    display.blit(background, (0,0))

    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            RUN = False
            RUN_SETTINGS = False
            CLOSED = True
        elif event.type == pygame.MOUSEMOTION:
            pos = event.pos
            if pos[0] <= max_option_width:
                pygame.mouse.set_cursor(*pygame.cursors.broken_x)
            else:
                pygame.mouse.set_cursor(*pygame.cursors.tri_left)
        elif event.type == pygame.MOUSEBUTTONDOWN:
            pass
        elif event.type == pygame.KEYDOWN:
            block = True if not block else False

        if block:
            pygame.mouse.set_pos([width/2, height/2])

    pygame.display.flip()

pygame.quit()
