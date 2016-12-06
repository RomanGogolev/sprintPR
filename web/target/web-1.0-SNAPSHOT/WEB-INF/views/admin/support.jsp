<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../../favicon.ico">

    <title>Панель администратора</title>

    <!-- Bootstrap core CSS -->
    <link href="../../../resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="../../../resources/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="../../../resources/css/jumbotron-narrow.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../../resources/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="../../../resources/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<div class="container">
    <div class="header clearfix">
        <nav>
            <ul class="nav nav-pills pull-right">
                <li role="presentation"><a href="/admin/orders">Заказы</a></li>
                <li role="presentation" class="active"><a href="/admin/supports">Вопросы</a></li>
                <li role="presentation"><a href="/admin/users">Пользователи</a></li>
            </ul>
        </nav>
        <h3 class="text-muted" style="color: #FFFFFF">${user_login}</h3>
    </div>

    <div class="jumbotron">
        <h3>${support.theme}</h3>
        <p><form class="form-signin" method="post" action="/admin/supports/answer" id="form-signin">
        <div class="control-group">
            <label class="control-label" for="id">ID Support:</label>
            <div class="controls">
                <input size="50" name="id" id="id" value="${support.id}" type="number" class="form-control" placeholder="" contenteditable="true">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="userid">User ID:</label>
            <div class="controls">
                <input size="50" name="userid" id="userid" value="${support.iduser}" type="number" class="form-control" placeholder="" disabled>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="messageuser">Message user:</label>
            <div class="controls">
                <input size="50" name="messageuser" id="messageuser" value="${support.messageuser}" type="text" class="form-control" placeholder="" disabled>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="messageadmin">Answer:</label>
            <div class="controls">
                <input size="256" name="messageadmin" id="messageadmin" value="" type="text" class="form-control" placeholder="">
            </div>
        </div>
        <button  name="submit" id="submit" value="" type="submit" class="btn btn-large btn-primary btn-block">Ответить</button>
        </form></p>
    </div>
</div> <!-- /container -->


<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../../resources/js/ie10-viewport-bug-workaround.js"></script>
</body>

<style>
    body{
        background-image: url("../../../resources/background.jpg");
        color: #080808;
    }
</style>
</html>