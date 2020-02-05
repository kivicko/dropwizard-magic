# Dropwizard Magic
Don't stuck on the name, this repository is created to learn an alternative way of web with java.

Dropwizard is such a cool framework. Lightweight and fast. 

# About Project
This project is basically money lending API at its minimum. 

You can create some accounts, send and receive money through API.

It will accept the transfer requests and save them in a Queue. 
Has integrated job classes to perform every second on this queues items.

Project is currently using the concurrentHashMap class as datastore to run in-memory.

# Endpoints

You can use following postman collection for accepted requests.

https://www.getpostman.com/collections/90e40ad282451bba44be

Endpoint details: 

http://localhost:8080/transfer (POST) (Accepts TransferDetail obj) : Creates new transfer data for queue.

http://localhost:8080/accounts (POST) (Accepts Account obj) : Creates new account.

http://localhost:8080/accounts/{id} (GET) (Accepts ID) : Retrieves account detail.

http://localhost:8080/accounts (GET) : returns all Accounts details.

# Referenced pages : 

[Dropwizard-jobs](https://github.com/dropwizard-jobs/dropwizard-jobs) :  So basically, I need some cron job to use in Dropwizard framework.
 
[Dropwizard-guicey](https://github.com/xvik/dropwizard-guicey) : Because every cool project needs DI.


