# spark-jooq-todoapp
Example of using Java Spark(http://sparkjava.com/) microframework with jOOQ(http://www.jooq.org/) and Vertabelo(http://www.vertabelo.com/) in building REST todo-app

#Installation

1. Clone the repo

	git clone https://github.com/pdybka-ep/spark-jooq-todoapp.git

2. Create a database in PostgreSQL database

3. In pom.xml configure the PostgreSQL driver, database url, username, and password

4. Place your database configuration in TodoCRUD.java

5. Run maven command 

	```	
	mvn clean install
	```   

The blog post related to this example you can find here: http://www.vertabelo.com/blog/technical-articles/how-to-create-a-spark-rest-api-with-jooq
