<html>
<#include "../common/header.ftl">
    <body>
    <div id="wrapper" class="toggled">
        <#--边栏sidebar-->
        <#include "../common/nav.ftl">

        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" action="/chileme/seller/pay/submit" method="post">
                        <div class="form-group">
                            <label for="exampleInputEmail1">openid:</label><input type="text" class="form-control" id="openid" name="openid" />
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">充值金额:</label><input type="number" class="form-control" id="payNumber" name="payNumber" />
                        </div>
                        <button type="submit" class="btn btn-default">Submit</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>