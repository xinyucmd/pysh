package com.sp2p.service.admin;

import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.dao.admin.SendmsgDao;
import com.shove.base.BaseService;
import com.shove.data.dao.MySQL;

public class SendmsgService extends BaseService {
	public static Log log = LogFactory.getLog(SendmsgService.class);
	private SendmsgDao sendmsgDao;

	public void setSendmsgDao(SendmsgDao sendmsgDao) {
		this.sendmsgDao = sendmsgDao;
	}
   /**
    * 发送审核后的信息(修改链接)
    * @param reciver
    * @param mailTitle
    * @param content
    * @param mailType
    * @param sender
    * @return
    * @throws Exception
    */
	public Long sendCheckMail(Connection conn, long reciver, String mailTitle,
			String content, int mailType, long sender) throws Exception {
		Long resultId = -1L;
			resultId = sendmsgDao.sendCheckMail(conn,  reciver, mailTitle,
					content, mailType, sender);
		return resultId;
	}

}
