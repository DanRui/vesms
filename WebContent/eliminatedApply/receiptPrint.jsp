<%@page import="java.util.Date"%>
<%@page import="com.jst.common.utils.DateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%--回执单打印页面 --%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>深圳市老旧车提前淘汰奖励补贴受理回执单</title>
</head>
<body>
	<!-- <div class="NOPRINT" style="text-align: center;font-size: 10pt">
		<input type="button" value="打印回执单" class="button" onClick="printReceipt();">&nbsp;&nbsp;
	</div> -->
	</br>
	<div id="printArea">
		<table cellpadding="1" width= "90%" border= "1" align= "center" cellspacing= "0" style="border-collapse:collapse;">
			<tr>
				<td colspan="5" align="center">老旧车提前淘汰业务受理回执单</td>
			</tr>
			<tr>
				<td style="text-align: center;">受理单编号</td>
				<td style="text-align: center;">${ v.applyNo }</td>
				<td colspan="3"></td>
			</tr>
			<TR>
				<td width="6%" rowspan="6"  style="text-align: center;">报<br>废<br>车<br>辆<br>基<br>本<br>信<br>息</td>
				<TD  style="text-align: center;" width="13%">车主</TD>
				<TD  width="24%">
					${v.vehicleOwner}&nbsp;
				</TD>
				<TD  style="text-align: center;" width="13%">经办人电话</TD>
				<TD  width="24%">
					${v.agentMobileNo}&nbsp;
				</TD>
			</TR>
			<TR>
				<TD  style="text-align: center;">号牌号码</TD>
				<TD >
					${ v.vehiclePlateNum }
				</TD>
				<TD  style="text-align: center;">号牌种类</TD>
				<TD >
					${ v.vehiclePlateTypeName }
				</TD>
			</TR>
			<TR>
				<TD  style="text-align: center;">车辆类型</TD>
				<TD >
					${ v.vehicleTypeName }
				</TD>
			<TD  style="text-align: center;">强制报废期限止</TD>
				<TD >
					<fmt:formatDate value="${v.deadline}" type="date" pattern="yyyy-MM-dd"/>&nbsp;
				</TD>
			</TR>
			<TR>
				<TD width="8%"  style="text-align: center;">车架号</TD>
				<TD width="16%" >
					${v.vehicleIdentifyNo}&nbsp;
				</TD>
				<TD  style="text-align: center;">发动机号</TD>
				<TD >
					${v.engineNo}&nbsp;
				</TD>
			</TR>
			<TR>
				<TD width="8%"  style="text-align: center;">补贴银行</TD>
				<TD width="16%" >
					${v.bankName}&nbsp;
				</TD>
				<TD  style="text-align: center;">补贴账号</TD>
				<TD >
					${v.bankAccountNo}
				</TD>
			</TR>
			<tr>
				</TD>
				<TD  style="text-align: center;">补贴金额</TD>
				<TD  colspan="3">
					人民币：<fmt:formatNumber value="${v.subsidiesMoney}" type="currency"/>
						&nbsp;&nbsp;&nbsp;&nbsp;元
					 &nbsp;&nbsp;
				</TD>
			</tr>
			<!-- 
			<TR>
				<td  style="text-align: center; line-height: 17pt">申请 <br>事项</td>
				<TD  style="text-align: left;" colspan="4">老旧车提前淘汰奖励补贴申请</TD>
			</TR>
			<TR>
				<TD style="text-align: center;">提<br>交<br>资<br>料<br>清<br>单</TD>
				<TD  style="text-align: left;" colspan="4">
					<input type="checkbox" name="check">1.报废汽车回收证明（原件一份）<br>
					<input type="checkbox" name="check">2.机动车注销证明（原件一份）<br>
					<input type="checkbox" name="check">3.银行卡（复印件一份）<br>
					<input type="checkbox" name="check">4.车主或公司有效证件（验原件）<br>
					<input type="checkbox" name="check">5.经办人有效证件（复印件一份）<br>
					<input type="checkbox" name="check">6.非财政供养单位证明(复印件一份，企业需要）<br>
					<input type="checkbox" name="check">7.组织机构代码证(复印件一份，企业需要）<br>
					<input type="checkbox" name="check">8.代办委托书（复印件一份）<br>
					<input type="checkbox" name="check">9.机动车登记证书（复印件一份）<br>
					<input type="checkbox" name="check">10.开户许可证（复印件一份，企业需要）<br>
					<div class="Noprint">
					<input type="checkbox" id="selectAllId">全选
					</div>
				</TD>
			</TR> -->	
			<TR>
				<TD style="text-align: center;">受理<br>信息</TD>
				<TD  style="text-align: center;">
					受理确认时间
				</TD>
				<TD  colspan="3">
					<fmt:formatDate value="${v.applyConfirmTime}" type="date" pattern="yyyy-MM-dd"/>
				</TD>
			</TR>
			<!-- <tr class="NOPRINT">
				<td colspan=6 class="input_buttom" style="text-align: center">
					<input type="button" value="打印" class="button" onClick="window.print()">&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="关闭" class="button" onClick="window.close()">&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr> -->
		</TABLE>
		<div style="width: 95%;text-align: right;font-size: 12pt">
			深圳市机动车排污监督管理办公室<br><br>
				<%=DateUtil.format(new Date(),"yyyy 年 MM 月 dd 日") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
	</div>
	<br/>
	<div id="receiptPreview" align="center">
		<a id="btnPrintReceipt" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-print'">打印回执单</a>
		<!-- <a id="btnContinueApply" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-nextstep'">继续受理下一单</a> -->
	</div>
	<script type="text/javascript">
		$(function() {
			var basePath = "<%=basePath%>";
			
			// 隐藏关闭按钮
			$("#common-dialog-close").hide();
			
			// 重新调整页面窗口大小和位置居中
			$("#common-dialog").dialog("resize", {
				width : 1030,
				height : 400 
			}).dialog("center");
			
			var stage = '${stage}';
			if (stage != "over") {
				Messager.alert({
					type : "error",
					title : "&nbsp;",
					content : "受理未完成！"
				});
			}
			
			var id = ${v.id};
			
			$("#btnPrintReceipt").click(function() {
				onPrint();
				//printReceipt();
			});
			
			$("#btnContinueApply").click(function() {
				// 继续受理下一单，后台查询是否有预约单号
				var url = basePath+"/eliminatedApply/continueApply.do?&id="+id;
				$("#common-dialog").dialog("close");
				
				openDialog({
					type : "add",
					title : "申报受理录入",
					width : 1132,
					height : 800,
					param: {reset:false,
					    	save:false,
					    	close:false,
					    	stage:"add"
					    	},
					maximizable : true,
					href : url
				});
				
				//$("#common-dialog").dialog("refresh", url);
				//$("#common-dialog").dialog({title:""}).dialog("restore").dialog("center");
			});
		});
		
		// 打印受理回执单
		function onPrint() {
			var url = "<%=basePath%>" + "/eliminatedApply/printReceipt.do?id="+"${v.id}";
			window.open(url, '_blank');
		}
		
		function printReceipt() {
			var html = $("#printArea").html();
			var oPop = window.open('','打印');  
	        var str = '<!DOCTYPE html>';  
	            str += '<html>';  
	            str += '<head>'; 
	            str += '<meta charset="utf-8">';  
	            str += '<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">';  
	            str += '<style>';
	            str += '#oDiv2 div{width: 100px;height: 100px;border:1px solid #c50000;}';  
	            str += '</style>';  
	            str += '</head>';  
	            str += '<body>';  
	            str += "<div id='oDiv2'>"+ html +"</div>";  
	            str += '</body>';  
	            str += '</html>';  
	  		
	        oPop.document.write(str);  
	        oPop.print();  
	        oPop.close();
			
		}
		
		function setPrintPage() {
			var HKEY_Root, HKEY_Path, HKEY_Key;
			HKEY_Root = "HKEY_CURRENT_USER";
			HKEY_Path = "\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
			var Wsh = new ActiveXObject("WScript.Shell");
	      	HKEY_Key = "header";
		    Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");
		    HKEY_Key = "footer";
		    Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");
		    HKEY_Key = "margin_left";
		    Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0"); //键值设定--左边边界

		    HKEY_Key = "margin_top";
		    Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0"); //键值设定--上边边界

		    HKEY_Key = "margin_right"; 
		    Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0"); //键值设定--右边边界

		    HKEY_Key = "margin_bottom"; 
		    Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0"); //键值设定--下边边界
		}
	
	</script>
	
</body>
</html>