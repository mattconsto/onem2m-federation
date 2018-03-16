from sense_hat import SenseHat

print("{pitch},{roll},{yaw}".format(**SenseHat().get_orientation()))
