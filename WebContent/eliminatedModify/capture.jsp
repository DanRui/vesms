<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%
	String basePath = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=8" >
        <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" >
        <title>抓拍上传</title>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>/js/plugins/easyui/themes/ui-cupertino/easyui.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/plugins/easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/plugins/easyui/themes/extension.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/plugins/date/skin/WdatePicker.css">
		<link rel="stylesheet" href="<%=basePath%>/js/plugins/editor/themes/default/default.css" />
		<link rel="stylesheet" href="<%=basePath%>/js/plugins/editor/plugins/code/prettify.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/table.css">
		
        <script type="text/javascript" src="<%=basePath%>/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/plugins/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/plugins/easyui/datagrid-detailview.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/plugins/easyui/jquery.easyui.extension.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/plugins/editor/kindeditor.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/plugins/easyui/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/plugins/date/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/plugins/editor/lang/zh_CN.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/plugins/editor/plugins/code/prettify.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/main.js"></script>
        <script language="javascript" type="text/javascript">
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
			
			        var sSubType = document.getElementById('subType1'); 								
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
			        }
			
			        var nResolution = sResolution.selectedIndex;
							
			        VideoMain = plugin().Device_CreateVideo(DeviceMain, nResolution, SelectType);
			        if (VideoMain)
			        {
					    MainView().View_SelectVideo(VideoMain);
					    MainView().View_SetText("打开视频中，请等待...", 0);
							
			        }
		        }

		        function OpenVideoAssist()
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
		        }
		
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
											document.getElementById('lab1').innerHTML = plugin().Device_GetFriendlyName(DeviceMain);
											
											var sSubType = document.getElementById('subType1');
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
											changesubTypeMain();
											
											OpenVideoMain();
										}
									}
								}
								/* else if(2 == deviceType || 3 == deviceType)//辅摄像头
								{
									if(null == DeviceAssist)
									{
										DeviceAssist = plugin().Global_CreateDevice(1, idx);										
										if(DeviceAssist)
										{
											document.getElementById('lab2').innerHTML = plugin().Device_GetFriendlyName(DeviceAssist);
											
											var sSubType = document.getElementById('subType2');
											sSubType.options.length = 0;
											var subType = plugin().Device_GetSubtype(DeviceAssist);
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
											if ((0 != (subType & 2)) && (0 != (subType & 1)))//辅摄像头优先采用mjpg模式打开 
											{
												sSubType.selectedIndex = 1;
											}
											else 
											{
												sSubType.selectedIndex = 0;
											}
											initFaceDetectSuccess = plugin().InitFaceDetect();
											
											changesubTypeAssist();
											
											OpenVideoAssist();
										}
									}
								} */
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
										
										document.getElementById('lab1').innerHTML = "";
										document.getElementById('subType1').options.length = 0; 
										document.getElementById('selRes1').options.length = 0; 
                                    }
                                }
								
                               /*  if (DeviceAssist) 
								{
                                    if (plugin().Device_GetIndex(DeviceAssist) == idx) 
									{
                                        CloseVideoAssist();
                                        plugin().Device_Release(DeviceAssist);
                                        DeviceAssist = null;
										
										document.getElementById('lab2').innerHTML = "";
										document.getElementById('subType2').options.length = 0; 
										document.getElementById('selRes2').options.length = 0; 
                                    }
                                } */
							}
						}
                    });

			        addEvent(plugin(), 'Ocr', function(flag, ret)
			        {
				        if (1 == flag && 0 == ret)
				        {
					        var ret = plugin().Global_GetOcrPlainText(0);
					        alert(ret);
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
				
					        document.getElementById("idcard").value=str;	
							
							var image = plugin().Global_GetIdCardImage(1);//1表示头像， 2表示正面， 3表示反面 ...
							plugin().Image_Save(image, "C:\\idcard.jpg", 0);
							plugin().Image_Release(image);
							
							document.getElementById("idcardimg").src= "C:\\idcard.jpg";
				        }
			        });
			
			        addEvent(plugin(), 'Biokey', function(ret)
			        {
				        if (4 == ret)
				        {
					        // 采集模板成功
					        var mem = plugin().Global_GetBiokeyTemplateData();
					        if (mem)
					        {
						        if (plugin().Memory_Save(mem, "C:\\1.tmp"))
						        {
							        document.getElementById("biokey").value="获取模板成功，存储路径为C:\\1.tmp";
						        }						
						        plugin().Memory_Release(mem);
					        }
					
					        var img = plugin().Global_GetBiokeyImage();
					        plugin().Image_Save(img, "C:\\BiokeyImg1.jpg", 0);
					        plugin().Image_Release(img);
					
					        document.getElementById("BiokeyImg1").src= "C:\\BiokeyImg1.jpg";					
					        alert("获取指纹模板成功");
				        }
				        else if (8 == ret)
				        {
					        var mem = plugin().Global_GetBiokeyFeatureData();
					        if (mem)
					        {
						        if (plugin().Memory_Save(mem, "C:\\2.tmp"))
						        {
							        document.getElementById("biokey").value="获取特征成功，存储路径为C:\\2.tmp";
						        }						
						        plugin().Memory_Release(mem);
					        }
					
					        var img = plugin().Global_GetBiokeyImage();
					        plugin().Image_Save(img, "C:\\BiokeyImg2.jpg", 0);
					        plugin().Image_Release(img);
					
					        document.getElementById("BiokeyImg2").src= "C:\\BiokeyImg2.jpg";
					        alert("获取指纹特征成功");
				        }
				        else if (9 == ret)
				        {
					        document.getElementById("biokey").value += "\r\n刷的不错！";
				        }
				        else if (10 == ret)
				        {
					        document.getElementById("biokey").value+= "\r\n图像质量太差！";
				        }
				        else if (11 == ret)
				        {
					        document.getElementById("biokey").value+= "\r\n图像点数太少！";
				        }
				        else if (12 == ret)
				        {
					        document.getElementById("biokey").value+= "\r\n太快！";
				        }
				        else if (13 == ret)
				        {
					        document.getElementById("biokey").value+= "\r\n太慢！";
				        }
				        else if (14 == ret)
				        {
					        document.getElementById("biokey").value+= "\r\n其它质量问题！";
				        }				
			        });
			
			        addEvent(plugin(), 'Reader', function(type, subtype)
			        {
				        var str = "";
				        if (4 == type)
				        {
							if( 0 == subtype)//接触式CPU卡
							{
								str += "[接触式CPU卡]Id:";
								str += plugin().Global_ReaderGetCpuId();
								str += "[银行卡号]:";
								str += plugin().Global_ReaderGetCpuCreditCardNumber();
							}
							else if( 1 == subtype)//非接触式CPU卡
							{
								str += "[非接触式CPU卡] :";
								str += "[银行卡号]:";
								str += plugin().Global_ReaderGetCpuCreditCardNumber();
								
								str += "[磁道数据]:";
								str += plugin().Global_CpuGetBankCardTrack();//磁道数据
								
								str += "[交易记录]:";
								var n = plugin().Global_CpuGetRecordNumber();//交易条数
								for(var i = 0; i < n; i++)
								{
									str += plugin().Global_CpuGetankCardRecord(i);
									str + ";";
								}			
							}							
						}
				        else if (2 == type)
				        {
					        str += "[M1卡] Id:";
					        str += plugin().Global_ReaderGetM1Id();
				        }
				        else if (3 == type)
				        {
					        str += "[Memory卡] Id:";
					        str += plugin().Global_ReaderGetMemoryId();	
				        }
				        else if (5 == type)
				        {
					        str += "[社保卡] :";
					        str += plugin().Global_ReaderGetSocialData(1);
					        str += plugin().Global_ReaderGetSocialData(2);
				        }
				        document.getElementById("reader").value=str;
			        });
			
			        addEvent(plugin(), 'Mag', function(ret)
			        {
				        var str = "";
				
				        str += "[磁卡卡号] ";
				        str += plugin().Global_MagneticCardGetNumber();
				
				        str += "[磁道数据]";		

				        str += "磁道1:";					
				        str += plugin(). Global_MagneticCardGetData(0);	
				        str += "磁道2:";
				        str += plugin(). Global_MagneticCardGetData(1);	
				        str += "磁道3:";
				        str += plugin(). Global_MagneticCardGetData(2);	
				
				        document.getElementById("mag").value=str;
			        });
			
			        addEvent(plugin(), 'ShenZhenTong', function(ret)
			        {
				        var str = "";
				
				        str += "[深圳通卡号] ";
				        str += plugin().Global_GetShenZhenTongNumber();
						
						str += "[金额:] ";
				        str += plugin().Global_GetShenZhenTongAmount();
				
				        str += "[交易记录:]";

						var n = plugin().Global_GetShenZhenTongCardRecordNumber();
						for(var i = 0; i < n ; i++)
						{			
							str += plugin().Global_GetShenZhenTongCardRecord( i);	
							str += ";";	
						}			
				        document.getElementById("shenzhentong").value=str;
			        });			
			
			        addEvent(plugin(), 'MoveDetec', function(video, id)
			        {
				        // 自动拍照事件	
			        });
			
			        addEvent(plugin(), 'Deskew', function(video, view, list)
			        {
				        // 纠偏回调事件
				        var count = plugin().RegionList_GetCount(list);				
				        for (var i = 0; i < count; ++i)
				        {				
					        var region = plugin().RegionList_GetRegion(list, i);
					
					        var x1 = plugin().Region_GetX1(region);
					        var y1 = plugin().Region_GetY1(region);
					
					        var width = plugin().Region_GetWidth(region);
					        var height = plugin().Region_GetHeight(region);
					
					        plugin().Region_Release(region);
				        }
				
				        plugin().RegionList_Release(list);
			        });
				
			        var title = document.title;
			        //document.title = title + plugin().version;
				
			        MainView().Global_SetWindowName("view");
			        //AssistView().Global_SetWindowName("view");
			        thumb1().Global_SetWindowName("thumb");
		
			        var ret;
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
			       /*  if (VideoAssist)
			        {
				        AssistView().View_SetText("", 0);
				        plugin().Video_Release(VideoAssist);
				        VideoAssist = null;
			        }
			        if(DeviceAssist)
			        {
				        plugin().Device_Release(DeviceAssist);
				        DeviceAssist = null;		
			        } */			
			
			        /* StopICCard();
					StopMagCard();
					StopShenZhenTongCard();
					StopIDCard(); */
			
			        plugin().Global_DeinitDevs();
					
					//进行人脸识别反初始化时，视频应处于关闭状态
					//plugin().DeinitFaceDetect();
		        }
		        function EnableDate(obj)
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
				        /* if (VideoAssist)
				        {
					        var width = plugin().Video_GetWidth(VideoAssist);
					        var heigth = plugin().Video_GetHeight(VideoAssist);	
					
					        plugin().Video_EnableDate(VideoAssist, font, width - offsetx, heigth - offsety, 0xffffff, 0);
				        } */
				        plugin().Font_Release(font);
			        }
			        else
			        {
				        if(VideoMain)
				        {
					        plugin().Video_DisableDate(VideoMain);
				        }
				       /*  if(VideoAssist)
				        {
					        plugin().Video_DisableDate(VideoAssist);
				        } */
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
				        /* if (VideoAssist)
				        {
					        plugin().Video_EnableAddText(VideoAssist, font, 0, 0, "文字水印", 65280, 150);
				        } */
				        plugin().Font_Release(font);
			        }
			        else
			        {
				        if(VideoMain)
				        {
					        plugin().Video_DisableAddText(VideoMain);
				        }
				        /* if(VideoAssist)
				        {
					        plugin().Video_DisableAddText(VideoAssist);
				        } */
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
				        /* if(VideoAssist)
				        {
					        plugin().Video_EnableDeskewEx(VideoAssist, 1);
				        } */
			        }
			        else
			        {
				        if(VideoMain)
				        {
					        plugin().Video_DisableDeskew(VideoMain);
				        }
				        /* if(VideoAssist)
				        {
					        plugin().Video_DisableDeskew(VideoAssist);	
				        } */		
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
						/* if (VideoAssist)
						{
							if (obj.checked)
								plugin().Global_EnableFaceRectCrop(VideoAssist, 1);
							else
								plugin().Global_DisableFaceRectCrop(VideoAssist);
						} */
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
				}
		
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
		        function Scan()
		        {
		            if (VideoMain) 
                    {
		                var imgList = plugin().Video_CreateImageList(VideoMain, 0, 0);
		                if (imgList) {
		                    var len = plugin().ImageList_GetCount(imgList);
		                    for (var i = 0; i < len; i++) {
		                        var img = plugin().ImageList_GetImage(imgList, i);
		                        var Name = "C:\\" + GetTimeString() + ".jpg";
		                        var b = plugin().Image_Save(img, Name, 0);
		                        if (b) {
		                            MainView().View_PlayCaptureEffect();
		                            thumb1().Thumbnail_Add(Name);

		                            PicPath = Name;
		                        }

		                        plugin().Image_Release(img);
		                    }

		                    plugin().ImageList_Release(imgList);
		                }
		            }

		           /*  if (VideoAssist) 
                    {
		                var imgList2 = plugin().Video_CreateImageList(VideoAssist, 0, 0);
		                if (imgList2) {
		                    var len = plugin().ImageList_GetCount(imgList2);
		                    for (var i = 0; i < len; i++) {
		                        var img = plugin().ImageList_GetImage(imgList2, i);
		                        var Name = "C:\\" + GetTimeString() + ".jpg";
		                        var b = plugin().Image_Save(img, Name, 0);
		                        if (b) {
		                            AssistView().View_PlayCaptureEffect();
		                            thumb1().Thumbnail_Add(Name);
		                        }

		                        plugin().Image_Release(img);
		                    }

		                    plugin().ImageList_Release(imgList2);
		                }
		            } */
		        }
		
			
		        function OCR()
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
		        }
		
		        function UploadToHttpServer()
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
		        }
				function UploadThumbToServer()
				{	
					//http://localhost:8080/vesmsDemo/vehicleRecycle/fileCaptureUpload.do
					//http://localhost:8080/vesmsDemo/servlet/FileSteamUpload?
							
					//fileCaptureUpload
					var http = thumb1().Thumbnail_HttpUploadCheckImage("http://localhost:8080/vesmsDemo/eliminatedApply/fileCaptureUpload.do?", 1);
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
								msg = eval('('+ msg + ')');
								// 判断每张图片是否上传成功
								if (msg.success) {
									// 上传图片成功，保存图片路径到页面
									str = str + msg.message.file + ",";
								} else {
									// 任意一张图片上传失败，返回重新提交
									alert(msg.message);
									return false;
								}
							}
							$("input[name='filepath']").val(str.substring(0, str.length - 1));
							alert("图片上传成功！");
						} else {
							alert("图片上传失败！");
						}	
								
								/* $.messager.defaults = { ok: "继续上传", cancel: "返回" };
								Messager.confirm({
		        		 				title : "提示",
		        		 				content : "图片上传成功，请选择：",
		        		 				handler : function(result) {
		        		 					if (result) {
		        		 						// 继续上传
		        		 						
		        		 					} else {
		        		 					// 返回受理录入窗口
	        		 						window.close();
		        		 					}
			        		 		}
								});
								$.messager.defaults = { ok: "确认", cancel: "取消" }; */
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
		        function StartMainRecord()
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
		        }
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
        
		        function Barcode()
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
		        }
		
		
		        /******************指纹识别********************/
		        function InitBiokey()
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
		        }
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
		        function StartICCard()
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
		        }
		        /******************磁条卡阅读器********************/
		        function StartMagCard()
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
		        }
					        
				 /******************深圳通卡阅读器********************/					
				function StartShenZhenTongCard()
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
		        }
				/******************人脸识别********************/
				function FaceDetect()
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
				}
				
				// 点击确认返回按钮到受理录入页面
				function ReturnPage() {
					// 释放资源
					Unload();
					var obj = new Object();
					if ($("input[name='filepath']").val() != "") {
						obj.filepath = $("input[name='filepath']").val();
						window.returnValue = obj;
					}
					window.close();
				}
        </script>
    </head>

<body onload="Load()" onunload="Unload()">

    <div>
        <object id="view1" type="application/x-eloamplugin" width="600" height="300" name="view"></object>
        <!-- <object id="view2" type="application/x-eloamplugin" width="600" height="300" name="view"></object> -->
    </div>

    <div>
        <object id="thumb1" type="application/x-eloamplugin" width="1208" height="150" name="thumb"></object>
    </div>

	<table class="datagrid-table-s datagrid-htable">
    <tr>
	    <td>
            <label id="lab1">设备1</label>
            <select id="subType1" style="width: 90px" name="subType1" onchange="changesubTypeMain()"></select> 
            <select id="selRes1" style="width: 90px" name="selRes"></select> 
			扫描尺寸<select id="scansize" style="width: 90px" name="scansize" onchange="changescansize()">			
				<option value ="org">原始</option>
				<option value ="mid">中</option>
				<option value="small">小</option>
			</select> 
            <!-- <label id="lab2">设备2</label>
            <select id="subType2" style="width: 90px" name="subType2" onchange="changesubTypeAssist()"></select> 
            <select id="selRes2" style="width: 90px" name="selRes"></select>  -->		
            <!-- <input class="submit_01" type="button" value="打开视频" onclick="OpenVideo()" />
            <input class="submit_01" type="button" value="关闭视频" onclick="CloseVideo()" /> -->
			<input class="submit_01" type="button" value="拍照"	onclick="Scan()" />
			<!-- <b>|</b>
			<input id="EnableDate" type="checkbox" value="" onclick="EnableDate(this)" />日期
            <input id="AddText" type="checkbox" value="" onclick="AddText(this)" />文字
            <input id="Deskew" type="checkbox" value="" onclick="Deskew(this)" />纠偏
            <input id="SetState" type="checkbox" value="" onclick="SetState(this)" />手动框选
			<input id="OpenVerifyFacRect" type="checkbox" value="" onclick="OpenVerifyFacRect(this)" />脸部裁剪	 -->
            <br />
			<input class="submit_01" type="button" value="左转"	onclick="Left()" />
            <input class="submit_01" type="button" value="右转"	onclick="Right()" />
            <input class="submit_01" type="button" value="属性"	onclick="ShowProperty()" />
            <b> | </b>
            <!-- <input class="submit_01" type="button" value="主头录像"	onclick="StartMainRecord()" />
            <input class="submit_01" type="button" value="停止"	onclick="StopMainRecord()" /> -->
            <!-- <input class="submit_01" type="button" value="副头录像"	onclick="StartAssistRecord()" />
            <input class="submit_01" type="button" value="停止"	onclick="StopAssistRecord()" />		 -->
			<input class="submit_01" type="button" value="缩略图多张上传"	onclick="UploadThumbToServer()" />
            <!-- <input class="submit_01" type="button" value="上传本地文件"	onclick="UploadToHttpServer()" /> -->
            <input class="submit_01" type="button" value="扫描直接上传"	onclick="ScanToHttpServer()" />
			<br/>
            <!-- <input class="submit_01" type="button" value="条码/二维码识别"	onclick="Barcode()" />
            <input class="submit_01" type="button" value="MD5" onclick="GetImgMD5()" />
            -->
            <!-- <input class="submit_01" type="button" value="OCR" onclick="OCR()" /> -->
            <!-- <br /> -->
           <!--  <input class="submit_01" type="button" value="初始化"	onclick="InitBiokey()" />
            <input class="submit_01" type="button" value="采集指纹特征"	onclick="GetFeature()" />
            <img src=""  id="BiokeyImg2" alt="指纹图像2" width=20 height=20/>
            <input class="submit_01" type="button" value="停止采集特征"	onclick="StopGetFeature()" />
            <input class="submit_01" type="button" value="采集指纹模板"	onclick="GetTemplate()" />
            <img src=""  id="BiokeyImg1" alt="指纹图像1" width=20 height=20/>
            <input class="submit_01" type="button" value="停止采集模板"	onclick="StopGetTemplate()" />
            <input class="submit_01" type="button" value="指纹对比" onclick="BiokeyVerify()" />
            <input class="submit_01" type="button" value="反初始化"	onclick="DInitBiokey()" />
			<b>|</b>
			<input class="submit_01" type="button" value="人脸识别" onclick="FaceDetect()" />
			<input class="submit_01" type="button" value="人证比对" onclick="VerifyFaceDetect()" />
            <br />
            <input class="submit_01" type="button" value="启动二代证阅读" onclick="StartIDCard()" />
            <input class="submit_01" type="button" value="停止阅读" onclick="StopIDCard()" />
			<input class="submit_01" type="button" value="单次读取二代证" onclick="ReadIDCard()" />
			<img src=""  id="idcardimg" alt="二代证图像" width=20 height=20/>
			<b>|</b>
            <input class="submit_01" type="button" value="启动IC卡阅读" onclick="StartICCard()" />
            <input class="submit_01" type="button" value="停止阅读" onclick="StopICCard()" />
			<b>|</b>
            <input class="submit_01" type="button" value="启动磁条卡阅读" onclick="StartMagCard()" />
            <input class="submit_01" type="button" value="停止阅读" onclick="StopMagCard()" />
			<b>|</b>
			<input class="submit_01" type="button" value="启动深圳通阅读" onclick="StartShenZhenTongCard()" />
            <input class="submit_01" type="button" value="停止阅读" onclick="StopShenZhenTongCard()" /> -->
			<br />
            <!-- <input type="text" id="message" size = "195"/>
            <input type="text" id="idcard" size = "195"/>
            <input type="text" id="biokey" size = "195"/>
            <input type="text" id="reader" size = "195"/>
            <input type="text" id="mag" size = "195"/>
			<input type="text" id="shenzhentong" size = "195"/> -->
			<input type="hidden" name="filepath"/>
	    </td>
    </tr>
    <tr>
    	<td align="center">
	   		<input class="submit_01" type="button" value="确认返回"	onclick="ReturnPage()" />
    	</td>
    </tr>
    </table>
    </body>
</html>