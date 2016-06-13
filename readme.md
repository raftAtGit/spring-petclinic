# Spring [PetClinic](https://github.com/spring-projects/spring-petclinic) with [Postvayler](https://github.com/raftAtGit/Postvayler) 

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

### Notes:

1) By default, persistence files are stored inside current directory which is inside target directory. So each time a 'mvn clean' is performed, persistence files are deleted. Persist directory can be configured inside @EnablePostvayler annotation at [Config class]  (https://github.com/raftAtGit/spring-petclinic/blob/master/src/main/java/org/springframework/samples/petclinic/Config.java)

2) This is just a proof of concept implementation with minimal changes to original PetClinic source. Using persistent domain classes as [DTO](https://en.wikipedia.org/wiki/Data_transfer_object)'s is far less then optimal. Particularly each time Spring creates and populates domain ojects to pass to @Controllers, actually behind the scenes Postvayler kicks in and captures and saves to disc all object creation and invocations of setter methods. These objects are soon garbage collected and cause no actual harm but is an unnecessary and far less-optimal behaviour.
