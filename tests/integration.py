#!/usr/bin/env python3

import json, subprocess

om2mHost = "http://om2m.sensivision.co:8080"
om2mAuth = "admin:admin"
om2mRoot = "/in-cse-id"
om2mTarget = "mn-pi"

try:
	print("GET '/~%s?rcn=5&lvl=1'\n" % (om2mRoot))
	response1 = json.loads(subprocess.check_output("curl -s '%s/~/in-cse-id?rcn=5&lvl=1' -H 'Accept: application/json' -H 'X-M2M-Origin: %s'" % (om2mHost, om2mAuth), shell=True))
		
	found = False
	mnpiCSR = ""
	for element in response1["m2m:cb"]["ch"]:
		if element["nm"] == om2mTarget:
			found = True
			mnpiCSR = element["val"]
			break

	if not found:
		raise ValueError("mn-pi missing")

	print(mnpiCSR + " discovered.\n")

	print("GET '/~%s?rcn=5&lvl=1'\n" % (mnpiCSR))
	response2 = json.loads(subprocess.check_output("curl -s '%s/~%s?rcn=5&lvl=1' -H 'Accept: application/json' -H 'X-M2M-Origin: %s'" % (om2mHost, mnpiCSR, om2mAuth), shell=True))
		
	mnpiCSI = response2["m2m:csr"]["csi"]

	print(mnpiCSI + " identified.\n")

	print("GET '/~%s?rcn=5&lvl=1'\n" % (mnpiCSI))
	response3 = json.loads(subprocess.check_output("curl -s '%s/~%s?rcn=5&lvl=1' -H 'Accept: application/json' -H 'X-M2M-Origin: %s'" % (om2mHost, mnpiCSI, om2mAuth), shell=True))
		
	expected = ["accelerometer", "camera", "compass", "cpu", "disk", "gyroscope", "humidity", "memory", "null", "orientation", "one", "pressure", "process", "quality", "rand", "temperature", "time", "uptime", "wifi", "zero"]

	for element in response3["m2m:cb"]["ch"]:
		if element["nm"] in expected:
			print(element["nm"] + " found.")
			expected.remove(element["nm"])

	if len(expected) > 0:
		raise ValueError("Missing: " + str(expected))

	print("\nEverything found!\n")

	testable = ["accelerometer", "camera", "compass", "cpu", "disk", "gyroscope", "humidity", "memory", "null", "orientation", "one", "pressure", "process", "quality", "rand", "temperature", "time", "uptime", "wifi", "zero"]

	for element in response3["m2m:cb"]["ch"]:

		if element["nm"] in testable:
			print("GET '/~%s?rcn=5&lvl=1'" % (element["val"]))
			response4 = json.loads(subprocess.check_output("curl -s '%s/~%s?rcn=5&lvl=1' -H 'Accept: application/json' -H 'X-M2M-Origin: %s'" % (om2mHost, element["val"], om2mAuth), shell=True))
						
			if response4["m2m:ae"]["ch"][0]["nm"] != "DESCRIPTOR":
				raise ValueError(element["nm"] + " is missing descriptor!")

			print("GET '/~%s?rcn=5&lvl=1'" % (response4["m2m:ae"]["ch"][0]["val"]))
			response5 = json.loads(subprocess.check_output("curl -s '%s/~%s?rcn=5&lvl=1' -H 'Accept: application/json' -H 'X-M2M-Origin: %s'" % (om2mHost, response4["m2m:ae"]["ch"][0]["val"], om2mAuth), shell=True))
						
			if len(response5["m2m:cnt"]["ch"]) == 0:
				raise ValueError(element["mn"] + " is missing content instance")

			print("GET '/~%s?rcn=5&lvl=1'" % (response5["m2m:cnt"]["ch"][0]["val"]))
			response6 = json.loads(subprocess.check_output("curl -s '%s/~%s?rcn=5&lvl=1' -H 'Accept: application/json' -H 'X-M2M-Origin: %s'" % (om2mHost, response5["m2m:cnt"]["ch"][0]["val"], om2mAuth), shell=True))
						
			if len(response6["m2m:cin"]["con"]) == 0:
				raise ValueError(element["mn"] + " is missing actions")

			print("GET '/~%s/%s/%s?op=get&amp;sensorid=%s?rcn=5&lvl=1'" % (mnpiCSI, om2mTarget, element["nm"], element["nm"]))
			response7 = json.loads(subprocess.check_output("curl -s '%s/~%s/mn-pi/%s?op=get&amp;sensorid=%s?rcn=5&lvl=1' -H 'Accept: application/json' -H 'X-M2M-Origin: %s'" % (om2mHost, mnpiCSI, element["nm"], element["nm"], om2mAuth), shell=True))

			print(element["nm"] + " validated!\n")

	print("***\n\nTest Success!!!")
except Exception as e:
	print(e)
	print("\n***\n\nTEST FAILED!!!!!!!!!!!!")
