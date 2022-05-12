#!/bin/bash

for dir in *; do
   if [ -d $dir ]; then
   		echo "Building for $dir"
   		pushd $dir;
   		chmod +x gradlew;
   		./gradlew test;
   		open build/reports/tests/test/index.html;
   		popd
   fi
done
