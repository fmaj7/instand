#!/bin/bash
set -e
/opt/wdr/env/instand/instand-service/target/stork/bin/instand-service --start

# Temporary solution to start ionic serve
PWD=$(pwd)
cd /opt/wdr/env/instand/instand-mobile
yum -y install nodejs npm --enablerepo=epel
npm install -g cordova ionic
screen -d -m -L ionic serve --address 0.0.0.0 --port 8100 --nolivereload
cd $PWD
