
package com.jiangchuanbanking.report.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.struts2.ServletActionContext;

import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.dict.domain.SalesStage;
import com.jiangchuanbanking.financing.domain.Opportunity;
import com.jiangchuanbanking.report.vo.ChartVO;
import com.jiangchuanbanking.system.domain.Role;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.UserUtil;
import com.jiangchuanbanking.util.Constant;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Manages the Opportunity By Stage Report
 * 
 */
public class OpportunityByStageReportAction extends ActionSupport {

    private static final long serialVersionUID = -2404576552417042445L;
    private IBaseService<Opportunity> baseService;
    private List<ChartVO> list;
    private Map<String, Object> map;
    private String report;
    private String reportType;

    /**
     * Pie chart
     */
    @Override
    public String execute() throws Exception {
        UserUtil.permissionCheck("view_opportunity");
        setList();
        String reportPath = "/reports/pie.jasper";
        setExporter(reportPath);
        reportType = ChartVO.CHART_PIE;
        return SUCCESS;
    }

    /**
     * Bar chart
     */
    @SuppressWarnings("unused")
    public String bar() throws Exception {
        UserUtil.permissionCheck("view_opportunity");
        setList();
        HttpServletRequest request = ServletActionContext.getRequest();
        String reportPath = "/reports/bar.jasper";
        setExporter(reportPath);
        reportType = ChartVO.CHART_BAR;
        return SUCCESS;
    }

    private void setExporter(String reportPath) throws JRException {
        HttpServletRequest request = ServletActionContext.getRequest();
        String path = request.getSession().getServletContext()
                .getRealPath(reportPath);
        File reportFile = new File(path);
        if (!reportFile.exists()) {
            throw new JRRuntimeException("Jasper report file is not found");
        }
        JasperReport jasperReport = (JasperReport) JRLoader
                .loadObjectFromFile(reportFile.getPath());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                map, new JRBeanCollectionDataSource(list));
        JRHtmlExporter exporter = new JRHtmlExporter();
        StringBuffer buffer = new StringBuffer();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, buffer);
        exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
                Constant.APP_PATH + "/servlets/image?image=");
        exporter.setParameter(
                JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                Boolean.TRUE);
        exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, "");
        exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT,
                JRHtmlExporterParameter.SIZE_UNIT_POINT);
        exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT,
                JRHtmlExporterParameter.SIZE_UNIT_PIXEL);
        exporter.setParameter(JRHtmlExporterParameter.ZOOM_RATIO, 1.0f);
        exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "");
        exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, "");
        exporter.exportReport();
        report = buffer.toString();
        request.getSession().setAttribute(
                ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,
                jasperPrint);
    }

    @SuppressWarnings({ "rawtypes" })
    private void setList() {
        User loginUser = UserUtil.getLoginUser();
        int scope = loginUser.getScope_account();
        StringBuilder hqlBuilder = new StringBuilder(
                "select sales_stage,count(opportunity.id) from Opportunity opportunity join opportunity.sales_stage sales_stage group by sales_stage.id");

        if (scope == Role.OWNER_OR_DISABLED) {
            hqlBuilder.append(" where owner = ").append(loginUser.getId());
        }
        List result = baseService.findVOByHQL(hqlBuilder.toString());
        Iterator it = result.iterator();
        ActionContext context = ActionContext.getContext();
        Map<String, Object> session = context.getSession();
        String local = (String) session.get("locale");
        list = new ArrayList<ChartVO>();
        ChartVO chartVO = null;
        SalesStage salesStage = null;
        while (it.hasNext()) {
            Object[] row = (Object[]) it.next();

            salesStage = (SalesStage) row[0];
            String IndustryLabel = "";
            if (salesStage != null) {
                if ("zh_CN".equals(local)) {
                    IndustryLabel = salesStage.getLabel_zh_CN();
                } else {
                    IndustryLabel = salesStage.getLabel_en_US();
                }
            }
            Long number = (Long) row[1];
            chartVO = new ChartVO();
            chartVO.setLabel(IndustryLabel);
            chartVO.setNumber(number.intValue());
            list.add(chartVO);
        }
        map = new HashMap<String, Object>();
    }

    /**
     * @return the map
     */
    public Map<String, Object> getMap() {
        return map;
    }

    /**
     * @param map
     *            the map to set
     */
    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    /**
     * @return the report
     */
    public String getReport() {
        return report;
    }

    /**
     * @param report
     *            the report to set
     */
    public void setReport(String report) {
        this.report = report;
    }

    /**
     * @return the list
     */
    public List<ChartVO> getList() {
        return list;
    }

    /**
     * @param list
     *            the list to set
     */
    public void setList(List<ChartVO> list) {
        this.list = list;
    }

    /**
     * @return the reportType
     */
    public String getReportType() {
        return reportType;
    }

    /**
     * @param reportType
     *            the reportType to set
     */
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    /**
     * @return the baseService
     */
    public IBaseService<Opportunity> getBaseService() {
        return baseService;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(IBaseService<Opportunity> baseService) {
        this.baseService = baseService;
    }

}
