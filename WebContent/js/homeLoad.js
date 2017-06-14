$(function(){
	
	$("#List-grid").datagrid({
		title : "我的通知公告",
		toolbar : "#List-grid-toolbar",
		border : false,
		fit : true,
		width : $(window).width() - 252,
		columns : [ [ {
			field : "ckb",
			checkbox : true
		}, {
			field : "title",
			title : "标题",
			width : "40%",
			halign : "center",
			align : "center",
			resizable : true
		}, {
			field : "inputTime",
			title : "收文时间",
			width : "30%",
			halign : "center",
			sortable : true ,
			align : "center",
			resizable : true
		}, {
			field : "readFlag",
			title : "阅读状态",
			width : "27%",
			align : "center",
			sortable : true ,
			resizable : false,
			styler: function(value,row,index){
				if("未阅读" == value){
					return "color:#666666";
				}
				
				if("已阅读" == value){
					return "color:#00BBFF";
				}
			}
		} ] ],
		//striped:true,
		fitColumns : true,
		//autoRowHeight:true,
		rownumbers : false,
		singleSelect : false,
		ctrlSelect : true,
		pagination : true,
		pageSize : 10,
		pageList : [ 5, 10, 15, 20, 25, 30 ],
		sortName : "inputTime",
		sortOrder : "desc",
		/* url : "data/noticeList.json", */
		url: "noticeInfo/noticeInfo_recvList.action",
		method : "post",
		loadMsg : "数据加载中...",
		onDblClickRow : function(rowIndex, rowData) {
			openDialog({
				type : "view",
				title : "收件箱查看",
				width : 800,
				height : 560,
				maximizable : false,
				/* href : "notice/recvView.jsp" */
				href : "noticeInfo/noticeInfo_view.action?id=" + rowData.id
			});
		},
		onRowContextMenu : function(e, rowIndex, rowData) {
			e.preventDefault();

			$(this).datagrid("unselectAll");

			$(this).datagrid("selectRow", rowIndex);

			$("#Read-menu").menu("show", {
				left : event.pageX,
				top : event.pageY
			});
		}
	}).datagrid("columnMoving").datagrid("columnHiding");
	
}) 