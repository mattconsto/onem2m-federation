#!/bin/sh

echo "Starting Gateway"
cd /home/pi/gateway
rm -r database log.out log.err
java -jar -ea -Declipse.ignoreApp=true -Dosgi.clean=true -Ddebug=true plugins/org.eclipse.equinox.launcher_1.3.0.v20140415-2008.jar -console -noExit
