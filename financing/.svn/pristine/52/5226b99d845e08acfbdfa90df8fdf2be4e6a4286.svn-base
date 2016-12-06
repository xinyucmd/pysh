package com.jiangchuanbanking.contract.vo;

import java.io.File;
import java.util.Map;



public class GenerateContractsUtils {

	/**
	 * ��ɴ���ͬ
	 * @param variables
	 * @param path
	 */
	public  static void generatePact(Map variables,String path,String packagePath,String contractName){
			WordHelper helper = new WordHelper(packagePath+File.separator+"loanpact.ftl", 
					path, variables);
			helper.createWord();
		
	}
	
	/**
	 * ����������ŵ�ļ�
	 * @param variables
	 * @param path
	 */
	public static void generateCar(Map variables,String path){
		WordHelper helper = new WordHelper("ftlTemplate/word/cdsm.ftl", 
				path, variables);
		helper.createWord();
	}
	/**
	 * ��Ѻ�����ͬ 20130624 ���
	 * @param variables
	 * @param path
	 */
	public static void generateDyJkht (Map variables,String path){
		WordHelper helper = new WordHelper("ftlTemplate/word/jkht.ftl", 
				path, variables);
		helper.createWord();
	}
	/**
	 * ��Ѻ��ͬ�����ˣ�����߶
	 * @param variables
	 * @param path
	 */
	public static void generateDyPact(Map variables,String path){
		WordHelper helper = new WordHelper("ftlTemplate/word/dyht.ftl", 
				path, variables);
		helper.createWord();
	}
	
	/**
	 * ���ݵ�Ѻ��ͬ
	 * @param variables
	 * @param path
	 */
	public static void generateHouseDyPact(Map variables,String path,String packagePath,String contractName,String classify_name){
		
		WordHelper helper=null ;
		if ("fwsmcz".equals(classify_name)) {
			 helper = new WordHelper(packagePath+File.separator+"fwsmcz.ftl", 	path, variables);
		}else if ("fwsmkz".equals(classify_name)) {
			 helper = new WordHelper(packagePath+File.separator+"fwsmkz.ftl", 	path, variables);
		}else if ("fwsm".equals(classify_name)) {
			 helper = new WordHelper(packagePath+File.separator+"fwsm.ftl", 	path, variables);
		}else if ("zgefwdyht".equals(classify_name)){
			 helper = new WordHelper(packagePath+File.separator+"zgefwdyht.ftl", 	path, variables);
		}else if ("fwdyht".equals(classify_name)){
			 helper = new WordHelper(packagePath+File.separator+"fwdyht.ftl", 	path, variables);
		}else if ("jkht".equals(classify_name)){
			 helper = new WordHelper(packagePath+File.separator+"jkht.ftl", 	path, variables);
		}else if ("jdcdyht".equals(classify_name)){
			 helper = new WordHelper(packagePath+File.separator+"jdcdyht.ftl", 	path, variables);
		}else if ("zyht".equals(classify_name)){
			 helper = new WordHelper(packagePath+File.separator+"zyht.ftl", 	path, variables);
		}else if ("escmmht".equals(classify_name)){
			 helper = new WordHelper(packagePath+File.separator+"escmmht.ftl", 	path, variables);
		}else if ("clsqwts".equals(classify_name)){
			 helper = new WordHelper(packagePath+File.separator+"clsqwts.ftl", 	path, variables);
		}
		
			
			helper.createWord();
		
	}
	
	/**
	 * ����Ѻ��ͬ
	 * @param variables
	 * @param path
	 */
	public static void generateCarDyPact(Map variables,String path){
		WordHelper helper = new WordHelper("ftlTemplate/word/jdcdyht.ftl", 
				path, variables);
		helper.createWord();
	}
	
	/**
	 * ��ǰ��֪��
	 * @param variables
	 * @param path
	 */
	public static void generateSqgz(Map variables,String path,String packagePath,String contractName){
		WordHelper helper = new WordHelper(packagePath+File.separator+"sqgzs.ftl", 	path, variables);
		helper.createWord();
	}
	/**
	 * ��ǰ��֪��
	 * @param variables
	 * @param path
	 */
	public static void generateQyjzb(Map variables,String path,String packagePath,String contractName){
		WordHelper helper = new WordHelper(packagePath+File.separator+"qyjzb.ftl", 	path, variables);
		helper.createWord();
	}
	
	/**
	 * ������ŵ
	 * @param variables
	 * @param path
	 */
	public static void generateSmcn(Map variables,String path){
		WordHelper helper = new WordHelper("ftlTemplate/word/smcn.ftl", 
				path, variables);
		helper.createWord();
	}
	
	/**
	 * ����ͬ
	 * @param variables
	 * @param path
	 */
	public static void generateJkht(Map variables,String path,String packagePath,String contractName,String classify_name){
		WordHelper helper = null;
		if ("gsjkht".equals(classify_name)) {
			 helper = new WordHelper(packagePath+File.separator+"gsjkht.ftl", 
						path, variables);
		}else if ("hktzs".equals(classify_name)){
			 helper = new WordHelper(packagePath+File.separator+"hktzs.ftl", 
						path, variables);
		}else if ("fktzs".equals(classify_name)){
			 helper = new WordHelper(packagePath+File.separator+"fktzs.ftl", 
						path, variables);
		}else if ("jkhtzgedy".equals(classify_name)){
			 helper = new WordHelper(packagePath+File.separator+"jkhtzgedy.ftl", 
						path, variables);
		}else if ("wtsq".equals(classify_name)){
			 helper = new WordHelper(packagePath+File.separator+"wtdlxy.ftl", 
						path, variables);
		}else if ("grzgedyjkzyd".equals(classify_name)){
			 helper = new WordHelper(packagePath+File.separator+"grzgedyjkzyd.ftl", 	path, variables);
		}else if ("csht".equals(classify_name)){
			 helper = new WordHelper(packagePath+File.separator+"csht.ftl", 	path, variables);
		}
				
		helper.createWord();
		
	}
	
	public static void generatedy(Map variables,String path,String packagePath,String classify_name){
		WordHelper helper = null;
		if ("lcht".equals(classify_name)) {
			 helper = new WordHelper(packagePath+File.separator+"lcht.ftl", 
						path, variables);
		}else if("hg".equals(classify_name)){
			 helper = new WordHelper(packagePath+File.separator+"hg.ftl", 
						path, variables);
		}
				
		helper.createWord();
		
	}
	
	
	
	
	public static void generateReceiptForALoan(Map variables,String path,String packagePath,String contractName) {
		WordHelper helper = new WordHelper(packagePath+File.separator+"jj.ftl", path, variables);
		helper.createWord();
		
	}
	/**
	 * ����ͬ
	 * @param variables
	 * @param path
	 */
	public static void generateGedysxJkht(Map variables,String path){
		WordHelper helper = new WordHelper("ftlTemplate/word/gedysxjkht.ftl", 
				path, variables);
		helper.createWord();
	}
	
	/**
	 * �����˻������Ȩί����
	 * @param variables
	 * @param path
	 */
	public static void generateDksq(Map variables,String path,String packagePath,String contractName){
		WordHelper helper=null;
			helper = new WordHelper(packagePath+File.separator+"dksq.ftl", path, variables);
		helper.createWord();
	}

	public static void generateBzht(Map<String, Object> variables, String path) {
		WordHelper helper = new WordHelper("ftlTemplate/word/bzht.ftl", path, variables);
		helper.createWord();
		
	}



	public static void generateAgencyByAgreement(Map<String, Object> variables,String path) {
		WordHelper helper = new WordHelper("ftlTemplate/word/wtdl.ftl", path, variables);
		helper.createWord();
		
	}

	public static void generateZxsqs(Map<String, Object> variables, String path, String packagePath, String contractName, String classify_name) {
		WordHelper helper = null;
		if ("perszx".equals(classify_name)) {
			 helper = new WordHelper(packagePath+File.separator+"perszx.ftl", 
						path, variables);
		}else if ("corpzx".equals(classify_name)){
			 helper = new WordHelper(packagePath+File.separator+"corpzx.ftl", 
						path, variables);
		}		
		helper.createWord();
		
	}
}
