<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">

    <#--边栏sidebar-->
    <#include "../common/nav.ftl">

    <#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/chileme/seller/save">
                        <div class="form-group">
                            <label>openid</label>
                            <input name="openid" type="text" class="form-control" value="${(sellerInfo.getOpenid())!''}" readonly="readonly" onfocus="1"/>
                        </div>
                        <div class="form-group">
                            <label>用户名</label>
                            <input name="username" type="text" class="form-control" value="${(sellerInfo.getUsername())!''}"/>
                        </div>
                        <div class="form-group">
                            <label>密码</label>
                            <input name="password" type="password" class="form-control" value="${(sellerInfo.getPassword())!''}"/>
                        </div>
                        <input hidden type="text" name="productId" value="${(sellerInfo.getOpenid())!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>