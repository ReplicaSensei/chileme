<head>
    <meta charset="utf-8">
    <title>卖家后端管理系统</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/chileme/css/nav_style.css">
</head>
<script src="https://cdn.bootcss.com/jquery/2.0.0/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>
<body>
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">
                    提醒
                </h4>
            </div>
            <div class="modal-body">
                您有新的订单
            </div>
            <div class="modal-footer">
                <button onclick="location.reload();javascript:document.getElementById('notice').pause()" type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<audio id="notice" loop="loop">
    <source src="/chileme/mp3/dingdong.mp3" type="audio/mpeg" />
</audio>
<script>
    var websocket = null;
    if('WebSocket' in window) {
        websocket = new WebSocket('ws://chileme.natapp1.cc/chileme/webSocket');
    }else {
        alert('该浏览器不支持websocket!');
    }

    websocket.onopen = function (event) {
        console.log('建立连接');
    }
    websocket.onclose = function (event) {
        console.log('连接关闭');
    }
    websocket.onmessage = function (event) {
        console.log('收到消息:' + event.data);
        //弹窗提醒
        $('#myModal').modal('show');
        //播放音乐
        document.getElementById('notice').play();
    }
    websocket.onerror = function () {
        alert('websocket通信发生错误！');
    }
    window.onbeforeunload = function () {
        websocket.close();
    }
</script>
</body>