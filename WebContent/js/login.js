$(function(){
	// 页面初始化完成，用户名获得焦点
	$("input[name='username']").focus();
	
	// 绑定回车键入事件
	document.onkeydown = function(event) {
		var ev = document.all ? window.event : event;
		if(ev.keyCode == 13) {
			$("form").submit();
		}
	};
	
	$("form").submit(function(){
		var valid = true;
		
		$.each($("form input:visible"),function(i,o){
			var val = $(o).val();
			
			if(null == val
					|| "" == $.trim(val)){
				var msg = $(o).prev("span").text().replace(/\s+/g,"").split("：")[0];
				
				msg = "请输入" + msg;
				
				if($("div.login-error").children("ul.actionMessage")){
					$("div.login-error").children("ul.actionMessage").remove();
				}
				
				$("div.login-error").children("label").text(msg);
				
				valid = false;
				
				return false;
			}
		});
		
		if(!valid){
			return false;
		}
		
		$("div.login-error").children("label").text("");
		
		$("input:password").val(hex_md5($("input:password").val()));
	});
	
	$("a.logining").click(function(e){
		e.preventDefault();
		
		$("form").submit();
	});
	
	$("a.look_for").click(function(e){
		e.preventDefault();
		
		alert("请在公共基础平台自行修改密码！");
	});
})