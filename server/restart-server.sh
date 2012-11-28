#!/bin/bash

#PIDFILE="/u/kspace/www/fluency/student-files/fall2012/a10/team-all/gameserverpid"
PIDFILE="/u/softeng/gameserverpid"
LOGFILE="/u/kspace/www/fluency/student-files/fall2012/a10/team-all/gameServer.log"
if [ -s $PIDFILE ] 
then
  PID=`cat $PIDFILE`
  kill -9 $PID
fi

server_processid=`ps aux | awk '/8097/ {print $2}' | grep -v awk | head -1`;
kill -9 $server_processid;

count=`ps aux | awk '/cargo:run/ {print $2}' | wc -l`;
while [ $count != 1 ]
do
        process_id=`ps aux | awk '/cargo:run/ {print $2}' | tail -1`;
        kill -9 $process_id;
        count=`ps aux | awk '/cargo:run/ {print $2}' | wc -l`;
done

# cargo_processid=`ps aux | awk '/cargo:run/ {print $2}' | grep -v awk | head -1`;
# kill -9 $cargo_processid;


/u/kspace/local/maven/bin/mvn -f /u/kspace/www/fluency/student-files/fall2012/a10/team-all/server/pom.xml clean install > $LOGFILE 2>&1
/u/kspace/local/maven/bin/mvn -f /u/kspace/www/fluency/student-files/fall2012/a10/team-all/server/pom.xml cargo:run >> $LOGFILE 2>&1 &
pid=$!
echo $pid > $PIDFILE
