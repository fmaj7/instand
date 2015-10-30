#!/usr/bin/env python
import os
import glob
import inspect
import pprint

import boto3

from utilities import wait_until_true

SCRIPT_PATH = os.path.dirname(__file__)
TEMPLATE_PATH = os.path.join(SCRIPT_PATH, "..", "stack-templates")
ROOT_TEMPLATE_FILE_NAME = "instand.template"
BUCKET_NAME = "cloud-formation-stack-template"


def get_template_root_url(stack_name):
    return "https://s3.amazonaws.com/{}/{}".format(BUCKET_NAME, stack_name)


def get_template_url(stack_name):
    return "{}/{}".format(get_template_root_url(stack_name), ROOT_TEMPLATE_FILE_NAME)


def add_args(parser):
    parser.add_argument("stack_name", help='stack name')


def upload_template_to_s3(stack_prefix):
    s3 = boto3.resource("s3")
    bucket = s3.Bucket(BUCKET_NAME)

    template_file_pattern = os.path.join(TEMPLATE_PATH, "*.template")
    template_files = glob.glob(template_file_pattern)

    for template_file in template_files:
        bucket.upload_file(Filename=template_file, Key="{}/{}".format(stack_prefix, os.path.basename(template_file)))


def has_stack_action_successfully_completed(cfn_client, stack_name):
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
    caller_name = inspect.stack()[1][3]
    if "update" in caller_name:
        action_msg_ing = "Updating"
        action_msg_ed = "updated"
    else:
        action_msg_ing = "Creating"
        action_msg_ed = "created"
    wait_until_true("{} stack".format(action_msg_ing), has_stack_action_successfully_completed(cfn_client, stack_name))
    print "Stack {} has been {}.".format(stack_name, action_msg_ed)


def print_stack(cfn_client, stack_name):
    describe_response = cfn_client.describe_stacks(
        StackName=stack_name
    )
    pprint.pprint(describe_response['Stacks'][0]['Outputs'])
