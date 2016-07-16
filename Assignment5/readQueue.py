import boto3
import time
import pymysql
import pymysql.cursors
import json

from boto.sqs.message import Message

import threading

db = pymysql.connect(host='localhost',
                             user='root',
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
        print(message['Parameters'])
        if message['Parameters']==None:
            message['Parameters'] = "NULL"

        message['Time'] = time.strftime("%Y-%m-%d %H:%M:%S", time.gmtime(float(message['Time']) / 1000))
        print(message['Time'])

        #cmd = "INSERT INTO audit_log (request_ip_address,response_code,request_duration_ms,url,parameters,timestamp,request_type) VALUES(\""+str(message["IPAddress"])+ "\","+str(message["ResponseCode"])+","+str(message["ExecuteTime"])+",\""+message["RequestURL"]+"\",\""+message["Parameters"]+"\","+str(message["Time"])+",\""+message["RequestType"]+"\");"
        k = "INSERT INTO audit_log (request_ip_address,response_code,request_duration_ms,url,parameters,timestamp,request_type) VALUES(\""+str(message["IPAddress"]) + "\","+str(message["ResponseCode"])+","+str(message["ExecuteTime"])+",\""+message["RequestURL"]+"\","+str(message["Parameters"])+",\""+str(message["Time"])+"\",\""+message["RequestType"]+"\");"
        print k
        cursor.execute(k)
        db.commit()
        message1.delete()
printit()

while True:
    import time
    printit()
    time.sleep(5)

