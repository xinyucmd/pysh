package app.batch.bo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dx.back.systemdate.bean.SystemDateBean;
import com.dx.back.systemdate.services.impl.SystemDateServices;
import com.dx.collect.bean.AftChk;
import com.dx.collect.bean.CheckReportPolicy;
import com.dx.collect.bean.CollectBr;
import com.dx.collect.bean.CollectPolicy;
import com.dx.collect.bean.CollectTask;
import com.dx.collect.bean.Collection;
import com.dx.collect.bean.ZcLnPact;
import com.dx.collect.dao.AftChkDAO;
import com.dx.collect.dao.CheckReportPolicyDAO;
import com.dx.collect.dao.CollectBrDAO;
import com.dx.collect.dao.CollectPolicyDAO;
import com.dx.collect.dao.CollectTaskDAO;
import com.dx.collect.dao.CollectionDAO;
import com.dx.collect.dao.LnDueDAO;
import com.dx.collect.dao.LnPactDAO;
import com.dx.collect.dao.TblOrgUserDAO;
import com.dx.common.util.BigNumberUtil;
import com.dx.common.util.DateUtil;
import com.dx.common.util.SpringFactory;
import com.dx.loan.repay.bean.AcLnMstBean;
import com.dx.loan.repay.bean.LnDue;
import com.dx.loan.repay.dao.IRepayDao;
import com.dx.loan.repay.services.IRepayService;

/**
 * ������������
 * 
 * @author hy.yang
 *
 */
public class CollectBO {
	Log log = LogFactory.getLog(this.getClass());

	private CollectPolicyDAO collectPolicyDAO;
	private CollectionDAO collectionDAO;
	private CollectBrDAO collectBrDAO;
	private IRepayDao repayDao;
	private CheckReportPolicyDAO checkReportPolicyDAO;
	private CollectTaskDAO collectTaskDAO;
	private AftChkDAO aftChkDAO;
	private LnPactDAO lnPactDAO;
	private TblOrgUserDAO tblOrgUserDAO;
	private LnDueDAO lnDueDao;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * �����������--���
	 * 
	 */
	public void ExecTask() {
		Map<String, ArrayList<LnDue>> map = new HashMap<String, ArrayList<LnDue>>();
		Map<String, String> map1 = new HashMap<String, String>();
		// ��ȡ���еĴ��չ���
		List<CollectPolicy> plist = collectPolicyDAO.getall();
		List<CollectPolicy> pplist = new ArrayList<CollectPolicy>();
		List<CollectPolicy> nplist = new ArrayList<CollectPolicy>();
		for (CollectPolicy p : plist) {
			if ("0".equals(p.getDef_flg())) {
				nplist.add(p);
			} else {
				pplist.add(p);
			}
		}

		for (CollectPolicy p : pplist) {
			LnDue due = new LnDue();
			due.setOver_days(Integer.valueOf(p.getOverdays()));
			due.setPrdt_no(p.getPrdt_no());
			due.setCollected("0");
			List<LnDue> dueList = repayDao.getDueList(due);

			for (LnDue d : dueList) {
				map = putAdd(map, d.getMang_brno(), d);
				updateDue(d.getDue_no());
				map1.put(d.getDue_no(), p.getId());
			}
		}

		for (CollectPolicy p : nplist) {
			LnDue due = new LnDue();
			due.setOver_days(Integer.valueOf(p.getOverdays()));
			due.setCollected("0");
			List<LnDue> dueList = repayDao.getDueList(due);

			for (LnDue d : dueList) {
				map = putAdd(map, d.getMang_brno(), d);
				updateDue(d.getDue_no());
				map1.put(d.getDue_no(), p.getId());
			}
		}

		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, ArrayList<LnDue>> element = (Map.Entry<String, ArrayList<LnDue>>) it.next();
			String key = element.getKey();
			ArrayList<LnDue> duelist = element.getValue();
			CollectBr collectBr = new CollectBr();
			collectBr.setBr_no(key);
			List<CollectBr> brList = collectBrDAO.getTelMng(collectBr);
			
			if (brList!=null && brList.size() > 0) {
				for (int i = 0; i < duelist.size(); i++) {
					LnDue due = duelist.get(i);
					int index = 0;

					if (brList.size() >= duelist.size()) {
						index = i;
					} else {
						index = i % brList.size();
					}

					Collection collection = new Collection();
					collection.setPact_no(due.getPact_no());
					collection.setDue_no(due.getDue_no());
					collection.setCif_no(due.getCif_no());
					collection.setCif_name(due.getCif_name());
					collection.setCollector(brList.get(index).getOp_no());
					collection.setOp_name(brList.get(index).getOp_name());
					collection.setStage("1");
					collection.setBr(key);
					collection.setPolicy(map1.get(due.getDue_no()));
					collection.setStart_time(sdf.format(new Date()));
					collection.setCreateDate(sdf.format(new Date()));

					collectionDAO.insert(collection);
				}
			}
		}
	}

	/**
	 * ��������ת�׶�
	 * @throws ParseException 
	 * 
	 */
	public void ExecShare() throws ParseException {
		// ��ȡ���е�δ�����Ĵ�������
		List<Collection> clist = collectionDAO.getall();
		// ת�׶�
		for (Collection c : clist) {
			CollectPolicy p = collectPolicyDAO.getById(c.getPolicy());
			String days = "";
			String stage = "";
//			Calendar calendar1 = Calendar.getInstance();
//			Calendar calendar2 = Calendar.getInstance();
//			calendar1.setTime(sdf.parse(c.getStart_time()));
			int daysnow = DateUtil.getBetweenDays(sdf1.format(sdf.parse(c.getStart_time())),
					sdf1.format(new Date()));
			if ("1".equals(c.getStage())) {
				days = "0".equals(c.getProlonged()) ? p.getProlong_days() : p
						.getTeldays();
				stage = "2";
			} else if ("2".equals(c.getStage())) {
				days = p.getPerdays();
				stage = "9";
			} else {
				continue;
			}

			if (Integer.valueOf(days) < daysnow) {
				c.setShare_no("");
				c.setShare_name("");
				c.setStage(stage);
				c.setStart_time(sdf.format(new Date()));
				
				CollectBr cb = new CollectBr();
				LnDue due = lnDueDao.getByApplyPactNo(c.getPact_no());
				if (due == null) {
					System.out.println(c.getPact_no());
					continue;
				}
				cb.setBr_no(due.getMang_brno().substring(0, 4) + "00");
					
				cb = collectBrDAO.getMng(cb);
				if (cb != null && !"".equals(cb.getOp_no())) {
					c.setCollector(cb.getOp_no());
					c.setOp_name(cb.getOp_name());
					collectionDAO.changestage(c);
				}
			}
		}
	}

	/**
	 * �����ֵ�
	 * 
	 * @param m
	 * @param sr
	 * @param d
	 * @return
	 */
	private Map<String, ArrayList<LnDue>> putAdd(
			Map<String, ArrayList<LnDue>> m, String sr, LnDue d) {
		sr = sr.substring(0, 4) + "00";
		if (!m.containsKey(sr)) {
			ArrayList<LnDue> l = new ArrayList<LnDue>();
			l.add(d);
			m.put(sr, l);
		} else {
			m.get(sr).add(d);
		}
		return m;
	}

	/**
	 * �ɼ���ݼ�¼��־λ
	 * 
	 * @param due_no
	 */
	private void updateDue(String due_no) {
		LnDue due = new LnDue();
		due.setDue_no(due_no);
		due.setCollected("1");
		repayDao.setcollect(due);
	}
	
	/**
	 * ������
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	private int getWorkingDay(Calendar d1, Calendar d2) {  
        int result = -1;     
          if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end     
           java.util.Calendar swap = d1;     
           d1 = d2;     
           d2 = swap;     
          }     
          int betweendays = getDaysBetween(d1, d2);     
          int charge_date = 0;     
         int charge_start_date = 0;//��ʼ���ڵ�����ƫ����     
          int charge_end_date = 0;//�������ڵ�����ƫ����     
           // ���ڲ���ͬһ��������     
           int stmp;     
           int etmp;     
           stmp = 7 - d1.get(Calendar.DAY_OF_WEEK);     
          etmp = 7 - d2.get(Calendar.DAY_OF_WEEK);     
           if (stmp != 0 && stmp != 6) {// ��ʼ����Ϊ��������������ʱƫ����Ϊ0     
            charge_start_date = stmp - 1;     
           }     
           if (etmp != 0 && etmp != 6) {// ��������Ϊ��������������ʱƫ����Ϊ0     
            charge_end_date = etmp - 1;     
           }     
   
          result = (getDaysBetween(this.getNextMonday(d1), this.getNextMonday(d2)) / 7)     
            * 5 + charge_start_date - charge_end_date;     
           
          System.out.println("between day is-->" + betweendays);     
          return result;     
          
    }  
  
  
    private Calendar getNextMonday(Calendar date) {  
        Calendar result = null;     
          result = date;     
          do {     
           result = (Calendar) result.clone();     
           result.add(Calendar.DATE, 1);     
          } while (result.get(Calendar.DAY_OF_WEEK) != 2);     
          return result;     
    }  
  
  
    private int getDaysBetween(Calendar d1, Calendar d2) {  
        if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end     
               java.util.Calendar swap = d1;     
               d1 = d2;     
               d2 = swap;     
              }     
              int days = d2.get(java.util.Calendar.DAY_OF_YEAR)     
                - d1.get(java.util.Calendar.DAY_OF_YEAR);     
             int y2 = d2.get(java.util.Calendar.YEAR);     
              if (d1.get(java.util.Calendar.YEAR) != y2) {     
               d1 = (java.util.Calendar) d1.clone();     
               do {     
               days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);     
               d1.add(java.util.Calendar.YEAR, 1);     
               } while (d1.get(java.util.Calendar.YEAR) != y2);     
              }     
              return days;     
    }
    
	/**
	 * ������鱨��
	 * 
	 * @throws Exception
	 */
	public void ExecTrade() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// ϵͳ���ڷ�����
		SystemDateServices systemDateServices = SpringFactory.getBean("systemDateServices");
		SystemDateBean systemDateBean = new SystemDateBean();
		systemDateBean = systemDateServices.getSytstemDate();

		List<CheckReportPolicy> crpList;
		CheckReportPolicy crp;

		IRepayService repayServiceImpl = SpringFactory.getBean("repayServiceImpl");
		AcLnMstBean parmAcLnMstBean;

		// ��ѯ������Щ�����Ҫ������������
		List<AcLnMstBean> acLnMstBeans;

		CollectTask ct;
		ZcLnPact lnpact;
		// =========================================================//=========================================================
		// �״μ�鱨��
		parmAcLnMstBean = new AcLnMstBean();
		acLnMstBeans = new ArrayList();
		crpList = new ArrayList();
		crp = new CheckReportPolicy();
		ct = new CollectTask();
		lnpact = new ZcLnPact();

		crp.setReport_type("1");
		crpList = checkReportPolicyDAO.getReportPolicy(crp);// ��ȡ�״μ�鱨�����list

		parmAcLnMstBean.setDueState("1");
		for (CheckReportPolicy crpBean : crpList) {// ��ȡ����Щ�����Ҫ������������  ��һ�ڻ�����
			parmAcLnMstBean.setMainGuaran(crpBean.getVou_type());
			parmAcLnMstBean.setDueEndDate(systemDateBean.getNowDate());//���ô��ֶλ�ȡϵͳ����
			parmAcLnMstBean.setDueBegDate(crpBean.getDays_ahead());
			acLnMstBeans.addAll(repayServiceImpl.getAcLnMstBeansByReportPolicy(parmAcLnMstBean));
		}
		// �������ѱ�collectTask
		for (AcLnMstBean acLnMstBean : acLnMstBeans) {
			lnpact = lnPactDAO.getById(acLnMstBean.getPactNo());
			ct.setPact_no(acLnMstBean.getPactNo());
			ct.setCif_no(acLnMstBean.getCifNo());
			ct.setCif_name(lnPactDAO.getById(acLnMstBean.getPactNo()).getCif_name());
			ct.setPlan_time(DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT_));
			ct.setCreate_time(systemDateBean.getNowDate());
			ct.setUpdate_time(systemDateBean.getNowDate());
			ct.setBr_no(lnpact.getMang_brno());
			ct.setTerm_no("1");
			ct.setStatus("1");// 1��Ч��2����ɣ�3��ʱδ��
			ct.setMang_no(lnpact.getMang_no());
			ct.setCif_name(lnpact.getCif_name());
			collectTaskDAO.insert(ct);
		}
		System.out.println("�״μ�鱨������������ϣ��������ڣ�"+systemDateBean.getNowDate());
		// =========================================================//=========================================================
		// ���ټ�鱨��
		
		acLnMstBeans = new ArrayList();
		crpList = new ArrayList();
		crp = new CheckReportPolicy();
		ct = new CollectTask();
		lnpact = new ZcLnPact();
		AftChk aftchk = new AftChk();
		List<CollectTask> ctList = new ArrayList();

		crp.setReport_type("2");
		crpList = checkReportPolicyDAO.getReportPolicy(crp);// ��ȡ���ټ�鱨�����list

		// 1.ȡ����������ɵļ�鱨�� group by pact ȡ���к�ͬ��
		ct.setStatus("2");
		ctList = collectTaskDAO.findAllReport(ct);
		for (CollectTask ctBean : ctList) {
			AftChk aftchkBean = new AftChk();
			CollectTask ctTemp = new CollectTask();
			List<CollectTask> ctTempList = new ArrayList();
			parmAcLnMstBean = new AcLnMstBean();
			// ȡ����ǰ��ͬ�Ż�ȡ��ǰ��ͬ��collectTaskList
			ctTemp.setPact_no(ctBean.getPact_no());
			ctTemp.setStatus("2");
			ctTempList = collectTaskDAO.findByPact(ctTemp);
			if (ctTempList.size() > 0) {
				// ��collectTask����Ϣ��ȡ�����aftchk��Ϣ��Ҳ�����ϴε�
				aftchkBean.setPactNo(ctTempList.get(0).getPact_no());
				aftchkBean.setTerm_no(ctTempList.get(0).getTerm_no());
				aftchkBean = aftChkDAO.findByPact(aftchkBean);
				lnpact = lnPactDAO.getById(aftchkBean.getPactNo());
				parmAcLnMstBean.setPactNo(aftchkBean.getPactNo());
				parmAcLnMstBean = repayServiceImpl.getAcLnMstBean(parmAcLnMstBean);
				// 2.�����ϴα���Ĵ�������������ȡ�����ƶ��´μ�鱨�����Ϣ
				for (CheckReportPolicy crpBean : crpList) {// ƥ�����ĳһ������ʽ����ȷ�Χ����������������µĲ���
					if (crpBean.getVou_type().equals(aftchkBean.getVouType()) && crpBean.getAft_classify().equals(aftchkBean.getAft_classify()) && lnpact.getPact_amt() >= Double.parseDouble(crpBean.getAmt_min()) && lnpact.getPact_amt() <= Double.parseDouble(crpBean.getAmt_max())) {

						// 3.����������������²����´ε�collectTask
						String currentDate = systemDateBean.getNowDate();
						String time_interval = crpBean.getTime_interval();
						String advance_days = crpBean.getDays_ahead();
						String last_planDate = ctTempList.get(0).getPlan_time();

						if (Math.abs(DateUtil.getDays(ctTempList.get(0).getPlan_time())) == Long.parseLong(time_interval)) {
							// ����ϴμ�����ڸ��������һ�����ʱ��
							String term_no = BigNumberUtil.Add(BigNumberUtil.Divide2(String.valueOf(DateUtil.getDaysBetween(currentDate, parmAcLnMstBean.getDueBegDate())), time_interval), "1");
							long a = DateUtil.getDaysBetween(currentDate, last_planDate);
							String b = BigNumberUtil.Subtract(time_interval, advance_days);
							if (a == Long.parseLong(b)) {
								ct = new CollectTask();
								ct.setPact_no(aftchkBean.getPactNo());
								ct.setCif_no(aftchkBean.getCifNo());
								ct.setCif_name(lnpact.getCif_name());
								ct.setCreate_time(systemDateBean.getNowDate());
								ct.setUpdate_time(systemDateBean.getNowDate());
								ct.setBr_no(lnpact.getMang_brno());
								ct.setPlan_time(DateUtil.addByDay(currentDate, Integer.parseInt(advance_days)));//
								ct.setTerm_no(term_no);
								ct.setMang_no(lnpact.getMang_no());
								ct.setCif_name(lnpact.getCif_name());
								ct.setStatus("1");// 1��Ч��2����ɣ�3��ʱδ��
							}
							collectTaskDAO.insert(ct);
						}
					}
				}
			}

		}
		System.out.println("���ټ�鱨������������ϣ��������ڣ�"+systemDateBean.getNowDate());
		// =========================================================//=========================================================
		//��������ʱδ������ update collect_task t set t.status=#status# where #plan_time# >= t.plan_time and t.status='1' and t.collect_time is null
		ct = new CollectTask();
		ct.setStatus("3");
		ct.setPlan_time(systemDateBean.getNowDate());
		collectTaskDAO.UpdateNotDone(ct);
		System.out.println("��������ʱδ����������������ϣ��������ڣ�"+systemDateBean.getNowDate());
	}
	public CollectPolicyDAO getCollectPolicyDAO() {
		return collectPolicyDAO;
	}

	public void setCollectPolicyDAO(CollectPolicyDAO collectPolicyDAO) {
		this.collectPolicyDAO = collectPolicyDAO;
	}

	public CollectBrDAO getCollectBrDAO() {
		return collectBrDAO;
	}

	public void setCollectBrDAO(CollectBrDAO collectBrDAO) {
		this.collectBrDAO = collectBrDAO;
	}

	public IRepayDao getRepayDao() {
		return repayDao;
	}

	public void setRepayDao(IRepayDao repayDao) {
		this.repayDao = repayDao;
	}

	public CollectionDAO getCollectionDAO() {
		return collectionDAO;
	}

	public void setCollectionDAO(CollectionDAO collectionDAO) {
		this.collectionDAO = collectionDAO;
	}

	public CheckReportPolicyDAO getCheckReportPolicyDAO() {
		return checkReportPolicyDAO;
	}

	public void setCheckReportPolicyDAO(CheckReportPolicyDAO checkReportPolicyDAO) {
		this.checkReportPolicyDAO = checkReportPolicyDAO;
	}

	public LnPactDAO getLnPactDAO() {
		return lnPactDAO;
	}

	public void setLnPactDAO(LnPactDAO lnPactDAO) {
		this.lnPactDAO = lnPactDAO;
	}

	public CollectTaskDAO getCollectTaskDAO() {
		return collectTaskDAO;
	}

	public void setCollectTaskDAO(CollectTaskDAO collectTaskDAO) {
		this.collectTaskDAO = collectTaskDAO;
	}

	public AftChkDAO getAftChkDAO() {
		return aftChkDAO;
	}

	public void setAftChkDAO(AftChkDAO aftChkDAO) {
		this.aftChkDAO = aftChkDAO;
	}

	public TblOrgUserDAO getTblOrgUserDAO() {
		return tblOrgUserDAO;
	}

	public void setTblOrgUserDAO(TblOrgUserDAO tblOrgUserDAO) {
		this.tblOrgUserDAO = tblOrgUserDAO;
	}

	public LnDueDAO getLnDueDao() {
		return lnDueDao;
	}

	public void setLnDueDao(LnDueDAO lnDueDao) {
		this.lnDueDao = lnDueDao;
	}

}
