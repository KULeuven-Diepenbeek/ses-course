#!/bin/bash

chmod +x gradlew
./gradlew compileJava
cat /Users/wgroeneveld/Downloads/ses-assignments/voorbeeld.input | java -cp build/classes/java/main/ be.kuleuven.ses.capita.Main > vb.out
diff vb.out /Users/wgroeneveld/Downloads/ses-assignments/voorbeeld.output
cat /Users/wgroeneveld/Downloads/ses-assignments/voorbeeld.input | java -cp build/classes/java/main/ be.kuleuven.ses.capita.Main > wed.out
diff wed.out /Users/wgroeneveld/Downloads/ses-assignments/wedstrijd.output
