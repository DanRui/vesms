<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored = "false" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8," >
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" >

<style type="text/css">
	#oDiv2 div
	{
		width: 950px;
		height: 630px;
		/* border:1px solid #c50000; */
	}
</style>
<style media="print">
	.NOPRINT{display:none;}
</style>

<title>打印受理申请表</title>
</head>
<body>
	<div id="oDiv2">
	<div id="printArea">
		<table cellpadding="1" width= "90%" border= "1" align= "center" cellspacing= "0" style="border-collapse:collapse;">
			<!-- <tr>
				<td colspan="8" class="tdp1" style="text-align: center;font-weight: bold;">深圳市老旧车提前淘汰奖励补贴申请表</td>
			</tr> -->
			<tr>
				<td colspan="7" style="text-align: center;font-weight: bold;">老旧车提前淘汰奖励补贴申请表</td>
			</tr>
			<tr>
				<td style="text-align: center;">系统ID号</td>
				<td style="text-align: center;">${ v.id }</td>
				<td></td>
				<td style="text-align: center;">受理单号</td>
				<td style="text-align: left: ;">${ v.applyNo }</td>
				<td style="text-align: center;">档案盒号</td>
				<td style="text-align: left;">${v.archiveBoxNo}盒${v.archivedInnerNo}号</td>
			</tr>
			<TR>
				<td width="5%" rowspan="5" class="tdp1" style="text-align: center;">报废<br>车辆<br>信息</td>
				<TD width="8%" style="text-align: center;">号牌号码</TD>
				<TD width="16%">
					${ v.vehiclePlateNum }
				</TD>					
				<TD style="text-align: center;">号牌类别</TD>
				<TD>
					${ v.vehiclePlateTypeName }
				</TD>
				<TD style="text-align: center;">车辆类型</TD>
				<TD>
					${ v.vehicleTypeName }
				</TD>
			</TR>
			<TR>
				<TD style="text-align: center;">车架号</TD>
				<TD>
					${v.vehicleIdentifyNo}
				</TD>
				<TD width="8%" style="text-align: center;">发动机号</TD>
				<TD width="16%">
					${v.engineNo}
				</TD>
				<TD width="8%" style="text-align: center;">燃油类型</TD>
				<TD width="16%">
					${v.iolTypeName}
				</TD>
			</TR>
			<TR>
				<TD style="text-align: center;">初次登记日期</TD>
				<TD>
					<fmt:formatDate value="${v.registerDate}" type="date" pattern="yyyy-MM-dd"/>
				</TD>
				<TD style="text-align: center;">排放标准</TD>
				<TD>
					${v.emissionStandard}
				</TD>
				<TD style="text-align: center;">强制报废期止</TD>
				<TD>
					<fmt:formatDate value="${v.deadline}" type="date" pattern="yyyy-MM-dd"/>
				</TD>
			</TR>
			<TR>
				<TD style="text-align: center;">交售日期</TD>
				<TD>
					<fmt:formatDate value="${v.recycleDate}" type="date" pattern="yyyy-MM-dd"/>
				</TD>
				<TD style="text-align: center;">提前报废时长(天)</TD>
				<TD>
					${v.advancedScrapDays}
				</TD>
				<TD style="text-align: center;">是否财政供养</TD>
				<TD>
					<c:if test="${v.isFinancialSupport eq '1'}">
						个人
					</c:if>
					<c:if test="${v.isFinancialSupport eq '2'}">
						车主自证非财政供养
					</c:if>
				</TD>
			</TR>
			<TR>
				<TD style="text-align: center;">注销日期</TD>
				<TD>
					<fmt:formatDate value="${v.destroyDate}" type="date" pattern="yyyy-MM-dd"/>
				</TD>
				<TD style="text-align: center;">注销类别</TD>
				<TD>
					${v.cancelReason}
				</TD>
				<TD style="text-align: center;">回收证明号</TD>
				<TD>
					${v.callbackProofNo}
				</TD>
			</TR>
			<TR>
				<td rowspan="2" style="text-align: center;">车主<br>信息</td>
				<TD style="text-align: center;">车辆所有人</TD>
				<TD colspan="5">
					${v.vehicleOwner}
				</TD>
			</TR>
			<TR>
				<TD style="text-align: center;">证件号码</TD>
				<TD colspan="2">
					${v.vehicleOwnerIdentity}
				</TD>
				<TD style="text-align: center;">联系电话</TD>
				<TD colspan="2">
					${v.mobile}
				</TD>
			</TR>
			<TR>
				<td rowspan="3" style="text-align: center;">补贴<br>信息</td>
				<TD style="text-align: center;">账户名称</TD>
				<TD colspan="2">
					${v.bankAccountName}
				</TD>
				<TD style="text-align: center;">开户银行</TD>
				<TD colspan="2">
					${v.bankName}
				</TD>
			</TR>
			<TR>
				<TD style="text-align: center;">银行账号</TD>
				<TD colspan="5">
					${v.bankAccountNo}
				</TD>
			</TR>
			<TR>
				<TD style="text-align: center;">补贴标准</TD>
				<TD colspan="2">
					${v.subsidiesStandard}
				</TD>
				<TD style="text-align: center;">补贴金额</TD>
				<TD colspan="2">
					人民币：<fmt:formatNumber value="${v.subsidiesMoney}" type="currency"/>
					   &nbsp;&nbsp;&nbsp;&nbsp;元
					 &nbsp;&nbsp;
				</TD>
			</TR>
			<TR>
				<td rowspan="2" style="text-align: center;">申请人<br>信息</td>
				<TD style="text-align: center;">申请人</TD>
				<TD colspan="5">
					${v.agent}
				</TD>
			</TR>
			<TR>
				<TD style="text-align: center;">证件号码</TD>
				<TD colspan="2">
					${v.agentIdentity}
				</TD>
				<TD style="text-align: center;">联系电话</TD>
				<TD colspan="2">
					${v.agentMobileNo}
				</TD>
			</TR>
			<TR>
				<TD colspan="7" style="text-align:left;font-weight: bold">
					重要提醒：该车已核定为<font color="red">${ v.subsidiesStandard }</font>,按相应标准给予补贴，拨付补贴款时将以该车在车管部门注册登记的车主名称
					作为银行账户名称向车主所提供的银行账号拨付补贴款。
				</TD>
			</TR>
			<TR>
				<TD colspan="7" style="text-align:left;font-weight: bold;">
					经办人申明：本人已核对以上信息且无异议；保证所提供的收款账户为车辆所有人拥有，银行账户名称与车辆注册登记的车主名称完全相同，且处正常状态。
				</TD>
			</TR>
			<TR style="height: 90px">
				<TD colspan="7" style="text-align: center;font-weight: bold">
					申请人核对签名：______________ 日期：_____年_____月_____日
				</TD>
			</TR>
		</TABLE>
	</div>
	</div>
	</br>
	</br>
	<div class="NOPRINT" align="center">
		<input type="button" value="重新打印" class="button" onClick="printApply();">
	</div>
	
	<script type="text/javascript">
		function printApply() {
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
		
		printApply();
	</script>
</body>
</html>