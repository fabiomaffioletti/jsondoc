#!/bin/bash

mvn clean gpg:sign deploy -Dgpg.executable=gpg2 -s ~/.m2/settings.xml

