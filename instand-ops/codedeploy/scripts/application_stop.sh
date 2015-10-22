#!/bin/bash
/opt/wdr/env/instand/instand-service/target/stork/bin/instand-service --stop

# Kill all screen sessions which should only be used to run ionic serve
screen -ls | grep pts | cut -d. -f1 | awk '{print $1}' | xargs kill || true
