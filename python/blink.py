import time
from sense_hat import SenseHat

sense = SenseHat()

message = "Hello, World!"
color = [0, 0, 0]
speed = 1

if len(sys.argv) > 3:
	speed = float(sys.argv[3])

if len(sys.argv) > 2:
	code = sys.argv[2].lstrip('#')
	color=list(int(code[i:i+2], 16) for i in (0, 2 ,4))

if len(sys.argv) > 1:
	message = sys.argv[1]

for i in sys.argv[1]:
	sense.show_letter(str(i))
	time.sleep(1/speed)