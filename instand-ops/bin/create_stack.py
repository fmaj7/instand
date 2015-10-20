#!/usr/bin/env python
import os
import argparse
import boto3
import glob

SCRIPT_PATH = os.path.dirname(__file__)
TEMPLATE_PATH = os.path.join(SCRIPT_PATH, "..", "stack-templates")
ROOT_TEMPLATE_FILE_NAME = "instand.template"


def upload_template_to_s3(stack_prefix):
    s3 = boto3.resource("s3")
    bucket = s3.Bucket("cloud-formation-stack-template")

    template_file_pattern = os.path.join(TEMPLATE_PATH, "*.template")
    template_files = glob.glob(template_file_pattern)

    print template_files

    for template_file in template_files:
        bucket.upload_file(Filename=template_file, Key="{}/{}".format(stack_prefix, os.path.basename(template_file)))


def create_cloud_formation_stack(stack_prefix, template_url):
    cfn = boto3.client("cloudformation")
    response = cfn.create_stack(
        StackName='string',
        TemplateURL='string',
        Parameters=[
            {
                'ParameterKey': 'StackPrefix',
                'ParameterValue': stack_prefix,
            },
            {
                'ParameterKey': 'TemplateURL',
                'ParameterValue': template_url,
            }
        ],
        DisableRollback=True,
        OnFailure='DO_NOTHING',
    )
    print response


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
