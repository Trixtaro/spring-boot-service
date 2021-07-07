Hello, this is an example of a Spring Boot API REST.


# Usage

1 - Start application from your **IDE**

2 - Open a web browser and search

`http://localhost:5555/link/google`

or

`http://localhost:5555/link/facebook`

or

`http://localhost:5555/link/justclick`

3 - You'll be redirected to another page meanwhile endpoint remaining calls are greater than zero, else you will use a 404 error page


You can set up the host and port on **application.properties** file

User logs with user information will be saved inside **click.json** file at the root of the project

# Installation

### IDE

I used **IntellijIdea** and **Maven** to install Java dependencies

### Database

You have to run **start.sql** using MySQL database to create schema, table and insert data. This SQL file will create a database called **justclickmediatest**

### Configuration

Credentials configuration are in **application.properties** file

`spring.jpa.hibernate.ddl-auto=none`

`spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/justclickmediatest`

`spring.datasource.username=root`

`spring.datasource.password=`

`spring.datasource.driver-class-name =com.mysql.jdbc.Driver`


You can change these properties with your own access credentials

### ElasticSearch

You have to run and ElasticSearch instance at **localhost** on default port **9200**

Application will send data from a JSON log, to this instance each hour at 5th minute

I downloaded ElasticSearch from the [official web page](https://www.elastic.co/es/downloads/elasticsearch)
