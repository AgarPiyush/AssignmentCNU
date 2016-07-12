import boto3
import time
import pymysql
import pymysql.cursors
import json

from boto.sqs.message import Message

import threading

db = pymysql.connect(host='aline-cnu-insights-dev.czuocyoc6awe.us-east-1.rds.amazonaws.com',
                             user='pagarwal',
                             password='pagarwal',
                             db='cnu2016_pagarwal',
                             charset='utf8mb4',
                             cursorclass=pymysql.cursors.DictCursor)

sqs = boto3.resource('sqs')

queue = sqs.get_queue_by_name(QueueName='cnu2016_pagarwal_assignment05')

cursor = db.cursor()
cursor.execute("SHOW TABLES")
data = cursor.fetchone()
print ("Database version : %s " % data)


def printit():
    print "Hello, World!"
    for message1 in queue.receive_messages() :
        body = message1.body
        message = json.loads(body)
        print(message)
        CMD = "INSERT INTO AuditLog (ipAddress,ExecuteTime,RequestTime,ResponseCode,RequestUrl) VALUES(\"" + \
              str(message["IP Address"]) + "\",\"" + str(message["ExecuteTime"]) + "\",\"" + str(message["Time"]) \
              + "\",\"" + str(message["Response code"]) + "\",\"" + str(message["Request URL"]) + "\");"
        print CMD
        cursor.execute(CMD)
        db.commit()
        message1.delete()
printit()

while True:
    import time
    printit()
    time.sleep(5)

