package com.udcredit.demo;

import com.udcredit.demo.config.DemoConfig;
import com.udcredit.demo.pojo.AntifraudRequest;
import com.udcredit.demo.util.HttpRequestSimple;
import com.udcredit.demo.util.ParameterFactory;
import com.udcredit.demo.util.SignatureHelper;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Map;

/**
 * 实名认证规则调用Demo.
 * @author hush@bsfit.com.cn
 * @version 1.0
 * @date 10/29/15
 */
public class GeneralDemo {

    public static void main(String[] args) {


        Map<String, String> param = ParameterFactory.getShimingParameterMap("覃燕","500102198803160263","12341234");
        AntifraudRequest request = new AntifraudRequest();

        request.setPartnerCode(DemoConfig.PARTNER_CODE);
        request.setNonceStr(RandomStringUtils.randomNumeric(32));
        request.setStrategyCode(DemoConfig.SHIMING_STRATEGY_CODE);
        request.setScenarioCode(DemoConfig.SHIMING_SENARIO_CODE);
        request.setPackageStr(SignatureHelper.generateSignedPackageStr(param));
        request.setSignature(SignatureHelper.generateSignatureFromRequest(DemoConfig.SECRET_KEY, request));

        System.out.println(request);

        HttpRequestSimple httpRequestSimple = new HttpRequestSimple();
        String response = httpRequestSimple.postSendHttp(DemoConfig.SERVICE_URL,request.toString());
        System.out.println(response);


    }

}
