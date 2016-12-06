package com.jiangchuanbanking.mail.action;

import java.util.List;

import com.jiangchuanbanking.base.action.BaseEditAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.mail.domain.EmailSetting;
import com.jiangchuanbanking.mail.service.MailService;
import com.jiangchuanbanking.system.security.UserUtil;
import com.jiangchuanbanking.util.Constant;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits Account
 * 
 */
public class EmailSettingAction extends BaseEditAction implements Preparable {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<EmailSetting> baseService;
    private MailService mailService;
    private EmailSetting emailSetting;
    private String emailAddress;

    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */
    public String save() throws Exception {
        UserUtil.permissionCheck("update_system");
        super.updateBaseInfo(emailSetting);
        getBaseService().makePersistent(emailSetting);
        return SUCCESS;
    }

    /**
     * Gets the entity.
     * 
     * @return the SUCCESS result
     */
    public String get() throws Exception {
        UserUtil.permissionCheck("view_system");
        List<EmailSetting> emailSettings = baseService
                .getAllObjects(EmailSetting.class.getSimpleName());
        if (emailSettings != null && emailSettings.size() > 0) {
            emailSetting = emailSettings.get(0);
            this.getBaseInfo(emailSetting, EmailSetting.class.getSimpleName(),
                    Constant.SYSTEM_NAMESPACE);
        }
        return SUCCESS;
    }

    /**
     * Sends test email
     * 
     * @return the SUCCESS result
     */
    public String sendEmail() throws Exception {
        mailService.sendSimpleMail(this.getEmailAddress());
        return SUCCESS;
    }

    /**
     * Prepares the list
     * 
     */
    public void prepare() throws Exception {
    }

    /**
     * @return the baseService
     */
    public IBaseService<EmailSetting> getBaseService() {
        return baseService;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(IBaseService<EmailSetting> baseService) {
        this.baseService = baseService;
    }

    /**
     * @return the emailSetting
     */
    public EmailSetting getEmailSetting() {
        return emailSetting;
    }

    /**
     * @param emailSetting
     *            the emailSetting to set
     */
    public void setEmailSetting(EmailSetting emailSetting) {
        this.emailSetting = emailSetting;
    }

    /**
     * @return the mailService
     */
    public MailService getMailService() {
        return mailService;
    }

    /**
     * @param mailService
     *            the mailService to set
     */
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress
     *            the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

}
