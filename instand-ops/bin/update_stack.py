#!/usr/bin/env python
import argparse

import boto3

from stack_utilities import wait_until_stack_succeeds, get_template_root_url, get_template_url, add_args, \
    upload_template_to_s3


def update_stack(stack_name):
    upload_template_to_s3(stack_name)
    cfn_client = boto3.client("cloudformation")
    response = cfn_client.update_stack(
        StackName=stack_name,
        TemplateURL=get_template_url(stack_name),
        UsePreviousTemplate=False,
        Parameters=[
            {
                'ParameterKey': 'StackPrefix',
                'ParameterValue': stack_name,
            },
            {
                'ParameterKey': 'TemplateRootUrl',
                'ParameterValue': get_template_root_url(stack_name),
            }
        ],
        Capabilities=[
            'CAPABILITY_IAM',
        ]
    )
    if response['ResponseMetadata']['HTTPStatusCode'] == 200:
        wait_until_stack_succeeds(cfn_client, stack_name)
    else:
        print "Stack failed to create. Response {}".format(response)


def main():
    parser = argparse.ArgumentParser()
    add_args(parser)
    arguments = parser.parse_args()
    update_stack(arguments.stack_name)


if __name__ == '__main__':
    main()
