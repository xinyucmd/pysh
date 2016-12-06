package com.sp2p.service.brokerage;

import java.util.Map;

/**
 *  佣金接口
 * @author ZhaoChenglong
 */
public interface IBrokerage {
	
	public void updateBrokerage(Long userId,double sumMoney,Map<String,Object> param);
}
