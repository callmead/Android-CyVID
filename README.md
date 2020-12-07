# CyVID

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

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

### Models

#### Node

| Property    | Type      |                 Description   |
| ------------| ----------|                -----------    |  
| \_id        | String    | unique id for the node        |
| HostName    | String    | Host name for the node        |
| HostIP      | String    | IP Address of the node        |
| HostGateway | String    | IP Gateway of teh node        |
| HostOS      | String    | operating system of node      |

#### Product

| Property    | Type      |                 Description   |
| ------------| ----------|                -----------    |  
| \_id        | String    | unique id for the product     |
| ProdDesc    | String    | name and version of product   |

#### Node_Product_Map

| Property    | Type      |                 Description   |
| ------------| ----------|                -----------    |  
| \_id        | String    | unique id                     |
| ProductId   | String    | unique id of the product      |
| NodeId      | String    | unique id of the node         |

#### Product_CVE_Map

| Property    | Type      |                 Description   |
| ------------| ----------|                -----------    |  
| \_id        | String    | unique id                     |
| ProductId   | String    | product name                  |
| CVE_ID      | String    | vulnerability identification# |
| CWE_ID      | String    | vulnerability Classification# |

#### CWE_Master

| Property    | Type      |                 Description   |
| ------------| ----------|                -----------    |  
| \_id        | String    | unique id for CWE             |
| CWEName     | String    | CWE name                      |
| Parents     | List      | List of parents for the CWE   |
| Children    | List      | List of children for the CWE  |

#### CWE_Detailed

| Property                  | Type        | Description   |
| ------------              | ----------  | -----------   |  
| \_id                      | String      | unique id     |
| CWEName                   | String      | CWE name      |
| weakness_abstraction      | String      | CWE Details   |
| Status                    | String      | CWE Details   |
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
| lang                      | String      | Language                                    |
| CWE_ID                    | String      | unique id                                   |
| CWE_Description           | String      | Description                                 |
| CWE_Platform              | String      | Platform                                    |
| CWE_Af_Res                | String      | Affected Resources                          |
| Severity                  | String      | Vulnerability Severity                      |
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

### Networking

#### Node
- (Read/GET) Query all nodes
- (Add/Node) Add a new node profile
- (Delete) Delete an existing node profile
- (Update/PUT) Update a node profile

#### Product
- (Read/GET) Query all nodes
- (Add/Node) Add a new product
- (Delete) Delete an existing product
- (Update/PUT) Update a product details

#### Node_Product_Map
- (Read/GET) Query node/product mappings
- (Add/Node) Add a new node/product mapping
- (Delete) Delete an existing node/product mapping
- (Update/PUT) Update a node/product mapping


#### Product_CVE_Map
- (Read/GET) Query product/CVE mappings
- (Add/Node) Add a new product/CVE mapping
- (Delete) Delete an existing product/CVE mapping
- (Update/PUT) Update a product/CVE mapping

#### CWE_Master
- (Read/GET) Query CWE records

#### CWE_Detailed
- (Read/GET) Query CWE details

#### CyVID_Dataset
- (Read/GET) Query CyVID dataset for vulnerability details.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://github.com/jannyr08/CyVID/blob/main/cyVID-s1.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).
