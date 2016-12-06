package com.jiangchuanbanking.SMS.cn;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


public class SingletonClient {
	private static Client client=null;
	static InputStream is = SingletonClient.class.getClassLoader().getResourceAsStream("config.properties");
	static Properties prop = new Properties();
	private SingletonClient(){
	}
	public synchronized static Client getClient(String softwareSerialNo,String key){
		if(client==null){
			try {
				client=new Client(softwareSerialNo,key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	public synchronized static Client getClient() throws IOException{
		//ResourceBundle bundle=PropertyResourceBundle.getBundle("config");
		prop.load(is);
		String softwareSerialNo = prop.getProperty("sms.softwareSerialNo");
		String key=prop.getProperty("sms.key");
//		String softwareSerialNo = bundle.getString("sms.softwareSerialNo");
//		String key=bundle.getString("sms.key");
		if(client==null){
			try {
				client=new Client(softwareSerialNo,key);//bundle.getString("softwareSerialNo"),bundle.getString("key")

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	
	
}
