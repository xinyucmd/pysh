/**
 * Copyright (C) DXHM 版权所有
 * 文件名： SystemParm.java
 * 包名： com.dx.common
 * 说明：
 * @author 乾之轩
 * @date 2012-5-16 上午09:34:36
 * @version V1.0
 */ 
package com.dx.common;

import com.dx.back.systemdate.services.impl.SystemDateServices;
import com.dx.common.util.SpringFactory;

/**
 * 类名： SystemParm
 * 描述： 系统参数
 * @author 乾之轩
 * @date 2012-5-16 上午09:34:36
 *
 *
 */
public class SystemParm {
	// 系统日期
	 public static String SystemDate="";
	// 提前还款时利息计算到当前日期还是计算到当期,0 计算到当前日期,1计算到当前结束日期
	public static String RateEndDay="0";
	// 一年的天数
	public static int DaysOfYear = 365;
	// 利率调整后(立即生效)的策略 1 次日生效 2 立即生效
	public static int EffectiveType = 1;
	
	/**放款之前有利率调整的处理方式**/
	/** 0  根据计息方式,首先计算出生效点.然后进行分段计算.
	*	如:5月5号为合同开始日期 5月7号进行利率调整并且计息方式为按月,5月10 号进行放款,那么5.7---6.7 使用老利率6.7—借据结束日使用新利率	
	*	1 使用新利率
	*	从放款日开始直接使用新利率
	**/
	public static int BeforeRateAdjust = 1;
	/**
	 * 按计划每期的天数采用哪种方式计算  0 (默认)和1 
	 */
	public static int  PLAN_DAY_TYPE=0;  
	/**
	 * 0 还款计划是闭区间 如:2012-05-25 --2012-06-25   1 还是半闭半开2012-05-25 --2012-06-24 
	 */
	public static int PLAN_END=0;
	/**
	 *  最后一期如果不足一期是否按照整期计算 0 不是 1是
	 */
	public static int LAST_TERM_FULL=1;
	/**
	 * 最后一期是否开区间 
	 * 0 不是如(开始日期为2012-05-20  结束日期为2013-05-19)
	 * 1是开区间(开始日期为2012-05-20  结束日期为2013-05-20)           
	 */
	public static int  LAST_TERM_OPEN=1;
	/**
	 * 计算利息时使用的利率类型 
	 * 0 日利率
	 * 1 月利率
	 */
	public static int  USE_RATE_TYPE=1;
	/**
	 * 当选月利率,并且不是足月的处理
	 * 0  足月部分使用月利率计算,不足月部分使用日利率计算
	 * 1 使用日利率计算
	 */
	public static int  RATE_NOT_FULL=0;
	
	
	/**
	 * 在用日利率计算时一个月的天数
	 * 0 实际天数
	 * 1 足月按30天计算
	 */
	public static int MONTH_OF_DAYS=0;
	
	/**
	 * 是否有固定还款日期
	 * 0 没有 
	 * 1 有
	 */
	public static int IS_FIX_DAY=1;
	
	/**
	 * 固定还款日期的类型
	 * 0 任意日期
	 * 1 设定日期
	 */
	public static int FIX_DAY_TYPE;
	/**
	 * 是否延期  只有选择固定还款日后该设置才能起作用
	 * 0 不延期
	 * 1 延期
	 */
	public static int IS_DELAY=1;
	/**
	 * 固定还款日还款时截取部分的处理方式
	 * 0  第一期收取
	 * 1 单独当做一期有本金
	 * 2 单独做为一期无本金
	 */
	public static int  DEAL_ADVANCE_TYPE=2;
	/**
	 * 固定还款日时预先截取出来的(首期)的结束日期时在本月还是下一个月
	 * 0 本月
	 * 1 下一月 
	 */
	public static int ADVANCE_END_TYPE=0;
	
	/**
	 * 从结束日到延期日期是否收取利息
	 * 0 不收取
	 * 1 收取
	 */
	public static int GATHER_DELAY_RATE = 0;
	
	/**
	 * 判断是否强制计划
	 * 0  不是
	 * 1 是
	 */
	public static int IS_FORCE = 1 ;
	/**
	 * 是否收取账户管理费
	 * 0  不收取
	 * 1  收取
	 */
	public static int ACCOUNT_FEE = 1; 
	
	
	
    /***************还款参数开始***************/    
	/**
	 * 是否允许优惠
	 * 0 不能优惠
	 * 1 能优惠
	 */
	public static int  IS_PRIVILEGE=1;
	
	
	/***************还款参数结束***************/    
	
	
	
	
	
	
	
	/*********************系统常量开始*****************/
	
	/**
	 *  计息方式常量说明
	 */

	/** 固定利率 */
	public static final String RATE_WAY_FIX = "1";

	/** 按月调整 */
	public static final String RATE_WAY_MONTH = "2";

	/** 按月对日调整 */
	public static final String RATE_WAY_MONTH_DAY = "3";

	/** 按季调整 */
	public static final String RATE_WAY_SEASON = "4";

	/** 按季对日调整 */
	public static final String RATE_WAY_SEASON_DAY = "5";

	/** 按年调整 */
	public static final String RATE_WAY_YEAR = "6";

	/** 按年对月对日调整 */
	public static final String RATE_WAY_YEAR_MONTH_DAY = "7";

	/** 立即调整 */
	public static final String RATE_WAY_IMMEDIATELY = "8";

	/** 固定利息 */
	public static final String RATE_WAY_FIX_INTEREST = "9";
	
	
	
	
	/**利率类型**/
	// 日利率类型
	public static final String RATE_DAY="1";
	// 月利率类型
	public static final String RATE_MONTH="2";
	// 年利率类型
	public static final String RATE_YEAR="3";
	
	
	
	/**
	 *  还款方式常量说明
	 */

	/** 等额本息 */
	public static final String RETURNMETHOD_INTEREST= "4";

	/** 等额本金 */
	public static final String RETURNMETHOD_PRINCIPAL = "1";

	/** 利随本清 */
	public static final String RETURNMETHOD_PROFITSCLEAR = "3";

	/** 到期还本金按月结息 */
	public static final String RETURNMETHOD_MONTH = "2";

	/** 到期还本金按季结息 */
	public static final String RETURNMETHOD_SEASON = "5";

	/** 按计划 */
	public static final String RETURNMETHOD_PLAN = "B";
	
	
	/**
	 * 合同状态
	 */
	// 待补录状态
	public static final String PACT_STS_ADDIT="0";
	// 补录完成还没有放款
	public static final String PACT_STS_PUT="1";
	// 完成放款还没有生成还款计划
	public static final String PACT_STS_PLAN="2";
	
	
	
	/*********************系统常量结束*****************/
	
	

	

}
