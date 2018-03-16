import subprocess
from sense_hat import SenseHat

calibration = 5.466 # TODO Lazyily taken from a guide, needs to be actually measured.

cpu_temp = subprocess.check_output("vcgencmd measure_temp")
cpu_temp = float(cpu_temp.split("=")[1].split("'")[0])
raw_temp = SenseHat().get_temperature()
cal_temp = raw_temp - ((cpu_temp - raw_temp) / calibration)
print(cal_temp)
