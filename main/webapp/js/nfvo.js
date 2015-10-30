/**
 * 
 */
var fullview_timer;
var changebgcolor_timer;

$(function() {
	// 面包屑
	var sA1 = "模板管理";
	var sA2 = "VNF";
	var sA3 = "<a>" + sA1 + "</a>" + ">" + "<a>" + sA2 + "</a>";
	var sA4;

	// 伸缩meanu
	$(".meanul > li > a").click(
			function() {
				$(this).addClass("active").siblings("ul").slideDown().parent()
						.siblings().children("ul").slideUp().siblings("a")
						.removeClass("active");
				sA1 = $(this).html();// 保存面包屑1
				// 如果alt为true，那么第6个contents出现其他隐藏
				var altval = $(this).attr("alt");
				if (altval) {
					var index = altval - 1;
					$(".contents").eq(index).css("left", "0px").siblings().css(
							"left", "-9999px");
					$(".lev2 a").removeClass("active");
					$("#banner").html("<a>" + sA1 + "</a>");
				}
				configTimer();
			});

	// 二级菜单点击添加样式
	$(".alist").click(
			function() {
				$(".alist").removeClass("active");
				$(this).addClass("active");
				sA2 = $(this).html();// 保存面包屑2
				sA3 = "<a>" + sA1 + "</a>" + ">" + "<a>" + sA2 + "</a>";// 组合面包屑1
				$("#banner").html(sA3);
				var altval = $(this).attr("alt");
				// 如果alt为true，那么第index个contents出现其他隐藏
				if (altval) {
					var index = altval - 1;
					if (index == 2) {
						$("#instancetype").val("vIMS");
						$('#vIMSGrid').datagrid("reload", {
							"type" : "vIMS"
						});
					} else if (index == 3 || index == 4) {
						$("#instancetype").val(index == 3 ? "vEPC" : "vBras");
						$('#vIMSGrid').datagrid("reload", {
							"type" : index == 3 ? "vEPC" : "vBras"
						});
						index = 2;
					}
					if (index == 2) {
						$(".contents").eq(index).children(".div_box1").eq(0)
								.show().siblings().hide();
					}
					// 避免ns模板列表显示不全
					if ($(".contents").eq(index).css("left") == "-9999px") {
						$(".contents").eq(index).attr("style", "");
					}
					$(".contents").eq(index).css("left", "0px").siblings().css(
							"left", "-9999px");
				}
				configTimer();
			});

	// 上传模板按钮点击后的加载动画；
	$(".upload_btn").click(function() {
		$(".mask ,.wrapper").fadeIn();
		setTimeout(function() {
			$(".mask ,.wrapper").fadeOut();
		}, 5000);
	});

	// 图形界面，配置文件
	$(".nav2 li").click(
			function() {
				$(this).addClass("active").siblings().removeClass("active");
				var index = $(this).index();

				$(this).parent().siblings(".tab_box").children(".tab_div").eq(
						index).show().siblings().hide();
			});

	// 查看XML文件，后期可删除
	$(".look").click(
			function() {
				$(this).parents(".div_box1").hide().siblings().show();

				sA4 = "<a>" + sA1 + "</a>" + ">" + "<a>" + sA2 + "</a>" + ">"
						+ "<a>" + "XX模板详情" + "</a>";// 组合面包屑2
				$("#banner").html(sA4);
			});

	// 返回列表
	$(".goback_tab").click(function() {
		$(this).parents(".div_box1").hide().siblings().show();
		// 返回后面包屑改变
		$("#banner").html(sA3);
		var tabId = $(this).attr("alt");
		if (tabId && tabId != "") {
			$('#' + tabId).datagrid("reload");
		}
	});

	// 点击弹出实例化按钮
	var sLindex;
	$(".shili_btn").click(function() {
		sLindex = $(this).parents(".contents").index();

		var sTabSLH = -$(".tab_shili").height() / 2;
		$(".tab_shili").css("margin-top", sTabSLH);
		$('#nsChooseGrid').datagrid("reload", {
			"type" : $("#instancetype").val()
		});
		$(".mask").fadeIn();
		$(".tab_shili").css("visibility", "visible");
	});

	// 点击隐藏实例化按钮
	$(".close_btn").click(function() {
		$(".mask").fadeOut();
		$(".tab_shili").css("visibility", "hidden");
	});

	// 确认实例化
	$(".a_sl_btn").click(
			function() {
				$(".mask").hide();
				$(".tab_shili").css("visibility", "hidden");
				var instanceId = instance();
				if (!instanceId || instanceId == "") {
					alert("模版不存在");
					return;
				}
				var div_box = $(".contents").eq(sLindex).children(".div_box1")
						.eq(0);
				$(div_box).siblings().find(".tab_div_sub").eq(
						$("#instancetype").val() == "vIMS" ? 0 : 1).show()
						.siblings().hide();
				$(div_box).hide().siblings().show();
				if ($("#instancetype").val() == "vIMS") {
					monitorServiceStatus(instanceId);
				}
			});

	// 单选实例化模板
	$(".shili_change_box input").click(function() {
		$(".shili_change_box input").attr("checked", false);
		$(this).attr("checked", true);
	});

	// 鼠标移动到P-CSCF上显示属性
	var x = 14;
	var y = 12;
	$(".in_pc,.in_pc2").mouseover(function(e) {
		this.myTitle = this.title;
		this.title = "";
		var tooltip = "<div id='tooltip'>" + this.myTitle + "</div>"; // 创建
		// div
		// 元素
		$("body").append(tooltip); // 把它追加到文档中
		$("#tooltip").css({
			"top" : (e.pageY + y) + "px",
			"left" : (e.pageX + x) + "px"
		}).show("fast"); // 设置x坐标和y坐标，并且显示
	}).mouseout(function() {
		this.title = this.myTitle;
		$("#tooltip").remove(); // 移除
	}).mousemove(function(e) {
		$("#tooltip").css({
			"top" : (e.pageY + y) + "px",
			"left" : (e.pageX + x) + "px"
		});
	});

	// 主机信息
	$('.submit').click(
			function() {
				if ($('#account').val().length == 0) {
					$('.hint').text("用户名不能位空").css({
						"background-color" : "green"
					});
				} else {
					$.ajax({
						url : '/nfvo/template/host',
						data : {
							account : $('#account').val()
						},
						dataType : 'json', // 很重要!!!. 预期服务器返回的数据类型
						error : function() {
							alert("error occured!!!");
						},
						success : function(data) {
							$.each(data.jsonArray, function(index) {
								$.each(data.jsonArray[index], function(key,
										value) {
									alert(key + ":" + value)
								});
							});

							$('body').append("<div>" + data.account + "</div>")
									.css("color", "red");
						}
					});
				}
			});
});

$(function() {
	$('#vnf_upload').uploadify({
		'swf' : 'js/uploadify/uploadify.swf',
		'uploader' : '/nfvo/template/upload?templateType=vnf',
		'buttonText' : '模版上传',
		'method' : 'GET',
		'height' : 15,
		'width' : 80,
		'fileTypeDesc' : '模版文件',
		'fileTypeExts' : '*.xml',
		'auto' : true,
		'multi' : true,
		'buttonCursor' : 'hand',
		'onUploadSuccess' : function(file, data, response) {
			if (data == '0') {
				alert("模版上传成功");
				$('#vnfGrid').datagrid("reload");
			} else if (data == "-1") {
				alert("上传失败，模版标识冲突或模板格式不合法");
			}
		}
	});
});

$(function() {
	$('#ns_upload').uploadify({
		'swf' : 'js/uploadify/uploadify.swf',
		'uploader' : '/nfvo/template/upload?templateType=ns',
		'buttonText' : '模版上传',
		'method' : 'GET',
		'height' : 15,
		'width' : 80,
		'fileTypeDesc' : '模版文件',
		'fileTypeExts' : '*.xml',
		'auto' : true,
		'multi' : true,
		'buttonCursor' : 'hand',
		'onUploadSuccess' : function(file, data, response) {
			if (data == '0') {
				alert("模版上传成功");
				$('#nsGrid').datagrid("reload");
			} else if (data == "-1") {
				alert("上传失败，模版标识冲突或模板格式不合法");
			}
		}
	});
});

function instance(templateId) {
	var row = $("#nsChooseGrid").datagrid("getSelected");
	if (null == row) {
		alert("请选择模版!");
		$('.disableCss').removeAttr('onclick');
	}
	var instanceId = null;
	$.ajax({
		url : '/nfvo/rest/instance/template/' + row.id,
		type : 'POST',
		async : false,
		cache : false,
		dataType : 'json',
		success : function(data) {
			instanceId = data.instanceId;
		},
		error : function() {
		}
	});
	return instanceId;
}

function onload() {
	var canvas = document.getElementById('canvas_id');
	if ($(canvas).html() == "") {
		var cans = canvas.getContext('2d');
		cans.moveTo(490, 80);// 第一个起点
		cans.lineTo(490, 200);// 第二个点
		cans.restore();
		cans.moveTo(475, 210);// 第三个点（以第二个点为起点）
		cans.lineTo(255, 210);
		cans.lineTo(255, 368);
		cans.restore();
		cans.moveTo(280, 395);
		cans.lineTo(408, 395);
		cans.restore();
		cans.moveTo(430, 395);
		cans.lineTo(586, 395);
		cans.restore();
		cans.moveTo(570, 380);
		cans.lineTo(570, 282);
		cans.lineTo(496, 282);
		cans.lineTo(496, 226);

		cans.restore();

		cans.moveTo(590, 380);
		cans.lineTo(590, 210);
		cans.lineTo(550, 210);
		cans.lineTo(550, 150);
		cans.lineTo(520, 150);
		cans.lineTo(520, 80);
		cans.restore();
		cans.moveTo(610, 380);
		cans.lineTo(610, 282);
		cans.lineTo(688, 282);
		cans.lineTo(688, 228);
		cans.restore();
		cans.moveTo(658, 210);
		cans.lineTo(590, 210);
		cans.lineWidth = 3;
		cans.strokeStyle = '#009cff';
		cans.stroke();
	}
}

function monitorServiceStatus(instanceId) {
	initialCanvas();
	changeBackground(instanceId);
	clearInterval(changebgcolor_timer);
	setInterval("changeBackground('" + instanceId + "')", 5000);
}

function initialCanvas() {
	onload();
	$("#ellis").css({
		"background-image" : "url(images/elli-down.png)"
	});
	$("#xdms").css({
		"background-image" : "url(images/xdm-down.png)"
	});
	$("#hss").css({
		"background-image" : "url(images/hss-down.png)"
	});
	$("#isscsf").css({
		"background-image" : "url(images/is-down.png)"
	});
	$("#pscsf").css({
		"background-image" : "url(images/pc-down.png)"
	});
}

function changeBackground(instanceId) {
	$.ajax({
				url : '/nfvo/rest/instance/' + instanceId + '/status',
				type : 'GET',
				cache : false,
				dataType : 'json',
				success : function(data) {
					$("#ue").css("background-image", "url('images/ue-on.png')");
					for (var i = 0; i < data.length; ++i) {
						var vnfName = data[i].vnfName;
						var status = data[i].status;
						if (vnfName == 'ellis') {
							$("#ellis").css(
									"background-image",
									"url('images/elli-" + statusImg(status)
											+ ".png')");
						} else if (vnfName == 'xdms') {
							$("#xdms").css(
									"background-image",
									"url('images/xdm-" + statusImg(status)
											+ ".png')");

						} else if (vnfName == 'hss') {
							$("#hss").css(
									"background-image",
									"url('images/hss-" + statusImg(status)
											+ ".png')");

						} else if (vnfName == 'isscsf') {
							$("#isscsf").css(
									"background-image",
									"url('images/is-" + statusImg(status)
											+ ".png')");
						} else if (vnfName == 'pscsf') {
							$("#pscsf").css(
									"background-image",
									"url('images/pc-" + statusImg(status)
											+ ".png')");
						}
					}
				},
				error : function(data) {
					//alert("实例创建失败，状态更新异常");
				}
			});
}

function statusImg(status) {
	if (status == "0") {
		return "on";
	} else {
		return "down";
	}
}

function showFullview() {
	var aj = $.ajax({
		url : '/nfvo/rest/instance/fullview',
		type : 'GET',
		cache : false,
		dataType : 'json',
		success : function(data) {
			var div_view = "";
			// data[2] = data[0];
			// data[3] = data[1];
			// data[4] = data[1];
			var perwidth = 100 / data.length - 2;
			// 根据主机数目确定主机所占的百分比
			if (perwidth > 25) {
				perwidth = 25;
			} else if (perwidth < 25) {
				perwidth = 20;
			}
			for (var i = 0; i < data.length; ++i) {
				div_view = div_view + "<li><a>"
						+ data[i].hypervisor_hostname.split(".")[0]
						+ "</a></li>";
			}
			$("#fullviewid").html(div_view);
			div_view = "";
			for (var i = 0; i < data.length; ++i) {
				if (null==data[i].servers||data[i].servers == undefined ||''==data[i].servers){
					continue;
				}
				if (data[i].servers.length < 10) {
					div_view = div_view + "<div style='width:" + perwidth
							+ "%;height:400px;' class='fullview'>";
					for (var j = 0; j < data[i].servers.length; ++j) {
						var config = data[i].servers[j].addresses;
						var virtualName = data[i].servers[j].name;
						for ( var key in config) {
							config = config[key][0];
						}
						div_view = div_view
								+ "<div class='server2' style='background:"
								+ getServerStatus(data[i].servers[j].status)
								+ "' alt='"
								+ getServerAttr(config, virtualName) + "'>"
								+ virtualName + "</div>";
					}
					div_view = div_view + "<div class='fullview_bottom'>主机: "
							+ data[i].hypervisor_hostname.split(".")[0]
							+ " </div>" + "</div>";
				} else {
					div_view = div_view + "<div style='width:" + perwidth
							+ "%;height:520px;' class='fullview'>";
					for (var j = 0; j < data[i].servers.length; ++j) {
						var config = data[i].servers[j].addresses;
						var virtualName = data[i].servers[j].name;
						for ( var key in config) {
							config = config[key][0];
						}
						div_view = div_view
								+ "<div class='server' style='background:"
								+ getServerStatus(data[i].servers[j].status)
								+ "' alt='"
								+ getServerAttr(config, virtualName) + "'>"
								+ data[i].servers[j].name + "</div>";
					}
					div_view = div_view + "<div class='fullview_bottom'>主机: "
							+ data[i].hypervisor_hostname.split(".")[0]
							+ " </div>" + "</div>";
				}
			}
			$(".full_views").html(div_view);
			showAttr();
		},
		error : function() {
		}
	});
}

function getServerStatus(status) {
	if (status == 'ACTIVE') {
		return "#00e42b";
	} else if (status == 'ERROR') {
		return "red";
	} else {
		return "#6c6c6c";
	}
}

function getServerAttr(attrs, virtualName) {
	var attrStr = "";
	attrStr += "虚拟机：" + virtualName + ",IP: " + attrs.addr + ", MAC: "
			+ attrs["OS-EXT-IPS-MAC:mac_addr"];
	return attrStr;
}

function configTimer() {
	var left = $("#fullview_content_id").css("left");
	if (left == "0px") {
		clearInterval(fullview_timer);
		fullview_timer = setInterval("showFullview()", 10000);
		showFullview();
	} else {
		clearInterval(fullview_timer);
	}
	//cancel instance status monitor
	clearInterval(changebgcolor_timer);
}

// 鼠标移动到P-CSCF上显示属性
var x = 14;
var y = 12;
function showAttr() {
	$(".server,.server2").mouseover(function(e) {
		this.myTitle = $(this).attr("alt");
		var attrArr = this.myTitle.split(",");
		console.log(attrArr);
		var temp = "";
		for (var i = 0; i < attrArr.length; ++i) {
			temp += "<p class='color1' >" + attrArr[i] + "<br></p>";
		}
		//console.log(temp);
		var tooltip = "<div id='tooltip'>" + temp + "</div>"; // 创建
		// 元素从页面中移出
		$("#tooltip").remove();
		$("body").append(tooltip); // 把它追加到文档中
		$("#tooltip").css({
			"top" : (e.pageY + y) + "px",
			"left" : (e.pageX + x) + "px"
		}).show(); // 设置x坐标和y坐标，并且显示
	}).mouseout(function() {
		this.alt = this.myTitle;
		// $(this).attr("alt", this.myTitle);
		$("#tooltip").remove(); // 移除
	}).mousemove(function(e) {
		$("#tooltip").css({
			"top" : (e.pageY + y) + "px",
			"left" : (e.pageX + x) + "px"
		});
	});
}
