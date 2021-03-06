<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'index.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>

<body>

	<div id="webcam"></div>
	<div class="btn">
		<input type="button" value="删除" id="delBtn" onclick="delPhoto();" />
		<input type="button" value="拍照" id="saveBtn" onclick="savePhoto();" />
	</div>
	<div id="photos">
		<img src="" id="img">
	</div>

	<script type="text/javascript" src="/js/jquery.webcam.min.js"></script>
	<script type="text/javascript">
    $(function () {
    	var basePath = "<%=path%>";
        var w = 320, h = 240;
        var pos = 0, ctx = null, saveCB, image = [];
 
        var canvas = document.createElement("canvas");
        canvas.setAttribute('width', w);
        canvas.setAttribute('height', h);
 
        console.log(canvas.toDataURL);
        if (canvas.toDataURL) {
 
            ctx = canvas.getContext("2d");
 
            image = ctx.getImageData(0, 0, w, h);
 
            saveCB = function(data) {
 
                var col = data.split(";");
                var img = image;
 
                for(var i = 0; i < w; i++) {
                    var tmp = parseInt(col[i]);
                    img.data[pos + 0] = (tmp >> 16) & 0xff;
                    img.data[pos + 1] = (tmp >> 8) & 0xff;
                    img.data[pos + 2] = tmp & 0xff;
                    img.data[pos + 3] = 0xff;
                    pos+= 4;
                }
 
                if (pos >= 4 * w * h) {
                    ctx.putImageData(img, 0, 0);
                    /* $.post("servlet/CatD", {type: "data", image: canvas.toDataURL("image/png")}, function(msg){
                        console.log("===="+eval(msg));
                        pos = 0;
                        $("#img").attr("src", msg+"");
                    }); */
                    $.ajax({
			             type: "post",
			             url: basePath+"/vehicleRecycle/fileUpload.do?t="+new Date().getTime(),
			             data: {type: "pixel", image: canvas.toDataURL("image/png")},
			             dataType: "html",
			             success: function(data){
			                        console.log("===="+data);
			                        pos = 0;
			                        $("#img").attr("src", "");
			                        $("#img").attr("src", data);
			                 }
			         });
                }
            };
 
        } else {
 
            saveCB = function(data) {
                image.push(data);
 
                pos+= 4 * w;
 
                if (pos >= 4 * w * h) {
                    /* $.post("servlet/CatD", {type: "pixel", image: image.join('|')}, function(msg){
                        console.log("+++++"+eval(msg));
                        pos = 0;
                        $("#img").attr("src", msg+"");
                    }); */
                    
                     $.ajax({
			             type: "post",
			             url: basePath+"/vehicleRecycle/fileUpload.do?",
			             data: {type: "pixel", image: image.join('|')},
			             dataType: "json",
			             success: function(data){
			                        console.log("+++++"+eval(msg));
			                        pos = 0;
			                        $("#img").attr("src", msg+"");
			                    }
			         });
                }
            };
        }
 
        $("#webcam").webcam({
            width: w,
            height: h,
            mode: "callback",
            swffile: "js/jscam_canvas_only.swf",
 
            onSave: saveCB,
 
            onCapture: function () {
                webcam.save();
            },
 
            debug: function (type, string) {
                console.log(type + ": " + string);
            }
        });
    });
 
    //拍照
    function savePhoto(){
        webcam.capture();
    }
     
    /*$(function () {
    var image = new Array();
    var w = 320, h = 240;
    var pos = 0;
    $("#webcam").webcam({
        width: w,
        height: h,
        mode: "callback",
        swffile: "${ctxStatic }/jquery-plugin/jscam_canvas_only.swf", // canvas only doesn't implement a jpeg encoder, so the file is much smaller
 
        onTick: function (remain) {
            if (0 == remain) {
                jQuery("#status").text("Cheese!");
            } else {
                jQuery("#status").text(remain + " seconds remaining...");
            }
        },
 
        onSave: function (data) {
            // Work with the picture. Picture-data is encoded as an array of arrays... Not really nice, though =/
            image.push(data);
            pos += 4 * w;
            if (pos == 4 * w * h) {
                $.post("${ctx}/common/webcam/uploadPhoto", {w: w, h: h, image: image.join('|')}, function (msg) {
                    pos = 0;
                    image = new Array();
                    $("#img").attr("src", "${ctx}/"+msg);
                });
            }
        },
 
        onCapture: function () {
            webcam.save();
            // Show a flash for example
        },
 
        debug: function (type, string) {
            // Write debug information to console.log() or a div, ...
        },
 
        onLoad: function () {
            // Page load
            var cams = webcam.getCameraList();
            for (var i in cams) {
                jQuery("#cams").append("<li>" + cams[i] + "</li>");
            }
        }
    });
});*/
</script>
</body>
</html>