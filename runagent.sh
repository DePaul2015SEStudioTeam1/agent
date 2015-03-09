#!/bin/sh
###########################################################################
# This is the script that ensures cadvisor is configured correctly,
# and runs the agent application that logs back to Armada.
#
# This script should be called in the same directory as the Agent.jar file.
###########################################################################


#runagent.sh expects the URL of Armada as an argument
if [ $# -eq 0 ] ; then
    echo 'Error: Armada URL must be passed as first argument'
    exit 0
fi

ARMADA_URL=$1
CADVISOR_URL=http://localhost:8890/api/v1.2/docker

echo "Configured Armada URL: $ARMADA_URL"
echo "Configured cAdvisor URL: $CADVISOR_URL"

#docker remove cadvisor running container if already exists
docker stop cadvisor >/dev/null
docker rm cadvisor >/dev/null

#start cadvisor on port 8890
docker run \
  --volume=//:/rootfs:ro \
  --volume=/var/run:/var/run:rw \
  --volume=/sys:/sys:ro \
  --volume=/var/lib/docker/:/var/lib/docker:ro \
  --publish=8890:8080 \
  --detach=true \
  --name=cadvisor \
  google/cadvisor:latest >/dev/null

echo "cAdvisor started..."
java -jar target/agent.jar $ARMADA_URL $CADVISOR_URL
echo "Agent started!"

exit 0
