# Spring "[PetClinic](https://github.com/spring-projects/spring-petclinic)" with [Postvayler](https://github.com/raftAtGit/Postvayler) 

This is a 'proof of concept' sample of Postvayler integration with Spring. Postvayler provides persistance capabilities to POJO's. 

### Steps:

1) Clone Postvayler and install into local Maven repository
```
git clone https://github.com/raftAtGit/Postvayler.git
cd Postvayler
gradle clean build publishToMavenLocal
```
2) Clone PetClinic
```
git clone https://github.com/raftAtGit/spring-petclinic.git
```
3) Build PetClinic and run locally
```
cd spring-petclinic
mvn clean package -DskipTests
mvn org.codehaus.cargo:cargo-maven2-plugin:run
```
4) Open PetClinic in a browser 
<http://localhost:8080/spring-petclinic/>
