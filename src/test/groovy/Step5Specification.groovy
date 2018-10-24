import groovy.util.logging.Slf4j
import groovyx.net.http.HttpResponseDecorator
import spock.lang.Shared
import spock.lang.Specification
import groovyx.net.http.RESTClient

@Slf4j
class Step5Specification extends Specification {

    // adding a call to a REST API to Step 4

    //@Shared because these resource will be initialized once in the setupSpec section and shared amongst multiple tests
    @Shared String currentEnv = "local"; //declare which environment you want to use here
    @Shared RESTClient swClient;

    def setupSpec(){
        ConfigObject config = new ConfigSlurper(currentEnv).parse(EnviroConfig);
        log.debug("setupSpec() - Runs once per Specification");
        swClient = new RESTClient( 'https://swapi.co/api/' );
        log.debug("setupSpec() - Runs once per Specification");
    }
    def setup() {
        log.debug("setup() - Runs before every feature method");
    }
    def "TestCase 1 - verify that we can successfully make a call to a REST API"(){
        log.debug ("TestCase 1 - start");
        given : "an API endpoint exists that provides data about Star Wars Movies (SWAPI)"
        //NA - was created in the setupSpec portion of the Spec
        when: "a call is made to the API"
        HttpResponseDecorator resp = swClient.get( path : 'people');
        then: "the HTTP response status should be 200"
        resp.status == 200;
    }

    def "TestCase 2 - verify the contents of the JSON response"() {
        log.debug("TestCase 2 - start");
        given: "an API endpoint exists that provides data about Star Wars Movies (SWAPI)"
        //NA - was created in the setupSpec portion of the Spec
        when: "a call is made to the API requesting data for the planet Tatooine"
        HttpResponseDecorator JsonResponse = swClient.get(path: 'planets', query: ["search": "tatooine"]);
        then: "the value of 'climate' for the planet Tatooine should be 'arid'"
        ArrayList climates = JsonResponse.getData().getAt("results").getAt("climate");
        climates.contains("arid") == true;
    }

    def cleanup(){
        log.debug ("Cleanup method - Runs after every feature method.");
    }
    def cleanupSpec(){
        log.debug ("cleanupSpec() - Runs only once per specification");
    }
}