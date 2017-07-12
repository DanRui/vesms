<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导入国库文件</title>
</head>
<body>
		<h1>导入Excel</h1>  
       <hr>  
       <form action="payImport/importExcel.do" method="post" enctype="multipart/form-data" id="importId">  
         <!--   <input type="file" name="importExcel" id="importExcel">  
           <input type="submit" value="导入">    -->
           
        <table border="1" width="450" cellpadding="4" cellspacing="2" bordercolor="#9BD7FF">
	        <tr>
	            <td width="100%" colspan="2">
	
	                            文件：<input name="excelFile" id="excelFile" size="30" type="file">
	
	            </td>
	        </tr>
        </table>
        <br/><br/>

        <table>
        <tr>
          <td align="center">
          	<input name="upload" type="submit" value="开始上传" id="fileUpload"/>
      	  </td>
        </tr>
        </table>
           
       </form>  
       
       
        <script type="text/javascript">
		$(function() {
         $("#fileUpload").click(function() {
			   
			   var file = $("input[name='excelFile']").val();
			   if (file == "") {
				   alert("请选择需要上传的文件");
				   return false;
			   } 
        	  	$("#importId").form("submit", {
        			url  : $(this).attr("action"),
        		 	success : function(result) {
        		 		var result = eval('(' + result + ')');
        		 		if (result.success) {
        		 			alert("文件上传成功！");
        		 		} else {
        		 			alert("文件上传失败！");
        		 			$("#excelFile").filebox("clear");
        		 		}
        			}
        	 	}); 
		/*	   $("#importId").form(function(data){
				   $("#fileUpload").attr("disabled","true"); 
        	 		alert(data);
        	 	}).submit(function()
        	 		{
        	 			return false;
        	 		}
        	 	);*/
	        });
         
		})
		</script>
       

</body>
</html>