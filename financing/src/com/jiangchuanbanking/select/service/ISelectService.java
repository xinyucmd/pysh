package com.jiangchuanbanking.select.service;

import java.util.List;

import com.jiangchuanbanking.select.domain.WealthParnDic;



public interface ISelectService {
	
	public List<WealthParnDic> queryWPD(String keyCode) throws Exception;

	public List<WealthParnDic> queryWPD();
    
	public String getSubParentId();

	public String getOpCnName(String keyCode,String opCode);
}
