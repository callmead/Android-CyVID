import requests, json

BASE = "http://127.0.0.1:5000/"

# Insert document 
# response = requests.get(BASE + 'CyVID_functions/add/test_db/{"_id":"12", "data":"Test 12"}')
# print(response.json(), '\n')

# Query documents # query can be any key value within the document
# response = requests.get(BASE + 'CyVID_functions/query/test_db/{"data":"Test 12"}')
# print(response.json(), '\n')

# Query all
# response = requests.get(BASE + 'CyVID_functions/query/test_db/{"all":"docs"}')
# print(response.json(), '\n')

# update document # _rev is required for updating a record, that has to come with data
# response = requests.get(BASE + 'CyVID_functions/update/test_db/{"_id":"11", "data":"new value", "_rev": "3-cf6bfa49a8a8665f7e90580b7dd85cba"}')
# add applications to a node
# response = requests.get(BASE + 'CyVID_functions/addapps/test_db/{"_id":"0", "_rev": "20-d41f7f5748fd2c5e568a4d9272050bd0", "applications": "Test 6.0"}')
# print(response.json(), '\n')

# Delete document # doc_id
# response = requests.get(BASE + 'CyVID_functions/delete/test_db/{"_id":"11", "_rev": "6-85c2acee6d155b7eeecfa0e05b627385"}')
# print(response.json(), '\n')

# response = requests.get(BASE + 'CVEs/Acrobat Reader 10.0')
# print(response.json(), '\n')

# Authenticate # /Authenticate/<string:userpass>/
# response = requests.get(BASE + 'Authenticate/{"user":"admin", "pass":"password"}')
# insert user
# response = requests.get(BASE + 'CyVID_functions/add/cyvid_users/{"user":"admin", "pass":"password"}')
# query all users
# response = requests.get(BASE + 'CyVID_functions/query/cyvid_users/{"all":"docs"}')
# print(response.json(), '\n')

# Analysis # single node
# response = requests.get(BASE + 'CyVID_analysis/{"HostIP": "192.168.0.113"}')
# all nodes
# response = requests.get(BASE + 'CyVID_analysis/{"all":"nodes"}')
# print(response.json(), '\n')

# Analysis # all nodes
# response = requests.get(BASE + 'CyVID_analysis/{"all":"nodes"}')
# print(response.json(), '\n')
