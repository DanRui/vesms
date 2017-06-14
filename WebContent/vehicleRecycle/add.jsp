<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>vehicle-recycle-add</title>
</head>
<body>
	<form id="vehicle-recycle-add" action="vehicleRecycle/save.do"
		method="post">
		<!-- <div id="vehicle-recycle-add" class="easyui-accordion"
			data-options="fit:true,animate:false"> -->
		<div id="vehicle-recycle-add" class="datagrid-header">
			<table class="datagrid-table-s datagrid-htable">
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">车辆基本信息</td>
				</tr>
				<tr class="datagrid-header-row">
					<td class="view_table_left" style="width: 110px">报废回收证明编号：</td>
					<td class="view_table_right" colspan="5"><input type="text"
						name="callbackProofNo" data-options="required:true"
						class="easyui-validatebox" /> <font color="red">&nbsp;*&nbsp;</font>
						<a id="btnVerify" href="#" class="easyui-linkbutton"
						data-options="iconCls:'icon-baofei-message'">同步</a></td>
				</tr>
				<tr class="datagrid-header-row">
					<td class="view_table_left">号牌号码：</td>
					<td class="view_table_right"><input type="text"
						name="vehiclePlateNum" readonly="readonly" /></td>
					<td class="view_table_left">号牌种类：</td>
					<td class="view_table_right">
						<input name="vehiclePlateTypeName" type="text" readonly="readonly" />
						<input type="hidden" name="vehiclePlateType"/>
						<!-- <input id="vehiclePlateType" class="easyui-combobox" name="vehiclePlateType" 
						data-options="editable:false,required:true,valueField:'value',textField:'name',url:'data/vehiclePlateType.json',panelHeight:'auto'"/>
						<a id="btnVerify" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-baofei-message'">取机动车资料</a>   -->
					</td>
					<td class="view_table_left">厂牌型号：</td>
					<td class="view_table_right"><input type="text"
						name="vehicleModelNo" readonly="readonly" /></td>
				</tr>
				<tr class="datagrid-header-row">
					<td class="view_table_left">车辆类型：</td>
					<td class="view_table_right">
						<input type="text" name="vehicleTypeName" readonly="readonly" />
						<input type="hidden" name="vehicleType" />
					</td>
					<td class="view_table_left">车辆状态：</td>
					<td class="view_table_right">
						<input type="text" name="vehicleStatusName" readonly="readonly" />
						<input type="hidden" name="vehicleStatus" />
					</td>
					<td class="view_table_left">车架号：</td>
					<td class="view_table_right"><input type="text"
						name="vehicleIdentifyNo" readonly="readonly" /></td>
				</tr>
				<tr class="datagrid-header-row">
					<td class="view_table_left">使用性质：</td>
					<td class="view_table_right">
						<input type="text" name="useOfPropertyName" readonly="readonly" />
						<input type="hidden" name="useOfProperty"/>
					</td>
					<td class="view_table_left">燃油类型：</td>
					<td class="view_table_right">
						<input type="text" name="iolTypeName" readonly="readonly" />
						<input type="hidden" name="iolType" />
					</td>
					<td class="view_table_left" style="width: 110px">总质量（千克）：</td>
					<td class="view_table_right"><input type="text"
						name="totalWeight" readonly="readonly" value = "0" /></td>
				</tr>
				<tr class="datagrid-header-row">
					<td class="view_table_left" style="width: 110px">核定载客数（人）：</td>
					<td class="view_table_right"><input type="text"
						name="vehicleNumPeople" readonly="readonly" value = "0" /></td>
					<td class="view_table_left">初次登记日期：</td>
					<td class="view_table_right"><input type="text"
						name="registerDate" readonly="readonly" /></td>
					<td class="view_table_left">强制报废期：</td>
					<td class="view_table_right"><input type="text"
						name="deadline" readonly="readonly" /></td>
				</tr>
				<tr class="datagrid-header-row">
					<td class="view_table_left">发动机号：</td>
					<td class="view_table_right"><input type="text"
						name="engineNo" readonly="readonly" /></td>
				</tr>
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">车主信息</td>
				</tr>
				<tr class="datagrid-header-row">
					<td class="view_table_left">车主：</td>
					<td class="view_table_right"><textarea name="vehicleOwner"
							readonly="readonly"></textarea></td>
				</tr>

				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">报废信息</td>
				</tr>
				<tr class="datagrid-header-row">
					<td class="view_table_left">交售日期：</td>
					<td class="view_table_right"><input type="text"
						name="recycleDate" readonly="readonly" /></td>
				</tr>
				<input type="hidden" name="callbackProofFile"/>
				<input type="hidden" name="vehicleRegisterProofFiles"/>
				<input type="hidden" name="vehicleLicenseFiles"/>
			</table>
		</div>
	</form>

	<form id="formUpload" action="vehicleRecycle/fileUpload.do"
		method="post" enctype="multipart/form-data">
		<div id="vehicle-recycle-upload" class="datagrid-header">
			<table class="datagrid-table-s datagrid-htable">
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">材料附件</td>
				</tr>
				<tr class="datagrid-header-row">
					<td class="view_table_left" style="width: 110px">报废回收证明：</td>
					<td class="view_table_right" colspan="2">
						<input id="callbackProofFiles" name="callbackProofFiles" data-options="editable:false,required:true,buttonText:'请选择'"
						class="easyui-filebox" />
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoCallbackProof" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-baofei-message'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="callbackProofFileImg" href="#" target="_blank"></a>
					</td>
				</tr>
				<tr class="datagrid-header-row">
					<td class="view_table_left" style="width: 120px">机动车注册登记证书：</td>
					<td class="view_table_right" colspan="2"><input
						id="vehicleRegisterProof" class="easyui-filebox"
						name="vehicleRegisterProof"
						data-options="editable:false,buttonText:'请选择'" />
						<a id="btnTakePhotoRegisterProof" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-baofei-message'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="registerProofFileImg" href="#" target="_blank"></a>
					</td>
				</tr>
				<tr class="datagrid-header-row">
					<td class="view_table_left" style="width: 120px">行驶证：</td>
					<td class="view_table_right" colspan="2"><input
						id="vehicleLicense" class="easyui-filebox" name="vehicleLicense"
						data-options="editable:false,buttonText:'请选择'" />
						<a id="btnTakePhotoVehicleLicense" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-baofei-message'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="vehicleLicenseFileImg" href="#" target="_blank"></a>
					</td>
				</tr>
				<tr class="datagrid-header-row">
					<td align="center" colspan="6">
						<a id="btnUpload" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-shangchuan'">上传</a>
					</td>
				</tr>
			</table>
		</div>
	</form>

	<script type="text/javascript">
		$(function() {
			var basePath = "<%=basePath%>";
			
			$('#btnVerify').bind('click', function(){  
				var callbackProofNo = $("input[name='callbackProofNo']").val();
				if (callbackProofNo == "") {
					alert("请输入报废回收证明编号获取报废车辆录入数据！");
					return false;
				}
		        $.ajax({
		        	url : basePath + "/vehicleRecycle/syncVehicleInfo.do?callbackProofNo=" + callbackProofNo,
		        	async : true,
		        	type : "POST",
		        	dataType : "json",
		        	beforeSend : function() {
		        		if (null == callbackProofNo) {
		        			return false;
		        		}
		        	},
		        	success : function(data) {
		        		if (data.success) {
			        		//将获取到的数据填充到页面字段，并显示。
			        		// 号牌号码
			        		$("input[name='vehiclePlateNum']").val(data.message.vehiclePlateNum);
			        		// 号牌种类
			        		$("input[name='vehiclePlateType']").val(data.message.vehiclePlateType);
			        		$("input[name='vehiclePlateTypeName']").val(data.message.vehiclePlateTypeName);
			        		// 厂牌型号
			        		$("input[name='vehicleModelNo']").val(data.message.vehicleModelNo);
			        		// 车辆类型
			        		$("input[name='vehicleType']").val(data.message.vehicleType);
			        		$("input[name='vehicleTypeName']").val(data.message.vehicleTypeName);
			        		// 发动机型号
			        		$("input[name='engineNo']").val(data.message.engineNo);
			        		// 车辆状态
			        		$("input[name='vehicleStatus']").val(data.message.vehicleStatus);
			        		$("input[name='vehicleStatusName']").val(data.message.vehicleStatusName);
			        		// 车架号
			        		$("input[name='vehicleIdentifyNo']").val(data.message.vehicleIdentifyNo);
			        		// 使用性质
			        		$("input[name='useOfProperty']").val(data.message.useOfProperty);
			        		$("input[name='useOfPropertyName']").val(data.message.useOfPropertyName);
			        		// 燃油种类
			        		$("input[name='iolType']").val(data.message.iolType);
			        		$("input[name='iolTypeName']").val(data.message.iolTypeName);
			        		// 总质量
			        		$("input[name='totalWeight']").val(data.message.totalWeight);
			        		// 核定载客数
			        		$("input[name='vehicleNumPeople']").val(data.message.vehicleNumPeople);
			        		// 初次登记日期
			        		$("input[name='registerDate']").val(getNowFormatDate(new Date(data.message.registerDate.time)));
			        		// 强制报废期止
			        		$("input[name='deadline']").val(getNowFormatDate(new Date(data.message.deadline.time)));
			        		// 车主
			        		$("textarea[name='vehicleOwner']").val(data.message.vehicleOwner);
			        		// 交售日期
			        		$("input[name='recycleDate']").val(getNowFormatDate(new Date(data.message.recycleDate.time)));
		        		} else {
		        			alert("报废数据获取失败!");
		        		}
		        	}
		        }); 
		    });  
			
			//报废回收证明、机动车注册登记证书、行驶证
		   $("#btnUpload").click(function() {
			   
			   var callbackProofFiles = $("input[name='callbackProofFiles']").val();
			   if (callbackProofFiles == "") {
				   alert("报废回收证明为空，请上传！");
				   return false;
			   } 
			   
        	 	$("#formUpload").form("submit", {
        			url  : $(this).attr("action"),
        		 	success : function(result) {
        		 		var result = eval('(' + result + ')');
        		 		if (result.success) {
        		 			alert("文件上传成功！");
        					$("#callbackProofFileImg").text("报废汽车回收证明");
        					$("#callbackProofFileImg").attr("href", basePath+'/'+result.message.callbackProofFiles);
        					$("input[name='callbackProofFile']").val(result.message.callbackProofFiles);
        					
        					if (result.message.vehicleRegisterProof != "") {
	        					$("#registerProofFileImg").text("机动车注册登记证书");
	        					$("#registerProofFileImg").attr("href", basePath+'/'+result.message.vehicleRegisterProof);
        					}
        					
        					if (result.message.vehicleLicense != "") {
	        					$("#vehicleLicenseFileImg").text("行驶证");
    	    					$("#vehicleLicenseFileImg").attr("href", basePath+'/'+result.message.vehicleLicense);
        					}
        		 		} else {
        		 			alert("文件上传失败！");
        		 			$("#callbackProofFile").filebox("clear");
        		 			$("#vehicleRegisterProof").filebox("clear");
        		 			$("#vehicleLicense").filebox("clear");
        		 		}
        			}
        	 	});
	        });
	        
			// 报废回收证明抓拍
	        $("#btnTakePhotoCallbackProof").click(function() {
	        	// 弹出高拍仪抓拍图片界面
				var parentValue = window.showModalDialog("vehicleRecycle/capture.jsp", "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
	        	
	        	alert(parentValue.filepath);
	        	
	        	// 抓拍返回，设置图片路径到页面字段
	        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
	        		var files = parentValue.filepath.split(",");
	        		// 多张图片预览
	        		$("#callbackProofFileImg").text("报废汽车回收证明(1)");
        			$("#callbackProofFileImg").attr("href", basePath+'/'+files[0]);
	        		if (files.length > 1) {
	        			for (var i = 2 ; i <= files.length ; i ++) {
	        				// alert(files[i-1]);
	        				var filepath = basePath + '/' + files[i-1];
	        				var _a = "&nbsp<a id='#callbackProofFileImg"+i+"' href='"+filepath+"' target='_blank'>报废汽车回收证明("+i+")</a>";
	        				$("#callbackProofFileImg").append(_a);
	        			}
	        		} 
	        		
        			// 设置隐藏字段传递到后台
    				$("input[name='callbackProofFile']").val(parentValue.filepath);
	        	}
	        	/*Dialog.create("take_photo", {
					type : "take_photo",
					title : "图片抓拍",
					width : 700,
					height : 500,
					param: {
							reset:false,
							buttons:[
									{id:"photo_save",text:"确认",iconCls:"icon-save",handler:function(){
										//var ifValid = $("#pay-res-check-form").form("enableValidation").form("validate");
										//if (ifValid) {
											alert("标记完成！");
											$("#take_photo").dialog("close");
										//}
									}},
									{id:"photo_cancel",text:"取消",iconCls:"icon-cancel",handler:function(){
										$("#take_photo").dialog("close");
									}}
								]
						},
					maximizable : true,
					href : basePath+"/vehicleRecycle/capture.html"
				});
				*/
	        });
	        
			// 机动车注册登记证书抓拍
			$("#btnTakePhotoRegisterProof").click(function() {
	        	// 弹出高拍仪抓拍图片界面
				var parentValue = window.showModalDialog("vehicleRecycle/capture.jsp", "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
	        	
	        	//alert(parentValue.filepath);
	        	
	        	// 抓拍返回，设置图片路径到页面字段
	        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
	        		var files = parentValue.filepath.split(",");
	        		// 多张图片预览
	        		$("#registerProofFileImg").text("机动车注册登记证书(1)");
        			$("#registerProofFileImg").attr("href", basePath+'/'+files[0]);
	        		if (files.length > 1) {
	        			for (var i = 2 ; i <= files.length ; i ++) {
	        				var filepath = basePath + '/' + files[i-1];
	        				var _a = "&nbsp<a id='#registerProofFileImg"+i+"' href='"+filepath+"' target='_blank'>机动车注册登记证书("+i+")</a>";
	        				$("#registerProofFileImg").append(_a);
	        			}
	        		} 
	        		
        			// 设置隐藏字段传递到后台
    				$("input[name='registerProofFiles']").val(parentValue.filepath);
	        	}
	        });
			
	        // 行驶证抓拍
			$("#btnTakePhotoVehicleLicense").click(function() {
	        	// 弹出高拍仪抓拍图片界面
				var parentValue = window.showModalDialog("vehicleRecycle/capture.jsp", "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
	        	
	        	//alert(parentValue.filepath);
	        	
	        	// 抓拍返回，设置图片路径到页面字段
	        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
	        		var files = parentValue.filepath.split(",");
	        		// 多张图片预览
	        		$("#vehicleLicenseFileImg").text("行驶证(1)");
        			$("#vehicleLicenseFileImg").attr("href", basePath+'/'+files[0]);
	        		if (files.length > 1) {
	        			for (var i = 2 ; i <= files.length ; i ++) {
	        				var filepath = basePath + '/' + files[i-1];
	        				var _a = "&nbsp<a id='#vehicleLicenseFileImg"+i+"' href='"+filepath+"' target='_blank'>行驶证("+i+")</a>";
	        				$("#vehicleLicenseFileImg").append(_a);
	        			}
	        		} 
	        		
        			// 设置隐藏字段传递到后台
    				$("input[name='vehicleLicenseFiles']").val(parentValue.filepath);
	        	}
	        });
			
	        Callback.onSubmit = function () {
	        	//jquery easyui校验表单提交
	        	var callbackProofFile = $("input[name='callbackProofFile']").val();
        		if (callbackProofFile == "") {
        			alert("请上传报废回收证明文件！");
        			return false;
        		}
	        	return true;
	        }
	        
		});
		
	</script>

</body>
</html>