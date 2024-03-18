@echo off
set CLASSPATH=.\target\classes;%CLASSPATH%
java -Djava.security.policy=registerit.policy es.ubu.lsi.Cliente %1
pause


