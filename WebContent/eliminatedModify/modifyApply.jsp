<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
		<div id="dataEdit-apply-add" class="datagrid-header">
			<table class="datagrid-table-s datagrid-htable">
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">经办人信息</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">经办人：</td>
					<td class="view_table_right">
						${v.agent}			
					</td>
					<td class="view_table_left">经办人手机号：</td>
					<td class="view_table_right">
						${v.agentMobileNo}
					</td>
					<td class="view_table_left">经办人身份证号：</td>
					<td class="view_table_right">
						${v.agentIdentity}					
					</td>
				</tr>

				<c:if test="${fn:contains(modifyTypes, '10') || fn:contains(modifyTypes, '11') || fn:contains(modifyTypes, '12') || fn:contains(modifyTypes, '13')}">
				<tr class="datagrid-row">
					<c:if test="${fn:contains(modifyTypes, '11')}">
						<td class="view_table_left">新的经办人：</td>
						<td class="view_table_right">
						<input type="text" name="agent" class="easyui-validatebox" data-options="required:true" />
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					</c:if>
					<c:if test="${fn:contains(modifyTypes, '12')}">
					<td class="view_table_left">新的经办人手机号：</td>
					<td class="view_table_right">
						<input type="text" name="agentMobileNo" 
						class="easyui-validatebox" data-options="required:true" />
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					</c:if>
					<c:if test="${fn:contains(modifyTypes, '13')}">
					<td class="view_table_left">新的经办人身份证号：</td>
					<td class="view_table_right">
						<input type="text" name="agentIdentity" 
						class="easyui-validatebox" data-options="required:true" />
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					</c:if>
				</tr>
				</c:if>
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">补贴对象信息</td>
				</tr>
				<!-- <tr class="datagrid-header-row">
					<td class="view_table_left">办理类型：</td>
					<td class="view_table_right" colspan="5" >					
						自办
					</td>
				</tr> -->
				<tr class="datagrid-row">
					<td class="view_table_left">车主：</td>
					<td class="view_table_right">
						${v.vehicleOwner}
					</td>
					<td class="view_table_left">车主手机号码：</td>
					<td class="view_table_right">
						${v.mobile}
					</td>
					<td class="view_table_left">车主身份证号码：</td>
					<td class="view_table_right">
						${v.vehicleOwnerIdentity}
					</td>
				</tr>
				<!-- <tr>
					<td class="view_table_left">邮政编码：</td>
					<td class="view_table_right">
						850050
					</td>
					<td class="view_table_left">联系地址：</td>
					<td class="view_table_right">
						广东省深圳市福田区深南中路2018号兴华大厦商业十一层11-G号						
					</td>
					<td></td><td></td>	
				</tr> -->
				<tr class="datagrid-row">
					<td class="view_table_left">开户户名：</td>
					<td class="view_table_right">
						${v.bankAccountName}				
					</td>
					<td class="view_table_left">开户银行：</td>
					<td class="view_table_right">
						${v.bankName}
					</td>
					<td class="view_table_left">开户银行账号：</td>
					<td class="view_table_right">
						${v.bankAccountNo}				
					</td>
				</tr>
				
				<c:if test="${fn:contains(modifyTypes, '20') || fn:contains(modifyTypes, '21') || fn:contains(modifyTypes, '22')}">
				<tr id="accountInfo" class="datagrid-row">
					<c:if test="${fn:contains(modifyTypes, '21')}">
					<td class="view_table_left">新的开户银行：</td>
					<td class="view_table_right">
						<input type="text" 
						name="bankName" class="easyui-validatebox"
						data-options="required:true" />
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					</c:if>
					<c:if test="${fn:contains(modifyTypes, '21')}">
					<td class="view_table_left">新的开户银行账号：</td>
					<td class="view_table_right"><input type="text"
						name="bankAccountNo" class="easyui-validatebox"
						data-options="required:true" />
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					</c:if>
				</tr>
				</c:if>
				
				
				
				
				<c:if test="${fn:contains(modifyTypes, '30') || fn:contains(modifyTypes, '31') || fn:contains(modifyTypes, '32') || fn:contains(modifyTypes, '33') || fn:contains(modifyTypes, '34') || fn:contains(modifyTypes, '35') || fn:contains(modifyTypes, '33') || fn:contains(modifyTypes, '33') || fn:contains(modifyTypes, '36') || fn:contains(modifyTypes, '37') || fn:contains(modifyTypes, '38') || fn:contains(modifyTypes, '39')}">
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">证明材料</td>
				</tr>
				<c:if test="${fn:contains(modifyTypes, '31')}">
				<tr class="datagrid-row filesInfo">
					<td class="view_table_left callbackProof" style="width:110px">报废汽车回收证明：</td>
					<td class="view_table_right callbackProof">
						<input class="easyui-filebox" name="callbackProofFile" data-options="required:true,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
					</td>
					<td class="view_table_left vehicleCancelProof" style="width:110px">机动车注销证明：</td>
					<td class="view_table_right vehicleCancelProof">
						<input class="easyui-filebox" name="vehicleOwnerProof" data-options="required:true,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
					</td>
				</tr>
				</c:if>
				<tr class="datagrid-row filesInfo">
					<td class="view_table_left bankCard" style="width:120px">银行卡：</td>
					<td class="view_table_right bankCard">
						<input class="easyui-filebox" name="bankCard" data-options="required:true,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
					</td>
					<td class="view_table_left vehicleOwnerProof" style="width:110px">车主身份证明：</td>
					<td class="view_table_right vehicleOwnerProof">
						<input class="easyui-filebox" name="vehicleOwnerProof" data-options="required:true,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
					</td>
				</tr>
				<tr class="datagrid-row filesInfo">
					<td class="view_table_left agentProxy" style="width:120px">代理委托书：</td>
					<td class="view_table_right agentProxy">
						<input class="easyui-filebox" name="agentProxy" data-options="required:true,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
					</td>
					<td class="view_table_left agentProof" style="width:110px">代理人身份证：</td>
					<td class="view_table_right agentProof">
						<input class="easyui-filebox" name="agentProof" data-options="required:true,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
					</td>
				</tr>
				<tr class="datagrid-row filesInfo">
					<td class="view_table_left noFinanceProvide" style="width:120px">非财政供养单位证明：</td>
					<td class="view_table_right noFinanceProvide">
						<input class="easyui-filebox" name="noFinanceProvide" data-options="required:true,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
					</td>
					<td class="view_table_left openAccPermit" style="width:110px">开户许可证：</td>
					<td class="view_table_right openAccPermit">
						<input class="easyui-filebox" name="openAccPermit" data-options="required:true,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
					</td>
				</tr>
				<tr class="datagrid-row filesInfo">
					<td class="view_table_left vehicleRegFile" style="width:120px">签字确认的受理表：</td>
					<td class="view_table_right vehicleRegFile">
						<input class="easyui-filebox" name="signedApplyFile" data-options="required:true,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
					</td>
				</tr>
				</c:if>
				
				<tr class="datagrid-header-row" style="color:red">
					<td class="view_table_left">修正结果：</td>
					<td class="view_table_right">
						<input type="radio" name="modifyResult" value="1"/>结束修正
						<input type="radio" name="modifyResult" value="2"/>继续修正
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
				</tr>
			</table>
		</div>
		<script type="text/javascript">
		
			$(function() {
				var basePath = $("#basePath").val();
				
				// 默认选择结束修正
				$("input[type='radio'][name='modifyResult'][value='1']").attr("checked", true);
				
			});
				
		
		
		</script>
		
</body>
</html>