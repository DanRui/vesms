<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>	
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Eliminated-Apply-Add</title>
</head>
<body>
	<table class="datagrid-table-s datagrid-htable">
		<tr class="datagrid-header-row">
			<td class="view_table_left" style="width:135px;">请输入预约单号：</td>
			<td class="view_table_right" colspan="5" style="text-align:left;">
				<input type="text" name="appointmentNo"/>
				<a id="btnAppointment" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-baofei-message'">获取预约数据</a>
			</td>
		</tr>
		<tr class="datagrid-row">
			<td colspan="6">
				<div id="appoint-list">
				</div>
			</td>
		</tr>
	</table>
	<form id="form-apply-save" action="eliminatedApply/save.do" method="post">
		<input name="stage" type="hidden" value="${stage}"/>
		<input name="applyNo" type="hidden"/>
		<input name="id" type="hidden"/>
		<div id="eliminated-apply-add" class="datagrid-header">
			<table class="datagrid-table-s datagrid-htable">
				<!-- <tr class="datagrid-header-row">
					<td class="view_table_left">请输入预约单号：</td>
					<td class="view_table_right" colspan="2">
						<input type="text" name="appointmentNo"/>
						<a id="btnAppointment" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-baofei-message'">获取预约数据</a>
					</td>
				</tr>
				<tr class="datagrid-row">
					<td colspan="6">
						<div id="appoint-list">
						</div>
					</td>
				</tr> -->
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">车辆基本信息</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">号牌号码：</td>
					<td class="view_table_right">
						粤B <input type="text" name="vehiclePlateNum" class="easyui-validatebox" data-options="required:true" style="width:112px;"/>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					<td class="view_table_left">号牌种类：</td>
					<td class="view_table_right">
						<input id="vehiclePlateTypeAdd" class="easyui-combobox" name="vehiclePlateType" 
						data-options="editable:false,required:true,valueField:'code',textField:'value',url:'sysDict/getDictListFromMap.do?dictType=VEHICLE_PLATE_TYPE',panelHeight:'auto'"/>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
						<a id="btnApplyVerify" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-baofei-message'">获取数据</a>
					</td>
					<td class="view_table_left">厂牌型号：</td>
					<td class="view_table_right">
						<input type="text" name="vehicleModelNo" readonly="readonly"/>
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">车辆类型：</td>
					<td class="view_table_right">
						<input type="hidden" name="vehicleType" />
						<input type="text" name="vehicleTypeName" readonly="readonly" />
					</td>
					<td class="view_table_left">车架号：</td>
					<td class="view_table_right">
						<input type="text" name="vehicleIdentifyNo" readonly="readonly" /></td>
					<td class="view_table_left">发动机号：</td>
					<td class="view_table_right">
						<input type="text" name="engineNo" readonly="readonly" /></td>

				</tr>
				<!-- 
				<tr class="datagrid-row">
					<td class="view_table_left">使用性质：</td>
					<td class="view_table_right">
						<input type="text" name="useOfPropertyName" readonly="readonly" /></td>
					<td class="view_table_left">燃油类型：</td>
					<td class="view_table_right">
						<input type="text" name="iolTypeName" readonly="readonly" />
					</td>
					<td class="view_table_left">总质量（千克）</td>
					<td class="view_table_right">
						<input type="text" name="totalWeight" value="0" readonly="readonly" />
					</td>
				</tr>
				 -->
				<tr class="datagrid-row">
					<!-- <td class="view_table_left">核定载客数（人）：</td>
					<td class="view_table_right">
						<input type="text" name="vehicleNumPeople" readonly="readonly" /></td> -->
					<td class="view_table_left">燃油类型：</td>
					<td class="view_table_right">
						<input type="hidden" name="iolType" />
						<input type="text" name="iolTypeName" readonly="readonly" />
					</td>	
					<td class="view_table_left">初次登记日期：</td>
					<td class="view_table_right">
						<input  type="text" name="registerDate" readonly="readonly" /></td>
					<td class="view_table_left">强制报废期：</td>
					<td class="view_table_right">
						<input type="text" name="deadline" readonly="readonly" /></td>
				</tr>
				<!-- <tr class="datagrid-row applyType">
					<td class="view_table_left">机构类型：</td>
					<td class="view_table_right">
						<input type="text" name="orgType"/>
					</td>
					<td class="view_table_left">组织机构代码：</td>
					<td class="view_table_right applyType">
						<input type="text" name="orgCode" />
					</td>
				</tr> -->
				<tr class="datagrid-row">
					<td class="view_table_left">注销日期：</td>
					<td class="view_table_right">
						<input id="destroyDate" type="text" name="destroyDate" class="easyui-datebox" options="editable:false,required:true,panelHeight:'auto'"/>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					<td class="view_table_left">车辆状态：</td>
					<td class="view_table_right">
						<input type="hidden" name="vehicleStatus" />
						<input type="text" name="vehicleStatusName" readonly="readonly"/>
					</td>
						<td class="view_table_left">提前报废天数：</td>
					<td class="view_table_right">
						<input type="text" name="advancedScrapDays" readonly="readonly"/>
					</td>	
				</tr>
				<tr class="datagrid-header-row">
					<td class="view_table_left">排放标准：</td>
					<td class="view_table_right">
						<input type="text" name="emissionStandard" readonly="readonly"/>
					</td>
					<td class="view_table_left">注销类别：</td>
					<td class="view_table_right">
						<input type="text" name="cancelReason" readonly="readonly"/>
					</td>	
					<td class="view_table_left ">是否财政供养：</td>
					<td class="view_table_right">
						<input type="hidden" name="isFinancialSupport"/>
						<input type="text" name="isFinancialSupportName" readonly="readonly"/>
						<!-- <select id="isFinancialSupport" name="isFinancialSupport" class="easyui-combobox" data-options="editable:false,required:true,width:118,panelHeight:'auto'">
							<option value="1">个人</option>
							<option value="2">车主自证非财政供养</option>
						</select> -->
						<!-- <span style="color:red;text-align:center">&nbsp;*&nbsp;</span> -->
					</td>		
				</tr>
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">报废信息</td>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:130px">报废回收证明编号：</td>
					<td class="view_table_right">
						<textarea rows="2" name="callbackProofNo" readonly="readonly"></textarea>
						<!-- <input type="text" name="callbackProofNo" readonly="readonly"  /> -->
					</td>
					<td class="view_table_left">交售日期：</td>
					<td class="view_table_right">
						<input  type="text" name="recycleDate" readonly="readonly" />
					</td>
				</tr>
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">办理类型</td>
				</tr>
				<tr class="datagrid-header-row">
					<td class="view_table_left">车主类型：</td>
					<td class="view_table_right">
						<select id="isPersonal" name="isPersonal" class="easyui-combobox" data-options="editable:false,required:true,width:160,panelHeight:'auto'">
							<option selected>请选择是自然人或企业</option> 
							<option value="Y">自然人</option>
							<option value="N">企业</option>
						</select>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					<td class="view_table_left">办理类型：</td>
					<td class="view_table_right">					
						<select id="isProxy" name="isProxy" class="easyui-combobox" data-options="editable:false,required:true,width:75,panelHeight:'auto'">
							<option selected>请选择</option>
							<option value="Y">自办</option>
							<option value="N">代办</option>
						</select>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
				</tr>
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">补贴对象信息</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">车主：</td>
					<td class="view_table_right">
						<!-- <input type="text" name="vehicleOwner" readonly="readonly"/> -->
						<textarea  name="vehicleOwner" class="easyui-validatebox" readonly="readonly"></textarea>
					</td>
					<td class="view_table_left">车主身份证明号码：</td>
					<td class="view_table_right">
						<input type="text" name="vehicleOwnerIdentity" class="easyui-validatebox" data-options="required:true"/>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
						<a id="btnVerifyVehicleOwnerCard" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">识别</a>
					</td>
					<td class="view_table_left">车主联系号码：</td>
					<td class="view_table_right">
						<input type="text" name="mobile" class="easyui-validatebox" />
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">经办人：</td>
					<td class="view_table_right">
						<input type="text" name="agent" class="easyui-validatebox"/>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					<td class="view_table_left">经办人手机号：</td>
					<td class="view_table_right">
						<input type="text" name="agentMobileNo" class="easyui-validatebox"/>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					<td class="view_table_left">经办人身份证号：</td>
					<td class="view_table_right">
						<input type="text" name="agentIdentity" class="easyui-validatebox"/>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
						<a id="btnVerifyAgentCard" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">识别</a>
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">开户户名：</td>
					<td class="view_table_right">
						<textarea name="bankAccountName" class="easyui-validatebox" readonly="readonly"></textarea>
						<!-- <input type="text" name="bankAccountName" readonly="readonly"/> -->
						<!-- <span style="color:red;text-align:center">&nbsp;*&nbsp;</span> -->
					</td>
					<td class="view_table_left">开户银行：</td>
					<td class="view_table_right">
						<input id="bankCodeAdd" class="easyui-combobox" name="bankCode" 
						data-options="editable:false,required:true,valueField:'code',textField:'value',url:'sysDict/getDictListFromMap.do?dictType=BANK_CODE',panelHeight:150"/>
						<input type="text" name="bankName"/>
						<!-- <input type="text" name="bankName" class="easyui-validatebox" data-options="required:true" /> -->
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					<td class="view_table_left">开户银行账号：</td>
					<td class="view_table_right">
						<input type="text" id="bankAccountNo" name="bankAccountNo" class="easyui-numberbox" data-options="required:true" />
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
				</tr>
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">补贴信息</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">补贴金额：</td>
					<td class="view_table_right">
						<input type="text" name="subsidiesMoney"  readonly="readonly"/>
					</td>
					<td class="view_table_left">补贴标准说明：</td>
					<td class="view_table_right">
						<textarea name="subsidiesStandard" cols="50" readonly="readonly"></textarea>
						<!-- <input type="text" name="subsidiesStandard" readonly="readonly"/> -->
					</td>
				</tr>
				<!-- 报废汽车回收证明 -->
				<input type="hidden" name="callbackProofFile"/>
				<!-- 机动车注销证明 -->
				<input type="hidden" name="vehicleCancelProofFiles"/>
				<!-- 银行卡 -->
				<input type="hidden" name="bankCardFiles"/>
				<!-- 车主身份证明 -->
				<input type="hidden" name="vehicleOwnerProofFiles"/>
				<!-- 代理委托书 -->
				<input type="hidden" name="agentProxyFiles"/>
				<!-- 代理人身份证 -->
				<input type="hidden" name="agentProofFiles"/>
				<!-- 非财政供养单位证明 -->
				<input type="hidden" name="noFinanceProvideFiles"/>
				<!-- 开户许可证 -->
				<input type="hidden" name="openAccPromitFiles"/>
			</table>
		</div>
	</form>
	<form id="form-apply-upload" action="eliminatedApply/fileUpload.do" method="post" enctype="multipart/form-data">
		<div id="div-apply-upload" class="datagrid-header">
			<table class="datagrid-table-s datagrid-htable">
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">证明材料</td>
				</tr>
				<tr class="datagrid-row">
					<td colspan="2" class="view_table_left">
						<!-- <input type="button" id="btnCapture" value="拍照上传"/> -->
						<a id="btnCapture" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照上传</a>
						<font color="red">（当高拍仪拍照不工作时，可点击“浏览”选择本地图片文件，点击“本地文件上传”进行操作。）</font>
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:110px">报废汽车回收证明：</td>
					<td class="view_table_right" colspan="2">
						<input id="callbackProofFiles" type="file" name="callbackProofFiles" required="required" multiple="multiple" />
						<!-- <input id="callbackProofFiles" name="callbackProofFiles" data-options="editable:false,required:true,buttonText:'请选择'"
						class="easyui-filebox" /> -->
						<font color="red">&nbsp;*&nbsp;</font>
						<!-- <a id="btnTakePhotoCallbackProof" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a> -->
					</td>
					<td class="view_table_right" colspan="3">
						<a id="callbackProofFileImg" href="#" ></a>
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:110px">机动车注销证明：</td>
					<td class="view_table_right" colspan="2">
						<input id="vehicleCancelProof" type="file" name="vehicleCancelProof" required="required" multiple="multiple" />
						<!-- <input id="vehicleCancelProof" class="easyui-filebox" name="vehicleCancelProof" data-options="editable:false,required:true,buttonText:'请选择'"/> -->
						<font color="red">&nbsp;*&nbsp;</font>
						<!-- <a id="btnTakePhotoVehicleCancelProof" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a> -->
					</td>
					<td class="view_table_right" colspan="3">
						<a id="vehicleCancelProofFileImg" href="#" ></a>
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:120px">银行卡：</td>
					<td class="view_table_right" colspan="2">
						<input id="bankCard" type="file" name="bankCard" required="required" multiple="multiple" />
						<!-- <input id="bankCard" class="easyui-filebox" name="bankCard" data-options="editable:false,required:true,buttonText:'请选择'"/> -->
						<font color="red">&nbsp;*&nbsp;</font>
						<!-- <a id="btnTakePhotoBankCard" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a> -->
					</td>
					<td class="view_table_right" colspan="3">
						<a id="bankCardFileImg" href="#" ></a>
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:110px">车主身份证明：</td>
					<td class="view_table_right" colspan="2">
						<input id="vehicleOwnerProof" type="file" name="vehicleOwnerProof" required="required" multiple="multiple" />
						<!-- <input id="vehicleOwnerProof" class="easyui-filebox" name="vehicleOwnerProof" data-options="editable:false,required:true,buttonText:'请选择'"/> -->
						<font color="red">&nbsp;*&nbsp;</font>
						<!-- <a id="btnTakePhotoVehicleOwnerProof" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a> -->
					</td>
					<td class="view_table_right" colspan="3">
						<a id="vehicleOwnerProofFileImg" href="#" ></a>
					</td>
				</tr>
				<tr class="datagrid-row proxyType">
					<td class="view_table_left" style="width:120px">代理委托书：</td>
					<td class="view_table_right" colspan="2">
						<input id="agentProxy" type="file" name="agentProxy" multiple="multiple" />
						<!-- <input id="agentProxy" class="easyui-filebox" name="agentProxy" data-options="editable:false,width:141,buttonText:'请选择'"/> -->
						<font color="red">&nbsp;*&nbsp;</font>
						<!-- <a id="btnTakePhotoAgentProxyProof" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a> -->
					</td>
					<td class="view_table_right" colspan="3">
						<a id="agentProxyFileImg" href="#" ></a>
					</td>
				</tr>
				<tr class="datagrid-row proxyType">
					<td class="view_table_left" style="width:110px">代理人身份证：</td>
					<td class="view_table_right" colspan="2">
						<input id="agentProof" type="file" name="agentProof" multiple="multiple" />
						<!-- <input id="agentProof" class="easyui-filebox" name="agentProof" data-options="editable:false,width:141,buttonText:'请选择'"/> -->
						<font color="red">&nbsp;*&nbsp;</font>
						<!-- <a id="btnTakePhotoAgentProof" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a> -->
					</td>
					<td class="view_table_right" colspan="3">
						<a id="agentProofFileImg" href="#" ></a>
					</td>
				</tr>
				<tr class="datagrid-row applyType">
					<td class="view_table_left" style="width:120px">非财政供养单位证明：</td>
					<td class="view_table_right" colspan="2">
						<input id="noFinanceProvide" type="file" name="noFinanceProvide" multiple="multiple" />
						<!-- <input id="noFinanceProvide" class="easyui-filebox" name="noFinanceProvide" data-options="editable:false,width:141,buttonText:'请选择'"/> -->
						<font color="red">&nbsp;*&nbsp;</font>
						<!-- <a id="btnTakePhotoNoFinanceProvide" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a> -->
					</td>
					<td class="view_table_right" colspan="3">
						<a id="noFinanceProvideFileImg" href="#" ></a>
					</td>
				</tr>
				<tr class="datagrid-row applyType">
					<td class="view_table_left" style="width:110px">开户许可证：</td>
					<td class="view_table_right" colspan="2">
						<input id="openAccPromit" type="file" name="openAccPromit" multiple="multiple" />
						<!-- <input id="openAccPromit" class="easyui-filebox" name="openAccPromit" data-options="editable:false,width:141,buttonText:'请选择'"/> -->
						<font color="red">&nbsp;*&nbsp;</font>
						<!-- <a id="btnTakePhotoOpenAccPromit" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a> -->
					</td>
					<td class="view_table_right" colspan="3">
						<a id="openAccPromitFileImg" href="#" ></a>
					</td>
				</tr>
				<tr class="datagrid-row">
					<td></td>
					<td class="view_table_right">
						<a id="btnUpload" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-shangchuan'">本地文件上传</a>
					</td>
					<!-- <td colspan="4"></td> -->
				</tr>
			</table>
		</div>
	</form>
	
	<object id="view1" type="application/x-eloamplugin" name="view"></object>
	
	<div id="update_buttons" align="center">
		<a id="btnNextStep" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-nextstep'">下一步</a>
	</div>
	
	<script type="text/javascript">
	
			$(function() {
				
				/* $("#common-dialog").dialog("options",{onBeforeClose:function(){
					alert("asdasd");
					//$(this).dialog("options",{onBeforeClose:null});
					return true;
				}}); */
				
				Initial();
				
				var basePath = "<%=basePath%>";
				
				// 设置其它银行名称隐藏
				$("input[name='bankName']").hide();
				
				$("#bankCodeAdd").combobox({
					onSelect : function(rec) {
						if (rec.code == "999") {
							$("input[name='bankName']").attr("required", true);
							$("input[name='bankName']").show();
						} else {
							// 隐藏银行名称并且清空原值
							$("input[name='bankName']").val("");
							$("input[name='bankName']").attr("required", false);
							$("input[name='bankName']").hide();
						}
					}
				});
				
				// 初始化号牌号码输出框，显示粤B开头
				//$("input[name='vehiclePlateNum']").val("粤B");
				
				// 页面DOM加载完毕后,隐藏预约车辆列表区域
				$("DIV#appoint-list").hide();
				
				//隐藏组织结构类型和文件上传区域
				$("tr[class*='applyType']").each(function() {
					$(this).hide();
				});
				
				$("tr[class*='proxyType']").each(function() {
					$(this).hide();
				});
				
				$("#isPersonal").combobox({
					onChange : function(newVal, oldVal) {
						var isPersonal = $(this).combobox("getValue");
						$("tr[class*='applyType']").each(function() {
							if (isPersonal == "N") {
								$("#isProxy").combobox("setValue", "N");
								$(this).show();
								$("#noFinanceProvide").attr("required","true");
								$("#openAccPromit").attr("required","true");
								// 设置经办人信息必填
								// 企业清空经办人手填
								$("input[name='agent']").val("");
								$("input[name='agent']").attr("required", "true");
								$("input[name='agentMobileNo']").attr("required", "true");
								$("input[name='agentIdentity']").attr("required", "true");
								// 更新财政供养为个人
								$("input[name='isFinancialSupport']").val("2");
								$("input[name='isFinancialSupportName']").val("车主自证非财政供养");
								//$("textarea[name='checkOpinion']").attr("required","true");
								//$("#checkOpinion").validatebox({required:true});
							} else if (isPersonal == "Y") {
								$(this).hide();
								// 车主类型是个人,隐藏开户许可证和非财政供养单位证明
								$("#noFinanceProvide").attr("required","false");
								$("#openAccPromit").attr("required","false");
								// 设置经办人信息非必填
								$("input[name='agent']").val($("textarea[name='vehicleOwner']").val());
								$("input[name='agent']").attr("required", "false");
								//$("input[name='agentMobileNo']").attr("required", "false");
								$("input[name='agentIdentity']").attr("required", "false");
								// 更新财政供养为个人
								if ($("#isProxy").combobox("getValue") == "N") {
									$("#isProxy").combobox("setValue", "请选择");
								}
								$("input[name='isFinancialSupport']").val("1");
								$("input[name='isFinancialSupportName']").val("个人");
								//$("#checkOpinion").validatebox({required:true});
							} else {
								$(this).hide();
								// 车主类型是个人,隐藏开户许可证和非财政供养单位证明
								$("#noFinanceProvide").attr("required","false");
								$("#openAccPromit").attr("required","false");
								// 设置经办人信息非必填
								$("input[name='agent']").val("");
								$("input[name='agent']").attr("required", "false");
								$("input[name='agentIdentity']").attr("required", "false");
								// 更新财政供养为个人
								if ($("#isProxy").combobox("getValue") == "N") {
									$("#isProxy").combobox("setValue", "请选择");
								}
								$("input[name='isFinancialSupport']").val("1");
								$("input[name='isFinancialSupportName']").val("个人");
							}
						});
					}
				});
				
				$("#isProxy").combobox({
					onChange : function(newVal, oldVal) {
						var isProxy = $(this).combobox("getValue");
						$("tr[class*='proxyType']").each(function() {
							if (isProxy == "N") {
								$(this).show();
								$("#agentProxy").attr("required","true");
								$("#agentProof").attr("required","true");
								
								// 清空经办人信息
								$("input[name='agent']").val("");
								
								//$("textarea[name='checkOpinion']").attr("required","true");
								//$("#checkOpinion").validatebox({required:true});
							} else if (isProxy == "Y" || isProxy == "请选择") {
								$("#isPersonal").combobox("setValue", "Y");
								$(this).hide();
								// 自办隐藏代理委托书和代理人身份证明
								$("#agentProxy").attr("required","false");
								$("#agentProof").attr("required","false");
								
								// 自办设置经办人为车主
								$("input[name='agent']").val($("textarea[name='vehicleOwner']").val());
								// 经办人手机号、身份证跟车主一致
								//$("#checkOpinion").validatebox({required:true});
							}
						});
					}
				});
				
				// 点击取预约号处理
				$("#btnAppointment").click(function() {
					// 校验预约号
					var appointmentNo = $("input[name='appointmentNo']").val();
					if (appointmentNo == "") {
						alert("如果是预约受理，请填写预约号！");
						return false;
					}
					
					/* if (appointmentNo.length != 16) {
						alert("预约号输入位数错误！(16位预约号)");
						return false;
					} */
					
					/* if (!DateFormatter.DateISO(appointmentNo.substring(5, 13), "yyyyMMdd")) {
						// 日期格式不匹配
						alert("输入的预约单号格式有误，请检查！");
						return false;
					} */
					// 从服务器获取预约的车辆列表
					$.ajax({
						url : basePath + "/eliminatedApply/getAppointmentInfo.do?appointmentNo=" + appointmentNo,
			        	async : true,
			        	type : "POST",
			        	dataType : "json",
			        	beforeSend : function() {
			        		if (null == appointmentNo || "" == appointmentNo) {
			        			return false;
			        		}
			        		// 将预约车辆列表数据清空
			        		$("DIV#appoint-list").html('');
			        	},
			        	success : function(data) {
			        		if (data.success) {
			        			// 获取数据成功，显示预约号内预约车辆信息列表
			        			if (data.message.total <= 0) {
			        				alert("当前预约号内无预约车辆数据！");
			        				return false;
			        			}
			        			var list = data.message.list;
			        			$("DIV#appoint-list").show();
			        			var appointDiv = $("DIV#appoint-list");
			        			var table = $("<table style='border-collapse:collapse' id='appoint-table' bordercolor='#111111' cellspacing='0' cellpadding='2' border='1'></table>");
			        			var th = "<tr border='1'><td colspan='9'>预约车辆列表：(<font color='red'>请双击选择车辆进行受理</font>)</td></tr>";
			        			table.append(th);
			        			for (var i = 0 ; i < data.message.total ; i ++) {
			        				var tr = "<tr id='appoint-"+i+"' class='change mouseOn' onDblClick='dblClickAppointInfo("+i+");'>"
			        					   + "<td>号牌号码</td><td>"+list[i].vehiclePlateNum+"</td>"
			        				       + "<td>号牌种类</td><td>"+list[i].vehiclePlateTypeName+"<input type='hidden' name='appointVehPlateCode' value='"+ list[i].vehiclePlateType +"'/></td>"
			        				       + "<td>补贴银行</td><td>"+list[i].bankName+"<input type='hidden' name='appointBankCode' value='"+ list[i].bankCode +"'/></td>"
			        				       + "<td>银行账号</td><td>"+list[i].bankAccount+"<input type='hidden' name='appointAgentIdentity' value='"+ list[i].agentIdentity +"'/></td>"
			        				       + "<td>"+list[i].applyStatus+"</td>"
			        				       + "</tr>";
			        				table.append(tr);
			        			}
			        			
			        			appointDiv.append(table);
			        			
			        		} else {
			        			alert(data.message);
			        		}
			        	}
					});
					
				});
				
				// 绑定输入预约号元素的回车事件
				$("input[name='appointmentNo']").keypress(function(event) {
					//var appointmentNo = $(this).val();
					if(event.keyCode == '13')
					{
						$("#btnAppointment").click();
					}
				});
				
				// 点击拍照上传按钮，跳转到拍照上传页面，统一上传所有的证明材料
				$("#btnCapture").click(function() {
					var vehiclePlateNum = $("input[name='vehiclePlateNum']").val();
					if (vehiclePlateNum == null || vehiclePlateNum == "" || typeof(vehiclePlateNum) == "undefined") {
						Messager.alert({
							type : "error",
							title : "&nbsp;",
							content : "请先输入号牌号码！"
						});
						return false;
					}
					
					// 先获取页面已经上传的文件路径
					var proof_files = new Object();
					
					// 报废回收证明
					proof_files.callbackProofFile = $("input[name='callbackProofFile']").val();
					// 机动车注销证明
					proof_files.vehicleCancelProofFiles = $("input[name='vehicleCancelProofFiles']").val();
					// 银行卡
					proof_files.bankCardFiles = $("input[name='bankCardFiles']").val();
					// 车主身份证明
					proof_files.vehicleOwnerProofFiles = $("input[name='vehicleOwnerProofFiles']").val();
					// 开户许可证
					proof_files.openAccPromitFiles = $("input[name='openAccPromitFiles']").val();
					// 非财政供养单位证明
					proof_files.noFinanceProvideFiles = $("input[name='noFinanceProvideFiles']").val();
					// 代理人身份证
					proof_files.agentProofFiles = $("input[name='agentProofFiles']").val();
					// 代理委托书
					proof_files.agentProxyFiles = $("input[name='agentProxyFiles']").val();
					
					// 车主类型
					var isPersonal = $("#isPersonal").combobox("getValue");
					
					// 办理类型
					var isProxy = $("#isProxy").combobox("getValue");
					
					if (isPersonal != "Y" && isPersonal != "N") {
						alert("请选择车主类型！");
						return;
					}
					
					if (isProxy != "Y" && isProxy != "N") {
						alert("请选择办理类型！");
						return;
					}
					
					// 释放高拍仪资源
					Destroy();
					
					// alert(JSON.stringify(proof_files));
					
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog('eliminatedApply/captureNew.jsp?vehiclePlateNum='+vehiclePlateNum + '&isPersonal=' + isPersonal + '&isProxy=' + isProxy, proof_files, 
							"dialogWidth=800px,dialogHeight=700px,resizable=yes,status=no,scrollbars=yes,menubar=no");
					
					// 抓拍返回，设置图片路径到页面字段, 且设置后台隐藏字段
		        	if (typeof(parentValue) != "undefined" && parentValue != null) {
		        		// 分别获取对应的证明材料
		        		// 报废回收证明
		        		if (parentValue.JDCHSZM != null && parentValue.JDCHSZM != "") {
		        			setFilePreview("callbackProof", parentValue.JDCHSZM, "报废回收证明");
		        		}
		        		
		        		// 机动车注销证明
		        		if (parentValue.JDCZXZM != null && parentValue.JDCZXZM != "") {
		        			setFilePreview("vehicleCancelProof", parentValue.JDCZXZM, "机动车注销证明");
		        		}
		        		
		        		// 银行卡
		        		if (parentValue.YHK != null && parentValue.YHK != "") {
		        			setFilePreview("bankCard", parentValue.YHK, "银行卡");
		        		}
		        		
		        		// 车主身份证明
		        		if (parentValue.CZSFZM != null && parentValue.CZSFZM != "") {
		        			setFilePreview("vehicleOwnerProof", parentValue.CZSFZM, "车主身份证明");
		        		}
		        		
		        		// 代理人身份证明
		        		if (parentValue.DLRSFZ != null && parentValue.DLRSFZ != "") {
		        			setFilePreview("agentProof", parentValue.DLRSFZ, "代理人身份证明");
		        		}
		        		
		        		// 代理委托书
		        		if (parentValue.DLWTS != null && parentValue.DLWTS != "") {
		        			setFilePreview("agentProxy", parentValue.DLWTS, "代理委托书");
		        		}
		        		
		        		// 开户许可证
		        		if (parentValue.KHXKZ != null && parentValue.KHXKZ != "") {
		        			setFilePreview("openAccPromit", parentValue.KHXKZ, "开户许可证");
		        		}
		        		
		        		// 非财政供养单位证明
		        		if (parentValue.FCZGYZM != null && parentValue.FCZGYZM != "") {
		        			setFilePreview("noFinanceProvide", parentValue.FCZGYZM, "非财政供养单位证明");
		        		}
		        	}
					
					/* Dialog.create("capture_files", {
						type : "capture_files",
						title : "证明材料拍照上传",
						width : 900,
						height : 600,
						param: {
								reset:false,
								buttons:[
										{id:"confirm_back",text:"确认返回",iconCls:"icon-save"}
									]
							},
						maximizable : true,
						href : basePath+"/eliminatedApply/captureNew.jsp?isPersonal=" + isPersonal + "&isProxy=" + isProxy + "&proof_files=" + JSON.stringify(proof_files)
					}); */
				});
				
				// 车主身份证识别
				$("#btnVerifyVehicleOwnerCard").click(function() {
					var idCard = ReadIDCard();
					
					// 填入到车主身份证
					$("input[name='vehicleOwnerIdentity']").val(idCard);
					//alert(idCard);
					
					var isProxy = $("#isProxy").combobox("getValue");
					
					var vehicleOwnerIdentity = $("input[name='vehicleOwnerIdentity']").val();
					
					var agentIdentity = $("input[name='agentIdentity']").val();
					
					if (vehicleOwnerIdentity != "" && agentIdentity != "") {
						if (isProxy == "Y" && (idCard != vehicleOwnerIdentity || agentIdentity != idCard)) {
							alert("办理类型为自办，经办人身份证号必须与车主一致！");
							$("input[name='vehicleOwnerIdentity']").val("");
						} else if (isProxy == "N" && (vehicleOwnerIdentity == agentIdentity || idCard == vehicleOwnerIdentity)) {
							alert("办理类型为代理，经办人身份证号必须与车主身份证明号不同！");
							$("input[name='vehicleOwnerIdentity']").val("");
						}
					}
				});
				
				// 经办人身份证识别
				$("#btnVerifyAgentCard").click(function() {
					var idCard = ReadIDCard();
					
					$("input[name='agentIdentity']").val(idCard);
					
					var isProxy = $("#isProxy").combobox("getValue");
					
					var vehicleOwnerIdentity = $("input[name='vehicleOwnerIdentity']").val();
					
					var agentIdentity = $("input[name='agentIdentity']").val();
					
					if (vehicleOwnerIdentity != "" && agentIdentity != "") {
						if (isProxy == "Y" && (idCard != vehicleOwnerIdentity || agentIdentity != idCard)) {
							alert("办理类型为自办，经办人身份证号必须与车主一致！");
							$("input[name='agentIdentity']").val("");
						} else if (isProxy == "N" && (vehicleOwnerIdentity == agentIdentity || idCard == vehicleOwnerIdentity)) {
							alert("办理类型为代理，经办人身份证号必须与车主身份证明号不同！");
							$("input[name='agentIdentity']").val("");
						}
					}
					
					//alert(idCard);
				});
				
				// 报废回收证明抓拍上传
				$("#btnTakePhotoCallbackProof").click(function() {
					var vehiclePlateNum = $("input[name='vehiclePlateNum']").val();
					if (vehiclePlateNum == null || vehiclePlateNum == "" || typeof(vehiclePlateNum) == "undefined") {
						Messager.alert({
							type : "error",
							title : "&nbsp;",
							content : "请先输入号牌号码！"
						});
						return false;
					}
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedApply/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "dialogWidth=700px,dialogHeight=600px,resizable=yes,status=no,scrollbars=yes,menubar=no");
	        	
		        	//alert(parentValue.filepath);
		        	
		        	// 抓拍返回，设置图片路径到页面字段
		        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
		        		var files = parentValue.filepath.split(",");
		        		// 多张图片预览
		        		$("#callbackProofFileImg").text("报废回收证明(1)");
	        			$("#callbackProofFileImg").attr("href", basePath+'/'+files[0]);
		        		if (files.length > 1) {
		        			for (var i = 2 ; i <= files.length ; i ++) {
		        				var filepath = basePath + '/' + files[i-1];
		        				var _a = "&nbsp<a id='#callbackProofFileImg"+i+"' href='"+filepath+"' target='_blank'>报废回收证明("+i+")</a>";
		        				$("#callbackProofFileImg").append(_a);
		        			}
		        		} 
		        		
	        			// 设置隐藏字段传递到后台
	    				$("input[name='callbackProofFile']").val(parentValue.filepath);
		        	}
				});
				
				// 机动车注销证明
				$("#btnTakePhotoVehicleCancelProof").click(function() {
					var vehiclePlateNum = $("input[name='vehiclePlateNum']").val();
					if (vehiclePlateNum == null || vehiclePlateNum == "" || typeof(vehiclePlateNum) == "undefined") {
						Messager.alert({
							type : "error",
							title : "&nbsp;",
							content : "请先输入号牌号码！"
						});
						return false;
					}
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedApply/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "dialogWidth=700px,dialogHeight=600px,resizable=yes,status=no,scrollbars=yes,menubar=no");
	        	
		        	//alert(parentValue.filepath);
		        	
		        	// 抓拍返回，设置图片路径到页面字段
		        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
		        		var files = parentValue.filepath.split(",");
		        		// 多张图片预览
		        		$("#vehicleCancelProofFileImg").text("机动车注销证明(1)");
	        			$("#vehicleCancelProofFileImg").attr("href", basePath+'/'+files[0]);
		        		if (files.length > 1) {
		        			for (var i = 2 ; i <= files.length ; i ++) {
		        				var filepath = basePath + '/' + files[i-1];
		        				var _a = "&nbsp<a id='#vehicleCancelProofFileImg"+i+"' href='"+filepath+"' target='_blank'>机动车注销证明("+i+")</a>";
		        				$("#vehicleCancelProofFileImg").append(_a);
		        			}
		        		} 
		        		
	        			// 设置隐藏字段传递到后台
	    				$("input[name='vehicleCancelProofFiles']").val(parentValue.filepath);
		        	}
				});
				
				// 车主身份证明
				$("#btnTakePhotoVehicleOwnerProof").click(function() {
					var vehiclePlateNum = $("input[name='vehiclePlateNum']").val();
					if (vehiclePlateNum == null || vehiclePlateNum == "" || typeof(vehiclePlateNum) == "undefined") {
						Messager.alert({
							type : "error",
							title : "&nbsp;",
							content : "请先输入号牌号码！"
						});
						return false;
					}
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedApply/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "dialogWidth=700px,dialogHeight=600px,resizable=yes,status=no,scrollbars=yes,menubar=no");
	        	
		        	//alert(parentValue.filepath);
		        	
		        	// 抓拍返回，设置图片路径到页面字段
		        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
		        		var files = parentValue.filepath.split(",");
		        		// 多张图片预览
		        		$("#vehicleOwnerProofFileImg").text("车主身份证明(1)");
	        			$("#vehicleOwnerProofFileImg").attr("href", basePath+'/'+files[0]);
		        		if (files.length > 1) {
		        			for (var i = 2 ; i <= files.length ; i ++) {
		        				var filepath = basePath + '/' + files[i-1];
		        				var _a = "&nbsp<a id='#vehicleOwnerProofFileImg"+i+"' href='"+filepath+"' target='_blank'>车主身份证明("+i+")</a>";
		        				$("#vehicleOwnerProofFileImg").append(_a);
		        			}
		        		} 
		        		
	        			// 设置隐藏字段传递到后台
	    				$("input[name='vehicleOwnerProofFiles']").val(parentValue.filepath);
		        	}
				});
				
				// 银行卡
				$("#btnTakePhotoBankCard").click(function() {
					var vehiclePlateNum = $("input[name='vehiclePlateNum']").val();
					if (vehiclePlateNum == null || vehiclePlateNum == "" || typeof(vehiclePlateNum) == "undefined") {
						Messager.alert({
							type : "error",
							title : "&nbsp;",
							content : "请先输入号牌号码！"
						});
						return false;
					}
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedApply/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "dialogWidth=700px,dialogHeight=600px,resizable=yes,status=no,scrollbars=yes,menubar=no");
	        	
		        	//alert(parentValue.filepath);
		        	
		        	// 抓拍返回，设置图片路径到页面字段
		        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
		        		var files = parentValue.filepath.split(",");
		        		// 多张图片预览
		        		$("#bankCardFileImg").text("银行卡(1)");
	        			$("#bankCardFileImg").attr("href", basePath+'/'+files[0]);
		        		if (files.length > 1) {
		        			for (var i = 2 ; i <= files.length ; i ++) {
		        				var filepath = basePath + '/' + files[i-1];
		        				var _a = "&nbsp<a id='#bankCardFileImg"+i+"' href='"+filepath+"' target='_blank'>银行卡("+i+")</a>";
		        				$("#bankCardFileImg").append(_a);
		        			}
		        		} 
		        		
	        			// 设置隐藏字段传递到后台
	    				$("input[name='bankCardFiles']").val(parentValue.filepath);
		        	}
				});
				
				// 非财政供养单位证明
				$("#btnTakePhotoNoFinanceProvide").click(function() {
					var vehiclePlateNum = $("input[name='vehiclePlateNum']").val();
					if (vehiclePlateNum == null || vehiclePlateNum == "" || typeof(vehiclePlateNum) == "undefined") {
						Messager.alert({
							type : "error",
							title : "&nbsp;",
							content : "请先输入号牌号码！"
						});
						return false;
					}
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedApply/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "dialogWidth=700px,dialogHeight=600px,resizable=yes,status=no,scrollbars=yes,menubar=no");
	        	
		        	//alert(parentValue.filepath);
		        	
		        	// 抓拍返回，设置图片路径到页面字段
		        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
		        		var files = parentValue.filepath.split(",");
		        		// 多张图片预览
		        		$("#noFinanceProvideFileImg").text("非财政供养单位证明(1)");
	        			$("#noFinanceProvideFileImg").attr("href", basePath+'/'+files[0]);
		        		if (files.length > 1) {
		        			for (var i = 2 ; i <= files.length ; i ++) {
		        				var filepath = basePath + '/' + files[i-1];
		        				var _a = "&nbsp<a id='#noFinanceProvideFileImg"+i+"' href='"+filepath+"' target='_blank'>非财政供养单位证明("+i+")</a>";
		        				$("#noFinanceProvideFileImg").append(_a);
		        			}
		        		} 
		        		
	        			// 设置隐藏字段传递到后台
	    				$("input[name='noFinanceProvideFiles']").val(parentValue.filepath);
		        	}
				});
				
				// 开户许可证
				$("#btnTakePhotoOpenAccPromit").click(function() {
					var vehiclePlateNum = $("input[name='vehiclePlateNum']").val();
					if (vehiclePlateNum == null || vehiclePlateNum == "" || typeof(vehiclePlateNum) == "undefined") {
						Messager.alert({
							type : "error",
							title : "&nbsp;",
							content : "请先输入号牌号码！"
						});
						return false;
					}
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedApply/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "dialogWidth=700px,dialogHeight=600px,resizable=yes,status=no,scrollbars=yes,menubar=no");
	        	
		        	//alert(parentValue.filepath);
		        	
		        	// 抓拍返回，设置图片路径到页面字段
		        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
		        		var files = parentValue.filepath.split(",");
		        		// 多张图片预览
		        		$("#openAccPromitFileImg").text("开户许可证(1)");
	        			$("#openAccPromitFileImg").attr("href", basePath+'/'+files[0]);
		        		if (files.length > 1) {
		        			for (var i = 2 ; i <= files.length ; i ++) {
		        				var filepath = basePath + '/' + files[i-1];
		        				var _a = "&nbsp<a id='#openAccPromitFileImg"+i+"' href='"+filepath+"' target='_blank'>开户许可证("+i+")</a>";
		        				$("#openAccPromitFileImg").append(_a);
		        			}
		        		} 
		        		
	        			// 设置隐藏字段传递到后台
	    				$("input[name='openAccPromitFiles']").val(parentValue.filepath);
		        	}
				});
				
				// 代理委托书
				$("#btnTakePhotoAgentProxyProof").click(function() {
					var vehiclePlateNum = $("input[name='vehiclePlateNum']").val();
					if (vehiclePlateNum == null || vehiclePlateNum == "" || typeof(vehiclePlateNum) == "undefined") {
						Messager.alert({
							type : "error",
							title : "&nbsp;",
							content : "请先输入号牌号码！"
						});
						return false;
					}
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedApply/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "dialogWidth=700px,dialogHeight=600px,resizable=yes,status=no,scrollbars=yes,menubar=no");
	        	
		        	//alert(parentValue.filepath);
		        	
		        	// 抓拍返回，设置图片路径到页面字段
		        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
		        		var files = parentValue.filepath.split(",");
		        		// 多张图片预览
		        		$("#agentProxyFileImg").text("代理委托书(1)");
	        			$("#agentProxyFileImg").attr("href", basePath+'/'+files[0]);
		        		if (files.length > 1) {
		        			for (var i = 2 ; i <= files.length ; i ++) {
		        				var filepath = basePath + '/' + files[i-1];
		        				var _a = "&nbsp<a id='#agentProxyFileImg"+i+"' href='"+filepath+"' target='_blank'>代理委托书("+i+")</a>";
		        				$("#agentProxyFileImg").append(_a);
		        			}
		        		} 
		        		
	        			// 设置隐藏字段传递到后台
	    				$("input[name='agentProxyFiles']").val(parentValue.filepath);
		        	}
				});
				
				// 代理人身份证
				$("#btnTakePhotoAgentProof").click(function() {
					var vehiclePlateNum = $("input[name='vehiclePlateNum']").val();
					if (vehiclePlateNum == null || vehiclePlateNum == "" || typeof(vehiclePlateNum) == "undefined") {
						Messager.alert({
							type : "error",
							title : "&nbsp;",
							content : "请先输入号牌号码！"
						});
						return false;
					}
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedApply/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "dialogWidth=700px,dialogHeight=600px,resizable=yes,status=no,scrollbars=yes,menubar=no");
	        	
		        	//alert(parentValue.filepath);
		        	
		        	// 抓拍返回，设置图片路径到页面字段
		        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
		        		var files = parentValue.filepath.split(",");
		        		// 多张图片预览
		        		$("#agentProofFileImg").text("代理人身份证(1)");
	        			$("#agentProofFileImg").attr("href", basePath+'/'+files[0]);
		        		if (files.length > 1) {
		        			for (var i = 2 ; i <= files.length ; i ++) {
		        				var filepath = basePath + '/' + files[i-1];
		        				var _a = "&nbsp<a id='#agentProofFileImg"+i+"' href='"+filepath+"' target='_blank'>代理人身份证("+i+")</a>";
		        				$("#agentProofFileImg").append(_a);
		        			}
		        		} 
		        		
	        			// 设置隐藏字段传递到后台
	    				$("input[name='agentProofFiles']").val(parentValue.filepath);
		        	}
				});
				
			   //报废回收证明、机动车注销证明、银行卡、车主身份证明、代理委托书、代理人身份证、开户许可证、非财政供养单位证明
			   $("#btnUpload").click(function() {
				   
				   var isPersonal = $("#isPersonal").combobox("getValue");
				   var isProxy = $("#isProxy").combobox("getValue");
				   
				   if (isPersonal != "Y" && isPersonal != "N") {
					   alert("请选择车主类型！");
					   return false;
				   }
				   
				   if (isProxy != "Y" && isProxy != "N") {
					   alert("请选择办理类型！");
					   return false;
				   }
				   
				   //文件框页面校验，必填
				   //var ifValid = $("#form-apply-upload").form("enableValidation").form("validate");
				   var hasFileUpload = checkAttachmentsForUpload();
				   if (!hasFileUpload) {
					   alert("还有证明材料未选择，请选择本地证明材料！");
					   return false;
				   } else {
					   
				   //if (ifValid) {
					
					   var url =  $("#form-apply-upload").attr("action")+"?isPersonal="+isPersonal+"&isProxy="+isProxy+"&required="+true;
					   $("#form-apply-upload").form({
		        			url  : url,
		        		 	success : function(result) {
		        		 		var result = eval('(' + result + ')');
		        		 		if (result.success) {
		        		 			alert("文件上传成功！");
		        		 			
		        		 			// 设置文件回显、页面隐藏路径、点击事件等
		        		 			setFilePreview("callbackProof", result.message.callbackProofFiles, "报废回收证明");
		        		 			
		        		 			setFilePreview("vehicleCancelProof", result.message.vehicleCancelProof, "机动车注销证明");
		        		 			
		        		 			setFilePreview("bankCard", result.message.bankCard, "银行卡");
		        		 			
		        		 			setFilePreview("vehicleOwnerProof", result.message.vehicleOwnerProof, "车主身份证明");
		        		 			
		        					/* $("#callbackProofFileImg").text("报废汽车回收证明");
		        					$("#callbackProofFileImg").attr("href", basePath+'/'+result.message.callbackProofFiles);
		        					$("input[name='callbackProofFile']").val(result.message.callbackProofFiles);
		        					
		        					$("#vehicleCancelProofFileImg").text("机动车注销证明");
		        					$("#vehicleCancelProofFileImg").attr("href", basePath+'/'+result.message.vehicleCancelProof);
		        					$("input[name='vehicleCancelProofFiles']").val(result.message.vehicleCancelProof);
		        					
		        					$("#bankCardFileImg").text("银行卡");
		        					$("#bankCardFileImg").attr("href", basePath+'/'+result.message.bankCard);
		        					$("input[name='bankCardFiles']").val(result.message.bankCard);
		        					
		        					$("#vehicleOwnerProofFileImg").text("车主身份证明");
		        					$("#vehicleOwnerProofFileImg").attr("href", basePath+'/'+result.message.vehicleOwnerProof);
		        					$("input[name='vehicleOwnerProofFiles']").val(result.message.vehicleOwnerProof);
		        					*/
		        					
		        					// 办理类型为代办
		        					if (isProxy == "N") {
		        						setFilePreview("agentProxy", result.message.agentProxy, "代理委托书");
		        						
		        						setFilePreview("agentProof", result.message.agentProof, "代理人身份证");
		        						
			        					/* $("#agentProxyFileImg").text("代理委托书");
			        					$("#agentProxyFileImg").attr("href", basePath+'/'+result.message.agentProxy);
			        					$("input[name='agentProxyFiles']").val(result.message.agentProxy);
			        					
			        					$("#agentProofFileImg").text("代理人身份证");
			        					$("#agentProofFileImg").attr("href", basePath+'/'+result.message.agentProof);
			        					$("input[name='agentProofFiles']").val(result.message.agentProof); */
		        					}
		        					
		        					// 车主类型为企业
		        					if (isPersonal == "N") {
		        						setFilePreview("noFinanceProvide", result.message.noFinanceProvide, "非财政供养单位证明");
		        						
		        						setFilePreview("openAccPromit", result.message.openAccPromit, "开户许可证");
		        						
		        						/* $("#noFinanceProvideFileImg").text("非财政供养单位证明");
			        					$("#noFinanceProvideFileImg").attr("href", basePath+'/'+result.message.noFinanceProvide);
			        					$("input[name='noFinanceProvideFiles']").val(result.message.noFinanceProvide);
			        					
			        					$("#openAccPromitFileImg").text("开户许可证");
			        					$("#openAccPromitFileImg").attr("href", basePath+'/'+result.message.openAccPromit);
			        					$("input[name='openAccPromitFiles']").val(result.message.openAccPromit); */
		        					}
		        					
		        		 		} else {
		        		 			alert("文件上传失败！");
		        		 			
		        		 			// 清空页面选择的文件
		        		 			clearUploadFiles(isPersonal, isProxy);
		        		 		}
		        			}
		        	 	});
					 
					   $("#form-apply-upload").submit();
				   }
	        	 	
		        });
				
				Callback.onsubmit = function() {
					// 点击下一步，页面提交前的特殊处理
					return true;
				}
				
				// 下一步按钮点击事件处理函数
				$("#btnNextStep").click(function() {
					var isPersonal = $("#isPersonal").combobox("getValue");
				   	var isProxy = $("#isProxy").combobox("getValue");
				   
				   	if (isPersonal != "Y" && isPersonal != "N") {
					   alert("请选择车主类型！");
					   return false;
				   	}
				   
				   	if (isProxy != "Y" && isProxy != "N") {
					   alert("请选择办理类型！");
					   return false;
				   	}
					
				   	//alert($("input[name='vehiclePlateNum']").val());
				   	//$("input[name='vehiclePlateNum']").val(vehiclePlateNum);
				   	
					var isValid = $("#form-apply-save").form("enableValidation").form("validate");
					
					if (isValid) {
						// 校验注销日期，必选大于交售日期
						if (!checkDestroyDate()) {
							return false;	
						}
						
						// 校验经办人信息是否必填
						if (!checkAgentInfo(isPersonal)) {
							alert("请填写经办人信息！");
							return false;
						}
						
						// 校验其它银行是否必填
						if (!checkOtherBankName()) {
							alert("请填写其它银行名称！");
							return false;
						}
						
						// 点击下一步，校验必传文件是否上传
						var hasFileUpload = checkAttachments();
						if (!hasFileUpload) {
							alert("还有证明材料未上传，请先拍照上传！");
							return false;
						}
						
						$.ajax({
			                //cache: true,
			                type: "POST",
			                url:$("#form-apply-save").attr("action"),
			                data:$("#form-apply-save").serialize(),
			                async: true,
			                beforeSend : function(XMLHttpRequest) {
			                	// 点击下一步按钮时，将按钮置为灰色，防止重复点击提交
			                	$("#btnNextStep").linkbutton("disable");
			                },
			                success: function(data) {
			                	
			                	if(Object.prototype.toString.call(data) === "[object String]") {
									data = eval("(" + data + ")");
								}
								
								if(data.success) {
									
									Messager.show({
										title:"&nbsp;",
										content:data.message.msg
									});
									// 设置受理单号、受理表Id、档案盒编号
									$("input[name='id']").val(data.message.id);
									$("input[name='applyNo']").val(data.message.applyNo);
									
									// 资格校验成功，受理表信息保存，页面跳转到受理表打印预览页面
									var url = basePath+"/eliminatedApply/applyPreview.do?id="+data.message.id;
									
									$("#common-dialog").dialog("close");
									
									openDialog({
									   	type : "PRINT_APPLY_TABLE",
										title : "补贴受理表打印预览",
										width : 1040,
										height : 400,
										param: {reset:false,save:false,
											beforeCloseFunc:"clearCaptureRes",
											isBeforeClose:true},	
										maximizable : true,
										href : url
								   });
									
									
			                	} else {
			                		Messager.alert({
			                			type:"error",
										title:"&nbsp;",
										content:data.message.msg
									});
			                		
			                		// 清空表单元素
			                		//clearForm("form-apply-save", filters);
			                		
			                		$("#form-apply-save").form("clear");
									
									// 证明材料区域清空文件框值和回显路径
									clearUploadFiles(isPersonal, isProxy);
									//$("#form-apply-upload").form("clear");
									
									// 恢复下一步按钮正常点击触发提交事件
									$("#btnNextStep").linkbutton("enable");
									
			                		//$("#common-dialog").dialog("close");
			                	}
			            	},
			            	error : function(XMLHttpRequest, textStatus, errorThrown) {
				        		alert("服务器异常，请联系后台管理人员！");
				        		$("#btnNextStep").linkbutton("enable");
				        		return;
			            	}
						});
					} else {
						alert("请先填写受理表信息");
						return;
					}
				});
				
				var verify = function() {
					//校验输入的号牌号码和号牌种类，判断是否在系统中录入的车辆，过滤不符合资格或者补贴金额为0的车辆。
					var vehiclePlateNum = $("input[name='vehiclePlateNum']").val();
					if (vehiclePlateNum == "") {
						alert("请输入号牌号码！");
						return false;
					}
					if(vehiclePlateNum != null) {
						vehiclePlateNum = "B" + vehiclePlateNum;
					}
					var vehiclePlateType = $("#vehiclePlateTypeAdd").combobox("getValue");
					if (vehiclePlateType == "") {
						alert("请选择号牌种类！");
						return false;
					}
					
			        $.ajax({
			        	url : basePath + "/eliminatedApply/getVehicleInfo.do?vehiclePlateNum=" + vehiclePlateNum + "&vehiclePlateType=" + vehiclePlateType,
			        	async : true,
			        	type : "POST",
			        	dataType : "json",
			        	beforeSend : function() {
			        		if (null == vehiclePlateNum || null == vehiclePlateType) {
			        			return false;
			        		}
			        		// 提交表单前格式化号牌号码，截取粤字
			        		//$(input[name='vehiclePlateNum']).val();
			        	},
			        	success : function(data) {
			        		if (data.success) {
				        		//将获取到的数据填充到页面字段，并显示。
				        		// 厂牌型号
				        		$("input[name='vehicleModelNo']").val(data.message.vehicleModelNo);
				        		// 车辆类型
				        		$("input[name='vehicleType']").val(data.message.vehicleType);
				        		$("input[name='vehicleTypeName']").val(data.message.vehicleTypeName);
				        		// 发动机型号
				        		$("input[name='engineNo']").val(data.message.engineNo);
				        		// 车辆状态
				        		$("input[name='vehicleStatus']").val(data.message.vehicleStatus);
				        		$("input[name='vehicleStatusName']").val(data.message.vehicleStatusName);
				        		// 车架号
				        		$("input[name='vehicleIdentifyNo']").val(data.message.vehicleIdentifyNo);
				        		// 使用性质
				        		$("input[name='useOfProperty']").val(data.message.useOfProperty);
				        		$("input[name='useOfPropertyName']").val(data.message.useOfPropertyName);
				        		// 燃油种类
				        		$("input[name='iolType']").val(data.message.iolType);
				        		$("input[name='iolTypeName']").val(data.message.iolTypeName);
				        		// 总质量
				        		$("input[name='totalWeight']").val(data.message.totalWeight);
				        		// 核定载客数
				        		$("input[name='vehicleNumPeople']").val(data.message.vehicleNumPeople);
				        		// 初次登记日期
				        		$("input[name='registerDate']").val(getNowFormatDate(new Date(data.message.registerDate.time)));
				        		// 强制报废期止
				        		$("input[name='deadline']").val(getNowFormatDate(new Date(data.message.deadline.time)));
				        		// 车主
				        		$("textarea[name='vehicleOwner']").val(data.message.vehicleOwner);
				        		// 交售日期
				        		$("input[name='recycleDate']").val(getNowFormatDate(new Date(data.message.recycleDate.time)));
				        		// 排放标准
				        		$("input[name='emissionStandard']").val(data.message.emissionStandard);
				        		// 提前报废时长（天）
				        		$("input[name='advancedScrapDays']").val(data.message.advancedScrapDays);
				        		// 注销类别
				        		$("input[name='cancelReason']").val(data.message.cancelReason);
				        		// 车主身份号或者其它证明号码
				        		$("input[name='vehicleOwnerIdentity']").val(data.message.vehicleOwnerIdentity);
				        		// 车主手机号码
				        		$("input[name='mobile']").val(data.message.mobile);
				        		// 补贴账户名称
				        		$("textarea[name='bankAccountName']").val(data.message.bankAccountName);
				        		// 补贴金额
				        		$("input[name='subsidiesMoney']").val(data.message.subsidiesMoney);
				        		// 补贴标准
				        		$("textarea[name='subsidiesStandard']").val(data.message.subsidiesStandard);
				        		// 注销日期
				        		//$("input[name='destroyDate']").val(getNowFormatDate(new Date(data.message.destroyDate.time)));
				        		// 报废回收证明编号
				        		$("textarea[name='callbackProofNo']").val(data.message.callbackProofNo);
				        		
			        		} else {
			        			alert(data.message);
			        			// 清空前一次输入留下的数据，主要是指表单控件、隐藏域、文件上传框等。
								// 车辆信息、补贴对象信息、报废信息等表单控件
								$("#form-apply-save").form("clear");
								
								// 证明材料区域清空文件框值和回显路径
								var isPersonal = $("#isPersonal").combobox("getValue"); 
								var isProxy = $("#isProxy").combobox("getValue"); 
								clearUploadFiles(isPersonal, isProxy);
								//$("#form-apply-upload").form("clear");
			        		}
			        	},
			        	error : function(XMLHttpRequest, textStatus, errorThrown) {
			        		alert("服务器异常，请联系后台管理人员！");
			        		// 清空页面数据
			        	}
			        });
				}
				
				// 取报废录入数据和交警接口数据
				$("#btnApplyVerify").click(verify);
				
			});
			
			// 校验车辆是否符合补贴资格
			function verifyApply() {
				var basePath = "<%=basePath%>";
				//校验输入的号牌号码和号牌种类，判断是否在系统中录入的车辆，过滤不符合资格或者补贴金额为0的车辆。
				var vehiclePlateNum = $("input[name='vehiclePlateNum']").val();
				if (vehiclePlateNum == "") {
					alert("请输入号牌号码！");
					return false;
				}
				if(vehiclePlateNum != null) {
					vehiclePlateNum = "B" + vehiclePlateNum;
				}
				var vehiclePlateType = $("#vehiclePlateTypeAdd").combobox("getValue");
				if (vehiclePlateType == "") {
					alert("请选择号牌种类！");
					return false;
				}
				
		        $.ajax({
		        	url : basePath + "/eliminatedApply/getVehicleInfo.do?vehiclePlateNum=" + vehiclePlateNum + "&vehiclePlateType=" + vehiclePlateType,
		        	async : true,
		        	type : "POST",
		        	dataType : "json",
		        	beforeSend : function() {
		        		if (null == vehiclePlateNum || null == vehiclePlateType) {
		        			return false;
		        		}
		        		// 提交表单前格式化号牌号码，截取粤字
		        		//$(input[name='vehiclePlateNum']).val();
		        	},
		        	success : function(data) {
		        		if (data.success) {
			        		//将获取到的数据填充到页面字段，并显示。
			        		// 厂牌型号
			        		$("input[name='vehicleModelNo']").val(data.message.vehicleModelNo);
			        		// 车辆类型
			        		$("input[name='vehicleType']").val(data.message.vehicleType);
			        		$("input[name='vehicleTypeName']").val(data.message.vehicleTypeName);
			        		// 发动机型号
			        		$("input[name='engineNo']").val(data.message.engineNo);
			        		// 车辆状态
			        		$("input[name='vehicleStatus']").val(data.message.vehicleStatus);
			        		$("input[name='vehicleStatusName']").val(data.message.vehicleStatusName);
			        		// 车架号
			        		$("input[name='vehicleIdentifyNo']").val(data.message.vehicleIdentifyNo);
			        		// 使用性质
			        		$("input[name='useOfProperty']").val(data.message.useOfProperty);
			        		$("input[name='useOfPropertyName']").val(data.message.useOfPropertyName);
			        		// 燃油种类
			        		$("input[name='iolType']").val(data.message.iolType);
			        		$("input[name='iolTypeName']").val(data.message.iolTypeName);
			        		// 总质量
			        		$("input[name='totalWeight']").val(data.message.totalWeight);
			        		// 核定载客数
			        		$("input[name='vehicleNumPeople']").val(data.message.vehicleNumPeople);
			        		// 初次登记日期
			        		$("input[name='registerDate']").val(getNowFormatDate(new Date(data.message.registerDate.time)));
			        		// 强制报废期止
			        		$("input[name='deadline']").val(getNowFormatDate(new Date(data.message.deadline.time)));
			        		// 车主
			        		$("textarea[name='vehicleOwner']").val(data.message.vehicleOwner);
			        		// 交售日期
			        		$("input[name='recycleDate']").val(getNowFormatDate(new Date(data.message.recycleDate.time)));
			        		// 排放标准
			        		$("input[name='emissionStandard']").val(data.message.emissionStandard);
			        		// 提前报废时长（天）
			        		$("input[name='advancedScrapDays']").val(data.message.advancedScrapDays);
			        		// 注销类别
			        		$("input[name='cancelReason']").val(data.message.cancelReason);
			        		// 车主身份号或者其它证明号码
			        		$("input[name='vehicleOwnerIdentity']").val(data.message.vehicleOwnerIdentity);
			        		// 车主手机号码
			        		$("input[name='mobile']").val(data.message.mobile);
			        		// 补贴账户名称
			        		$("textarea[name='bankAccountName']").val(data.message.bankAccountName);
			        		// 补贴金额
			        		$("input[name='subsidiesMoney']").val(data.message.subsidiesMoney);
			        		// 补贴标准
			        		$("textarea[name='subsidiesStandard']").val(data.message.subsidiesStandard);
			        		// 注销日期
			        		//$("input[name='destroyDate']").val(getNowFormatDate(new Date(data.message.destroyDate.time)));
			        		// 报废回收证明编号
			        		$("textarea[name='callbackProofNo']").val(data.message.callbackProofNo);
			        		
		        		} else {
		        			alert(data.message);
		        			// 清空前一次输入留下的数据，主要是指表单控件、隐藏域、文件上传框等。
							// 车辆信息、补贴对象信息、报废信息等表单控件
							$("#form-apply-save").form("clear");
							
							// 证明材料区域清空文件框值和回显路径
							var isPersonal = $("#isPersonal").combobox("getValue"); 
							var isProxy = $("#isProxy").combobox("getValue"); 
							clearUploadFiles(isPersonal, isProxy);
							//$("#form-apply-upload").form("clear");
		        		}
		        	},
		        	error : function(XMLHttpRequest, textStatus, errorThrown) {
		        		alert("服务器异常，请联系后台管理人员！");
		        		// 清空页面数据
		        	}
		        });
			}
			
			function checkAttachments() {
				var isOk = true;
				var isPersonal = $("#isPersonal").combobox("getValue");
				var isProxy = $("#isProxy").combobox("getValue");
				
				// 报废回收证明
				if ($("input[name='callbackProofFile']").val() == "") {
					isOk = false;
				}
				
				// 机动车注销证明
				if ($("input[name='vehicleCancelProofFiles']").val() == "") {
					isOk = false;
				}
				// 车主身份证明
				if ($("input[name='vehicleOwnerProofFiles']").val() == "") {
					isOk = false;
				}
				// 银行卡
				if ($("input[name='bankCardFiles']").val() == "") {
					isOk = false;
				}
				// 企业需要非财政供养单位证明、开户许可证
				if (isPersonal == 'N') {
					if ($("input[name='noFinanceProvideFiles']").val() == "") {
						isOk = false;
					}
					if ($("input[name='openAccPromitFiles']").val() == "") {
						isOk = false;
					}
				}
				// 代理需要代理委托书、代理人身份证
				if (isProxy == "N") {
					if ($("input[name='agentProxyFiles']").val() == "") {
						isOk = false;
					}
					if ($("input[name='agentProofFiles']").val() == "") {
						isOk = false;
					}
				}
				
				return isOk;
			}
			
			// 双击预约车辆列表，获得预约车辆列表，并加载车辆数据。
			function dblClickAppointInfo(i) {
				// 车辆信息、补贴对象信息、报废信息等表单控件
				$("#form-apply-save").form("clear");
				
				// 证明材料区域清空文件框值和回显路径
				var isPersonal = $("#isPersonal").combobox("getValue"); 
				var isProxy = $("#isProxy").combobox("getValue"); 
				clearUploadFiles(isPersonal, isProxy);
				//$("#form-apply-upload").form("clear");
				
				// 双击每一行时触发
				var trId = "#appoint-"+i;
				$(trId).css("background-color", "red").siblings(".change").css("background-color","#fff");
				
				
				// 受理状态
				var applyStatus = $(trId).find("td:eq(8)").html();
				if (applyStatus == "已受理") {
					alert("改车辆已经受理，请选择其他未受理车辆！");
					return false;
				}
				
				// 号牌号码
				var vehiclePlateNum = $(trId).find("td:eq(1)").html();
				// 号牌种类Code
				var vehiclePlateType = $(trId).find("td:eq(3)").find("input[name='appointVehPlateCode']").val();
				
				// 补贴账户银行
				var bankName = $(trId).find("td:eq(5)").text();
				//alert(bankName);
				var bankCode = $(trId).find("td:eq(5)").find("input[name='appointBankCode']").val();
				
				// 补贴账户卡号
				var bankAccountNo = $(trId).find("td:eq(7)").html();
				
				// 经办人身份证
				var agentIdentity = $(trId).find("td:eq(7)").find("input[name='appointAgentIdentity']").val();
				
				// 设置受理页面的号牌号码和号牌种类值
				$("input[name='vehiclePlateNum']").val(vehiclePlateNum.substring(1));
				$("#vehiclePlateTypeAdd").combobox("setValue", vehiclePlateType);
				
				// 直接调用后台资格校验接口返回机动车数据
				verifyApply();
				
				// 设置开户银行和银行账号
				$("#bankAccountNo").numberbox("setValue", bankAccountNo);
				$("#bankCodeAdd").combobox("setValue", bankCode);
				if (bankCode == "999") {
					$("input[name='bankName']").show();
					$("input[name='bankName']").val(bankName);
				}
				//$("#bankCode").combobox("select", bankCode);
				
				// 设置经办人身份证
				$("input[name='agentIdentity']").val(agentIdentity);
			}
			
			// 检查注销日期是否填写且合法
			function checkDestroyDate() {
				var isOK = true;
				var recycleDate = $("input[name='recycleDate']").val();
				var destroyDate = $("input[name='destroyDate']").val();
				if (destroyDate == "") {
					alert("请填写注销日期！");
					return false;
				}
				var stdt = new Date(recycleDate.replace("-","/"));
				var etdt = new Date(destroyDate.replace("-","/"));
				var now = new Date();
				if (etdt < stdt || etdt > now) {
					alert("注销日期不合法（注销日期应介于报废日期与今天之间），请重新填写！");
					isOK = false;
				}
				return isOK;
			}
			
			// 代理则经办人信息必填
			function checkAgentInfo(isPersonal) {
				var isOk = true;
				if (isPersonal == "N") {
					// 获取经办人信息
					var agent = $("input[name='agent']").val();
					var agentMobileNo = $("input[name='agentMobileNo']").val();
					var agentIdentity = $("input[name='agentIdentity']").val();
					
					if (agent == "") {
						isOk = false;
					}
					
					if (agentMobileNo == "") {
						isOk = false;
					}
					
					if (agentIdentity == "") {
						isOk = false;
					}
				}
				return isOk;
			}
			
			// 校验其它银行必填
			function checkOtherBankName() {
				var isOk = true;
				var bankCode = $("#bankCodeAdd").combobox("getValue");
				if (bankCode == "999" && $("input[name='bankName']").val() == "") {
					isOk = false;
				}
				return isOk;
			}
			
			// 清空文件上传框的值，使得可以重新选择文件进行上传
			function clearUploadFiles(isPersonal, isProxy) {
				$('#form-apply-upload')[0].reset();
				
				if (isPersonal == 'N') {
					// 如果是企业，则清空页面开户许可证和非财政供养单位证明的文件路径
					$("input[name='noFinanceProvideFiles']").val("");

					$("input[name='openAccPromitFiles']").val("");
				}
				
				if (isProxy == 'N') {
					// 如果是代理，则清空页面代理人身份证明和代理委托书的文件路径
					$("input[name='agentProofFiles']").val("");

					$("input[name='agentProxyFiles']").val("");
				}
				
				$("input[name='callbackProofFile']").val("");

				$("input[name='vehicleCancelProofFiles']").val("");
				
				$("input[name='bankCardFiles']").val("");

				$("input[name='vehicleOwnerProofFiles']").val("");
				
				// 清空页面超链接
				
				/* if (isPersonal == 'N') {
					// 如果是企业，则清空开户许可证和非财政供养单位证明
					//$("#openAccPromit").filebox("setValue", "");
					//$("#noFinanceProvide").filebox("setValue", "");
					
					$("#openAccPromitFileImg").text("");
	    			$("#openAccPromitFileImg").attr("href", "#");
	    			
	    			$("#noFinanceProvideFileImg").text("");
	    			$("#noFinanceProvideFileImg").attr("href", "#");
				}
				if (isProxy == 'N') {
					// 如果是代办，则清空代理人身份证明和代理委托书
					//$("#agentProof").filebox("setValue", "");
					//$("#agentProxy").filebox("setValue", "");
					
					$("#agentProofFileImg").text("");
	    			$("#agentProofFileImg").attr("href", "#");
	    			
	    			$("#agentProxyFileImg").text("");
	    			$("#agentProxyFileImg").attr("href", "#");
				}
				//$("#callbackProofFiles").filebox("setValue", "");
				//$("#vehicleCancelProof").filebox("setValue", "");
				//$("#bankCard").filebox("setValue", "");
				//$("#vehicleOwnerProof").filebox("setValue", "");
				
				// 清空文件预览后的链接
				$("#callbackProofFileImg").text("");
    			$("#callbackProofFileImg").attr("href", "#");
    			
    			$("#vehicleCancelProofFileImg").text("");
    			$("#vehicleCancelProofFileImg").attr("href", "#");
    			
    			$("#bankCardFileImg").text("");
    			$("#bankCardFileImg").attr("href", "#");
    			
    			$("#vehicleOwnerProofFileImg").text("");
    			$("#vehicleOwnerProofFileImg").attr("href", "#"); */
			}
			
			// 图片集中预览
			function picturePreview(currentType, index) {
				var basePath = "<%=basePath%>";
				// 获取页面所有图片路径，传递到新窗口
				// 先获取页面已经上传的文件路径
				var proof_files = window;
				
				// 报废回收证明
				proof_files.callbackProofFile = $("input[name='callbackProofFile']").val();
				// 机动车注销证明
				proof_files.vehicleCancelProofFiles = $("input[name='vehicleCancelProofFiles']").val();
				// 银行卡
				proof_files.bankCardFiles = $("input[name='bankCardFiles']").val();
				// 车主身份证明
				proof_files.vehicleOwnerProofFiles = $("input[name='vehicleOwnerProofFiles']").val();
				// 开户许可证
				proof_files.openAccPromitFiles = $("input[name='openAccPromitFiles']").val();
				// 非财政供养单位证明
				proof_files.noFinanceProvideFiles = $("input[name='noFinanceProvideFiles']").val();
				// 代理人身份证
				proof_files.agentProofFiles = $("input[name='agentProofFiles']").val();
				// 代理委托书
				proof_files.agentProxyFiles = $("input[name='agentProxyFiles']").val();
				
				//alert("adadad");
				window.open(basePath + '/eliminatedApply/picturePreview.jsp?filetype='+currentType+'&index='+index,'证明材料预览','height=300,width=400,top=200,left=400,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
			}
			
			// 根据文件路径设置回显和传递到后台的隐藏值
			function setFilePreview(type, files, name) {
				var basePath = "<%=basePath%>";
				
				if (files == null && files == "") {
					return;
				}
				
				var filesArray = files.split(",");
				var id = "#" + type + "FileImg";
				var filename;
				
				if (type == "callbackProof") {
					filename = type + "File";
				} else {
					filename = type + "Files";
				}
        		// 多张图片预览
        		$(id).text(name + "(1)");
        		
        		// 添加图片点击事件，到新窗口预览
        		$(id).attr('href', 'javascript:void(0)');
        		//$(id).attr('href', "javascript:picturePreview('"+type+"');");
        		
        		$(id).attr('onclick', "picturePreview('"+type+"', '1');");
        		
    			//$(id).attr("href", basePath+'/'+filesArray[0]);
    			
        		if (filesArray.length > 1) {
        			for (var i = 2 ; i <= filesArray.length ; i ++) {
        				//var filepath = basePath + '/' + filesArray[i-1];
        				//var filepath = "picturePreview('"+type+"');";
        				var filepath = "picturePreview('"+type+"', '"+ i +"');";
        				var _a = "&nbsp<a id='"+id+i+"' href='javascript:void(0)' onclick='"+filepath+"'>"+name+"("+i+")</a>";
        				$(id).append(_a);
        			}
        		} 
        		
    			// 设置隐藏字段传递到后台
				$("input[name='"+filename+"']").val(files);
			}
			
			// 关闭窗口后清空绑定的关闭前处理事件
			/* function clearBeforeCloseFunc() {
				$("#common-dialog").dialog("options", {onBeforeClose:null});
			} */
			// 检查必填证明材料是否选择			
			function checkAttachmentsForUpload() {
				var isOk = true;
				var isPersonal = $("#isPersonal").combobox("getValue");
				var isProxy = $("#isProxy").combobox("getValue");
				
				// 报废回收证明
				if ($("input[name='callbackProofFiles']").val() == "") {
					isOk = false;
				}
				
				// 机动车注销证明
				if ($("input[name='vehicleCancelProof']").val() == "") {
					isOk = false;
				}
				// 车主身份证明
				if ($("input[name='vehicleOwnerProof']").val() == "") {
					isOk = false;
				}
				// 银行卡
				if ($("input[name='bankCard']").val() == "") {
					isOk = false;
				}
				// 企业需要非财政供养单位证明、开户许可证
				if (isPersonal == 'N') {
					if ($("input[name='noFinanceProvide']").val() == "") {
						isOk = false;
					}
					if ($("input[name='openAccPromit']").val() == "") {
						isOk = false;
					}
				}
				// 代理需要代理委托书、代理人身份证
				if (isProxy == "N") {
					if ($("input[name='agentProxy']").val() == "") {
						isOk = false;
					}
					if ($("input[name='agentProof']").val() == "") {
						isOk = false;
					}
				}
				
				return isOk;
			}
			
		</script>
		
</body>
</html>