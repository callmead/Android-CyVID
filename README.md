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

* User logs in to access their network information
* User has an analysis screen that shows the current state of their network
* Data entry screens required for adding network data

**Optional Nice-to-have Stories**

* Push notifications to users for network events
* Customizable reports

### 2. Screen Archetypes

* Login 
* Dashboard 
* Add Node Screen
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
<img src="https://github.com/jannyr08/CyVID/blob/main/IMG_5373.jpg" width=800><br>

## Schema 

### Models

#### Node

| Property  | Type      |                 Description   |
| ----------| ----------|                -----------    |  
| objectId  | String    | unique id for the node        |
| nodeName  | String    | name of node                  |
| node type | String    | node type (correspoding to DB)|
| OS        | String    | operating system of node      |
| description | String  | description of node           |

#### User


### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
