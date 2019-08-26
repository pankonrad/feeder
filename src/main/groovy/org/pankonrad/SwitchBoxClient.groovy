package org.pankonrad

import groovy.transform.CompileStatic
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

@CompileStatic
@Client("http://192.168.0.51/s") 
interface SwitchBoxClient {
    @Get('/0/{state}') 
    String light(int state)
	
	@Get('/1/{state}') 
    String filter(int state)
}