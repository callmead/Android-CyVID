# CyVID

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)
2. [Networking]{#Networking}

## Overview
### Description
The app is intended to provide information about the computer's at risk within a given network and help cyber defenders decide which parts have more vulnerabilities and can be secured. 

### App Evaluation
- **Category:** Cybersecurity
- **Mobile:** This app can be developed as both as mobile app and a web app. 
- **Story:** Analyzes a user's network nodes for vulnerabilities and displays the collected analysis. 
- **Market:** It's an open-source app available for any cyber defenders. 
- **Habit:** This can be used periodically to gather current trends. 
- **Scope:** First we start by providing network analysis and later can be used for preedicting future risks. 

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* [x] User logs in to access their network information
* [x] User access their dashboard to add nodes
* [x] Data entry screens required for adding network data
* User has an analysis screen that shows the current state of their network

**Optional Nice-to-have Stories**

* Push notifications to users for network events
* Graphs
* Customizable reports

### 2. Screen Archetypes

* [x] Login 
* [x] Dashboard 
* [x] Add Node Screen
* Add Products to Node Screen
* Analysis Screen
* User Management Screen

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Dashboard
* Analysis

**Flow Navigation** (Screen to Screen)
* Forced Log-in -> Account creation if no log in is available
* Dashboard -> Leads to Add Node/Product/User
* Add Node/Product/User -> Text field to be add/update/delete data. 

## Wireframes
<img src="https://github.com/jannyr08/CyVID/blob/main/images/wireframe.jpg" width=800><br>

## Schema 
For the mobile app to interact with the backend database, we selected CouchDB (nosql) database because it is a very flexible and scaleable document store. The database is running on our own server and the database structure is as follows.

### Models

#### CyVID_Nodes

| Property    | Type      |                 Description   |
| ------------| ----------|                -----------    |  
| \_id        | String    | unique id for the node        |
| \_rev       | String    | revision # of the document    |
| HostName    | String    | Host name for the node        |
| HostIP      | String    | IP Address of the node        |
| HostGateway | String    | IP Gateway of teh node        |
| HostOS      | String    | operating system of node      |
| applications| String    | list of installed apps        |

#### CyVID_CWE_Master

| Property    | Type      |                 Description   |
| ------------| ----------|                -----------    |  
| \_id        | String    | unique id for CWE             |
| \_rev       | String    | revision # of the document    |
| name        | String    | CWE name                      |
| parents     | List      | List of parents for the CWE   |
| children    | List      | List of children for the CWE  |

#### CyVID_CWE_Combined

| Property                  | Type        | Description   |
| ------------              | ----------  | -----------   |  
| \_id                      | String      | unique id     |
| \_rev                     | String      | revision # doc|
| name                      | String      | CWE name      |
| weakness_abstraction      | String      | CWE Details   |
| status                    | String      | CWE Details   |
| description               | String      | CWE Details   |
| extended_description      | String      | CWE Details   |
| related_weaknesses        | String      | CWE Details   |
| weakness_ordinalities     | String      | CWE Details   |
| applicable_platforms      | String      | CWE Details   |
| alternate_terms           | String      | CWE Details   |
| modes_of_introduction     | String      | CWE Details   |
| exploitation_factors      | String      | CWE Details   |
| likelihood_of_exploit     | String      | CWE Details   |
| common_consequences       | String      | CWE Details   |
| detection_methods         | String      | CWE Details   |
| potential_mitigations     | String      | CWE Details   |
| observed_examples         | String      | CWE Details   |
| functional_areas          | String      | CWE Details   |
| affected_resources        | String      | CWE Details   |
| taxonomy_mappings         | String      | CWE Details   |
| related_attack_patterns   | String      | CWE Details   |
| notes                     | String      | CWE Details   |

#### CyVID_Dataset

| Property                  | Type        | Description                                 |
| ------------              | ----------  | -----------                                 |    
| \_id                      | String      | unique id                                   |
| \_rev                     | String      | revision # of the document                  |
| lang                      | String      | Language                                    |
| CWE_ID                    | String      | unique id                                   |
| CWE_Description           | String      | Description                                 |
| CWE_Platform              | String      | Platform                                    |
| CWE_Af_Res                | String      | Affected Resources                          |
| severity                  | String      | Vulnerability Severity                      |
| CVSS_V2                   | String      | CVSS V2 Score                               |
| CVSS_V3                   | String      | CVSS V3 Score                               |
| Vul_Access_Vector         | String      | Vulnerability Access Vector                 |
| user_int_req              | Boolean     | User Interaction Required                   |
| OS                        | List        | Operating systems affected by Vulnerability |
| SW                        | String      | Softwares affected by Vulnerability         |
| published_date            | Date        | Date of publication                         |
| modified_date             | Date        | Modification Date                           |
| description               | String      | CWE Details                                 |
| url_and_tags              | List        | References                                  |

#### CyVID_Users

| Property                  | Type        | Description   |
| ------------              | ----------  | -----------   |  
| \_id                      | String      | unique id     |
| \_rev                     | String      | revision # doc|
| user                      | String      | CWE name      |
| pass                      | String      | CWE Details   |

## Networking
For the mobile app to interact with the backend, we built a custom API that runs on the same server as the database. The API is capable of receiving Add/Update/Delete/Query requests and perform the related operation on database and return status messages or data in response.  

#### CyVID_Node
- (Read/GET) Query single node data - http://hostIP:port/CyVID_functions/query/cyvid_node/{"data":"Test 12"}
- (Read/GET) Query all nodes data - http://hostIP:port/CyVID_functions/query/cyvid_node/{"all":"docs"} 
- (Add/Node) Add a new node profile - http://hostIP:port/CyVID_functions/add/cyvid_node/{"HostName":"Mike's Computer", "HostIP":"192.168.1.184", "HostGateway": "192.168.1.1", "HostOS": "Microsoft Windows 10 Pro Build 12457"}
- (Add/Node) Add node applications - http://hostIP:port/CyVID_functions/addapps/cyvid_node/{"_id":"0", "_rev": "20-d41f7f5748fd2c5e568a4d9272050bd0", "applications": "Test 6.0"}
- (Update/Node) Update existing node - http://hostIP:port/CyVID_functions/update/cyvid_node/{"_id":"11", "_rev": "3-cf6bfa49a8a8665f7e90580b7dd85cba", , "data":"new value"}
- (Delete/Node) Delete an existing node - http://hostIP:port/CyVID_functions/delete/cyvid_node/{"_id":"11", "_rev": "6-85c2acee6d155b7eeecfa0e05b627385"}

#### CyVID_CWE_Master
- This document store is updated through a python-based agent running on the server.
- The API interacts with this database to fetch related information and feeds to the Android app.

#### CyVID_CWE_Combined
- This document store is updated through a python-based agent running on the server.
- The API interacts with this database to fetch related information and feeds to the Android app.

#### CyVID_Dataset
- This document store is updated through a python-based agent running on the server.
- The API interacts with this database to fetch related information and feeds to the Android app.

#### CyVID_Users
- (Read/GET) Query single user data - http://hostIP:port/CyVID_functions/query/cyvid_users/{"user":"sarah"}
- (Read/GET) Query all users data - http://hostIP:port/CyVID_functions/query/cyvid_users/{"all":"docs"} 
- (Add/Node) Add a new user profile - http://hostIP:port/CyVID_functions/add/cyvid_users/{"user":"John Albert", "pass":"john@123"}
- (Update/User) Update existing user - http://hostIP:port/CyVID_functions/update/cyvid_users/{"_id":"11", "_rev": "3-cf6bfa49a8a8665f7e90580b7dd85cba", , "pass":"new value"}
- (Delete/User) Delete an existing user - http://hostIP:port/CyVID_functions/delete/cyvid_users/{"_id":"11", "_rev": "6-85c2acee6d155b7eeecfa0e05b627385"}
- (Authenticate/User) Authenticate user - http://hostIP:port/Authenticate/{"user":"admin", "pass":"password"}

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://github.com/jannyr08/CyVID/blob/main/cyVID-s1.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).
