class Player():

    def __init__(self, name):
        self.start = 0
        self.turn = 0
        self.piece = ""
        self.name = name

    def set_turn(self, a:int):
        self.turn = a

    def set_piece(self, xo: str):
        self.piece = xo

    def get_turn(self):
        return self.turn

    def get_piece(self):
        return self.piece
