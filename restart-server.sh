#!/bin/bash

PIDFILE="/u/kspace/www/fluency/student-files/fall2012/a10/team-all/gameserverpid"
LOGFILE="/u/kspace/www/fluency/student-files/fall2012/a10/team-all/gameServer.log"
if [ -s $PIDFILE ] 
then
  PID=`cat $PIDFILE`
  kill -9 $PID
fi

mvn -f server/pom.xml cargo:run > $LOGFILE 2>&1 &
pid=$!
echo $pid > $PIDFILE
