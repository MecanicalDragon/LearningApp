$(document).ready(function () {

    // tune some DataPicker settings
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
                START.val(formatStartDate(date[0]));
                DATE_BOX.style.backgroundColor = "white";
                if (date[1] != null) {
                    END.val(formatEndDate(date[1]));
                    DATE_BOX.style.backgroundColor = "lightblue";
                }
            }
        }
    });

    //applying dates function
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

    if (DATA.START !== null && DATA.END !== null) {
        markDates(DATA.START, DATA.END);
    }
});

// function, that cuts off excess days from the month
function verifyDate(date, month, day) {
    while (date.getMonth() !== month) {
        date.setMonth(month);
        date.setDate(--day);
    }
}

function formatStartDate(date) {
    return date.getFullYear() + '-' + ("0" + (date.getMonth() + 1)).slice(-2) + '-' + ("0" + date.getDate()).slice(-2) + 'T00:00:00';
}

function formatEndDate(date) {
    return date.getFullYear() + '-' + ("0" + (date.getMonth() + 1)).slice(-2) + '-' + ("0" + date.getDate()).slice(-2) + 'T23:59:59';
}
