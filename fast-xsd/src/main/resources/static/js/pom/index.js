$(document).ready(function () {

    //lang selection
    $('#language-select').change(function () {
        var selectedOption = $('#language-select').val();
        window.location.replace('?lang=' + selectedOption);
    });

    //change threshold labels text colors
    parseColors();

    // checking fulfillment of all data before request
    $('#selectData').submit(function () {
        if ($('#start').val() !== "" && $('#end').val() !== "") {
            return true;
        } else {
            openModal(DATA.EMPTY_DATES);
            return false;
        }
    });

    //searching through the "select connection"-table
    $("#searchingBox").on("input", function () {
        pagData.matchPattern = $("#searchingBox").val().toLowerCase();
        pag();
    });

    //Sorting connection table by ascension/descending   //   METHOD IS BOUNDED WITH DOM STRUCTURE
    $(".ascDesc").on("click", function () {
        event.preventDefault();
        let id = this.id;
        let col = id.substring(7, 8);
        let order = id.substring(8);

        let connections = Array.prototype.slice.call(docConnectionTableBody.rows);

        if (col == 2) {
            connections.sort(function (a, b) {
                return Number(a.cells[col].innerHTML) - Number(b.cells[col].innerHTML);
            })
        } else {
            connections.sort(function (a, b) {
                return a.cells[col].innerHTML.localeCompare(b.cells[col].innerHTML);
            });
        }
        if (order == 1) {
            connections = connections.reverse();
        }

        let parent = docConnectionTableBody;
        for (let i = 0; i < connections.length; i++) {
            let row = parent.removeChild(connections[i]);
            row.hidden = true;
            parent.appendChild(row);
        }

        sortConnections();

        let elId = "ascDesc" + col + (order == 1 ? 2 : 1);
        document.getElementById(id).style.display = "none";
        document.getElementById(elId).style.display = "inline";
        $("#" + elId).focus();
    });

    //fast date selector
    $('#quickSelection').change(function () {
        let datepicker = $('.datepicker-here').datepicker().data('datepicker');
        let dates = [];
        let today = new Date();

        let selectedPeriod = $('#quickSelection').val();
        switch (selectedPeriod) {
            case 'lastYear':
                dates.push(new Date(new Date().setFullYear(today.getFullYear() - 1, 0, 1)));
                dates.push(new Date(new Date().setFullYear(today.getFullYear(), 0, 0)));
                break;
            case 'lastQuarter':
                dates.push(new Date(new Date().setMonth(today.getMonth() - 3, 1)));
                dates.push(new Date(new Date().setDate(0)));
                break;
            case 'lastMonth':
                dates.push(new Date(new Date().setMonth(today.getMonth() - 1, 1)));
                dates.push(new Date(new Date().setDate(0)));
                break;
            case 'lastWeek':
                dates.push(new Date(new Date().setDate(today.getDate() - today.getDay() - 6)));
                dates.push(new Date(new Date().setDate(today.getDate() - today.getDay())));
                break;
            case 'yesterday':
                let yesterday = new Date(new Date().setDate(today.getDate() - 1));
                dates.push(yesterday);
                dates.push(yesterday);
                break;
            case 'today':
                dates.push(today);
                dates.push(today);
                break;
            case 'thisWeek':
                dates.push(new Date(new Date().setDate(today.getDate() - today.getDay() + 1)));
                dates.push(today);
                break;
            case 'thisMonth':
                dates.push(new Date(new Date().setDate(1)));
                dates.push(today);
                break;
            case 'thisQuarter':
                dates.push(new Date(new Date().setMonth(today.getMonth() - 2, 1)));
                dates.push(today);
                break;
            case 'thisYear':
                dates.push(new Date(new Date().setMonth(0, 1)));
                dates.push(today);
                break;
            default:
                datepicker.clear();
                break;
        }
        datepicker.selectDate(dates);
    });

    //do the pagination
    pag();

});

const docConnectionTable = document.getElementById("connectionTable");
const docConnectionTableBody = document.getElementById("someDataTableBody");
const docConfigTableBody = document.getElementById("configTableBody");
const path = getPath();
const nextButtons = document.getElementsByClassName("nextPageButton");
const prevButtons = document.getElementsByClassName("prevPageButton");
const shittyPag = document.getElementById("shittyPagParent").children;
const pagData = {
    version: 2,
    matchPattern: "",
    connections: docConnectionTableBody.rows,
    arrayLength: docConnectionTableBody.rows.length,
    pageSize: 10,
    rowsShown: 0,
    rowsInPage: 0,
    bottomLine: 0,
    upperThreshold: 10,
    currentPage: 1,
    totalPages: 1,
    panel: {1: 1, 2: 2, 3: 3, 4: 4, 5: 5, 6: 6, 7: 7, 8: 8, 9: 9}
};
let saveAnimationInProcess = false;
let changesPerformedButAnimationStillInProcess = false;

//prepare pagination function
const pag = function () {

    let pageScrollBoolean = pagData.rowsShown < pagData.pageSize;
    let connections = pagData.connections;
    let arrayLength = 0;
    pagData.rowsShown = 0;
    pagData.rowsInPage = 0;
    let balancer = false;
    for (let i = 0, matching = 0; i < connections.length; i++) {

        //if matches pattern
        if (connections[i].innerText.toLowerCase().indexOf(pagData.matchPattern) >= 0) {

            matching++;
            connections[i].filtered2 = false;         //remove filtered mark

            if (matching <= pagData.pageSize) {       //hide if excess
                connections[i].hidden = false;
                pagData.rowsShown++;                //incr shown rows
                if (matching === pagData.pageSize) {
                    balancer = true;
                }
            } else {
                connections[i].hidden = true;
            }

            if (matching < pagData.pageSize || balancer) {
                pagData.rowsInPage++;           //incr rows in page
                balancer = false;
            }
            arrayLength++;                  //incr summary amount of filtered entries

            //if doesn't match pattern
        } else {
            if (matching < pagData.pageSize || balancer) {       //count rows in page, including hidden
                pagData.rowsInPage++;
                balancer = false;
            }
            connections[i].filtered2 = true;
            connections[i].hidden = true;
        }

    }
    pagData.arrayLength = arrayLength;
    pagData.totalPages = arrayLength % pagData.pageSize !== 0
        ? Math.ceil(arrayLength / pagData.pageSize) : arrayLength / pagData.pageSize;
    pagData.currentPage = 1;
    pagData.bottomLine = 0;
    pagData.upperThreshold = pagData.bottomLine + pagData.rowsInPage;
    document.getElementById("currentPageLi").innerText = pagData.totalPages === 0 ? "0" : "1/" + pagData.totalPages;

    for (let i = 0; i < prevButtons.length; i++) {
        prevButtons[i].setAttribute("disabled", "disabled");
        prevButtons[i].style.cursor = "default";
    }

    if (pagData.upperThreshold === connections.length) {
        for (let i = 0; i < nextButtons.length; i++) {
            nextButtons[i].setAttribute("disabled", "disabled");
            nextButtons[i].style.cursor = "default";
        }
    } else {
        for (let i = 0; i < nextButtons.length; i++) {
            nextButtons[i].removeAttribute("disabled");
            nextButtons[i].style.cursor = "pointer";
        }
    }

    if (pagData.version === 2) {
        redrawPaginationPanel();
    }

    if (pageScrollBoolean) {
        setTimeout(function () {
            window.scrollTo(0, document.body.scrollHeight);
        }, 10);
    }
};
// change Page
const changePage = function (x, e) {
    e.preventDefault();
    let diff = pagData.panel[x] - pagData.currentPage;
    if (diff > 0) {
        nextPage(Math.abs(diff), e);
    } else if (diff < 0) {
        prevPage(Math.abs(diff), e);
    } else {
    }
};

//redraw pagination panel
const redrawPaginationPanel = function () {
    if (pagData.totalPages > shittyPag.length - 2) {
        let cbn = Math.floor((shittyPag.length - 2) / 2 + 1);

        if (pagData.currentPage <= cbn) {
            for (let j = 1; j < shittyPag.length - 1; j++) {
                pagData.panel[j] = j;
                shittyPag[j].style.display = "inline";
                shittyPag[j].children[0].innerText = j;
                if (j === shittyPag.length - 3) {
                    shittyPag[j].children[0].innerText = "...";
                }
                if (j === shittyPag.length - 2) {
                    shittyPag[j].children[0].innerText = pagData.totalPages;
                    pagData.panel[j] = pagData.totalPages;
                }
                shittyPag[j].style.border = pagData.panel[j] === pagData.currentPage
                && pagData.panel[j] == shittyPag[j].children[0].innerText ? "magenta 1px solid" : "#ededed solid 1px";
            }
        } else {
            if (pagData.currentPage > pagData.totalPages - cbn) {
                for (let j = shittyPag.length - 2, n = 0; j > 0; j--, n++) {
                    pagData.panel[j] = pagData.totalPages - n;
                    shittyPag[j].style.display = "inline";
                    shittyPag[j].children[0].innerText = pagData.totalPages - n;
                    if (j === 2) {
                        shittyPag[j].children[0].innerText = "...";
                    }
                    if (j === 1) {
                        shittyPag[j].children[0].innerText = 1;
                        pagData.panel[j] = 1;
                    }
                    shittyPag[j].style.border = pagData.panel[j] === pagData.currentPage
                    && pagData.panel[j] == shittyPag[j].children[0].innerText ? "magenta 1px solid" : "#ededed solid 1px";
                }
            } else {
                let shift = cbn - 2;
                let page = pagData.currentPage - shift;
                for (let j = 1; j < shittyPag.length - 1; j++) {
                    shittyPag[j].style.display = "inline";
                    if (j > 1 && j < shittyPag.length - 2) {
                        pagData.panel[j] = page;
                        shittyPag[j].children[0].innerText = page++;
                    }
                    shittyPag[j].style.border = pagData.panel[j] === pagData.currentPage
                    && pagData.panel[j] == shittyPag[j].children[0].innerText ? "magenta 1px solid" : "#ededed solid 1px";
                }
                pagData.panel[1] = 1;
                pagData.panel[shittyPag.length - 2] = pagData.totalPages;
                shittyPag[1].children[0].innerText = 1;
                shittyPag[shittyPag.length - 2].children[0].innerText = pagData.totalPages;
                shittyPag[2].children[0].innerText = "...";
                shittyPag[shittyPag.length - 3].children[0].innerText = "...";
            }
        }
    } else {
        for (let i = 1; i < shittyPag.length - 1; i++) {
            pagData.panel[i] = i;
            if (i <= pagData.totalPages) {
                shittyPag[i].style.display = "inline";
                shittyPag[i].children[0].innerText = i;
            } else {
                shittyPag[i].style.display = "none";
            }
            shittyPag[i].style.border = i === pagData.currentPage ? "magenta 1px solid" : "#ededed solid 1px";
        }
    }
};

//sort connections   //   METHOD IS BOUNDED WITH DOM STRUCTURE
const sortConnections = function () {

    let connections = pagData.connections;
    pagData.rowsShown = 0;
    pagData.rowsInPage = 0;

    let entriesMustBePassed = (pagData.currentPage - 1) * pagData.pageSize;
    for (let i = 0, matching = 0, blocker = false; i < connections.length && pagData.rowsShown < pagData.pageSize; i++) {
        if (matching >= entriesMustBePassed) {
            if (matching === entriesMustBePassed && !blocker) {
                pagData.bottomLine = i;
                blocker = true;
            }
            pagData.rowsInPage++;
            if (!connections[i].filtered2) {
                connections[i].hidden = false;
                pagData.rowsShown++;
            }
        }

        if (!connections[i].filtered2) {
            matching++;
        }
    }
    pagData.upperThreshold = pagData.bottomLine + pagData.rowsInPage;
};

//next Page
function nextPage(x, e) {
    e.preventDefault();
    if (nextButtons[0].getAttribute("disabled") !== "disabled") {

        let connections = docConnectionTableBody.rows;
        let entriesMustBePassed = pagData.currentPage + x > pagData.totalPages
            ? (pagData.totalPages - pagData.currentPage) * pagData.pageSize : pagData.pageSize * x;

        pagData.rowsShown = 0;
        pagData.rowsInPage = 0;

        for (let i = pagData.bottomLine, blocker = false, matching = 0;
             i < connections.length && pagData.rowsShown < pagData.pageSize; i++) {

            if (matching < entriesMustBePassed) {
                connections[i].hidden = true;
            } else {
                if (matching === entriesMustBePassed && !blocker) {
                    pagData.bottomLine = i;
                    blocker = true;
                }
                pagData.rowsInPage++;
                if (!connections[i].filtered2) {
                    connections[i].hidden = false;
                    pagData.rowsShown++;
                }
            }

            if (!connections[i].filtered2) {
                matching++;
            }
        }

        pagData.upperThreshold = pagData.bottomLine + pagData.rowsInPage;
        pagData.currentPage = pagData.currentPage + x > pagData.totalPages ? pagData.totalPages : pagData.currentPage + x;
        document.getElementById("currentPageLi").innerText = "" + pagData.currentPage + "/" + pagData.totalPages;

        if (pagData.upperThreshold === pagData.connections.length) {
            for (let i = 0; i < nextButtons.length; i++) {
                nextButtons[i].setAttribute("disabled", "disabled");
                nextButtons[i].style.cursor = "default";
            }
        }
        for (let i = 0; i < prevButtons.length; i++) {
            prevButtons[i].removeAttribute("disabled");
            prevButtons[i].style.cursor = "pointer";
        }
        if (pagData.version === 2) {
            redrawPaginationPanel();
        }
    }
}

//previous Page
function prevPage(x, e) {
    e.preventDefault();
    if (prevButtons[0].getAttribute("disabled") !== "disabled") {

        let connections = docConnectionTableBody.rows;
        let entriesMustBePassed = pagData.currentPage - x < 1 ? pagData.pageSize * (pagData.currentPage - 2) + pagData.rowsShown
            : pagData.pageSize * (x - 1) + pagData.rowsShown;

        let pageScrollBoolean = pagData.rowsShown < pagData.pageSize;
        pagData.rowsShown = 0;
        pagData.rowsInPage = 0;

        for (let i = pagData.upperThreshold - 1, matching = 0, blocker = false; i >= 0 && pagData.rowsShown < pagData.pageSize; i--) {

            if (matching < entriesMustBePassed) {
                connections[i].hidden = true;
            } else {
                if (matching === entriesMustBePassed && !blocker) {
                    pagData.upperThreshold = i + 1;
                    blocker = true;
                }
                pagData.rowsInPage++;
                if (!connections[i].filtered2) {
                    connections[i].hidden = false;
                    pagData.rowsShown++;
                }
            }

            if (!connections[i].filtered2) {
                matching++;
            }
        }

        pagData.bottomLine = pagData.upperThreshold - pagData.rowsInPage;
        pagData.currentPage = pagData.currentPage - x > 0 ? pagData.currentPage - x : 1;
        document.getElementById("currentPageLi").innerText = "" + pagData.currentPage + "/" + pagData.totalPages;

        if (pagData.currentPage === 1) {
            for (let i = 0; i < prevButtons.length; i++) {
                prevButtons[i].setAttribute("disabled", "disabled");
                prevButtons[i].style.cursor = "default";
            }
        }
        for (let i = 0; i < nextButtons.length; i++) {
            nextButtons[i].removeAttribute("disabled");
            nextButtons[i].style.cursor = "pointer";
        }
        if (pagData.version === 2) {
            redrawPaginationPanel();
        }
        if (pageScrollBoolean) {
            setTimeout(function () {
                window.scrollTo(0, document.body.scrollHeight);
            }, 10);
        }
    }
}

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

//open modal window with tips
function openModal(text) {
    $('#validationErrorModal').modal({show: true});
    $('#validationErrorModal .modal-open').removeAttr('style');
    $('#validationErrorModal .modal-body').empty().append('<p>' + text + '</p>');
}

//RGB to HEX function
function rgbToHex(rgb) {
    rgb = rgb.substring(4, rgb.length - 1).split(", ");
    return "#" +
        ("0" + parseInt(rgb[0], 10).toString(16)).slice(-2) +
        ("0" + parseInt(rgb[1], 10).toString(16)).slice(-2) +
        ("0" + parseInt(rgb[2], 10).toString(16)).slice(-2);
}

// show config page
function showConfig() {
    let rows = 0;
    for (let i = pagData.bottomLine; i < pagData.upperThreshold; i++) {
        if (pagData.connections[i].hidden === false) {
            docConfigTableBody.children[rows].setAttribute("data-id", pagData.connections[i].getAttribute("id").substring(7)); //set data-id attribute
            docConfigTableBody.children[rows].children[0].children[0].value = pagData.connections[i].children[1].innerText;   //set Alias input field
            docConfigTableBody.children[rows].children[1].innerText = pagData.connections[i].children[2].innerText;    //set number
            docConfigTableBody.children[rows].children[2].innerText = pagData.connections[i].children[3].innerText;    //set position
            docConfigTableBody.children[rows].children[3].innerText = pagData.connections[i].children[4].innerText;    //set location
            docConfigTableBody.children[rows].children[4].children[0].value = pagData.connections[i].children[5].children[0].children[0].innerText;    //set T1 input field
            docConfigTableBody.children[rows].children[5].children[0].children[0].setAttribute("data-color", rgbToHex(pagData.connections[i].children[5].children[0].style.backgroundColor));    //set T1 data-color attribute
            docConfigTableBody.children[rows].children[5].children[0].children[0].children[0].style.backgroundColor = pagData.connections[i].children[5].children[0].style.backgroundColor;    //set T1 color
            docConfigTableBody.children[rows].children[6].children[0].value = pagData.connections[i].children[5].children[1].children[0].innerText;    //set T2 input field
            docConfigTableBody.children[rows].children[7].children[0].children[0].setAttribute("data-color", rgbToHex(pagData.connections[i].children[5].children[1].style.backgroundColor));    //set T2 data-color attribute
            docConfigTableBody.children[rows].children[7].children[0].children[0].children[0].style.backgroundColor = pagData.connections[i].children[5].children[1].style.backgroundColor;    //set T2 color
            docConfigTableBody.children[rows].children[8].children[0].value = pagData.connections[i].children[5].children[2].children[0].innerText;    //set T3 input field
            docConfigTableBody.children[rows].children[9].children[0].children[0].setAttribute("data-color", rgbToHex(pagData.connections[i].children[5].children[2].style.backgroundColor));    //set T3 data-color attribute
            docConfigTableBody.children[rows].children[9].children[0].children[0].children[0].style.backgroundColor = pagData.connections[i].children[5].children[2].style.backgroundColor;    //set T3 color
            docConfigTableBody.children[rows].children[10].children[0].value = pagData.connections[i].children[5].children[3].children[0].innerText;    //set T4 input field
            docConfigTableBody.children[rows].children[11].children[0].children[0].setAttribute("data-color", rgbToHex(pagData.connections[i].children[5].children[3].style.backgroundColor));    //set T4 data-color attribute
            docConfigTableBody.children[rows].children[11].children[0].children[0].children[0].style.backgroundColor = pagData.connections[i].children[5].children[3].style.backgroundColor;    //set T4 color
            docConfigTableBody.children[rows].hidden = false;
            rows++;
        }
    }
    for (let i = rows; i < docConfigTableBody.rows.length; i++) {
        docConfigTableBody.rows[i].hidden = true;
    }
    $("#dataSelector").hide();
    $("#configs").show();
}

function showSelector() {
    $("#configs").hide();
    $("#dataSelector").show();
}

//3 drag&drop functions below
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
        preparePreservation();
    }
}

//Open navigation panel
function navigate(link, e) {
    e.preventDefault();
    let naviBar = document.getElementById("navic" + link.parentElement.parentElement.id.substring(7));
    if (naviBar.style.display === "none" || naviBar.style.display === "") {
        naviBar.style.display = "inline"
    } else {
        naviBar.style.display = "none"
    }
}

//Move row up
function moveUp(up, e) {
    e.preventDefault();
    let connections = docConnectionTableBody.rows;
    let id = "connRow" + up.parentElement.id.substring(5);

    for (let i = pagData.bottomLine, shown = 0, changeable = 0; ; i++) {
        if (connections[i].id == id) {
            if (shown === 0) {
                if (pagData.currentPage !== 1) {
                    let temp = connections[i].outerHTML;
                    prevPage(1, event);
                    for (let j = pagData.upperThreshold - 1; ; j--) {
                        if (connections[j].hidden !== true) {
                            connections[j].hidden = true;
                            connections[i].outerHTML = connections[j].outerHTML;
                            connections[j].outerHTML = temp;
                            preparePreservation();
                            break;
                        }
                    }
                }
                break;
            } else {
                let temp = connections[i].outerHTML;
                connections[i].outerHTML = connections[changeable].outerHTML;
                connections[changeable].outerHTML = temp;
                preparePreservation();
                break;
            }
        }
        if (connections[i].hidden !== true) {
            shown++;
            changeable = i;
        }
    }
    document.getElementById(up.id).focus();
}

//Move row down
function moveDown(down, e) {
    e.preventDefault();
    let connections = docConnectionTableBody.rows;
    let id = "connRow" + down.parentElement.id.substring(5);

    for (let i = pagData.upperThreshold - 1, shown = 0, changeable = 0; ; i--) {
        if (connections[i].id == id) {
            if (shown === 0) {
                if (pagData.currentPage !== pagData.totalPages) {
                    let temp = connections[i].outerHTML;
                    nextPage(1, event);
                    for (let j = pagData.bottomLine; ; j++) {
                        if (connections[j].hidden !== true) {
                            connections[j].hidden = true;
                            connections[i].outerHTML = connections[j].outerHTML;
                            connections[j].outerHTML = temp;
                            preparePreservation();
                            break;
                        }
                    }
                }
                break;
            } else {
                let temp = connections[i].outerHTML;
                connections[i].outerHTML = connections[changeable].outerHTML;
                connections[changeable].outerHTML = temp;
                preparePreservation();
                break;
            }
        }
        if (connections[i].hidden !== true) {
            shown++;
            changeable = i;
        }
    }
    document.getElementById(down.id).focus();
}

//Save new connection order to the database
function saveNewConnectionOrder(e) {
    e.preventDefault();
    let dbRequest = [];
    for (let i = 1; i < docConnectionTable.rows.length; i++) {
        dbRequest.push(docConnectionTable.rows[i].id.substring(7));
    }

    // post new connection position
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: path + '/saveIndexes',
        data: JSON.stringify(dbRequest),
        dataType: 'json',
        success: function (resp) {
            //  Here we return array because after swapping data instances we need to restructure whole table to avoid the data loosing. Also we need to rebuild table after saving configuration.
            console.log(resp);
            window.location.reload(true);
        }
    });
    saveAnimationInProcess = true;
    document.getElementById("saveRowsOrder").style.display = "none";
    document.getElementById("newOrderSaved").style.display = "inline";
    let opacity = 1;
    let fadeOut = setInterval(function () {
        document.getElementById("newOrderSaved").style.opacity = opacity;
        opacity = opacity - 0.03;
    }, 100);
    setTimeout(function () {
        clearInterval(fadeOut);
        document.getElementById("newOrderSaved").style.display = "none";
        document.getElementById("newOrderSaved").style.opacity = "1";

        let multithreadingLock = false;
        if (changesPerformedButAnimationStillInProcess) {
            multithreadingLock = true;
        }
        saveAnimationInProcess = false;
        if (multithreadingLock) {
            changesPerformedButAnimationStillInProcess = false;
            document.getElementById("saveRowsOrder").style.display = "inline";
        }
    }, 3300);
}

//This function is needed because of saving animation, it prevents saving interface collisions.
function preparePreservation() {
    if (!changesPerformedButAnimationStillInProcess) {
        changesPerformedButAnimationStillInProcess = saveAnimationInProcess;
        if (!saveAnimationInProcess) {
            document.getElementById("saveRowsOrder").style.display = "inline";
        }
    }
}

//show data as graph by clicking on "asGraph"-button
const asGraph = function (id, e) {
    e.preventDefault();
    console.log("Graph data for id " + id + " has been shown. Haven't you seen it?")
};

//show data as table by clicking on "asTable"-button
const asTable = function (id, e) {
    e.preventDefault();
    console.log("Table data for id " + id + " has been shown. Haven't you seen it?")
};

//change threshold labels text colors
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

const exportToCsv = function ($event) {
    $event.preventDefault();
    let table = document.querySelector("table");
    let csv = [];
    let rows = table.querySelectorAll("tr");

    for (let i = 0; i < rows.length; i++) {

        let row = [], cols = rows[i].querySelectorAll("td, th");
        if (i === 1) row.push(" ");

        for (let j = 0; j < cols.length; j++) {

            row.push(cols[j].innerText);

            if (Number(cols[j].getAttribute("colspan")) > 1) {
                for (let k = 1; k < Number(cols[j].getAttribute("colspan")); k++) {
                    row.push(" ");
                }
            }
            // if (Number(cols[j].getAttribute("rowspan")) > 1) {
            // }
        }

        if (i === 1) row.push(" ");
        csv.push(row.join(";"));   // used ',' by default or '\t' for just for
    }
    csv = csv.join("\n");

    // return (window.open('data:text/csv;charset=utf-8,' + csv));

    let csvFile = new Blob([csv], {type: "text/csv"});
    let downloadLink = document.createElement("a");
    downloadLink.download = document.getElementById("fileName").innerText + ".csv";
    downloadLink.href = window.URL.createObjectURL(csvFile);
    downloadLink.style.display = "none";
    // document.body.appendChild(downloadLink);
    downloadLink.click();
};