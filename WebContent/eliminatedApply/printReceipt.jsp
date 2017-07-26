<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="java.util.Date"%>
<%@page import="com.jst.common.utils.DateUtil"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored = "false" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" >
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" >

<style type="text/css">
	#oDiv2 div
	{
		width: 750px;
		height: 310px;
		/* border:1px solid #c50000; */
	}
</style>

<style media="print">
	.NOPRINT{display:none;}
	.PageNext{page-break-after: always;}
</style>

<title>打印受理回执单</title>
</head>
<body>
	<div id="oDiv2">
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
			<TR>
				<TD style="text-align: center;">受理<br>信息</TD>
				<TD  style="text-align: center;">
					受理确认时间
				</TD>
				<TD colspan="3">
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
	</div>
	</br>
	<div style="height: 75px;width: 62%;text-align: right;font-size: 12pt">
		深圳市机动车排污监督管理办公室<br><br>
			<%=DateUtil.format(new Date(),"yyyy 年 MM 月 dd 日") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
	</div>
	</br>
	</br>
	<div class="NOPRINT" align="center">
		<input type="button" value="重新打印" class="button" onClick="printReceipt();">
	</div>
	
	<script type="text/javascript">
		function printReceipt() {
			var hkey_root,hkey_path,hkey_key;
			hkey_root = "HKEY_CURRENT_USER";
			hkey_path = "\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
			//设置网页打印的页眉页脚为空
			try {
				var RegWsh = new ActiveXObject("WScript.Shell");
				//头
				hkey_key = "header";
				RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
				//脚
				hkey_key = "footer";
				RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
				//上边距
			 	hkey_key = "margin_top"; 
		  	    Wsh.RegWrite(hkey_root+hkey_path+hkey_key,"40");
		  	    //左边距
				hkey_key = "margin_left"; 
		  	    Wsh.RegWrite(hkey_root+hkey_path+hkey_key,"40"); 
			} catch(e) {
				
			}
			window.moveTo(0,0); 
			window.resizeTo(screen.availWidth,screen.availHeight);
			window.print();
		}
		
		printReceipt();
	</script>
</body>
</html>