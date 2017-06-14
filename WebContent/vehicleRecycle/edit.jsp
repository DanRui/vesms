<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>vehicle-recycle-update</title>
</head>
<body>
	<form action="vehicleRecycle/save.do" method="post" enctype="multipart/form-data">
		<div id="vehicle-recycle-edit" class="datagrid-header">
			<table class="datagrid-table-s datagrid-htable">
				<tr class="datagrid-header-row classify-tr">
						<td colspan="6">车辆基本信息</td>
					</tr>
					<tr class="datagrid-row">
						<td class="view_table_left">号牌号码：</td>
						<td class="view_table_right">${v.vehiclePlateNum }</td>
						<td class="view_table_left">号牌种类：</td>
						<td class="view_table_right">${v.vehiclePlateType }</td>
						<td class="view_table_left">厂牌型号：</td>
						<td class="view_table_right">${v.vehicleModelNo }</td>
					</tr>
					<tr class="datagrid-row">
						<td class="view_table_left">车辆类型：</td>
						<td class="view_table_right">${v.vehicleType }</td>
						<td class="view_table_left">车架号：</td>
						<td class="view_table_right">${v.vehicleIdentifyNo }</td>
						<td class="view_table_left">车辆状态：</td>
						<td class="view_table_right">${v.vehicleStatus }</td>
					</tr>
					<tr class="datagrid-row">
						<td class="view_table_left">使用性质：</td>
						<td class="view_table_right">${v.useOfProperty }</td>
						<td class="view_table_left">燃油类型：</td>
						<td class="view_table_right">${v.iolType }</td>
						<td class="view_table_left">总质量（千克）</td>
						<td class="view_table_right">${v.totalWeight }</td>
					</tr>
					<tr class="datagrid-row">
						<td class="view_table_left">核定载客数（人）：</td>
						<td class="view_table_right">${v.vehicleNumPeople }</td>
						<td class="view_table_left">初次登记日期：</td>
						<td class="view_table_right">${v.registerDate }</td>
						<td class="view_table_left">强制报废期：</td>
						<td class="view_table_right">${v.deadline }</td>
					</tr>
					<tr class="datagrid-row">
						<td class="view_table_left">发动机号：</td>
						<td class="view_table_right">${v.engineNo }</td>
					</tr>
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">车主信息</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">车主：</td>
					<td class="view_table_right">${v.vehicleOwner }</td>
				</tr>
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">报废信息</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:110px">报废回收证明编号：</td>
					<td class="view_table_right">${v.callbackProofNo }</td>
					</td>
					<td class="view_table_left">交售日期：</td>
					<td class="view_table_right">
					<input type="text" name="recycleDate" value=${v.recycleDate } /></td>
				</tr>
				<tr class="datagri-row">
					<td class="view_table_left">录入人：</td>
					<td class="view_table_right">${v.inputUserName }</td>
					<td class="view_table_left">录入时间：</td>
					<td class="view_table_right">${v.inputTime }</td>
				</tr>
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">证明材料</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left"><a href="images/jdchszm.jpg" target="_blank">报废汽车回收证明书</a></td>
					<td class="view_table_right">
						<input class="easyui-filebox" name="JDCHSZM" data-options="buttonText:'请选择'"/>
					</td>
				</tr>				
				<tr class="datagrid-header-row">
					<td class="view_table_left"><a href="images/jdczcdjzs.jpg" target="_blank">机动车注册登记证书</a></td>
					<td class="view_table_right">
						<input class="easyui-filebox" name="JDCZCDJZS" data-options="buttonText:'请选择'"/>
					</td>
				</tr> 
					<tr class="datagrid-header-row">
					<td class="view_table_left"><a href="images/xsz.jpg" target="_blank">行驶证</a></td>
					<td class="view_table_right">
						<input class="easyui-filebox" name="XSZ" data-options="buttonText:'请选择'"/>
					</td>
				</tr> 
			</table>
		</div>
	</form>
	
	<script type="text/javascript">
		$(function() {
			$('#btnVerifyUpdate').bind('click', function(){    
		        alert('验证通过');    
		    });  
		});
	</script>
	
</body>
</html>