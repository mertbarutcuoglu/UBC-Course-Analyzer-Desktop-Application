function filterTable(tableName,filter,columns) {
  rows = document.getElementById(tableName).tBodies[0].rows;
  for (var i=0; i<rows.length; i++) {
    if (filter.match("<All>")) {
      rows[i].style.display = '';
    } else {
      for (var j=0; j<columns.length; j++) {

        data = getInnerText(rows[i].cells[columns[j]]);

        if (data != null && data.match(filter)) {
          rows[i].style.display = '';
          break;
        } else {
          rows[i].style.display = 'none';
        }
      }
    }
  }
}

/**
Retrieves the text value that this DOM element contains.
*/
function getInnerText(obj) {
	var contentText = '';
	if(document.all){
	     contentText = obj.innerText;
	} else{
	    contentText = obj.textContent;
	}
	contentText = stripOut(contentText, '\n');
	contentText = stripOut(contentText, ' ');

	if(contentText!=''){
		return contentText;
	}
}

/**
Since the javascript string.replace function only replaces
ONCE, we need to loop it and remove all instances of the
textToStrip.
*/
function stripOut(str, textToStrip){
	var original = str;
	str = str.replace(textToStrip, '');

	while(original != str){
		original = str;
		str = str.replace(textToStrip, '');
	}
	return str;
}

