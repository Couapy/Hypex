"""This is the game data model's file."""


class Game:
    """Game object."""

    def __init__(self):
        """Initialise the object."""
        # Game Data
        self.players = {}  # TODO: Create player object (Sprite)
        self.score = (0, 0)

        # Self Data
        self.name = ""
        self.pos = (-1, -1)
        self.lookangle = 0
        self.life = 0
        self.maxlife = 100
        self.weapons = ['Pistol']
        self.weapon = 'Pistol'
        self.weapon_bullets = 12
        self.weapon_charger = 24
