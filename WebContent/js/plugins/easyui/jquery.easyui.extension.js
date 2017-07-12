/**
 * 2014-07-02 李博禹
 * This is the method for easyui-datagrid to move or hide the columns extended by lee.
 */
$.extend($.fn.datagrid.methods,{
	columnMoving:function(jq){
		return jq.each(function(){
			var grid = this;
			
			var directionDiv = $("<div></div>");
			
			directionDiv.hide();
			
			$("body").append(directionDiv);
			
			$(grid).datagrid("getPanel")
					.find(".datagrid-header td[field]:not(td[field='ckb'])").draggable({
				revert:true,
				cursor:"move",
				deltaX:10,
				deltaY:10,
				edge:10,
				proxy:function(source){
					var proxyEl = $("<div></div>");
					
					proxyEl.addClass("dg-proxy dg-proxy-error");
					
					proxyEl.text($(source).text());
					
					proxyEl.appendTo($("body"));
					
					return proxyEl;
				}
			}).droppable({
				accept:".datagrid-header td[field]",
				onDragOver:function(e,source){
					$(source).draggable("proxy").removeClass("dg-proxy-error").addClass("dg-proxy-right");
					
					$(".dg-hide-div").hide();
					
					var thisIndex = $(this).index();
					var sourceIndex = $(source).index();
					
					var className = null;
					
					var height = null;
					
					var thisOffset = null;
					
					var left = null;
					
					var top = null;
					
					height = $(this).height();
					
					if(sourceIndex > thisIndex){
						className = "dg-move-prev";

						thisOffset = $(this).offset();
						
						left = thisOffset.left;
						top = thisOffset.top;
					}else{
						className = "dg-move-next";
						
						if(thisIndex == $(this).parent().children(":last").index()){
							thisOffset = $(this).offset();
							
							left = thisOffset.left + $(this).width() - directionDiv.width();
							top = thisOffset.top;
						}else{
							thisOffset = $(this).next().offset();
							
							left = thisOffset.left - directionDiv.width();
							top = thisOffset.top;
						}
					}
					
					directionDiv.removeClass().addClass(className);
					directionDiv.css({height:height, left:left, top:top});
					directionDiv.show();
				},
				onDragLeave:function(e,source){
					$(source).draggable("proxy").removeClass("dg-proxy-right").addClass("dg-proxy-error");
					
					directionDiv.hide();
				},
				onDrop:function(e,source){
					directionDiv.remove();
					
					var thisIndex = $(this).index();
					var sourceIndex = $(source).index();
					
					var sourceCol = new Array();
					
					$(source).remove();
					$.each($(grid).datagrid("getPanel")
									.find(".datagrid-body tr"),function(index,obj){
						var sourceTd = $(obj).children("td:eq(" + sourceIndex + ")");
						
						sourceCol.push(sourceTd);
						
						sourceTd.remove();
					});
					
					var prev = sourceIndex > thisIndex;
					
					thisIndex = $(this).index();
					
					if(prev){
						$(this).before($(source));
					}else{
						$(this).after($(source));
					}
					
					$.each($(grid).datagrid("getPanel")
									.find(".datagrid-body tr"),function(index,obj){
						var thisTd = $(obj).children("td:eq(" + thisIndex + ")");
						
						if(prev){
							thisTd.before(sourceCol[index]);
						}else{
							thisTd.after(sourceCol[index]);
						}
					});
					
					$(grid).datagrid("columnMoving").datagrid("columnHiding");
				}
			});
		});
	},
	columnHiding:function(jq){
		return jq.each(function(){
			var grid = this;
			
			var tds = $(grid).datagrid("getPanel").find(".datagrid-header td[field]:not(td[field='ckb'])");
			
			var downDiv = null;
			
			if($(".dg-hide-div").length == 0){
				downDiv = $("<div></div>");
				downDiv.addClass("dg-hide-div");
				downDiv.hide();
				
				$("body").append(downDiv);
			}else{
				downDiv = $(".dg-hide-div");
			}
			
			downDiv.click(function(){
				tbDiv.show();
			}).mouseout(function(){
				var tbVisible = tbDiv.is(":visible");
				
				if(!tbVisible){
					$(this).hide();
				}
			});
			
			var tbDiv = null;
			
			if($(".dg-hide-tb").length == 0){
				tbDiv = $("<div><table></table></div>");
				tbDiv.addClass("dg-hide-tb");
				tbDiv.hide();
				
				$("body").append(tbDiv);
			}else{
				tbDiv = $(".dg-hide-tb");
				tbDiv.children("table").children().remove();
			}
			
			var trs = "";
			
			var columns = ($(grid).datagrid("options").columns)[0];
			
			$.each(columns,function(index, obj){
				if(index > 0){
					trs += "<tr>";
					
					trs += "<td><input type='checkbox' checked='checked'></td><td id=" + obj.field + ">" + obj.title + "</td>";
					
					trs += "</tr>";
				}
			});
			
			tbDiv.children().append($(trs));
			
			tbDiv.mouseout(function(e){
				var minX = $(this).offset().left;
				
				var curMouseX = e.pageX;
				
				var maxX = $(this).offset().left + $(this).width();
				
				var minY = $(this).offset().top;
				
				var curMouseY = e.pageY;
				
				var maxY = $(this).offset().top + $(this).height();
				
				var ifOverThis = (curMouseX >= minX && curMouseX < maxX) 
									&& (curMouseY >= minY && curMouseY <= maxY);
				
				if(!ifOverThis){
					downDiv.hide();
					$(this).hide();
				}
			});
			
			tbDiv.children().find("input[type='checkbox']").click(function(){
				var checked = $(this).is(":checked");
				
				var visibleTds = $(grid).datagrid("getPanel")
										.find(".datagrid-header td[field]:visible").length - 1;

				if(1 == visibleTds && !checked){
					return false;
				}
				
				var field = $(this).parent().next().attr("id");
				
				if(checked){
					$(grid).datagrid("showColumn", field);
				}else{
					$(grid).datagrid("hideColumn", field);
				}
			});
			
			tds.mouseover(function(){
				tbDiv.hide();
				
				var thisOffset = $(this).offset();
				
				var height = $(this).height();
				
				var left = null;
				
				if($(this).index() == $(this).parent().children(":last").index()){
					left = thisOffset.left;
				}else{
					//left = thisOffset.left + $(this).width() - downDiv.width();
					
					//fix the conflict between hide and resize. 2015-02-28
					//more improvement is needed.
					left = thisOffset.left + $(this).width() - downDiv.width() - 2;
				}
				
				var top = thisOffset.top;
				
				downDiv.css({height:height, left:left, top:top});
				tbDiv.css({left:left,top:top+height+1});
				
				downDiv.show();
			}).mouseout(function(e){
				
				var minY = $(this).offset().top;
				
				var curMouseY = e.pageY;
				
				var maxY = $(this).offset().top + $(this).height();
				
				var minX = null;
				
				var curMouseX = e.pageX;
				
				var maxX = null;
				
				if($(this).index() == $(this).parent().children(":last").index()){
					minX = $(this).offset().left;
					maxX = $(this).offset().left + downDiv.width();
				}else{
					minX = $(this).offset().left + $(this).width() - downDiv.width();
					maxX = $(this).next().offset().left;
				}
				
				var ifOverThis = (curMouseX >= minX && curMouseX <= maxX) 
									&& (curMouseY >= minY && curMouseY <= maxY);
				
				if(!ifOverThis){
					downDiv.hide();
				}
			});
			
		});
	},
	onLoadSuccess:function(node,data) {
		if(data){
			if(data.hasOwnProperty("success")) {
				if(!data.success) {
					Messager.alert({
						type: "error",
						title: "&nbsp;",
						content: data.message,
						fn:function(){
							window.location.href=data.url;
						}
					});
				}
			}
		}
	},
	//删除
	remove:function(_this,p,fn){
		if(null == fn){
			var selectedRows = _this.datagrid("getSelections");
			var infoMsg = null;
			infoMsg = selectedRows.length < 1 ? "请选择一条记录" : (selectedRows.length > 1 ? "最多只能选择一条记录" : null);
			
			
				if (null != infoMsg) {
					Messager.alert({
						type : "info",
						title : "&nbsp;",
						content : infoMsg
					});
				}else {
					$.messager.confirm("提示", "确认删除当前选中的"+selectedRows.length + "记录", function(r) {
						if (r) {
							var obj = new Object();
							obj.id = selectedRows[0].id;
							
							var _open = false;
							if(p.confim !=null &&p.confim!=undefined){
								_open = p.confim.call(_this);
							}else{
								_open = true;
							}
							if(_open){
								$.post(p.url, obj , function(data) {
									var _a_d = null;
									if(Object.prototype.toString.call(data) === "[object String]"){
										_a_d = eval("("+data+")");
									}else{
										_a_d = data;
									}
									if(_a_d!=null){
										if(_a_d.success){
											_this.datagrid("reload");
										}
										Messager.alert({
											type:"info",
											title :"&nbsp",
											content: _a_d.message
										})
									}else{
										Messager.alert({
											type:"info",
											title :"&nbsp",
											content: "删除失败"
										})
									}
								});
							}
						}
					});
				}
			
		}else{
			fn.call();
		}
	},
	//添加
	add:function(_this,param ,fn){
		if(fn==null){
			openDialog({
				type : "add",
				title : param.content,
				width : param.width,
				height : param.height,
				param: param.param,
				maximizable : true,
				href : param.url
			});
		}
		else{
			fn.call();
		}
	},
	//修改
	update:function(_this,param,fn){
		if(fn==null){
			var selectedRows = _this.datagrid("getSelections");
			var infoMsg = null;
			infoMsg = selectedRows.length < 1 ? "请选择一条记录" : (selectedRows.length > 1 ? "最多只能选择一条记录" : null);
			if (null != infoMsg) {
				Messager.alert({
					type : "info",
					title : "&nbsp;",
					content : infoMsg
				});
			}else {
				var _open = false;
				if(param.confim !=null &&param.confim!=undefined){
					_open = param.confim.call(_this);
				}else{
					_open = true;
				}
				if(_open){
					var url =param.url+"?id="+selectedRows[0].id+"&type=update";
						openDialog({
							type : "update",
							title : param.content,
							width : param.width,
							height : param.height,
							param: param.param,
							maximizable : true,
							href : url
					});
				}
			}
		}else{
			fn.call();
		}
	},
	//查看
	view:function(_this,p){
		openDialog({
			type : "view",
			title : p.content,
			width : p.width,
			height : p.height,
			maximizable : true,
			href : p.url,
			param : p.param
		});
	},
	initSearch:function(_this,p){
		var prvg = "";
		
		if(p!=undefined&&!p.debug){
			prvg = $.ajax({
				  url: "main/prvg.do?type="+p.module,
				  async: false
			}).responseText;
			//alert(prvg);
		}
		var _t = _this.datagrid("options").toolbar;
		var columns = p.columns;
		var shownum = 4;
		if(p.shownum){
			shownum = p.shownum;
		}
		
		var s = "";
		var sectioncount = 0;
		var up_ = false;
		var cur_td = 0;
		for(var i = 0;i<columns.length;i++){
			var c = i;
			var nextSection = false;
			
			if(sectioncount>0){
				c = c+sectioncount;
			}
			if(i<columns.length-1){
				if(columns[i+1].section!=undefined&&columns[i+1].section!=null){
					nextSection = true;
				}
			}
			if(c==0||up_||shownum*2/cur_td==1){
					if(s == null || s == ""){
						s = '<tr class="datagrid-header-row" style = "height:35px">';
					}else{
						s += '</tr><tr class="datagrid-header-row" style = "height:35px">';
					}
					up_ = false;
					cur_td=0;
			}
			
			if(nextSection){
				if(shownum*2-cur_td<6){
					up_ = true;
				}
			}
			
			if(columns[i].section){
				sectioncount++;
			}
			var width_num = 50;
			
			if(columns[i].title.length<=10&&columns[i].title.length>=5){
				width_num = 130;
			}else if(columns[i].title.length<=20 && columns[i].title.length>10){
				width_num = 180;
			}else{
				width_num = 100;
			}
			s += '<td style = "padding-right:5px ;border:none;text-align:right;width:'+width_num+'px">'+columns[i].title+'</td>';
				if(columns[i].section||(nextSection&&shownum*2-cur_td<6)){
					cur_td+=4;
					s+='<td style = "padding-left:5px;border:none;text-align:left;" colspan = "3">';
				}else{
					cur_td+=2;
					s+='<td style = "padding-left:5px;border:none;text-align:left;">';
				}
			if(columns[i].type == "text"){
				s+='<input type="text" o_type = "text" id = "'+columns[i].field+'" style = "width:150px" class = "search_input textbox">';
			}else if(columns[i].type == "date"){
				if(columns[i].section){
					var f_field = columns[i].startField;
					var e_field = columns[i].endField;
					s+='<input type="text" o_type = "date" id = "'+f_field+'" style = "width:100px" class = "search_input easyui-datebox" data-options="editable:false">&nbsp;&nbsp&nbsp至&nbsp;&nbsp&nbsp';
					s+='<input type="text" o_type = "date" id = "'+e_field+'" style = "width:100px" class = "search_input easyui-datebox" data-options="editable:false">'
				}else{
					s+='<input type="text" o_type = "date" id = "'+columns[i].field+'" style = "width:100px" class = "search_input easyui-datebox" data-options="editable:false">';
				}
			}else if(columns[i].type == "datetime"){
				if(columns[i].section){
					var f_field = columns[i].startField;
					var e_field = columns[i].endField;
					s+='<input type="text" o_type = "date" id = "'+f_field+'" style = "width:150px" class = "search_input easyui-datetimebox" data-options="editable:false">&nbsp;&nbsp&nbsp至&nbsp;&nbsp&nbsp';
					s+='<input type="text" o_type = "date" id = "'+e_field+'" style = "width:150px" class = "search_input easyui-datetimebox" data-options="editable:false">'
				}else{
					s+='<input type="text" o_type = "datetime" id = "'+columns[i].field+'" style = "width:150px" class = "search_input easyui-datetimebox" data-options="editable:false">';
				}
			}else if(columns[i].type == "combobox"){
				if (columns[i].panelHeight) {
					s+='<input id="'+columns[i].field+'" class="search_input easyui-combobox" style = "width:150px" o_type = "combobox"  data-options="valueField:\''+columns[i].value+'\',textField:\''+columns[i].text+'\',url:\''+columns[i].url+'\',panelHeight:\'auto\',editable:false" />';
				} else {
					s+='<input id="'+columns[i].field+'" class="search_input easyui-combobox" style = "width:150px" o_type = "combobox"  data-options="valueField:\''+columns[i].value+'\',textField:\''+columns[i].text+'\',url:\''+columns[i].url+'\',panelHeight:\'100\',editable:false" />';
				}
			}else if(columns[i].type == "number"){
				s+='<input type="text" o_type = "text" id = "'+columns[i].field+'" style = "width:150px" class = "search_input numberbox">';
			}
			s += '</td>';
		}
		$(_t).find("table").append(s);
		
		
		
	/*	var s = '<tr class="datagrid-header-row" style = "height:35px"><td>';
		for(var i = 0;i<columns.length;i++){
			var width_num = 50;
			if(columns[i].title.length<=10&&columns[i].title.length>=5){
				width_num = 130;
			}else if(columns[i].title.length<=20 && columns[i].title.length>10){
				width_num = 180;
			}else{
				width_num = 100;
			}
			s += '<dl style = "float:left;height:20px;margin-top:5px;margin-bottom:5px">';
			s += '<dt style = "float:left;padding-right:5px;border:none;text-align:right;width:'+width_num+'px";height:35px>'+columns[i].title+'</dt>';
			
			s += '<dd style = "margin:-2px 0px 0px 5px;float:left;padding-left:5px;border:none;text-align:left;width:180px">';
			if(columns[i].type == "text"){
				s+='<input type="text" o_type = "text" id = "'+columns[i].field+'" style = "width:150px" class = "search_input textbox">';
			}else if(columns[i].type == "date"){
				s+='<input type="text" o_type = "date" id = "'+columns[i].field+'" style = "width:150px" class = "search_input easyui-datebox">';
			}else if(columns[i].type == "datetime"){
				s+='<input type="text" o_type = "datetime" id = "'+columns[i].field+'" style = "width:150px" class = "search_input easyui-datetimebox">';
			}else if(columns[i].type == "combobox"){
				s+='<input id="'+columns[i].field+'" class="search_input easyui-combobox" style = "width:150px" o_type = "combobox"  data-options="valueField:\''+columns[i].value+'\',textField:\''+columns[i].text+'\',url:\''+columns[i].url+'\'" />';
			}
			s += '</dd></dl>';
		}
		s+='</td></tr>';
		$(_t).find("table").append(s);*/
		
		
		
		//设置查询等按钮的内容
		var col_num = shownum*2;
		if(columns.length<shownum){
			col_num = columns.length*2;
		}
		var t = '<tr class = "datagrid-header-row" style = "height:30px"><td colspan = "'+col_num+'">';
		if(p.tools){
			for(var _i = 0; _i< p.tools.length;_i++)
			{
				var _t_t = p.tools[_i].type;
				var title = p.tools[_i].title;
				var _icon = p.tools[_i].icon;
				var p_width= p.tools[_i].text_width;
				var b_color = p.tools[_i].color;
				if(p_width==undefined){
					p_width= 70;
				}
				
				if(_t_t == "ADD"  && ((p.debug!=undefined&&p.debug)||prvg.indexOf(_t_t)>=0)){
					if(title==null||title==undefined){
						title = "新增";
					}
					if(_icon==null || _icon == undefined){
						_icon = "icon-add";
						
					}
					t +='<a href="javascript:void(0);" class="easyui-linkbutton" p-index = "'+_i+'" o_type = "'+_t_t+'" data-options="iconCls: \''+_icon+'\'" style="margin: 2px 2px; height: 26px;width:'+p_width+'px">'+title+'</a>';
				}else if(_t_t == "UPDATE" && ((p.debug!=undefined&&p.debug)||prvg.indexOf(_t_t)>=0)){
					if(title==null||title==undefined){
						title = "修改";
					}
					if(_icon==null || _icon == undefined){
						_icon = "icon-edit";
					}
					t += '<a href="javascript:void(0);" class="easyui-linkbutton" p-index = "'+_i+'" o_type = "'+_t_t+'" data-options="iconCls: \''+_icon+'\'" style="margin: 2px 2px; height: 26px;width:'+p_width+'px">'+title+'</a>';
				}else if(_t_t == "DELETE"  && ((p.debug!=undefined&&p.debug)||prvg.indexOf(_t_t)>=0)){
					if(title==null||title==undefined){
						title = "删除";
					}
					if(_icon==null || _icon == undefined){
						_icon = "icon-remove";
					}
					if (b_color == null || b_color == undefined) {
						b_color = "#2779AA";
					}
					t += '<a href="javascript:void(0);" class="easyui-linkbutton" p-index = "'+_i+'" o_type = "'+_t_t+'" data-options="iconCls: \''+_icon+'\'" style="margin: 2px 2px; height: 26px;width:'+p_width+'px;color:'+b_color+'">'+title+'</a>';
				}else if(_t_t == "CLEAR"){
					if(title==null||title==undefined){
						title = "重置查询";
					}
					if(p.tools[_i].text_width==undefined){
						p_width= 100;
					}
					if(_icon==null || _icon == undefined){
						_icon = "icon-clear";
						
					}
					t += '<a href="javascript:void(0);" class="easyui-linkbutton" p-index = "'+_i+'" o_type = "'+_t_t+'" data-options="iconCls: \''+_icon+'\'" style="margin: 2px 2px; height: 26px;width:'+p_width+'px">'+title+'</a>';
				}else if(_t_t == "QUERY"  && ((p.debug!=undefined&&p.debug)||prvg.indexOf(_t_t)>=0)){
					if(title==null||title==undefined){
						title = "查询";
					}
					if(_icon==null || _icon == undefined){
						_icon = "icon-search";
						
					}
					t+='<a href="javascript:void(0);" class="easyui-linkbutton" p-index = "'+_i+'" id ="'+_this.attr("id")+'_search" o_type = "'+_t_t+'" data-options="iconCls: \''+_icon+'\'" style="margin: 2px; height: 26px;width:'+p_width+'px">'+title+'</a>';
				}else {
					if(((p.debug!=undefined&&p.debug)||prvg.indexOf(_t_t)>=0)){						
						t+='<a href="javascript:void(0);" class="easyui-linkbutton" p-index = "'+_i+'" id ="'+_this.attr("id")+'_'+_t_t+'" o_type = "'+_t_t+'" data-options="iconCls: \''+p.tools[_i].icon+'\'" style="margin: 2px; height: 26px;width:'+p_width+'px">'+title+'</a>';
					}
				}
			}
		}
		
		$(_t).find("table").append(t);
		
		$(_t).find(".easyui-linkbutton").click(function(_in){
			var o_type = $(this).attr("o_type");
			var _z_i = $(this).attr("p-index");
			
			if (p.tools[_z_i].fn != null) {
				if(o_type == p.tools[_z_i].type){
					p.tools[_z_i].fn.call(_this);
				}
			} else {
				if(o_type == "ADD"){
					_this.datagrid("add",p.tools[_z_i]);
				}else if(o_type == "UPDATE"){
					_this.datagrid("update",p.tools[_z_i]);
				}else if(o_type == "DELETE"){
					_this.datagrid("remove",p.tools[_z_i]);
				}else if(o_type == "QUERY"){
					var searchObj = new Object();
					$(_t).find(".search_input").each(function(x){
						var x_name = $(this).attr("id");
						var x_type = $(this).attr("o_type");
						if(x_type == "text"){
							searchObj[x_name] = $(this).val();
						}else if(x_type == "date"){
							searchObj[x_name] = $(this).datebox("getValue");
						}else if(x_type == "datetime"){
							searchObj[x_name] = $(this).datetimebox("getValue");
						}else if(x_type == "combobox"){
							searchObj[x_name] = $(this).combobox("getValue");
						}else{
							searchObj[x_name] = $(this).val();
						}
					});
					_this.datagrid("load",searchObj);
				}else if(o_type == "CLEAR"){
					$(_t).find(".search_input").each(function(x){
						var x_type = $(this).attr("o_type");
						if(x_type == "text"){
							$(this).val("");
						}else if(x_type == "date"){
							$(this).datebox("setValue","");
						}else if(x_type == "datetime"){
							$(this).datetimebox("setValue","");
						}else if(x_type == "combobox"){
							 $(this).combobox("setValue","");
						}
					})
				}
			}
		})
		
		return _this;
	},
	customButton:function(_this,p){
		var _t = _this.datagrid("options").toolbar;
		for(var i = 0 ; i< p.length;i++){
			var appendhtml = '<a href="javascript:void(0);" class="easyui-linkbutton" o_type = "append" data-options="iconCls: \''+p[i].icon+'\'" style="margin: 2px 2px; height: 26px;width:70px">'+p[i].title+'</a>';
			var _index = $(_t).find(".easyui-linkbutton").length-1;
			
			if(p[i].after){
				_index = $(_t).find(".easyui-linkbutton").length-1;
			}else{
				_index = 0;
			}
			if(p[i].index){
				_index = p[i].index;
			}
			if(p[i].after){
				$(_t).find(".easyui-linkbutton").eq(_index).after(appendhtml);
				var _a =$(_t).find(".easyui-linkbutton").eq(_index+1);
				_a.click(p[i].fn);
			}else{
				$(_t).find(".easyui-linkbutton").eq(_index).before(appendhtml);
				var _a =$(_t).find(".easyui-linkbutton").eq(_index);
				_a.click(p[i].fn);
			}
		}
		return _this;
	}
});


$.extend($.fn.datagrid.defaults,{
	onLoadSuccess:function(data){
		if(data){
			if(data.hasOwnProperty("success")) {
				if(!data.success) {
					Messager.alert({
						type: "error",
						title: "&nbsp;",
						content: data.message,
						fn:function(){
							window.location.href=data.url;
						}
					});
				}
			}
		}
	},
	onLoadError:function(){
		$.messager.alert('警告','数据获取异常');
	},title : "",
	width : "100%",
	border : true,
	fit : false,
	fitColumns : true,
	autoRowHeight : false,
	striped : false,
	nowrap : true,
	rownumbers : false,
	singleSelect : false,
	multiSort : true,
	remoteSort : true,
	pagination:true,
	method : "post",
	pageSize : 10,
	pageList : [ 10, 20, 50, 100, 200, 500],
	loadMsg : "数据加载中..."
});


/**
 * 2014-08-23 李博禹
 * This is the extension for rules of easyui-validatebox to validate the value that user inputs.
 *
 * Default rules: email: eg. validType:'email',
 * 				    url: eg. validType:'url',
 *               length: eg. validType:'length[3,5]',
 *               remote: eg. validType:{remote:['actionUrl','paramName']}
 *             combobox: eg. validType:{combobox:['param']}
 *             						   'combobox[\'param\']'
 * 
 * multiply validType: eg. validType:['email','length[3,5]']
 * 					   eg. validType:['vehicleNo','combobox[\'param\']']
 *                     eg. validType:{length:[3,5],remote:['actionUrl','paramName']}
 */
$.extend($.fn.validatebox.defaults.rules,{
	minLength: {// 判断当前值最小长度
        validator: function(value, param) {
            return value.length >= param[0];
        },
        message: "最少输入 {0}个字符。"
    },
    maxLength: {// 判断当前值最小长度
        validator: function(value, param) {
            return /^\d{0,5}$/i.test(value);
        },
        message: "请输入数字，最多输入5位。"
    },
    combobox: {// 验证当前comboxbox文本框中的值是否在列表中
    	validator: function(value,param){
    		return $(param[0]) && undefined != $(param[0]).combobox("getValue");
    	},
    	message: "请选择列表中存在的条目"
    },
    equals: {// 验证两个输入值是否相等
        validator: function(value,param){
            return value == $(param[0]).val();
        },
        message: "密码不一致"
    },
    number: {// 验证数字，包含整数、小数
    	validator: function(value) {
            return /^(\d{1,3}(,\d\d\d)*(\.\d{1,3}(,\d\d\d)*)?|\d+(\.\d+))?$/i.test(value);
        },
        message: "请输入数字"
    },
    integer: {// 验证数据，必须为整数
    	validator: function(value) {
            return /^[+]?[1-9]+\d*$/i.test(value);
        },
        message: "请输入整数"
    },
    chinese: {// 验证中文
        validator: function(value) {
            return /^[\u0391-\uFFE5]+$/i.test(value);
        },
        message: "请输入中文"
    },
    english: {// 验证英语
        validator: function(value) {
            return /^[A-Za-z]+$/i.test(value);
        },
        message: "请输入英文"
    },
    name: {// 验证姓名，可以是中文或英文(正则有点有问题，需要调整)
        validator: function(value) {
            return /^[\u0391-\uFFE5]+$/i.test(value)|/^\w+[\w\s]+\w+$/i.test(value);
        },
        message: "请输入有效的姓名"
    },
    idCard: {// 验证身份证号码
    	validator: function(value) {
            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
        },
        message: "请输入有效的身份证号码"
    },
    phone: {// 验证固定电话或手机号码
    	validator: function(value) {
            return /^(13|15|18)\d{9}$/i.test(value) || /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: "请输入有效的号码,如：13688888888或020-8888888"
    },
    fixedPhone: {// 验证固定电话
        validator: function(value) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: "请输入有效的固定电话,如:020-88888888"
    },
    mobilePhone: {// 验证手机号码
    	validator: function(value) {
            return /^(13|15|18)\d{9}$/i.test(value);
        },
        message: "请输入有效的手机号码,如：13688888888"
    },
    faxNo: {// 验证传真
        validator: function(value) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: "请输入有效的传真号码"
    },
    ip: {// 验证IP地址
        validator: function(value) {
            return /d+.d+.d+.d+/i.test(value);
        },
        message: "请输入有效的IP地址"
    },
    vehicleNo: {// 验证车牌号
        validator: function(value){
            return /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/.test(value);
        },
        message: "请输入有效的车牌号码"
    },
    photo: {// 验证上传图片格式
    	validator: function(value) {
    		return /^[a-zA-Z]:(\\.+)(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$/.test(value);
    	},
    	message: "图片格式不正确,请选择.JPEG|.JPG|.BMP|.PNG格式的图片"
    },
    checkError: {// 用户输入错误
    	validator: function(value) {
            return /^\d{20}(\d{2}[A-Za-z0-9])?$/i.test(value);
        },
        message: "请重新输入"
    },
    email: {// 用户输入错误
    	validator: function(value) {
    		///^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/
            //return /^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$/i.test(value);
    		return /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(value);
        },
        message: "请输入正确的邮箱地址"
    },
    userName: {// 用户输入错误
    	validator: function(value) {
            return /^[a-z][a-z_0-9]{5,20}$/i.test(value);
        },
        message: "以字母开头6-20位组成"
    },
    chOrEg: {// 验证中文和英文
        validator: function(value) {
            return /^[\u4e00-\u9fa5a-zA-Z0-9]+$/i.test(value);
        },
        message: "请输入中文、英文、数字"
    }
});