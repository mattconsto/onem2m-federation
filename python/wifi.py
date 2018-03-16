import subprocess

subprocess.check_output("wpa_cli -i wlan0 scan")
print(subprocess.check_output("wpa_cli -i wlan0 scan_results | wc -l"))
