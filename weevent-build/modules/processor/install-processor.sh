#!/bin/bash

function processor_setup() { 
    echo "install processor into ${out_path} "
    
    #create processor list
    mkdir -p ${out_path}
    cp -r ./* ${out_path}/
    rm -f ${out_path}/install-processor.sh

    application_properties=${out_path}/conf/application-prod.properties

    if [[ -z ${server_port} ]];then
        echo "server_port is empty."
        echo "set server_port failed"
        exit 1
    else
       sed -i "/server.port*/cserver.port=${server_port}" ${application_properties}
    fi
    echo "set server_port success"

    if [[ ${database_type} != "h2" ]];then
        if [[ -z ${mysql_ip} ]];then
            echo "mysql_ip is empty."
            echo "set mysql_ip failed"
            exit 1
        else
           sed -i "s/127.0.0.1:3306/${mysql_ip}:3306/" ${application_properties}
        fi
        echo "set mysql_ip success"

        if [[ -z ${mysql_port} ]];then
            echo "mysql_port is empty."
            echo "set mysql_port failed"
            exit 1
        else
            sed -i "s/3306/${mysql_port}/" ${application_properties}
        fi
        echo "set mysql_port success"

        if [[ -z ${mysql_user} ]];then
            echo "mysql_user is empty."
            echo "set mysql_user failed"
            exit 1
        else
            sed -i "s/xxxx/${mysql_user}/" ${application_properties}
        fi
        echo "set mysql_user success"

        if [[ -z ${mysql_pwd} ]];then
            echo "mysql_pwd is empty"
            echo "set mysql_pwd failed"
            exit 1
        else
            sed -i "s/yyyy/${mysql_pwd}/" ${application_properties}
        fi
        echo "set mysql_pwd success"
    fi
     
    # init db, create database and tables
    cd ${out_path}
    ./init-processor.sh
    if [[ $? -ne 0 ]];then

        echo "Error,init mysql fail"
        exit 1
    fi
    echo "init db success"
    
    echo "processor module install success"
}

#get parameter
para=""

# usage
if [[ $# -lt 2 ]]; then
    echo "Usage:"
    echo "    $0 --out_path /data/app/weevent-install/processor "
    echo "      --server_port --database_type --mysql_ip --mysql_port  --mysql_user  --mysql_pwd  "
    exit 1
fi

server_port=""
database_type=""
mysql_ip=""
mysql_port=""
mysql_user=""
mysql_pwd=""
out_path=""
current_path=$(pwd)
echo "current path $current_path"

while [[ $# -ge 2 ]] ; do
    case "$1" in
        --out_path) para="$1 = $2;";out_path="$2";shift 2;;
        --server_port) para="$1 = $2;";server_port="$2";shift 2;;
        --database_type) para="$1 = $2;";database_type="$2";shift 2;;
        --mysql_ip) para="$1 = $2;";mysql_ip="$2";shift 2;;
        --mysql_port) para="$1 = $2;";mysql_port="$2";shift 2;;
        --mysql_user) para="$1 = $2;";mysql_user="$2";shift 2;;
        --mysql_pwd) para="$1 = $2;";mysql_pwd="$2";shift 2;;
        *) echo "unknown parameter $1." ; exit 1 ; break;;
    esac
done

processor_setup