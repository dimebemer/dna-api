import boto3


def lambda_handler(event, context):
    # print(f"Received event: {event}")

    db = boto3.resource('dynamodb')
    stats = db.Table('DnaStats')

    mutant = 0
    human = 0

    for record in event['Records']:
        if record['eventName'] == "INSERT":
            dna = record['dynamodb']['NewImage']
            modifier = 1
        elif record['eventName'] == "REMOVE":
            dna = record['dynamodb']['OldImage']
            modifier = -1
        else:
            continue

        if dna["category"]["S"] == "SIMIAN":
            mutant += modifier
        elif dna["category"]["S"] == "HUMAN":
            human += modifier

    # print(f"Updating - Simians: {mutant} | Humans: {human}")

    response = stats.update_item(
        Key={
            'statsId': 'latest'
        },
        UpdateExpression='set countMutantDna = countMutantDna + :mutant, countHumanDna = countHumanDna + :human',
        ExpressionAttributeValues={
            ':mutant': mutant,
            ':human': human
        },
        ReturnValues="UPDATED_NEW"
    )

    # print(f"Update status: {response}")
