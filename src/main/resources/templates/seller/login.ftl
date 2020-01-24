<html xmlns="http://www.w3.org/1999/html">
<head>

    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/chileme/css/login_style.css">
    <script src="https://cdn.bootcss.com/jquery/2.0.0/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>

</head>

<body>

<div id="content" class="container">
    <div class="login-header">
        <img src="/chileme/images/logo.jpeg">
    </div>


    <form class="login-form" role="form" method="post" action="/chileme/seller/submit">
        <a>chileme外卖后台管理员登陆</a>
        <div class="login-input-box">
            <span class="icon icon-user"></span>
            <input name="openid" type="text"  placeholder="Please enter your openid">
        </div>
        <div class="login-input-box">
            <span class="icon icon-password"></span>
            <input name="password" type="password" class="form-control" placeholder="Please enter your password">
        </div>
        <input hidden type="text" name="productId">
        <button style="margin-top: 20px" type="submit" class="btn btn-warning btn-lg btn-block">Submit</button>
    </form>
</div>
</body>
</html>