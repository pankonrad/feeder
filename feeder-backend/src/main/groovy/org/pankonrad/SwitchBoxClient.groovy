package org.pankonrad

import groovy.transform.CompileStatic
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

@CompileStatic
@Client("http://10.0.0.253")
interface SwitchBoxClient {
    @Get('/s/0/{state}') 
    String light(int state)
	
	@Get('/s/1/{state}') 
    String filter(int state)
	
	@Get('/api/relay/state')
    String state()
}