#!/bin/bash
#Name:serviceCheck
##change by the enviroment
SERVER="192.168.1.29"
PORT=2233
#########################################################
# ellis for Ellis, homer for XDMS, homestead for HSS Mirror
# bono for P-CSCF, sprout for I/S-CSCF
program=ellis
NIC=eth0
MAC=`LANG=C ifconfig $NIC | awk '/HWaddr/{ print $5 }' `
Time=`date +"%Y-%m-%d %H:%M.%S"`

##########################################################
# Main

exec 3<>/dev/udp/${SERVER}/${PORT}
while [ true ];
do
    /bin/sleep 3

    sn=`ps -ef |grep $program |grep -v grep |awk '{print $2}'`
#echo ${UNICOM} ${MAC} "["`date +"%Y-%m-%d %H:%M.%S"`"]" >&3
    if [ "${sn}" = "" ]
    then
        echo ${MAC}"|-1" >&3
    else
        echo ${MAC}"|0"  >&3
    fi
done

cat <&3

exit $?
