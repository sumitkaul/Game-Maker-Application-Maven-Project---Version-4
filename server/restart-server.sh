#!/bin/bash

PIDFILE="/u/kspace/www/fluency/student-files/fall2012/a10/team-all/gameserverpid"
LOGFILE="/u/kspace/www/fluency/student-files/fall2012/a10/team-all/gameServer.log"
if [ -s $PIDFILE ] 
then
  PID=`cat $PIDFILE`
  kill -9 $PID
fi

processid=`ps aux | awk '/8097/ {print $2}' | grep -v awk | head -1`;
kill -9 $processid;
echo $processid > /u/kspace/www/fluency/student-files/fall2012/a10/team-all/server_port.txt;
kill -9 1449
kill -9 1725
kill -9 2467
kill -9 4569
kill -9 4645
kill -9 9239
kill -9 10570
kill -9 10752
kill -9 13706
kill -9 14832
kill -9 15713
kill -9 16269
kill -9 19272
kill -9 20078
kill -9 21168
kill -9 21541
kill -9 22603
kill -9 24392
kill -9 25411
kill -9 25873
kill -9 27023
kill -9 29850
kill -9 31475
kill -9 32299
kill -9 32713


/u/kspace/local/maven/bin/mvn -f /u/kspace/www/fluency/student-files/fall2012/a10/team-all/server/pom.xml clean install > $LOGFILE 2>&1
/u/kspace/local/maven/bin/mvn -f /u/kspace/www/fluency/student-files/fall2012/a10/team-all/server/pom.xml cargo:run >> $LOGFILE 2>&1 &
pid=$!
echo $pid > $PIDFILE
