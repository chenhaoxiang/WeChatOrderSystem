<html>
    <head>
        <meta charset="UTF-8">
        <title>卖家商品列表</title>
        <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
    <div class="container">
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
    </body>

</html>

