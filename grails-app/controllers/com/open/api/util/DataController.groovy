package com.open.api.util

class DataController {

    def index() { }

    def sort(){
    	print "${params}"
    	if(!params.sort){
    		render status:400
    		return
    	}
    	def list=params.sort.split(",")

    	list.eachWithIndex { it,index ->
    		print "$index: $it"
    	}

    	print list.sum()

    	print list.join('-')

    	print list.sort()

    	//读取日志中的内容
    	List<String> re= new File("D://log/15.log") as List<String>
    	re.each{
    		//print it
    	}

    	File dir = new File(/D:\log/)
    	//递归输出当前目录下及子目录下的所有文件
    	dir.traverse{ file ->
    		print file.name
    	}

    	render "ok"
    }
}
