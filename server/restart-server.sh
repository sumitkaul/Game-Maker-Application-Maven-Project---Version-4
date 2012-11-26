#!/bin/bash

PIDFILE="/u/kspace/www/fluency/student-files/fall2012/a10/team-all/gameserverpid"
LOGFILE="/u/kspace/www/fluency/student-files/fall2012/a10/team-all/gameServer.log"
if [ -s $PIDFILE ] 
then
  PID=`cat $PIDFILE`
  kill -9 $PID
fi

processid=`ps aux | awk '/8097/ {print $2}' | head -1`;
kill -9 $processid;
echo $processid > /u/kspace/www/fluency/student-files/fall2012/a10/team-all/server_port.txt;

/u/kspace/local/maven/bin/mvn -f /u/kspace/www/fluency/student-files/fall2012/a10/team-all/server/pom.xml clean install > $LOGFILE 2>&1
/u/kspace/local/maven/bin/mvn -f /u/kspace/www/fluency/student-files/fall2012/a10/team-all/server/pom.xml cargo:run >> $LOGFILE 2>&1 &
pid=$!
echo $pid > $PIDFILE
