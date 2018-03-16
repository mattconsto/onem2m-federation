import sys
from sense_hat import SenseHat

sense = SenseHat()

if len(sys.argv) > 1:
	sense.set_rotation(int(sys.argv[1]))
else:
	print(sense.rotation)