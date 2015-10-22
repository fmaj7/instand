#!/usr/bin/env python
from time import sleep, time
from sys import stdout


def wait_until_true(msg, func, wait_interval=3):
    stdout.write(msg)
    stdout.flush()
    start_time = time()
    while not func():
        stdout.write(".")
        stdout.flush()
        sleep(wait_interval)
    time_taken = time() - start_time
    print "{} seconds for {}".format(time_taken, msg)
