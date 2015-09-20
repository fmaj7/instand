#!/bin/bash
set -e
/opt/wdr/env/instand/instand-service/target/stork/bin/instand-service --start

PWD=$(pwd)
cd /opt/wdr/env/instand/instand-mobile
sudo yum install nodejs npm --enablerepo=epel
sudo npm install -g cordova ionic
ionic serve --address 0.0.0.0 --port 8100
cd $PWD
