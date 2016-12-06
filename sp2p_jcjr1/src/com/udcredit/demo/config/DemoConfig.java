package com.udcredit.demo.config;

/**
 * 有盾规则调用配置Demo, Demo中的默认配置都无法调用成功,请根据注释进行对应配置修改.
 *
 * @author hush@udcredit.com
 * @version 1.0
 * @date 10/29/15
 */
public class DemoConfig {

    /**
     * 有盾服务调用地址
     */
    public static final String SERVICE_URL = "https://service.udcredit.com:12000/api/antifraud";

    /**
     * 开发私钥Demo,请根据开户邮件通知进行修改.
     */
    public static final String SECRET_KEY = "JXQphMUGAv0lFysC9jJU";

    /**
     * 商户号,请根据开户邮件通知进行修改.
     */
    public static final String PARTNER_CODE = "201511194146";

    /**
     * 商户号后四位,请根据开户邮件通知进行修改.
     */
    public static final String PARTNER_CODE_POSTFIX = "4146";

    /**
     * 实名认证攻略号,通过登录商户站创建攻略获得.
     */
    public static final String SHIMING_STRATEGY_CODE = "GL_JCBANKING";

    /**
     * 实名认证场景号,不需要修改!
     */
    public static final String SHIMING_SENARIO_CODE = "0010";

    private DemoConfig() {}
}
