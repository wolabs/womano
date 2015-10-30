/**
 * 
 */
function loadVnfGrid() {
	$('#vnfGrid').datagrid({
		height : '500',
		nowrap : false,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		method : 'get',
		url : '/nfvo/rest/template/vnf', // action地址
		idField : 'id',
		fitColumns : true,
		loadMsg : '载入中...',
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],
		columns : [ [ {
			field : 'id',
			title : '标识',
			width : 150,
			align : 'center'
		}, {
			field : 'name',
			title : '名称',
			width : 150,
			align : 'center'
		}, {
			field : 'provider',
			title : '厂家',
			width : 150,
			align : 'center'
		}, {
			field : 'version',
			title : '版本',
			width : 150,
			align : 'center'
		}, {
			field : 'desc',
			title : '描述',
			width : 200,
			align : 'center'
		}, {
			field : 'operation',
			title : '操作',
			width : 200,
			formatter : formatVNFOperate,
			align : 'center'
		}, ] ],
		pagination : true, // 包含分页
		pageSize : 30,
		pageList : [ 10, 20, 30, 40, 50 ],
		rownumbers : true,
		singleSelect : true,
	});
}

// 编辑和删除按钮
function formatVNFOperate(val, row) {
	return "<a class='easyui_look_xml' onclick='forwardVNFTempDetail(\""
			+ row.id
			+ "\",\""
			+ row.name
			+ "\")' title='查看XML文件'>&#xe625;</a> &nbsp;&nbsp; \
	         <a class='spanios' onclick='deleteVNFTemplById(\""
			+ row.id + "\")'>&#xe62a;</a>";
}

function forwardVNFTempDetail(id, name) {
	// 面包屑
	var sA1 = "模板管理";
	var sA2 = "VNF";
	var sA3 = "<a>" + sA1 + "</a>" + ">" + "<a>" + sA2 + "</a>";
	var sA4;
	$("#vnfGrid").parents(".div_box1").hide().siblings().show();
	sA4 = "<a>" + sA1 + "</a>" + ">" + "<a>" + sA2 + "</a>" + ">" + "<a>"
			+ name + "</a>";// 组合面包屑2
	$("#banner").html(sA4);
	showVNFTemplate(id);
}

function showVNFTemplate(id) {
	$.ajax({
		url : '/nfvo/rest/template/vnf/' + id + '/xml',
		// data : {
		// selRollBack : selRollBack,
		// },
		type : 'GET',
		cache : false,
		dataType : 'text',
		success : function(data) {
			$("#vnf_xml").text(data);
		},
		error : function() {
		}
	});
}

function deleteVNFTemplById(id) {
	 if(confirm("是否确认删除")) {
		 $.ajax({
				url : '/nfvo/rest/template/vnf/' + id,
				// data : {
				// selRollBack : selRollBack,
				// },
				type : 'DELETE',
				cache : false,
				dataType : 'json',
				success : function(data) {
					if (data.code == "0") {
						$('#vnfGrid').datagrid("reload");
					} else {
						view(data.message);
					}
				},
				error : function() {
					alert("删除失败!");
				}
			});
	 }
}

function loadNsGrid() {
	$('#nsGrid').datagrid({
		height : '500',
		nowrap : false,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		method : 'get',
		url : '/nfvo/rest/template/ns', // action地址
		idField : 'id',
		fitColumns : true,
		loadMsg : '载入中...',
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],
		columns : [ [ {
			field : 'id',
			title : '标识',
			width : 150,
			align : 'center'
		}, {
			field : 'name',
			title : '名称',
			width : 150,
			align : 'center'
		}, {
			field : 'provider',
			title : '厂家',
			width : 150,
			align : 'center'
		}, {
			field : 'version',
			title : '版本',
			width : 100,
			align : 'center'
		}, {
			field : 'type',
			title : '类型',
			width : 100,
			align : 'center'
		}, {
			field : 'desc',
			title : '描述',
			width : 160,
			align : 'center'
		}, {
			field : 'operation',
			title : '操作',
			width : 150,
			formatter : formatNSOperate,
			align : 'center'
		}, ] ],
		pagination : true, // 包含分页
		pageSize : 30,
		pageList : [ 10, 20, 30, 40, 50 ],
		rownumbers : true,
		singleSelect : true,
	});
}

// 编辑和删除按钮
function formatNSOperate(val, row) {
	return "<a class='easyui_look_xml' onclick='forwardNSTempDetail(\""
			+ row.id
			+ "\",\""
			+ row.name
			+ "\")' title='查看XML文件'>&#xe625;</a> &nbsp;&nbsp; \
	         <a class='spanios' onclick='deleteNSTemplById(\""
			+ row.id + "\")'>&#xe62a;</a>";
}

function forwardNSTempDetail(id, name) {
	// 面包屑
	var sA1 = "模板管理";
	var sA2 = "NS";
	var sA3 = "<a>" + sA1 + "</a>" + ">" + "<a>" + sA2 + "</a>";
	var sA4;
	$("#nsGrid").parents(".div_box1").hide().siblings().show();
	sA4 = "<a>" + sA1 + "</a>" + ">" + "<a>" + sA2 + "</a>" + ">" + "<a>"
			+ name + "</a>";// 组合面包屑2
	$("#banner").html(sA4);
	showNSTemplate(id);
}

function showNSTemplate(id) {
	$.ajax({
		url : '/nfvo/rest/template/ns/' + id + '/xml',
		type : 'GET',
		cache : false,
		dataType : 'text',
		success : function(data) {
			$("#ns_xml").text(data);
		},
		error : function() {
		}
	});
}

function deleteNSTemplById(id) {
	if(confirm("是否确认删除")) {
		var aj = $.ajax({
			url : '/nfvo/rest/template/ns/' + id,
			// data : {
			// selRollBack : selRollBack,
			// },
			type : 'DELETE',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if (data.code == "0") {
					$('#nsGrid').datagrid("reload");
				} else {
					view(data.message);
				}
			},
			error : function() {
				alert("删除失败!");
			}
		});
	}
}

// ===========vIMS instance table=============================
function loadVIMSGrid() {
	$('#vIMSGrid').datagrid({
		height : '500',
		nowrap : false,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		method : 'get',
		url : '/nfvo/rest/instance/instances', // action地址
		idField : 'instance_id',
		fitColumns : true,
		loadMsg : '载入中...',
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],
		columns : [ [ {
			field : 'instance_id',
			title : '标识',
			width : 150,
			align : 'center'
		}, {
			field : 'instance_name',
			title : '实例名称',
			width : 150,
			align : 'center'
		}, {
			field : 'type',
			title : '类型',
			width : 100,
			align : 'center'
		}, {
			field : 'status',
			title : '状态',
			width : 100,
			formatter : formatInstanceStatus,
			align : 'center'
		}, {
			field : 'description',
			title : '描述',
			width : 100,
			align : 'center'
		}, {
			field : 'create_time',
			title : '创建时间',
			width : 100,
			align : 'center'
		}, {
			field : 'operation',
			title : '操作',
			width : 150,
			formatter : formatVIMSOperate,
			align : 'center'
		}, ] ],
		pagination : true, // 包含分页
		pageSize : 30,
		pageList : [ 10, 20, 30, 40, 50 ],
		rownumbers : true,
		singleSelect : true,
	});
}

function formatInstanceStatus(val, row) {
	var status = "";
	if (row.status == "0") {
		status = "<span style='color:green;'>完成</span>";
	} else if (row.status == "1") {
		status = "<span style='color:orange;'>构建中</span>";
	} else if (row.status == "-1") {
		status = "<span style='color:red;'>失败</span>";
	}
	return status;
}

// 编辑和删除按钮
function formatVIMSOperate(val, row) {
	var oper = "";
	if (row.status == "0") {
		oper += "<a class='easyui_look_xml' onclick='forwardVIMSInstanceDetail(\""
				+ row.instance_id
				+ "\",\""
				+ row.name
				+ "\")' title='查看'>&#xe625;</a> &nbsp;&nbsp; ";
	}

	oper += "<a class='spanios' onclick='deleteInstanceById(\""
			+ row.instance_id + "\")'>&#xe62a;</a>";
	return oper;
}

function forwardVIMSInstanceDetail(id, name) {
	var div_box = $("#vIMSGrid").parents(".div_box1").eq(0);
	$(div_box).siblings().find(".tab_div_sub").eq(
			$("#instancetype").val() == "vIMS" ? 0 : 1).show().siblings()
			.hide();
	$(div_box).hide().siblings().show();
	monitorServiceStatus(id);
}

function showVIMSInstance(id) {
	$.ajax({
		url : '/nfvo/rest/instance/' + id + '/status',
		type : 'GET',
		cache : false,
		dataType : 'text',
		success : function(data) {
			alert(data);
		},
		error : function() {
		}
	});
}

function deleteInstanceById(id) {
	if(confirm("是否确认删除")) {
		var aj = $.ajax({
			url : '/nfvo/rest/instance/' + id,
			// data : {
			// selRollBack : selRollBack,
			// },
			type : 'DELETE',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if (data.code == 0) {
					$('#vIMSGrid').datagrid("reload");
				} else {
					view(data.message);
				}

			},
			error : function() {
				alert("删除失败!");
			}
		});
	}
}

// ===========vIMS instance table=============================
function loadNSChooseGrid() {
	$('#nsChooseGrid').datagrid({
		height : '300',
		nowrap : false,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		method : 'get',
		url : '/nfvo/rest/template/ns', // action地址
		idField : 'id',
		fitColumns : true,
		loadMsg : '载入中...',
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],
		columns : [ [ {
			field : 'id',
			title : '标识',
			width : 50,
			align : 'center'
		}, {
			field : 'name',
			title : '名称',
			width : 50,
			align : 'center'
		}, {
			field : 'provider',
			title : '厂家',
			width : 50,
			align : 'center'
		}, {
			field : 'version',
			title : '版本',
			width : 50,
			align : 'center'
		}, {
			field : 'type',
			title : '类型',
			width : 50,
			align : 'center'
		}, {
			field : 'desc',
			title : '描述',
			width : 60,
			align : 'center'
		} ] ],
		pagination : false, // 包含分页
		pageSize : 30,
		pageList : [ 10, 20, 30, 40, 50 ],
		rownumbers : true,
		singleSelect : true,
	});
}

// 显示表格
$(function() {
	loadVnfGrid();
	loadNsGrid();
	loadVIMSGrid();
	loadNSChooseGrid();
});
