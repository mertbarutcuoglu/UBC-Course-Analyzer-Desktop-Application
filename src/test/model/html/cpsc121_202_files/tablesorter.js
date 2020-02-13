addEvent(window, "load", sortables_init);

var SORT_COLUMN_INDEX;

function sortables_init() {
   // Find all tables with class sortable and make them sortable
if (!document.getElementsByTagName) return;
   tbls = document.getElementsByTagName("table");
   for (ti=0;ti<tbls.length;ti++) {
       thisTbl = tbls[ti];
       if (((' '+thisTbl.className+' ').indexOf("sortable") != -1) && (thisTbl.id)) {
           //initTable(thisTbl.id);
           ts_makeSortable(thisTbl);
       }
   }
}

function ts_makeSortable(table) {
   if (table.rows && table.rows.length > 0) {
       var firstRow = table.rows[0];
   }
   if (!firstRow) return;

   // We have a first row: assume it's the header, and make its contents clickable links
   for (var i=0;i<firstRow.cells.length;i++) {
       var cell = firstRow.cells[i];
       //get only the text
       var txt = ts_getInnerText(cell);
       txt = trimRemoveNewLine(txt);
       //get everything
       var html = cell.innerHTML;
       html = trimRemoveNewLine(html);
       html = getAllHTML(html);
       cell.innerHTML = '<span onclick="ts_resortTable(this);" class="sortarrow" id="column' + i + '">' + txt + '</span >'+html;
//	cell.onclick=ts_resortTable(this);
       cell.style["cursor"] = "pointer";
   }
}

/**
Returns only the tagged elements
**/
function getAllHTML(html){
  var tmp = html;
  var returnHtml="";
  var reg = /<[^<]*>/;
  while(tmp.match(reg,"i")!=null){
    var foundHtml = tmp.match(reg,"i");
    returnHtml += foundHtml
    tmp = tmp.replace(foundHtml, "");
  }
  return returnHtml;
}


function trimRemoveNewLine(string){
  string = escape(string);
  //remove all new lines (carriage returns)
  //Windows encodes returns as \r\n hex
   while(string.indexOf("%0D%0A") > -1){
    string = string.replace("%0D%0A", "%20");
  }
  //Unix encodes returns as \n hex
   while(string.indexOf("%0A") > -1){
    string = string.replace("%0A", "%20");
   }
  //Mac
   while(string.indexOf("%0D") > -1){
    string = string.replace("%0D", "%20");
   }

  //remove double spaces
  //%20 is UTF-8 covering windows and mac
  while(string.indexOf("%20%20")!=-1){
    string = string.replace("%20%20", "%20");
  }
  //remove leading space
  while(string.indexOf("%20")==0){
    string = string.substring("%20".length);
  }
  //remove trailing space
  var indexOfEnd = string.length - "%20".length;
  while(string.substring(indexOfEnd).indexOf("%20")!=(-1)){
    string = string.substring(0, indexOfEnd);
    indexOfEnd = string.length - "%20".length;
  }

   string = unescape(string);

  return string;
}

function ts_getInnerText(el) {
  if (typeof el == "string") return el;
  if (typeof el == "undefined") { return el };
  if (el.innerText) return el.innerText;	//Not needed but it is faster
  var str = "";

  var cs = el.childNodes;
  var l = cs.length;
  for (var i = 0; i < l; i++) {
    switch (cs[i].nodeType) {
      case 1: //ELEMENT_NODE
        str += ts_getInnerText(cs[i]);
        break;
      case 3:	//TEXT_NODE
        str += cs[i].nodeValue;
        break;
    }
  }
  return str;
}

function ts_resortTable(span) {
    if (navigator.userAgent.match(/MSIE.*Mac/) != null) {
      alert("Sorting is not available in this browser.  Sorry for the inconvenience");
      return;
    }
   var spantext = ts_getInnerText(span);
   var td = span.parentNode;
   var tr = td.parentNode;
   for (i=0; i<tr.cells.length; i++) {
    if (td == tr.cells[i]) {
        break;
     }
   }
   var column = i;
   var table = getParent(td,'TABLE');

    // Work out a type for the column
    if (table.rows.length <= 1 ) return;
    for (i=1;i<table.rows.length; i++) {
      var itm = ts_getInnerText(table.rows[i].cells[column]);
			itm =  itm.replace(/^\s*|\s*$/g,"");
			itm = itm.replace(/[\t\s]+/," ");
      sortfn = ts_sort_caseinsensitive;
      if (itm != '&nbsp;' && itm != ' ' && itm.length > 0 && !(itm.length == 1 && itm.charCodeAt(0) == 160)) {
        if (itm.match(/^\d\d[\/-]\d\d[\/-]\d\d\d\d/)) sortfn = ts_sort_date; else 
        if (itm.match(/^\d\d\d\d[\/-]\d\d[\/-]\d\d.*/)) sortfn = ts_sort_date; else
        if (itm.match(/^[a-zA-Z]+ *\d\d*, *\d\d\d*/)) { sortfn = ts_sort_text_date; } else
        if (itm.match(/^[?$]/)) sortfn = ts_sort_currency; else
        if (!isNaN(parseFloat(itm))) sortfn = ts_sort_numeric;
	break;
      }
    }
    SORT_COLUMN_INDEX = column;
    var firstRow = new Array();
    var newRows = new Array();
    for (i=0;i<table.rows[0].length;i++) { firstRow[i] = table.rows[0][i]; }
    for (j=1;j<table.rows.length;j++) { newRows[j-1] = table.rows[j]; }

   newRows.sort(sortfn);

   if (span.getAttribute("sortdir") == 'down') {
       newRows.reverse();
       span.setAttribute('sortdir','up');
td.className = "listHeaderSortDesc";
   } else {
       span.setAttribute('sortdir','down');
td.className = "listHeaderSortAsc";
   }

   // We appendChild rows that already exist to the tbody, so it moves them rather than creating new ones
   // don't do sortbottom rows
   for (i=0;i<newRows.length;i++) { if (!newRows[i].className || (newRows[i].className && (newRows[i].className.indexOf('sortbottom') == -1))) table.tBodies[0].appendChild(newRows[i]);}
   // do sortbottom rows only
   for (i=0;i<newRows.length;i++) { if (newRows[i].className && (newRows[i].className.indexOf('sortbottom') != -1)) table.tBodies[0].appendChild(newRows[i]);}

   // cycle through the other theads and set the classname to listHeader to remove the arrows.
   for (i=0; i<tr.cells.length; i++) {
td1 = tr.cells[i];
if (td != td1) {
  td1.className = "listHeader";
}
   }


}

function getParent(el, pTagName) {
if (el == null) return null;
else if (el.nodeType == 1 && el.tagName.toLowerCase() == pTagName.toLowerCase())	// Gecko bug, supposed to be uppercase
  return el;
else
  return getParent(el.parentNode, pTagName);
}

function ts_sort_date(a,b) {
   aa = ts_getInnerText(a.cells[SORT_COLUMN_INDEX]);
   bb = ts_getInnerText(b.cells[SORT_COLUMN_INDEX]);
	 var dateA = getMyDate(aa);
	 var dateB = getMyDate(bb);
	 if(dateA == dateB) return 0;
	 if(dateA < dateB) return -1;
	 return 1;
}


function getMyDate(myDate, dateOb){
	myDate= myDate.replace(/^\s*|\s*$/g,"");
	myDate= myDate.replace(/[\t\s]+/, " ");
	var dateAr = myDate.split(" ");
	var dayAr = dateAr[0].split("-");
	var hourAr= dateAr[dateAr.length-1].split(":");
	var newDate = new Date();
	newDate.setFullYear(dayAr[0]);
	newDate.setMonth(dayAr[1]-1);
	newDate.setDate(dayAr[2]);
	newDate.setHours(hourAr[0]);
	newDate.setMinutes(hourAr[1]);
	return newDate;
}

/*** Testing getMyDate Function ***/
//var nDate = getMyDate("2005-02-16  		12:12");
//alert(nDate);

var monthArray = new Array("JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC");

function ts_sort_text_date(a,b) {
   aa = ts_getInnerText(a.cells[SORT_COLUMN_INDEX]);
   bb = ts_getInnerText(b.cells[SORT_COLUMN_INDEX]);
   if (aa == null || bb == null) return 0;
   if (aa.length < 10 & bb.length < 10) return 0;
   if (aa.length < 10) return -1;
   if (bb.length < 10) return 1;
   aMonth = aa.substr(0,3).toUpperCase();
   bMonth = bb.substr(0,3).toUpperCase();
   amint = "00";
   bmint = "00";
   for (i=0; i<monthArray.length; i++) {
if (monthArray[i] == aMonth) {
  if (i < 9) {
  amint = "0" + (i+1);
  } else {
  amint = "" + (i+1);
  }
}
if (monthArray[i] == bMonth) {
  if (i < 9) {
  bmint = "0" + (i+1);
  } else {
  bmint = "" + (i+1);
  }
}
   }
   ayear = aa.substr(aa.indexOf(",") + 2,bb.length);
   byear = bb.substr(bb.indexOf(",") + 2,bb.length);
   aday = aa.substr(aa.indexOf(" ") + 1,aa.indexOf(",") - aa.indexOf(" ") - 1);
   bday = bb.substr(bb.indexOf(" ") + 1,bb.indexOf(",") - bb.indexOf(" ") - 1);

   dt1 = parseInt(ayear + amint + aday);
   dt2 = parseInt(byear + bmint + bday);

   if (dt1==dt2) return 0;
   if (dt1<dt2) return -1;
   return 1;
}

function ts_sort_currency(a,b) {
   aa = ts_getInnerText(a.cells[SORT_COLUMN_INDEX]).replace(/[^0-9.]/g,'');
   bb = ts_getInnerText(b.cells[SORT_COLUMN_INDEX]).replace(/[^0-9.]/g,'');
   return parseFloat(aa) - parseFloat(bb);
}

function ts_sort_numeric(a,b) {
   aa = parseFloat(ts_getInnerText(a.cells[SORT_COLUMN_INDEX]));
   if (isNaN(aa)) aa = 0;
   bb = parseFloat(ts_getInnerText(b.cells[SORT_COLUMN_INDEX]));
   if (isNaN(bb)) bb = 0;
   return aa-bb;
}

function ts_sort_caseinsensitive(a,b) {
   aa = ts_getInnerText(a.cells[SORT_COLUMN_INDEX]).toLowerCase();
   bb = ts_getInnerText(b.cells[SORT_COLUMN_INDEX]).toLowerCase();
   if (aa==bb) return 0;
   if (aa<bb) return -1;
   return 1;
}

function ts_sort_default(a,b) {
   aa = ts_getInnerText(a.cells[SORT_COLUMN_INDEX]);
   bb = ts_getInnerText(b.cells[SORT_COLUMN_INDEX]);
   if (aa==bb) return 0;
   if (aa<bb) return -1;
   return 1;
}


function addEvent(elm, evType, fn, useCapture)
// addEvent and removeEvent
// cross-browser event handling for IE5+,  NS6 and Mozilla
// By Scott Andrew
{
 if (elm.addEventListener){
   elm.addEventListener(evType, fn, useCapture);
   return true;
 } else if (elm.attachEvent){

   var r = elm.attachEvent("on"+evType, fn);
   return r;
 }
}
