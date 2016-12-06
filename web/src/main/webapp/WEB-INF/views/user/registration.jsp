<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="../../../resources/css/bootstrap.css" type="text/css">
    <script src="../../../resources/js/bootstrap.js"></script>
    <title>Регистрация</title>
</head>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.6.2.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
    $('#username').keyup(function () {
        var
                login = $('#username').val();
        if (login == '') {
            $('#msg').html('Введите логин');
        } else if (login.length > 20) {
            $('#msg').html('Логин должен быть менее 20 символов');
        } else if (login.length < 3) {
            $('#msg').html('Логин должен быть больше 3 символов');
        } else {
            $('#msg').html('Заполнено верно');
        }
    });
});</script>
<body>
<div class="container">
    <form class="form-signin" method="post" action="/registration" id="form-signin">
        <div class="control-group">
            <label class="control-label" for="email">Email:</label>
            <div class="controls">
                <input size="50" name="email" id="email" value="" type="email" class="form-control" placeholder="Email">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="username">Логин:</label>
            <div class="controls">
                <input size="50" name="username" id="username" value="" type="text" class="form-control" placeholder="Введите логин">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="password">Пароль:</label>
            <div class="controls">
                <input size="50" name="password" id="password" value="" type="password" class="form-control" placeholder="Введите пароль">
            </div>
        </div>
        <button  name="submit" id="submit" value="" type="submit" class="btn btn-large btn-primary btn-block">Регистрация</button>
        <div id="msg">${error}</div>
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
