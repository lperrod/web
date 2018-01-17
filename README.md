[![Build Status](https://travis-ci.org/lperrod/web.svg?branch=master)](https://travis-ci.org/lperrod/web) [![Coverage Status](https://coveralls.io/repos/github/lperrod/web/badge.svg?branch=master)](https://coveralls.io/github/lperrod/web?branch=master) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

A light application that allows you to create a simple website.

# Configuration
The main configuration file is application.yml.
There you will be able to declare the application name, some log level or format, where is the database and how to connect to it.

Please do note that there are several *.properties and you need to change the paths in compliance with your own server.

# Language
Currently only french and english are supported.
Feel free to add any Locale you need. 
In order to do so, create the corresponding files in the i18n folder and add your locale in BackDisplayFactoryImpl.computeLocales()

# Access
The backoffice url is your_localhost:your_port/manager/*
The front office url is your_localhost:your_port/pages/your_page_name
The media url is your_localhost:your_port/public/media/
In order to access to the backoffice you need to create backUser.json with a login and a password, store it on your server and declare the path in core.properties

# Website management
## Pages
There is a menu where you can create a page with its body, its header and footer, its meta and open graph meta tags
You can also decide if the page has to contain some blog entries
Please not that the technology used for the templates is Thymeleaf (http://www.thymeleaf.org/doc/tutorials/2.1/thymeleafspring.html)
## Menu
You can create a menu and any sub menu there and link it to a page
## Media
You can upload and download any kind of file there
## Carousel
You can create a carousel there and link it to a page
## Style
You can configure the stylesheet of the website there
## Blog
You can create blog entries there
## Facebook
You can connect to your Facebook account and import the latest posts and convert them to Blog entries



# Posts from facebook
The application gives the possibility to import the latest 25 posts from a Facebook account
In order to use this feature, you have to delare your app in Facebook.
This will give you an appId and an appSecret.
You have to store this on your server in a "facebook.json" file and give its path to the application in the facebook.properties file.
Then the user simply has to go to the menu and login into his Facebook account and authorize the app.

# Backup
The application automatically creates a backup once a day of all the files.
The application imports all the data in a backup file on start.
You have the possibility to send a backup of all the files to your googleDrive.
For this, you have to declare the application in a similar fashion to Facebook.
You have to ask for a client secret file.
You have to store this json file on your server and give its path to your application via the google.properties file.
On the first launch, the console will prompt you to go to a certain url. You have to follow the link and accept the app.
This will store some credentials according to the path described in google.properties.

# Deployment
Remember to change the *.properties files and the pom.xml and follow the guidelines described here.
If you wish to deploy as an executable jar, you have to change the pom.xml file by removing the tomcat declaration and by changing war to jar
Also you will need to change the main class so that it works as a simple springboot application
If you wish to deploy as a war, follow this :
https://docs.spring.io/spring-boot/docs/current/reference/html/howto-traditional-deployment.html



