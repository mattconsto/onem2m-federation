import subprocess

# Used, Total, Percentage
print(subprocess.check_output("free -h | grep Mem | awk '{print substr($3, 1, length($3)-1) \",\" substr($2, 1, length($2)-1) \",\" ($3 / $2 * 100)}'", shell=True))
