OneM2M Federation: Multi-Vendor Internet-of-Things
==================================================

By Adib Dezfouli, Altay Adademir, Dennis Parchkov, Edmond Ipindamitan, and Matthew Consterdine.

## Abstract

For the mass deployment of the Internet of Things to be a success, a global standard for machine to machine communication needs to become established. This report explores the oneM2M standard for Machine to Machine communication, researching its capabilities, how to make use of it, and ultimately builds systems upon it. Using them, data streaming, live video, and federation are put to the test.

This project is a oneM2M research project, with InterDigital as the client. They have created the oneTRANSPORT data marketplace, and this report with federate with their system, to demonstrate the power of oneM2M.

## Photo

[![](/latex/images/okpicture.jpg)](https://consto.uk/2018/03/16/onem2m-federation-multi-vendor-internet-of-things)

## Folders

* abstract - Abstract for compiling into a booklet.
* bash - Installation scripts.
* icons - Pixel art for the LED matrix.
* latex - Report source code.
* om2m - Eclipse OM2M Sensivision plug-in.
* openmtc - OpenMTC video streaming plug-in.
* poster - A1 project poster
* presentation - Project presentation
* python - Python sensor scripts.
* tests - Python unit and integration tests.
* video - Video viewer, serve via apache.

## Requirements

* Hardware: At least one Raspberry Pi with Sense HAT, and at least one cloud server.
* Operating System: Raspbian-Lite on the gateway, anything on the server.
* Packages: openvpn easy-rsa oracle-java8-jdk oracle-java8-jre sense-hat apache.
