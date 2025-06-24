import json
import boto3

sns_client = boto3.client('sns')
codepipeline_client = boto3.client('codepipeline')

SNS_TOPIC_ARN = 'arn:aws:sns:us-east-1:418295691381:PipelineNotifications:77ea151e-50d0-4c67-a90f-d509cca0a3fa'

def lambda_handler(event, context):
    print(event)
    detail = event.get('detail', {})
    pipeline_name = detail.get('pipeline', 'unknown-pipeline')
    execution_id = detail.get('execution-id', 'unknown-execution')
    state = detail.get('state', 'UNKNOWN')
    failed_stage = 'Not determined'

    if state == 'FAILED':
        try:
            response = codepipeline_client.get_pipeline_execution(
                pipelineName=pipeline_name,
                pipelineExecutionId=execution_id
            )
            for stage in response['pipelineExecution']['stageStates']:
                latest = stage.get('latestExecution', {})
                if latest.get('status') == 'Failed':
                    failed_stage = stage.get('stageName')
                    print(failed_stage)
                    break
        except Exception as e:
            failed_stage = f"Error fetching stage: {str(e)}"

        message = (
            f"❌ Pipeline '{pipeline_name}' has FAILED.\n"
            f"Execution ID: {execution_id}\n"
            f"Failed Stage: {failed_stage}\n"
            f"State: {state}"
        )

        sns_client.publish(
            TopicArn=SNS_TOPIC_ARN,
            Subject=f"[FAILED] Pipeline {pipeline_name}",
            Message=message
        )

    return {
        'statusCode': 200,
        'body': json.dumps('Failure notification processed')
    }

