import subprocess

# Used, Total, Percentage
output = subprocess.check_output("df -h | grep /dev/root | awk '{print substr($3, 1, length($3)-1) \",\" substr($4, 1, length($4)-1) \",\" substr($5, 1, length($5)-1)}'", shell=True))

if output != "":
	print(output)
else:
	# as this script checks for /dev/boot, t's only garunteed to work an a RPi
	print("0,0,0")
	exit(1)
