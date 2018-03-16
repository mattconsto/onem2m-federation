import subprocess

output = subprocess.check_output("wpa_cli -i wlan0 signal level").split("\n")

for line in output:
	if line.startswith("RSSI"):
		print(line.split("=")[1])
		exit(0)

# If signal strength is not found
print(-100000)
exit(1)
