$(function() {
    nameList();
    animateTit()
});
var nameList = function() {
    var e = $(".caroufredsel_wrapper"),
    f = 0,
    c = null,
    d = 155,
    a = 0;
    var g = e.html();
    e.html(g + g);
    c = setInterval(b, 7000);
    function b() {
        a++;
        f = f + d;
        e.animate({
            top: -f
        },
        function() {
            if (a == 2) {
                a = 0;
                f = 0;
                e.css({
                    top: f
                })
            }
        })
    }
    e.hover(function() {
        clearInterval(c)
    },
    function() {
        c = setInterval(b, 2000)
    })
};
var animateTit = function() {
    var b = $("#an1");
    var a = $("#an2");
    if ($.browser.msie && ($.browser.version == "8.0" || $.browser.version == "7.0")) {
        b.css({
            top: "-300px"
        });
        a.css({
            top: "-400px"
        });
        b.delay(800).animate({
            top: "84px"
        },
        600);
        a.delay(0).animate({
            top: "310px"
        },
        800)
    } else {
        b.css({
            opacity: "0",
            top: "-200px",
        });
        a.css({
            opacity: "0",
            top: "-300px",
        });
        b.delay(800).animate({
            opacity: "1",
            top: "84px"
        },
        600,
        function() {
            $(this).find("img").addClass("scales")
        });
        a.delay(0).animate({
            opacity: "1",
            top: "310px"
        },
        800)
    }
};