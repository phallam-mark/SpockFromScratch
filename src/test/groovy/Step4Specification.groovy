import com.xlson.groovycsv.CsvIterator
import com.xlson.groovycsv.CsvParser
import groovy.util.logging.Slf4j
import spock.lang.Shared
import spock.lang.Specification
import groovy.util.*


@Slf4j
class Step4Specification extends Specification {

    // adding environment configuration to step 2

    //@Shared because these resource will be initialized once in the setupSpec section and shared amongst multiple tests
    @Shared File file1;
    @Shared String currentEnv = "local"; //declare which environment you want to use here

    def setupSpec(){
        log.debug("setupSpec() - Runs once per Specification");
        ConfigObject config = new ConfigSlurper(currentEnv).parse(EnviroConfig);
        String filePath = config.getProperty("filePath") + config.getProperty("fileName");
        new File(filePath).delete();
        //create a file and write some CSV lines to it
        file1 = new File(filePath)
        file1.write("NAME,DIAMETER,CLIMATE,TERRAIN\n");
        file1.append("Tatooine,10465,arid,desert\n");
        file1.append("Bespin,118000,temperate,gas giant\n");
        file1.append("Kamino,19720,temperate,ocean\n");
    }
    def setup() {
        log.debug("setup() - Runs before every feature method");
    }
    def "TestCase 1 - verify that the file we created and wrote to in the setupSpec step exists"(){
        log.debug ("TestCase 1 - start");
        given : "the starWarsPlanets file has been created"
        //NA - was created in the setupSpec portion of the Spec
        when: "the existence of the file is verified"
        boolean fileExists = file1.exists();
        then: "the file should exist"
        fileExists;
    }

    def "TestCase 2 - verify that the file we created and wrote to in the setupSpec step contains the correct number of lines"(){
        log.debug ("TestCase 2 - start");
        given : "the starWarsPlanets file has been created and lines of text have been written to it"
        //NA - was created in the setupSpec portion of the Spec
        when: "the lines in the file are counted"
        ArrayList lines = file1.readLines();
        then: "the number of lines should equal 4"
        lines.size() == 4;
    }

    def "TestCase 3 - verify that we can read the contents of the starWarsPlanets file"(){
        given : "a csv file"
        //NA - file was created in setupSpec portion of the Spec
        when: "the file is read, the climate of the planet Tatooine should be arid"
        //read the contents of the file into one long string
        String csv_content = file1.getText('utf-8');
        then: "the climate of the planet Tatooine should be arid"
        boolean result = false;
        //parse the string into key value pairs using csvParser
        CsvIterator data_iterator = new CsvParser().parse(csv_content);
        //iterate through the data 'records' and see if there's a match
        for(line in data_iterator){
            if(line.NAME == "Tatooine" && line.CLIMATE == "arid"){
                result = true;
            }
        }
        result;
    }

    def cleanup(){
        log.debug ("Cleanup method - Runs after every feature method.");
    }
    def cleanupSpec(){
        log.debug ("cleanupSpec() - Runs only once per specification");
    }
}