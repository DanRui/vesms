<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="" method="post" enctype="multipart/form-data">
		<div id="eliminated-apply-update" class="datagrid-header">
			<table class="datagrid-table-s datagrid-htable">
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">车辆基本信息</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">车主类型：</td>
					<td class="view_table_right" colspan="5">
					<input type="text" disabled="disabled" name="isPersonal" id="isPersonal" value="自然人"/>
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">号牌号码：</td>
					<td class="view_table_right">
						<input type="text" name="vehiclePlateNum" value="粤B6U4D7" disabled="disabled" />
					</td>
					<td class="view_table_left">号牌种类：</td>
					<td class="view_table_right">
						<input type="text" name="vehiclePlateType" value="黄牌" disabled="disabled"/>			
					</td>
					<td class="view_table_left">厂牌型号：</td>
					<td class="view_table_right"><input type="text"
						name="vehicleModelNo" value="新凯HXK6490C" disabled="disabled"/></td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">车辆类型：</td>
					<td class="view_table_right"><input type="text"
						name="vehicleType" value="小型载客车" disabled="disabled" /></td>
					<td class="view_table_left">车架号：</td>
					<td class="view_table_right"><input type="text"
						name="vehicleIdentifyNo" value="LTA1278U35X132040"
						disabled="disabled" /></td>
					<td class="view_table_left">发动机号：</td>
					<td class="view_table_right"><input type="text"
						name="engineNo" value="507506" disabled="disabled"/></td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">使用性质：</td>
					<td class="view_table_right"><input type="text"
						name="useOfProperty" value="非营运" disabled="disabled" /></td>
					<td class="view_table_left">燃油类型：</td>
					<td class="view_table_right"><input type="text"
						name="iolLType" value="柴油" disabled="disabled" /></td>
					<td class="view_table_left">总质量（千克）</td>
					<td class="view_table_right"><input type="text"
						name="totalWeight" value="3000" disabled="disabled"/></td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">核定载客数（人）：</td>
					<td class="view_table_right"><input type="text"
						name="vehicleNumPeople" value="5" disabled="disabled"/></td>
					<td class="view_table_left">初次登记日期：</td>
					<td class="view_table_right">
						<input	type="text" name="registerDate" value="2016-07-01" disabled="disabled"/></td>
					<td class="view_table_left">强制报废期：</td>
					<td class="view_table_right"><input type="text"
						name="destroyDate" value="2020-07-01"
						disabled="disabled" /></td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">车主：</td>
					<td class="view_table_right">
						<textarea name="vehicleOwner" class="easyui-validatebox" disabled="disabled">叶向东</textarea>
						<!-- <span style="color:red;text-align:center">&nbsp;*&nbsp;</span> -->
					</td>
					<td class="view_table_left">车主联系号码：</td>
					<td class="view_table_right">
						<input type="text" name="mobile" class="easyui-validatebox" disabled="disabled" value="15811807108"/>
						<!-- <span style="color:red;text-align:center">&nbsp;*&nbsp;</span> -->
					</td>
					<td class="view_table_left">车主身份证号码：</td>
					<td class="view_table_right">
						<input type="text" name="vehicleOwnerIdentity" class="easyui-validatebox" value="430123456789110119" disabled="disabled"/>
						<!-- <span style="color:red;text-align:center">&nbsp;*&nbsp;</span> -->
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">联系地址：</td>
					<td class="view_table_right">
						<textarea name="address" class="easyui-validatebox" disabled="disabled">广东省深圳市福田区深南中路2018号兴华大厦商业十一层11-G号 </textarea>
						<!-- <span style="color:red;text-align:center">&nbsp;*&nbsp;</span> -->
					</td>
					<td class="view_table_left">邮政编码：</td>
					<td class="view_table_right">
						<input type="text" name="postalCode" value="425000" class="easyui-validatebox" disabled="disabled"/>
					</td>
				</tr>
				<tr class="datagrid-row applyType">
					<td class="view_table_left">机构类型：</td>
					<td class="view_table_right"><input type="text"
						name="orgType" class="easyui-validatebox"
						data-options="required:true" value="" disabled="disabled"/></td>
					<td class="view_table_left">组织机构代码：</td>
					<td class="view_table_right"><input type="text" name="orgCode" disabled="disabled" />
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">注销日期：</td>
					<td class="view_table_right">
						<input type="text" name="vehicleStatus" value="2017-07-10" disabled="disabled"/>
					</td>
					<td class="view_table_left">车辆状态：</td>
					<td class="view_table_right"><input type="text"
						name="vehicleStatus" class="easyui-validatebox"
						data-options="required:true" value="正常" disabled="disabled" /></td>
				</tr>
				
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">报废信息</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:110px">报废回收证明编号：</td>
					<td class="view_table_right"><input type="text"
						name="callbackProofNo" value="HS-730000-1323-20150605-8"
						class="easyui-validatebox" data-options="required:true" disabled="disabled"/></td>
					<td class="view_table_left">交售日期：</td>
					<td class="view_table_right">
						<input type="text" name="recycleDate" value="2017-07-01" disabled="disabled"/>
					</td>
				</tr>
				
				
				
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">补贴对象信息</td>
				</tr>
				<tr class="datagrid-header-row">
					<td class="view_table_left">办理类型：</td>
					<td class="view_table_right" colspan="5" >					
						<input type="text" id="isProxy" name="isProxy" value="自办" disabled="disabled" />
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">经办人：</td>
					<td class="view_table_right">
						<input type="text" name="agent" class="easyui-validatebox" value="叶向东" data-options="required:true"/>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					<td class="view_table_left">经办人手机号：</td>
					<td class="view_table_right">
						<input type="text" name="agentMobileNo" class="easyui-validatebox" value="13688789542" data-options="required:true"/>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					<td class="view_table_left">经办人身份证号：</td>
					<td class="view_table_right" >
						<input type="text" name="agentIdentity" class="easyui-validatebox" value="42312319950412125X" data-options="required:true"/>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">开户户名：</td>
					<td class="view_table_right"><input type="text"
						name="bankAccountName" class="easyui-validatebox" value="叶向东"
						data-options="required:true" />
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					<td class="view_table_left">开户银行：</td>
					<td class="view_table_right"><input type="text" value="招商银行"
						name="bankName" class="easyui-validatebox"
						data-options="required:true" />
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					<td class="view_table_left">开户银行账号：</td>
					<td class="view_table_right"><input type="text" value="433121345646123"
						name="bankAccountNo" class="easyui-validatebox"
						data-options="required:true" />
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>

				</tr>
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">证明材料</td>
				</tr>
				<tr>
					<td class="view_table_left" style="width:120px">
						<a href="images/bfzm.jpg" target="_blank" style="text-decoration:none;">报废汽车回收证明书</a>
					</td>
					<td class="view_table_right">
						<table id="#callbackFileTable">
							<tr>
								<td>
									<form id="uploadFile" enctype="multipart/form-data" method="post">
	            						<input id="callbackProofFile" class="easyui-filebox" name="callbackProofFile" data-options="prompt:'选择文件',required:true,buttonText:'请选择'">
	            						<font color="red">&nbsp;*&nbsp;</font>
	            						<a id="btn_upload_file1" href="#" class="easyui-linkbutton" iconCls="icon-ok">上传</a>
	       				 			</form>
	       				 		</td>
	       				 	</tr>
						</table>
					</td>
					<td class="view_table_left" style="width:110px">
						<a href="images/zxzm.jpg" target="_blank" style="text-decoration:none;">机动车注销证明</a>
					</td>
					<td class="view_table_right">
						<input class="easyui-filebox" name="vehicleCancelProof" data-options="required:true,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:120px">
						<a href="images/jdczcdjzs.jpg" target="_blank" style="text-decoration:none;">机动车注册登记证书</a>
					</td>
					<td class="view_table_right">
						<input class="easyui-filebox" name="vehicleRegFile" data-options="required:true,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
					</td>
					<td class="view_table_left" style="width:110px">
						<a href="images/czsfz.jpg" target="_blank" style="text-decoration:none;">车主身份证明</a>
					</td>
					<td class="view_table_right">
						<input class="easyui-filebox" name="vehicleOwnerProof" data-options="required:true,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:120px">
						<a href="images/dlwts.jpg" target="_blank" style="text-decoration:none;">银行卡</a>
					</td>
					<td class="view_table_right">
						<input class="easyui-filebox" name="bankCardFile" data-options="required:true,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
					</td>
				</tr>
				<tr class="datagrid-row proxyType">
					<td class="view_table_left" style="width:120px">
						<a href="images/dlwts.jpg" target="_blank" style="text-decoration:none;">代理委托书</a>
					</td>
					<td class="view_table_right">
						<input class="easyui-filebox" name="agentProxy" data-options="required:true,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
					</td>
					<td class="view_table_left" style="width:110px">
						<a href="images/dlrsfz.jpg" target="_blank" style="text-decoration:none;">代理人身份证</a>
					</td>
					<td class="view_table_right">
						<input class="easyui-filebox" name="agentProof" data-options="required:true,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
					</td>
				</tr>
				<tr class="datagrid-row applyType">
					<td class="view_table_left" style="width:120px">
						<a href="images/dlwts.jpg" target="_blank" style="text-decoration:none;">开户许可证</a>
					</td>
					<td class="view_table_right">
						<input class="easyui-filebox" name="noFinanceProvide" data-options="required:true,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
					</td>
					<td class="view_table_left" style="width:120px">
						<a href="images/dlwts.jpg" target="_blank" style="text-decoration:none;">非财政供养单位证明</a>
					</td>
					<td class="view_table_right">
						<input class="easyui-filebox" name="noFinanceProvide" data-options="required:true,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
					</td>
				</tr>
			</table>
		</div>
		
		<script type="text/javascript">
			$(function() {
				//隐藏组织结构类型和文件上传区域
				$("tr[class*='applyType']").each(function() {
					var isPersonal = $("#isPersonal").val();
					if (isPersonal == "自然人") {
						$(this).hide();
					}
			});
				
				$("tr[class*='proxyType']").each(function() {
					var isProxy = $("#isProxy").val();
					if (isProxy == "自办") {
						$(this).hide();
					}
			});
				
			$("#btn_upload_file1").click(function() {
				var imgHtml = "";
				var imgSrc = "images/bfzm.jpg";
				var imgName = "报废回收证明.jpg";
				imgHtml += '<tr><td><a href=\"'+imgSrc+'\" target=\"_blank\" style=\"text-decoration:none;\">'
						+ imgName+"</a>"+'<a id=\"btn_delete_file1\" href=\"#\" class=\"easyui-linkbutton\" iconCls=\"icon-cancel\">删除</a>'
						+ "</td></tr>";
				$(this).parent().parent().parent().append(imgHtml);
				$("#btn_delete_file1").bind("click", function() {
					$(this).parent().parent().remove();
				});
				$.parser.parse($(this).parent().parent().parent());
				alert("文件上传成功");
			});
			
			$("#btn_delete_file1").click(function() {
				$("#btn_delete_file1").parent().parent().remove();
			});
				
		});	
		</script>
		
</body>
</html>