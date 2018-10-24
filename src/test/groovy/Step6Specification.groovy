import groovy.util.logging.Slf4j
import spock.lang.Shared
import spock.lang.Specification
//below imports are new for this spec
import groovy.xml.*
import wslite.soap.*
import wslite.http.auth.*


@Slf4j
class Step6Specification extends Specification {

    // adding a call to a SOAP Service to Step 4

    //@Shared because these resource will be initialized once in the setupSpec section and shared amongst multiple tests
    @Shared String currentEnv = "local"; //declare which environment you want to use here
    @Shared String wsdlURL = "http://www.holidaywebservice.com//HolidayService_v2/HolidayService2.asmx?wsdl";
    @Shared SOAPClient client;

    def setupSpec(){
        ConfigObject config = new ConfigSlurper(currentEnv).parse(EnviroConfig);
        log.debug("setupSpec() - Runs once per Specification");
        client = new SOAPClient(wsdlURL);
    }

    def setup() {
        log.debug("setup() - Runs before every feature method");
    }

    def "TestCase 1 - verify that we can successfully make a call to SOAP Service"(){
        log.debug ("TestCase 1 - start");
        given : "a SOAP Service endpoint exists that provides data about holiday dates"
        //NA - was created in the setupSpec portion of the Spec
        when: "a call is made to the SOAP Service"
        //this commented out stuff also works, but a less elegant way of doing it
        /*SOAPResponse response = client.send(
                """<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hs="http://www.holidaywebservice.com/HolidayService_v2/">
                            <soapenv:Body>
                            <hs:GetHolidayDate>
                            <hs:countryCode>Canada</hs:countryCode>
                            <hs:holidayCode>VICTORIA-DAY</hs:holidayCode>
                            <hs:year>2018</hs:year>
                            </hs:GetHolidayDate>
                            </soapenv:Body>
                            </soapenv:Envelope>"""
                ) */
        SOAPResponse response = client.send(SOAPAction: 'http://www.holidaywebservice.com/HolidayService_v2/GetHolidayDate'){
            body {
                GetHolidayDate ('xmlns':'http://www.holidaywebservice.com/HolidayService_v2/') {
                    countryCode('Canada');
                    holidayCode('VICTORIA-DAY');
                    year('2018');
                }
            }
        }
        then: "the HTTP response status should be 200"
        response.getHttpResponse().statusCode == 200;
        and: "the date of Victoria Day in 2018 is May 21"
        String date = response.GetHolidayDateResponse.GetHolidayDateResult.toString();
        date.contains("2018-05-21");
    }


    def cleanup(){
        log.debug ("Cleanup method - Runs after every feature method.");
    }
    def cleanupSpec(){
        log.debug ("cleanupSpec() - Runs only once per specification");
    }
}