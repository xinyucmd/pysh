<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>媒体报道-添加</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<link rel="stylesheet" href="../kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="../kindeditor/plugins/code/prettify.css" />
		<script charset="utf-8" src="../kindeditor/kindeditor.js"></script>
		<script charset="utf-8" src="../kindeditor/lang/zh_CN.js"></script>
		<script charset="utf-8" src="../kindeditor/plugins/code/prettify.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>

		
	</head>
	<body>
	
	<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="120" height="28" class="main_alll_h2">
								<a href="userBaseInfoInit.do?paramMap.applyId=${applyId }">基本信息</a>
							</td>
						
							<td width="2">
								&nbsp;
							</td>
							<td width="120" height="28" class="xxk_all_a">
							<s:if test="#request.uId==-1">
							   上传资料
							</s:if>
							<s:else>
							  <a href="userUploadInit.do?paramMap.applyId=${applyId }&paramMap.type=1">上传资料</a>
							</s:else>
								
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" height="28" class="xxk_all_a">
							
							<s:if test="#request.authStep==2">
							<a href="publishBorrowInit.do?paramMap.applyId=${applyId }&paramMap.type=1">发布借款</a>
							</s:if>
							<s:else>
							  发布借款
							</s:else>
<%--							<s:if test="#request.userId==-1">--%>
<%--							  发布借款--%>
<%--							</s:if>--%>
<%--							<s:else>--%>
<%--							  <a href="publishBorrowInit.do?paramMap.userId=${userId}&paramMap.applyId=${applyId }&paramMap.type=1">发布借款</a>--%>
<%--							</s:else>--%>
							</td>
							<td width="2">
								&nbsp;
							</td>
						</tr>
					</table>
					<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						
						<span id="dataInfo">
						
						<div class="boxmain2">
	<p class="tips">
		<span style="color: #DB7017">*</span> <span style="font-size:12px;">为必填项，所有资料均会严格保密。</span><span style="color: #DB7017">*</span> &nbsp;<span style="font-size:12px;">成为借款人必填项。</span>
	</p>
	<div class="biaoge2">
		<form id="BaseDataform">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style=" font-size:12px;">
				<tr>
					<td align="right">
						<span style="color: red; float: none;" class="formtips">*</span>真实姓名：
					</td>
					<td>
<%--						<s:if test='#request.flag=="2"'>--%>
							<input type="text" class="inp188" name="paramMap.realName"
								id="realName" value="${map.realName}"
								 />
							<span style="color: red; float: none;" id="u_realName"
								class="formtips"></span>
<%--						</s:if>--%>
<%--						<s:else>--%>
<%--												  ${map.realName}--%>
<%--												  <input type="hidden" class="inp188"--%>
<%--								name="paramMap.realName" id="realName" value="${map.realName}" />--%>
<%--						</s:else>--%>

					</td>
				</tr>
				<tr height="8px;">
						<td></td>
						<td></td>
					</tr>
				<tr>
					<td align="right">
						<span style="color: red; float: none;" class="formtips">*</span>身份证号：
					</td>
					<td>
<%--						<s:if test='#request.flag=="2"'>--%>
							<input type="text" class="inp188" name="paramMap.idNo" id="idNo" value="${map.idNo}"/>
							<span style="color: red; float: none;" id="u_idNo"
								class="formtips"></span>
<%--						</s:if>--%>
<%--						<s:else>--%>
<%--							<a id='idNo1'></a>--%>
<%--							<input type="hidden" class="inp188" name="paramMap.idNo"--%>
<%--								id="idNo" value="${map.idNo}" />--%>
<%--						</s:else>--%>
					</td>
				</tr>
				<tr height="8px;">
						<td></td>
						<td></td>
					</tr>
				<tr>
					<td align="right">
						<span style="color: red; float: none;" class="formtips">*</span>手机号码：
					</td>
					<td>
<%--						<s:if test="#request.map.cellPhone!=null">--%>
<%--							<a id="cellPhone1"></a>--%>
<%--								<input type="hidden" class="inp188" name="paramMap.cellPhone"--%>
<%--									id="cellPhone" value="${map.cellPhone}" />--%>
<%--						</s:if>--%>
<%--						<s:else>--%>
<%--								<s:if test='#request.flag=="2"'>--%>
									<input type="text" class="inp188" name="paramMap.cellPhone"
										id="cellPhone" value="${map.cellPhone}"/>
							
									<span style="color: red; float: none;" id="u_cellPhone"
										class="formtips"></span>
<%--								</s:if>--%>
<%--								<s:else>--%>
<%--									<a id="cellPhone1"></a>--%>
<%--									<input type="hidden" class="inp188" name="paramMap.cellPhone"--%>
<%--										id="cellPhone" value="${map.cellPhone}" />--%>
<%--								</s:else>--%>
<%--						</s:else>--%>
					</td>
				</tr>
				<tr height="8px;">
						<td></td>
						<td></td>
					</tr>
				<tr>
					<td align="right">
					<span style="color: red; float: none;" class="formtips">*</span>性别：
					</td>
					<td>
					<%-- 	<s:if test='#request.flag=="2"'>--%>
							<input type="radio" id="sex" value="男" name="paramMap_sex"
							<s:if test='#request.map.sex == "男"'>checked="checked"</s:if>
								class="sex" onclick="javascript:$('#u_sex').html('')" 
								/>
													男
													<input type="radio" id="sex" value="女" name="paramMap_sex"
								<s:if test='#request.map.sex == "女"'>checked="checked"</s:if>
								class="sex" onclick="javascript:$('#u_sex').html('')" 
								/>
													女
													<span style="color: red; float: none;" id="u_sex"
								class="formtips"></span>
					<%-- 	
					</s:if>
						<s:else>
												        ${map.sex }		
												            <input type="hidden" class="inp188"
								name="paramMap_sex" id="sex" value="${map.sex }" />
						</s:else>--%>
					</td>
				</tr>
				<tr height="8px;">
						<td></td>
						<td></td>
					</tr>
				<tr>
					<td align="right">
					<span style="color: red; float: none;" class="formtips">*</span>出生日期：
					</td>
					<td>
						<%--<s:if test='#request.flag=="2"'>--%>
							<input type="text" class="inp188 Wdate" name="paramMap.birthday"
								id="birthday" value="${birth }"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" 
								/>
							<span style="color: red; float: none;" id="u_birthday"
								class="formtips"></span>
					<%--	</s:if>
						<s:else>
														     ${birth }		
												            <input type="hidden" class="inp188"
								name="paramMap.birthday" id="birthday" value="${birth }" />
						</s:else>
					--%></td>
				</tr>
				<tr height="8px;">
						<td></td>
						<td></td>
					</tr>
				<tr>
					<td align="right">
					<span style="color: red; float: none;" class="formtips">*</span>最高学历：
					</td>
					<td>
							<select name="paramMap.highestEdu" id="highestEdu" onchange="javascript:if($('#highestEdu').val()!=''){$('#u_highestEdu').html('');} " >
													<option value="">
													请选择
														</option>
														<option value="高中或以下"
														<s:if test='#request.map.highestEdu == "高中或以下"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															高中或以下
														</option>
														<option value="大专"
															<s:if test='#request.map.highestEdu == "大专"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															大专
														</option>
														<option value="本科"
															<s:if test='#request.map.highestEdu == "本科"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															本科
														</option>
														<option value="研究生或以上"
															<s:if test='#request.map.highestEdu == "研究生或以上"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															研究生或以上
														</option>
														<option value="其他"
															<s:if test='#request.map.highestEdu == "其他"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															其他
													</option>
						</select>
						<span style="color: red; float: none;" id="u_highestEdu"
							class="formtips"></span>
					</td>
				</tr>
				<tr height="8px;">
						<td></td>
						<td></td>
					</tr>
				<tr>
					<td align="right">
					<span style="color: red; float: none;" class="formtips">*</span>入学年份：
					</td>
					<td>
						<input type="text" class="inp188 Wdate"
							name="paramMap.eduStartDay" id="eduStartDay"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"
							value="${rxedate }"
							 />
						<span style="color: red; float: none;" id="u_eduStartDay"
							class="formtips"></span>
					</td>
				</tr>
				<tr height="8px;">
						<td></td>
						<td></td>
					</tr>
				<tr>
					<td align="right">
					<span style="color: red; float: none;" class="formtips">*</span>毕业院校：
					</td>
					<td>
						<input type="text" class="inp188" name="paramMap.school"
							id="school" value="${map.school }"
							 />
						<span style="color: red; float: none;" id="u_school"
							class="formtips"></span>
					</td>
				</tr>
				<tr height="8px;">
						<td></td>
						<td></td>
					</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>工作城市：
												</td>
												<td>
													<s:select id="workPro" name="workPro"
														cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;"
														list="#request.provinceList" listKey="regionId"
														listValue="regionName" headerKey="-1"
														headerValue="--请选择--"  />
													<s:select id="workCity" name="cityId"
														cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;"
														list="#request.cityList" listKey="regionId"
														listValue="regionName" headerKey="-1"
														headerValue="--请选择--" />


												</td>
											</tr>
											<tr height="8px;">
												<td></td>
												<td></td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>公司类别：
												</td>
												<td>
													<select id="companyType" name="paramMap.companyType"
														>
														<option value="">
															请选择
														</option>
														<option value="事业单位"
															<s:if test='#request.map.companyType == "事业单位"'>selected="selected"</s:if>
															<s:else></s:else>>
															事业单位
														</option>
														<option value="国家单位"
															<s:if test='#request.map.companyType == "国家单位"'>selected="selected"</s:if>
															<s:else></s:else>>
															国家单位
														</option>
														<option value="央企(包括下级单位)"
															<s:if test='#request.map.companyType == "央企(包括下级单位)"'>selected="selected"</s:if>
															<s:else></s:else>>
															央企(包括下级单位)
														</option>
														<option value="地方国资委直属企业"
															<s:if test='#request.map.companyType == "地方国资委直属企业"'>selected="selected"</s:if>
															<s:else></s:else>>
															地方国资委直属企业
														</option>
														<option value="世界500强(包括合资企业及下级单位)"
															<s:if test='#request.map.companyType == "世界500强(包括合资企业及下级单位)"'>selected="selected"</s:if>
															<s:else></s:else>>
															世界500强(包括合资企业及下级单位)
														</option>
														<option value="外资企业(包括合资企业)"
															<s:if test='#request.map.companyType == "外资企业(包括合资企业)"'>selected="selected"</s:if>
															<s:else></s:else>>
															外资企业(包括合资企业)
														</option>
														<option value="一般上市公司(包括国外上市公司)"
															<s:if test='#request.map.companyType == "一般上市公司(包括国外上市公司)"'>selected="selected"</s:if>
															<s:else></s:else>>
															一般上市公司(包括国外上市公司)
														</option>
														<option value="一般民营企业"
															<s:if test='#request.map.companyType == "一般民营企业"'>selected="selected"</s:if>
															<s:else></s:else>>
															一般民营企业
														</option>
													</select>

												</td>
											</tr>
											<tr height="8px;">
										<td></td>
										<td></td>
									</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span> 公司行业：
												</td>
												<td>
													<select id="companyLine" name="paramMap.companyLine"
														>
														<option value="">
															请选择
														</option>
														<option value="农、林、牧、渔业"
															<s:if test='#request.map.companyLine == "农、林、牧、渔业"'>selected="selected"</s:if>
															<s:else></s:else>>
															农、林、牧、渔业
														</option>
														<option value="采矿业"
															<s:if test='#request.map.companyLine == "采矿业"'>selected="selected"</s:if>
															<s:else></s:else>>
															采矿业
														</option>
														<option value="电力、热力、燃气及水的生产和供应业"
															<s:if test='#request.map.companyLine == "电力、热力、燃气及水的生产和供应业"'>selected="selected"</s:if>
															<s:else></s:else>>
															电力、热力、燃气及水的生产和供应业
														</option>
														<option value="制造业"
															<s:if test='#request.map.companyLine == "制造业"'>selected="selected"</s:if>
															<s:else></s:else>>
															制造业
														</option>
														<option value="教育"
															<s:if test='#request.map.companyLine == "教育"'>selected="selected"</s:if>
															<s:else></s:else>>
															教育
														</option>
														<option value="环境和公共设施管理业"
															<s:if test='#request.map.companyLine == "环境和公共设施管理业"'>selected="selected"</s:if>
															<s:else></s:else>>
															环境和公共设施管理业
														</option>
														<option value="建筑业"
															<s:if test='#request.map.companyLine == "建筑业"'>selected="selected"</s:if>
															<s:else></s:else>>
															建筑业
														</option>
														<option value="交通运输、仓储业和邮政业"
															<s:if test='#request.map.companyLine == "交通运输、仓储业和邮政业"'>selected="selected"</s:if>
															<s:else></s:else>>
															交通运输、仓储业和邮政业
														</option>
														<option value="信息传输、计算机服务和软件业"
															<s:if test='#request.map.companyLine == "信息传输、计算机服务和软件业"'>selected="selected"</s:if>
															<s:else></s:else>>
															信息传输、计算机服务和软件业
														</option>
														<option value="批发和零售业"
															<s:if test='#request.map.companyLine == "批发和零售业"'>selected="selected"</s:if>
															<s:else></s:else>>
															批发和零售业
														</option>
														<option value="住宿、餐饮业"
															<s:if test='#request.map.companyLine == "住宿、餐饮业"'>selected="selected"</s:if>
															<s:else></s:else>>
															住宿、餐饮业
														</option>
														<option value="金融、保险业"
															<s:if test='#request.map.companyLine == "金融、保险业"'>selected="selected"</s:if>
															<s:else></s:else>>
															金融、保险业
														</option>

														<option value="房地产业"
															<s:if test='#request.map.companyLine == "房地产业"'>selected="selected"</s:if>
															<s:else></s:else>>
															房地产业
														</option>
														<option value="文化、体育、娱乐业"
															<s:if test='#request.map.companyLine == "文化、体育、娱乐业"'>selected="selected"</s:if>
															<s:else></s:else>>
															文化、体育、娱乐业
														</option>
														<option value="综合（含投资类、主业不明显)"
															<s:if test='#request.map.companyLine == "综合（含投资类、主业不明显)"'>selected="selected"</s:if>
															<s:else></s:else>>
															综合（含投资类、主业不明显)
														</option>
														<option value="其它"
															<s:if test='#request.map.companyLine == "其它"'>selected="selected"</s:if>
															<s:else></s:else>>
															其它
														</option>
													</select>

												</td>
											</tr>
											<tr height="8px;">
												<td></td>
												<td></td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>公司规模：
												</td>
												<td>
													<select name="paramMap.companyScale" id="companyScale"
														>
														<option value="50人以下"
															<s:if test='#request.map.companyScale == "50人以下"'>selected="selected"</s:if>
															<s:else></s:else>>
															50人以下
														</option>
														<option value="50-100人"
															<s:if test='#request.map.companyScale == "50-100人"'>selected="selected"</s:if>
															<s:else></s:else>>
															50-100人
														</option>
														<option value="100-500人"
															<s:if test='#request.map.companyScale == "100-500人"'>selected="selected"</s:if>
															<s:else></s:else>>
															100-500人
														</option>
														<option value="500人以上"
															<s:if test='#request.map.companyScale == "500人以上"'>selected="selected"</s:if>
															<s:else></s:else>>
															500人以上
														</option>
													</select>
												</td>
											</tr>
											<tr height="8px;">
												<td></td>
												<td></td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>职 位：
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.job"
														id="job" value="${map.job }"
														/>

												</td>
											</tr>
					<tr height="8px;">
						<td></td>
						<td></td>
					</tr>
											<tr>
					<td align="right">
					<span style="color: red; float: none;" class="formtips">*</span>婚姻状况：
					</td>
					<td>
						<input type="radio" name="paramMap_maritalStatus"
							id="maritalStatus" value="已婚"
							<s:if test='#request.map.maritalStatus == "已婚"'>checked="checked"</s:if>
							<s:else></s:else>
							onclick="javascript: $('#u_maritalStatus').html('')"/>
						已婚
						<input type="radio" name="paramMap_maritalStatus"
							id="maritalStatus" value="未婚"
							<s:if test='#request.map.maritalStatus == "未婚"'>checked="checked"</s:if>
							<s:else></s:else>
							
							onclick="javascript:$('#u_maritalStatus').html('')" />
						未婚
						<input type="radio" name="paramMap_maritalStatus"
							id="maritalStatus" value="离异" 
								<s:if test='#request.map.maritalStatus == "离异"'>checked="checked"</s:if>
							<s:else></s:else> 
							onclick="javascript:$('#u_maritalStatus').html('')"
							/>
						离异
						<input type="radio" name="paramMap_maritalStatus"
							id="maritalStatus" value="丧偶" 
								<s:if test='#request.map.maritalStatus == "丧偶"'>checked="checked"</s:if>
							<s:else></s:else> 
							onclick="javascript:$('#u_maritalStatus').html('')"
							/>
						丧偶
						<span style="color: red; float: none;" id="u_maritalStatus"
							class="formtips"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
					<span style="color: red; float: none;" class="formtips">*</span>有无子女：
					</td>
					<td>
						<input type="radio" name="paramMap_hasChild" id="hasChild"
							value="有"
							
							<s:if test='#request.map.hasChild == "有"'>checked="checked"</s:if>
							<s:else></s:else> onclick="javascript:$('#u_hasChild').html('')" />
						有
						<input type="radio" name="paramMap_hasChild" id="hasChild"
							value="无"
							
							<s:if test='#request.map.hasChild == "无"'>checked="checked"</s:if>
							<s:else></s:else> onclick="javascript:$('#u_hasChild').html('')" />
						无
						<span style="color: red; float: none;" id="u_hasChild"
							class="formtips"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
					<span style="color: red; float: none;" class="formtips">*</span>是否有房：
					</td>
					<td>
						<input type="radio" name="paramMap_hasHourse" id="hasHourse"
							value="有"
							
							<s:if test='#request.map.hasHourse == "有"'>checked="checked"</s:if>
							<s:else></s:else> onclick="javascript:$('#u_hasHourse').html('')" />
						有
						<input type="radio" name="paramMap_hasHourse" id="hasHourse"
							value="无"
							
							<s:if test='#request.map.hasHourse == "无"'>checked="checked"</s:if>
							<s:else></s:else> onclick="javascript:$('#u_hasHourse').html('')" />
						无
						<span style="color: red; float: none;" id="u_hasHourse"
							class="formtips"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
					<span style="color: red; float: none;" class="formtips">*</span>有无房贷：
					</td>
					<td>
						<input type="radio" name="paramMap_hasHousrseLoan"
							id="hasHousrseLoan" value="有"
							
							<s:if test='#request.map.hasHousrseLoan == "有"'>checked="checked"</s:if>
							<s:else></s:else>
							onclick="javascript:$('#u_hasHousrseLoan').html('')" />
						有
						<input type="radio" name="paramMap_hasHousrseLoan"
							id="hasHousrseLoan" value="无"
							
							<s:if test='#request.map.hasHousrseLoan == "无"'>checked="checked"</s:if>
							<s:else></s:else>
							onclick="javascript:$('#u_hasHousrseLoan').html('')" />
						无
						<span style="color: red; float: none;" id="u_hasHousrseLoan"
							class="formtips"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
					<span style="color: red; float: none;" class="formtips">*</span>是否有车：
					</td>
					<td>
						<input type="radio" name="paramMap_hasCar" id="hasCar" value="有"
							
							<s:if test='#request.map.hasCar == "有"'>checked="checked"</s:if>
							<s:else></s:else> onclick="javascript:$('#u_hasCar').html('')" />
						有
						<input type="radio" name="paramMap_hasCar" id="hasCar" value="无"
							
							<s:if test='#request.map.hasCar == "无"'>checked="checked"</s:if>
							<s:else></s:else> onclick="javascript:$('#u_hasCar').html('')" />
						无
						<span style="color: red; float: none;" id="u_hasCar"
							class="formtips"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
					<span style="color: red; float: none;" class="formtips">*</span>有无车贷：
					</td>
					<td>
						<input type="radio" name="paramMap_hasCarLoan" id="hasCarLoan"
							value="有"
							
							<s:if test='#request.map.hasCarLoan == "有"'>checked="checked"</s:if>
							<s:else></s:else>
							onclick="javascript:$('#u_hasCarLoan').html('')" />
						有
						<input type="radio" name="paramMap_hasCarLoan" id="hasCarLoan"
							value="无"
							
							<s:if test='#request.map.hasCarLoan == "无"'>checked="checked"</s:if>
							<s:else></s:else>
							onclick="javascript:$('#u_hasCarLoan').html('')" />
						无
						<span style="color: red; float: none;" id="u_hasCarLoan"
							class="formtips"></span>
					</td>
				</tr>
					
				<tr height="8px;">
						<td></td>
						<td></td>
					</tr>
				<tr>
					<td align="right">&nbsp;
						
					</td>
					<td style="padding-top: 20px;">
							<input id="jc_btn" value="修改并保存" id="bcbtn" style="background: #666666;color: white;width: auto;" type="button"  />
					</td>
				</tr>
				<tr height="8px;">
						<td></td>
						<td></td>
					</tr>
					<tr height="8px;">
						<td></td>
						<td></td>
					</tr>
				</table>
			</form>
	
		</div>
	</div>
						 </span>
					</div>
				</div>
			</div>
			</div>
	
	
	
		
		<script type="text/javascript" src="script/nav-zh.js"></script>
		<script type="text/javascript" src="common/date/calendar.js"></script>
		<script type="text/javascript" src="css/popom.js"></script>
		<script>
  $(function(){
    if('${map.auditStatus}'=='3'){
       $("#province").attr("disabled","true");
       $("#registedPlacePro").attr("disabled","true");
       $("#city").attr("disabled","true");
       $("#registedPlaceCity").attr("disabled","true");
       $("#clickCode").hide();
    }

    //省份改变
	$("#workPro").change(function(){
     var provinceId = $("#workPro").val();
     citySelectInit(provinceId, 2);
    	//$("#area").html("<option value='-1'>--请选择--</option>");
     });

	//城市跟随改变
	function citySelectInit(parentId, regionType){
		var _array = [];
		_array.push("<option value='-1'>--请选择--</option>");
		var param = {};
		param["regionType"] = regionType;
		param["parentId"] = parentId;
		$.post("ajaxqueryRegionAdmin.do",param,function(data){
		for(var i = 0; i < data.length; i ++){
			_array.push("<option value='"+data[i].regionId+"'>"+data[i].regionName+"</option>");
		}
		$("#workCity").html(_array.join(""));
	});
}
    
  });





</script>
<script>
$(function(){
  var t = '${map.idNo}'==''?'':'${map.idNo}';
  var len = t.length;
  
  if(len==15){
  var tmplen = t.substr(0,2);
  
  $('#idNo1').html(tmplen+'***** **** ****');  
  }else if(len==18){
  var tttttt = t.substr(0,2);
  $('#idNo1').html(tttttt+'**** **** **** ****');  
  }
  
  //alert(t.substr);
  
  var tt = '${map.cellPhone}'==''?'':'${map.cellPhone}';
  var len1 = tt.length;
  var tmplen1;
  var tmplen2;
  if(len1!=0){
  tmplen1 = tt.substr(0,3);
  tmplen2 = tt.substr(7,11);
  $('#cellPhone1').html(tmplen1+' **** '+tmplen2); 
  }

<%--  $("#grtx").hide(); --%>
  
});
</script>
<script type="text/javascript">
//提交基本资料 start-->
$(document).ready(function(){
    $("#BaseDataform :input").blur(function(){
    //验证手机号码
      if($(this).attr("id")=="cellPhone"){
      if( $(this).val() ==""){
         $("#u_cellPhone").attr("class", "formtips onError");
		 $("#u_cellPhone").html("请填写手机号码！");
      }else if((!/^1[3,5,8]\d{9}$/.test($("#cellPhone").val()))){ 
       $("#u_cellPhone").attr("class", "formtips onError");
	     $("#u_cellPhone").html("请正确填写手机号码！");
      }else{
           $("#u_cellPhone").attr("class", "formtips");
	       $("#u_cellPhone").html(""); 
      }
  }
  //真实姓名
  		if($(this).attr("id")=="realName"){  
  		        var isName = new Object();
  		        isName = /^[a-zA-Z\u4e00-\u9fa5]+$/;
				if($(this).val() ==""){
			      	$("#u_realName").attr("class", "formtips onError");
			      	$("#u_realName").html("请填写真实姓名！");
			    }else if($(this).val().length <2 || $(this).val().length> 20){   
	            	$("#u_realName").attr("class", "formtips onError");
	                $("#u_realName").html("名字长度为2-20个字符"); 
	            }else if(!isName.test($(this).val())){
	                  $("#u_realName").html("真实姓名输入有误"); 
	            }
	            else{   
	            	$("#u_realName").attr("class", "formtips");
	                $("#u_realName").html(""); 
	            } 
          }
  //========
  //身份号码
  if($(this).attr("id")=="idNo"){
     var isIDCard1 = new Object();
     var isIDCard2 = new Object();
     //身份证正则表达式(15位) 
     isIDCard1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/; 
     //身份证正则表达式(18位) 
     isIDCard2=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}(x|X))$/; 
    if($(this).val() ==""){
       //$("#u_idNo").attr("class", "formtips onError");
      $("#u_idNo").html("请填写身份证号码");
    }else if(isIDCard1.test($(this).val())||isIDCard2.test($(this).val())){
           //验证身份证号码的有效性
      var parama = {};
      parama["paramMap.idNo"] = $(this).val();
      $.post("isIDNOAdmin.do",parama,function(data){
       if(data.putStr=="0"){
        $("#u_idNo").html("请填写身份证号码");
      }else if(data.putStr=="1"){
           $("#u_idNo").html("身份证号码不合法!");
      }
      else{
        $("#u_idNo").html("");
      }
    });
    }else {
        $("#u_idNo").html("身份证号码不正确");
    }
  }
  //========== 验证出生年月
      if($(this).attr("id")=="birthday"){
    if($(this).val() !=""){
       $("#u_birthday").html("");
    }
  }
 //================居住电话
        if($(this).attr("id")=="telephone"){
        var mdd =/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
    if($(this).val() ==""){
 //   $("#u_telephone").attr("class", "formtips onError");
 //   $("#u_telephone").html("请填写居住电话");
 }else if(!mdd.test($(this).val())){
       $("#u_telephone").attr("class", "formtips onError");
       $("#u_telephone").html("请填写正确的居住电话");
    }else{
    $("#u_telephone").attr("class", "formtips");
       $("#u_telephone").html("");
    }
  }
 //============
     });
     		$("#province").change(function(){
			var provinceId = $("#province").val();
			citySelectInit(provinceId, 2);
		});
			 $("#registedPlacePro").change(function(){
			var provinceId = $("#registedPlacePro").val();
			registedPlaceCity(provinceId, 2);
		});

});
	function registedPlaceCity(parentId, regionType){
		var _array = [];
		_array.push("<option value='-1'>--请选择--</option>");
		var param = {};
		param["regionType"] = regionType;
		param["parentId"] = parentId;
		$.post("ajaxqueryRegion.do",param,function(data){
			for(var i = 0; i < data.length; i ++){
				_array.push("<option value='"+data[i].regionId+"'>"+data[i].regionName+"</option>");
			}
			$("#registedPlaceCity").html(_array.join(""));
		});
	}


	function jump(url) {
		
		alert("请先回答密保问题！");
		window.location.href = url;
}

	function citySelectInit(parentId, regionType){
		var _array = [];
		_array.push("<option value='-1'>--请选择--</option>");
		var param = {};
		param["regionType"] = regionType;
		param["parentId"] = parentId;
		$.post("ajaxqueryRegion.do",param,function(data){
			for(var i = 0; i < data.length; i ++){
				_array.push("<option value='"+data[i].regionId+"'>"+data[i].regionName+"</option>");
			}
			$("#city").html(_array.join(""));
		});
	}
	
	
	//提交基础资料
	  $("#jc_btn").click(function(){
	     var tsex = '${map.sex}'==''?'':'${map.sex}';
	     if($("#realName").val()==""){
	    // $("#u_realName").html("请填写真实姓名！");
	     alert("请填写真实姓名！");
	     return false 
	     }
	     if($("#idNo").val()==""){
	    // $("#u_idNo").html("请填写身份号码！");
	     alert("请填写身份号码！");
	     return false
	      }
	     if($("#cellPhone").val()==""){
	     //$("#u_cellPhone").html("请填写手机号码！");
	      alert("请填写手机号码！");
	     return false 
	     }
	    
		  
	   $("#jc_btn").attr("disabled",true);
	   var param = {};
	    param["paramMap.realName"]=$("#realName").val();
	    param["paramMap.idNo"]=$("#idNo").val();
	    param["paramMap.cellPhone"]=$("#cellPhone").val();
	    //param["paramMap.vilidataNum"]=$("#vilidataNum").val();
	    
	  
	   // if(tsex==''){
	    param["paramMap.sex"]=$("input[name='paramMap_sex']:checked").val();
	   // }else{
	     //param["paramMap.sex"]=$("#sex").val();
	   // }
	    
	    param["paramMap.birthday"]=$("#birthday").val();
	    param["paramMap.highestEdu"]=$("#highestEdu").val();
	    param["paramMap.eduStartDay"]=$("#eduStartDay").val();
	    param["paramMap.school"]=$("#school").val();
	    param["paramMap.maritalStatus"]=$("input[name='paramMap_maritalStatus']:checked").val();
	    param["paramMap.hasChild"]=$("input[name='paramMap_hasChild']:checked").val();
	    param["paramMap.hasHourse"]=$("input[name='paramMap_hasHourse']:checked").val();
	    param["paramMap.hasHousrseLoan"]=$("input[name='paramMap_hasHousrseLoan']:checked").val();
	    param["paramMap.hasCar"]=$("input[name='paramMap_hasCar']:checked").val();
	    param["paramMap.hasCarLoan"]=$("input[name='paramMap_hasCarLoan']:checked").val();
	    param["paramMap.workPro"]=$("#workPro").val();
	    param["paramMap.workCity"]=$("#workCity").val();
	    param["paramMap.companyType"]=$("#companyType").val();
	    param["paramMap.companyLine"]=$("#companyLine").val();
	    param["paramMap.companyScale"]=$("#companyScale").val();
	    param["paramMap.job"]=$("#job").val();
	    param["paramMap.applyId"]='${applyId}';
	    param["paramMap.userId"]='${userId}';
	    var applyId='${applyId}';
	    
	    
	    $.post("updateUserBaseInfoAdmin.do",param,function(data){
	       if(data.msg=="保存成功"){
	            alert("保存成功"); 
	           // window.clearInterval(tipId);
	            //$("#clickCode5").html("点击获取验证码");
	           //window.location.reload();
	            window.location.href='userUploadInit.do?paramMap.applyId='+applyId+'&paramMap.type=1';
               
		           
	       }else{
	    	   if(data.msg=="保存成功2"){
	    		   alert("保存成功"); 
	    		   window.clearInterval(tipId);
	    		   $("#clickCode5").html("点击获取验证码");
	    		   window.location.reload();
		   }
		     $("#jc_btn").attr("disabled",false);
	         //alert("请正确填写基本资料");
	         if(data.msg=="请正确填写真实名字"){
	         //$("#u_realName").html("请填写真实姓名！")
	         alert("请填写真实姓名！");
	       }
	            if(data.msg=="真实姓名的长度为不小于2和大于20"){
	            //$("#u_realName").html("真实姓名的长度为不小于2和大于20！")
	             alert("真实姓名的长度为不小于2和大于20！");
	       }
	       if(data.msg=="请正确身份证号码"){
	        // $("#u_idNo").html("请正确身份证号码！")
	         alert("请正确输入你的身份证号码");
	       }
	        if(data.msg=="身份证已注册")
	         {
	            	alert("身份证已注册!");
	        }
	        if(data.msg=="请正确填写手机号码"){
	           // $("#u_cellPhone").html("请填写手机号码！");
	              alert("请填写手机号码!");
	           
	       }
	       if(data.msg=="手机号码长度不对"){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("手机号码长度不对！");
	
	       }
	       
	       if(data.msg=="手机已存在"){
	       
	        alert("该手机号码已经被注册过了！");
	        
	       }
	      
	      }
		      
	       if(data.msg=="请正确填写手机号码"){
	         alert("手机验证码填写错误！");
	       }
	      if(data.msg=="请正确填写性别"){
	         alert("请勾选填写性别！");
	       }
	       if(data.msg=="请正确填写出生日期"){
	         alert("请正确填写出生日期！");
	       }
	       if(data.msg=="请正确填写入学年份"){
	         alert("请正确填写入学年份！");
	       }
	             if(data.msg=="请正确填写入毕业院校"){
	          alert("请正确填写入毕业院校！");
	       }

	             if(data.msg=="请正确填写入婚姻状况"){
	     	          alert("请正确填写入婚姻状况！");
	     	       }

	             if(data.msg=="请正确填写入有无子女"){
		     	          alert("请正确填写入有无子女！");
		     	       }

	             if(data.msg=="请正确填写入是否有房"){
		     	          alert("请正确填写入是否有房！");
		     	       }

	             if(data.msg=="请正确填写入有无房贷"){
		     	          alert("请正确填写入有无房贷！");
		     	       }

	             if(data.msg=="请正确填写入是否有车"){
		     	          alert("请正确填写入是否有车！");
		     	       }

	             if(data.msg=="请正确填写入有无车贷"){
		     	          alert("请正确填写入有无车贷！");
		     	       }
	             if(data.msg=="请正确填写入工作省份"){
	     	         alert("请正确填写入工作省份！");
	     	       }
	     	       if(data.msg=="请正确填写入工作城市"){
	     	         alert("请正确填写入工作城市！");
	     	       }
	     	       if(data.msg=="请正确填写入公司类别"){
	     	         alert("请正确填写入公司类别！");
	     	       }
	     	        if(data.msg=="请正确填写入公司行业"){
	     	         alert("请正确填写入公司行业！");
	     	       }
		     	       
	     	       if(data.msg=="请正确填写工作职位"){
		     	         alert("请正确填写工作职位！");
		     	       }
	     	       
     
	        if(data.msg=="请正确填写最高学历"){
	          alert("请正确填写最高学历！");
	       }
	        
	       if(data.msg=="身份证不合法"){
	        alert("你输入的身份证号码不合法,请重新输入");
	        $("#u_idNo").html("请输入正确身份证号码！");
	    
	       }
	       if(data.msg=="身份证已被注册"){
	        alert("你输入的身份证号码身份证已被注册,请重新输入");
	        $("#u_idNo").html("请重新输入身份证号码！");
	      
	       }
	    });
	    
	});
</script>
	</body>
</html>
