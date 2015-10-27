#!/bin/bash
set -e

# Stop services
# The reason to stop services in start script is that CodeDeploy always uses previous version's stop script, which means
# if there is a bug to fail the stop script, CodeDeploy deployment will forever fail till manual intervention.
/opt/wdr/env/instand/instand-service/target/stork/bin/instand-service --stop

# Kill screen sessions which should only be used to run ionic serve
PID=`screen -ls | grep ionic | cut -d. -f1 | awk '{print $1}'`
if [ -n "${PID}" ] ; then echo $PID | xargs kill; fi

# Start services
/opt/wdr/env/instand/instand-service/target/stork/bin/instand-service --start

# Temporary solution to start ionic serve
PWD=$(pwd)
cd /opt/wdr/env/instand/instand-mobile
yum -y install nodejs npm --enablerepo=epel
npm install -g cordova ionic
screen -S ionic -d -m -L ionic serve --address 0.0.0.0 --port 8100 --nolivereload
cd $PWD
