<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>
<html>
<head>
    <meta charset="utf-8">
    <title>Авторизация</title>
    <link rel="stylesheet" href="../../../resources/css/bootstrap.css" type="text/css">
    <script src="../../../resources/js/bootstrap.js"></script>

</head>
<body>
<div class="container">
    <form class="form-signin" method="post" action="j_spring_security_check" id="form-signin">
        <div class="control-group">
            <label class="control-label" for="j_username">Логин:</label>
            <div class="controls">
                <input size="50" name="j_username" id="j_username" value="" type="text" class="form-control" placeholder="Login">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="j_password">Пароль:</label>
            <div class="controls">
                <input size="50" name="j_password" id="j_password" value="" type="password" class="form-control" placeholder="Password">
            </div>
        </div>
        <label class="checkbox">
            <input type="checkbox" name="save" value=""> Запомнить меня
        </label>
        <button  name="submit" id="submit" value="" type="submit" class="btn btn-large btn-primary btn-block">Войти</button>
        <a class="btn btn-large btn-primary btn-block" href="/registration" role="button">Регистрация</a>
        <div class="control-group">
            <div class="controls">
                <center>
                    <br>${error}
                </center>
            </div>
        </div>
    </form>
</div>
</body>

<style>
    body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-image: url("../../../resources/background.jpg");
        color: #FFFFFF;
    }

    .form-signin {
        max-width: 330px;
        padding: 15px;
        margin: 0 auto;
    }
    .form-signin .form-signin-heading,
    .form-signin .checkbox {
        margin-bottom: 10px;
    }
    .form-signin .checkbox {
        font-weight: normal;
    }
    .form-signin .form-control {
        position: relative;
        font-size: 16px;
        height: auto;
        padding: 10px;
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
    }
    .form-signin .form-control:focus {
        z-index: 2;
    }
    .form-signin input[type="text"],
    .form-signin input[type="password"] {
        margin-bottom: 10px;
    }
    .form-signin label {
        font-weight: normal;
    }
    .error {
        color: #b94a48;
    }
</style>
</html>