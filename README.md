# Project 01 - Banking API

## Overview

Create basic back-end banking api for users to access database client and account information from http requests.

### Technologies

- Maven - Project Management
- Java - Core Language
- Mockito - Testing

* AWS - Database server
* PostgreSql - Database type
* DBeaver - Test Database SQL queries and access

- Javalin - Web Framework for http requests/responses
- Poastman - Test http requests/responses



### Functionallity

- Create new clients
- Retrieve all or specific client
- Update clients
- Delete client

* Create account associated with specific client
* Retrieve all clients of a specific client
    * Can restrict to get accounts with a certain balance range
* Retrieve specific account from a client
* Update accounts
* Deposite/Withdraw funds
* Transfer funds between accounts
* Delete accounts

### Structure

#### Model

- **Client:** Unique id, firstname, lastname  
- **Account:** Unique id, associated client id, account type, balance

#### Architecture

- **Driver:** runs the program and starts javalin server and controllers
- **Controller:** handles http requests and organizes user input
    - Sends appropriate response based on retireved data
- **Service:** validates user input, and data output
- **DAO:** Retrives data from database

#### Utility

- **DatabaseConnection:** Simplifies creatting connections to database

## Notes

Postman tests are run assuming database is an an inital state (see postgres/project0.sql)
- Invalid tests are run first, then valid ones