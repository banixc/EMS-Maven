<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="u" uri="http://banixc.com/j2ee" %>

<!DOCTYPE >
<html>
<head lang="zh-CN">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0"/>
    <title>考试成绩管理系统::登陆</title>
    <link rel="shortcut icon" href="static/favicon.ico" type="image/x-icon"/>
    <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="static/css/custom.css">
    <script src="//cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <style type="text/css">

        html, body {
            height: 100%;
        }

        .box {

            margin: 0 auto;
            position: relative;
            width: 100%;
            height: 100%;
        }

        .login-box {
            width: 100%;
            max-width: 500px;
            height: 400px;
            position: absolute;
            top: 50%;

            margin-top: -200px;
            /*设置负值，为要定位子盒子的一半高度*/

        }

        @media screen and (min-width: 500px) {
            .login-box {
                left: 50%;
                /*设置负值，为要定位子盒子的一半宽度*/
                margin-left: -250px;
            }
        }

        .form {
            width: 100%;
            max-width: 500px;
            height: 275px;
            margin: 25px auto 0 auto;
            padding-top: 25px;
        }

        .login-content {
            height: 300px;
            width: 100%;
            max-width: 500px;
            background-color: rgba(255, 250, 2550, .6);
            float: left;
        }

        .input-group {
            margin: 0px 0px 30px 0px !important;
        }

        .form-control,
        .input-group {
            height: 40px;
        }

        .form-group {
            margin-bottom: 0px !important;
        }

        .login-title {
            padding: 20px 10px;
            background-color: rgba(0, 0, 0, .6);
        }

        .login-title h1 {
            margin-top: 10px !important;
        }

        .login-title small {
            color: #fff;
        }

        .link p {
            line-height: 20px;
            margin-top: 30px;
        }

        .btn-sm {
            padding: 8px 24px !important;
            font-size: 16px !important;
        }

        body {
            background: url("http://imgur.com/NVFeDWm.png") no-repeat;
            background-size: cover;
        }

        #stuff
        {
            margin: auto;
            left: 0;
            right: 0;
            position: fixed;
            top: 50px;
        }

    </style>
</head>
<body>

<div id="stuff">
    <div class="container center">
        <u:message-box/>
    </div>
</div>

<div class="box">
    <div class="login-box">
        <div class="login-title text-center">
            <h1>
                <small>登录</small>
            </h1>
        </div>
        <div class="login-content ">
            <div class="form">
                <form action="/login" method="post" name="loginForm">
                    <div class="form-group">
                        <div class="col-xs-12  ">
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                                <input id="uid" type="text" name="uid" class="form-control" placeholder="学号/工号">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-12  ">
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                                <input id="password" type="password" name="password" class="form-control" placeholder="密码">
                            </div>
                        </div>
                    </div>

                    <div class="form-group form-actions">
                        <div class="col-xs-4 col-xs-offset-4 ">
                            <button type="submit" class="btn btn-sm btn-info"><span
                                    class="glyphicon glyphicon-off"></span> 登录
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>