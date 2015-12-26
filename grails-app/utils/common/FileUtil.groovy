package common

import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.text.SimpleDateFormat

import javax.imageio.ImageIO

import org.apache.log4j.Logger
import org.apache.tools.zip.ZipEntry
import org.apache.tools.zip.ZipFile
import grails.converters.JSON
import com.sun.image.codec.jpeg.JPEGCodec
import com.sun.image.codec.jpeg.JPEGEncodeParam
import com.sun.image.codec.jpeg.JPEGImageEncoder
class FileUtil {

	def static grailsApplication
	def static springSecurityService
	def static Logger log = Logger.getLogger(this);
	/**
	 * 得到id路径
	 * @return
	 */
	def static getIdPath() {
		def company = springSecurityService.currentUser?.company
		return "${company?.id?:0}"
	}
	
	/**
	 * 文件是否是图片
	 * @param fileNameWithoutPath
	 * @return
	 */
	def static isImage(def file) {
		if(!file.exists()) {  
	        return false
	    }  
	    def img
	    try {  
	        img = ImageIO.read(file)
	        if(!img || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {  
	            return false
	        }  
	        return true
	    } catch (Exception e) {  
	        return false
	    } finally {  
	        img = null
	    }
	}

	

	/**
	 * 上传本地文件，返回文件路径
	 * @param multipartFile
	 * @return
	 */
	def static getFilePathByUpload(def multipartFile) {
		try {

			// 上传图片
			def tempPath = "${grailsApplication.config.filePath.temp}/";
			//def tempPath = "D:/images/temp/";
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			def tempFilePath = tempPath + df.format(date)+"/";
			
			def f = new File(tempFilePath)
			if(!f.exists()) {
				f.mkdirs()
			}
			def fileNameWithoutPath	// 图片文件名
			def fileAbsolutePath = multipartFile.fileItem.name	// 文件路径
			def filename	// 文件路径  + 随机数  + 文件名 --> 文件路径  + 随机数  + 后缀名（由于文件名上传出错）
			if(fileAbsolutePath.indexOf('.') != -1) {
				fileNameWithoutPath = StringUtil.getCharacterAndNumber(10) + fileAbsolutePath.substring(fileAbsolutePath.lastIndexOf("."), fileAbsolutePath.length())
			} else {
				fileNameWithoutPath = StringUtil.getCharacterAndNumber(10) + '.jpg'
			}

			filename = tempFilePath + fileNameWithoutPath
			def file = new File(filename)

			// 上传到本地
			multipartFile.transferTo(file)
			print filename
			return filename

		} catch(Exception e) {
			println e.message
			e.printStackTrace()
		}
	}
	
	/**
	 * 上传本地文件，返回文件路径
	 * @param name
	 * @param path
	 * @param size
	 * @param md5
	 * @return
	 */
	def static getFilePathByNginxUpload(def name, def path, def size = null, def md5 = null) {
		try {

			// 上传图片
			def tempFilePath = "${grailsApplication.config.filePath.temp}/"
			def f = new File(tempFilePath)
			if(!f.exists()) {
				f.mkdirs()
			}
			def fileNameWithoutPath	// 图片文件名
			def fileAbsolutePath = name	// 文件路径
			def filename	// 文件路径  + 随机数  + 文件名 --> 文件路径  + 随机数  + 后缀名（由于文件名上传出错）
			if(fileAbsolutePath.indexOf('.') != -1) {
				fileNameWithoutPath = StringUtil.getCharacterAndNumber(10) + fileAbsolutePath.substring(fileAbsolutePath.lastIndexOf("."), fileAbsolutePath.length())
			} else {
				fileNameWithoutPath = StringUtil.getCharacterAndNumber(10) + '.jpg'
			}

			filename = tempFilePath + fileNameWithoutPath
			copy(path, filename)
			
			return filename

		} catch(Exception e) {
			println e.message
			e.printStackTrace()
		}
	}

	
	/**
	 * 得到截图后的图片路径
	 * @param srcImgPath
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	def static subImg(def srcImgPath, def x, def y, def width, def height) {
		try {

			def file = new File(srcImgPath)
			def is = new FileInputStream(file)
			def srcImage = ImageIO.read(is)
			def subImageBounds = new Rectangle(x, y, width, height)
			def tempFilePath = "${grailsApplication.config.filePath.temp}/"	// 文件路径
			def fileNameWithoutPath	// 图片文件名
			def filename	// 文件路径  + 随机数  + 文件名
			if(srcImgPath.indexOf('.') != -1) {
				fileNameWithoutPath = StringUtil.getCharacterAndNumber(10) + srcImgPath.substring(srcImgPath.lastIndexOf("."), srcImgPath.length())
			} else {
				fileNameWithoutPath = StringUtil.getCharacterAndNumber(10) + '.jpg'
			}
			def targetImgPath = tempFilePath + fileNameWithoutPath
            
    		String os = System.getProperty("os.name")    		
    		if (os.indexOf("indow") == -1){
    			def shellError = "convert -crop ${width}x${height}+${x}+${y} ${srcImgPath} ${targetImgPath}".execute().errorStream?.text
				if(shellError) println 'subImg' + shellError
    		} else {

    			BufferedImage subImage = srcImage.getSubimage(subImageBounds.x.intValue(), subImageBounds.y.intValue(), subImageBounds.width.intValue(), subImageBounds.height.intValue())
    			String formatName = targetImgPath.substring(targetImgPath.lastIndexOf('.') + 1)
    			def f = new File(targetImgPath)
    			def fDir = new File(tempFilePath)
    			if(!fDir.exists()) {
    				fDir.mkdirs()
    			}
			
    			FileOutputStream out = new FileOutputStream(f)
    			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out)
    			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(subImage)
    			param.setQuality(1.0f, true)
    			encoder.encode(subImage, param)
    			ImageIO.write(subImage, formatName, out)
    			out.close()
			
    //			ImageIO.write(subImage, formatName, f)
    			is.close()
    		}

			return targetImgPath
		} catch(Exception e) {
			println e.message
			e.printStackTrace()
		}
	}

	
}
