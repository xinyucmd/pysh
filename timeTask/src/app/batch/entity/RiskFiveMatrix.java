package app.batch.entity;

/**
* �� �弶�������    ������RISK_FIVE_MATRIX
* 
* @author liuniannian mailto:liuniannian.
* @date 2010-12-27
* @see
* @�޸���־��
*/
public class RiskFiveMatrix implements java.io.Serializable {
	public RiskFiveMatrix() {
	}
	
	public RiskFiveMatrix(String matrix_type,String grade_type,String vou_type){
		this.matrix_type = matrix_type;
		this.grade_type = grade_type;
		this.vou_type = vou_type;
	}

	// ��������
	private String matrix_type; // ��������
	private String vou_type; // ������ʽ
	private Integer five_1_beg; // ����
	private Integer five_1_end; // ����
	private Integer five_2_beg; // ��ע
	private Integer five_2_end; // ��ע
	private Integer five_3_beg; // �μ�
	private Integer five_3_end; // �μ�
	private Integer five_4_beg; // ����
	private Integer five_4_end; // ����
	private Integer five_5_beg; // ��ʧ
	private String pd_type; // ΥԼ����
	private String grade_type; // ���õȼ�
	// set����

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

	// get����
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
