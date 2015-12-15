#!/bin/sh

# This script will publish the HTML version of a PlayN project to a
# Web server. It should be run from Jenkins on continteg.dhcp.bsu.edu
# after a succesful build completes. Any old build with the same
# version (as specified in the project's root pom.xml file) will be
# clobbered.

# Your team's name (replace the X by 1-5)
TEAM=cs315-team5

PROJECT=`xmllint --xpath "//*[local-name()='project'='project']/*[local-name()='artifactId']/text()" pom.xml`
echo Found project: $PROJECT

VERSION=`xmllint --xpath "//*[local-name()='project'='project']/*[local-name()='version']/text()" pom.xml`
echo Found version: $VERSION

PROJECT_DIR=$PROJECT-html-$VERSION
echo Using project directory $PROJECT_DIR

cd html/target
set -x
rsync -avlz --stats --progress --delete --exclude META-INF --exclude WEB-INF -e "ssh -l $TEAM" $PROJECT_DIR $TEAM@csmadison.dhcp.bsu.edu:/home/$TEAM/public_html
set +x