# Basic Ubuntu and Docker setup Vagrantfile
#
# Weave Getting Started Guides
#

require 'fileutils'
require 'ipaddr'

Vagrant.require_version ">= 1.6.0"

VAGRANTFILE_API_VERSION = "2"
SIMPLE_DOCKER_APACHE = File.join(File.dirname(__FILE__), "Dockerfile-simple-apache")

ENV['VAGRANT_DEFAULT_PROVIDER'] = 'virtualbox'

# Defaults for config options defined in CONFIG
#

$num_instances = 3
$instance_name_prefix = "weave-demo"
$share_home = false
$vm_gui = false
$vm_memory = 1024
$vm_cpus = 1
$vm_starting_ip = "172.17.8.100"

$vm_ip = $vm_starting_ip

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
    config.vm.box = "ubuntu/trusty64"
    config.ssh.insert_key = true

    if Vagrant.has_plugin?("vagrant-cachier")
        config.cache.scope = :box
    end

    (1..$num_instances).each do |i|
    
        config.vm.define vm_name = "%s-%02d" % [$instance_name_prefix, i] do |config|
            config.vm.hostname = vm_name

            # just split out the ip
            #

            ip = IPAddr.new($vm_ip)
            $vm_ip = ip.succ.to_s
            config.vm.network :private_network, ip: $vm_ip 
        end
    end

    config.vm.provider :virtualbox do |vb|
        vb.gui = $vm_gui
        vb.memory = $vm_memory
        vb.cpus = $vm_cpus
    end

    # install docker packages
    #
    # https://docs.docker.com/installation/ubuntulinux/
    #

    config.vm.provision "shell", privileged: true, inline: <<-SHELL
        [ -e /usr/lib/apt/methods/https ] || {
            apt-get update
            apt-get install apt-transport-https
        }

        echo deb https://get.docker.com/ubuntu docker main > /etc/apt/sources.list.d/docker.list
        sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 36A1D7869245C8950F966E92D8576A8BA88D21E9
        apt-get update
        apt-get install -y lxc-docker
    SHELL

    # for the purposes of our example we create a dockerfile to install apache

    if File.exist?(SIMPLE_DOCKER_APACHE)
        config.vm.provision "file", :source => "#{SIMPLE_DOCKER_APACHE}", :destination => "/home/vagrant/Dockerfile"
    end 
end
