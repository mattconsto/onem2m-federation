from sense_hat import SenseHat

print("{x},{y},{z}".format(**SenseHat().get_accelerometer_raw()))