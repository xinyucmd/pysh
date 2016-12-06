package com.dx.common.bean;
/**
 * 
 * @类名 PageBean.java
 * @描述 TODO
 *  
 * @作者 jsj_spark
 * @创建时间 May 9, 2012 2012 11:36:15 AM
 * 
 * @修改者 jsj_spark
 * @修改时间 May 9, 2012 2012 11:36:15 AM
 */
public class PageBean {
	//curPage TODO
	private String curPage;
	private String pageSizeStart;
	private String pageSize;
	private String orderByCol;
	private String searchByCol;
	private String orderByVal;
	private String searchByVal;
	private String searchOperator;
	private String totalSize;
	/*********************扩展参数********************************/
	private String parm1;
	private String parm2;
	private String parm3;
	private String parm4;
	private String parm5;
	
	public String getParm1() {
		return parm1;
	}
	public void setParm1(String parm1) {
		this.parm1 = parm1;
	}
	public String getParm2() {
		return parm2;
	}
	public void setParm2(String parm2) {
		this.parm2 = parm2;
	}
	public String getParm3() {
		return parm3;
	}
	public void setParm3(String parm3) {
		this.parm3 = parm3;
	}
	public String getParm4() {
		return parm4;
	}
	public void setParm4(String parm4) {
		this.parm4 = parm4;
	}
	public String getParm5() {
		return parm5;
	}
	public void setParm5(String parm5) {
		this.parm5 = parm5;
	}
	
	
	public String getCurPage() {
		return curPage;
	}
	public void setCurPage(String curPage) {
		this.curPage = curPage;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getOrderByCol() {
		return orderByCol;
	}
	public void setOrderByCol(String orderByCol) {
		this.orderByCol = orderByCol;
	}
	public String getSearchByCol() {
		return searchByCol;
	}
	public void setSearchByCol(String searchByCol) {
		this.searchByCol = searchByCol;
	}
	public String getOrderByVal() {
		return orderByVal;
	}
	public void setOrderByVal(String orderByVal) {
		this.orderByVal = orderByVal;
	}
	public String getSearchByVal() {
		return searchByVal;
	}
	public void setSearchByVal(String searchByVal) {
		this.searchByVal = searchByVal;
	}
	public String getSearchOperator() {
		return searchOperator;
	}
	public void setSearchOperator(String searchOperator) {
		this.searchOperator = searchOperator;
	}
	public String getPageSizeStart() {
		return pageSizeStart;
	}
	public void setPageSizeStart(String pageSizeStart) {
		this.pageSizeStart = pageSizeStart;
	}
	public String getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}
}
