package com.sp2p.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class createImageUtil {
	
	/**
	 * 将文本内容生成png图片--受让承诺函
	 * 
	 * @author 郭井超
	 * @date 2014-12-05
	 * @param str
	 * @param font
	 * @param outFile
	 * @throws Exception
	 */
	 public final static void createImageSrcnh(String[] param,File outFile,String createPath,String companyPath) throws Exception{
		 
		 //创建图片
		  BufferedImage image=new BufferedImage(800,1000,BufferedImage.TYPE_INT_BGR);
		  Graphics g=image.getGraphics();
		  g.setColor(Color.WHITE);
		  g.fillRect(0, 0, 800, 1000);//先用白色填充整张图片,也就是背景
		  g.setColor(Color.black);//在换成黑色
		 
		  /* 创建模板开始**/
		  g.setFont(new Font("宋体",Font.BOLD,30));
		  g.drawString("微信贷网贷产品", 270, 60);
		  g.setFont(new Font("宋体",Font.BOLD,30)); 
		  g.drawString("受让承诺函", 300, 100);
		 
		  g.setFont(new Font("宋体",Font.BOLD,25)); 
		  g.drawString("致:", 40, 180);
		  g.setFont(new Font("宋体",Font.PLAIN,25)); 
		  String type = "";
		  if(param[1].equals("3")){
			  type = "优选宝";
		  }else if(param[1].equals("4")){
			  type = "定息宝";
		  }else if(param[1].equals("6")){
			  type = "活利宝";
		  }
		  g.drawString("编号为"+param[0]+"的"+type+"产品项下全部投资人:", 90, 180);
		 
		  g.setFont(new Font("宋体",Font.BOLD,25)); 
		  g.drawString("首先，感谢您对江川金融集团旗下微信贷线上产品的信任", 82, 240);
		  g.setFont(new Font("宋体",Font.BOLD,25)); 
		  g.drawString("与大力支持！", 40, 280);
		  
		  g.setFont(new Font("宋体",Font.PLAIN,25)); 
		  g.drawString("鉴于微信贷平台编号为"+param[0]+"的"+type+"产品已经通过网", 82, 340);
		  g.setFont(new Font("宋体",Font.PLAIN,25)); 
		  g.drawString("络投标的方式匹配成功。", 40, 380);
		  
		  g.setFont(new Font("宋体",Font.BOLD,25)); 
		  g.drawString("我公司在此郑重承诺：", 82, 440);
		  
		  g.setFont(new Font("宋体",Font.PLAIN,25)); 
		  g.drawString("若债务人未按约定履行借款协议，我公司承诺受让您在该份", 82, 500);
		  g.setFont(new Font("宋体",Font.PLAIN,25)); 
		  g.drawString("借款协议项下尚未受偿的本金及预期收益。", 40, 540);
		  
		  g.setFont(new Font("宋体",Font.PLAIN,25)); 
		  g.drawString("本承诺函自您收到中企联合融资担保有限公司出具的《担保", 82, 600);
		  g.setFont(new Font("宋体",Font.PLAIN,25)); 
		  g.drawString("函》之日起自动失效。", 40, 640);

		  g.setFont(new Font("宋体",Font.BOLD,25)); 
		  g.drawString("此致", 82, 700);
		  
		  g.setFont(new Font("宋体",Font.PLAIN,25)); 
		  g.drawString("江川控股有限公司", 450, 820);
		  
		  g.setFont(new Font("宋体",Font.PLAIN,25)); 
		  
		  Calendar a=Calendar.getInstance();
		  
		  g.drawString(a.get(Calendar.YEAR)+"年"+(a.get(Calendar.MONTH)+1)+"月"+a.get(Calendar.DATE)+"日", 460, 870);
		  /* 创建模板结束**/
		  
		  g.dispose();
		  ImageIO.write(image, "png", outFile);//输出png图片
		  pressImage(companyPath, createPath,450, 700);
		  
	 }
	 
	 
	 /**
		 * 将文本内容生成png图片--回购承诺函
		 * 
		 * @author 郭井超
		 * @date 2014-12-05
		 * @param str
		 * @param font
		 * @param outFile
		 * @throws Exception
		 */
		 public final static void createImageHgcnh(String[] param,File outFile,String createPath,String companyPath) throws Exception{
			 
			 //创建图片
			  BufferedImage image=new BufferedImage(800,1000,BufferedImage.TYPE_INT_BGR);
			  Graphics g=image.getGraphics();
			  g.setColor(Color.WHITE);
			  g.fillRect(0, 0, 800, 1000);//先用白色填充整张图片,也就是背景
			  g.setColor(Color.black);//在换成黑色
			 
			  /* 创建模板开始**/
			  g.setFont(new Font("宋体",Font.BOLD,30));
			  g.drawString("微信贷线上产品", 270, 60);
			  g.setFont(new Font("宋体",Font.BOLD,30)); 
			  g.drawString("回购承诺函", 300, 100);
			 
			  g.setFont(new Font("宋体",Font.BOLD,25)); 
			  g.drawString("致:", 40, 180);
			  g.setFont(new Font("宋体",Font.PLAIN,25)); 
			  String type = "";
			  if(param[1].equals("3")){
				  type = "优选宝";
			  }else if(param[1].equals("4")){
				  type = "定息宝";
			  }else if(param[1].equals("6")){
				  type = "活利宝";
			  }
			  g.drawString("编号为"+param[0]+"的"+type+"产品项下全部投资人:", 90, 180);
			 
			  g.setFont(new Font("宋体",Font.BOLD,25)); 
			  g.drawString("首先，感谢您对江川金融集团旗下微信贷线上产品的信任", 82, 240);
			  g.setFont(new Font("宋体",Font.BOLD,25)); 
			  g.drawString("与大力支持！", 40, 280);
			  
			  g.setFont(new Font("宋体",Font.PLAIN,25)); 
			  g.drawString("鉴于微信贷平台编号为"+param[0]+"的"+type+"产品已经通过投", 82, 340);
			  g.setFont(new Font("宋体",Font.PLAIN,25)); 
			  g.drawString("投标的方式匹配成功。", 40, 380);
			  
			  g.setFont(new Font("宋体",Font.BOLD,25)); 
			  g.drawString("我公司在此郑重承诺：", 82, 440);
			  
			  g.setFont(new Font("宋体",Font.PLAIN,25)); 
			  g.drawString("若债务人未按约定履行借款协议，我公司愿意回购您在该份", 82, 500);
			  g.setFont(new Font("宋体",Font.PLAIN,25)); 
			  g.drawString("借款协议项下的本金及预期收益。", 40, 540);
			  
			  

			  g.setFont(new Font("宋体",Font.BOLD,25)); 
			  g.drawString("此致", 82, 600);
			  
			  g.setFont(new Font("宋体",Font.PLAIN,25)); 
			  g.drawString("江川控股有限公司", 450, 820);
			  
			  g.setFont(new Font("宋体",Font.PLAIN,25)); 
			  
			  Calendar a=Calendar.getInstance();
			  
			  g.drawString(a.get(Calendar.YEAR)+"年"+(a.get(Calendar.MONTH)+1)+"月"+a.get(Calendar.DATE)+"日", 460, 870);
			  /* 创建模板结束**/
			  
			  g.dispose();
			  ImageIO.write(image, "png", outFile);//输出png图片
			  pressImage(companyPath, createPath,500, 500);
			  
		 }
	  
	 /***
	  * 将盖章图片合成目标图片
	  * 
	  * @author 郭井超
	  * @date 201412-05
	  * @param pressImg 盖章图片 
	  * @param targetImg 目标图片
	  * @param 盖章x轴坐标
	  * @param 盖章y轴坐标
	  */
	 
	 public final static void pressImage(String companyPath, String createPath,int x, int y) throws Exception{
	        try {
	            //目标文件
	            File _file = new File(createPath);
	            Image src = ImageIO.read(_file);
	            int wideth = src.getWidth(null);
	            int height = src.getHeight(null);
	            BufferedImage image = new BufferedImage(wideth, height,BufferedImage.TYPE_INT_RGB);
	            Graphics2D g = image.createGraphics();
	            g.drawImage(src, 0, 0, wideth, height, null);
	 
	            //水印文件
	            File _filebiao = new File(companyPath);
	            Image src_biao = ImageIO.read(_filebiao);
	            int wideth_biao = src_biao.getWidth(null);
	            int height_biao = src_biao.getHeight(null);
	            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.6f));
	            g.drawImage(src_biao, x,y, wideth_biao+10, height_biao+10, null);
	            //g.drawImage(src_biao, (wideth - wideth_biao) /2,(height - height_biao) / 2, wideth_biao, height_biao, null);
	            //水印文件结束
	            g.dispose();
	            FileOutputStream out = new FileOutputStream(createPath);
	            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	            encoder.encode(image);
	            out.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
  
	public static void main(String[] args) {
		
		 try {
			 String[] str= {"1000","3"};
			 File outFile = new File("C:/guo/000.png");
			 createImageSrcnh(str,outFile,"C:/guo/000.png","C:/guo/target.png");
			 
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
