<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head></head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员注册</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/bootstrap.min.css" type="text/css" />
<script src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}

font {
	color: #3164af;
	font-size: 18px;
	font-weight: normal;
	padding: 0 10px;
}

.error {
	color: red;
}
</style>
</head>
<script>

$(function(){
	
	//自定义校验方法
	//第一个参数- "校验规则名称"
	$.validator.addMethod(
		"checkUsername",
		function(value,element,params){
			var flag = true;
			$.ajax({
				"data":{"username":value},
				"url":"${pageContext.request.contextPath}/user/checkUsername.action",
				"type":"POST",
				"dataType":"json",
				"async":false,
				"success":function(data){
					var isExist = data.isExist;
					if(isExist){
						flag = false;
					}
				}
			});
			return flag;//返回true,代表校验通过
		}
	);
	
	//校验-validate校验的是name
	$("#myform").validate({
		rules:{
			"username":{
				"required":true,
				"checkUsername":true
			},
			"password":{
				"required":true,
				"rangelength":[5,10]
			},
			"repassword":{
				"required":true,
				"rangelength":[5,10],
				"equalTo":"#password"
			},
			"email":{
				"required":true,
				"email":true
			},
			"sex":{
				"required":true
			}
		},
		messages:{
			"username":{
				"required":"用户名必输",
				"checkUsername":"用户名已经存在"
			},
			"password":{
				"required":"密码必输",
				"rangelength":"密码长度介于5-10"
			},
			"repassword":{
				"required":"请确认密码",
				"rangelength":"密码长度介于5-10",
				"equalTo":"两次密码不一致"
			},
			"email":{
				"required":"邮箱必输",
				"email":"邮箱格式不正确"
			}
		}
	});
	
});
</script>
<body>

	<!-- 引入header.jsp -->
	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>

	<div class="container"
		style="width: 100%; background: url('${pageContext.request.contextPath }/image/regist_bg.jpg');">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8"
				style="background: #fff; padding: 40px 80px; margin: 30px; border: 7px solid #ccc;">
				<font>会员注册</font>
				<form id="myform" class="form-horizontal" style="margin-top: 5px;" action="${pageContext.request.contextPath}/user/register.action" method="post">
					<div class="form-group">
						<label for="username" class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="username" name="username"
								placeholder="请输入用户名">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="password" name="password"
								placeholder="请输入密码">
						</div>
					</div>
					<div class="form-group">
						<label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="repassword" name="repassword"
								placeholder="请输入确认密码">
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">Email</label>
						<div class="col-sm-6">
							<input type="email" class="form-control" id="email" name="email"
								placeholder="Email">
						</div>
					</div>
					<div class="form-group">
						<label for="usercaption" class="col-sm-2 control-label">姓名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="name" name="name"
								placeholder="请输入姓名">
						</div>
					</div>
					<div class="form-group opt">
						<label for="inlineRadio1" class="col-sm-2 control-label">性别</label>
						<div class="col-sm-6">
							<label class="radio-inline">
								<input type="radio" name="sex" id="inlineRadio1" value="male">
								男
							</label> 
							<label class="radio-inline">
								<input type="radio" name="sex" id="inlineRadio2" value="female">
								女
							</label>
							<!-- 校验未通过提示信息框 -->
							<label class="error" for="sex" style="display:none;">性别必输</label>
						</div>
					</div>
					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">出生日期</label>
						<div class="col-sm-6">
							<input type="date" class="form-control" name="birthday">
						</div>
					</div>

					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">验证码</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" name="checkCode">

						</div>
						<div class="col-sm-2">
							<img src="${pageContext.request.contextPath }/image/captcha.jhtml" />
						</div>

					</div>

					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<input type="submit" width="100" value="注册"
								style="background: url('${pageContext.request.contextPath }/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
						</div>
					</div>
				</form>
			</div>

			<div class="col-md-2"></div>

		</div>
	</div>

	<!-- 引入footer.jsp -->
	<jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>

</body>
</html>




