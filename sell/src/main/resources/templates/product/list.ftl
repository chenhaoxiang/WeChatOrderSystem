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
                                <th>商品Id</th>
                                <th>名称</th>
                                <th>图片</th>
                                <th>单价</th>
                                <th>库存</th>
                                <th>描述</th>
                                <th>介绍</th>
                                <th>类目</th>
                                <th>创建时间</th>
                                <th>修改时间</th>
                                <th colspan="2">操作</th><!-- 占两列 -->
                            </tr>
                            </thead>
                            <tbody>
                            <#list productInfoPage.getContent() as productInfo>
                            <tr>
                                <td>${productInfo.productId}</td>
                                <td>${productInfo.productName}</td>
                                <td>
                                    <img width="100" height="100" src="${productInfo.productIcon}" alt="${productInfo.productName}">
                                </td>
                                <td>${productInfo.productPrice}</td>
                                <td>${productInfo.productStock}</td>
                                <td>${productInfo.productDescription}</td>
                                <td></td>
                                <td>${productInfo.categoryType}</td>
                                <td>${productInfo.createTime}</td>
                                <td>${productInfo.updateTime}</td>
                                <td>
                                    <a href="/seller/product/index?productId=${productInfo.productId}">修改</a>
                                </td>
                                <td>
                                    <#if productInfo.getProductStatusEnum().message == "在架">
                                        <a href="/seller/product/offSale?productId=${productInfo.productId}">下架</a>
                                    <#else>
                                        <a href="/seller/product/onSale?productId=${productInfo.productId}">上架</a>
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
                            <li><a href="/seller/product/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                        </#if>
                        <#-- m..n 的语法就是[m,n]，进行循环 -->
                        <#-- m..<n 的语法就是[m,n)，进行循环 -->

                        <#list 1..productInfoPage.getTotalPages() as index>
                            <#if currentPage == index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#elseif (index-currentPage lte 2 && index-currentPage gte -2) || (currentPage lte 2 && index lte 5) || (productInfoPage.getTotalPages()-currentPage lte 2 && productInfoPage.getTotalPages()-index lt 5)>
                                <li><a href="/seller/product/list?page=${index}&size=${size}">${index}</a></li>
                            </#if>
                        </#list>
                        <#--当前页大于等于总页数-->
                        <#if currentPage gte productInfoPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                        <#else>
                            <li><a href="/seller/product/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                        </#if>
                        <#if currentPage gte productInfoPage.getTotalPages()>
                            <li class="disabled"><a href="#">尾页</a></li>
                        <#else>
                            <li><a href="/seller/product/list?page=${productInfoPage.getTotalPages()}&size=${size}">尾页</a></li>
                        </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    </body>

</html>

