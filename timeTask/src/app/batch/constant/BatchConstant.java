package app.batch.constant;

/**
 * @author SuoZhiqi
 * @version V1.0
 * 
 * @createTime 2014年6月10日
 */
public interface BatchConstant {

	/**
	 * 客戶
	 */
	public interface Client {
		/**
		 * 客户类型-个人
		 */
		public static final String CLIENT_TYPE_PERSON = new String("2");
	}
	/**
	 * 客戶等级
	 */
	public interface Grade {
		/**
		 * 客户等级AAA
		 */
		public static final String GRADE_AAA = new String("AAA");
		/**
		 * 客户等级BBB
		 */
		public static final String GRADE_BBB = new String("BBB");
		/**
		 * 客户等级CCC
		 */
		public static final String GRADE_CCC = new String("CCC");
	}
	/**
	 * 五级分类矩阵
	 */
	public interface FiveClassMatrixType {
		/**
		 * 矩阵类型-个人
		 */
		public static final String MATRIX_TYPE_PERSON = new String("1");
		/**
		 * 矩阵类型-公司
		 */
		public static final String MATRIX_TYPE_CORP = new String("6");
	}
	
	/**
	 * 担保方式
	 */
	public interface VOUTYPE {
		/**
		 * 担保方式-质押
		 */
		public static final String ZHIYA = new String("1");
		/**
		 * 担保方式-抵押
		 */
		public static final String DIYA = new String("2");
		/**
		 * 担保方式-保证
		 */
		public static final String BAOZHENG = new String("3");
		/**
		 * 担保方式-信用
		 */
		public static final String XINYONG= new String("4");
	}

}
