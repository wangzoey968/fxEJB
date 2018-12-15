<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1 , maximum-scale=1,user-scalable=no">
    <%@ include file="/cusweb/headNav.jsp"%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/cusweb/product/product.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/cusweb/product/zhanBan/inHouseZhanBan.js?v=2018-11-19"></script>
    <title>户内展板</title>
    <style>
        div#paper-group a {
            width: 100px;
        }
        div#baoBian-group a {
            width: 110px;
        }
        div#pen-group a {
            width: 100px;
        }
    </style>
</head>
<body>

<div class="container container_border">
    <div class="row">
        <div class="gallery col-lg-4 col-md-4 col-sm-4">
            <div class="selected-images" id="source">
                <img id="s-images" src="${pageContext.request.contextPath}/cusweb/image/product/gallery/xieZhen-1.jpg">
            </div>
            <div class="list-images" id="img-list" style="margin-top: 20px;">
                <div class="row">
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
                        <a href="javascript:void(0)" class="active"><img src="${pageContext.request.contextPath}/cusweb/image/product/gallery/xieZhen-1.jpg"></a>
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
                        <a href="javascript:void(0)"><img src="${pageContext.request.contextPath}/cusweb/image/product/gallery/xieZhen-2.jpg"></a>
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
                        <a href="javascript:void(0)"><img src="${pageContext.request.contextPath}/cusweb/image/product/gallery/xieZhen-3.jpg"></a>
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
                        <a href="javascript:void(0)"><img src="${pageContext.request.contextPath}/cusweb/image/product/gallery/comGallery-1.jpg"></a>
                    </div>
                </div>
            </div>
        </div>
        <%--户内展板参数选择--%>
        <div class="zhanBanCalPrice col-lg-8 col-md-8 col-sm-8" style="position: relative;">
            <h3>户内展板</h3>
            <form class="sj-right-form">
                <table class="table-price">
                    <tbody>
                    <!--品种-->
                    <tr class="tr-price">
                        <td class="notSelectColor">
                            品种：
                        </td>
                        <td>
                            <div class="btn-group" id="paper-group">
                            </div>
                        </td>
                    </tr>
                    <!--规格-->
                    <tr class="tr-price">
                        <td style="padding-top: 6px;">规格：</td>
                        <td>
                            <div class="input-group sizeSet" style="margin:5px 8px;">
                                <input type="number"
                                       style="width: 47px;padding-left: 6px;margin: 0;"
                                       class="widthSet" placeholder="m">
                                X
                                <input type="number" style="width: 47px;padding-left: 6px;margin: 0;"
                                       class="heightSet" placeholder="m">
                            </div>
                        </td>
                    </tr>
                    <!--单双面-->
                    <tr class="tr-price">
                        <td class="notSelectColor">单双面：</td>
                        <td>
                            <div class="btn-group" id="printFace-group">
                            </div>
                        </td>
                    </tr>
                    <!--印刷数量-->
                    <tr class="tr-price">
                        <td>数量：</td>
                        <td>
                            <div>
                                <div class="input-group spinner"
                                     style="width: 88px; padding: 5px 0; float: left;">

                                </div>
                            </div>
                        </td>
                    </tr>
                    <!--覆膜-->
                    <tr class="tr-price">
                        <td>覆膜：</td>
                        <td>
                            <div class="btn-group" id="fuMo-group">
                            </div>
                        </td>
                    </tr>
                    <!--其它-->
                    <tr class="tr-price">
                        <td>其它：</td>
                        <td>
                            <div class="btn-group" id="pen-group">
                            </div>
                            <%--<div id="daKou-group">
                            </div>--%>
                            <div style="position: relative;">
                                <a id="daKouTip" class="btn btn-default btnCell" style="margin-left: 8px;width: 80px;">打扣</a>
                                <div class="box-border" id="daKouOptions">
                                    <div>
                                        <a class="btn btn-default btnCell" style="border:0 none;margin: 0;padding: 4px 0">沿边打扣</a>
                                    </div>
                                    <div>
                                        <a class="btn btn-default btnCell" style="border:0 none;margin: 0;padding: 4px 0">四角打扣</a>
                                    </div>
                                </div>
                                <a id="daKouSelect" class="btn btn-default btnCell btnCell-selected" style="margin-left: 6px;display: none;width:80px;">凹凸</a>
                            </div>
                        </td>
                    </tr>
                    <%--包边--%>
                    <tr class="tr-price">
                        <td>包边：</td>
                        <td>
                            <div id="baoBian-group">
                            </div>
                        </td>
                    </tr>
                    <%--款数--%>
                    <tr class="tr-price">
                        <td>款数：</td>
                        <td>
                            <div id="type" style="width: 88px;padding: 5px 0;float: left;" class="input-group"></div>
                        </td>
                    </tr>
                    <%--<!--价格-->--%>
                    <tr class="tr-price">
                        <td style="vertical-align:middle;">价格：</td>
                        <td>
                            <div class="btn-group" id="price-group" style="margin-left: 8px;">
                                <div id="result"><span style="font-size:16px;margin-right:4px;">____</span>元</div>
                                <%--<div>出货时间：<span id="deliveTime"></span></div>--%>
                            </div>
                        </td>
                    </tr>
                    <%--参数--%>
                    <tr class="tr-price">
                        <td>参数：</td>
                        <td>
                            <div id="orderDesc">

                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
            <%--<div>
                <label for="mingPianFiles" class="btn btn_FileInfo"
                       style="padding: 6px;vertical-align:top;">选择文件</label>
                <input id="mingPianFiles" style="position:absolute;clip:rect(0 0 0 0);" type="file" name="fileupload">
                <div style="display:inline-block;min-height: 34px;line-height:34px;width: 300px;border:1px solid #dedede;border-radius: 6px;" class="webOrder">
                    <span style="color: #d4d7e2;background-color: #fafbfe; font-size: 24px;font-family: '黑体';">或 拖拽文件到此页面</span>
                </div>
                <button class="btn btn_filePrimary" style="vertical-align:top;" id="fileUpload">上传下单</button>
            </div>--%>
        </div>
    </div>
</div>

<%--遮罩--%>
<div class="modal fade" id="modal-mask" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false" data-keyboard="false" style="background-color:rgba(66,66,66,.5);">
    <div class="modal-dialog" style="width: 500px;margin-top: 20%;">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">正在上传</h4>
            </div>
            <div class="modal-body">
                <div class="progress" style="width: 430px;">
                    <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;">
                        0%
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" id="btn-abort">取消上传</button>
            </div>
        </div>
    </div>
</div>
<%--产品详情--%>
<div class="containerBox clearfix" style="margin-top:40px; padding:0">
    <div class="newOrderR_left">
        <%@ include file="/cusweb/product/productHot.jsp" %>
    </div>
    <div class="newOrderR_right">
        <ul class="newOrderR_tab">
            <li class="newOrderR_list1 cur"><a href="javascript:void(0);">产品介绍</a></li>
        </ul>
        <img src="${pageContext.request.contextPath}/cusweb/image/product/comDesc/comDesc0.jpg">
        <%--<img src="${pageContext.request.contextPath}/cusweb/image/product/mingpian/mPPrice1.jpg">
        <img src="${pageContext.request.contextPath}/cusweb/image/product/mingpian/mPPrice2.jpg">
        <img src="${pageContext.request.contextPath}/cusweb/image/product/mingpian/mPPrice3.jpg">
        <img src="${pageContext.request.contextPath}/cusweb/image/product/mingpian/mPPrice4.jpg">
        <img src="${pageContext.request.contextPath}/cusweb/image/product/mingpian/mPPrice5.jpg">
        <img src="${pageContext.request.contextPath}/cusweb/image/product/mingpian/mPDesc0.jpg">--%>
        <%--<img src="../image/product/mingpian/mPDesc1.jpg">--%>

        <%@ include file="/cusweb/product/comDesc.jsp" %>
    </div>
</div>
<%@ include file="/Copyright.jsp" %>
</body>
</html>