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
<title>Test-update</title>
</head>
<body>
	<form id="form-confirm" action="eliminatedApply/uploadConfirm.do" method="post" enctype="multipart/form-data">
		<div id="eliminated-apply-confirm" class="datagrid-header">
			<table class="datagrid-table-s datagrid-htable">
				<%-- <tr class="datagrid-header-row classify-tr">
					<td colspan="6">车辆基本信息</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">车主类型：</td>
					<td class="view_table_right" colspan="5">
						<c:if test="${v.isPersonal eq 'Y'}">
							自然人
						</c:if>				
				 		<c:if test="${v.isPersonal eq 'N'}">
							企业
						</c:if>	
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
					<td class="view_table_left">车主：</td>
					<td class="view_table_right">叶向东</td>
					<td class="view_table_left">车主手机号码：</td>
					<td class="view_table_right">15811807108</td>
					<td class="view_table_left">车主身份证号码：</td>
					<td class="view_table_right">430123456789110119</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">邮政编码：</td>
					<td class="view_table_right">850050</td>
					<td class="view_table_left">联系地址：</td>
					<td class="view_table_right">广东省深圳市福田区深南中路2018号兴华大厦商业十一层11-G号
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">注销日期：</td>
					<td class="view_table_right">2017-07-10</td>
					<td class="view_table_left">车辆状态：</td>
					<td class="view_table_right">已注销</td>
				</tr>

				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">报废信息</td>
				</tr>
				
				<tr class="datagrid-row">
					<td class="view_table_left">报废回收证明编号：</td>
					<td class="view_table_right">HS-730000-1323-20150605-8</td>
					<td class="view_table_left">交售日期：</td>
					<td class="view_table_right">2017-07-01</td>
					<td class="view_table_left">录入时间：</td>
					<td class="view_table_right">2017-07-01 16:36</td>
				</tr>
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">补贴对象信息</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">办理类型：</td>
					<td class="view_table_right">自办</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">经办人：</td>
					<td class="view_table_right"></td>
					<td class="view_table_left">经办人手机号：</td>
					<td class="view_table_right"></td>
					<td class="view_table_left">经办人身份证号：</td>
					<td class="view_table_right"></td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">开户户名：</td>
					<td class="view_table_right">叶向东</td>
					<td class="view_table_left">开户银行：</td>
					<td class="view_table_right">招商银行</td>
					<td class="view_table_left">开户银行账号：</td>
					<td class="view_table_right">433121345646123</td>
				</tr>
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">补贴信息</td>
				</tr>
				<tr class="datagri-row">
					<td class="view_table_left">补贴金额：</td>
					<td class="view_table_right">6000元</td>
					<td class="view_table_left">补贴标准：</td>
					<td class="view_table_right">1升及以下排量轿车、专项作业车 --6000元</td>
				</tr>
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">证明材料</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_right"><a href="images/bfzm.jpg" target="_blank" style="text-decoration:none;">报废汽车回收证明书</a></td>
					<td class="view_table_right"><a href="images/zxzm.jpg" target="_blank" style="text-decoration:none;">机动车注销证明</a></td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_right"><a href="images/zrryhk.jpg" target="_blank" style="text-decoration:none;">银行卡</a></td>
					<td class="view_table_right"><a href="images/zrrsfz.jpg" target="_blank" style="text-decoration:none;">车主身份证明</a></td>
				</tr>
				<c:if test="${v.isPersonal == 'N'}">
				<tr class="datagrid-row">
					<td class="view_table_right"><a href="images/fzrryhk.jpg" target="_blank" style="text-decoration:none;">开户许可证</a></td>
					<td class="view_table_right"><a href="images/dlwts.jpg" target="_blank" style="text-decoration:none;">非财政供养单位证明</a></td>
				</tr>
				</c:if>
				<c:if test="${v.isProxy == 'N'}">
				<tr class="datagrid-row">
					<td class="view_table_right"><a href="images/dlwts.jpg" target="_blank" style="text-decoration:none;">代理委托书</a></td>
					<td class="view_table_right"><a href="images/dlrsfz.jpg" target="_blank" style="text-decoration:none;">代理人身份证</a></td>
				</tr>
				</c:if>
				<tr class="datagrid-row">
					<td class="view_table_right"><a href="images/jdczcdjzs.jpg" target="_blank" style="text-decoration:none;">机动车注册登记证书</a></td>
				</tr> --%>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:110px">签字的受理表：</td>
					<td class="view_table_right">
						<input id="signedApplyFile" type="file" name="signedApplyFile" required="required" />
						<!-- <input id="signedApplyFile" class="easyui-filebox" name="signedApplyFile" data-options="prompt:'选择文件',required:true,buttonText:'请选择'"/> -->
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoConfirmFile" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照上传</a>
						<a id="btnUploadConfirmFile" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-shangchuan'">本地文件上传</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="signedApplyFileImg" href="#"></a>
					</td>
				</tr>
			</table>
		</div>
	</form>
	
	<input type="hidden" name="signedApplyFiles"/>
	<div id="confirm" align="center">
		<a id="btnPrintAgain" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-print'">重新打印受理表</a>
		<a id="btnConfirmPrevStep" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-prevstep'">返回继续修改</a>
		<a id="btnConfirmNextStep" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">受理确认</a>
	</div>
	
	<script type="text/javascript">
		$(function() {
			var basePath = "<%=basePath%>";
			
			// 隐藏关闭按钮
			$("#common-dialog-close").hide();
			
			// 调整页面大小高度，展示
			/* $("#common-dialog").dialog("resize", {
				width : 800,
				height : 200
			}).dialog("center"); */
			
			// 上传确认的受理表时所处阶段
			var stage = '${stage}';
			var id = ${v.id};
			var vehiclePlateNum = '${v.vehiclePlateNum}';
			if (stage == "confirm") {
				// 点击上一步按钮，跳转到受理表更新页面
				 $("#btnConfirmPrevStep").click(function() {
					var url = basePath+"/eliminatedApply/view.do?type=update&id="+id;
					//$("#common-dialog").dialog("refresh", url); 
					$("#common-dialog").dialog("close"); 
					openDialog({
					   	type : "UPDATE",
						title : "补贴受理信息修改",
						width : 1132,
						height : 800,
						param : 
				    	   {
				    		   reset:false,
				    		   save:false,
							   beforeCloseFunc:"clearCaptureRes",
							   isBeforeClose:true
						   },	
						maximizable : true,
						href : url
					});
				 });
				 
				 // 点击下一步，更新受理单状态并跳转到打印受理回执单页面
				 $("#btnConfirmNextStep").click(function() {
					// 确认受理表上传成功，更修受理表信息，页面跳转到受理回执单打印预览页面
					if ($("input[name='signedApplyFiles']").val() == "") {
						alert("确认的受理表还未上传，请先上传！");
						return false;
					} else {
						// var receipt_url = basePath+"/eliminatedApply/receiptPreview.do?id="+id+"&signedApplyFiles="+$("input[name='signedApplyFiles']").val();
						var confirm_url = basePath+"/eliminatedApply/confirmApply.do";
						
						// ajax请求后台，更新受理确认时间、受理确认表附件、并判断该车辆所属是否有未预约的其他车辆
						$.ajax({
			                //cache: true,
			                type: "POST",
			                url: confirm_url,
			                data:
			                	{
			                		"id" : id,
			                		"signedApplyFiles" : $("input[name='signedApplyFiles']").val()
			                	},
			                async: true,
			                success: function(data) {
			                	
			                	if(Object.prototype.toString.call(data) === "[object String]") {
									data = eval("(" + data + ")");
								}
								
								if(data.success) {
									
									Messager.show({
										title:"&nbsp;",
										content:data.message
									});
									// 更新受理表成功，跳转到打印受理回执单页面
									
									var url = basePath+"/eliminatedApply/receiptPreview.do?id="+id;
									$("#common-dialog").dialog("close"); 
									openDialog({
									   	type : "PRINT_RECEIPT",
										title : "补贴受理回执单打印预览",
										width : 1040,
										height : 500,
										param: {reset:false,save:false,close:false},	
										maximizable : true,
										href : url
									});
									
									$("#eliminated-apply-list #eliminated-apply-grid").datagrid("load");
									
									//$("#common-dialog").dialog("refresh", url);
									
			                	} else {
			                		Messager.alert({
			                			type:"error",
										title:"&nbsp;",
										content:data.message
									});
			                		$("#common-dialog").dialog("close");
			                	}
			            	}
						});
						
						$("#eliminated-apply-list #eliminated-apply-grid").datagrid("load");
						//$("#common-dialog").dialog("refresh", receipt_url); 
					}
				});
			}
			
			// 点击上传按钮，上传确认的受理表
			$("#btnUploadConfirmFile").click(function() {
				$("#form-confirm").form("submit", {
					 url : $("#form-confirm").attr("action")+"?id="+id,
					 onSubmit : function() {
						 var isValid = $(this).form("enableValidation").form("validate");
						 if (isValid) {
							 return isValid;
						 } else {
							 alert("请选择签字确认的受理表！");
							 return false;
						 }
					 },
					 success : function(data) {
						 if(Object.prototype.toString.call(data) === "[object String]") {
								data = eval("(" + data + ")");
							}
							
							if(data.success) {
								
								Messager.show({
									title:"&nbsp;",
									content:"签字确认的受理表上传成功！"
								});
								
								// 设置文件回显、页面隐藏路径、点击事件等
								setFilePreview("signedApply", data.message.signedApplyFile, "确认签字的受理表");
								
								// 将图片路径传到页面，方便查看。
								/* $("#signedApplyFileImg").text("确认签字的受理表");
	        					$("#signedApplyFileImg").attr("href", basePath+'/'+data.message.signedApplyFile);
	        					$("input[name='signedApplyFiles']").val(data.message.signedApplyFile); */
					 	 	} else {
					 	 		alert("签字确认的受理表上传失败！");
					 	 	}		 
				 		}
		   			});
				});
			
			// 重新打印受理表
			$("#btnPrintAgain").click(function() {
				var url = basePath+"/eliminatedApply/applyPreview.do?&id="+id;
				$("#common-dialog").dialog("refresh", url);
				$("#common-dialog").dialog("close");
			 	openDialog({
				   	type : "PRINT_APPLY_TABLE",
					title : "补贴受理表打印预览",
					width : 1040,
					height : 500,
					param: {reset:false,save:false},	
					maximizable : true,
					href : url
				});
			});
			
			// 点击拍照进行抓拍确认受理表
			$("#btnTakePhotoConfirmFile").click(function() {
				var vehiclePlateNum = '${v.vehiclePlateNum}';
				// 弹出高拍仪抓拍图片界面
				var parentValue = window.showModalDialog("eliminatedApply/capture.jsp?vehiclePlateNum="+vehiclePlateNum.substring(1), "图片抓拍上传", "dialogWidth=700px,dialogHeight=600px,resizable=yes,status=no,scrollbars=yes,menubar=no");
        	
	        	//alert(parentValue.filepath);
	        	
	        	// 抓拍返回，设置图片路径到页面字段
	        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
	        		
	        		setFilePreview("signedApply", parentValue.filepath, "确认签字的受理表");
	        		
	        		/* var files = parentValue.filepath.split(",");
	        		// 多张图片预览
	        		$("#signedApplyFileImg").text("签字受理表(1)");
        			$("#signedApplyFileImg").attr("href", basePath+'/'+files[0]);
	        		if (files.length > 1) {
	        			for (var i = 2 ; i <= files.length ; i ++) {
	        				var filepath = basePath + '/' + files[i-1];
	        				var _a = "&nbsp<a id='#signedApplyFileImg"+i+"' href='"+filepath+"' target='_blank'>签字受理表("+i+")</a>";
	        				$("#signedApplyFileImg").append(_a);
	        			}
	        		} 
	        		
        			// 设置隐藏字段传递到后台
    				$("input[name='signedApplyFiles']").val(parentValue.filepath); */
	        	}
			});
			
		});
		
		// 图片集中预览
		function picturePreview(currentType, index) {
			var basePath = "<%=basePath%>";
			// 获取页面所有图片路径，传递到新窗口
			// 先获取页面已经上传的文件路径
			var proof_files = window;
			
			// 签字确认的受理表
			proof_files.signedApplyFiles =  $("input[name='signedApplyFiles']").val();
			
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
    				var filepath = "picturePreview('"+type+"', '"+ i +"');";
    				var _a = "&nbsp<a id='"+id+i+"' href='javascript:void(0)' onclick='"+filepath+"'>"+name+"("+i+")</a>";
    				$(id).append(_a);
    			}
    		} 
    		
			// 设置隐藏字段传递到后台
			$("input[name='"+filename+"']").val(files);
		}
	</script>

</body>
</html>