#!/bin/bash
set -e

# see https://coderwall.com/p/9b_lfq

if [ "$TRAVIS_REPO_SLUG" == "olivierlemasle/plaintext-maven-plugin" ] && \
   [ "$TRAVIS_PULL_REQUEST" == "false" ] && \
   [ "$TRAVIS_BRANCH" == "master" ]; then

  echo -e "Publishing maven snapshot...\n"

  mvn clean source:jar deploy --settings="distribution/settings.xml" -Dmaven.javadoc.skip=true

  echo -e "Published maven snapshot"
fi
