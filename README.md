# SpockFromScratch
A collection of Spock Specifications that illustrate how to build a Spock specification from scratch and use Groovy features for various aspects of testing
## New Groovy / Gradle / Spock Project
Skip to end of metadata
Created by Patricia Hallam-Mark, last modified just a moment ago Go to start of metadata
based on: https://examples.javacodegeeks.com/core-java/spock-tutorial-beginners/

Below version is modified/enhanced for Loyalty Environment since the Code Geeks tutorial uses Eclipse and doesn't use the Given/When/Then test format.

## Pre-requisites:
the following are installed:

- Java 1.8
- Groovy 2.4
- Intellij IDE
- IntelliJ is configured with:

  Gradle, Groovy, Spock Framework Enhancements plugins
## Create and configure a Gradle project in Intellij IDE:
image 1 here

Select Gradle and Groovy under 'additional libraries and frameworks' and then click 'next'



GroupId can be left blank. Enter the project name in the 'ArtifactId' and leave Version as is auto-filled. Click 'next'.



 

Configure as shown below. Click next.



Define where you want the project to be created. Your directory structure may look different than this. Press 'finish'.



Intellij / Gradle will work for a minute or two downloading some dependencies.

Once it's finished, go to File > Settings > Build, Execution and Deployment > Gradle

If 'Create Directory for Empty Content Roots Automatically' isn't already checked, check it and click on 'OK'. This will create the project structure and folders.



There should be a file called 'build.gradle' in the 'src' folder of your project. Open it up and modify it so it looks like this:



Once you've done this, go to Build > Build Project. Gradle will work for a minute or two. The build should complete without errors.



Once that has completed successfully, expand the 'External Dependencies' folder in the Project pane on the left. You should see all of the dependencies specified in the 'Dependencies' section of the build.gradle in the 'External Libraries' folder. For Spock test projects, Gradle is largely used for managing dependencies. If you are having difficulties with the proper 'GroupID:ArtifactID:VersionID' syntax to use within the 'build.gradle' file to manage a dependency, here are some useful links:

Gradle Docs: Declaring Dependencies

To find the build.gradle syntax for a specific dependency, go to https://search.maven.org/ and search for the dependency. Once you've found the one you want to use, click on the version number. You will be taken to a page that provides the Gradle syntax for including that dependency. Note that although Maven is a huge repository of libraries, it is not the only one and it is possible that the dependency you are looking for is not there (and you may have to search other repositories).

Your project is now ready for code to be added.

Step 1: Add a very simple Spock specification that compares two strings
See 'Step1Specification.groovy' in the 'SpockFromScratch' project

Step 2: Add a new spec with the functionality to read from and write to a file
see 'Step2Specification.groovy' in the 'SpockFromScratch' project
NOTE: need to add 2 new dependencies for this Spec: 
groovy.util.FileNameFinder //groovy.util is part of the groovy-all package, so don't need to add to build.gradle, only need to add import statement to Spec class
com.xlson.groovycsv.* //Groovy Library for reading/parsing comma-separated files
Step 3: Add a new spec with the functionality to query a database and validate the data queried
see 'Step3Specification.groovy' in the 'SpockFromScratch' project
NOTE: need to add 2 new dependencies for this Spec:
groovy.sql.Sql //Groovy Library for interacting with DB's using SQL
org.h2.jdbcx.* //Groovy Library for creating a small, in-memory DB
Step 4: Add some more sophisticated configuration management, so Spec can easily be re-pointed to different environments
see 'Step4Specification.groovy' in the 'SpockFromScratch' project
NOTE: need to import new library into the specification class for this step (but no new dependencies):
import groovy.util.*
Also need to add new config file:
\SpockFromScratch\src\test\groovy\configuration\EnviroConfig.groovy
Step 5: Add a call to a REST API and evaluate the response
see 'Step5Specification.groovy' in the SpockFromScratch' project
NOTE: need to add 2 new dependencies for this Spec:

compile group: 'org.codehaus.groovy.modules.http-builder', name: 'http-builder', version: '0.7.1'
compile 'org.apache.httpcomponents:httpclient:4.5.3'
Step 6: Add a call to a SOAP Service and evaluate the response
see 'Step6Specification' in the 'SpockFromScratch' project
NOTE: need to add a new 'group' dependency for this Spec:

compile group: 'com.github.groovy-wslite', name: 'groovy-wslite', version: '1.1.3'

