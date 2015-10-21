#!/usr/bin/env python
from utilities import wait_until_true


def is_stack_created_successfully(cfn_client, stack_name):
    def wait():
        response = cfn_client.describe_stacks(StackName=stack_name)
        if "Stacks" in response:
            for stack in response['Stacks']:
                if "StackStatus" in stack:
                    stack_status = stack["StackStatus"]
                    if stack_status.endswith("ROLLBACK_COMPLETE"):
                        raise Exception("Stack has been rolled back")
                    elif stack_status.endswith("COMPLETE"):
                        return True
                    elif stack_status.endswith("FAILED"):
                        raise Exception("Stack has failed")
                    else:
                        return False
                else:
                    raise Exception("{}".format(response))
        else:
            raise Exception("{}".format(response))
    return wait


def wait_until_stack_succeeds(cfn_client, stack_name):
    wait_until_true("Creating stack", is_stack_created_successfully(cfn_client, stack_name))
    print "Stack {} has been created.".format(stack_name)
