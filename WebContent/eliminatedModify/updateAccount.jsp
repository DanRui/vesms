<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false" %>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form id="form-update-account" action="eliminatedModify/accountSave.do" method="post" >
		<div id="eliminated-update-account" class="datagrid-header">
			<table class="datagrid-table-s datagrid-htable">
				<tr class="datagrid-header-row">
				<td class="view_table_left">车主类型：</td>
				<td class="view_table_right" colspan="2">
					<c:if test="${v.isPersonal eq 'Y'}">
						自然人
					</c:if>
					<c:if test="${v.isPersonal eq 'N'}">
						企业
					</c:if>
				</td>
				<td class="view_table_left">办理类型：</td>
				<td class="view_table_right" colspan="2">
					<c:if test="${v.isProxy == 'Y'}">
						自办
					</c:if>
					<c:if test="${v.isProxy == 'N'}">
						代理
					</c:if>
				</td>
			</tr>
			<tr class="datagrid-header-row classify-tr">
				<td colspan="6">车辆基本信息</td>
			</tr>
			<tr class="datagrid-row">
				<td class="view_table_left">号牌号码：</td>
				<td class="view_table_right">
					${v.vehiclePlateNum}
				</td>
				<td class="view_table_left">号牌种类：</td>
				<td class="view_table_right">
					${v.vehiclePlateTypeName}
				</td>
				<td class="view_table_left">厂牌型号：</td>
				<td class="view_table_right">
					${v.vehicleModelNo}
				</td>
			</tr>
			<tr class="datagrid-row">
				<td class="view_table_left">车辆类型：</td>
				<td class="view_table_right">
					${v.vehicleTypeName}
				</td>
				<td class="view_table_left">车架号：</td>
				<td class="view_table_right">
					${v.vehicleIdentifyNo}
				</td>
				<td class="view_table_left">发动机号：</td>
				<td class="view_table_right">
					${v.engineNo}
				</td>
			</tr>
			<tr class="datagrid-row">
				<td class="view_table_left">使用性质：</td>
				<td class="view_table_right">
					${v.useOfPropertyName}
				</td>
				<td class="view_table_left">燃油类型：</td>
				<td class="view_table_right">
					${v.iolTypeName}
				</td>
				<td class="view_table_left">总质量（千克）</td>
				<td class="view_table_right">
					${v.totalWeight}
				</td>
			</tr>
			<tr class="datagrid-row">
				<td class="view_table_left">核定载客数（人）：</td>
				<td class="view_table_right">
					${v.vehicleNumPeople}
				</td>
				<td class="view_table_left">初次登记日期：</td>
				<td class="view_table_right">
					<fmt:formatDate value="${v.registerDate}" type="date" pattern="yyyy-MM-dd"/>
				</td>
				<td class="view_table_left">强制报废期：</td>
				<td class="view_table_right">
					<fmt:formatDate value="${v.deadline}" type="date" pattern="yyyy-MM-dd"/>
				</td>
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
					<fmt:formatDate value="${v.destroyDate}" type="date" pattern="yyyy-MM-dd"/>
				</td>
				<td class="view_table_left">车辆状态：</td>
				<td class="view_table_right">
					${v.vehicleStatusName}
				</td>
				<td class="view_table_left">提前报废时长（天）：</td>
				<td class="view_table_right">
					${v.advancedScrapDays}
				</td>
			</tr>
			<tr class="datagrid-header-row">
				<td class="view_table_left">排放标准：</td>
				<td class="view_table_right">
					${v.emissionStandard}
				</td>
				<td class="view_table_left">注销类别：</td>
				<td class="view_table_right">
					${v.cancelReason}
				</td>
				<td class="view_table_left">是否财政供养：</td>
				<td class="view_table_right">
					<c:if test="${v.isFinancialSupport eq '1'}">
						个人
					</c:if>
					<c:if test="${v.isFinancialSupport eq '2'}">
						车主自证非财政供养
					</c:if>
				</td>
			</tr>
			<tr class="datagrid-header-row classify-tr">
				<td colspan="6">报废信息</td>
			</tr>
			<tr class="datagrid-row">
				<td class="view_table_left" style="width: 110px">报废回收证明编号：</td>
				<td class="view_table_right">
					${v.callbackProofNo}
				</td>
				<td class="view_table_left">交售日期：</td>
				<td class="view_table_right">
					<fmt:formatDate value="${v.recycleDate}" type="date" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr class="datagrid-header-row classify-tr">
				<td colspan="6">补贴对象信息</td>
			</tr>
			<tr class="datagrid-row">
				<td class="view_table_left">车主：</td>
				<td class="view_table_right">
					${v.vehicleOwner}
				</td>
				<td class="view_table_left">车主身份证明号码：</td>
				<td class="view_table_right">
					${v.vehicleOwnerIdentity}
				</td>
				<td class="view_table_left">车主联系号码：</td>
				<td class="view_table_right">
					${v.mobile}
				</td>
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
			<tr class="datagrid-row">
				<td class="view_table_left" style="width:110px">修改后开户户名：</td>
				<td class="view_table_right"><input type="text"
					name="bankAccountName" class="easyui-validatebox"
					data-options="required:true" />
					<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
				</td>
				<td class="view_table_left" style="width:110px">修改后开户银行：</td>
				<td class="view_table_right">
					<input id="bankCodeNew" class="easyui-combobox" name="bankCode" 
					data-options="editable:false,required:true,valueField:'code',textField:'value',url:'sysDict/getDictListByType.do?dictType=BANK_CODE',panelHeight:150"/>
					<input type="text" name="OtherBankName"/>
					<input type="hidden" name="bankName" />
					<!-- <input type="text" name="bankName" class="easyui-validatebox"
					data-options="required:true" /> -->
					<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
				</td>
				<td class="view_table_left" style="width:120px">修改后开户银行账号：</td>
				<td class="view_table_right"><input type="text" value=""
					name="bankAccountNo" class="easyui-validatebox"
					data-options="required:true" />
					<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
				</td>
			</tr>
			<tr class="datagrid-header-row classify-tr">
				<td colspan="6">补贴信息</td>
			</tr>
			<tr class="datagrid-row">
				<td class="view_table_left">补贴金额：</td>
				<td class="view_table_right">
					${v.subsidiesMoney}
				</td>
				<td class="view_table_left">补贴标准说明：</td>
				<td class="view_table_right">
					${v.subsidiesStandard}
				</td>
			</tr>
			
			<tr class="datagrid-header-row" style="color:red">
				<td class="view_table_left">修正结果：</td>
				<td class="view_table_right">
					<input type="radio" name="modifyResult" value="1"/>结束修正
					<input type="radio" name="modifyResult" value="2"/>继续修正
					<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
				</td>
			</tr>
			
			<input type="hidden" name="accountChangeProofFiles"/>
		</table>
		</div>
	</form>
	<form id="form-acc-change-upload" action="eliminatedModify/fileUploadAccChange.do" method="post" enctype="multipart/form-data">
		<div class="datagrid-header">
			<table class="datagrid-table-s datagrid-htable">
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">补贴账户变更证明材料</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:110px">账户变更材料证明：</td>
					<td class="view_table_right">
						<input type="file" name="accountChangeProof" required="required" multiple="multiple" />
						<!-- <input id="accountChangeFiles" name="accountChangeFiles" data-options="editable:false,required:true,buttonText:'请选择'"
						class="easyui-filebox" /> -->
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoAccChangeProof" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="accountChangeProofFileImg" href="#"></a>
					</td>
				</tr>
				<tr class="datagrid-row">
					<td align="center" colspan="6">
						<a id="btnAccUpload" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-shangchuan'">本地文件上传</a>
						<!-- <a id="btnFilePreview" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-view'">查看图片</a> -->
					</td>
				</tr>
			</table>
		</div>
	</form>
	
	<script type="text/javascript">
		$(function() {
			
			$("#btnFilePreview").hide();
			
			var basePath = "<%=basePath%>";
			
			var vehiclePlateNum = "${v.vehiclePlateNum}";
			
			// 默认修正结果为结束修正
			$("input[type='radio'][name='modifyResult'][value='1']").attr("checked", true);
			
			// 选择银行名称
			$("input[name='OtherBankName']").hide();
			
			$("#bankCodeNew").combobox({
				onSelect : function(rec) {
					if (rec.code == "999") {
						$("input[name='OtherBankName']").attr("required", true);
						$("input[name='OtherBankName']").show();
					} else {
						$("input[name='OtherBankName']").attr("required", false);
						$("input[name='OtherBankName']").hide();
					}
				}
			});
			
			// 上传补贴账户变更材料
			$("#btnAccUpload").click(function() {
				//校验文件是否选择
				if ($("input[name='accountChangeProof']").val() == "") {
					alert("请选择文件上传！");
					return false;
				}
				$("#form-acc-change-upload").form("submit", {
					url : basePath+"/eliminatedModify/fileUploadAccChange.do?id="+'${v.id}',
					success : function(data) {
						var data = eval('(' + data + ')');
						if (data.success) {
							// 文件上传成功，将文件路径传到前台页面
							Messager.alert({
								type : "info",
								title : "&nbsp;",
								content : "证明材料上传成功！"
							});
							/* var files = data.message.accountChangeProof.split(",");
			        		// 多张图片预览
			        		$("#accountChangeFileImg").text("补贴对象变更证明(1)");
		        			$("#accountChangeFileImg").attr("href", basePath+'/'+files[0]);
			        		if (files.length > 1) {
			        			for (var i = 2 ; i <= files.length ; i ++) {
			        				var filepath = basePath + '/' + files[i-1];
			        				var _a = "&nbsp<a id='#accountChangeFileImg"+i+"' href='"+filepath+"' target='_blank'>补贴对象变更证明("+i+")</a>";
			        				$("#accountChangeFileImg").append(_a);
			        			}
			        		}
							$("input[name='accountChangeProofFiles']").val(data.message.accountChangeProof); */
							// $("#btnFilePreview").show();
							//alert(data.message.accountChangeProof);
							// 解析文件路径数组，显示图片预览
							setFilePreview("accountChangeProof", data.message.accountChangeProof, "补贴对象变更证明");
							
						} else {
							alert("文件上传失败！");
							return;
						}
					}
				});
			});
			
			$("#btnTakePhotoAccChangeProof").click(function() {
				var vehiclePlateNum = '${v.vehiclePlateNum}'; 
				// 弹出高拍仪抓拍图片界面
				var parentValue = window.showModalDialog("eliminatedModify/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "dialogWidth=700px,dialogHeight=600px,resizable=yes,status=no,scrollbars=yes,menubar=no");
        	
	        	//alert(parentValue.filepath);
	        	
	        	// 抓拍返回，设置图片路径到页面字段
	        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
	        		
	        		setFilePreview("accountChangeProof", parentValue.filepath, "补贴对象变更证明");
	        		
	        		/* var files = parentValue.filepath.split(",");
	        		// 多张图片预览
	        		$("#accountChangeFileImg").text("补贴对象变更证明(1)");
        			$("#accountChangeFileImg").attr("href", basePath+'/'+files[0]);
	        		if (files.length > 1) {
	        			for (var i = 2 ; i <= files.length ; i ++) {
	        				var filepath = basePath + '/' + files[i-1];
	        				var _a = "&nbsp<a id='#accountChangeFileImg"+i+"' href='"+filepath+"' target='_blank'>补贴对象变更证明("+i+")</a>";
	        				$("#accountChangeFileImg").append(_a);
	        			}
	        		} 
	        		
        			// 设置隐藏字段传递到后台
    				$("input[name='accountChangeProofFiles']").val(parentValue.filepath); */
	        	}
			});
			
			// 查看图片按钮
			$("#btnFilePreview").click(function() {
				if ($("input[name='accountChangeProofFiles']").val() == "") {
					alert("证明材料还未上传，请先上传！");
					return false;
				}
				// 弹出层进行图片预览
			});
			
			// 点击提交按钮，更新数据
			$("#common-dialog-update_account").click(function() {
				/* var isValid = $("#form-update-account form").form("enableValidation").form("validate"); */
				var id = '${v.id}';
				var modifyResult = $('input[name="modifyResult"]:checked').val();
				var bankName = $("#bankCodeNew").combobox("getText");
				$("input[name='bankName']").val(bankName);
				$("#form-update-account").form("submit", {
						url : $(this).attr("action"),
						onSubmit : function(param) {
							param.id = id;
							param.otherBankName = $("input[name='OtherBankName']").val();
							var isValid = $(this).form("enableValidation").form("validate");
							if (isValid) {
								var bankCode = $("#bankCodeNew").combobox("getValue");
								if (bankCode == "999" && $("input[name='OtherBankName']").val() == "") {
									alert("请先填写其它银行名称！");
									return false;
								}
								
								if ($("input[name='accountChangeProofFiles']").val() == "") {
									alert("证明材料还未上传，请先上传！");
									return false;
								}
							} else {
								alert("请填写修正后的补贴账户信息！");
								// 如果选择的是“其它银行”，则必填
								return false;
							}
							return true;
						},
						success : function(data) {
							var result = eval('(' + data + ')');
	        		 		if (result.success) {
	        		 			alert(result.message.msg);
	        		 			
	        		 			// 选择结束修正，受理表信息更新，页面跳转到受理表打印预览页面
								var url = basePath+"/eliminatedModify/applyPreview.do?id="+result.message.id;
								
								$("#common-dialog").dialog("close");
								
								if (modifyResult == "1") {
									openDialog({
									   	type : "PRINT_APPLY_TABLE",
										title : "补贴受理表打印预览",
										width : 1040,
										height : 400,
										param: {reset:false,save:false,
											beforeCloseFunc:"clearCaptureRes",
											isBeforeClose:true},	
										maximizable : true,
										href : url
								    });
								}
	        		 			
	        		 			//$("#common-dialog").dialog("close");
	        		 			
	        		 			$("#subsidTarget-apply-list #subsidTarget-apply-grid").datagrid("load");
	        		 			
	        		 		} else {
	        		 			alert(result.message.msg);
	        		 			$("#common-dialog").dialog("close");
	        		 			
	        		 			$("#subsidTarget-apply-list #subsidTarget-apply-grid").datagrid("load");
		        		 	}
						}
					});
			});
			
		});
		
		// 图片集中预览
		function picturePreview(currentType, index) {
			var basePath = "<%=basePath%>";
			// 获取页面所有图片路径，传递到新窗口
			// 先获取页面已经上传的文件路径
			var proof_files = window;
			
			/* // 报废回收证明
			proof_files.callbackProofFile = $("input[name='callbackProofFile']").val();
			// 机动车注销证明
			proof_files.vehicleCancelProofFiles = $("input[name='vehicleCancelProofFiles']").val();
			// 银行卡
			proof_files.bankCardFiles = $("input[name='bankCardFiles']").val();
			// 车主身份证明
			proof_files.vehicleOwnerProofFiles = $("input[name='vehicleOwnerProofFiles']").val();
			// 开户许可证
			proof_files.openAccPromitFiles = $("input[name='openAccPromitFiles']").val();
			// 非财政供养单位证明
			proof_files.noFinanceProvideFiles = $("input[name='noFinanceProvideFiles']").val();
			// 代理人身份证
			proof_files.agentProofFiles = $("input[name='agentProofFiles']").val();
			// 代理委托书
			proof_files.agentProxyFiles = $("input[name='agentProxyFiles']").val();
			// 签字确认的受理表
			proof_files.signedApplyFiles = getFilepath("signedApply"); */
			// 补贴对象变更证明材料
			proof_files.accountChangeProofFiles = $("input[name='accountChangeProofFiles']").val();
			
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