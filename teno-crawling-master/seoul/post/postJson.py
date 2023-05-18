import requests
import json

url = "http://ec2-3-36-137-100.ap-northeast-2.compute.amazonaws.com:8080/courts"


def post_json(json_data):
    response = requests.post(url, data=json.dumps(json_data), headers ={'content-type' : 'application/json'})
