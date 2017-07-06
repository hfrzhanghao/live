<%@ page language="java" import="java.util.*,java.net.InetAddress"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>红云直播日志查询系统</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />

<link href="css/general.css" rel="stylesheet" type="text/css" />
<link href="css/count.css?<%=new Date().getTime()%>" rel="stylesheet"
	type="text/css" />
<link href="css/util.css?<%=new Date().getTime()%>" rel="stylesheet"
	type="text/css" />
<link href="css/log.css?<%=new Date().getTime()%>" rel="stylesheet"
	type="text/css" />

<script src="js/jquery-1.6.1.min.js" type="text/javascript"></script>
<script src="js/map.js" type="text/javascript"></script>
<script src="js/json2.js" type="text/javascript"></script>

<script src="js/util.js?<%=new Date().getTime()%>"
	type="text/javascript"></script>
<script src="js/count.js?<%=new Date().getTime()%>"
	type="text/javascript"></script>
<script src="js/viewModel.js?<%=new Date().getTime()%>"
	type="text/javascript"></script>
<script src="js/SortTable.js" type="text/javascript"></script>

<!-- 导入highstock框架文件，以下两个文件顺序不能反 -->
<script src="Highstock-2.0.3/js/highstock.js" type="text/javascript"></script>
<script src="Highstock-2.0.3/js/modules/exporting.js"
	type="text/javascript"></script>
<script src="Highstock-2.0.3/js/highcharts-3d.js" type="text/javascript"></script>

<!-- 字典表 -->
<script src="js/dictionary_tab.js?<%=new Date().getTime()%>"
	type="text/javascript"></script>
<!-- 挂断原因字典表 -->
<script src="js/disconnectedReason_tab.js?<%=new Date().getTime()%>"
	type="text/javascript"></script>
<!-- 日历插件 -->
<script src="My97DatePicker/WdatePicker.js" type="text/javascript"></script>

<script
	src="js/callerdetail/diagnose_count.js?<%=new Date().getTime()%>"
	type="text/javascript"></script>

<script type="text/javascript" charset="utf-8">
	$(function() {
		var ti = "2015-05-24 09:52:06";
		ti = ti.replace(/\s/g, "-");
		//$(".sortable tr:odd").css("background-color","#00ff00");
		//$(".sortable tr:even").css("background-color","#0000ff");
	});

	$(function() {
		//获取所有的查询框
		var tabContainers = $('div.tabs > form >div');
		//隐藏所有的查询框，初始时显示第一个查询框
		tabContainers.hide().filter(':first').show();

		$('div.tabs ul.tabNavigation a').click(
				function() {
					//$('.allSearchDiv').css({display:'none'});//不显示最下面的查询按钮
					//显示单个标签下的查询框
					$('.subbtn').removeClass('subbtn1');
					//单独标签显示时，宽度自适应
					$('#second > table,#third > table,#forth > table').removeClass('newTable');
					tabContainers.hide();//隐藏所有查询框
					tabContainers.filter(this.hash).show();//显示当前标签的查询框
					//将ul下所有标签的类名移除
					$('div.tabs ul.tabNavigation a').removeClass('selected');
					//为当前点击的标签设置类名
					$(this).addClass('selected');
					return false;
				}).filter(':first').click();

		/*****************星号之间的部分逻辑复杂些，作用：点击“高级”按钮展开/合并时显示当前的单独查询框************************/
		var array = new Array();
		array[0] = "#first";
		array[1] = "#second";
		array[2] = "#third";
		array[3] = "#forth";
		array[3] = "#fifth";

		var flag = 0;
		//var flag2 = 0;
		//单击“高级”按钮
		/* $('#highBut').click(
				function() {
					var num = 0;//num每次点击都要置零
					for ( var i = 0; i < array.length; i++) {
						if ($(array[i]).css('display') == 'block') {
							flag = i;//取得点击“高级”前哪一个标签处于显示状态
							//num++;//如果是在展开前判断，num肯定为1
						}
					}
					//if(num==1){
					//	flag2 = flag;
					//}
					$(array[flag]).hide();

					tabContainers.toggle();

					//当展开时，不显示单个查询标签的查询按钮
					if ($('.subbtn').hasClass('subbtn1')) {
						$('.subbtn').removeClass('subbtn1');
					} else {
						$('.subbtn').addClass('subbtn1');
					}

					//所有查询框div样式，点击高级前只显示一个，点击高级后显示所有
					//单独标签时，表格宽度定长，占据满屏
					//点击“高级”后，宽度根据内容自适应
					//因为第一个标签刚加载页面时就已经显示，所以只能用除了第一个标签以外的其它标签判断(不包括“高级”标签)
					if ($('#second > table').hasClass('newTable')) {
						$('#second > table,#third > table,#forth > table,#fifth > table').removeClass('newTable');
					} else {
						$('#second > table,#third > table,#forth > table,#fifth > table').addClass('newTable');
					}

					//复合查询的查询按钮事件及样式
					
					 if($('.allSearchDiv').css('display')=='none'){
						$('.allSearchDiv').css({display:'block'});
					}else{
						$('.allSearchDiv').css({display:'none'});
					} 
					 
				}); */
	});
</script>
</head>
<body style="background-image:url(images/bg.jpg);padding:5px;">
	<div class="layer"></div>
	<center>
		<table class="allTable" width="1224px" border="1"
			style="border-color:#CFBCAA;">
			<tr>
				<td align="center"><br />
					<h1>
						<font style="color: RGB(51, 158, 53);">红云直播日志查询系统</font>
					</h1> <br /></td>
			</tr>
			<tr>
				<td>
					<div class="tabs">
						<ul class="tabNavigation">
							<li><a href="#first">IP</a>
							</li>
							<li><a href="#second">卡顿次数</a>
							</li>
							<li><a href="#third">出画面时间</a>
							</li>
							<li><a href="#forth">出错类型</a>
							</li>
							<li><a href="#fifth" id="highBut">高级查询</a>
							</li>
							<%--<input type="button" value="高级查询" id="highBut"/>--%>
						</ul>
						<div id="zero">
							<!-- 通用 -->
							<table class="topzero">
								<tr>
									<td>开始时间：</td>
									<td><input type="text" class="Wdate" readonly="readonly"
										onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
										id="startTime" onblur="removeRadio()"
										value="1970-01-01 08:00:00" /></td>
									<td>&nbsp;&nbsp;结束时间：</td>
									<td><input type="text" class="Wdate" readonly="readonly"
										onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
										id="endTime" onblur="removeRadio()"
										value="1970-01-01 08:00:10" /></td>
									<td>&nbsp; <input type="radio" name="timeradio"
										id="radioFirst" value="30" />最近0.5小时 <input type="radio"
										name="timeradio" id="radioSecond" value="60" />最近1小时 <input
										type="radio" name="timeradio" id="radioThird" value="90" />最近1.5小时
										<input type="radio" name="timeradio" id="radioFourth"
										value="120" />最近2小时 <input type="radio" name="timeradio"
										id="radioFifth" value="10080" />一周以内 &nbsp;&nbsp;&nbsp;&nbsp;
									</td>
									<!-- <td>播放类型：</td>
									<td>直播<input type="radio" name="playtyperadio" id="radioLive" value="1" checked/>
										点播<input type="radio" name="playtyperadio" id="radioDemand" value="3" />
										<input id="playType" type=hidden value="1"/>
									</td> -->
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td style="color:red;">
										&nbsp;&nbsp;手动设置“开始时间”和“结束时间”，上方快捷按钮将失效，如需使用请重新点击</td>
								</tr>
							</table>
						</div>
						<form action="" method="post">
							<div id="first">
								<!-- IP -->
								<div class="hightitle">IP</div>
								<table class="oldTable">
									<tr id="flag_ordinary_tr">
										<td width="40%">URL： <input type="text" id="aboutURL"
											size="50" /></td>
										<td>IP地址： <input type="text" id="aboutIP" size="50" /></td>
										<td class="subbtn" width="180px"><input type="reset"
											class="selectreset" value="重置" /> &nbsp; <input
											type="button" id="aboutIDsubmit" value="查询" /></td>
									</tr>
								</table>
								<table class="oldTable">
									<tr>
										<td>
											<div id="IP_URL_Content">
												<%--<h1>查询内容显示在此</h1>--%>
											</div></td>
									</tr>
								</table>
							</div>
							<div id="second">
								<!-- 卡顿次数-->
								<div class="hightitle">卡顿次数</div>
								<table class="oldTable">
									<tr>
										<td width="40%">URL： <input type="text" id="lockURL"
											size="50" /></td>
										<td>卡顿次数： <select id="lockSelect">
												<option value="gt">大于</option>
												<option value="lt">小于</option>
												<option value="eq">等于</option>
										</select> <input type="text" id="lockCount" size="20" /></td>

										<td class="subbtn" width="150px"><input type="reset"
											class="selectreset" value="重置" /> &nbsp; <input
											type="button" id="lockSearch" value="查询" /></td>
									</tr>
								</table>
								<table class="oldTable">
									<tr>
										<td>
											<div id="lockCount_Content">
												<%--<h1>查询内容显示在此</h1>--%>
											</div></td>
									</tr>
								</table>
							</div>
							<div id="third">
								<!-- 出画面时间-->
								<div class="hightitle">出画面时间</div>
								<table class="oldTable">
									<tr>
										<td width="40%">URL： <input type="text" id="firstPicURL"
											size="50" /></td>
										<td>出画面时间： <select id="timeduraction">
												<option value="gt">大于</option>
												<option value="lt">小于</option>
												<option value="eq">等于</option>
										</select> <input type="text" id="firstPic" size="20" />(毫秒)</td>
										<td class="subbtn" width="150px"><input type="reset"
											class="selectreset" value="重置" /> &nbsp; <input
											type="button" id="firstPicSearch" value="查询" /></td>
									</tr>
								</table>
								<table class="oldTable">
									<tr>
										<td>
											<div id="firstPic_Content">
												<%--<h1>查询内容显示在此</h1>--%>
											</div></td>
									</tr>
								</table>
							</div>
							<div id="forth">
								<!-- 出错类型 -->
								<div class="hightitle">出错类型</div>
								<table class="oldTable">
									<tr>
										<td width="40%">URL： <input type="text" id="resultURL"
											size="50" /></td>
										<!-- <td>播放出错类型： <select id="resultChoise">
												<option value="10000">全部异常</option>
												<option value="2016">Host超时，同步接口结束[2016]</option>
												<option value="2020">用户被踢时，挂断呼叫[2020]</option>
												<option value="2069">HOST未连接[2069]</option>
												<option value="2078">UCS未连接HOST[2078]</option>
												<option value="2079">DHT未连接HOST[2079]</option>
												<option value="2080">CM未连接HOST[2080]</option>
												<option value="2081">DHT未JOIN[2081]</option>
												<option value="2098">与HOST断开[2098]</option>
												<option value="2099">UCS异常，在场服务断开[2099]</option>
												<option value="2101">UCS异常，通讯录断开[2101]</option>
												<option value="2103">UCS异常，用户中心断开[2103]</option>
												<option value="2114">用户中心返回令牌失效[2114]</option>
										</select></td> -->
										<td width="30%">手动输入错误码： <input type="text" id="errCode"
											size="5" />置空则展示所有出错的IP</td>
										<td class="subbtn" width="150px"><input type="reset"
											class="selectreset" value="重置" /> &nbsp; <input
											type="button" id="resultSearch" value="查询" /></td>
									</tr>
								</table>
								<table class="oldTable">
									<tr>
										<td>
											<div id="result_Content">
												<%--<h1>查询内容显示在此</h1>--%>
											</div></td>
									</tr>
								</table>
							</div>
							<div id="fifth">
								<!-- 高级查询 -->
								<div class="hightitle">高级查询</div>
								<table class="oldTable">
									<tr>
										<td width="30%">URL： <input type="text" id="URL_high" size="30"/>
										</td>
										<td>卡顿次数： <select id="lockSelect_high">
												<option value="gt">大于</option>
												<option value="lt">小于</option>
												<option value="eq">等于</option>
											</select> <input type="text" id="lockCount_high" size="5" />
										</td>
										<td>出画面时间： <select id="timeduraction_high">
												<option value="gt">大于</option>
												<option value="lt">小于</option>
												<option value="eq">等于</option>
											</select> <input type="text" id="firstPic_high" size="5" />(毫秒)
										</td>
										<!-- <td>播放出错类型： <select id="result_high">
												<option value="0">不过滤</option>
												<option value="2016">Host超时，同步接口结束[2016]</option>
												<option value="2020">用户被踢时，挂断呼叫[2020]</option>
												<option value="2069">HOST未连接[2069]</option>
												<option value="2078">UCS未连接HOST[2078]</option>
												<option value="2079">DHT未连接HOST[2079]</option>
												<option value="2080">CM未连接HOST[2080]</option>
												<option value="2081">DHT未JOIN[2081]</option>
												<option value="2098">与HOST断开[2098]</option>
												<option value="2099">UCS异常，在场服务断开[2099]</option>
												<option value="2101">UCS异常，通讯录断开[2101]</option>
												<option value="2103">UCS异常，用户中心断开[2103]</option>
												<option value="2114">用户中心返回令牌失效[2114]</option>
										</select></td> -->
										<td width="20%">手动输入错误码： <input type="text" id="errCode_high"
											size="5" /></td>
										<td class="subbtn" width="100px"><input type="reset"
											class="selectreset" value="重置" /> &nbsp; <input
											type="button" id="highSearch" value="查询" /></td>
									</tr>
								</table>
								<table class="oldTable">
									<tr>
										<td>
											<div id="high_Content">
												<%--<h1>查询内容显示在此</h1>--%>
											</div></td>
									</tr>
								</table>
							</div>
						</form>
					</div></td>
			</tr>

		</table>
		<br /> <font style="font-size:22px;text-align: center;">友情提示：为保证最佳页面效果，请使用Firefox(火狐浏览器)</font>
		<img style="width:39px;height:50px;" src="images/firefox.jpg"></img><br />
		<span style="font-family: cursive;">Copyright&nbsp;&copy;&nbsp;2016【Butel】&nbsp;Version:1.0.0.20161012_beta</span>

		<!-- <div
			style="line-height: 30px;background-color:silver;width:380px;font-variant:small-caps;">
			<a href="statistic.jsp" target="_blank">【直播数据统计】</a>
		</div> -->
	</center>
</body>
</html>
