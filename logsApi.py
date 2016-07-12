from flask import Flask
from flask_sqlalchemy import SQLAlchemy
import json
import time
from flask import request
from datetime import datetime
from flask import jsonify


app = Flask(__name__)

app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://pagarwal:pagarwal@aline-cnu-insights-dev-cluster.cluster-czuocyoc6awe.us-east-1.rds.amazonaws.com:3306/cnu2016_pagarwal'
db = SQLAlchemy(app)

class AuditLog(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    request_ip_address = db.Column(db.String(256))
    request_duration_ms = db.Column(db.Integer)
    timestamp = db.Column(db.DateTime)
    url = db.Column(db.String(256))
    parameters = db.Column(db.String(256))
    response_code = db.Column(db.Integer)
    request_type = db.Column(db.String(256))


@app.route('/api/auditLogs/', methods=['GET'])
def sort_logs():
    print "inside apu"
    global logs

    offset = request.args.get('offsetNo', 0)
    limit = (int)(request.args.get('limit', 10))
    if limit > 10:
        limit = 10
    print limit
    print "limit"
    print limit
    fmt = '%m/%d/%YT%H:%M:%S'

    logs = AuditLog.query.order_by(AuditLog.timestamp.desc())
    if 'startTime' in request.args and 'endTime' in request.args:
        startTime = datetime.strptime(request.args['startTime'], fmt)
        endTime = datetime.strptime(request.args['endTime'], fmt)
        logs = AuditLog.query.filter(AuditLog.timestamp.between(startTime,endTime)).order_by(AuditLog.timestamp.desc()).limit(limit).offset(offset)

    elif 'startTime' in request.args:
        print "start time"
        startTime = datetime.strptime(request.args['startTime'], fmt)
        print startTime
        logs = AuditLog.query.filter(AuditLog.timestamp >= startTime).order_by(AuditLog.timestamp.desc()).limit(limit).offset(offset)

    elif 'endTime' in request.args:
        print "inside end"
        endTime = datetime.strptime(request.args['endTime'], fmt)
        print endTime
        logs = AuditLog.query.filter(AuditLog.timestamp <= endTime).order_by(AuditLog.timestamp.desc()).limit(limit).offset(offset)
    else:
        logs = AuditLog.query.order_by(AuditLog.timestamp.desc()).limit(limit).offset(offset)



    dlist = []
    for x in logs:
        dict_obj = dict()
        dict_obj["id"] = x.id
        dict_obj["timestamp"] = x.timestamp.strftime("%m/%d/%YT%H:%M:%S");
        dict_obj["response_code"] = x.response_code
        dict_obj["request_type"] = x.request_type
        dict_obj["response_duration_ms"] = x.request_duration_ms
        dict_obj["parameters"] = x.parameters
        dict_obj["url"] = x.url
        dlist.append(dict_obj)
    dlist2 = dict()
    dlist2["body"] = dlist
    return jsonify(dlist2)


def alc2json(row):
    return dict([(col, (getattr(row,col))) for col in row.__table__.columns.keys()])


