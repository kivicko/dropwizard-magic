# About
This project is basicly money lending API at its minimum. You can create some accounts, send and receive money through API.

# Endpoints
http://localhost:8080/transfer (POST) (Accepts TransferDetail obj) : Creates new transfer data for queue.

http://localhost:8080/accounts (POST) (Accepts Account obj) : Creates new account.
http://localhost:8080/accounts/{id} (GET) (Accepts ID) : Retrieves account detail.
http://localhost:8080/accounts (GET) : returns all Accounts details.

# Dropwizard
Such a cool framework. Lightweight and fast. 

# Referenced pages : 

[Dropwizard-jobs](https://github.com/dropwizard-jobs/dropwizard-jobs) :  So basically, I need some cron job to use in Dropwizard framework. 
[Dropwizard-guicey](https://github.com/xvik/dropwizard-guicey) : Because every cool project needs DI.


