#!/bin/bash

if [ -z "$1" ] 
    then 
        echo "Specify release version as first argument"
	exit 0
fi

if [ -z "$2" ]
    then
        echo "Specify development version as second argument"
        exit 0
fi


RELEASEVERSION=${1}
DEVELOPMENTVERSION=${2}

echo "Releasing with version: $RELEASEVERSION"

mvn -T 1C versions:set -DnewVersion=$RELEASEVERSION
git add .
git commit -m "poms for release version $RELEASEVERSION"
git push origin master
git tag -a v$RELEASEVERSION -m "tag v$RELEASEVERSION"
git push --tags
mvn -T 1C clean gpg:sign deploy -Dgpg.executable=gpg2 -s ~/.m2/settings.xml
mvn -T 1C versions:set -DnewVersion $DEVELOPMENTVERSION
git add .
git commit -m "poms for development version $DEVELOPMENTVERSION"
git push origin master




