<!-- Created by Stanislav Tretyakov-->
<!--19.09.2018-->
// Ajax requests + jQuery

var btn = $("#environmentMenuButton");
var environment;
var allOn = $("#allOn");
var allOff = $("#allOff");
var SUCCESS_MSG = "XSD-update was successful: all apps have refreshed their validation status.";
var SUCCESS = "SUCCESS";
var UNCHANGED = "UNCHANGED";
var FAILURE = "FAILURE";
var requestCounter = 0;

// before send function
function beforeSend() {
    requestCounter++;
    btn.text("Waiting...");
    btn.prop("disabled", true);
    allOn.prop("disabled", true);
    allOff.prop("disabled", true);
}

// bad response function
function badResponse(resp, env) {
    unlockBtns(env);
    console.log("Response from app was:\n" + resp.toString());
    alert("Something went wrong. Try again. Perhaps you can find some info in console");
}

// 'loading'-image function
function loading(onOff) {
    if (onOff === true) {
        $("#gif").append('<img src="img/loading.gif" class="rounded mx-auto d-block" alt="Responsive image">');
    } else {
        $("#gif").empty();
    }
}

// unlock all disabled buttons
function unlockBtns(env) {
    if (--requestCounter === 0) {
        btn.text(env);
        btn.prop("disabled", false);
        allOn.prop("disabled", false);
        allOff.prop("disabled", false);
    }
}

// success response function
function status200(resp, env) {
    unlockBtns(env);
    var i = 0;
    resp.forEach(function (entry) {
        var checked = entry.switched;
        $("#list").append(
            '<div class="row align-items-center" id="row-' + entry.id + '"' + (i % 2 === 0 ? ' style="background-color: #D8F4DD;"' : '') + '>' +
            '<div class="col-2 text-center"></div>' +
            '<div class="col-2 text-center">' + (entry.dienst === null ? "" : entry.dienst) + '</div>' +
            '<div class="col-3 text-center">' + (entry.dienstauspraegung === null ? "" : entry.dienstauspraegung) + '</div>' +
            '<div class="btn-group btn-group-sm col-3">\n' +
            '<button onclick="singleChange(this)" style="width:100px;" class="btn btn-success changer"' +
            (checked ? ' disabled' : '') + ' id="' + entry.id + '-on-' + entry.anwendsungsname + '" >Turn' + (checked ? 'ed' : '') + ' on\n' +
            '</button>\n' +
            '<button onclick="singleChange(this)" style="width:100px;" class="btn btn-danger changer"' +
            (!checked ? ' disabled' : '') + ' id="' + entry.id + '-off-' + entry.anwendsungsname + '" >Turn' + (!checked ? 'ed' : '') + ' off\n' +
            '</button>\n' +
            '</div>' +
            '<div class="col-2 text-start"><span id="comment-' + entry.id + '">' + (entry.comment == null ? "" :
            (entry.comment === SUCCESS || entry.comment === UNCHANGED ? entry.comment : FAILURE)) + '</span>' +
            '</div></div>');
        if (entry.comment != null && entry.comment !== SUCCESS && entry.comment !== UNCHANGED) {
            console.log(entry.comment);
        }
        changeColor("#comment-" + entry.id, entry.comment);
        ++i;
    });
}

// show success message function
function changeColor(id, state) {
    $(id).removeClass("text-danger");
    $(id).removeClass("text-success");
    $(id).removeClass("text-primary");
    if (state === SUCCESS) {
        $(id).addClass("text-success");
        $(id).html("<b>SUCCESS</b>");
    } else if (state === UNCHANGED) {
        $(id).addClass("text-primary");
        $(id).html("<b>UNCHANGED</b>");
    } else if (state != null) {
        $(id).addClass("text-danger");
        $(id).html("<b>FAILURE</b>");
    }
    $(id).show();
    $(id).fadeOut(5000);
}

// choosing environment function
$(".chooser").click(function () {
    environment = $(this).attr("id");
    $.ajax('getItems', {
        method: "GET",
        dataType: "json",
        data: 'env=' + environment,
        beforeSend: function () {
            $("#list").empty();
            beforeSend();
            loading(true);
        },
        error: function (resp) {
            loading(false);
            badResponse(resp, environment);
        },
        success: function (resp) {
            loading(false);
            status200(resp, environment);
        }
    });
});

// mass status changes
$(".mass-changer").click(function () {
    $.ajax('massChange', {
        method: "POST",
        data: {env: environment, change: $(this).attr("id")},
        beforeSend: function () {
            $("#list").empty();
            beforeSend();
            loading(true);
        },
        error: function (resp) {
            loading(false);
            badResponse(resp, environment);
        },
        success: function (resp) {
            loading(false);
            status200(resp, environment);
        }
    });
});

// single status changing
function singleChange(pushed) {
    var btnId = $(pushed).attr("id");
    $.ajax('singleChange', {
        method: "POST",
        data: {env: environment, change: $(pushed).attr("id")},
        dataType: "text",
        beforeSend: function () {
            $(pushed).prop('disabled', true);
            beforeSend();
        },
        error: function (resp) {
            $(pushed).prop('disabled', false);
            badResponse(resp, environment);
        },
        success: function (resp) {
            unlockBtns(environment);
            if (resp.toString() === SUCCESS) {
                changeColor($("#comment-" + btnId.split("-")[0]), resp.toString());
                var newId;
                if (btnId.split("-")[1] === "off") {
                    newId = btnId.split("-")[0] + "-on-" + btnId.split("-")[2];
                    $("#" + newId).prop('disabled', false);
                } else {
                    newId = btnId.split("-")[0] + "-off-" + btnId.split("-")[2];
                    $("#" + newId).prop('disabled', false);
                }
                if ($(pushed).text().trim() === "Turn on") {
                    $(pushed).text("Turned on");
                    $("#" + newId).text("Turn off");
                } else {
                    $(pushed).text("Turned off");
                    $("#" + newId).text("Turn on");
                }
                console.log(SUCCESS_MSG);
            } else {
                $(pushed).prop('disabled', false);
                changeColor($("#comment-" + btnId.split("-")[0]), FAILURE);
                console.log(resp);
            }
        }
    });
}

//joker function
$("#joker").click(function () {
    $.ajax('getJoke', {
        method: "GET",
        dataType: "text",
        beforeSend: function () {
            $(this).prop('disabled', true);
        },
        success: function (resp) {
            $(this).prop('disabled', false);
            console.log(resp);
        }
    });
});