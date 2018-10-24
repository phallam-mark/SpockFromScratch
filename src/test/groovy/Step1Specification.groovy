import spock.lang.Shared
import spock.lang.Specification
import groovy.util.logging.Slf4j

@Slf4j
class Step1Specification extends Specification {

    // a very simple Spock Specification comparing two strings to illustrate the form and function of the framework

    //@Shared because this resource will be initialized once in the setupSpec section and shared amongst multiple tests
    @Shared String string1;
    String string2;
    String string3;

    def setupSpec(){
        log.debug("setupSpec() - Runs once per Specification");
        string1 = "StringyStringString";
    }
    def setup() {
        log.debug("setup() - Runs before every feature method");
    }
    def "Feature method 1 - a demonstration of a test that passes"(){
        log.debug ("Feature method 1 - start");
        given : "2 strings to be compared, string 1 and string 2"
        //NA
        when: "the second string is set to be equal to the first string"
        string2 = string1;
        then: "when the two string are compared, they should be equal"
        string1.equals(string2);
        and: "if they are compared this way, they should also be equal"
        string1 == string2;
    }

    def "Feature method 2 - a demonstration of a test that fails" () {
        log.debug ("Feature method 2 - start");
        given : "2 strings to be compared, string 1 and string 3"
        //NA
        when: "the second string is set to be a lower-case version of the first string"
        string3 = string1.toLowerCase();
        then: "when the two strings are compared, they will not be equal and the test will fail"
        string1.equals(string3);
    }

    def cleanup(){
        log.debug ("Cleanup method - Runs after every feature method.");
    }
    def cleanupSpec(){
        log.debug ("cleanupSpec() - Runs only once per specification");
    }
}