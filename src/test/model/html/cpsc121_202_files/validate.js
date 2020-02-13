
function remLeadingZero(number){
  //remove leading 0, as the number gets corrupted in parseInt()
  if(number.indexOf("0")==0){
    number = number.substring(1);
    remLeadingZero(number);
  } else{
    return number;
  }
}

function validate(validateWhat) {
 if (validateWhat == "SECT_SEARCH_TIMES") {
  theForm = document.forms["sect_srch_criteria_simp_search"];
  tStartTime = new String(theForm.elements['sTime'].value);
  tEndTime = new String(theForm.elements['eTime'].value);
  startTime = theForm.elements['sTime'];
  endTime = theForm.elements['eTime'];
  addStart = 0;
  addEnd = 0;

  if (tStartTime.toUpperCase().indexOf("PM") >=0) addStart = 1200;
  if (tEndTime.toUpperCase().indexOf("PM") >= 0) addEnd = 1200;

   tStartTime = remLeadingZero(tStartTime);
   tEndTime = remLeadingZero(tEndTime);

  re = /[^0-9]/
  sTime = parseInt(tStartTime.replace(re,""))
  eTime = parseInt(tEndTime.replace(re,""))

  if (sTime < 25) sTime = sTime * 100
  if (eTime < 25) eTime = eTime * 100

  if (sTime < 1200) sTime = sTime + addStart
  if (eTime < 1200) eTime = eTime + addEnd
  if ((eTime < sTime) && (eTime < 1200)) eTime = eTime + 1200;

  if  ((isNaN(sTime) && startTime.value != "") || sTime < 0 || sTime > 2400) {
    window.alert("Start Time is invalid.  Please re-enter");
    startTime.focus();
    return false;
  }
  if  ((isNaN(eTime) && endTime.value != "") || eTime < 0 || sTime > 2400) {
    window.alert("End Time is invalid.  Please re-enter");
    endTime.focus();
    return false;
  }
  if  (eTime < sTime) {
    window.alert("End Time is before Start Time.  Please re-enter");
    startTime.focus();
    return false;
  }

  if (isNaN(sTime))
	startTime.value = "";
  else
	startTime.value = sTime;
  if (isNaN(eTime))
	endTime.value = "";
  else
  	endTime.value = eTime;
  return true;
 } else if (validateWhat == "SET_SESSION_CHANGE") {
  theForm = document.forms["set-session-form"];
  theForm.submit();
  return true;
 }
 return true;
}

var linkHasBeenSent = false;

function checkLinks() {
  if (linkHasBeenSent) {
    alert("You have already sent a request from this page.  Please wait while it processes.");
    return false;
  }
  linkHasBeenSent = true;
  showProcessing();
  return true;
}

 var fudgeFactor = {top:-1, left:-1};
   function showProcessing() {
     show = document.getElementById("processingOverlay");
     show.style.display = '';

     if (fudgeFactor.top == -1) {
         if ((typeof show.offsetTop == "number") && show.offsetTop > 0) {
             fudgeFactor.top = show.offsetTop;
             fudgeFactor.left = show.offsetLeft;
         } else {
             fudgeFactor.top = 0;
             fudgeFactor.left = 0;
         }
         if (show.offsetWidth && show.scrollWidth) {
             if (show.offsetWidth != show.scrollWidth) {
                 show.style.width = show.scrollWidth;
             }
         }
     }
     var x = Math.round((getInsideWindowWidth( )/2) - (getObjectWidth(show)/2));
     var y = Math.round((getInsideWindowHeight( )/2) - (getObjectHeight(show)/2));
     shiftTo(show,x,y);
     return true;
   }
