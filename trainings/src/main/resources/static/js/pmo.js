$(document).ready(function () {

    //change labels text colors
    parseColors();

    // Live search through the table
    const connectionTable = document.getElementById("thatTable");
    const connectionTableRows = connectionTable.rows;
    $("#searchingBox").on("input", function () {
        let pattern = $("#searchingBox").val().toLowerCase();
        for (let i = 1; i < connectionTableRows.length; i++) {
            if (connectionTableRows[i].innerText.toLowerCase().indexOf(pattern) === -1) {
                connectionTableRows[i].setAttribute("hidden", "true");
            } else {
                connectionTableRows[i].removeAttribute("hidden");
            }
        }
    });

    // init DataPicker
    $.fn.datepicker.language['en'] = {
        days: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
        daysShort: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
        daysMin: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
        months: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
        monthsShort: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
        today: 'Today',
        clear: 'Clear',
        dateFormat: 'mm/dd/yyyy',
        timeFormat: 'hh:ii aa',
        firstDay: 0
    };

    // define some variables
    const DATE_BOX = document.getElementById('period');
    const DPICKER = $('.datepicker-here');
    const PERIOD = $('#period');
    const START = $('#start');
    const END = $('#end');
    const DATA = undefined;

    // construct DataPicker
    DPICKER.datepicker({
        language: DATA.LOCALE,
        todayButton: true,
        clearButton: true,
        onSelect: function (formattedDate, date) {
            if (!formattedDate) {
                PERIOD.val('');
                START.val('');
                END.val('');
                DATE_BOX.style.backgroundColor = "white";
            } else {
                PERIOD.val(formattedDate);
                START.val(date[0]);
                DATE_BOX.style.backgroundColor = "white";
                if (date[1] != null) {
                    END.val(date[1]);
                    DATE_BOX.style.backgroundColor = "lightblue";
                }
            }
        }
    });

    const markDates = function (dateA, dateB) {
        let dates = [];
        dates.push(new Date(Date.parse(dateA)));
        dates.push(new Date(Date.parse(dateB)));
        DPICKER.datepicker().data('datepicker').selectDate(dates);
    };

    // function sets values to #start and #end, if they match the pattern
    const datePatternEn = "^[0-1][0-9]\/[0-3][0-9]\/[0-9][0-9][0-9][0-9] - [0-1][0-9]\/[0-3][0-9]\/[0-9][0-9][0-9][0-9]$";
    const datePatternDe = "^[0-3][0-9]\.[0-1][0-9]\.[0-9][0-9][0-9][0-9] - [0-3][0-9]\.[0-1][0-9]\.[0-9][0-9][0-9][0-9]$";
    PERIOD.on('input', function () {

        // getting range string
        let range = PERIOD.val();
        if (range.match(datePatternEn) || range.match(datePatternDe)) {

            // define locale specs
            let splitter, monthPos, dayPos;
            if (range.match(datePatternEn)) {
                splitter = "/";
                monthPos = 0;
                dayPos = 1
            } else {
                splitter = ".";
                monthPos = 1;
                dayPos = 0
            }

            // getting start and end dates array
            let period_ = range.split(" - ");

            // parsing days, months, years
            let dateAmonth = (period_[0].split(splitter)[monthPos] - 1);
            let dateBmonth = (period_[1].split(splitter)[monthPos] - 1);
            let dateAday = (period_[0].split(splitter)[dayPos]);
            let dateBday = (period_[1].split(splitter)[dayPos]);
            let dateAyear = (period_[0].split(splitter)[2]);
            let dateByear = (period_[1].split(splitter)[2]);

            // approximate verification
            if (dateAmonth >= 0 && dateAmonth <= 11 &&
                dateBmonth >= 0 && dateBmonth <= 11 &&
                dateBday > 0 && dateBday < 32 &&
                dateAday > 0 && dateAday < 32 &&
                dateAyear > 1969 && dateAyear < 2500 &&
                dateByear > 1969 && dateByear < 2500) {

                // getting dates
                let dateA = new Date(dateAyear, dateAmonth, dateAday);
                let dateB = new Date(dateByear, dateBmonth, dateBday);

                // if user is trying to break this clockwork system
                verifyDate(dateA, dateAmonth, dateAday);
                verifyDate(dateB, dateBmonth, dateBday);

                // swapping dates in acceding order
                if (dateA > dateB) {
                    let dateTemp = dateA;
                    dateA = dateB;
                    dateB = dateTemp;
                }

                // setting values
                markDates(dateA, dateB);
            }
        } else {
            DATE_BOX.style.backgroundColor = "white";
            START.val("");
            END.val("");
            DPICKER.datepicker().data('datepicker').selectedDates = [];
            DPICKER.datepicker().data('datepicker').minRange = "";
            DPICKER.datepicker().data('datepicker').minRange = "";
            DPICKER.datepicker().data('datepicker').maxRange = "";
            DPICKER.datepicker().data('datepicker').maxRange = ""; //these four similar strings are necessary!
        }
    });

    //Sorting table by ascension/descending
    $(".ascDesc").on("click", function () {
        let id = this.id;
        let col = id.substring(7, 8);
        let order = id.substring(8);
        let cells = [];
        for (let i = 1; i < connectionTableRows.length; i++) {
            cells.push(connectionTableRows[i].cells[col].innerHTML);
        }
        if (col == 2) {
            cells.sort(function (a, b) {
                return Number(a) - Number(b);
            })
        } else {
            cells.sort();
        }
        if (order == 2) {
            cells.reverse();
        }
        for (let i = 0; i < cells.length; i++) {
            for (let t = 1; t < connectionTableRows.length; t++) {
                if (cells[i] == connectionTableRows[t].cells[col].innerHTML) {
                    let nextRow = connectionTableRows[t];
                    connectionTable.deleteRow(t);
                    let newRow = connectionTable.insertRow(1);
                    newRow.outerHTML = nextRow.outerHTML;
                }
            }
        }
        let elId = "ascDesc" + col + (order == 1 ? 2 : 1);
        document.getElementById(id).style.display = "none";
        document.getElementById(elId).style.display = "inline";
    });

});

const path = getPath();

//define page location
function getPath() {
    let pathArray = window.location.pathname.split('/');
    let path = '';
    for (let i = 0; i < pathArray.length; i++) {
        if (i !== pathArray.length - 1 && pathArray[i] !== '') {
            path += '/';
            path += pathArray[i];
        }
    }
    return path;
}

// function, that cuts off excess days from the month
function verifyDate(date, month, day) {
    while (date.getMonth() !== month) {
        date.setMonth(month);
        date.setDate(--day);
    }
}

// 3 drag&drop functions below
function drag(ev) {
    ev.dataTransfer.setData("dragId", ev.target.id);
    ev.dataTransfer.setData("mouseY", ev.clientY.toString());
}

function allowDrop(ev) {
    ev.preventDefault();
}

//when drop
function drop(ev) {
    ev.preventDefault();

    // define <tr> parent node
    let node = ev.target;
    while (node.nodeName !== "TR") {
        node = node.parentElement;
    }

    // extract transfer data
    let dragId = ev.dataTransfer.getData("dragId");
    let mouseYStart = ev.dataTransfer.getData("mouseY");
    let mouseYEnd = ev.clientY;

    let rowId = "connRow" + dragId.substring(8);
    let oldRow = document.getElementById(rowId);

    // if target row is not a dragged row
    if (oldRow !== node) {

        // delete old row, create new row, calculate row index
        let row = document.getElementById(rowId);
        oldRow.parentNode.removeChild(oldRow);

        if (Number(mouseYStart) < Number(mouseYEnd)) {
            node.insertAdjacentHTML("afterend", row.outerHTML);
        } else {
            node.insertAdjacentHTML("beforebegin", row.outerHTML);
        }

        // define new connection order
        let dbRequest = [];
        for (let i = 1; i < docConnectionTable.rows.length; i++) {
            dbRequest.push(docConnectionTable.rows[i].outerHTML.split("id=\"connRow")[1].split("\"")[0]);
        }

        // post new connection position
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: path + '/api/configuration/saveRowIndexes',
            data: JSON.stringify(dbRequest)
        });
    }
}

//show data as graph by clicking on "asGraph"-button
const asGraph = function (id) {
    $("#requestConId").val(id);
    $("#showGraphData").trigger("click");
};

//show data as table by clicking on "asTable"-button
const asTable = function (id) {
    $("#requestConId").val(id);
    $("#showTableData").trigger("click");
};

//change labels text colors, depend on color luminance
const parseColors = function () {
    let thresholdValues = document.getElementsByClassName("thresholdValue");
    for (let i = 0; i < thresholdValues.length; i++) {
        let span = thresholdValues[i];
        let color = span.parentElement.style.backgroundColor.substring(4, span.parentElement.style.backgroundColor.length - 1);
        let rgb = color.split(", ");
        let x = [];
        rgb.forEach(function (i) {
            i = i / 255;
            let z = i <= 0.03928 ? i / 12.92 : Math.pow((i + 0.055) / 1.055, 2.4);
            x.push(z);
        });
        let luminance = (0.2126 * x[0]) + (0.7152 * x[1]) + (0.0722 * x[2]);
        span.style.color = luminance > 0.5 ? "#000000" : "#ffffff";
    }
};

