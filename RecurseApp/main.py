import Player

def main():
    # init statements?
    global_turn = 1


# choose player to go first - later


# detect win -- either all cols are the same, all rows, or diagonals = [0][0],
# [1][1], up to [n][n]
moves_counter = 0 ## counter for # of moves, and tells you when to draw i.e. if 5x5,
# counter = 25 bc can only fill up those spaces
whose_move = 1 #player 1 or player 2; later convert in UI to X or O str

def win_checker(board:list, whose_move:int):
    while moves_counter != n*n:
        pass

def move(board: list, whose_move: int):
    pass
    # for row in board:
    #
    #     for col in row:

# def set_board_size():
#     pass

def setup_board(n: int):
    """ Takes input n from UI to decide board size, returns board."""

    col_labels = ("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
                  "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
                  "Y", "Z")

    row_labels = ("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
                  "13", "14", "15", "16", "17", "18", "19", "20", "21", "22",
                  "23", "24", "25", "26") # this one’s bc the indices is different; just wanna access to set board keys/rows

    board = {}
    # a = {}
    for ind1 in range(n):
        for ind2 in range(n):
            board[row_labels[ind1]] = {col_labels[ind2]: 0}
    #     print(a)
    # board += [a]*n
    #     n += 1
    print(board)

    # Rest of for loops go here;


    # board = {1: {"a": 0, "b": 0, "c": 0}, 2: {"a": 0, "b": 0, "c": 0, "d": 0}, 3: {"a": 0, "b": 0, "c": 0, "d": 0}, 4: {"a": 0, "b": 0, "c": 0, "d": 0}}

def checker(board): ## for n*n board, will need to check 2n+2 times for every move;

    # Or just check in that move’s col + row, and if it's in the board's diagonals (3 checks per move)

    for numrow in board:

        for alphacol in numrow:

            if alphacol == numrow:
                return none
                # total += 1


### Collapsed board: 1:[a:0, b: 0, c: 0, d:0, ...], 2:[a:0 , b:0, c:0, d:0], ...4:...]
### then check

setup_board(10)
