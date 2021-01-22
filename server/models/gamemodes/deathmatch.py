from models.gamemode import GameMode


class DeathMatch(GameMode):
    """This model is a game mode."""

    def __init__(self, server):
        super().__init__(server)

        # Rules
        self.max_players = 10
        self.max_rounds = 1
        self.instant_respawn = True
        self.time_warmup = 20
        self.time_per_round = 300
        self.time_pre_round = 0
