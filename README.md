# WHERE IS MY GO-JEK DRIVER v3.0

This is a node.js REST service powered by Hapi.js and Docker that provides the main functions for the mobile app you'd expect from a location-based app that makes on-demand taxi driver.

## Challenge

We have 50,000 drivers who go around the city looking for rides. Typically, drivers are evenly
distributed across the city. There are customers all over the place trying to find the driver. To
facilitate this, we need to keep track of driver’s current location and provide an ability to search
drivers in a given area. You need to build 2 APIs to achieve this.

## Features

- Written in Javascript ES6.
- Built on Docker. 
- Stupidly easy to deploy.
- Easy to configure deployment environment on Chef.
- Works on Mac, Linux and (maybe) Windows
- Easy to scale with Nginx.

## Built With

* [Javascript](https://www.javascript.com/) - The language 
  * Ease of integration with MongoDB and maybe later on with various front end frameworks like ReactJS,Anguler,also you can reduce the amount of time and efforts required for developing JS based apps.

* [Hapi](https://hapijs.com/) - The web framework
  * Easy-to-use APIs/Plugins, highly modular architecture, and can support application as it grows potentially very large.HapiJS powered [Walmart](https://www.walmart.com/) mobile APIs which is very promising in terms of scalability and performance.


  
* [Npm](https://www.npmjs.com/) - The dependency manager.
  * Basically used for managing dependencies of various server side dependencies. We can manages our server side dependencies manually as well but once our project's dependencies grow it becomes difficult to install and manage.

* [Jenkins](https://jenkins.io/) - The continuous integration tool
  * Jenkins was used to find and fix bugs in the code base by automate testing and also deploy the application on a successful build on staging server.

* [Chef](https://www.chef.io/configuration-management/) - The configuration management tool
  * Chef was used as a configuration management tool for managing the application infrastructure so that it can be repplicated on any environment on minimum effort.   
  
* [Docker](https://www.docker.com/) - The containerization platform
  * Docker is used to make it easier to deploy, and run application by using docker containers which makes it enables to package up an application with all of the dependencies it needs, such as packages and other infrastructure requirement like MongoDB,Nginx, and ship it all out as one package.Docker compose is used for defining and running MongoDB/Nginx/Api on  multi-container mode. 
  
* [PM2](http://pm2.keymetrics.io/) - The process manager tool
  * PM2 was used to ensure that HapiJS application starts automatically when your server restarts , With one command, PM2 can ensure that any applications it manages restart when the server reboots. Basically, HapiJS application will start as a service.
  
* [MongoDB](https://www.mongodb.com/) - The database
  * MongoDB was used because of various factors like performance/sharding/Geospatial Support.since we are dealing with massive amount of locaton based data it's always better to use MongoDB so that we can scale out horizontally using sharding.  
  
* [Nginx](https://www.nginx.com/) - The load balancer
  * NGINX is a very fast load balancer, its faster than Apache (under similar conditions) because it doesn't need to spawn new processes or threads for each request like Apache does. Hence it also has a low memory foot print and also support higher concurrency support.
  
* [Vagrant](https://www.vagrantup.com/) - The test kitchen
  * Vagrant was spawned by Chef Kitchen as development and deployment environment to automate and test out the chef scripts.


## Prerequisites

You will need:

#### System Requirement:

- MacOS or a Linux server running on CentOS, Ubuntu, or Red Hat Enterprise Linux 7 (RHEL7).
- NodeJS v8 or greater.
- NPM v6 or greater.

#### Required dependencies:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

#### Optional dependencies:

- [Oracle virtualbox](https://www.virtualbox.org/wiki/Downloads) - To spawn virtual machine from chef script.
- [Vagrant](https://www.vagrantup.com/docs/installation/) - For Vagrant CLI.
- [ChefDK](https://docs.chef.io/install_dk.html) - For Chef Script Deployment.

## Versioning

I'hv use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://gitlab.com/istore221/where-is-my-go-jek-driver/tags). 
