from flask import Flask, jsonify
from flask_restful import Api, Resource, reqparse, abort, fields, marshal_with
from flask_sqlalchemy import SQLAlchemy
import couchdb2, json, config
import pandas as pd
from datetime import datetime
from multiprocessing.pool import ThreadPool
        
app = Flask(__name__)
api = Api(app)
server = couchdb2.Server("http://%s:%s@%s:%s" % (config.db_user, config.db_pass, config.db_host, config.db_port))
if server.up(): # if server is up and ready
    x, y = 'CouchDB ('+str(server.version)+') up and running with ('+str(len(server))+') user defined databases. Databases include:', ''
    for dbname in server:
        y = y + ' ' + str(dbname)
    x = x + y
else:
    x = 'CouchDB not reachable'
print('status:', x)

# Check the couchdb server atatus if up and running.
class Home(Resource): 
    def get(self):
        return jsonify({"message": "CyVID API Running"})
        
# Check the couchdb server atatus if up and running.
class CyVID(Resource): 
    def get(self):
        return jsonify({"message": x})

class Authenticate(Resource):
    def get(self, attached_data):
        db_name = 'cyvid_users'
        db = server[db_name] # select database
        attached_doc = json.loads(attached_data) # make json of attached data
        
        for doc in db:
            if doc['user']==attached_doc['user']: # if user = provided username
                if doc['pass']==attached_doc['pass']:
                    print('Authentication: Sucessful')
                    return {"message":"sucess"}
                else:
                    print('Authentication: Invalid password')
                    return {"message":"invalid password"}
            else:
                print('Authentication: Invalid username')
                return {"message":"invalid user"}

# Add/Update/Delete/Query CouchDB Server        
class CyVID_functions(Resource):            
    def get(self, db_operation, db_name, attached_data):
        # Check if DB exists
        if db_name in server: # already existing database # if db.exists():
            print('\n*** Valid database ('+db_name+') selected.')
            db = server[db_name] # select database
            attached_doc = json.loads(attached_data) # make json of attached data
            
            # Function add
            if (db_operation == "add"):
                print('\n*** Inserting doc:\n'+json.dumps(attached_doc, indent=4))
                db.put(attached_doc)
                return {"operation": db_operation, "database": db_name, "doc": json.loads(attached_data), "status": "sucessfully added"}
     
            # Function update
            elif (str(db_operation) == "update"):
                print('\n*** Updating doc:\n'+json.dumps(attached_doc, indent=4))
                db.put(attached_doc)
                return {"operation": db_operation, "database": db_name, "attached_data": json.loads(attached_data), "status": "sucessfully updated"}
            
            # Function delete
            elif (db_operation == "delete"):
                print('\n*** Deleting doc:\n'+json.dumps(attached_doc, indent=4))
                db.delete(attached_doc)
                return {"operation": db_operation, "database": db_name, "attached_data": json.loads(attached_data), "status": "sucessfully deleted"}
            
            elif(db_operation == "addapps"):
                #print('\n*** Updating doc:\n'+json.dumps(attached_doc, indent=4))
                if attached_doc['_id'] in db:
                    doc = db[attached_doc['_id']]
                    if 'applications' in doc:
                        print(doc['applications'])
                        #print('Found applications', len(doc['applications']), type(doc['applications']))
                        doc['applications'].append(attached_doc['applications'])
                        print(doc['applications'])
                        new_doc = {"_id":doc['_id'], "_rev":doc['_rev'],"HostName":doc['HostName'], "HostIP":doc['HostIP'], "HostGateway":doc['HostGateway'], "HostOS":doc["HostOS"], "applications":doc['applications']}
                    else:
                        print('add applications')
                        print('add', attached_doc['applications'])
                        new_doc = {"_id":doc['_id'], "_rev":doc['_rev'], "HostName":doc['HostName'], "HostIP":doc['HostIP'], "HostGateway":doc['HostGateway'], "HostOS":doc["HostOS"], "applications":[attached_doc['applications']]}

                    db.put(new_doc)
                    return {"operation": db_operation, "database": db_name, "attached_data": json.loads(attached_data), "status": "sucessfully added"}
                else:
                    print('\n*** Document with _id not found')
                    return {"operation": db_operation, "database": db_name, "attached_data": json.loads(attached_data), "status": "document with _id not found"}
                #print(attached_doc['applications'], len(attached_doc['applications']))
                return {"a": "a"}        

            # Function query
            elif (db_operation == "query"):
                if (len(db) >= 1): # if documents exist in db then query
                    result = [] # documents dictionary
                    if attached_data == '{"all":"docs"}': # if requested all docs
                        for doc in db:
                            print('\n*** Found doc with rev:', doc['_rev'])
                            print(json.dumps(doc, indent=4))
                            result.append(doc)    
                        return {"operation": db_operation, "database": db_name, "attached_data": json.loads(attached_data), "status": "found "+str(len(result)), "doc(s)": result}                
                    
                    else: # find docs with keyword
                        for key, value in attached_doc.items():
                            print('\n*** Looking for key:', key, 'with value:', value)
                        
                            doc_found=False
                            for doc in db:
                                try:
                                    if doc[key]==value: # if document has key value pair
                                        print('\n*** Found doc with rev:', doc['_rev'])
                                        print(json.dumps(doc, indent=4))
                                        doc_found=True
                                        result.append(doc) # Add doc to result dictionary
                                        
                                except KeyError: # ignore documents that do not have key
                                    pass
                            if doc_found==False: 
                                print('\n*** Document not found!')
                                return {"operation": db_operation, "database": db_name, "attached_data": json.loads(attached_data), "status": "no documents found in "+db_name}
                            else:
                                return {"operation": db_operation, "database": db_name, "attached_data": json.loads(attached_data), "status": "found "+str(len(result)), "doc(s)": result}
                else:
                    return {"operation": db_operation, "database": db_name, "attached_data": json.loads(attached_data), "status": "no documents found in "+db_name}
            else:
                print('\n*** Invalid operation ('+db_operation+') selected.')
                return {"operation": db_operation, "database": db_name, "attached_data": json.loads(attached_data), "status": "selected operation not found"}
        else: # when database not found
            print('\n*** Invalid database ('+db_name+') selected.')
            return {"operation": db_operation, "database": db_name, "attached_data": json.loads(attached_data), "status": "selected database not found"}   

# CVE lookup for given product
class CVEs(Resource): # resource class will handle get and post
    def get(self, attached_data):
        product = attached_data.replace(" ", "+")
        url = "https://cve.mitre.org/cgi-bin/cvekey.cgi?keyword="+product
        print('Search string:', product, url)
        df_list = pd.read_html(url)

        if (len(df_list)!=5): # len(df_list) # if 5 on MITRE page, then the following code will work.
            print('\n*** Page style changed at MITRE website, reconfigure')

        cves_list = ((df_list[2]['Name']).to_string(index=False).split())
        print('\n*** ' + str(len(cves_list)) + ' vulnerabilities found in '+ attached_data)
        print(cves_list)
        return jsonify({"found": str(len(cves_list))+" vulnerabilities in "+attached_data, "CVE list": cves_list})

# Analyze node(s)
class CyVID_analysis(Resource):
    def get(self, attached_data):
        db_name = 'cyvid_nodes' # nodes database 
        attached_doc = json.loads(attached_data) # make json of attached data
    
        # Check if DB exists
        if db_name in server: # already existing database # if db.exists():
            print('\n*** Valid database ('+db_name+') selected.')
            db = server[db_name] # select database

            if (len(db) >= 1): # if documents exist in db then query
                attached_doc = json.loads(attached_data) # make json of attached data
                
                if attached_data == '{"all":"nodes"}': # analysis for all nodes
                    # Time the execution
                    now = datetime.now()
                    current_time = now.strftime("%H:%M:%S")
                    print("Start Time:", current_time)
                    startTime = datetime.now() # start timer

                    # Extract products from the node document
                    node_product_list=[]
                    for doc in db:
                        node_product_list.append(doc['HostOS'])
                        if len(doc['applications']) >=1: # if there are products in applications
                            node_product_list.extend(doc['applications']) # add applications to the node_product_list

                    # Find CVEs for the found products
                    total_cves = [] # list of total cves for all apps
                    product_to_cve = {} # dictionary to keep product to CVE data
                    products_not_found, cves_not_found = [], [] # list of products and cves for which no informaiton is found.
                    print('\n*** Finding CVE list for products from MITRE ...')

                    # Loop through the products to find vulnerabilities
                    for product in node_product_list:
                        product = product.replace(" ", "+")
                        url = "https://cve.mitre.org/cgi-bin/cvekey.cgi?keyword="+product
                        df_list = pd.read_html(url)
                        if (len(df_list)!=5):
                            print("Page style changed at MITRE website, reconfigure")
                        else: 
                            found_cves = ((df_list[2]['Name']).to_string(index=False).split())
                            if found_cves == ['Series([],', ')']: # if no data is found for product
                                products_not_found.append(product)
                            else: # if CVE list found    
                                if len(found_cves) != 0:
                                    product_to_cve[product] = found_cves # product to CVE mapping
                                    total_cves.extend(found_cves) # add found CVEs in the total_cves list

                    print('Total '+str(len(total_cves))+' CVEs found in '+str(len(node_product_list))+' products where for '+str(len(products_not_found))+' products had no CVEs found on MITRE:\n')
                    print(products_not_found)

                    # CVE to Product mapping
                    cve_to_product = {}
                    for k, v in product_to_cve.items():
                        if isinstance(v, list):
                            for c in v:
                                if c in cve_to_product.keys():
                                    cve_to_product[c].extend([k])
                                else:
                                    cve_to_product[c] = [k]
                        else:
                            if v in cve_to_product.keys():
                                cve_to_product[v].extend(k)
                            else:
                                cve_to_product[v] = [k]
                                
                    # method to return CVE record from the dataset database
                    print('\n*** Finding CVE information from CyVID dataset ...')
                    db_name = 'cyvid_dataset' # change database
                    db = server[db_name] # select database
                    docs = db.get_bulk(total_cves) # extract all documents with ids (total_cves) # docs[i] returns doc i

                    # craft a dataframe (df_all) to hold all data for further processing
                    df_all = pd.DataFrame(columns=['_id', 'cwe_id', 'cwe_desc', 'severity', 'cvss_v2'])
                    # loop through documents to extract specific information to be added in the dataframe df_all
                    for i in range(len(docs)):
                        if (docs[i]!=None):
                            df_all = df_all.append({'_id': docs[i]['_id'], 'cwe_id': docs[i]['cwe_id'], 'cwe_desc': docs[i]['cwe_desc'], 'severity': docs[i]['severity'], 'cvss_v2': docs[i]['cvss_v2']}, ignore_index=True)
                        else:
                            #print(i, total_cves[i], '- No record found, consider updating dataset!')
                            cves_not_found.append(total_cves[i])
                    print('Total '+str(len(df_all))+' CVE records found in CYVID dataset, and '+str(len(cves_not_found))+' not found:\n')
                    print(cves_not_found)

                    x = json.loads(df_all['severity'].value_counts().to_json())
                    y = json.loads(df_all['_id'].value_counts()[:10].to_json())
                    z = json.loads(df_all['cwe_id'].value_counts()[:10].to_json())
                    print('\nAnalysis as follows {Severity}, {Top10 CVEs}, {Top10 CWEs}')
                    print(json.dumps([x, y, z], indent=4))
                    print("\nExecution time: "+str(datetime.now() - startTime))
                    return ([x, y, z])

                else: # analysis for one node
                    # Time the execution
                    now = datetime.now()
                    current_time = now.strftime("%H:%M:%S")
                    print("Start Time:", current_time)
                    startTime = datetime.now() # start timer

                    # Extract products from the node document
                    node_product_list=[]
                    for key, value in attached_doc.items():
                        print('\n*** Looking for key:', key, 'with value:', value, '...')

                        doc_found=False
                        for doc in db:
                            try:
                                if doc[key]==value: # if document has key level with int value 4
                                    doc_found=True
                                    print('Document found!') # print(json.dumps(doc, indent=4))
                                    node_product_list.append(doc['HostOS'])
                                    if len(doc['applications']) >=1: # if there are products in applications
                                        node_product_list.extend(doc['applications']) # add applications to the node_product_list

                            except KeyError: # ignore documents that do not have key
                                pass
                        if doc_found==False: print('Document not found!')

                    # Find CVEs for the found products
                    total_cves = [] # list of total cves for all apps
                    product_to_cve = {} # dictionary to keep product to CVE data
                    products_not_found, cves_not_found = [], [] # list of products and cves for which no informaiton is found.
                    print('\n*** Finding CVE list for products from MITRE ...')

                    # Loop through the products to find vulnerabilities
                    for product in node_product_list:
                        product = product.replace(" ", "+")
                        url = "https://cve.mitre.org/cgi-bin/cvekey.cgi?keyword="+product
                        df_list = pd.read_html(url)
                        if (len(df_list)!=5):
                            print("Page style changed at MITRE website, reconfigure")
                        else: 
                            found_cves = ((df_list[2]['Name']).to_string(index=False).split())
                            if found_cves == ['Series([],', ')']: # if no data is found for product
                                products_not_found.append(product)
                            else: # if CVE list found    
                                if len(found_cves) != 0:
                                    product_to_cve[product] = found_cves # product to CVE mapping
                                    total_cves.extend(found_cves) # add found CVEs in the total_cves list

                    print('Total '+str(len(total_cves))+' CVEs found in '+str(len(node_product_list))+' products where for '+str(len(products_not_found))+' products had no CVEs found on MITRE:\n')
                    print(products_not_found)

                    # CVE to Product mapping
                    cve_to_product = {}
                    for k, v in product_to_cve.items():
                        if isinstance(v, list):
                            for c in v:
                                if c in cve_to_product.keys():
                                    cve_to_product[c].extend([k])
                                else:
                                    cve_to_product[c] = [k]
                        else:
                            if v in cve_to_product.keys():
                                cve_to_product[v].extend(k)
                            else:
                                cve_to_product[v] = [k]
                                
                    # method to return CVE record from the dataset database
                    print('\n*** Finding CVE information from CyVID dataset ...')
                    db_name = 'cyvid_dataset' # change database
                    db = server[db_name] # select database
                    docs = db.get_bulk(total_cves) # extract all documents with ids (total_cves) # docs[i] returns doc i

                    # craft a dataframe (df_all) to hold all data for further processing
                    df_all = pd.DataFrame(columns=['_id', 'cwe_id', 'cwe_desc', 'severity', 'cvss_v2'])
                    # loop through documents to extract specific information to be added in the dataframe df_all
                    for i in range(len(docs)):
                        if (docs[i]!=None):
                            df_all = df_all.append({'_id': docs[i]['_id'], 'cwe_id': docs[i]['cwe_id'], 'cwe_desc': docs[i]['cwe_desc'], 'severity': docs[i]['severity'], 'cvss_v2': docs[i]['cvss_v2']}, ignore_index=True)
                        else:
                            #print(i, total_cves[i], '- No record found, consider updating dataset!')
                            cves_not_found.append(total_cves[i])
                    print('Total '+str(len(df_all))+' CVE records found in CYVID dataset, and '+str(len(cves_not_found))+' not found:\n')
                    print(cves_not_found)

                    x = json.loads(df_all['severity'].value_counts().to_json())
                    y = json.loads(df_all['_id'].value_counts()[:10].to_json())
                    z = json.loads(df_all['cwe_id'].value_counts()[:10].to_json())
                    print('\nAnalysis as follows {Severity}, {Top10 CVEs}, {Top10 CWEs}')
                    print(json.dumps([x, y, z], indent=4))
                    print("\nExecution time: "+str(datetime.now() - startTime))
                    return ([x, y, z])
            else:
                return {"message": "no documents in database"}        

        else: # when database not found
            print('\n*** Invalid database ('+db_name+') selected.')
            return {"message": "database not found"}            


# API resources...
api.add_resource(Home, "/")
api.add_resource(CyVID, "/CyVID")
api.add_resource(Authenticate, "/Authenticate/<string:attached_data>")
api.add_resource(CyVID_functions, "/CyVID_functions/<string:db_operation>/<string:db_name>/<string:attached_data>")
api.add_resource(CVEs, "/CVEs/<string:attached_data>")
api.add_resource(CyVID_analysis, "/CyVID_analysis/<string:attached_data>")

if __name__ == "__main__":
	app.run(host="0.0.0.0", debug=True)