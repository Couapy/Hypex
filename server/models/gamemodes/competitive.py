from models.gamemode import GameMode


class Competitive(GameMode):
    """This model is a game mode."""

    def __init__(self, server):
        super().__init__(server)

        # Rules
        self.max_players = 10
        self.max_rounds = 16
        self.instant_respawn = False
        self.time_warmup = 30
        self.time_per_round = 120
        self.time_pre_round = 10
