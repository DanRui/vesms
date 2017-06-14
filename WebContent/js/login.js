$(function(){
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
})