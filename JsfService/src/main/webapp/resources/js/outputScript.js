$(document).ready(function () {
    let input1 = $("#form1\\:inp1");
    let input2 = $("#form1\\:inp2");
    let input55 = $("#form1\\:inp55");

    input55.on('input', function () {
        console.log("in")
    })

    input2.blur(function () {
        console.log("ok")
    })

    input1.on('input', function () {
        input2.val(input1.val());
    })
    let btn = $("#form1\\:btn1");
    btn.on('click', function () {
        console.log('clicked!')
        let i = 0;
        let f = setInterval(function () {
            if (i++ > 3) {
                console.log("finish")
                clearInterval(f)
            } else {
                console.log("flow");
            }
        }, 500)
    })

    let launchJS = $("#form1\\:launchJs");
    console.log(launchJS)
    launchJS.on("click", function () {
        let input3 = $("#form1\\:inp3");
        console.log(input3)
        input3.trigger("change");
    })
});