package com.sp2p.action.front;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.data.DataException;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.NewsAndMediaReportService;

/**
 * 前台媒体报道
 * 
 * @author Administrator
 * 
 */
public class FrontMediaReportdAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(FrontMediaReportdAction.class);
	private NewsAndMediaReportService mediaReportService;

	public NewsAndMediaReportService getMediaReportService() {
		return mediaReportService;
	}

	public void setMediaReportService(
			NewsAndMediaReportService mediaReportService) {
		this.mediaReportService = mediaReportService;
	}

	/**
	 * 初始化下载数据
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String frontQueryMediaReportdInit() throws SQLException,
			DataException {
		return SUCCESS;
	}

	/**
	 * 查询下载资料列表
	 * 
	 * @return String
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public String frontQueryMediaReportdList() throws Exception {
		String pageNum = (String) (request.getString("curPage") == null ? ""
				: request.getString("curPage"));
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(5);
		mediaReportService.queryMediaReportPage(pageBean);
		List<Map<String, Object>> lists = pageBean.getPage();

		// 截取内容字段 houli
		String content = "";
		if (lists != null) {
			for (Map<String, Object> map : lists) {
				String publishTime=map.get("publishTime").toString().trim();
				int index=publishTime.lastIndexOf(".");
				publishTime=publishTime.substring(0, index);
				map.put("publishTime", publishTime);
//				content = map.get("content").toString().replaceAll("(<(/*[a-zA-Z]+)>)|(&nbsp;)",""); 
				content = map.get("content").toString().replaceAll("(<[^>]*>)|(&nbsp;)","");
				content = content.replaceAll("\\s", "");
				if (content.length() > 60) {
					content = content.substring(0, 60);
					content = content + "...";
				}
				map.put("content", content);
			}
		}

		return SUCCESS;
	}

	/**
	 * 根据Id获取下载资料详情
	 * 
	 * @return
	 * @throws Exception
	 */
	public String frontQueryMediaReportById() throws Exception {
		Long id = request.getLong("id", -1);
		try {
			Map<String, String> map = null;
			map = mediaReportService.getMediaReportById(id);
			request().setAttribute("map", map);
			//JSONUtils.printObject(map);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 根据Id获取媒体报道
	 * 
	 * @return
	 * @throws Exception
	 */
	public String frontMedialinkId() throws Exception {
		Long id = request.getLong("id", -1);
		try {
			paramMap = mediaReportService.getMediaReportById(id);
			Map<String, String> premap = null;
			Map<String, String> aftermap = null;
			premap=mediaReportService.frontReportPreById(id);
			aftermap=mediaReportService.frontReportAfterById(id);
			request().setAttribute("premap", premap);
			request().setAttribute("aftermap", aftermap);
				
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}

		return SUCCESS;
	}

}
