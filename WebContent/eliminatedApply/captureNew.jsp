<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.*"%>
<%@ page import="com.jst.util.PropertyUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>	
<%
	String basePath = request.getContextPath();
	String vehiclePlateNum = request.getParameter("vehiclePlateNum");
	String isPersonal = request.getParameter("isPersonal");
	String isProxy = request.getParameter("isProxy");
	
	String uploadServer = PropertyUtil.getPropertyValue("uploadServer");
	String photoTmpDir = PropertyUtil.getPropertyValue("photoTmpDir");
%>

<%
	response.setHeader("Pragma", "No-cache");  
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	response.setContentType("text/html;charset=UTF-8"); 
%>
<%-- <%
//一个有中文内容的Cookie
String str = "我们都有一个家，名字叫中国！";
Cookie cookie = new Cookie("name", URLEncoder.encode(str, "UTF-8"));
response.addCookie(cookie);
 
//取出Cookie中的中文内容
Cookie [] cookies = request.getCookies();
String str = "";
for(int i = 0 ; i < cookies.length ; i++) {
    if(cookies[i].getName().equals("name")) {
        str = cookies[i].getValue();
    }
}
out.println(URLDecoder.decode(str, "UTF-8"));
%> --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
        <title>抓拍上传</title>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>/js/plugins/easyui/themes/ui-cupertino/easyui.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/plugins/easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/plugins/easyui/themes/extension.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/plugins/date/skin/WdatePicker.css">
		<link rel="stylesheet" href="<%=basePath%>/js/plugins/editor/themes/default/default.css" />
		<link rel="stylesheet" href="<%=basePath%>/js/plugins/editor/plugins/code/prettify.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/table.css">
		
        <script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.0.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/plugins/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/plugins/easyui/datagrid-detailview.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/plugins/easyui/jquery.easyui.extension.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/plugins/easyui/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/plugins/editor/plugins/code/prettify.js"></script>
        <script language="javascript" type="text/javascript">
        
        		// 项目路径
        		var basePath = "<%=basePath%>";
        		
        		// 定义全局号牌号码变量
        		var vehiclePlateNum = "B" + "<%=vehiclePlateNum%>";
        		
        		// 定义照片上传服务器地址
        		var uploadServer = "<%=uploadServer%>";
        
        
		        var DeviceMain;//主头
		        var DeviceAssist;//副头
		        var VideoMain;//主头
		        var VideoAssist;//副头
				var videoCapMain;
				var videoCapAssist;	
				
		        var PicPath;
				var initFaceDetectSuccess;
				var readIDcard = false;
				
		        function plugin()
                {
                    return document.getElementById('view1');
                }
	
                function MainView()
                {
                    return document.getElementById('view1');
                }
        
		        /* function AssistView()
                {
                    return document.getElementById('view2');
                } */
		
		        function thumb1()
                {
                    return document.getElementById('thumb1');
                }
		
		        function addEvent(obj, name, func)
                {
                    if (obj.attachEvent) {
                        obj.attachEvent("on"+name, func);
                    } else {
                        obj.addEventListener(name, func, false); 
                    }
                }
		        function OpenVideo()
		        {
					
		            OpenVideoMain();
			        OpenVideoAssist();
		        }
		        function CloseVideo()
		        {
		            CloseVideoMain();
			        CloseVideoAssist();
		        }
		        function CloseVideoMain()
		        {
		            if (VideoMain)
			        {
				        plugin().Video_Release(VideoMain);
				        VideoMain = null;
				
					    MainView().View_SetText("", 0);
			        }			
		        }
		        function CloseVideoAssist()
		        {
			        if (VideoAssist)
			        {
				        plugin().Video_Release(VideoAssist);
				        VideoAssist = null;

				        AssistView().View_SetText("", 0);
			        }
		        }
		
		        function OpenVideoMain()
		        {
		            CloseVideoMain();

		            if (!DeviceMain)
		                return;
			
			        /* var sSubType = document.getElementById('subType1'); 								
			        var sResolution = document.getElementById('selRes1'); 	
				
			        var SelectType = 0;
			        var txt;
			        if(sSubType.options.selectedIndex != -1)
			        {
				        txt = sSubType.options[sSubType.options.selectedIndex].text;
				        if(txt == "YUY2")
				        {
					        SelectType = 1;
				        }
				        else if(txt == "MJPG")
				        {
					        SelectType = 2;
				        }
				        else if(txt == "UYVY")
				        {
					        SelectType = 4;
				        }
			        } */
			
			        // var nResolution = sResolution.selectedIndex;
							
			        VideoMain = plugin().Device_CreateVideo(DeviceMain, 0, 1);
			        if (VideoMain)
			        {
					    MainView().View_SelectVideo(VideoMain);
					    MainView().View_SetText("高拍仪打开中，请等待...", 0);
							
			        }
		        }

		        /* function OpenVideoAssist()
		        {
		            CloseVideoAssist();

		            if (!DeviceAssist)
		                return;
			
			        var sSubType = document.getElementById('subType2'); 								
			        var sResolution = document.getElementById('selRes2'); 	
				
			        var SelectType = 0;
			        var txt;
			        if(sSubType.options.selectedIndex != -1)
			        {
				        txt = sSubType.options[sSubType.options.selectedIndex].text;
				        if(txt == "YUY2")
				        {
					        SelectType = 1;
				        }
				        else if(txt == "MJPG")
				        {
					        SelectType = 2;
				        }
				        else if(txt == "UYVY")
				        {
					        SelectType = 4;
				        }
			        }
			
			        var nResolution = sResolution.selectedIndex;
							
			        VideoAssist = plugin().Device_CreateVideo(DeviceAssist, nResolution, SelectType);
			        if (VideoAssist)
			        {
					    AssistView().View_SelectVideo(VideoAssist);
					    AssistView().View_SetText("打开视频中，请等待...", 0);													    							
			        }	
		        } */
		
		        function changesubTypeMain()
		        {
			        if (DeviceMain)
			        {	
				        var sSubType = document.getElementById('subType1'); 								
				        var sResolution = document.getElementById('selRes1'); 	
				        var SelectType = 0;
				        var txt;
				        if(sSubType.options.selectedIndex != -1)
				        {
					        var txt = sSubType.options[sSubType.options.selectedIndex].text;
					        if(txt == "YUY2")
					        {
						        SelectType = 1;
					        }
					        else if(txt == "MJPG")
					        {
						        SelectType = 2;
					        }
					        else if(txt == "UYVY")
					        {
						        SelectType = 4;
					        }
				        }
									
				        var nResolution = plugin().Device_GetResolutionCountEx(DeviceMain, SelectType);
				        sResolution.options.length = 0; 
				        for(var i = 0; i < nResolution; i++)
				        {
					        var width = plugin().Device_GetResolutionWidthEx(DeviceMain, SelectType, i);
					        var heigth = plugin().Device_GetResolutionHeightEx(DeviceMain, SelectType, i);
					        sResolution.add(new Option(width.toString() + "*" + heigth.toString())); 
				        }
				        sResolution.selectedIndex = 0;
			        }			
		        }

		        function changesubTypeAssist()
		        {
			        if (DeviceAssist)
			        {	
				        var sSubType = document.getElementById('subType2'); 								
				        var sResolution = document.getElementById('selRes2'); 	
				
				        var SelectType = 0;
				        var txt;
				        if(sSubType.options.selectedIndex != -1)
				        {
					        var txt = sSubType.options[sSubType.options.selectedIndex].text;
					        if(txt == "YUY2")
					        {
						        SelectType = 1;
					        }
					        else if(txt == "MJPG")
					        {
						        SelectType = 2;
					        }
					        else if(txt == "UYVY")
					        {
						        SelectType = 4;
					        }
				        }			
				
				        var nResolution = plugin().Device_GetResolutionCountEx(DeviceAssist, SelectType);
				        sResolution.options.length = 0; 
				        for(var i = 0; i < nResolution; i++)
				        {
					        var width = plugin().Device_GetResolutionWidthEx(DeviceAssist, SelectType, i);
					        var heigth = plugin().Device_GetResolutionHeightEx(DeviceAssist, SelectType, i);
					        sResolution.add(new Option(width.toString() + "*" + heigth.toString())); 
				        }
				        sResolution.selectedIndex = 0;
			        }			
		        }	
		
                function Load()
                {
			        //设备接入和丢失
			        //type设备类型， 1 表示视频设备， 2 表示音频设备
			        //idx设备索引
			        //dbt 1 表示设备到达， 2 表示设备丢失
                    addEvent(plugin(), 'DevChange', function (type, idx, dbt) 
					{
						if(1 == type)//视频设备
						{
							if(1 == dbt)//设备到达
							{
								var deviceType = plugin().Global_GetEloamType(1, idx);
								if(1 == deviceType)//主摄像头
								{
									if(null == DeviceMain)
									{
										DeviceMain = plugin().Global_CreateDevice(1, idx);										
										if(DeviceMain)
										{
											//alert(plugin().Device_GetState(DeviceMain));
											
											//document.getElementById('lab1').innerHTML = plugin().Device_GetFriendlyName(DeviceMain);
											
											/* var sSubType = document.getElementById('subType1');
											sSubType.options.length = 0;
											var subType = plugin().Device_GetSubtype(DeviceMain);
											if (subType & 1) 
											{
												sSubType.add(new Option("YUY2"));
											}
											if (subType & 2) 
											{
												sSubType.add(new Option("MJPG"));
											}
											if (subType & 4) 
											{
												sSubType.add(new Option("UYVY"));
											}
											
											sSubType.selectedIndex = 0;
											changesubTypeMain(); */
											
											OpenVideoMain();
										}
									}
								}
							}
							else if(2 == dbt)//设备丢失
							{
								if (DeviceMain) 
								{
                                    if (plugin().Device_GetIndex(DeviceMain) == idx) 
									{
                                        CloseVideoMain();
                                        plugin().Device_Release(DeviceMain);
                                        DeviceMain = null;
										
										//document.getElementById('lab1').innerHTML = "";
										/* document.getElementById('subType1').options.length = 0; 
										document.getElementById('selRes1').options.length = 0; */
                                    }
                                }
							}
						}
                    });

			        addEvent(plugin(), 'IdCard', function(ret)
			        {
				        if (1 == ret)
				        {
					        var str = GetTimeString() + "：";
					
					        for(var i = 0; i < 16; i++)
					        {
						        str += plugin().Global_GetIdCardData(i + 1);
						        str += ";";				
					        }
				
					        /* document.getElementById("idcard").value=str;	
							
							var image = plugin().Global_GetIdCardImage(1);//1表示头像， 2表示正面， 3表示反面 ...
							plugin().Image_Save(image, "C:\\idcard.jpg", 0);
							plugin().Image_Release(image);
							
							document.getElementById("idcardimg").src= "C:\\idcard.jpg"; */
				        }
			        });
				
			        //var title = document.title;
			        //document.title = title + plugin().version;
				
			        MainView().Global_SetWindowName("view");
			        thumb1().Global_SetWindowName("thumb");
		
			        var ret;
			        // 初始化高拍仪
					ret = plugin().Global_InitDevs();
					if(ret)
					{
						//进行人脸识别初始化时，视频应处于关闭状态
						 plugin().InitFaceDetect();
					}
					
					if( !plugin().Global_VideoCapInit())
					{
						alert("初始化失败！");
					}
                }
		
		        function Unload()
		        {
		        	if (null != plugin()) {
				        if (VideoMain)
				        {
					        MainView().View_SetText("", 0);
					        plugin().Video_Release(VideoMain);
					        VideoMain = null;
				        }
				        if(DeviceMain)
				        {
					        plugin().Device_Release(DeviceMain);
					        DeviceMain = null;		
				        }
		        	}
			
			        // 关闭页面，不释放高拍仪资源，避免多次来回使用高拍仪
			        //plugin().Global_DeinitDevs();
					
					//进行人脸识别反初始化时，视频应处于关闭状态
					//plugin().DeinitFaceDetect();
		        }
		        /* function EnableDate(obj)
		        {
			        if (obj.checked)
			        {
				        var offsetx = 1000;
				        var offsety = 60;
				
				        var font;
				        font = plugin().Global_CreateTypeface(50, 50, 0, 0, 2, 0, 0, 0, "宋体");
				
				        if (VideoMain)
				        {
					        var width = plugin().Video_GetWidth(VideoMain);
					        var heigth = plugin().Video_GetHeight(VideoMain);
					
					        plugin().Video_EnableDate(VideoMain, font, width - offsetx, heigth - offsety, 0xffffff, 0);
				        }
				        if (VideoAssist)
				        {
					        var width = plugin().Video_GetWidth(VideoAssist);
					        var heigth = plugin().Video_GetHeight(VideoAssist);	
					
					        plugin().Video_EnableDate(VideoAssist, font, width - offsetx, heigth - offsety, 0xffffff, 0);
				        }
				        plugin().Font_Release(font);
			        }
			        else
			        {
				        if(VideoMain)
				        {
					        plugin().Video_DisableDate(VideoMain);
				        }
				        if(VideoAssist)
				        {
					        plugin().Video_DisableDate(VideoAssist);
				        }
			        }
		        }
		
		        function AddText(obj)
		        {
			        if (obj.checked)
			        {			
				        var font;
				        font = plugin().Global_CreateTypeface(200, 200, 0, 0, 2, 0, 0, 0, "宋体");
				
				        if (VideoMain)
				        {				
					        plugin().Video_EnableAddText(VideoMain, font, 0, 0, "文字水印", 65280, 150);
				        }
				        if (VideoAssist)
				        {
					        plugin().Video_EnableAddText(VideoAssist, font, 0, 0, "文字水印", 65280, 150);
				        }
				        plugin().Font_Release(font);
			        }
			        else
			        {
				        if(VideoMain)
				        {
					        plugin().Video_DisableAddText(VideoMain);
				        }
				        if(VideoAssist)
				        {
					        plugin().Video_DisableAddText(VideoAssist);
				        }
			        }		
		        }
		
		        function ShowProperty()
		        {
			        if(DeviceMain)
			        {
				        plugin().Device_ShowProperty(DeviceMain, MainView().View_GetObject());
			        }
		        }
		
		        function Deskew(obj)
		        {
			        if (obj.checked)
			        {
				        if(VideoMain)
				        {
					        plugin().Video_EnableDeskewEx(VideoMain, 1);
				        }
				        if(VideoAssist)
				        {
					        plugin().Video_EnableDeskewEx(VideoAssist, 1);
				        }
			        }
			        else
			        {
				        if(VideoMain)
				        {
					        plugin().Video_DisableDeskew(VideoMain);
				        }
				        if(VideoAssist)
				        {
					        plugin().Video_DisableDeskew(VideoAssist);	
				        }		
			        }
		        }
		
		        function SetState(obj)
		        {
					if (obj.checked)
					{
						MainView().View_SetState(2);
						document.getElementById('scansize').disabled="disabled"; 
					}
					else
					{
						MainView().View_SetState(1);
						document.getElementById('scansize').disabled=""; 
					}
		        }
				function OpenVerifyFacRect(obj)
				{
					if(!initFaceDetectSuccess)
					{
						alert("人脸初始化失败，机型不支持！");
						obj.checked = false;
					}
					else
					{
						if (VideoMain)
						{
							if (obj.checked)
								plugin().Global_EnableFaceRectCrop(VideoMain, 1);
							else
								plugin().Global_DisableFaceRectCrop(VideoMain);
						}
						if (VideoAssist)
						{
							if (obj.checked)
								plugin().Global_EnableFaceRectCrop(VideoAssist, 1);
							else
								plugin().Global_DisableFaceRectCrop(VideoAssist);
						}
					}
				}
				function changescansize()
				{
					var rect;
					var width =  plugin().Video_GetWidth(VideoMain);
					var heigth =  plugin().Video_GetHeight(VideoMain);	
					
					var s = document.getElementById('scansize'); 									
					var size = s.options.selectedIndex;
					
					if(size == 0)
					{
						MainView().View_SetState(1);//取消框选 状态											
					}
					else if(size == 1)
					{
						rect = plugin().Global_CreateRect(width/2 - width/4, heigth/2 - heigth/4, width/2, heigth/2);
						MainView().View_SetState(2);//小尺寸
						MainView().View_SetSelectedRect(rect);						

					}
					else if(size == 2)
					{
						rect = plugin().Global_CreateRect(width/2 - width/6, heigth/2 - heigth/6, width/3, heigth/3);
						MainView().View_SetState(2);//中尺寸
						MainView().View_SetSelectedRect(rect);						
					}
					
					if(size != 0)
					{
						document.getElementById('SetState').checked = false; 
						document.getElementById('SetState').disabled="disabled"; 
					}
					else
					{
						document.getElementById('SetState').disabled = ""
					}
				} */
		
		        function Left()
		        {
			        if(VideoMain)
			        {
				        plugin().Video_RotateLeft(VideoMain);
			        }
			        /* if(VideoAssist)
			        {
				        plugin().Video_RotateLeft(VideoAssist);	
			        } */	

		        }
		
		        function Right()
		        {
			        if(VideoMain)
			        {
				        plugin().Video_RotateRight(VideoMain);
			        }
			        /* if(VideoAssist)
			        {
				        plugin().Video_RotateRight(VideoAssist);		
			        } */	

		        }
		
				function GetTimeString()
				{
					var date = new Date();
					var yy = date.getFullYear().toString();
					var mm = (date.getMonth() + 1).toString();
					var dd = date.getDate().toString();
					var hh = date.getHours().toString();
					var nn = date.getMinutes().toString();
					var ss = date.getSeconds().toString();
					var mi = date.getMilliseconds().toString();
					
					var ret = yy + mm + dd + hh + nn + ss + mi;
					return ret;
				}
		
                //拍照
		        function Scan(fileType)
		        {
                	// 判断是否打开高拍仪
		            if (VideoMain) 
                    {
		            	// 清空对应图片路径
		            	// 每次点击拍照时，清空对应证明材料图片路径
		            	//clearFiles(fileType);
		            	
		                var imgList = plugin().Video_CreateImageList(VideoMain, 0, 0);
		                if (imgList) {
		                    var len = plugin().ImageList_GetCount(imgList);
		                    for (var i = 0; i < len; i++) {
		                        var img = plugin().ImageList_GetImage(imgList, i);
		                        var Name = "D:\\LJCBT_PHOTO\\" + vehiclePlateNum + "_" + GetTimeString() + ".jpg";
		                        var b = plugin().Image_Save(img, Name, 0);
		                        if (b) {
		                            MainView().View_PlayCaptureEffect();
		                            thumb1().Thumbnail_Add(Name);

		                            PicPath = Name;
		                            
		                            // 每次点击拍照，自动复选框勾选文件
		                            var index = thumb1().Thumbnail_GetCount();
		                            //alert(index);
		                            //alert(thumb1().Thumbnail_GetCheck(index));
		                            if (!thumb1().Thumbnail_GetCheck(index)) {
		                            	thumb1().Thumbnail_SetCheck(index, true);
		                            }
		                        }

		                        plugin().Image_Release(img);
		                    }

		                    plugin().ImageList_Release(imgList);
		                }
		            }
		        }
			
		        /* function OCR()
		        {
			        if (PicPath)
			        {
				        plugin().Global_InitOcr();
				
				        var img;
				        //img = plugin().Global_CreateImageFromFile(PicPath, 0);
				        img = plugin().Global_CreateImageFromFile("C:\\1.jpg", 0);
				        alert(img);
				        plugin().Global_DiscernOcr(1, img);
				        var b = plugin().Global_WaitOcrDiscern();
				        if (b)
				        {
					        alert(plugin().Global_GetOcrPlainText(0));
					        plugin().Global_SaveOcr("C:\\1.doc", 0);
				        }
				        else 
				        {
				        	alert("识别失败！");
				        }
				
				        plugin().Global_DeinitOcr();
				
				        plugin().Image_Release(img);
			        }	
			        else
			        {
				        alert("请先拍照！");
			        }
		        } */
		
		        /* function UploadToHttpServer()
		        {

			        //var http = plugin().Global_CreateHttp("http://192.168.1.56:80/upload.asp");//asp服务器demo地址
			        var http = plugin().Global_CreateHttp("http://192.168.1.56:8080/FileStreamDemo/servlet/FileSteamUpload?");//java服务器demo地址
			        if (http)
			        {
				        var b = plugin().Http_UploadImageFile(http, "C:\\1.jpg", "2.jpg");
				        if (b)
				        {
					        alert("上传成功");
				        }
				        else
				        {
					        alert("上传失败");
				        }
				
				        plugin().Http_Release(http);
			        }
			        else
			        {
				        alert("url 错误");
			        }
		        } */
		        
		        // 上传图片到服务器后台处理
				function UploadThumbToServer(fileType, name)
				{	
		        	//alert(fileType);
					//http://localhost:8080/vesmsDemo/vehicleRecycle/fileCaptureUpload.do
					//http://localhost:8080/vesmsDemo/servlet/FileSteamUpload?
							
					//fileCaptureUpload
					//alert(thumb1());
					//alert(thumb1().Thumbnail_GetObject());
					var http = thumb1().Thumbnail_HttpUploadCheckImage(uploadServer + "/eliminatedApply/fileCaptureUpload.do?t="+GetTimeString(), 1);
					if(http)
					{
						var htInfo = thumb1().Thumbnail_GetHttpServerInfo();
						//alert(htInfo);
						var msgs = [];
						msgs = htInfo.split("##");
						if (msgs != "") {
							var str = "";
							for (var i = 0 ; i < msgs.length - 1 ; i ++) {
								var msg = msgs[i];
								msg = $.parseJSON(msg);
								//msg = eval('('+ msg + ')');
								// 判断每张图片是否上传成功
								if (msg.success) {
									// 上传图片成功，保存图片路径到页面
									str = str + msg.message.file + ",";
									
									// 文件上传成功，清空该类文件缩略图
									clearFiles(fileType);
								} else {
									// 任意一张图片上传失败，返回重新提交
									alert(msg.message);
									return false;
								}
							}
							// 设置图片路径回显和页面隐藏字段值
							setProofFileImgPreview(str.substring(0, str.length - 1).split(","), fileType, name);
							//$("input[name='"+fileType+"']").val(str.substring(0, str.length - 1));
							//alert("图片上传成功！");
						} else {
							alert("图片上传失败！");
						}	
					}
					else
					{
						alert("上传失败！");
					}
				}
		        
		        function ScanToHttpServer()
		        {
			        if(VideoMain)
			        {
				        var img = plugin().Video_CreateImage(VideoMain, 0, MainView().View_GetObject());
				        if (img)
				        {
					        //var http = plugin().Global_CreateHttp("http://192.168.1.193:8080/upload.asp");//asp服务器demo地址
					        var http = plugin().Global_CreateHttp("http://localhost:8080/vesmsDemo/vehicleRecycle/fileCaptureUpload.do");//java服务器demo地址
					        if (http)
					        {
						        var b = plugin().Http_UploadImage(http, img, 2, 0, "2.jpg");
						        if (b)
						        {
							        alert("上传成功");
						        }
						        else
						        {
							        alert("上传失败");
						        }
						
						        plugin().Http_Release(http);
					        }

					        plugin().Image_Release(img);
				        }
			        }
		        }
				
				function RGB(r, g, b)
				{
					return r | g<<8 | b<<16;
				}
				
				/******************录像********************/
		        /* function StartMainRecord()
		        {
					if(VideoMain)
					{
						var videoOutputWidth = plugin().Video_GetWidth(VideoMain);
						var videoOutputHeight = plugin().Video_GetHeight(VideoMain);
						//录像时，打开视频的分辨率越低，帧率越高,一般不超过200w像素
						//所设置的帧率应尽可能高于实际帧率，避免丢帧
						var FrameRate = 15;//此参数可根据录像分辨率与机型实际帧率调节
						var CheckWidth = 1600;
						var CheckHeight = 1200;
						//主流视频分辨率少有大于200万的，因此为节约cpu资源，当分辨率大于200w，应采用200w的配置录制
						if (videoOutputWidth * videoOutputHeight > (CheckWidth * CheckHeight))
						{
							if(confirm("当前分辨率过高，切换到200万像素时，录制效果最佳！\r\n点击'确定'，手动切换到1600X1200或相近分辨率后再次尝试\r\n点击'取消'，本次录制继续"))
							{
								return;
							}
							videoOutputWidth = CheckWidth;
							videoOutputHeight = CheckHeight;
						}
					
						if(	videoCapMain)
						{
							plugin().VideoCap_Stop(videoCapMain);
							plugin().VideoCap_Destroy(videoCapMain);
						}
						
						videoCapMain = plugin().Global_CreatVideoCap();
						if(null == videoCapMain)
						{
							alert("创建录像对象失败");
							return;
						}
						
						//设置水印
						plugin().VideoCap_SetWatermark(videoCapMain, 1, 0, 0, 4, 100, 0, "深圳新良田科技", RGB(255,23,140), "Microsoft YaHei", 72, 50, 0);
						
						var selMicIdx = -1;
						if(plugin().Global_VideoCapGetAudioDevNum() > 0)//有麦克风
						{
							selMicIdx = 0;
						}
						
						if(!plugin().VideoCap_PreCap(videoCapMain, "C:\\eloamPlugin_main.mp4", selMicIdx, FrameRate, 1, videoOutputWidth, videoOutputHeight))
						{
							alert("录像设置失败");
							return;
						}
						
						if(!plugin().VideoCap_AddVideoSrc(videoCapMain, VideoMain))
						{
							alert("添加视频源失败");
							return;
						}
						
						if(!plugin().VideoCap_Start(videoCapMain))
						{
							alert("启动录像失败");
							return;
						}
						alert("已开始录像");
					}
		        }
		        function StopMainRecord()
		        {
					if(plugin().VideoCap_Stop(videoCapMain))
					{
						plugin().VideoCap_Destroy(videoCapMain);
						videoCapMain = null;
						alert("录像结束，文件保存于C:\\eloamPlugin_main.mp4\r\n若录像失败请选择较小的分辨率尝试");
					}
					else
					{
						alert("停止录像失败");
					}
		        }
				function StartAssistRecord()
		        {
					if(VideoAssist)
					{
						var videoOutputWidth = plugin().Video_GetWidth(VideoAssist);
						var videoOutputHeight = plugin().Video_GetHeight(VideoAssist);
						//录像时，打开视频的分辨率越低，帧率越高,一般不超过200w像素
						//所设置的帧率应尽可能高于实际帧率，避免丢帧
						var FrameRate = 15;//此参数可根据录像分辨率与机型实际帧率调节
						var CheckWidth = 1600;
						var CheckHeight = 1200;
						//主流视频分辨率少有大于200万的，因此为节约cpu资源，当分辨率大于200w，应采用200w的配置录制
						if (videoOutputWidth * videoOutputHeight > (CheckWidth * CheckHeight))
						{
							if(confirm("当前分辨率过高，切换到200万像素时，录制效果最佳！\r\n点击'确定'，手动切换到1600X1200或相近分辨率后再次尝试\r\n点击'取消'，本次录制继续"))
							{
								return;
							}
							videoOutputWidth = CheckWidth;
							videoOutputHeight = CheckHeight;
						}
					
						if(	videoCapAssist)
						{
							plugin().VideoCap_Stop(videoCapAssist);
							plugin().VideoCap_Destroy(videoCapAssist);
						}
						
						videoCapAssist = plugin().Global_CreatVideoCap();					
						if(null == videoCapAssist)
						{
							alert("创建录像对象失败");
							return;
						}
						
						//设置水印
						plugin().VideoCap_SetWatermark(videoCapAssist, 1, 0, 0, 1, 220, 0, "深圳新良田科技", RGB(255,23,140), "Microsoft YaHei", 72, 50, 0);
						
						var selMicIdx = -1;
						if(plugin().Global_VideoCapGetAudioDevNum() > 0)//有麦克风
						{
							selMicIdx = 0;
						}
						
						if(!plugin().VideoCap_PreCap(videoCapAssist, "C:\\eloamPlugin_assist.mp4", selMicIdx, FrameRate, 1, videoOutputWidth, videoOutputHeight))
						{
							alert("启动录像失败");
							return;
						}
						
						if(!plugin().VideoCap_AddVideoSrc(videoCapAssist, VideoAssist))
						{
							alert("添加视频源失败");
							return;
						}
						
						if(!plugin().VideoCap_Start(videoCapAssist))
						{
							alert("启动录像失败");
							return;
						}
						alert("已开始录像");
					}
		        }
		        function StopAssistRecord()
		        {
					if(plugin().VideoCap_Stop(videoCapAssist))
					{
						plugin().VideoCap_Destroy(videoCapAssist);
						videoCapAssist = null;
						alert("录像结束，文件保存于C:\\eloamPlugin_assist.mp4\r\n若录像失败请选择较小的分辨率尝试");
					}
					else
					{
						alert("停止录像失败");
					}
		        } */
				/******************录像********************/
		        function GetImgMD5()
		        {		
			        if(PicPath)
			        {
				        var img;
				        img = plugin().Global_CreateImageFromFile(PicPath, 0);
				        var md5 = plugin().Image_GetMD5(img, 2, 0);
				        alert("图像的MD5值为：" + md5);
				
				        plugin().Image_Release(img);
			        }
			        else
			        {
				        alert("请先拍照！");
			        }
		        }
        
		        /* function Barcode()
		        {
					if (DeviceMain)
					{
						if (VideoMain)
						{
							var imgList = plugin().Video_CreateImageList(VideoMain, 0, 0);
							if (imgList) 
							{
								plugin().Global_InitBarcode();
							
								var len = plugin().ImageList_GetCount(imgList);
								for (var i = 0; i < len; i++) 
								{
									var image = plugin().ImageList_GetImage(imgList, i);

									if (image)
									{
										var b = plugin().Global_DiscernBarcode(image);
										if (b)
										{
											if(plugin().Global_GetBarcodeCount() <= 0)
											{
												alert("识别失败！");
											}
											else
											{
												for(var i = 0 ; i < plugin().Global_GetBarcodeCount(); i++)
													alert(plugin().Global_GetBarcodeData(i));										
											}
										}						
									}
								}
								plugin().ImageList_Release(imgList);
								plugin().Global_DeinitBarcode();
							}
						}
					}
		        } */
		
		
		        /******************指纹识别********************/
		        /* function InitBiokey()
		        {
			        if(!plugin().Global_InitBiokey())
			        {
				        alert("初始化指纹识别失败！");
			        }	
		        }
		        function DInitBiokey()
		        {
			        plugin().Global_DeinitBiokey();
		        }
		        function GetTemplate()
		        {
			        var b = plugin().Global_GetBiokeyTemplate();
			        if (b)
			        {
				        document.getElementById("BiokeyImg1").src= "";
				        document.getElementById("biokey").value="请按压手指三次完成模板采集！";
			        }
		        }
		
		        function StopGetTemplate()
		        {
			        var b = plugin().Global_StopGetBiokeyTemplate();
			        if (b)
			        {				
				        document.getElementById("biokey").value="已停止获取模板";
			        }
		        }
		
		        function GetFeature()
		        {
			        var b = plugin().Global_GetBiokeyFeature();
			        if (b)
			        {
			        document.getElementById("BiokeyImg2").src= "";
				        document.getElementById("biokey").value="请按压手指";
			        }
		        }
		
		        function StopGetFeature()
		        {
			        var b = plugin().Global_StopGetBiokeyFeature();
			        if (b)
			        {
				        document.getElementById("biokey").value="已停止获取特征";
			        }
		        }
		
		        function BiokeyVerify()
		        {			
			        var mem1 = plugin().Global_CreateMemoryFromFile("C:\\1.tmp");			
			        var mem2 = plugin().Global_CreateMemoryFromFile("C:\\2.tmp");
			        if (mem1&&mem2)
			        {
				        // 比较
				        var ret = plugin().Global_BiokeyVerify(mem1, mem2);
				        if (ret > 50)
				        {
					        document.getElementById("biokey").value="匹配成功" + ret.toString();
				        }
				        else
				        {
					        document.getElementById("biokey").value="匹配失败" + ret.toString();
				        }
				        plugin().Memory_Release(mem1);
				        plugin().Memory_Release(mem2);
			        }
			        else
			        {
				        alert("请采集完指纹模板和指纹特征后再比对！");
			        }
		        } */
		        /******************二代证阅读器********************/
		        function StartIDCard()
		        {					
					if(plugin().Global_InitIdCard())
					{
						if(plugin().Global_DiscernIdCard())
						{
							alert("请刷卡！");
							readIDcard = true;
						}
						else
						{
							document.getElementById("idcard").value= "启动二代证阅读失败！";
						}
					}
					else
					{
						document.getElementById("idcard").value= "初始化二代证阅读器失败！";
			        }
		        }
		        function StopIDCard()
		        {
			        plugin().Global_StopIdCardDiscern();
					plugin().Global_DeinitIdCard();
					readIDcard = false;
					
					document.getElementById("idcard").value= "已停止！";
		        }
				
				
				function ReadIDCard()
				{
					if(readIDcard)
					{
						alert("请先停止二代证阅读");
						return;
					}  
				
					if(plugin().Global_InitIdCard())
					{
						var ret = plugin().Global_ReadIdCard();
						if(ret)
						{
							var str = GetTimeString() + "：";
					
							for(var i = 0; i < 16; i++)
							{
								str += plugin().Global_GetIdCardData(i + 1);
								str += ";";				
							}						
							
							var image = plugin().Global_GetIdCardImage(1);//1表示头像， 2表示正面， 3表示反面 ...
							plugin().Image_Save(image, "C:\\idcard.jpg", 0);
							plugin().Image_Release(image);
							
							document.getElementById("idcardimg").src= "C:\\idcard.jpg";
							document.getElementById("idcard").value= str;
						}
						else
						{
							document.getElementById("idcard").value= "读取二代证失败！";
						}
						
						plugin().Global_DeinitIdCard();
					}
					else
					{
				        alert("初始化二代证阅读器失败！");
			        }
				}	
		        /******************IC卡阅读器********************/
		        /* function StartICCard()
		        {					
			        if(!plugin().Global_InitReader())
			        {
				        alert("初始化IC卡阅读器失败！");
				        return;
			        }
			        if(plugin().Global_ReaderStart())
			        {
				        alert("请刷卡！");
			        }
			        else
			        {
				        alert("启动IC卡阅读失败！");
			        }
		        }
		        function StopICCard()
		        {
			        plugin().Global_ReaderStop();
			        plugin().Global_DeinitReader();
		        } */
		        /******************磁条卡阅读器********************/
		        /* function StartMagCard()
		        {
			        if(!plugin().Global_InitMagneticCard())
			        {
				        alert("初始化磁条卡阅读器失败！");
				        return;
			        }
			        if(plugin().Global_MagneticCardReaderStart())
			        {
				        alert("请刷卡！");
			        }
			        else
			        {
				        alert("启动磁条阅读失败！");
			        }		
		        }
		        function StopMagCard()
		        {
			        plugin().Global_MagneticCardReaderStop();
			        plugin().Global_DeinitMagneticCard();	
		        } */
					        
				/******************深圳通卡阅读器********************/					
				/* function StartShenZhenTongCard()
		        {
					if(!plugin().Global_InitShenZhenTong())
			        {
				        alert("初始化深圳通卡阅读器失败！");
				        return;
			        }
			        if(plugin().Global_StartShenZhenTongCard())
			        {
				        alert("请刷卡！");
			        }
			        else
			        {
				        alert("启动深圳通卡阅读失败！");
			        }		
		        }			        
				
				function StopShenZhenTongCard()
		        {
			        plugin().Global_StopShenZhenTongCard();
			        plugin().Global_DeinitShenZhenTong();	
		        } */
				/******************人脸识别********************/
				/* function FaceDetect()
				{
					var image1 =  plugin().Global_CreateImageFromFile("C:\\1.jpg", 0);
					var image2 =  plugin().Global_CreateImageFromFile("C:\\2.jpg", 0);
					if(image1 && image2) 
					{
						var ret = plugin().DiscernFaceDetect(image1, image2);
						if(ret != -1)
						{
							ret += 20;
							if(ret > 100)
							{
								ret = 100;
							}
							var msg = "识别已完成，匹配度：" + ret + "\r\n（匹配阈值为70，高于阈值则为同一人）";
							alert(msg);
						}
						else
						{
							alert("识别失败！");
						}
					}
					else
					{
						alert("找不到图像！");
						return;
					}
					
					plugin().Image_Release(image1);
					plugin().Image_Release(image2);
				}
				
				function VerifyFaceDetect()
				{	
					if (VideoAssist)
					{		
						var image = plugin().Video_CreateImage(VideoAssist,0, AssistView().View_GetObject());
						var idcardImage = plugin().Global_CreateImageFromFile("C:\\idcard.jpg", 0);
						
						if(image && idcardImage) 
						{
							var ret = plugin().DiscernFaceDetect(image, idcardImage);
							if(ret != -1)
							{
								ret += 20;
								if(ret > 100)
								{
									ret = 100;
								}
								var msg = "识别已完成，匹配度：" + ret + "\r\n（匹配阈值为70，高于阈值则为同一人）";
								alert(msg);
							}
							else
							{
								alert("识别失败！");
							}
						}
						else
						{
							alert("获取图像失败，未识别到二代证！");
							return;
						}
						
						plugin().Image_Release(image);
						plugin().Image_Release(idcardImage);
					}
				} */
        </script>
    </head>

<body onload="Load()" onunload="Unload()" style="width: 700px;">

    <div align="center">
        <object id="view1" type="application/x-eloamplugin" width="700" height="300" name="view"></object>
        </br>
        <input class="submit_01" type="button" value="左转"	onclick="Left()" />
        <input class="submit_01" type="button" value="右转"	onclick="Right()" />
    </div>
    <div>
        <object id="thumb1" type="application/x-eloamplugin" width="1208" height="150" name="thumb"></object>
    </div>
    
    <!-- 报废汽车回收证明 -->
	<input type="hidden" name="JDCHSZM"/>
	<!-- 机动车注销证明 -->
	<input type="hidden" name="JDCZXZM"/>
	<!-- 银行卡 -->
	<input type="hidden" name="YHK"/>
	<!-- 车主身份证明 -->
	<input type="hidden" name="CZSFZM"/>
	<!-- 代理委托书 -->
	<input type="hidden" name="DLWTS"/>
	<!-- 代理人身份证 -->
	<input type="hidden" name="DLRSFZ"/>
	<!-- 非财政供养单位证明 -->
	<input type="hidden" name="FCZGYZM"/>
	<!-- 开户许可证 -->
	<input type="hidden" name="KHXKZ"/>
	<!-- 补贴对象变更证明材料 -->
	<input type="hidden" name="BTZHMBGZM"/>
    
    <div class="datagrid-header">
	<table class="datagrid-table-s datagrid-htable">
    <tr class="datagrid-row">
    	<td class="view_table_left">报废回收证明：</td>
		<td class="view_table_right">
    		<input class="submit_01" type="button" value="拍照"	onclick="Scan('JDCHSZM')" />
			<input class="submit_01" type="button" value="上传"	onclick="UploadThumbToServer('JDCHSZM', '报废回收证明')" />
			<!-- <a id="btnTakePhotoVehicleCancelProof" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a> -->
		</td>
		<td class="view_table_right" colspan="3">
			<a id="JDCHSZM_IMG" href="#" target="_blank"></a>
		</td>
    </tr>
    <tr class="datagrid-row">
    	<td style="width:130px;padding-right:5px;text-align:right;">机动车注销证明：</td>
		<td class="view_table_right">
    		<input class="submit_01" type="button" value="拍照"	onclick="Scan('JDCZXZM')" />
			<input class="submit_01" type="button" value="上传"	onclick="UploadThumbToServer('JDCZXZM', '机动车注销证明')" />
			<!-- <a id="btnTakePhotoVehicleCancelProof" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a> -->
		</td>
		<td class="view_table_right" colspan="3">
			<a id="JDCZXZM_IMG" href="#" target="_blank"></a>
		</td>
    </tr>
    <tr class="datagrid-row">
    	<td class="view_table_left" style="width:130px">银行卡：</td>
		<td class="view_table_right">
    		<input class="submit_01" type="button" value="拍照"	onclick="Scan('YHK')" />
			<input class="submit_01" type="button" value="上传"	onclick="UploadThumbToServer('YHK', '银行卡')" />
			<!-- <a id="btnTakePhotoVehicleCancelProof" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a> -->
		</td>
		<td class="view_table_right" colspan="3">
			<a id="YHK_IMG" href="#" target="_blank"></a>
		</td>
    </tr>
    <tr class="datagrid-row">
    	<td class="view_table_left" style="width:130px">车主身份证明：</td>
		<td class="view_table_right">
    		<input class="submit_01" type="button" value="拍照"	onclick="Scan('CZSFZM')" />
			<input class="submit_01" type="button" value="上传"	onclick="UploadThumbToServer('CZSFZM', '车主身份证明')" />
			<!-- <a id="btnTakePhotoVehicleCancelProof" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a> -->
		</td>
		<td class="view_table_right" colspan="3">
			<a id="CZSFZM_IMG" href="#" target="_blank"></a>
		</td>
    </tr>
    <c:if test="${param.isProxy eq 'N'}">
    <tr class="datagrid-row">
    	<td class="view_table_left" style="width:130px">代理人身份证明：</td>
		<td class="view_table_right">
    		<input class="submit_01" type="button" value="拍照"	onclick="Scan('DLRSFZ')" />
			<input class="submit_01" type="button" value="上传"	onclick="UploadThumbToServer('DLRSFZ', '代理人身份证明')" />
			<!-- <a id="btnTakePhotoVehicleCancelProof" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a> -->
		</td>
		<td class="view_table_right" colspan="3">
			<a id="DLRSFZ_IMG" href="#" target="_blank"></a>
		</td>
    </tr>
    <tr class="datagrid-row">
    	<td class="view_table_left" style="width:130px">代理委托书：</td>
		<td class="view_table_right">
    		<input class="submit_01" type="button" value="拍照"	onclick="Scan('DLWTS')" />
			<input class="submit_01" type="button" value="上传"	onclick="UploadThumbToServer('DLWTS', '代理委托书')" />
			<!-- <a id="btnTakePhotoVehicleCancelProof" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a> -->
		</td>
		<td class="view_table_right" colspan="3">
			<a id="DLWTS_IMG" href="#" target="_blank"></a>
		</td>
    </tr>
    </c:if>
    <c:if test="${param.isPersonal eq 'N'}">
    <tr class="datagrid-row">
    	<td class="view_table_left" style="width:130px">开户许可证：</td>
		<td class="view_table_right">
    		<input class="submit_01" type="button" value="拍照"	onclick="Scan('KHXKZ')" />
			<input class="submit_01" type="button" value="上传"	onclick="UploadThumbToServer('KHXKZ', '开户许可证')" />
			<!-- <a id="btnTakePhotoVehicleCancelProof" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a> -->
		</td>
		<td class="view_table_right" colspan="3">
			<a id="KHXKZ_IMG" href="#" target="_blank"></a>
		</td>
    </tr>
    <tr class="datagrid-row">
    	<td class="view_table_left" style="width:135px">非财政供养单位证明：</td>
		<td class="view_table_right">
    		<input class="submit_01" type="button" value="拍照"	onclick="Scan('FCZGYZM')" />
			<input class="submit_01" type="button" value="上传"	onclick="UploadThumbToServer('FCZGYZM', '非财政供养单位证明')" />
			<!-- <a id="btnTakePhotoVehicleCancelProof" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a> -->
		</td>
		<td class="view_table_right" colspan="3">
			<a id="FCZGYZM_IMG" href="#" target="_blank"></a>
		</td>
    </tr>
    </c:if>
    <c:if test="${param.hasChecked eq true}">
    <tr class="datagrid-row">
    	<td class="view_table_left" style="width:135px">补贴对象变更证明材料：</td>
		<td class="view_table_right">
    		<input class="submit_01" type="button" value="拍照"	onclick="Scan('BTZHMBGZM')" />
			<input class="submit_01" type="button" value="上传"	onclick="UploadThumbToServer('BTZHMBGZM', '补贴对象变更证明材料')" />
		</td>
		<td class="view_table_right" colspan="3">
			<a id="BTZHMBGZM_IMG" href="#" target="_blank"></a>
		</td>
    </tr>
    </c:if>
    <tr>
    	<td align="center" colspan="5">
	   		<input class="submit_01" type="button" value="确认返回"	onclick="ReturnPage()" />
    	</td>
    </tr>
    </table>
    </div>
    </body>
    
    <script type="text/javascript">
    	$(function() {
    		// 根据受理页面传递的参数，渲染页面数据
    		var proof_files;
    		if (window.dialogArguments != null) {
    			proof_files = window.dialogArguments;
    			
    			// 设置页面图片路径显示
    			setProofFilePath(proof_files);
    		}
    		
    	});	
    
    	// 设置各类证明材料的路径
    	function setProofFilePath(proof_files) {
    		// 设置图片路径
    		if (!proof_files) {
    			return;
    		}
    		
    		// 报废汽车回收证明
    		if (proof_files.callbackProofFile != "") {
	    		var callbackProofFile = proof_files.callbackProofFile.split(",");
	    		// 多张图片预览
	    		setProofFileImgPreview(callbackProofFile, "JDCHSZM", "报废回收证明");
    		}
    		
    		// 机动车注销证明
    		if (proof_files.vehicleCancelProofFiles != "") {
    			var vehicleCancelProofFiles = proof_files.vehicleCancelProofFiles.split(",");
    			// 多张图片预览
    			setProofFileImgPreview(vehicleCancelProofFiles, "JDCZXZM", "机动车注销证明");
    		}
    		
    		// 车主身份证明
    		if (proof_files.vehicleOwnerProofFiles != "") {
    			var vehicleOwnerProofFiles = proof_files.vehicleOwnerProofFiles.split(",");
    			// 多张图片预览
    			setProofFileImgPreview(vehicleOwnerProofFiles, "CZSFZM", "车主身份证明");
    		}
    		
    		// 银行卡
    		if (proof_files.bankCardFiles != "") {
    			var bankCardFiles = proof_files.bankCardFiles.split(",");
    			// 多张图片预览
    			setProofFileImgPreview(bankCardFiles, "YHK", "银行卡");
    		}
    		
    		// 代理人身份证明
    		if (proof_files.agentProofFiles != "") {
    			var agentProofFiles = proof_files.agentProofFiles.split(",");
    			// 多张图片预览
    			setProofFileImgPreview(agentProofFiles, "DLRSFZ", "代理人身份证明");
    		}
    		
    		// 代理委托书
    		if (proof_files.agentProxyFiles != "") {
    			var agentProxyFiles = proof_files.agentProxyFiles.split(",");
    			// 多张图片预览
    			setProofFileImgPreview(agentProxyFiles, "DLWTS", "代理委托书");
    		}
    		
    		// 开户许可证
    		if (proof_files.openAccPromitFiles != "") {
    			var openAccPromitFiles = proof_files.openAccPromitFiles.split(",");
    			// 多张图片预览
    			setProofFileImgPreview(openAccPromitFiles, "KHXKZ", "开户许可证");
    		}
    		
    		// 非财政供养单位证明
    		if (proof_files.noFinanceProvideFiles != "") {
    			var noFinanceProvideFiles = proof_files.noFinanceProvideFiles.split(",");
    			// 多张图片预览
    			setProofFileImgPreview(noFinanceProvideFiles, "FCZGYZM", "非财政供养单位证明");
    		}
    		
    		// 补贴对象变更证明材料
    		if (proof_files.accountChangeProofFiles != "") {
    			var accountChangeProofFiles = proof_files.accountChangeProofFiles.split(",");
    			// 多张图片预览
    			setProofFileImgPreview(accountChangeProofFiles, "BTZHMBGZM", "补贴对象变更证明材料");
    		}
    		
    	}
    	
    	// 根据文件路径、种类、名称生成图片回显链接
    	function setProofFileImgPreview(files, type, name) {
    		if (!files || !type) {
    			return;
    		}
    		
    		var id = "#" + type + "_IMG";
    		
    		$(id).text(name + "(1)");
			//$(id).attr("href", basePath+'/'+files[0]);
    		if (files.length > 1) {
    			for (var i = 2 ; i <= files.length ; i ++) {
    				var filepath = basePath + '/' + files[i-1];
    				var _a = "&nbsp<a id='"+id+i+"' href='#' target='_blank'>"+name+"("+i+")</a>";
    				$(id).append(_a);
    			}
    		}
    		
    		// 设置隐藏字段传递到后台
			$("input[name='"+type+"']").val(files);
    		
    	}
    	
    	// 清空各类证明材料回显路径
    	function clearFiles(fileType) {
    		// 获取所有文件链接
    		if (thumb1()) {
	    		thumb1().Thumbnail_Clear(false);
    		}
    		$("a[id^='" + fileType + "']").each( function(i, data) {
    			if (data.length > 0) {
    				if (data.id == (fileType + "_IMG")) {
    					$(this).text("");
    					$(this).attr('href', '#');
    				} else {
    					$(this).remove();
    				}
    			}
    		});
    	}
    	
    	// 点击确认返回按钮到受理录入页面
		function ReturnPage() {
			// 释放资源,关闭视频拍照
			Unload();
			
			// 将页面的所有图片路径传递到受理录入主页面
			
			var obj = new Object();
			// 报废回收证明
			if ($("input[name='JDCHSZM']").val() != "") {
				obj.JDCHSZM = $("input[name='JDCHSZM']").val();
			}
			
			// 机动车注销证明
			if ($("input[name='JDCZXZM']").val() != "") {
				obj.JDCZXZM = $("input[name='JDCZXZM']").val();
			}
			
			// 银行卡
			if ($("input[name='YHK']").val() != "") {
				obj.YHK = $("input[name='YHK']").val();
			}
			
			// 车主身份证明
			if ($("input[name='CZSFZM']").val() != "") {
				obj.CZSFZM = $("input[name='CZSFZM']").val();
			}
			
			// 代理人身份证明
			if ($("input[name='DLRSFZ']").val() != "") {
				obj.DLRSFZ = $("input[name='DLRSFZ']").val();
			}
			
			// 代理委托书
			if ($("input[name='DLWTS']").val() != "") {
				obj.DLWTS = $("input[name='DLWTS']").val();
			}
			
			// 开户许可证
			if ($("input[name='KHXKZ']").val() != "") {
				obj.KHXKZ = $("input[name='KHXKZ']").val();
			}
			
			// 非财政供养单位证明
			if ($("input[name='FCZGYZM']").val() != "") {
				obj.FCZGYZM = $("input[name='FCZGYZM']").val();
			}
			
			// 补贴对象变更证明材料
			if ($("input[name='BTZHMBGZM']").val() != "") {
				obj.BTZHMBGZM = $("input[name='BTZHMBGZM']").val();
			}
			window.returnValue = obj;
			window.close();
		}
    
    </script>
    
</html>
