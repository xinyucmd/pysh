$(function() {
    layout.simple.activity()
});
var layout = {};
layout.simple = {},
layout.simple.activity = function(a) {
    a = a || "";
    var b = {
        currentPageCode: a.toString().length > 0 ? '<span class="currentPage">' + a + "</span>": " "
    },
    c = '<div class="content clearfix"><a href="http://www.lufax.com" target="_blank"><img class="logo" src="https://static.lufaxcdn.com/config/images/logo_festival.png" alt="lufax" /></a><span class="headLink"><a class="goHomepage"  href="http://www.lufax.com" target="_blank">平安陆金所首页</a>' + b.currentPageCode + "</span></div>",
    d = '<div class="content clearfix"><p class="phoneNum">全国服务热线<span class="num">4008&nbsp;6666&nbsp;18</span></p><p class="copyright">版权所有 © 上海陆家嘴国际金融资产交易市场股份有限公司未经许可不得复制、转载或摘编，违者必究!<br/>Copyright Shanghai Lujiazui International Financial Asset Exchange Co.,LTD. ALL Rights Reserved<br/>沪 ICP 证 B2-20120023号&emsp;&emsp;&emsp;沪 ICP 备 12032241 号-2</p></div>';
    $(".header").empty().append(c),
    $(".footer").empty().append(d)
};