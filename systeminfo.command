echo off
cd /Users/Jet2kus84/Software/git/agent/
java -jar sigar-bin/sigar.jar free > freeMemoryFile.txt
java -jar sigar-bin/sigar.jar version > versionFile.txt
java -jar sigar-bin/sigar.jar netinfo > ipFile.txt
java -jar sigar-bin/sigar.jar df > diskFile.txt
java -jar sigar-bin/sigar.jar cpuinfo > cpuFile.txt