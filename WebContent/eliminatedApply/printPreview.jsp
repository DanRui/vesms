<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored = "false" %>
<%
String basePath = request.getContextPath();
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>深圳市老旧车提前淘汰奖励补贴申请表</title>
</head>
<body>
	<input type="hidden" name="stage"/>
	<br/>
	<!-- <div class="NOPRINT" style="text-align: center;font-size: 10pt">
		<input type="button" value="打印申请表" class="button" onClick="onPrint('printArea');">&nbsp;&nbsp;
	</div> -->
	<div id="printArea">
		<table class="tabp" cellpadding="1" width= "90%" border= "1 solid" align= "center" cellspacing= "0">
			<!-- <tr>
				<td colspan="8" class="tdp1" style="text-align: center;font-weight: bold;">深圳市老旧车提前淘汰奖励补贴申请表</td>
			</tr> -->
			<tr>
				<td colspan="7" style="text-align: center;font-weight: bold;">老旧车提前淘汰奖励补贴申请表</td>
			</tr>
			<tr class="datagrid-row">
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
				<TD width="8%" class=tdp style="text-align: center;">号牌号码</TD>
				<TD width="16%" class=tdp>
					${ v.vehiclePlateNum }
				</TD>					
				<TD class="tdp" style="text-align: center;">号牌类别</TD>
				<TD class=tdp>
					${ v.vehiclePlateTypeName }
				</TD>
				<TD class=tdp style="text-align: center;">车辆类型</TD>
				<TD class=tdp>
					${ v.vehicleTypeName }
				</TD>
			</TR>
			<TR>
				<TD class=tdp style="text-align: center;">车架号</TD>
				<TD class=tdp>
					${v.vehicleIdentifyNo}
				</TD>
				<TD width="8%" class=tdp style="text-align: center;">发动机号</TD>
				<TD width="16%" class=tdp>
					${v.engineNo}
				</TD>
				<TD width="8%" class=tdp style="text-align: center;">燃油类型</TD>
				<TD width="16%" class=tdp>
					${v.iolTypeName}
				</TD>
			</TR>
			<TR>
				<TD class=tdp style="text-align: center;">初次登记日期</TD>
				<TD class=tdp>
					<fmt:formatDate value="${v.registerDate}" type="date" pattern="yyyy-MM-dd"/>
				</TD>
				<TD class=tdp style="text-align: center;">排放标准</TD>
				<TD class=tdp>
					${v.emissionStandard}
				</TD>
				<TD class=tdp style="text-align: center;">强制报废期止</TD>
				<TD class=tdp>
					<fmt:formatDate value="${v.deadline}" type="date" pattern="yyyy-MM-dd"/>
				</TD>
			</TR>
			<TR>
				<TD class=tdp style="text-align: center;">交售日期</TD>
				<TD class=tdp>
					<fmt:formatDate value="${v.recycleDate}" type="date" pattern="yyyy-MM-dd"/>
				</TD>
				<TD class=tdp style="text-align: center;">提前报废时长</TD>
				<TD class=tdp >
					${v.advancedScrapDays}
				</TD>
				<TD class=tdp style="text-align: center;">是否财政供养</TD>
				<TD class=tdp>
					<c:if test="${v.isFinancialSupport eq '1'}">
						个人
					</c:if>
					<c:if test="${v.isFinancialSupport eq '2'}">
						车主自证非财政供养
					</c:if>
				</TD>
			</TR>
			<TR>
				<TD class=tdp style="text-align: center;">注销日期</TD>
				<TD class=tdp>
					<fmt:formatDate value="${v.destroyDate}" type="date" pattern="yyyy-MM-dd"/>
				</TD>
				<TD class=tdp style="text-align: center;">注销类别</TD>
				<TD class=tdp >
					${v.cancelReason}
				</TD>
				<TD class=tdp style="text-align: center;">回收证明号</TD>
				<TD class=tdp>
					${v.callbackProofNo}
				</TD>
			</TR>
			<TR>
				<td rowspan="2" class="tdp1" style="text-align: center;">车主<br>信息</td>
				<TD class=tdp style="text-align: center;">车辆所有人</TD>
				<TD class=tdp colspan="5">
					${v.vehicleOwner}
				</TD>
			</TR>
			<TR>
				<TD class=tdp style="text-align: center;">证件号码</TD>
				<TD class=tdp colspan="2">
					${v.vehicleOwnerIdentity}
				</TD>
				<TD class=tdp style="text-align: center;">联系电话</TD>
				<TD class=tdp colspan="2">
					${v.mobile}
				</TD>
			</TR>
			<TR>
				<td rowspan="3" class="tdp1" style="text-align: center;">补贴<br>信息</td>
				<TD class=tdp style="text-align: center;">账户名称</TD>
				<TD class=tdp colspan="2">
					${v.bankAccountName}
				</TD>
				<TD class=tdp style="text-align: center;">开户银行</TD>
				<TD class=tdp colspan="2">
					${v.bankName}
				</TD>
			</TR>
			<TR>
				<TD class=tdp style="text-align: center;">银行账号</TD>
				<TD class=tdp colspan="5">
					${v.bankAccountNo}
				</TD>
			</TR>
			<TR>
				<TD class=tdp style="text-align: center;">补贴标准</TD>
				<TD class=tdp colspan="2">
					${v.subsidiesStandard}
				</TD>
				<TD class=tdp style="text-align: center;">补贴金额</TD>
				<TD class=tdp colspan="2">
					人民币：${v.subsidiesMoney}
					   &nbsp;&nbsp;&nbsp;&nbsp;元
					 &nbsp;&nbsp;
				</TD>
			</TR>
			<TR>
				<td rowspan="2" class="tdp1" style="text-align: center;">申请人<br>信息</td>
				<TD class=tdp style="text-align: center;">申请人</TD>
				<TD class=tdp colspan="5">
					${v.agent}
				</TD>
			</TR>
			<TR>
				<TD class=tdp style="text-align: center;">证件号码</TD>
				<TD class=tdp colspan="2">
					${v.agentIdentity}
				</TD>
				<TD class=tdp style="text-align: center;">联系电话</TD>
				<TD class=tdp colspan="2">
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
			<TR style="height: 60px">
				<TD colspan="7" style="text-align: center;font-weight: bold">
					申请人核对签名：______________ 日期：_____年_____月_____日
				</TD>
			</TR>
		</TABLE>
	</div>
	<div style="text-align:center;">
	</div>
	<br>
	<br>
	<div id="applyPreview" align="center">
		<a id="btnPrint" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-print'">打印申请表</a>
		<a id="btnPreviewNextStep" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-nextstep'">下一步</a>
	</div>
	<script type="text/javascript">
		var basePath = "<%=basePath%>";
		var times = 0;
		
		// 隐藏关闭按钮
		$("#common-dialog-close").hide();
		
		// 重新设置窗口的大小，并居中
		$("#common-dialog").dialog("resize", {
				width : 1030,
				height : 500
			}).dialog("center");
		
		// 添加上一步、下一步按钮事件监听
		var stage = '${stage}';
		var apply_id = ${v.id};
		if (stage == "applyPreview") {
			 // 点击上一步按钮，跳转到受理表更新页面
			 /* $("#btnPreviewPrevStep").click(function() {
				var url = basePath+"/eliminatedApply/view.do?type=update&id="+apply_id;
				$("#common-dialog").dialog("refresh", url); 
			 }); */
			 
			 // 点击下一步，跳转到上传确认的签字受理表页面
			 $("#btnPreviewNextStep").click(function() {
				 if (times < 1) {
					 alert("请先打印受理申请表！");
					 return false;
				 }
				 var url = basePath+"/eliminatedApply/confirmPreview.do?id="+apply_id;
				 $("#common-dialog").dialog("refresh", url);
			 });
			 
			// 点击打印受理表页面
			 $("#btnPrint").click(function() {
				times ++;
				var html = $("#printArea").html();
				var oPop = window.open('','打印');  
		        var str = '<!DOCTYPE html>'  
		            str +='<html>'  
		            str +='<head>'  
		            str +='<meta charset="utf-8">'  
		            str +='<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">'  
		            str+='<style>';  
		            str+='#oDiv2 div{width: 100px;height: 100px;border:1px solid #c50000;}';  
		            str+='</style>';  
		            str +='</head>'  
		            str +='<body>'  
		            str +="<div id='oDiv2'>"+ html +"</div>";  
		            str +='</body>'  
		            str +='</html>'  
		  
		        oPop.document.write(str);  
		        oPop.print();  
		        oPop.close(); 
			 });
		}

		// 打印受理申请表
		function onPrint() {
			var html = $("#printArea").html();
			var oPop = window.open('','打印');  
	        var str = '<!DOCTYPE html>'  
	            str +='<html>'  
	            str +='<head>'  
	            str +='<meta charset="utf-8">'  
	            str +='<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">'  
	            str+='<style>';  
	            str+='#oDiv2 div{width: 100px;height: 100px;border:1px solid #c50000;}';  
	            str+='</style>';  
	            str +='</head>'  
	            str +='<body>'  
	            str +="<div id='oDiv2'>"+ html +"</div>";  
	            str +='</body>'  
	            str +='</html>'  
	  
	        oPop.document.write(str);  
	        oPop.print();  
	        oPop.close();  
			
			/* var bodyHtml = document.body.innerHTML;
			document.body.innerHTML = html;
			window.print();
			document.body.innerHTML = bodyHtml; */
		}
		
	    var insertStyleSheet = function(styles, styleId) {  
	        if (!document.getElementById(styleId)) {  
	            var style = document.createElement("style");  
	            style.id = styleId;  
	            (document.getElementsByTagName("head")[0] || document.body).appendChild(style);  
	            if (style.styleSheet) { //for ie  
	                style.styleSheet.cssText = styles;  
	            } else {//for w3c  
	                style.appendChild(document.createTextNode(styles));  
	            }  
	        }  
	    }  
		
	    function onPrintNew(areaId) {  
	        var styles = ".PrintStyleBtn{border:none;width:100%;;height:40px;line-height:40px;background-color:#eee;} .PrintStyleBtn input{ border-radius:5px;background-color:#999;color:#fff;float:right;margin:8px;height:24px;line-height:16px;padding:0px 5px;}.selected{background-color:#ccc;} .PrintStyleBtn span{margin:3px 0px;display:inline-block;border-radius:3px;height:24px;line-height:20px;padding:2px 5px;}.PrintStyleBtn span:hover{background-color:#999;} .print_content{border:1px solid #ddd;width:800px;height:auto;padding:20px;margin:50px auto;}";  
	        insertStyleSheet(styles, "PrintStyle");  
	        var printFrameHtml =  
	        '<div id="PrintFrame" style="border:none;width:100%;height:100%;position:absolute;top:0;left:0;background-color:#fff;">'  
	        + '<div class="PrintStyleBtn" style="">'  
	        + '    Font size:'  
	        + '    <span name="size">Small</span> |'  
	        + '    <span name="size" class="selected">Medium</span> |'  
	        + '    <span name="size">Large</span>    '  
	        + '    Line spacing:'  
	        + '    <span name="space">Compact</span> |'  
	        + '    <span name="space" class="selected">Normal</span>    '  
	        + '    Image:'  
	        + '    <span name="image" class="selected">Yes</span> |'  
	        + '    <span name="image">No</span>    '  
	        + '    <input id="btnCancel" type="button" value="Cancel"/>'  
	        + '    <input id="btnPrint"  type="button" value="Print" />'  
	        + '</div>'  
	        + '<div class="print_content">' + $('#' + areaId).html() + '</div></div>';  
	        $("body").append(printFrameHtml);  
	        $('.print_content').find(".viva-content-edit").remove();  
	        $('.PrintStyleBtn').find('span').on('click', function () {  
	            var obj = $(this),  
	                name = obj.attr('name'),  
	                value = obj.html();  
	            $('.PrintStyleBtn').find('span[name="' + name + '"]').removeClass('selected');  
	            obj.addClass('selected');  
	            if (name == 'size')  
	                $('.print_content').css('font-size', value == 'Small' ? 'small' : value == 'Medium' ? 'medium' : 'large' );  
	            if (name == 'space')  
	                $('.print_content').css('line-height', value == 'Compact' ? '1' : 'normal');  
	            if (name == 'image') {  
	                if (value == "Yes") {  
	                    $('.print_content').find("img").show();  
	                } else {  
	                    $('.print_content').find("img").hide();  
	                }  
	            }  
	        });  
	        $('.PrintStyleBtn').find('input').on('click', function () {  
	            if (this.value == 'Print') {  
	                $('.print_content').printArea();  
	            }   
	            $('#PrintFrame').remove();  
	        });  
	    }  
	</script>
	
</body>
</html>