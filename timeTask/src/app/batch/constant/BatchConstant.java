package app.batch.constant;

/**
 * @author SuoZhiqi
 * @version V1.0
 * 
 * @createTime 2014��6��10��
 */
public interface BatchConstant {

	/**
	 * �͑�
	 */
	public interface Client {
		/**
		 * �ͻ�����-����
		 */
		public static final String CLIENT_TYPE_PERSON = new String("2");
	}
	/**
	 * �͑��ȼ�
	 */
	public interface Grade {
		/**
		 * �ͻ��ȼ�AAA
		 */
		public static final String GRADE_AAA = new String("AAA");
		/**
		 * �ͻ��ȼ�BBB
		 */
		public static final String GRADE_BBB = new String("BBB");
		/**
		 * �ͻ��ȼ�CCC
		 */
		public static final String GRADE_CCC = new String("CCC");
	}
	/**
	 * �弶�������
	 */
	public interface FiveClassMatrixType {
		/**
		 * ��������-����
		 */
		public static final String MATRIX_TYPE_PERSON = new String("1");
		/**
		 * ��������-��˾
		 */
		public static final String MATRIX_TYPE_CORP = new String("6");
	}
	
	/**
	 * ������ʽ
	 */
	public interface VOUTYPE {
		/**
		 * ������ʽ-��Ѻ
		 */
		public static final String ZHIYA = new String("1");
		/**
		 * ������ʽ-��Ѻ
		 */
		public static final String DIYA = new String("2");
		/**
		 * ������ʽ-��֤
		 */
		public static final String BAOZHENG = new String("3");
		/**
		 * ������ʽ-����
		 */
		public static final String XINYONG= new String("4");
	}

}
