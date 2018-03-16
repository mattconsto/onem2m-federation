#!/usr/bin python3

import re, subprocess, sys

timeoutLimit = 5
scriptPath = "../python/"
processes = ["disk", "one", "process", "wifi", "accelerometer", "compass", "orientation", "quality", "temperature", "zero", "cpu", "gyroscope", "memory", "rand", "time", "camera", "humidity", "null", "pressure", "uptime"]
successCount = 0

for process in processes:
	success = True

	# Test single value
	try:
		output = subprocess.check_output(["python3", scriptPath + process + ".py"], timeout=timeoutLimit).decode().strip()
		if not re.match(r'^[A-Za-z0-9\+\/,:\.=|\[\]\-]+$', output, flags = re.MULTILINE):
			print(" - FAILED %s produced invalid output: %s" % (process, output))
			success = False
	except subprocess.CalledProcessError as e:
		print(" - FAILED %s call (exit code = %d)" % (process, e.returncode))
		success = False
	except subprocess.TimeoutExpired:
		print(" - FAILED %s timeout" % (process))
		success = False
	except Exception as e:
		print(" - FAILED %s:" % (e.message))
		success = False

	# Test stream
	try:
		subprocess.run(["python3", scriptPath + process + ".py", "1"], timeout=3, stdout=subprocess.PIPE)
		# Always times out
		print(" - FAILED %s didn't stream." % (process))
		success = False
	except subprocess.CalledProcessError as e:
		print(" - FAILED %s call (exit code = %d) streaming" % (process, e.returncode))
		success = False
	except subprocess.TimeoutExpired:
		# Good!
		pass
	except Exception as e:
		print(" - FAILED %s:" % (e.message))
		success = False

	if success:
		successCount += 1
		print("Success: " + process)
	else:
		print("FAILURE: " + process)

print("\n***\n\n%d / %d succeeded." % (successCount, len(processes)))
