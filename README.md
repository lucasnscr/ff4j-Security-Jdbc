# ff4j-Security-Jdbc

### **Project description**
🚀 This project implementing Feature Flags Platform with SpringBoot, Spring Security, Postgres and FF4J. 

[Here is the complete code of the project](https://github.com/lucasnscr/ff4j-Security-Jdbc)

## **Installation and  Technologies**

The following technologies were used to carry out the project and it is necessary to install some items:
- Docker
- Java 17
- Maven
- Kubernetes(MiniKube)
- SpringBoot
- Spring Security
- Postgres
- FF4J
- Adminer
- Flyway

### **Feature toggle**
Feature toggle (also known as feature flipping, feature flags or feature bits) is the capacity for a program or an application to enable and disable features at runtime. The toggle can be operate programmatically, through web console, through api, through command line or even through JMX. The source code includes several paths that will be executed or not depending on the values of flags/features.

A Feature represents a business logic that can potentially crosses every layer of applications from user interfaces to data access. Therefore, to implement a feature toggle mechanism, we must help you in each layer as shown with the picture on the right.

### **FF4J**
[FF4J](https://ff4j.github.io/), standing for Feature Flipping for Java, is a proposition of Feature Toggle written in Java. The present guide will describe the different capabilities of the framework including some sample codes.

### **FF4J Use Cases**

- **Feature Toggle:** Enable and disable features at runtime no deployments. In your code implement multiple paths protected by dynamic predicates.
- **Role-based Toggling** Enable features not only with flag values but also drive access with roles and groups (Canary Release). Different frameworks supported starting by Spring Security.
- **Features Monitoring:** For each features execution, ff4j evaluates the predicate therefore it's possible to collect and record events, metrics to compute nice dashboards or draw curves for features usage over time.
- **Web Console:** Administrate FF4j (including features and properties) with the web UI. Packaged as a servlet in the library you will expose it in your backend applications. Almost 10 languages available.
- **Audit Trail:** Each action (create, update, delete, toggles) can be traced and saved in the audit trail for troubleshooting. With permissions management (AuthorizationManager) it's possible to identify users.

### **About Project**

The project has a **fabric8** plugin, this plugin generate the docker image automatically, and supporting **ARM64 architecture**. The project have a **Dockerfile**. To generate the service image, you will need to run a command.

```
mvn clean install
```

After generating the artifact you will need to **install MiniKube** and **kubectl**, assuming that these two items are already configured correctly, we select three commands, one to start the kubernetes cluster, enable the kubernetes dashboard that allows tracking the cluster with a visual interface and the other to stop it.

```
minikube start
minikube dashboard
minikube stop
```

For you to be **able to deploy your pods with local docker images**, you will need to run the command below.

```
eval $(minikube -p minikube docker-env)
```

This command directs your minikube to use your local docker-env address in that terminal instance, so your pods will be uploaded using images that have already been built locally without necessarily being on DockerHub or some other image repository.

With the cluster initialized, there are some commands that are important for us to **see our pods, deployments and services**. Below are two examples of commands to get what is operating in the cluster:

```
kubectl get deployments
kubectl get pods
kubectl get services
```

### **Deploying Postgres on Kubernetes**

**Our application uses the Postgres database**, there is a directory **deploy_database** within the project **to make the database available on the cluster**, you need to run the commands below in sequence:

```
kubectl	create	-f	postgres.yaml
kubectl	create	-f	postgres-service.yaml
kubectl	port-forward svc/postgres 5000:5432
kubectl	create -f config-map.yaml
```

The respective commands create a deployment, a service, perform a database port forwarding inside k8s and create a configmap for the database.

### **Deploying Adminer on Kubernetes**

**Adminer** (formerly phpMinAdmin) is a PHP-based, free, open source database management tool. It’s super simple to deploy on your server. To use it, all you have to do is upload its single PHP file, point your browser towards it, and log in.

Using flyway library it already creates our schema and tables at the time of initialization of the application, however we added the Adminer to facilitate the management of our database, not only to monitor the tables but also to consult the data referring to the auditing part, a functionality that we have in FF4J strongly recommended for monitoring the management of flags.

To install the Adminer, you will need to go to the deploy_adminer directory and run the following commands:

```
kubectl	create	-f	adminer-deployment.yaml
kubectl	create	-f	adminer-service.yaml
```

You will need to open a new terminal instance to run a command that will redirect your container port to the specific port of the command:

```
kubectl	port-forward {pod name Adminer created} 8080:8080
```

**For consulting pod name, the command is:**
```
kubectl get pods
```

Everything running perfect, you access [Adminer](http://localhost:8080/) with credentials:

```
System=PostgresSQL
server=postgres
username=postgres
password=postgres
database=dev
```


### **Deploying the application on Kubernetes**
After preparing our dependencies, we will **deploy our ff4j-security service that make up the application**. Each service will have a deploy directory with the files related to deployments and services.

```
kubectl create -f deployment.yaml
kubectl create -f service.yaml
```

You will need to open a new terminal instance to run a command that will redirect your container port to the specific port of the command:

```
kubectl	port-forward {pod name ff4j-security created} 8081:8081
```

After executing the redirect command you will be able to access the [ff4j console](http://localhost:8081/ff4j-web-console/), but as we implement spring security, you will need to authenticate yourself in advance. You can use the following username and password:
```
user:superuser
password:superuser
```

### **Delete pods on Kubernetes**

Running everything in this sequence will successfully run the services in Kubernetes. In case **something fails** here follow some commands to kill the pod, the deployment and the service.

```
kubectl delete deployment {deployment-name}
kubectl delete service {service-name}
```

Finally, to follow the **logs of your application**, you need to run the command:

### **Consulting pod log on Kubernetes**

Execute this command:

```
kubectl logs {pod name}
```
