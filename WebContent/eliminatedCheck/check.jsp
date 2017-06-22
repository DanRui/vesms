<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored = "false" %>
<%
	String basePath = request.getContextPath();
	String checkIds = request.getParameter("ids");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
 
	<!-- <div id="eliminated-check" class="easyui-accordion" data-options="fit:false" animate="false"> -->
		<!-- <div title="车辆基本信息" class="datagrid-body"> 
			<div class="datagrid-header" style="overflow:hidden" > 			
				<table class="datagrid-table-s datagrid-htable">

				<tr>
					<td class="view_table_left">车主类型：</td>
					<td class="view_table_right" colspan="5">					
					 	自然人 					
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">号牌号码：</td>
					<td class="view_table_right">粤B6U4D7</td>
					<td class="view_table_left">号牌种类：</td>
					<td class="view_table_right">蓝牌</td>
					<td class="view_table_left">厂牌型号：</td>
					<td class="view_table_right">新凯HXK6490C</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">车辆类型：</td>
					<td class="view_table_right">小型载客车</td>
					<td class="view_table_left">车架号：</td>
					<td class="view_table_right">LTA1278U35X132040</td>
					<td class="view_table_left">发动机号：</td>
					<td class="view_table_right">507506</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">使用性质：</td>
					<td class="view_table_right">非营运</td>
					<td class="view_table_left">燃油类型：</td>
					<td class="view_table_right">柴油</td>
					<td class="view_table_left">总质量（千克）</td>
					<td class="view_table_right">3000</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">核定载客数（人）：</td>
					<td class="view_table_right">4</td>
					<td class="view_table_left">初次登记日期：</td>
					<td class="view_table_right">2016-07-01</td>
					<td class="view_table_left">强制报废期：</td>
					<td class="view_table_right">2020-07-01</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">注销日期：</td>
					<td class="view_table_right">2017-07-10</td>
					<td class="view_table_left">车辆状态：</td>
					<td class="view_table_right">已注销</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">报废回收证明编号：</td>
					<td class="view_table_right">HS-730000-1323-20150605-8</td>
					<td class="view_table_left">交售日期：</td>
					<td class="view_table_right">2017-07-01</td>
				</tr>
				<tr class="datagri-row">
					<td class="view_table_left">录入时间：</td>
					<td class="view_table_right">2017-07-01 16:36</td>
					<td class="view_table_left">报废截止日期：</td>
					<td class="view_table_right">2018-07-01 16:36</td>
				</tr>
			</table>
		</div>
		</div>
		<div title="补贴对象信息" class="datagrid-body">
			<div class="datagrid-header" style="overflow:hidden">
				<table class="datagrid-table-s datagrid-htable">											
				
	 						<tr class="datagrid-row">
							<td class="view_table_left">车主：</td>
							<td class="view_table_right">
								叶向东
							</td>
							<td class="view_table_left">车主手机号码：</td>
							<td class="view_table_right">
								15811807108
							</td>
							<td class="view_table_left">车主身份证号码：</td>
							<td class="view_table_right">
								430123456789110119
							</td>
						</tr>
						<tr>
							<td class="view_table_left">邮政编码：</td>
							<td class="view_table_right">
								850050
							</td>
							<td class="view_table_left">联系地址：</td>
							<td class="view_table_right">
								广东省深圳市福田区深南中路2018号兴华大厦商业十一层11-G号						
							</td>
							<td class="view_table_left">办理类型：</td>
							<td class="view_table_right">
								自办			
							</td>
						</tr>
							<tr>
							<td class="view_table_left">开户户名：</td>
							<td class="view_table_right">
								叶向东				
							</td>
							<td class="view_table_left">开户银行：</td>
							<td class="view_table_right">
								招商银行
							</td>
							<td class="view_table_left">开户银行账号：</td>
							<td class="view_table_right">
								433121345646123					
							</td>
							
						</tr>
						<tr>
							<td class="view_table_left">经办人：</td>
							<td class="view_table_right">
											
							</td>
							<td class="view_table_left">经办人手机号：</td>
							<td class="view_table_right">
									
							</td>
							<td class="view_table_left">经办人身份证号：</td>
							<td class="view_table_right">
														
							</td>
						</tr>
						<tr class="datagrid-row">
							<td class="view_table_left">机构类型：</td>
							<td class="view_table_right">
								
							</td>
							<td class="view_table_left">组织机构代码：</td>
							<td class="view_table_right">
							</td>							
						</tr>
 					</table>
 				</div>
 			</div>
		<div title="补贴信息" class="datagrid-body">
			<div class="datagrid-header" style="overflow:hidden">
				<table class="datagrid-table-s datagrid-htable">
 					<tr class="datagri-row">
					<td class="view_table_left">补贴金额：</td>
					<td class="view_table_right">6000元</td>
					<td class="view_table_left">补贴标准：</td>
					<td class="view_table_right">1升及以下排量轿车、专项作业车 --6000元</td>
				</tr>
 					
				</table>
			</div>
		</div>
		<div title="证明材料" class="datagrid-body">
			<div class="datagrid-header" style="overflow:hidden">
				<table class="datagrid-table-s datagrid-htable">
				
					<tr class="datagrid-row">
					
						<td align="center"><a href="images/bfzm.jpg" target="_blank">报废汽车回收证明书</a></td>
					
						<td align="center"><a href="images/zxzm.jpg" target="_blank">机动车注销证明</a></td>
						
					</tr>
					<tr class="datagrid-row">
					
						<td align="center"><a href="images/jdczcdjzs.jpg" target="_blank">机动车注册登记证书(1)</a></td>
					
						<td align="center"><a href="images/zxzm.jpg" target="_blank">机动车注册登记证书(2)</a></td>
						
					</tr>
					<tr class="datagrid-row">										
					
						<td align="center"><a href="images/zrryhk.jpg" target="_blank">银行卡</a></td>
					
						<td align="center"><a href="images/zrrsfz.jpg" target="_blank">车主身份证明</a></td>
							
					</tr>
					<tr class="datagrid-row">
						
						<td align="center"><a href="images/fzrryhk.jpg" target="_blank">开户许可证（非自然人）</a></td>
						
						<td align="center"><a href="images/fzrrsfz.jpg" target="_blank">非自然人身份证（工商执照、组织机构代码证）</a></td>						
					
					</tr>

					<tr class="datagrid-row">
					
						<td align="center"><a href="images/dlwts.jpg" target="_blank">代理委托书</a></td>
					
						<td align="center"><a href="images/dlrsfz.jpg" target="_blank">代理人身份证</a></td>
						
					</tr>
					<tr class="datagrid-row">
					
						<td align="center"><a href="images/dlwts.jpg" target="_blank">非财政供养单位证明</a></td>
					
						<td align="center" ><a href="images/dlrsfz.jpg" target="_blank">补贴受理确认表</a></td>
						
					</tr>
				</table>
			</div>	
		</div> -->
		<div title="审批操作" class="datagrid-body" data-options="selected:true">
			<div class="datagrid-header" style="overflow:hidden">
				<table class="datagrid-table-s datagrid-htable">
					<tr class="datagrid-row">
						<td class="view_table_left">审批操作：</td>
						<td class="view_table_right">
							<select id="checkType" name="checkType" class="easyui-combobox" data-options="editable:false,required:true,width:100,panelHeight:'auto'">   
    							<option>请选择</option>   
    							<option value="1">审核通过</option>   
    							<option value="2">异常退回</option>
    							<!-- <option value="-1">终结</option> -->   
							</select>
							<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
						</td>
						<td class="view_table_left isFault">退回类型：</td>
						<td class="view_table_right isFault">
							<input id="faultType" class="easyui-combobox" name="faultType" data-options="editable:false,valueField:'id',textField:'text',url:'data/faultType.json',panelHeight:'auto'"/>
							<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>  
						</td>
					</tr>
					<tr class="datagrid-row">
						<td class="view_table_left">具体原因：</td>
						<td class="view_table_right" colspan="3">
							<textarea id="checkOpinion" name="checkOpinion" rows="3" class="easyui-validatebox" style="width: 450px"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
<!-- 		<div title="业务流水记录" class="datagrid-body">
			<div class="datagrid-header" style="overflow:hidden">
				<table class="datagrid-table-s datagrid-htable">
 					<tr class="datagrid-row">
						<th>受理单号</th>
						<th>操作岗位</th>
						<th>操作动作</th>
						<th>发生时间</th>
						<th>经办人</th>
						<th>操作结果</th>
						<th>详情说明</th>
					</tr>
 					<tr class="datagrid-row">
						<td align="center">SL201704211001</td>
						<td align="center">窗口受理岗</td>
						<td align="center">受理录入</td>
						<td align="center">2017-07-10 15:32:45</td>
						<td align="center">曾笑梅</td>
						<td align="center">受理成功</td>
						<td align="center"></td>
					</tr>
				</table>
			</div>
		</div> -->
	<!-- </div> -->
	
	<script type="text/javascript">
		$(function() {
			var basePath = "<%=basePath%>";
			var checkIds = "<%=checkIds%>";
			var currentPost = "${currentPost}";
			// 隐藏退回类型 
			$("td[class*='isFault']").each(function() {
					$(this).hide();
			});
			
			// 如果是退回操作，则必需填写退回类型和退回原因
			$("#checkType").combobox({
				onChange : function() {
					var checkType = $(this).combobox("getValue");
					$("td[class*='isFault']").each(function() {
						if ("2" == checkType) {
							$(this).show();
							$("#faultType").validatebox({required:true});
							$("#checkOpinion").validatebox({required:true});
						} else {
							$(this).hide();
							$("#faultType").validatebox({required:false});
							$("#checkOpinion").validatebox({required:false});
						}
					});
				}
			});
			
			// 保存提交审核操作，后台更新受理表、动作流水日志表
			$("#common-dialog-check-submit").click(function() {
				var checkType = $("#checkType").combobox("getValue");
				var faultType = $("#faultType").combobox("getValue");
				var checkOpinion = $("#checkOpinion").val();
				
				if (checkType == "" || (checkType != "1" && checkType != "2")) {
					alert("请选择审批操作！");
					return false;
				}
				
				if (checkType == "2") {
					if (faultType == "") {
						alert("请选择退回类型！");
						return false;
					}
					
					if (checkOpinion == "") {
						alert("请填写具体原因！");
						return false;
					}
				}
				
				$.ajax({
	                //cache: true,
	                type : "POST",
	                url : basePath+"/eliminatedCheck/check.do?ids="+checkIds,
	                data : {"checkType":checkType,"faultType":faultType,"checkOpinion":checkOpinion, "currentPost":currentPost},//审核操作，传入后台
	                async : true,
	                success : function(data) {
	                	
	                	if(Object.prototype.toString.call(data) === "[object String]") {
							data = eval("(" + data + ")");
						}
						
						if(data.success) {
							
							Messager.alert({
								type : "info",
								title:"&nbsp;",
								content:data.message.msg
							});
							
							// 审批成功
							$("#common-dialog").dialog("close");
							
							//var listUrl = basePath+"/eliminatedCheck/list.do";
							$("#eliminated-check-list #eliminated-check-grid").datagrid("load");
							$("#back-check-list #back-check-grid").datagrid("load");
	                	} else {
	                		Messager.alert({
								type : "error",
								title:"&nbsp;",
								content:data.message
							});
	                	}
	                }
				});
			});
		});
	</script>
	
</body>
</html>