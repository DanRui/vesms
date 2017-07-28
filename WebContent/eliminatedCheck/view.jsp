<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored = "false" %>
<%
String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
 		<div id="eliminated-check-view" class="datagrid-header">
			<table class="datagrid-table-s datagrid-htable">
			<tr class="datagrid-header-row">
				<td class="view_table_left">车主类型：</td>
				<td class="view_table_right">
					<c:if test="${v.isPersonal eq 'Y'}">
						自然人
					</c:if>
					<c:if test="${v.isPersonal eq 'N'}">
						企业
					</c:if>
				</td>
				<td class="view_table_left">办理类型：</td>
				<td class="view_table_right">
					<c:if test="${v.isProxy == 'Y'}">
						自办
					</c:if>
					<c:if test="${v.isProxy == 'N'}">
						代理
					</c:if>
				</td>
				<td align="center" colspan="2">
					<a id="btnCheck" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-check-apply'">审核</a>
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
				<td class="view_table_left" style="width: 110px">初次登记日期：</td>
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
				<td class="view_table_left" style="width: 120px">提前报废时长（天）：</td>
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
				<td class="view_table_left" style="width: 110px">车主身份证明号码：</td>
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
			<tr class="datagrid-header-row classify-tr">
					<td colspan="6">证明材料</td>
			</tr>						
			<c:if test="${!empty callbackFiles && !empty vehicleCancelProofFiles}">
			<tr class="datagrid-row">
				<td class="view_table_left">报废汽车回收证明：</td>
				<c:if test="${!empty callbackFiles}">
				<td class="view_table_right">
					<c:forEach items="${callbackFiles}" var="callbackFile" varStatus="status">
						<a href="javascript:void(0)" onclick="preview('callbackProof', '${status.count}')">${callbackFile.name}</a>
						<input type="hidden" name="callbackProofFileImg" value="${callbackFile.filePath}"/>
					</c:forEach>
				</td>
				</c:if>
				<td class="view_table_left">机动车注销证明：</td>
				<c:if test="${!empty vehicleCancelProofFiles}">
				<td class="view_table_right">
					<c:forEach items="${vehicleCancelProofFiles}" var="vehicleCancelProofFile" varStatus="status">
						<a href="javascript:void(0)" onclick="preview('vehicleCancelProof', '${status.count}')">${vehicleCancelProofFile.name}</a>
						<input type="hidden" name="vehicleCancelProofFileImg" value="${vehicleCancelProofFile.filePath}"/>
					</c:forEach>
				</td>
				</c:if>
			</tr>
			</c:if>
			<c:if test="${!empty vehicleOwnerProofFiles && !empty bankCardFiles}">
			<tr class="datagrid-row">
				<td class="view_table_left">车主身份证明：</td>
				<c:if test="${!empty vehicleOwnerProofFiles}">
				<td class="view_table_right">
					<c:forEach items="${vehicleOwnerProofFiles}" var="vehicleOwnerProofFile" varStatus="status">
						<c:if test="${status.index % 2 eq 1}">
							<a href="javascript:void(0)" onclick="preview('vehicleOwnerProof', '${status.count}')">${vehicleOwnerProofFile.name}</a></br>
							<input type="hidden" name="vehicleOwnerProofFileImg" value="${vehicleOwnerProofFile.filePath}"/>
						</c:if>
						<c:if test="${status.index % 2 eq 0}">
							<a href="javascript:void(0)" onclick="preview('vehicleOwnerProof', '${status.count}')">${vehicleOwnerProofFile.name}</a>
							<input type="hidden" name="vehicleOwnerProofFileImg" value="${vehicleOwnerProofFile.filePath}"/>
						</c:if>
					</c:forEach>
				</td>
				</c:if>
				<td class="view_table_left">银行卡：</td>
				<c:if test="${!empty bankCardFiles}">
				<td class="view_table_right">
					<c:forEach items="${bankCardFiles}" var="bankCardFile" varStatus="status">
						<a href="javascript:void(0)" onclick="preview('bankCard', '${status.count}')">${bankCardFile.name}</a>
						<input type="hidden" name="bankCardFileImg" value="${bankCardFile.filePath}"/>
					</c:forEach>
				</td>
				</c:if>
			</tr>
			</c:if>
			<c:if test="${v.isPersonal == 'N'}">
			<tr class="datagrid-row">
				<td class="view_table_left">非财政供养单位证明：</td>
				<c:if test="${!empty noFinanceProvideFiles}">
				<td class="view_table_right">
					<c:forEach items="${noFinanceProvideFiles}" var="noFinanceProvideFile" varStatus="status">
						<a href="javascript:void(0)" onclick="preview('noFinanceProvide', '${status.count}')">${noFinanceProvideFile.name}</a>
						<input type="hidden" name="noFinanceProvideFileImg" value="${noFinanceProvideFile.filePath}"/>
					</c:forEach>
				</td>
				</c:if>
				<td class="view_table_left">开户许可证：</td>
				<c:if test="${!empty openAccPromitFiles}">
					<td class="view_table_right">
						<c:forEach items="${openAccPromitFiles}" var="openAccPromitFile" varStatus="status">
							<c:if test="${status.index % 2 eq 1}">
								<a href="javascript:void(0)" onclick="preview('openAccPromit', '${status.count}')">${openAccPromitFile.name}</a></br>
								<input type="hidden" name="openAccPromitFileImg" value="${openAccPromitFile.filePath}"/>
							</c:if>
							<c:if test="${status.index % 2 eq 0}">
								<a href="javascript:void(0)" onclick="preview('openAccPromit', '${status.count}')">${openAccPromitFile.name}</a>
								<input type="hidden" name="openAccPromitFileImg" value="${openAccPromitFile.filePath}"/>
							</c:if>
						</c:forEach>
					</td>
				</c:if>
			</tr>
			</c:if>
			<c:if test="${v.isProxy == 'N'}">
			<tr class="datagrid-row">
				<td class="view_table_left">代理委托书：</td>
				<c:if test="${!empty agentProxyFiles}">
				<td class="view_table_right">
					<c:forEach items="${agentProxyFiles}" var="agentProxyFile" varStatus="status">
						<a href="javascript:void(0)" onclick="preview('agentProxy', '${status.count}')">${agentProxyFile.name}</a>
						<input type="hidden" name="agentProxyFileImg" value="${agentProxyFile.filePath}"/>
					</c:forEach>
				</td>
				</c:if>
				<td class="view_table_left">代理人身份证：</td>
				<c:if test="${!empty agentProofFiles}">
				<td class="view_table_right">
					<c:forEach items="${agentProofFiles}" var="agentProofFile" varStatus="status">
						<a href="javascript:void(0)" onclick="preview('agentProof', '${status.count}')">${agentProofFile.name}</a>
						<input type="hidden" name="agentProofFileImg" value="${agentProofFile.filePath}"/>
					</c:forEach>
				</td>
				</c:if>
			</tr>
			</c:if>
			<c:if test="${!empty signedApplyFiles}">
				<tr class="datagrid-row">
					<td class="view_table_left">签字确认的受理表：</td>
					<td class="view_table_right">
						<c:forEach items="${signedApplyFiles}" var="signedApplyFile" varStatus="status">
							<c:if test="${status.index % 2 eq 1}">
								<a href="javascript:void(0)" onclick="preview('signedApply', '${status.count}')">${signedApplyFile.name}</a></br>
								<input type="hidden" name="signedApplyFileImg" value="${signedApplyFile.filePath}"/>
							</c:if>
							<c:if test="${status.index % 2 eq 0}">
								<a href="javascript:void(0)" onclick="preview('signedApply', '${status.count}')">${signedApplyFile.name}</a>
								<input type="hidden" name="signedApplyFileImg" value="${signedApplyFile.filePath}"/>
							</c:if>
						</c:forEach>
					</td>
				</tr>
			</c:if>
			<c:if test="${!empty accountChangeProofFiles}">
			<tr class="datagrid-row">
				<td class="view_table_left">补贴对象变更证明材料：</td>
				<td class="view_table_right">
					<c:forEach items="${accountChangeProofFiles}" var="accountChangeProofFile" varStatus="status">
						<c:if test="${status.index % 2 eq 1}">
							<a href="javascript:void(0)" onclick="preview('accountChangeProof', '${status.count}')">${accountChangeProofFile.name}</a></br>
							<input type="hidden" name="accountChangeProofFileImg" value="${accountChangeProofFile.filePath}"/>
						</c:if>
						<c:if test="${status.index % 2 eq 0}">
							<a href="javascript:void(0)" onclick="preview('accountChangeProof', '${status.count}')">${accountChangeProofFile.name}</a>
							<input type="hidden" name="accountChangeProofFileImg" value="${accountChangeProofFile.filePath}"/>
						</c:if>
					</c:forEach>
				</td>
			</tr>
			</c:if>
			<tr>
			<td colspan="6" style="vertical-align:text-top;">
			<table id="table-action-log" width="100%;">		
			<tr class="datagrid-header-row classify-tr">
				<td colspan="6">业务流水记录</td>
			</tr>	
			<tr class="datagrid-row" bgcolor="#d7ebf9" style="text-align:center;">
				<th>操作岗位</th>
				<th>操作动作</th>
				<th>发生时间</th>
				<th>处理人</th>
				<th>操作结果</th>
				<th width="25%">详情说明</th>
			</tr>
			<c:forEach items="${actionLogs}" var="log">
				<tr align="center">
					<td>
						<c:choose>
							<c:when test="${log.currentPost eq 'CKSLG'}">
								窗口受理岗
							</c:when>
							<c:when test="${log.currentPost eq 'YBSLXXXZG'}">
								一般信息修正岗
							</c:when>
							<c:when test="${log.currentPost eq 'BTDXXZG'}">
								补贴对象修正岗
							</c:when>
							<c:when test="${log.currentPost eq 'KJCSG'}">
								会计初审岗
							</c:when>
							<c:when test="${log.currentPost eq 'CKSHG'}">
								窗口审核岗
							</c:when>
							<c:when test="${log.currentPost eq 'KZSHG'}">
								科长审核岗
							</c:when>
							<c:when test="${log.currentPost eq 'CZSHG'}">
								处长审核岗
							</c:when>
							<c:when test="${log.currentPost eq 'KJFSG'}">
								会计复审岗
							</c:when>
							<c:when test="${log.currentPost eq 'BFSBG'}">
								拨付申报岗
							</c:when>
							<c:when test="${log.currentPost eq 'BFJGBJG'}">
								拨付结果标记岗
							</c:when>
							<c:when test="${log.currentPost eq 'YWBJG'}">
								业务办结岗
							</c:when>
							<c:otherwise>
								系统管理员
							</c:otherwise>
						</c:choose>
					</td>
					<td>${log.actionName}</td>
					<td><fmt:formatDate value="${log.actionTime}" type="time" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${log.actionUser}</td>
					<td>${log.actionResult}</td>
					<td>${log.actionDetail}</td>
				</tr>
			</c:forEach>
			</table>
			</td>
			</tr>
		</table>	
	</div>
	
	<script type="text/javascript">
		$(function() {
			var basePath = "<%=basePath%>";
			
			var id = ${v.id};
			var currentPost = '${v.currentPost}';
			
			// 点击审批按钮，触发事件
			$("#btnCheck").click(function() {
				openDialog({
					type : "check",
					title : "受理单审批",
					width : 600,
					height : 300,
					param: {reset:false, save:false,
							buttons : [{
							           id:"check-submit",
							           text:"提交",iconCls:"icon-save"}
							          ]
						   },
					maximizable : true,
					href : basePath+"/eliminatedCheck/checkView.do?ids="+id+"&currentPost="+currentPost
				});
			});
			
		});
	
	</script>
	<script type="text/javascript">
		// 根据证明材料种类获取页面保存的路径
		function getFilepath(type) {
			var filepath = "";
			$("input[name='"+type+"FileImg']").each(function() {
				filepath = filepath + $(this).val() + ",";
				
				// 设置当前<a>标签的路径
				//$(this).attr("href", "javascript:void(0)");
			});
			
			if (filepath != "" && filepath.indexOf(",") != -1) {
				filepath = filepath.substring(0, filepath.length - 1);
			}
			
			return filepath;
		}
		
		// 根据图片种类，预览图片
		function preview(type, index) {
			var basePath = "<%=basePath%>";
			// 获取页面所有图片路径，传递到新窗口
			var proof_files = window;
			
			// 报废回收证明
			proof_files.callbackProofFile = getFilepath("callbackProof");
			// 机动车注销证明
			proof_files.vehicleCancelProofFiles = getFilepath("vehicleCancelProof");
			// 银行卡
			proof_files.bankCardFiles = getFilepath("bankCard");
			// 车主身份证明
			proof_files.vehicleOwnerProofFiles = getFilepath("vehicleOwnerProof");
			// 开户许可证
			proof_files.openAccPromitFiles = getFilepath("openAccPromit");
			// 非财政供养单位证明
			proof_files.noFinanceProvideFiles = getFilepath("noFinanceProvide");
			// 代理人身份证
			proof_files.agentProofFiles = getFilepath("agentProof");
			// 代理委托书
			proof_files.agentProxyFiles = getFilepath("agentProxy");
			// 签字确认的受理表
			proof_files.signedApplyFiles = getFilepath("signedApply");
			// 补贴对象变更证明材料
			proof_files.accountChangeProofFiles = getFilepath("accountChangeProof");
			
			//alert("adadad");
			window.open(basePath + '/eliminatedApply/picturePreview.jsp?filetype='+type+"&index="+index,'证明材料预览','height=300,width=400,top=200,left=400,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
		}
		
	</script>	
</body>
</html>