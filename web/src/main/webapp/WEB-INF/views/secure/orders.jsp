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
        <h3 style="color: #080808">Ваши заказы</h3>
        <p style="color: #080808">
            <table class="table table-hover">
                <tr>
                    <th>Номер заказа</th>
                    <th>Приложение</th>
                    <th>Монитонирг</th>
                    <th>Статус</th>
                </tr>
                <c:choose>
                    <c:when test="${orders.size()>0}">
                        <c:forEach var="order" items="${orders}">
                            <c:if test="${order.state == 'В обработке'}">
                                <tr>
                                    <th>${order.id}</th>
                                    <th id="${order.appname}">${order.appname}</th>
                                    <th>-</th>
                                    <th>${order.state}</th>
                                </tr>
                            </c:if>
                            <c:if test="${order.state == 'Требуется оплата'}">
                                <tr>
                                    <th>${order.id}</th>
                                    <th id="${order.appname}">${order.appname}</th>
                                    <th>-</th>
                                    <th><a href="/secure/payment">${order.state}</a></th>
                                </tr>
                            </c:if>
                            <c:if test="${order.state == 'Выполняется'}">
                                <tr>
                                    <th>${order.id}</th>
                                    <th id="${order.appname}">${order.appname}</th>
                                    <th><a href="/secure/monitoring" class="glyphicon glyphicon-eye-open"></a></th>
                                    <th>${order.state}</th>
                                </tr>
                            </c:if>
                            <c:if test="${order.state == 'Готово'}">
                                <tr>
                                    <th>${order.id}</th>
                                    <th id="${order.appname}">${order.appname}</th>
                                    <th>-</th>
                                    <th>${order.state}</th>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <h3>Вы пока еще не сделали заказов</h3>
                    </c:otherwise>
                </c:choose>
            </table>
        </p>
        <p><a class="btn btn-lg btn-success" href="/secure/orders/add" role="button">Добавить заказ</a></p>
    </div>
</div>
</body>
<style>
    body{
        background-image: url("../../../resources/background.jpg");
        color: #080808;
    }
</style>
</html>
