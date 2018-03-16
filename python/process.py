import subprocess

# This count includes the python, the shell, ps, tail, and wc!
print(int(subprocess.check_output("ps | tail -n +2 | wc -l", shell=True)))
