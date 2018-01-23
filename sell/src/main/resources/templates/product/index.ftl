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
                    <form role="form" method="post" action="/seller/product/save">
                        <div class="form-group">
                            <label>名称</label>
                            <input name="productName" type="text" class="form-control" value="${(productInfo.productName)!''}"/>
                        </div>

                        <div class="form-group">
                            <label>价格</label>
                            <input name="productPrice" type="text" class="form-control" value="${(productInfo.productPrice)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>库存</label>
                            <input name="productStock" type="number" class="form-control" value="${(productInfo.productStock)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>描述</label>
                            <input name="productDescription" type="text" class="form-control" value="${(productInfo.productDescription)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>图片</label>
                            <img width="200" height="200" src="${(productInfo.productIcon)!''}">
                            <input name="productIcon" type="text" class="form-control" value="${(productInfo.productIcon)!''}"/>
                        </div>

                        <div class="form-group">
                            <label>类目</label>
                            <select name="categoryType" class="form-control">
                                <#list productCategoryList as productCategory>
                                    <#--商品类目存在 (productInfo.categoryType)?? -->
                                    <option value="${productCategory.categoryType}"
                                    <#if (productInfo.categoryType)?? && productInfo.categoryType==productCategory.categoryType>
                                        selected
                                    </#if>
                                    >
                                    ${productCategory.categoryName}
                                    </option>
                                </#list>
                            </select>
                        </div>

                        <#--加个隐藏的字段-->
                        <input hidden type="text" name="productId" value="${(productInfo.productId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>

        </div>
    </div>
</div>

</body>

</html>

