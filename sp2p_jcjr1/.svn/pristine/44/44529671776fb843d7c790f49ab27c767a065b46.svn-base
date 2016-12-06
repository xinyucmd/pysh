/**
 * 
 */
package com.sp2p.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author David‎-RYE
 *
 */
public class Test {

	public static void main(String[] args) {
		
		double aa = Math.floor(23112.34565656/100)*100;
		
		System.out.println(aa);
		
		
		
		String title = "定息宝-A15090036期";
		String title2 = "活力宝-"+DateUtil.dateToStringYYMM2(new Date());
		
//		title2 += title.substring(8, 12);
		
		String title2_1 = title.substring(8, 12);
		System.out.println(title2_1);
	
		if(title2_1.toCharArray()[0]=='0'){
			title2_1 = "1000";
		}else{
			title2_1 = String.valueOf(Integer.parseInt(title2_1)+1);
		}
		
		System.out.println(title2_1);
		title2 += title2_1;
		title2 += "期";
		System.out.println(title2);
	}
}
