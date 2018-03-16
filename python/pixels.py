import sys
import json
from sense_hat import SenseHat

sense = SenseHat()
lines = sys.stdin.readlines()

if len(sys.argv) > 1:
	# Load the image from file
	try:
		sense.load_image(sys.argv[1])
	except Exception e:
		print("ERROR failed to load image")
		exit(1)
elif len(lines) > 0:
	# Expects a 64 length list (8x8) each containing a smaller list of the [R, G, B] values. 
	try:
		sense.set_pixels(json.loads(lines))
	except:
		print("ERROR failed to parse JSON")
		exit(2)
else:
	# Default action
	print(sense.get_pixels())
