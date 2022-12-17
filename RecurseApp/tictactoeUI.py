# Setup how it looks
#
# print("-" * 5, "\n", "-" * 5,"\n", "-" * 5)
# import numpy as np

# Setup board
# board = np.array(2,3)
import Player
import main

n = int(input("Decide board size"))
main.setup_board(n)


# Add start attr to player class, set to 0 (false)

p1_name= input("Player 1’s Name:")

p2_name = input("player 2’s name")

start_p = int(input("enter starting player’s number"))

# This calls main:

def make_player(player):

    p1 = Player(p1_name)

    p2 = Player(p2_name)

    if start_p == 1:

        p1.start=1 #True

    else:

        p2.start =1

# In class Player, add attribute: Name, Start

# Set up board, ex: 3x3
#
# A B C
#
# 1
#
# 2
#
# 3
#
# in UI:

Board_size = input("what size would you like the board to be? 26x26 is max")


