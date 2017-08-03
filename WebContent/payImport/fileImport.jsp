<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8"/>
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
          	<input name="upload" type="button" value="上传文件并自动标记" id="fileUpload"/>
      	  </td>
        </tr>
        </table>
           
       </form>  
       
       
        <script type="text/javascript">
     /*    function PostData(){
        	alert(2);
		    /* $("#importId").ajaxSubmit(function(data){
		    	alert(1);
		    	 if(data.success) {
   		 			Messager.alert({
						type : "info",
						title : "&nbsp;",
						content : data
					});
    			}else {
            		Messager.alert({
            			type:"error",
						title:"&nbsp;",
						content:data.message
					});
            	} 
		    }) */
		    /*
		    $("#importId").ajaxSubmit({
		    	url:'payImport/importExcel.do',
		    	type : 'POST',
	            dataType : 'json',
	            headers : {"ClientCallMode" : "ajax"}, //添加请求头部
	            success :function(data){
	            	alert(data.message);
	            }
	            
		    });
		    return false;
	    } */
        
		$(function() {
			$.ajaxSetup({
				  async: false
				});
         $("#fileUpload").click(function() {
        	  var fileName =$("input[name='excelFile']").val(); 
        	  var index = fileName.lastIndexOf(".");  
        	  var suffix = fileName.substring(index).toLowerCase();  
        	 if(fileName ==""){
        		 alert("请选择文件");
        		 return false;
        	 }
        	// 验证文件格式  
              else if(!(".xlsx" == suffix || ".xls" == suffix)) {  
				  alert("文件格式不对，请选择xls或xlsx格式文件！"); 
                  return false;  
              }  
			   $("#fileUpload").attr("disabled","true"); //设置变灰按钮  
			   //PostData(); 
         	   	$("#importId").form("submit", {
        			url: $("#importId").attr("action"),
        		 	success : function(data) {
        		 	data = $.parseJSON(data);	
        			if(data.success) {
       		 			Messager.alert({
							type : "info",
							title : "&nbsp;",
							content : data.message
						})
        			}else {
                		Messager.alert({
                			type:"error",
							title:"&nbsp;",
							content:data.message
						});
                	}
        			$("#payImport-list #payImport-grid").datagrid('load');
        		 }
         	   	})
	        });
         
		})
		</script>
       

</body>
</html>