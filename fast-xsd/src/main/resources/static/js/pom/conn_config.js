$(document).ready(function () {
    $('.notification').hide();
    initColourPickers();

    //saving new configuration method
    $('#connConfig').submit(function (event) {
        event.preventDefault();
        saveConfiguration();
    });

    $(".threshold").on("focusout", function () {
        let row = this.parentElement.parentElement.children;
        let values = [];
        let fields = [];
        for (let i = 0; i < row.length; i++) {
            if (row[i].nodeName === "TD" && row[i].getAttribute("class") === "thresholdTd") {
                if (row[i].children[0].value > 100) {
                    row[i].children[0].value = 100;
                }
                values.push(row[i].children[0].value);
                fields.push(row[i].children[0])
            }
        }
        values.sort(function (a, b) {
            return Number(a) - Number(b);
        });
        for (let i = 0; i < fields.length; i++) {
            fields[i].value = values[i];
        }
    });

    // just for
    // $('.alias').on("focusout", function () {
    //     let length = this.value.length;
    //     if (length > 255) {
    //         this.value = this.value.substring(0, 255);
    //         let not = $('.notification');
    //         not.removeClass('notification-positive').addClass('notification-negative').text(DATA.MORE_255.replace("%s", length));
    //         not.slideDown().delay(5000).slideUp();
    //     }
    // });

    //saving new configuration method. Part 2.
    function saveConfiguration() {

        let not = $('.notification');
        not.removeClass('notification-negative').addClass('notification-positive').text(DATA.SAVING_NEW_CONFIG);

        //getting account information
        let accountInput = document.getElementById('account');
        let account = {
            id: accountInput.dataset.id,
        };

        //getting new connections information
        let someData = [];
        $('.input-table > tbody > tr').each(function () {
            if (this.hidden !== true) {
                let thresholds = new Map();
                $(this).find('.threshold').each(function () {
                    let threshold = {
                        value: $(this).val(),
                        color: hex($(this).parent().next().find('.bcPicker-picker').css('background-color'))
                    };
                    thresholds.set(this.dataset.type, threshold);
                });
                let sData = {
                    id: this.dataset.id,
                    alias: $(this).find('.alias').val(),
                    t1: thresholds.get('T1'),
                    t2: thresholds.get('T2'),
                    t3: thresholds.get('T3'),
                    t4: thresholds.get('T4')
                };
                someData.push(sData);
            }
        });

        //getting path
        let pathArray = window.location.pathname.split('/');
        let path = '';
        for (let i = 0; i < pathArray.length; i++) {
            if (i !== pathArray.length - 1 && pathArray[i] !== '') {
                path += '/';
                path += pathArray[i];
            }
        }

        //post data
        console.log("Updating data for account with id: " + account.toString());
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: path + '/save',
            data: JSON.stringify(someData),
            // dataType: 'json',
            dataType: 'text',
            success: function (response) {
                if (response === 'OK') {
                    not.removeClass('fail-note').addClass('success-note').text(DATA.SUCCESS_RESPONSE);
                    window.location.reload(true);
                } else {
                    not.removeClass('success-note').addClass('fail-note').text(DATA[response]);
                }
            },
            error: function (e) {
                not.removeClass('success-note').addClass('fail-note').text(e.responseText);
            }
        });

        not.slideDown().delay(10000).slideUp();
    }

    // init colorPicker
    function initColourPickers() {
        $('.color-picker').each(function () {
            $(this).bcPicker({defaultColor: this.dataset.color});
        });
    }

    //parse color for definition of text color, basing on label brightness
    function hex(rgb) {
        let parts = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
        delete (parts[0]);
        for (let i = 1; i <= 3; ++i) {
            parts[i] = parseInt(parts[i]).toString(16);
            if (parts[i].length === 1) parts[i] = '0' + parts[i];
        }
        return '#' + parts.join('');
    }
});