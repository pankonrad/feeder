package org.pankonrad

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification
import javax.inject.Inject

@MicronautTest 
class FeederTestSpec extends Specification {

    @Inject
    @Client("/")
    RxHttpClient client 

    void "test feeder response"() {
	
        when:
        HttpRequest request = HttpRequest.GET('/feeder') 
        String rsp = client.toBlocking().retrieve(request)
		println rsp
		
        then:
        rsp != null
		
    }
}