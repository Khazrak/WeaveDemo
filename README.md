# WeaveDemo
A Demonstration of Weave and Microservices

Software requirements:
Vagrant and VirtualBox (relatively new)

## Part 0 - Clone the project
```bash
git clone https://github.com/Khazrak/WeaveDemo.git
cd WeaveDemo
```

## Part 1 - Spring Boot Service
2 Micro Services, one uses the other

## Part 2 - Dockerize the Service
Put the jar in the container and make it run

## Part 3 - Install and Start Weave
Weave it and scale it and reslove by WeaveDNS

Start the VM's
```bash
vagrant up
```

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

## Part 4 - Build the DockerImage (can use registry)


## Part 5 - Weave It!



## Part 6 - Profit!
The End

/
Karl Andersson 
se.linkedin.com/in/karlandersson1987
