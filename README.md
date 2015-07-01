# WeaveDemo
A Demonstration of Weave and Microservices

Software requirements:
Vagrant and VirtualBox (relatively new)
JDK 8


## Part 0 - Clone the project
```bash
git clone https://github.com/Khazrak/WeaveDemo.git
cd WeaveDemo
```

## Part 1 - Spring Boot Service
2 Micro Services, Consumer uses Producer.

You Need Java 8 (JDK)

For Linux / OSX
```bash
cd weave-demo-consumer
./gradlew build
cd ..
cd weave-demo-producer/
./gradlew build
cd..
```

For Windows:
```bash
cd weave-demo-consumer/
gradlew.bat build
cd..
cd weave-demo-producer/
gradlew.bat build
cd..
```

## Part 2 - Dockerize the Service

Start the VM's (this will take some time)
```bash
vagrant up
```

The Jars and the Dockerfile will be copied into the VM's

Host 1 (Consumer 1)
```bash
vagrant ssh weave-demo-01
cd /home/vagrant/producer
sudo docker build --rm -t "khazrak/weaver-consumer:1.0" .
```

Host 2 (Producer 1)
```bash
vagrant ssh weave-demo-02
cd /home/vagrant/producer
sudo docker build --rm -t "khazrak/weaver-producer:1.0" .
```

Host 3 (Producer 2)
```bash
vagrant ssh weave-demo-03
cd /home/vagrant/consumer
sudo docker build --rm -t "khazrak/weaver-producer:1.0" .
```

## Part 3 - Install and Start Weave
Install Weave on all the VM's

The IP addresses we use for this demo are

172.17.8.101    weave-demo-01

172.17.8.102    weave-demo-02

172.17.8.103    weave-demo-02

### Install Weave

Host 1
```bash
vagrant ssh weave-demo-01
sudo curl -L git.io/weave -o /usr/local/bin/weave
sudo chmod a+x /usr/local/bin/weave
```

Host 2
```bash
vagrant ssh weave-demo-02
sudo curl -L git.io/weave -o /usr/local/bin/weave
sudo chmod a+x /usr/local/bin/weave
```

Host 3
```bash
vagrant ssh weave-demo-03
sudo curl -L git.io/weave -o /usr/local/bin/weave
sudo chmod a+x /usr/local/bin/weave
```
### Start Weave and Weave DNS
On host weave-demo-01
```bash
sudo weave launch
sudo weave launch-dns
```

On host weave-demo-02
```bash
sudo weave launch 172.17.8.101
sudo weave launch-dns
```

On host weave-demo-03
```bash
sudo weave launch 172.17.8.101
sudo weave launch-dns
```

## Part 4 - Weave It!

On host 1
```bash
sudo weave run --with-dns 10.3.1.1/24 -h weave-consumer.weave.local -e SERVICE_NAME="Consumer 1" -p 8080:8080 khazrak/weave-consumer:1.0
```

On host 2
```bash
sudo weave run --with-dns 10.3.1.2/24 -h weave-service.weave.local -e SERVICE_NAME="Producer 1" --name="prod1" khazrak/weave-producer:1.0
```

On host 3
```bash
sudo weave run --with-dns 10.3.1.3/24 -h weave-service.weave.local -e SERVICE_NAME="Producer 2" --name="prod2" khazrak/weave-producer:1.0
```

## Part 5 - Test it

On host 1:
```bash
curl localhost:8080/rest/get
```

It will return:
```bash
Consumer: Consumer, Producer: Producer X
```

X will be 1 or 2. Usually it will return the same if you try it several times. if you succeed to overload it (to much load) it will use the other.

On host 1:
```bash
sudo weave status
```
After using the curl command the weave-status will show the dns-names. 


To test the fail over, stop the container that is used,
if X is 1 then on host 1 do:
```bash
sudo docker stop prod1
```

if X is 2 then on host 2 do:
```bash
sudo docker stop prod1
```

Then on host 1:
```bash
curl localhost:8080/rest/get
```

The return message should now show the other number.

On host 1:
```bash
sudo weave status
```
Now one of the dns-names should be gone.


## Part 6 - Profit!
The End

/
Karl Andersson 

se.linkedin.com/in/karlandersson1987
