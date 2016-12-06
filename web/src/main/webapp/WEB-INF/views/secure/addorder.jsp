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
    <link rel="icon" href="../../favicon.ico">

    <title>Личный кабинет</title>

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
                <li role="presentation"><a href="/secure">Как начать?</a></li>
                <li role="presentation"  class="active"><a href="/secure/orders">Мои заказы</a></li>
                <li role="presentation"><a href="/secure/monitoring">Мониторинг</a></li>
                <li role="presentation"><a href="/secure/support">Техподдержка</a></li>
                <li role="presentation"><a href="/secure/payment">Корзина</a></li>
            </ul>
        </nav>
        <h3 class="text-muted" style="color: #FFFFFF">${user_login}</h3>
    </div>

    <div class="jumbotron">
        <div class="container">
            <form class="form-signin" method="post" action="/secure/orders/add" id="form-signin">
                <div class="control-group">
                    <label class="control-label" for="appname" style="color: #080808">Название приложения:</label>
                    <div class="controls">
                        <input size="50" name="appname" id="appname" value="" type="text" class="form-control" placeholder="">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="hrefappstore" style="color: #080808">Сыллка в AppStore:</label>
                    <div class="controls">
                        <input size="256" name="hrefappstore" id="hrefappstore" value="" type="text" class="form-control" placeholder="">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="hrefplaymarket" style="color: #080808">Сыллка в PlayMarket:</label>
                    <div class="controls">
                        <input size="256" name="hrefplaymarket" id="hrefplaymarket" value="" type="text" class="form-control" placeholder="">
                    </div>
                </div>
                <div class="input-group">
                        <span class="input-group-addon">
                            <input id="clicks" type="radio" name="service" value="clicks" aria-label="Клики" style="color: #080808">Клики
                        </span>
                        <span class="input-group-addon">
                            <input id="installs" type="radio" name="service" value="installs" aria-label="Установки" style="color: #080808">Установки
                        </span>
                </div>
                <div class="control-group">
                    <label class="control-label" for="count" style="color: #080808">Колличество:</label>
                    <div class="controls">
                        <input size="50" name="count" id="count" value="" type="number" class="form-control" placeholder="">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" style="color: #080808">*Цена одного клика 1.5 рубля, цена одной инсталяции 20 рублей</label>
                </div>
                <button  name="submit" id="submit" value="" type="submit" class="btn btn-large btn-primary btn-block">Отправить</button>
            </form>
        </div>
    </div>
</div>
</body>
<style>
    body{
        background-image: url("../../../resources/background.jpg");
        color: #FFFFFF;
    }
</style>
</html>
