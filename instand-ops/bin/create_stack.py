#!/usr/bin/env python
import os
import argparse
import glob

import boto3

from stack_utilities import wait_until_stack_succeeds

SCRIPT_PATH = os.path.dirname(__file__)
TEMPLATE_PATH = os.path.join(SCRIPT_PATH, "..", "stack-templates")
ROOT_TEMPLATE_FILE_NAME = "instand.template"
BUCKET_NAME = "cloud-formation-stack-template"


def get_template_root_url(stack_prefix):
    return "https://s3.amazonaws.com/{}/{}".format(BUCKET_NAME, stack_prefix)


def get_template_url(stack_prefix):
    return "{}/{}".format(get_template_root_url(stack_prefix), ROOT_TEMPLATE_FILE_NAME)


def get_stack_name(stack_prefix):
    return "{}-stack".format(stack_prefix)


def upload_template_to_s3(stack_prefix):
    s3 = boto3.resource("s3")
    bucket = s3.Bucket(BUCKET_NAME)

    template_file_pattern = os.path.join(TEMPLATE_PATH, "*.template")
    template_files = glob.glob(template_file_pattern)

    for template_file in template_files:
        bucket.upload_file(Filename=template_file, Key="{}/{}".format(stack_prefix, os.path.basename(template_file)))


def create_cloud_formation_stack(stack_prefix):
    stack_name = get_stack_name(stack_prefix)
    cfn_client = boto3.client("cloudformation")
    response = cfn_client.create_stack(
        StackName=stack_name,
        TemplateURL=get_template_url(stack_prefix),
        Parameters=[
            {
                'ParameterKey': 'StackPrefix',
                'ParameterValue': stack_prefix,
            },
            {
                'ParameterKey': 'TemplateRootUrl',
                'ParameterValue': get_template_root_url(stack_prefix),
            }
        ],
        Capabilities=[
            'CAPABILITY_IAM',
        ],
        OnFailure='DO_NOTHING',
    )
    if response['ResponseMetadata']['HTTPStatusCode'] == 200:
        wait_until_stack_succeeds(cfn_client, stack_name)
    else:
        print "Stack failed to create. Response {}".format(response)


def create_stack(stack_prefix):
    upload_template_to_s3(stack_prefix)
    create_cloud_formation_stack(stack_prefix)


def add_args(parser):
    parser.add_argument("stack_prefix", help='stack name prefix')


def main():
    parser = argparse.ArgumentParser()
    add_args(parser)
    arguments = parser.parse_args()
    create_stack(arguments.stack_prefix)


if __name__ == '__main__':
    main()
