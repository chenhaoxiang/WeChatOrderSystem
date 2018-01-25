<html>
    <#include "../common/header.ftl">
    <body>

    <div id="wrapper" class="toggled">
        <#--边栏sidebar-->
        <#include "../common/nav.ftl">
        <#--主要内容区域-->
        <div id="page-content-wrapper">
            <#--fluid-流动布局-->
            <div class="container-fluid">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>订单Id</th>
                                <th>姓名</th>
                                <th>手机号
                                </th>
                                <th>地址</th>
                                <th>金额</th>
                                <th>订单状态</th>
                            <#--<th>-->
                            <#--支付方式-->
                            <#--</th>-->
                                <th>支付状态</th>
                                <th>创建时间</th>
                                <th colspan="2">操作</th><!-- 占两列 -->
                            </tr>
                            </thead>
                            <tbody>
                            <#list orderDTOPage.getContent() as orderDTO>
                            <tr>
                                <td>${orderDTO.orderId}</td>
                                <td>${orderDTO.buyerName}</td>
                                <td>${orderDTO.buyerPhone}</td>
                                <td>${orderDTO.buyerAddress}</td>
                                <td>${orderDTO.orderAmount}</td>
                                <td>${orderDTO.getOrderStatusEnum().message}</td>
                                <td>${orderDTO.getPayStatusEnum().message}</td>
                                <td>${orderDTO.createTime}</td>
                                <td>
                                    <a href="/seller/order/detail?orderId=${orderDTO.orderId}">详情</a>
                                </td>
                                <td>
                                    <#if orderDTO.getOrderStatusEnum().message == "新订单">
                                        <a href="/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                                    </#if>
                                </td>
                            </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>

                <#--分页-->
                    <div class="col-md-12 column">
                        <ul class="pagination pull-right">
                        <#if currentPage lte 1>
                            <li class="disabled"><a href="#">首页</a></li>
                        <#else>
                            <li><a href="/seller/order/list?page=1&size=${size}">首页</a></li>
                        </#if>

                        <#-- lte小于等于
                             gte 大于等于
                             lt 小于
                             gt 大于
                        -->
                        <#if currentPage lte 1>
                            <li class="disabled"><a href="#">上一页</a></li>
                        <#else>
                            <li><a href="/seller/order/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                        </#if>
                        <#-- m..n 的语法就是[m,n]，进行循环 -->
                        <#-- m..<n 的语法就是[m,n)，进行循环 -->

                        <#list 1..orderDTOPage.getTotalPages() as index>
                            <#if currentPage == index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#elseif (index-currentPage lte 2 && index-currentPage gte -2) || (currentPage lte 2 && index lte 5) || (orderDTOPage.getTotalPages()-currentPage lte 2 && orderDTOPage.getTotalPages()-index lt 5)>
                                <li><a href="/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                            </#if>
                        </#list>
                        <#--当前页大于等于总页数-->
                        <#if currentPage gte orderDTOPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                        <#else>
                            <li><a href="/seller/order/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                        </#if>
                        <#if currentPage gte orderDTOPage.getTotalPages()>
                            <li class="disabled"><a href="#">尾页</a></li>
                        <#else>
                            <li><a href="/seller/order/list?page=${orderDTOPage.getTotalPages()}&size=${size}">尾页</a></li>
                        </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <#--弹窗-->
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
                    <button onclick="document.getElementById('notice').pause();" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button onclick="location.reload()" type="button" class="btn btn-primary">查看新的订单</button>
                </div>
            </div>
        </div>
    </div>

    <#--播放音乐 loop="loop"循环播放-->
    <audio id="notice" loop="loop">
        <source src="/mp3/song.mp3" type="audio/mpeg" />
    </audio>

    <script src="https://cdn.bootcss.com/jquery/2.2.0/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script>
        var webSocket = null;
        if('WebSocket' in window) {//判断浏览器是否支持
            webSocket = new WebSocket('ws://anyi.nat300.top/webSocket');
        }else{
            alert('该浏览器不支持webSocket');
        }
        webSocket.onopen = function (event) {
            console.log("建立连接");
            setTimeout(function(){webSocket.send("测试")},10000);
        }

        webSocket.onclose = function (event) {
            console.log("连接关闭");
        }


        webSocket.onmessage = function (event) {
            console.log("收到消息:"+event.data);
            //收到消息后可以做一系列事件
            $("#myModal").modal('show');//弹窗
            //播放音乐
            document.getElementById('notice').play();
        }

        webSocket.onerror = function (event) {
            alert("webSocket通信发生错误");
        }

        /**
         * 页面关闭前事件
         */
        window.onbeforeunload = function () {
            webSocket.close();//关闭websocket
        }

    </script>
    </body>

</html>

