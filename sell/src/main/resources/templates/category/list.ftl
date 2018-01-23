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
                                <th>类目Id</th>
                                <th>名称</th>
                                <th>type</th>
                                <th>创建时间</th>
                                <th>修改时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list productCategoryList as productCategory>
                            <tr>
                                <td>${productCategory.categoryId}</td>
                                <td>${productCategory.categoryName}</td>
                                <td>${productCategory.categoryType}</td>
                                <td>${productCategory.createTime}</td>
                                <td>${productCategory.updateTime}</td>
                                <td>
                                    <a href="/seller/category/index?categoryId=${productCategory.categoryId}">修改</a>
                                </td>
                            </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>

                </div>
            </div>
        </div>
    </div>

    </body>

</html>

