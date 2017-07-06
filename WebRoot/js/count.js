/**
 * 查询页面count.jsp的JS文件
 */
var startTime = $("#startTime").val();// 开始时间
var endTime = $("#endTime").val();// 结束时间

$(document).ready(function() {
	/** ********************************************** */
	// 刷新页面时设置半小时和直播的单选按钮被选中，并设置默认查询半小时以内和直播的记录
	$("#radioFirst").attr("checked", "checked");
	$("#radioLive").attr("checked", "checked");
	setTime(30);

	// 单击单选按钮事件
	$(":radio[name='timeradio']").click(function() {
		var minute = $(":radio[name='timeradio'][checked]").val();
		setTime(minute);
	});

	function setTime(flag) {
		$("#startTime").val(time(flag));
		$("#endTime").val(time(0));
	}

	// 初始加载时默认显示标识的一般查询，隐藏与APPKey查询相关的
	$(":radio[id='flag_ordinary']").attr("checked", "checked");
	$("#flag_ordinary_tr").show();// 显示按诊断结果查询
});

/**
 * 验证前台两个日期之间不能超过七天
 * 
 * @return {Boolean}
 */
function checkTime() {
	var date1 = $("#startTime").val();
	var date2 = $("#endTime").val();
	var tmp = date1.split(" ")[0].split("-");
	var d1 = new Date(tmp[0], tmp[1] - 1, tmp[2]);

	tmp = date2.split(" ")[0].split("-");
	var d2 = new Date(tmp[0], tmp[1] - 1, tmp[2]);
	if ((d2.getTime() - d1.getTime()) > 7 * 24 * 60 * 60 * 1000) {
		alert("只能查询相隔一周之内的数据哦！");
		return false;
	}
	if (d1 > d2) {
		alert('开始时间不能大于结束时间');
		return false;
	}
	return true;
}
/**
 * 当点击“查询”按钮时，触发时间框时间 如果有单选按钮被选中，则根据单选按钮自动调整时间，
 * 如果没有单选按钮被选中，则不调整时间，时间按照时间文本域中的时间计算
 */
function resetTime() {
	$(":radio[name='timeradio']").each(function() {
		var id = $(this).attr("id");
		// 只要有单选按钮被选中，即按此单选按钮规定的时间设置时间
		if ($("#" + id).attr("checked") == "checked") {
			var minute = $("#" + id).val();
			;
			setTime(minute);
		}
	});
}

/**
 * 当手动为时间文本域设置时间时，取消所有时间单选按钮的选择状态，使其不被选择
 */
function removeRadio() {
	$(":radio[name='timeradio']").removeAttr("checked");
}

// 1 根据IP或URL查询Log
$("#aboutIDsubmit").live("click", function() {
	if (checkTime() == true) {
		// 1、不输入标识，查询全部
		// 2、输入标识，按输入条件查，并验证输入条件是否符合格式
		if ($('#aboutIP').val() != '') {
			resetTime();
			aboutIDsubmit();
			
		} else {
			alert("IP不能为空");
		}
	}
});

function aboutIDsubmit() {
	var currPage;// 当前页
	var downSid;// 是否点击了导出sid的链接
	var pageSize = 25;// 每页显示的记录数，默认显示及点击几个翻页链接每页显示的页数
	var attrLength = arguments.length;// 可变参数

	if (attrLength == 1) {// 点击几个翻页链接
		currPage = arguments[0];
	} else {// 点击导出sid
		currPage = arguments[0];
		downSid = arguments[1];
		pageSize = arguments[2];
	}

	startTime = dateToUTC($("#startTime").val());// 开始时间
	endTime = dateToUTC($("#endTime").val());// 结束时间
	var ip = $("#aboutIP").val();
	var url = $("#aboutURL").val();
	var playType = 1;
	$.ajax({
		type : "post",
		url : "search.test.action",
		dataType : "json",

		data : "startTime=" + startTime + "&endTime=" + endTime + "&playType=" + playType + "&ip=" + ip
				+ "&url=" + encodeURIComponent(url),
		beforeSend : function() {
			$(".layer").show();
		},
		complete : function() {
			$(".layer").hide();
		},
		success : function(data) {
			show(data, "aboutIDsubmit", "IP_URL_Content");
		},
		error : function(request, textStatus, errorThrown) {
			if (request.status == 900) {
				window.location.href = "login.jsp";
			}
		}
	});
}

// 2 根据卡顿次数查询IP
$("#lockSearch").live("click", function() {
	if (checkTime() == true) {
		// 1、不输入标识，查询全部
		// 2、输入标识，按输入条件查，并验证输入条件是否符合格式
		if ($('#lockURL').val() != '' && $('#lockCount').val() != '') {
			resetTime();
			aboutLockCount(1);
		} else {
			alert("URL和卡顿次数不能为空");
		}
	}
});

function aboutLockCount() {
	var currPage;// 当前页
	var downSid;// 是否点击了导出sid的链接
	var pageSize = 25;// 每页显示的记录数，默认显示及点击几个翻页链接每页显示的页数
	var attrLength = arguments.length;// 可变参数

	if (attrLength == 1) {// 点击几个翻页链接
		currPage = arguments[0];
	} else {// 点击导出sid
		currPage = arguments[0];
		downSid = arguments[1];
		pageSize = arguments[2];
	}

	startTime = dateToUTC($("#startTime").val());// 开始时间
	endTime = dateToUTC($("#endTime").val());// 结束时间
	var playType = 1;
	var url = $("#lockURL").val();
	var lockCount = $("#lockCount").val();
	var lockCountSelect = $("#lockSelect").val();
	$.ajax({
		type : "post",
		url : "search.lock.action",
		dataType : "json",

		data : "startTime=" + startTime + "&endTime=" + endTime + "&playType=" + playType + "&lockCount="
				+ lockCount + "&lockCountSelect="
				+ lockCountSelect + "&url=" + encodeURIComponent(url),
		beforeSend : function() {
			$(".layer").show();
		},
		complete : function() {
			$(".layer").hide();
		},
		success : function(data) {
			showIP(data, url,"aboutLockCount", "lockCount_Content");
		},
		error : function(request, textStatus, errorThrown) {
			if (request.status == 900) {
				window.location.href = "login.jsp";
			}
		}
	});
}

//3 根据出画面时间查询IP
$("#firstPicSearch").live("click", function() {
	if (checkTime() == true) {
		// 1、不输入标识，查询全部
		// 2、输入标识，按输入条件查，并验证输入条件是否符合格式
		if ($('#firstPicURL').val() != '' && $('#firstPic').val() != '') {
			resetTime();
			aboutFirstPic(1);
		} else {
			alert("URL和出画面时间不能为空");
		}
	}
});

function aboutFirstPic() {
	var currPage;// 当前页
	var downSid;// 是否点击了导出sid的链接
	var pageSize = 25;// 每页显示的记录数，默认显示及点击几个翻页链接每页显示的页数
	var attrLength = arguments.length;// 可变参数

	if (attrLength == 1) {// 点击几个翻页链接
		currPage = arguments[0];
	} else {// 点击导出sid
		currPage = arguments[0];
		downSid = arguments[1];
		pageSize = arguments[2];
	}

	startTime = dateToUTC($("#startTime").val());// 开始时间
	endTime = dateToUTC($("#endTime").val());// 结束时间
	var playType = 1;
	var url = $("#firstPicURL").val();
	var firstPic = $("#firstPic").val();
	var picDurationSelect = $("#timeduraction").val();
	$.ajax({
		type : "post",
		url : "search.firstPic.action",
		dataType : "json",
		data : "startTime=" + startTime + "&endTime=" + endTime + "&playType=" + playType + "&firstPic=" + firstPic+ "&picDurationSelect=" + picDurationSelect + "&url=" + encodeURIComponent(url),
		beforeSend : function() {
			$(".layer").show();
		},
		complete : function() {
			$(".layer").hide();
		},
		success : function(data) {
			showIP(data, url,"aboutFirstPic", "firstPic_Content");
		},
		error : function(request, textStatus, errorThrown) {
			if (request.status == 900) {
				window.location.href = "login.jsp";
			}
		}
	});
}

//3 根据result查询IP
$("#resultSearch").live("click", function() {
	if (checkTime() == true) {
		// 1、不输入标识，查询全部
		// 2、输入标识，按输入条件查，并验证输入条件是否符合格式
		if ($('#resultURL').val() != '') {
			resetTime();
			aboutResult(1);
		} else {
			alert("URL不能为空");
		}
	}
});

function aboutResult() {
	var currPage;// 当前页
	var downSid;// 是否点击了导出sid的链接
	var pageSize = 25;// 每页显示的记录数，默认显示及点击几个翻页链接每页显示的页数
	var attrLength = arguments.length;// 可变参数

	if (attrLength == 1) {// 点击几个翻页链接
		currPage = arguments[0];
	} else {// 点击导出sid
		currPage = arguments[0];
		downSid = arguments[1];
		pageSize = arguments[2];
	}

	startTime = dateToUTC($("#startTime").val());// 开始时间
	endTime = dateToUTC($("#endTime").val());// 结束时间
	var playType = 1;
	var url = $("#resultURL").val();
	
	var result = "-10000";
	
	var errCode = $("#errCode").val();
	
	if(errCode != null && errCode != ""){
		result = errCode;
	}
	
	$.ajax({
		type : "post",
		url : "search.result.action",
		dataType : "json",
		data : "startTime=" + startTime + "&endTime=" + endTime + "&playType=" + playType +"&result=" + result + "&url=" + encodeURIComponent(url),
		beforeSend : function() {
			$(".layer").show();
		},
		complete : function() {
			$(".layer").hide();
		},
		success : function(data) {
			showIP(data, url,"aboutResult", "result_Content");
		},
		error : function(request, textStatus, errorThrown) {
			if (request.status == 900) {
				window.location.href = "login.jsp";
			}
		}
	});
}

//4 高级查询
$("#highSearch").live("click", function() {
	if (checkTime() == true) {
		/*if ($('#resultURL').val() != '') {
			resetTime();*/
			aboutHigh(1);
		/*} else {
			alert("URL不能为空");
		}*/
	}
});

function aboutHigh() {
	startTime = dateToUTC($("#startTime").val());// 开始时间
	
	endTime = dateToUTC($("#endTime").val());// 结束时间
	
	var playType = 1;
	
	var url = $("#URL_high").val();
	
	var result = "-10000";
	
	var errCode = $("#errCode_high").val();
	
	if(errCode != null && errCode != ""){
		result = errCode;
	}
	
	if($("#lockCount_high").val() != ""){
		var lockCount = $("#lockCount_high").val();
	}else{
		var lockCount = -1;
	}
	
	var lockCountSelect = $("#lockSelect_high").val();
	
	if($("#firstPic_high").val() != ""){
		var firstPic = $("#firstPic_high").val();
	}else{
		var firstPic = -1;
	}
	
	var picDurationSelect = $("#timeduraction_high").val();
	$.ajax({
		type : "post",
		url : "search.high.action",
		dataType : "json",
		data : "ishigh=true"+"&startTime=" + startTime + "&endTime=" + endTime + "&playType=" + playType + "&lockCount=" + lockCount + "&lockCountSelect=" + lockCountSelect + "&firstPic=" + firstPic + "&picDurationSelect=" + picDurationSelect + "&result=" + result + "&url=" + encodeURIComponent(url),
		beforeSend : function() {
			$(".layer").show();
		},
		complete : function() {
			$(".layer").hide();
		},
		success : function(data) {
			showIP(data, url,"aboutHigh", "high_Content");
		},
		error : function(request, textStatus, errorThrown) {
			if (request.status == 900) {
				window.location.href = "login.jsp";
			}
		}
	});
}

// 展示Log
function show(data, func, mainid) {
	var list = data.data.list;
	list.sort(sortBy('uid', false, String)).sort(
			sortBy('operation_guid', false, String));
	/*for(var i = 0 ;i<list.length;i++){
		alert(list[i].uid);
	}*/
	var tableStr = "<table id='dataTab' class='datagrid2'>";
	tableStr = tableStr
			+ "<thead><td>GUID</td><td>OPERATION_GUID</td><td>URL</td><td>LOG</td></thead>";
	var len = list.length;

	for ( var i = 0; i < len; i++) {
		tableStr = tableStr + "<tr><td>" + list[i].uid + "</td>" + "<td>"
				+ list[i].operation_guid + "</td>" + "<td>" + list[i].url
				+ "</td>" + "<td>" + list[i].log.replace(/\-#log#\-/g,"<br /> ------------------------------------------------------------ <br /> ") + "</td></tr>";
	}
	tableStr = tableStr + "</table>";
	// 将动态生成的table添加的事先隐藏的div中.
	$("#" + mainid).html(tableStr);
	// 将相邻两个相同的单元格合并
	mc('dataTab', 0, 0, 0);
}

// 展示IP
function showIP(data,url, func, mainid) {
	var list = data.data.list;
	list = list.unique();// 去重
	var tableStr = "<table id='dataTab' class='datagrid2'>";
	tableStr = tableStr + "<thead><td></td><td>IP地址</td></thead>";
	var len = list.length;

	for ( var i = 0; i < len; i++) {
		var fitParam = encodeURIComponent(list[i])+"@@"+url;
		tableStr = tableStr + "<tr><td>" + i + "</td><td><a onclick=jump('"+fitParam+"')>" + list[i] + "</a></td></tr>";
	}
	tableStr = tableStr + "</table>";
	// 将动态生成的table添加的事先隐藏的div中.
	$("#" + mainid).html(tableStr);
	// 将相邻两个相同的单元格合并
	mc('dataTab', 0, 0, 0);
}

/**
 * 点击快捷按钮时，为时间文本域设置时间
 * 
 * @param {}
 *            flag
 */
function setTime(flag) {
	$("#startTime").val(time(flag));
	$("#endTime").val(time(0));
}

// 对list进行排序
var sortBy = function(filed, rev, primer) {
	rev = (rev) ? -1 : 1;
	return function(a, b) {
		a = a[filed];
		b = b[filed];
		if (typeof (primer) != 'undefined') {
			a = primer(a);
			b = primer(b);
		}
		if (a < b) {
			return rev * -1;
		}
		if (a > b) {
			return rev * 1;
		}
		return 1;
	};
};

// 单元格合并：相邻单元格如果内容相同则合并
function mc(tableId, startRow, endRow, col) {
	var tb = document.getElementById(tableId);
	if (col >= tb.rows[0].cells.length) {
		return;
	}
	if (col == 0) {
		endRow = tb.rows.length - 1;
	}
	for ( var i = startRow; i < endRow; i++) {
		if(tb.rows[startRow].cells[col] && tb.rows[i + 1].cells[0]){
			if (tb.rows[startRow].cells[col].innerHTML == tb.rows[i + 1].cells[0].innerHTML) {
				tb.rows[i + 1].removeChild(tb.rows[i + 1].cells[0]);
				tb.rows[startRow].cells[col].rowSpan = (tb.rows[startRow].cells[col].rowSpan | 0) + 1;
				if (i == endRow - 1 && startRow != endRow) {
					mc(tableId, startRow, endRow, col + 1);
				}
			} else {
				mc(tableId, startRow, i + 0, col + 1);
				startRow = i + 1;
			}
		}
	}
}

/**
 * 将以下格式的字符串转化为UTC时间(国际标准时间) 数据格式为“2015-1-14 14:32:45:165”
 * 
 * @param {}
 *            time 返回的是毫秒
 */
function dateToUTC(time) {
	var times = time;
	var year;// 年
	var month;// 月
	var day;// 日
	var hours;// 时
	var minutes;// 分
	var seconds;// 秒
	if (times != undefined && times != null && times !== "") {
		var timesArr01 = times.split(' ');
		if (timesArr01 != undefined && timesArr01 != null && timesArr01 !== "") {
			if (timesArr01[0] != undefined && timesArr01[0] != null
					&& timesArr01[0] !== "") {
				var timesArr01_01 = timesArr01[0].split('-');

				if (timesArr01_01 != undefined && timesArr01_01 != null
						&& timesArr01_01 !== "") {
					year = timesArr01_01[0];// 年
					month = timesArr01_01[1];// 月
					day = timesArr01_01[2];// 日
				}

			}
			if (timesArr01[1] != undefined && timesArr01[1] != null
					&& timesArr01[1] !== "") {
				var timesArr01_02 = timesArr01[1].split(':');
				if (timesArr01_02 != undefined && timesArr01_02 != null
						&& timesArr01_02 !== "") {
					hours = timesArr01_02[0];// 时
					minutes = timesArr01_02[1];// 分
					seconds = timesArr01_02[2];// 秒
				}

			}

			if (year != undefined && year != null && year !== ""
					&& month != undefined && month != null && month !== ""
					&& day != undefined && day != null && day !== ""
					&& hours != undefined && hours != null && hours !== ""
					&& minutes != undefined && minutes != null
					&& minutes !== "" && seconds != undefined
					&& seconds != null && seconds !== "") {
				return Date
						.UTC(year, (month - 1), day, hours, minutes, seconds) - 8*60*60*1000;
			}
		}
	}
}

// 数组去重
Array.prototype.unique = function() {
	var res = [ this[0] ];
	for ( var i = 1; i < this.length; i++) {
		var repeat = false;
		for ( var j = 0; j < res.length; j++) {
			if (this[i] == res[j]) {
				repeat = true;
				break;
			}
		}
		if (!repeat) {
			res.push(this[i]);
		}
	}
	return res;
};

function jump(data){
	//获取所有的查询框
	var tabContainers = $('.tabs > form >div');
	//隐藏所有的查询框，初始时显示第一个查询框
	tabContainers.hide().filter(':first').show();

	$('.tabs ul.tabNavigation a').click(
			function() {
				//$('.allSearchDiv').css({display:'none'});//不显示最下面的查询按钮
				//显示单个标签下的查询框
				$('.subbtn').removeClass('subbtn1');
				//单独标签显示时，宽度自适应
				$('#second > table,#third > table,#forth > table').removeClass('newTable');
				tabContainers.hide();//隐藏所有查询框
				tabContainers.filter(this.hash).show();//显示当前标签的查询框
				//将ul下所有标签的类名移除
				$('.tabs ul.tabNavigation a').removeClass('selected');
				document.getElementById("aboutIP").value = data.split("@@")[0];
				document.getElementById("aboutURL").value = data.split("@@")[1];
				$('#IP_URL_Content').html("");
				//为当前点击的标签设置类名
				$(this).addClass('selected');
				return false;
			}).filter(':first').click();
}
