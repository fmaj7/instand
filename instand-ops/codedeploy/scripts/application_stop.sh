#!/bin/bash
/opt/wdr/env/instand/instand-service/target/stork/bin/instand-service --stop

# Kill screen sessions which should only be used to run ionic serve
PID=`screen -ls | grep ionic | cut -d. -f1 | awk '{print $1}'`
if [ -n "${PID}" ] ; then echo $PID | xargs kill; fi
