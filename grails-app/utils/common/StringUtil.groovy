package common
import java.text.SimpleDateFormat
import org.springframework.web.util.JavaScriptUtils;
import org.apache.commons.lang.StringEscapeUtils;

class StringUtil {
	
	/***
	 * 生成16位 订单号
	 * @return
	 */
	public static String createOrderId(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		Random random = new Random();
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		int secondCount = hour*3600+minute*60+second;
		String secondNum = "${secondCount}";
		while(secondNum.length()<5){
			secondNum = "0"+secondNum;
		}
		String randomNum = "";
		for(int i=0;i<5;i++){
			randomNum =randomNum + random.nextInt(10);
		}
		return (sdf.format(cal.getTime())+secondNum+randomNum);
	}
	
	/**
	 * 生成数字和字母随机数
	 * @param length
	 * @return
	 */
	public static String getCharacterAndNumber(int length) {
		String password = ""
		Random random = new Random()
		for(int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2)%2 == 0 ? "char" : "num";
			if("char".equalsIgnoreCase(charOrNum)) {
				// 字符串
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97 //取得大写字母还是小写字母
				password += (char) (choice + random.nextInt(26))
			}else if("num".equalsIgnoreCase(charOrNum)) {
				// 数字
				password += String.valueOf(random.nextInt(10))
			}
		}

		return password
	}
	
	/**
	 * 生成数字和字母随机数
	 * @param length
	 * @return
	 */
	public static String getCharacter(int length) {
		String password = ""
		Random random = new Random()
		for(int i = 0; i < length; i++) {

			// 字符串
			// int choice = random.nextInt(2) % 2 == 0 ? 65 : 97 //取得大写字母还是小写字母
			int choice = 65 // 大写字母
			password += (char) (choice + random.nextInt(26))
		}

		return password
	}

	/**
	 * 字符串转map
	 * @param source
	 * @return
	 */
	public static HashMap parseToMap(String source) {
		def result = [:]
		String[] mapList = source.split("#,")
		mapList.each {
			String key = it.split("#:")[0]
			String value = it.split("#:")[1]
			result[key] = "${value}"
		}
		return result
	}

	/**
	 * Filter the contact info.
	 * @param content
	 * @return
	 */
	public static String filter(String content) {

		if(!content) return ''

		// filter email
		def pattern = ~/[\w-\.]+@(.|\s+|[\w-]+\.com)/
		def matcher = pattern.matcher(content)
		def count = matcher.getCount()
		(0..<count).each { i ->
			content = content.replaceAll(matcher[i][0],'*')
		}

		// filter phone number
		pattern = ~/(\d|\s|-){8,15}/
		matcher = pattern.matcher(content)
		count = matcher.getCount()
		(0..<count).each { i ->
			content = content.replaceAll(matcher[i][0],' **** ')
		}
		return content
	}

	/**
	 * 判断中英文姓名
	 */
	public static boolean ifGbkUserName(String nickName) {
		boolean isGbk = false;
		for (int i = 0; i < nickName.length(); i++) {
			String str = nickName.substring(i, i+1);
			//生成一个Pattern,同时编译一个正则表达式
			isGbk = java.util.regex.Pattern.matches("[\u4E00-\u9FA5]", str);
			if(isGbk){
				break;
			}
		}
		return isGbk;
	}

	/**
	 * 对页面中的特殊字符进行转义
	 */
	public static String getEscapeString(String content) {
		if(content == null) {
			return null;
		}

		return JavaScriptUtils.javaScriptEscape(content);
	}

	public static String getSqlEscapeString(String content) {
		if(content == null) {
			return null;
		}
		return StringEscapeUtils.escapeSql(content);
	}

	public static String getHtmlEscapeString(String content) {
		if(content == null) {
			return null;
		}
		return StringEscapeUtils.unescapeHtml(content);
	}

	def static String escapeURL(String str){
		return str.replace('%','%25').replace('+','%2B').replace(' ','%20').replace('/','%2F').replace('?','%3F').replace('#','%23').replace('&','%26').replace('=','%3D');
	}
	def static String unescapeURL(String str){
		return str.replace('%2B','+').replace('%20',' ').replace('%2F','/').replace('%3F','?').replace('%23','#').replace('%26','&').replace('%3D','=').replace('%25','%');
	}

}
