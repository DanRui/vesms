//关闭窗口前清理高拍仪资源
function clearCaptureRes() {
	Unload();
	$("#common-dialog").dialog("options").onBeforeClose = null;
	return true;
}

/*//关闭窗口后清空绑定的关闭前处理事件
function clearBeforeCloseFunc() {
	$("#common-dialog").dialog("options").onBeforeClose=null;
}*/