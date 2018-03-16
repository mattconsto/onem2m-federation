import sys
from sense_hat import SenseHat

color = [0, 0, 0]

if len(sys.argv) > 1:
	code = sys.argv[1].lstrip('#')
	color = list(int(code[i:i+2], 16) for i in (0, 2 ,4))

SenseHat().clear(color)