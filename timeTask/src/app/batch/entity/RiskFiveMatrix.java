package app.batch.entity;

/**
* 　 五级分类矩阵    表名：RISK_FIVE_MATRIX
* 
* @author liuniannian mailto:liuniannian.
* @date 2010-12-27
* @see
* @修改日志：
*/
public class RiskFiveMatrix implements java.io.Serializable {
	public RiskFiveMatrix() {
	}
	
	public RiskFiveMatrix(String matrix_type,String grade_type,String vou_type){
		this.matrix_type = matrix_type;
		this.grade_type = grade_type;
		this.vou_type = vou_type;
	}

	// 变量定义
	private String matrix_type; // 矩阵类型
	private String vou_type; // 担保方式
	private Integer five_1_beg; // 正常
	private Integer five_1_end; // 正常
	private Integer five_2_beg; // 关注
	private Integer five_2_end; // 关注
	private Integer five_3_beg; // 次级
	private Integer five_3_end; // 次级
	private Integer five_4_beg; // 可疑
	private Integer five_4_end; // 可疑
	private Integer five_5_beg; // 损失
	private String pd_type; // 违约类型
	private String grade_type; // 信用等级
	// set方法

	public void setMatrix_type(String matrix_type) {
		this.matrix_type = matrix_type;
	}

	public void setVou_type(String vou_type) {
		this.vou_type = vou_type;
	}

	public void setFive_1_beg(Integer five_1_beg) {
		this.five_1_beg = five_1_beg;
	}

	public void setFive_1_end(Integer five_1_end) {
		this.five_1_end = five_1_end;
	}

	public void setFive_2_beg(Integer five_2_beg) {
		this.five_2_beg = five_2_beg;
	}

	public void setFive_2_end(Integer five_2_end) {
		this.five_2_end = five_2_end;
	}

	public void setFive_3_beg(Integer five_3_beg) {
		this.five_3_beg = five_3_beg;
	}

	public void setFive_3_end(Integer five_3_end) {
		this.five_3_end = five_3_end;
	}

	public void setFive_4_beg(Integer five_4_beg) {
		this.five_4_beg = five_4_beg;
	}

	public void setFive_4_end(Integer five_4_end) {
		this.five_4_end = five_4_end;
	}

	public void setFive_5_beg(Integer five_5_beg) {
		this.five_5_beg = five_5_beg;
	}

	public void setPd_type(String pd_type) {
		this.pd_type = pd_type;
	}

	public void setGrade_type(String grade_type) {
		this.grade_type = grade_type;
	}

	// get方法
	public String getMatrix_type() {
		return matrix_type;
	}

	public String getVou_type() {
		return vou_type;
	}

	public Integer getFive_1_beg() {
		return five_1_beg;
	}

	public Integer getFive_1_end() {
		return five_1_end;
	}

	public Integer getFive_2_beg() {
		return five_2_beg;
	}

	public Integer getFive_2_end() {
		return five_2_end;
	}

	public Integer getFive_3_beg() {
		return five_3_beg;
	}

	public Integer getFive_3_end() {
		return five_3_end;
	}

	public Integer getFive_4_beg() {
		return five_4_beg;
	}

	public Integer getFive_4_end() {
		return five_4_end;
	}

	public Integer getFive_5_beg() {
		return five_5_beg;
	}

	public String getPd_type() {
		return pd_type;
	}

	public String getGrade_type() {
		return grade_type;
	}
}
