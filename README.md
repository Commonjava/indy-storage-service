# Indy Storage Service
Indy Storage Service allows users to store and retrieve path-mapped files. 

Each file has a path, e.g, '/foo/bar.txt', associated with it. All files are grouped 
by 'filesystem'. You may think of it as the different drivers on you disk.

Users can r/w the files via REST api. Different users can r/w same file without 
affecting each other. For example, if one user is downloading the '/foo/bar.txt' 
while another user is uploading a new version of it, the first user will get the file 
content at the moment he/she started the download.

The most significant feature is the 'cluster' mode. You can deploy it on a Cloud platform,
such as Openshift, and scale up as many nodes as you want. 
The concurrent r/w promise is still held without worrying about r/w conflicts.
On cluster mode, all nodes share the same persistent volume and connect to the same Cassandra as the backend DB.
There are some instructions at the bottom about how to set up a storage service cluster on Openshift.

## Prerequisite for building
1. jdk11+
2. mvn 3.6.2+

## Prerequisite for debugging in local
1. docker 20+
2. docker-compose 1.20+

## Configure 

Refer to [src/main/resources/application.yaml](./src/main/resources/application.yaml) for details


## Try it locally

There are a few steps to set it up on your local machine.

1. Build (make sure you use jdk11+ and mvn 3.6.2+)
```
$ git clone git@github.com:Commonjava/indy-storage-service.git
$ cd indy-storage-service
$ mvn clean compile
```
2. Start dependent services (you need to have Docker installed). 
Storage service use Cassandra as the backend DB. Below will start a standalone Cassandra 
server. For more information, please refer to Cassandra docs. 
```
$ docker-compose up
```
3. Start in debug mode
```
$ mvn quarkus:dev
```
4. Open another terminal, upload a file then download it and list a directory.
```
$ echo "test $(date)" | curl -X PUT -d @- http://localhost:8080/api/content/myfiles/foo/bar.txt
$ curl http://localhost:8080/api/content/myfiles/foo/bar.txt
$ curl http://localhost:8080/api/browse/myfiles/foo
```

## Scale up

The docker image is at https://quay.io/factory2/indy-storage-service:latest, 
and you can run a cluster by deploying it on a cloud platform. 

TODO: instructions for deploying it on Openshift.


