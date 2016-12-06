package com.dx.collect.bean;

import java.io.Serializable;

public class CollectBr implements Serializable {

	private static final long serialVersionUID = 701936908202659108L;

	private String id;
	private String op_no;
	private String br_no;
	private String op_name;
	private String br_name;
	private String cif_type;
	private String collect_type;
	private String del_flg;
	private String type;
	private String createDate;
	private String updateDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOp_no() {
		return op_no;
	}

	public void setOp_no(String op_no) {
		this.op_no = op_no;
	}

	public String getBr_no() {
		return br_no;
	}

	public void setBr_no(String br_no) {
		this.br_no = br_no;
	}

	public String getCif_type() {
		return cif_type;
	}

	public void setCif_type(String cif_type) {
		this.cif_type = cif_type;
	}

	public String getDel_flg() {
		return del_flg;
	}

	public void setDel_flg(String del_flg) {
		this.del_flg = del_flg;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getOp_name() {
		return op_name;
	}

	public void setOp_name(String op_name) {
		this.op_name = op_name;
	}

	public String getBr_name() {
		return br_name;
	}

	public void setBr_name(String br_name) {
		this.br_name = br_name;
	}

	public String getCollect_type() {
		return collect_type;
	}

	public void setCollect_type(String collect_type) {
		this.collect_type = collect_type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
