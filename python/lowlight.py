from sense_hat import SenseHat

sense = SenseHat()

if len(sys.argv) > 1:
	sense.low_light = sys.argv[1].lower() in ("true", "yes", "on", "t", "y")
else:
	print(sense.low_light)
