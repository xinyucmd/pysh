package com.sp2p.action.front;

import java.util.Date;
import java.util.Map;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.sp2p.constants.IConstants;
import com.sp2p.service.PartenersService;
import com.sp2p.util.CookieUtil;
import com.sp2p.util.HttpClientHelp;


public class PartenersAction extends BaseFrontAction{
	
	private static final long serialVersionUID = 1L;
	
	private PartenersService partenersService;
	public static Log log = LogFactory.getLog(PartenersAction.class);
	
	/**
	 * 广告商到达方法
	 * @author zcl
	 * @return
	 */
	public String outer(){
		try {
			String pid = request().getParameter("jc_pid");
			String key = request().getParameter("jc_key");
			
			if(StringUtils.isNotBlank(pid) && StringUtils.isNotBlank(key)){
				String newKey = com.shove.security.Encrypt.MD5(pid + IConstants.PASS_KEY);
				if(StringUtils.isNotBlank(key) && key.equals(newKey)){
					Map<String,String> parteners = partenersService.queryParteners(Convert.strToLong(pid, -1));
					if(parteners!=null){
						String url = String.valueOf(parteners.get("url"));
						if(StringUtils.isNotBlank(url)){
							CookieUtil.setCookie(response(), "patener_id", pid);
							//whb 区分广告商
							if("1".equals(pid)){
								//获取uid
								String uid = request().getParameter("uid");
								CookieUtil.setCookie(response(), "patener_uid", uid);
								partenersService.addParenersMessage(1, "来自富爸爸的跳转成功", url, -1L, 1, 0, 0, "", new Date());
								getOut().print("<script>window.location.href='"+url+"';</script>");
								log.info("来自富爸爸的跳转成功：url="+url);
							}
							//GEO
							else if("2".equals(pid)){
								String executeid = request().getParameter("executeid");
								String pidGeo = request().getParameter("pid");
								CookieUtil.setCookie(response(), "patener_executeid", executeid);
								CookieUtil.setCookie(response(), "patener_pidGeo", pidGeo);
								String result = spliceUrl(executeid, pidGeo, -1);
								if("success".equals(result)){
									partenersService.addParenersMessage(1, "来自GEO的跳转成功", url, -1L, 2, 0, 0, "", new Date());
									getOut().print("<script>window.location.href='"+url+"';</script>");
									log.info("来自GEO的跳转成功：url="+url);
								}else if("error".equals(result)){
									partenersService.addParenersMessage(4, "广告商参数有误", url, -1L, 2, 0, 0, "", new Date());
									log.error("GEO广告商参数设置错误，URL="+url);
									return null;
								}
							}
							//黄金投资网
							else if("4".equals(pid)){
								//uid:黄金投资网查看统计数据
								String uid = request().getParameter("uid");
								if("99".equals(uid)){
									url += "dataProvidedToJintou.do?curPage=1&pid="+pid;
									partenersService.addParenersMessage(2, "来自黄金投资网查看统计数据的跳转成功", url, -1L, 4, 0, 0, "", new Date());
									getOut().print("<script>window.location.href='"+url+"';</script>");
									log.info("来自黄金投资网查看统计数据的跳转成功：url="+url);
								}else{
									url += "page/activity/20150325/activity.html";
									CookieUtil.setCookie(response(), "patener_uid", uid);
									partenersService.addParenersMessage(1, "来自黄金投资网的跳转成功", url, -1L, 4, 0, 0, "", new Date());
									getOut().print("<script>window.location.href='"+url+"';</script>");
									log.info("来自黄金投资网的跳转成功：url="+url);
								}
							}
							//希财网
							else if("5".equals(pid)){
								String uid = request().getParameter("uid");
								String id = request().getParameter("id");
								if("1002".equals(uid)){
									url += "financeDetail.do?id="+id;
									CookieUtil.setCookie(response(), "patener_uid", uid);
									partenersService.addParenersMessage(1, "来自希财网的跳转成功", url, -1L, 5, 0, 0, "", new Date());
									getOut().print("<script>window.location.href='"+url+"';</script>");
									log.info("来自希财网的跳转成功：url="+url);
								}
							}
							
							//棕榈树
							else if("9".equals(pid)){
								CookieUtil.setCookie(response(), "patener_id", pid);
								partenersService.addParenersMessage(1, "来自棕榈树跳转成功", url, -1L, 9, 0, 0, "", new Date());
								getOut().print("<script>window.location.href='"+url+"';</script>");
								log.info("来自棕榈树跳转成功：url="+url);
							}
							
							//九盟互动
							else if("10".equals(pid)){
								String uid = request().getParameter("uid");//九盟渠道uid;
								CookieUtil.setCookie(response(), "patener_id", pid);
								CookieUtil.setCookie(response(), "patener_uid", uid);
								partenersService.addParenersMessage(1, "来自九盟互动跳转成功", url, -1L, 10, 0, 0, "", new Date());
								getOut().print("<script>window.location.href='"+url+"';</script>");
								log.info("来自九盟互动跳转成功：url="+url);
							}
						}else{
							partenersService.addParenersMessage(4, "广告商参数有误", url, -1L, 0, 0, 0, "", new Date());
							log.error("广告商参数设置错误，URL="+url);
							return null;
						}
					}else{
						getOut().print("<script>alert('无效的广告商! ');window.location.href='index.jsp';</script>");
						return null;
					}
				}else{
					getOut().print("<script>alert('广告商参数有误! ');window.location.href='index.jsp';</script>");
					return null;
				}
			}else{
				getOut().print("<script>alert('广告商参数有误! ');window.location.href='index.jsp';</script>");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				getOut().print("<script>alert('广告商调用异常! ');window.location.href='index.jsp';</script>");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return null;
	}
	
	/**
	 * 返回拼接好的url给广告商
	 * @author whb
	 * @return
	 * @throws Exception 
	 */
	public String spliceUrl(String executeid, String pidGeo, long userId) throws Exception{
		String result = "";
		StringBuffer url = new StringBuffer();
		//GEO
		url.append("http://lg.istreamsche.com/cpa.gif?ref=");
		url.append("&executeid="+executeid);
		url.append("&pid="+pidGeo);
		if(userId > 0){
			String userid = com.shove.security.Encrypt.encrypt3DES(String.valueOf(userId), IConstants.PASS_KEY);
			url.append("&userid="+userid);
		}
		url.append("&r=");
		partenersService.addParenersMessage(6, "来自GEO的参数回传", url.toString(), -1L, 2, 0, 0, "", new Date());
		log.info("来自GEO的参数回传：url="+url);
		//返回url
		HttpClientHelp httpClientHelp = new HttpClientHelp();
		InputStream io = httpClientHelp.byGetMethodToInputStream(url.toString());
		if(io != null){
			result = "success";
		}else{
			result = "error";
		}
		return result;
	}

	public PartenersService getPartenersService() {
		return partenersService;
	}

	public void setPartenersService(PartenersService partenersService) {
		this.partenersService = partenersService;
	}
	
}
