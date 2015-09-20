#!/bin/bash
/opt/wdr/env/instand/instand-service/target/stork/bin/instand-service --start

PWD=$(PWD)
cd /opt/wdr/env/instand/instand-mobile
ionic serve --address 0.0.0.0 --port 8100
cd $PWD
