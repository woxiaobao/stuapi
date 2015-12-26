package common

class GlobalUtil {
	public static int STATE_SUCCESS = 1		//正常
	public static int STATE_DELETE = -1		//删除
	
	public static int QYHAO_SUCCESS = 1		//正常
	public static int QYHAO_DELETE = -1		//删除
	
	public static int AUDIT_APPLY = 1 //认证申请
	public static int AUDIT_NOT_BY = 2 //未通过
	public static int AUDIT_PASSED = 3 //认证通过
	
	public static String QYHAO_TEMPLATE = "QYHAO_TEMPLATE_INDEX"  //企用号模板
	
	public static int UP = 1		//上架
	public static int DOWN = 0		//下架
	
	/**
	 * int
	 * -1
	 * 删除
	 */
	public static int DELETE = -1		//删除
	
	
	public static int NO_PAYMENT = 0 //未付款
	public static int DELIVERED = 1 //已发货
	public static int NO_DELIVERY = 2 //未发货
	public static int APPLY_REFOUND = 3 //申请退款
	public static int AGREE_REFOUND = 4 //同意退款
	public static int REFOUND = -1 //退款成功
	public static int ORDER_SUCCESS = 5	//订单完成

	public static int DELIVERED_LOGISTICS=1	//物流发货状态
	public static int REFOUND_LOGISTICS=2	//物流退货状态
	
	//连接类型
	public static String LINK_APP = "APP"		//app
	public static String LINK_GOODS = "GOODS"		//商品
	public static String LINK_NEWS = "NEWS"		//新闻
	public static String LINK_CATEGORY = "CATEGORY"	//行业分类
	public static String LINK_HYPD = "HYPD"	//行业频道
	public static String LINK_TOPIC = "TOPIC"	//专题
	public static String LINK_COMPANY = "COMPANY"	//企用号
	public static String LINK_WEIXIN = "WEIXIN"	//微信
	
	public static final Map<String,String> LABEL_TYPES = ["1":"精品聚焦","2":"时下热门"]
	
	static enum TAG_TYPE {
		NEW,DISCOUNT,HOT
	}
}
