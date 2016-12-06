
package com.jiangchuanbanking.mail.action;

import java.util.List;

import com.jiangchuanbanking.base.action.BaseEditAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.dict.domain.UserStatus;
import com.jiangchuanbanking.mail.domain.EmailTemplate;
import com.jiangchuanbanking.system.security.UserUtil;
import com.jiangchuanbanking.util.Constant;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits Email Template
 * 
 */
public class EditEmailTemplateAction extends BaseEditAction implements
        Preparable {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<EmailTemplate> baseService;
    private EmailTemplate emailTemplate;
    private List<UserStatus> statuses;

    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */
    public String save() throws Exception {
        saveEntity();
        emailTemplate = getBaseService().makePersistent(emailTemplate);
        this.setId(emailTemplate.getId());
        this.setSaveFlag("true");
        return SUCCESS;
    }

    /**
     * Gets the entity.
     * 
     * @return the SUCCESS result
     */
    public String get() throws Exception {
        if (this.getId() != null) {
            emailTemplate = baseService.getEntityById(EmailTemplate.class,
                    this.getId());
            this.getBaseInfo(emailTemplate,
                    EmailTemplate.class.getSimpleName(),
                    Constant.SYSTEM_NAMESPACE);
        }
        return SUCCESS;
    }

    /**
     * Saves entity field
     * 
     * @throws Exception
     */
    private void saveEntity() throws Exception {
        if (emailTemplate.getId() != null) {
            UserUtil.permissionCheck("update_system");
        } else {
            UserUtil.permissionCheck("create_system");
        }
        super.updateBaseInfo(emailTemplate);
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
    public IBaseService<EmailTemplate> getBaseService() {
        return baseService;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(IBaseService<EmailTemplate> baseService) {
        this.baseService = baseService;
    }

    /**
     * @return the emailTemplate
     */
    public EmailTemplate getEmailTemplate() {
        return emailTemplate;
    }

    /**
     * @param emailTemplate
     *            the emailTemplate to set
     */
    public void setEmailTemplate(EmailTemplate emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    /**
     * @return the statuses
     */
    public List<UserStatus> getStatuses() {
        return statuses;
    }

    /**
     * @param statuses
     *            the statuses to set
     */
    public void setStatuses(List<UserStatus> statuses) {
        this.statuses = statuses;
    }

}
