import subprocess

# Used, Total, Percentage
print(subprocess.check_output("grep 'cpu ' /proc/stat | awk '{usage=($2+$4)*100/($2+$4+$5)} END {print usage}'", shell=True))
