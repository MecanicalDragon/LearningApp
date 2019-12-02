$(document).ready(function() {
    setUpButtons();
    setUpHeader($('.brand-header'));
    setUpFooter($('.brand-footer'));
    setUpMinPageHeight();

    $(window).on('resize', function(){
        setUpMinPageHeight();
    });

    $('#openHelp').click(function () {
        $('#helpModal').modal({ show: true });
    });
});

function setUpMinPageHeight() {
    var bodyHeight = $('body').height();
    var windowHeight = $(window).height();
    if (bodyHeight < windowHeight) {
        var main = $('.main');
        var finalHeight = main.height() + (windowHeight - bodyHeight);
        main.height(finalHeight);
    }
}

function setUpButtons() {
    $('.form-select').selectbox();
    $('.form-radio').radio();
    $('.form-checkbox').checkbox();
}

function setUpHeader(header) {
    header.fixation({
        offsetTop: function() {
            return -$('.brandbar').height()
        }
    });
    fixHorizontally(header);
}

function setUpFooter(footer) {
    fixHorizontally(footer);
}

function fixHorizontally(element) {
    $(window).scroll(function() {
        if ($(window).width() < 640) {
            if (element.hasClass('fixed')) {
                element.removeClass('fixed')
            }
        }

        if (!element.hasClass('fixed')) {
            element.css({
                'left': $(this).scrollLeft()
            });
        } else {
            element.css({
                'left': ''
            });
        }
    });
}
