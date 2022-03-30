How to open mysql client
------------------------

### Check status
```
sudo systemctl status mysql

service mysql status
```

### Login as root

In ubuntu systems running MySQL 5.7 (and later), the root user is authenticated by the `auth_socket` plugin by default.

The `auth_socket` plugin authenticates users that connect from the `localhost` through the Unix socket file. This means that you can't authenticate as root by providing password.

To log in to the MySQL server as the root user type:
```
sudo mysql
```
You will be presented with the MySQL shell.

### List and Create Databases
```
mysql> SHOW DATABASES;

mysql> SHOW DATABASES LIKE 'pattern';
```

```
CREATE DATABASE [IF NOT EXISTS] database-name;
```

In MySQL, the schema is the synonym for the database. Creating a new schema also means creating a new database.

### Access database
```
HELP;
USE database-name;
SHOW TABLES;
DESCRIBE table-name;
```
