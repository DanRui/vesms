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
<title>Test-add</title>
</head>
<body>
	<form id="form-apply-save" action="eliminatedApply/save.do" method="post">
		<input name="stage" type="hidden" value="${stage}"/>
		<input name="applyNo" type="hidden"/>
		<input name="id" type="hidden"/>
		<div id="eliminated-apply-add" class="datagrid-header">
			<table class="datagrid-table-s datagrid-htable">
				<tr class="datagrid-header-row">
					<td class="view_table_left">请输入预约单号：</td>
					<td class="view_table_right" colspan="2">
						<input type="text" name="appointmentNo" value="${appointmentNo}"/>
						<a id="btnAppointment" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-baofei-message'">获取预约数据</a>
					</td>
				</tr>
				<tr class="datagrid-row">
					<td colspan="6">
						<div id="appoint-list">
						</div>
					</td>
				</tr>
				<tr class="datagrid-header-row">
					<td class="view_table_left">车主类型：</td>
					<td class="view_table_right" colspan="2">
						<select id="isPersonal" name="isPersonal" class="easyui-combobox" data-options="editable:false,required:true,width:160,panelHeight:'auto'">
							<option selected>请选择是自然人或企业</option> 
							<option value="Y">自然人</option>
							<option value="N">企业</option>
						</select>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					<td class="view_table_left">办理类型：</td>
					<td class="view_table_right" colspan="2" >					
						<select id="isProxy" name="isProxy" class="easyui-combobox" data-options="editable:false,required:true,width:75,panelHeight:'auto'">
							<option selected>请选择</option>
							<option value="Y">自办</option>
							<option value="N">代办</option>
						</select>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
				</tr>
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">车辆基本信息</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">号牌号码：</td>
					<td class="view_table_right">
						<input type="text" name="vehiclePlateNum" class="easyui-validatebox" data-options="required:true"  />
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					<td class="view_table_left">号牌种类：</td>
					<td class="view_table_right">
						<input id="vehiclePlateTypeNew" name="vehiclePlateType"/>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
						<a id="btnApplyVerify" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-baofei-message'">取机动车数据</a>
					</td>
					<td class="view_table_left">厂牌型号：</td>
					<td class="view_table_right">
						<input type="text" name="vehicleModelNo" readonly="readonly"/></td>

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
						<input type="text" name="destroyDate" readonly="readonly"/>
					</td>
					<td class="view_table_left">车辆状态：</td>
					<td class="view_table_right">
						<input type="text" name="vehicleStatus" />
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
						<!-- <input type="text" name="isFinancialSupport" readonly="readonly"/> -->
						<select id="isFinancialSupport" name="isFinancialSupport" class="easyui-combobox" data-options="editable:false,required:true,width:118,panelHeight:'auto'">
							<option value="1">个人</option>
							<option value="2">车主自证非财政供养</option>
						</select>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>		
				</tr>
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">报废信息</td>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:110px">报废回收证明编号：</td>
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
					<td colspan="6">补贴对象信息</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">车主：</td>
					<td class="view_table_right">
						<!-- <input type="text" name="vehicleOwner" readonly="readonly"/> -->
						<textarea  name="vehicleOwner" class="easyui-validatebox" readonly="readonly"></textarea>
					</td>
					<td class="view_table_left">车主身份证号码或其它证明：</td>
					<td class="view_table_right">
						<input type="text" name="vehicleOwnerIdentity" class="easyui-validatebox" data-options="required:true"/>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					<td class="view_table_left">车主联系号码：</td>
					<td class="view_table_right">
						<input type="text" name="mobile" class="easyui-numberbox" />
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">经办人：</td>
					<td class="view_table_right">
						<input type="text" name="agent" class="easyui-validatebox" data-options="required:true"/>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					<td class="view_table_left">经办人手机号：</td>
					<td class="view_table_right">
						<input type="text" name="agentMobileNo" class="easyui-numberbox" data-options="required:true"/>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					<td class="view_table_left">经办人身份证号：</td>
					<td class="view_table_right">
						<input type="text" name="agentIdentity" class="easyui-validatebox" data-options="required:true"/>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
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
						<input id="bankName" class="easyui-combobox" name="bankName" 
						data-options="editable:false,required:true,valueField:'code',textField:'value',url:'sysDict/getDictListFromMap.do?dictType=BANK_CODE',panelHeight:150"/>
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
						<textarea name="subsidiesStandard" readonly="readonly"></textarea>
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
					<td class="view_table_left" style="width:110px">报废汽车回收证明：</td>
					<td class="view_table_right" colspan="2">
						<input id="callbackProofFiles" name="callbackProofFiles" data-options="editable:false,required:true,buttonText:'请选择'"
						class="easyui-filebox" />
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoCallbackProof" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="callbackProofFileImg" href="#" target="_blank"></a>
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:110px">机动车注销证明：</td>
					<td class="view_table_right" colspan="2">
						<input id="vehicleCancelProof" class="easyui-filebox" name="vehicleCancelProof" data-options="editable:false,required:true,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoVehicleCancelProof" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="vehicleCancelProofFileImg" href="#" target="_blank"></a>
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:120px">银行卡：</td>
					<td class="view_table_right" colspan="2">
						<input id="bankCard" class="easyui-filebox" name="bankCard" data-options="editable:false,required:true,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoBankCard" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="bankCardFileImg" href="#" target="_blank"></a>
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:110px">车主身份证明：</td>
					<td class="view_table_right" colspan="2">
						<input id="vehicleOwnerProof" class="easyui-filebox" name="vehicleOwnerProof" data-options="editable:false,required:true,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoVehicleOwnerProof" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="vehicleOwnerProofFileImg" href="#" target="_blank"></a>
					</td>
				</tr>
				<tr class="datagrid-row proxyType">
					<td class="view_table_left" style="width:120px">代理委托书：</td>
					<td class="view_table_right" colspan="2">
						<input id="agentProxy" class="easyui-filebox" name="agentProxy" data-options="editable:false,width:141,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoAgentProxyProof" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="agentProxyFileImg" href="#" target="_blank"></a>
					</td>
				</tr>
				<tr class="datagrid-row proxyType">
					<td class="view_table_left" style="width:110px">代理人身份证：</td>
					<td class="view_table_right" colspan="2">
						<input id="agentProof" class="easyui-filebox" name="agentProof" data-options="editable:false,width:141,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoAgentProof" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="agentProofFileImg" href="#" target="_blank"></a>
					</td>
				</tr>
				<tr class="datagrid-row applyType">
					<td class="view_table_left" style="width:120px">非财政供养单位证明：</td>
					<td class="view_table_right" colspan="2">
						<input id="noFinanceProvide" class="easyui-filebox" name="noFinanceProvide" data-options="editable:false,width:141,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoNoFinanceProvide" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="noFinanceProvideFileImg" href="#" target="_blank"></a>
					</td>
				</tr>
				<tr class="datagrid-row applyType">
					<td class="view_table_left" style="width:110px">开户许可证：</td>
					<td class="view_table_right" colspan="2">
						<input id="openAccPromit" class="easyui-filebox" name="openAccPromit" data-options="editable:false,width:141,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoOpenAccPromit" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="openAccPromitFileImg" href="#" target="_blank"></a>
					</td>
				</tr>
				<tr class="datagrid-row">
					<td align="center" colspan="6">
						<a id="btnUpload" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-shangchuan'">上传</a>
					</td>
				</tr>
			</table>
		</div>
	</form>
	
	<div id="update_buttons" align="center">
		<a id="btnNextStep" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-nextstep'">下一步</a>
	</div>
		
		<script type="text/javascript">
			$(function() {
				
				var basePath = "<%=basePath%>";
				
				// 页面渲染完成，构造号牌种类下拉框
				//"editable:false,required:true,valueField:'code',textField:'value',url:'sysDict/getDictListFromMap.do?dictType=VEHICLE_PLATE_TYPE',panelHeight:'auto'"
				$("#vehiclePlateTypeNew").combobox({
					editable : false,
					reqiured : true,
					valueField : 'code',
					textField : 'value',
					panelHeight : 'auto'
				});
				
				$("#vehiclePlateTypeNew").combobox("reload", basePath + '/sysDict/getDictListFromMap.do?dictType=VEHICLE_PLATE_TYPE');
				
				// 重新调整页面窗口大小和位置居中
				/* $("#common-dialog").dialog({
					title : "申报受理录入"
				}).dialog("resize", {
					width : 1132,
					height : 800 
				}).dialog("center"); */
				
				// 初始化号牌号码输出框，显示粤B开头
				$("input[name='vehiclePlateNum']").val("粤B");
				
				// 录入时判断是否预约过
				var appointmentNo = $("input[name='appointmentNo']").val();
				if (appointmentNo != "" && appointmentNo != null && appointmentNo != undefined) {
					//有预约号
					$("input[name='appointmentNo']").val(appointmentNo);
					var appointments = '${appointments}';
					var appointList = eval('(' + appointments + ')');
					if (appointList.total <= 0) {
						$("DIV#appoint-list").hide();
        			} else {
	        			$("DIV#appoint-list").show();
	        			var appointDiv = $("DIV#appoint-list");
	        			var table = $("<table style='border-collapse:collapse' id='appoint-table' bordercolor='#111111' cellspacing='0' cellpadding='2' border='1'></table>");
	        			var th = "<tr border='1'><td colspan='9'>预约车辆列表：(<font color='red'>请双击选择车辆进行受理</font>)</td></tr>";
	        			table.append(th);
	        			for (var i = 0 ; i < appointList.total ; i ++) {
	        				var tr = "<tr id='appoint-"+i+"' onDblClick='dblClickAppointInfo("+i+");'>"
	        					   + "<td>号牌号码</td><td>"+appointList.list[i].vehiclePlateNum+"</td>"
	        				       + "<td>号牌种类</td><td>"+appointList.list[i].vehiclePlateTypeName+"<input type='hidden' name='appointVehPlateCode' value='"+ appointList.list[i].vehiclePlateType +"'/></td>"
	        				       + "<td>补贴银行</td><td>"+appointList.list[i].bankName+"<input type='hidden' name='appointBankCode' value='"+ appointList.list[i].bankCode +"'/></td>"
	        				       + "<td>银行账号</td><td>"+appointList.list[i].bankAccount+"</td>"
	        				       + "<td>"+appointList.list[i].applyStatus+"</td>"
	        				       + "</tr>";
	        				table.append(tr);
	        			}
	        			appointDiv.append(table);
        			}
				} else {
					// 无预约号
					// 页面DOM加载完毕后,隐藏预约车辆列表区域
					$("DIV#appoint-list").hide();
				}
				
				//隐藏组织结构类型和文件上传区域
				$("tr[class*='applyType']").each(function() {
					$(this).hide();
				});
				
				$("tr[class*='proxyType']").each(function() {
					$(this).hide();
				});
				
				$("#isPersonal").combobox({
					onChange : function() {
						var isPersonal = $(this).combobox("getValue");
						$("tr[class*='applyType']").each(function() {
							if (isPersonal == "N") {
								$("#isProxy").combobox("setValue", "N");
								$(this).show();
								$("#noFinanceProvide").attr("required","true");
								$("#openAccPromit").attr("required","true");
								//$("textarea[name='checkOpinion']").attr("required","true");
								//$("#checkOpinion").validatebox({required:true});
							} else if (isPersonal == "Y") {
								$(this).hide();
								// 车主类型是个人,隐藏开户许可证和非财政供养单位证明
								$("#noFinanceProvide").attr("required","false");
								$("#openAccPromit").attr("required","false");
								//$("#checkOpinion").validatebox({required:true});
							}
						});
					}
				});
				
				$("#isProxy").combobox({
					onChange : function() {
						var isProxy = $(this).combobox("getValue");
						$("tr[class*='proxyType']").each(function() {
							if (isProxy == "N") {
								$(this).show();
								$("#agentProxy").attr("required","true");
								$("#agentProof").attr("required","true");
								//$("textarea[name='checkOpinion']").attr("required","true");
								//$("#checkOpinion").validatebox({required:true});
							} else if (isProxy == "Y") {
								$("#isPersonal").combobox("setValue", "Y");
								$(this).hide();
								// 自办隐藏代理委托书和代理人身份证明
								$("#agentProxy").attr("required","false");
								$("#agentProof").attr("required","false");
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
					
					if (appointmentNo.length != 15) {
						alert("预约号输入位数错误！");
						return false;
					}
					
					/* if (!DateFormatter.DateISO(appointmentNo.substring(5, 13), "yyyyMMdd")) {
						// 日期格式不匹配
						alert("输入的预约单号有误，请检查！");
						return false;
					} */
					// $("DIV#appoint-list").empty();
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
			        			} else {
				        			var list = data.message.list;
				        			$("DIV#appoint-list").show();
				        			var appointDiv = $("DIV#appoint-list");
				        			var table = $("<table style='border-collapse:collapse' id='appoint-table' bordercolor='#111111' cellspacing='0' cellpadding='2' border='1'></table>");
				        			var th = "<tr border='1'><td colspan='9'>预约车辆列表：(<font color='red'>请双击选择车辆进行受理</font>)</td></tr>";
				        			table.append(th);
				        			for (var i = 0 ; i < data.message.total ; i ++) {
				        				var tr = "<tr id='appoint-"+i+"' onDblClick='dblClickAppointInfo("+i+");'>"
				        					   + "<td>号牌号码</td><td>"+list[i].vehiclePlateNum+"</td>"
				        				       + "<td>号牌种类</td><td>"+list[i].vehiclePlateTypeName+"<input type='hidden' name='appointVehPlateCode' value='"+ list[i].vehiclePlateType +"'/></td>"
				        				       + "<td>补贴银行</td><td>"+list[i].bankName+"<input type='hidden' name='appointBankCode' value='"+ list[i].bankCode +"'/></td>"
				        				       + "<td>银行账号</td><td>"+list[i].bankAccount+"</td>"
				        				       + "<td>"+list[i].applyStatus+"</td>"
				        				       + "</tr>";
				        				table.append(tr);
				        			}
				        			appointDiv.append(table);
			        			}
			        			
			        		} else {
			        			alert(data.message);
			        		}
			        	}
					});
					
				});
				
				// 报废回收证明抓拍上传
				$("#btnTakePhotoCallbackProof").click(function() {
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedApply/capture.jsp", "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
	        	
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
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedApply/capture.jsp", "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
	        	
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
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedApply/capture.jsp", "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
	        	
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
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedApply/capture.jsp", "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
	        	
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
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedApply/capture.jsp", "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
	        	
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
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedApply/capture.jsp", "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
	        	
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
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedApply/capture.jsp", "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
	        	
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
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedApply/capture.jsp", "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
	        	
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
				   var ifValid = $("#form-apply-upload form").form("enableValidation").form("validate");
				   
				   if (ifValid) {
					
					   var url =  $("#form-apply-upload").attr("action")+"?isPersonal="+isPersonal+"&isProxy="+isProxy;
					   $("#form-apply-upload").form({
		        			url  : url,
		        		 	success : function(result) {
		        		 		var result = eval('(' + result + ')');
		        		 		if (result.success) {
		        		 			alert("文件上传成功！");
		        					$("#callbackProofFileImg").text("报废汽车回收证明");
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
		        					
		        					// 办理类型为代办
		        					if (isProxy == "N") {
			        					$("#agentProxyFileImg").text("代理委托书");
			        					$("#agentProxyFileImg").attr("href", basePath+'/'+result.message.agentProxy);
			        					$("input[name='agentProxyFiles']").val(result.message.agentProxy);
			        					
			        					$("#agentProofFileImg").text("代理人身份证");
			        					$("#agentProofFileImg").attr("href", basePath+'/'+result.message.agentProof);
			        					$("input[name='agentProofFiles']").val(result.message.agentProof);
		        					}
		        					
		        					// 车主类型为企业
		        					if (isPersonal == "N") {
		        						$("#noFinanceProvideFileImg").text("非财政供养单位证明");
			        					$("#noFinanceProvideFileImg").attr("href", basePath+'/'+result.message.noFinanceProvide);
			        					$("input[name='noFinanceProvideFiles']").val(result.message.noFinanceProvide);
			        					
			        					$("#openAccPromitFileImg").text("开户许可证");
			        					$("#openAccPromitFileImg").attr("href", basePath+'/'+result.message.openAccPromit);
			        					$("input[name='openAccPromitFiles']").val(result.message.openAccPromit);
		        					}
		        					
		        		 		} else {
		        		 			alert("文件上传失败！");
		        		 			
		        		 			$("#callbackProofFile").filebox("clear");
		        		 			$("#vehicleCancelProof").filebox("clear");
		        		 			$("#bankCard").filebox("clear");
		        		 			$("#vehicleOwnerProof").filebox("clear");
		        		 			if (isPersonal == "N") {
			        		 			$("#agentProxy").filebox("clear");
			        		 			$("#agentProof").filebox("clear");
		        		 			}
		        		 			if (isProxy == "N") {
		        		 				$("#noFinanceProvide").filebox("clear");
		        		 				$("#openAccPromit").filebox("clear");
		        		 			}
		        		 		}
		        			}
		        	 	});
					 
					   $("#form-apply-upload").submit();
				   } else {
					   alert("还有证明材料未选择，请选择后上传！");
					   return false;
				   }
				   
	        	 	
		        });
				
				// 取报废录入数据和交警接口数据
				$("#btnApplyVerify").bind("click", function() {
					//校验输入的号牌号码和号牌种类，判断是否在系统中录入的车辆，过滤不符合资格或者补贴金额为0的车辆。
					var vehiclePlateNum = $("input[name='vehiclePlateNum']").val();
					if(vehiclePlateNum!=null && vehiclePlateNum.indexOf("粤") != -1) {
						vehiclePlateNum = vehiclePlateNum.substring(1);
					}
					if (vehiclePlateNum == "" || vehiclePlateNum < 6) {
						alert("号牌号码为空！");
						return false;
					}
					var vehiclePlateType = $("#vehiclePlateTypeNew").combobox("getValue");
					if (vehiclePlateType == "") {
						alert("号牌种类为空！");
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
				        		$("input[name='destroyDate']").val(getNowFormatDate(new Date(data.message.destroyDate.time)));
				        		// 报废回收证明编号
				        		$("textarea[name='callbackProofNo']").val(data.message.callbackProofNo);
				        		
			        		} else {
			        			alert("报废数据获取失败!");
			        		}
			        	}
			        });
					//alert("机动车未注销，不得受理！");
					//$("#common-dialog").dialog("close");
				});
				
				
				Callback.onsubmit = function() {
					// 点击下一步，页面提交前的特殊处理
					return true;
					
				}
				
				// 下一步按钮点击事件处理函数
				$("#btnNextStep").click(function() {
					var isValid = $("#common-dialog form").form("enableValidation").form("validate");
					
					if (isValid) {
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
			                data:$("#form-apply-save").serialize(),//
			                async: true,
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
									//$("input[name='archiveBoxNo']").val(data.message.archiveBoxNo);
									//$("input[name='archivedInnerNo']").val(data.message.archivedInnerNo);
									
									// 页面刷新前，先移除下一步按钮点击事件
									/* if(window.addEventListener) { // Mozilla, Netscape, Firefox   
										obj.removeEventListener('click', handler, false);    
									} else if(window.attachEvent) { // IE   
										obj.detachEvent('onclick', handler);   
								    } else {  
								    	obj.onclick= "";
								    } */
									
									// 资格校验成功，受理表信息保存，页面跳转到受理表打印预览页面
									var url = basePath+"/eliminatedApply/applyPreview.do?id="+data.message.id;
									$("#common-dialog").dialog("refresh", url);
									
			                	} else {
			                		Messager.show({
										title:"&nbsp;",
										content:data.message
									});
			                		$("#common-dialog").dialog("close");
			                	}
			            	}
						});
					} else {
						alert("请先填写受理表信息");
						// 校验证明材料是否上传齐全
					}
				
				});
				
			});
			
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
			
			function dblClickAppointInfo(i) {
				// 双击每一行时触发
				var trId = "#appoint-"+i;
				
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
				var bankCode = $(trId).find("td:eq(5)").find("input[name='appointBankCode']").val();
				
				// 补贴账户卡号
				var bankAccountNo = $(trId).find("td:eq(7)").html();
				
				// 设置受理页面的号牌号码和号牌种类值
				$("input[name='vehiclePlateNum']").val(vehiclePlateNum);
				$("#vehiclePlateTypeNew").combobox("setValue", vehiclePlateType);
				
				// 设置开户银行和银行账号
				$("#bankName").combobox("setValue", bankCode);
				$("#bankAccountNo").numberbox("setValue", bankAccountNo);
			}
			
		</script>
		
</body>
</html>