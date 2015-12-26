package com.open.api.util

import grails.converters.JSON
import common.FileUtil

class FileController {

	def grailsApplication 

    def index() { }

    def upload(){
    	println "params=${params}"
		 try{
		 	print "--"+request.multipartFiles.myFile[0]
			def tempFilePath = FileUtil.getFilePathByUpload(request.multipartFiles.myFile[0]?:request.multipartFiles."${params.mf}"[0])
			
			def showfile = tempFilePath.substring(tempFilePath.indexOf('html/')-1, tempFilePath.length())
			def result = [showfile:"${showfile}",file:"${tempFilePath}","success":"true"]
			render(text:result as JSON,contentType: "text/plain", status:response.SC_OK)
		 }catch(Exception e){
		 	println e.message
			render(status: response.SC_INTERNAL_SERVER_ERROR, text:"{success: false}")
		 }
    }
}