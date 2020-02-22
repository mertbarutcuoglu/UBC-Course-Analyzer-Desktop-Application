/*
<!-- This script and many more are available free online at -->
<!-- The JavaScript Source!! http://javascript.internet.com -->
<!-- Original:  Patrick Lewis (gtrwiz@aol.com) -->
<!-- Web Site:  http://www.patricklewis.net -->
<!-- Begin
*/
//   ##############  SIMPLE  BROWSER SNIFFER
if (document.layers) {navigator.family = "nn4"}
if (document.all) {navigator.family = "ie4"}
if (window.navigator.userAgent.toLowerCase().match("gecko")) {navigator.family = "gecko"}

overdiv="0";
//  #########  CREATES POP UP BOXES
var popupHtml = "";
var regCataNos_Timeslots="";
var wlCataNos__Timeslots="";
var regiSttCataNos_Timeslots="";
var wlStttCataNos_Timeslots="";
var conflictedTimeSlots="";
var token_regi = "|PARAM_SEPARATOR_REGI|";
var token_wl = "|PARAM_SEPARATOR_WL|";
var token_regi_stt = "|PARAM_SEPARATOR_REGI_STT|";
var token_wl_stt = "|PARAM_SEPARATOR_WL_STT|";
var token_conflicted_timeslots = "|PARAM_SEPARATOR_CONFLICTED_TIMESLOT|";

var loopcheck = 0;
//strips out information that has beeen appended to this table and
//sets them to the appropriate arrays of catanos. 
function removeDelimitedValues(desc1, aToken){
  var arrToFill;

  if(loopcheck>20) return desc1;
  var check = desc1.indexOf(aToken);
  var idx = desc1.indexOf(aToken) + aToken.length;
  //put in a token in front of an array that contains all the catanos that this stud is
  //regi/WL in. To handle the 'in course but not conflicting hover table'.
  if(check!=-1){
    var tmp = desc1.substring(idx);
    var secondIdx = tmp.indexOf(aToken);

    var restOfTable1 = desc1.substring(0,desc1.indexOf(aToken));
    var restOfTable2 = tmp.substring(secondIdx+aToken.length);

    if(aToken==token_regi){
      regCataNos_Timeslots+=  tmp.substring(0,secondIdx);
    } else if(aToken==token_wl){
      wlCataNos__Timeslots+=  tmp.substring(0,secondIdx);
    } else if(aToken==token_regi_stt){
      regiSttCataNos_Timeslots+=  tmp.substring(0,secondIdx);
    } else if(aToken==token_wl_stt){
      wlStttCataNos_Timeslots+=  tmp.substring(0,secondIdx);
    } else if(aToken==token_conflicted_timeslots){
      conflictedTimeSlots+=  tmp.substring(0,secondIdx);
    }

    //clean out the delimted text and return clean table for the function to render
    desc1 = restOfTable1 + restOfTable2
  }
  loopcheck+=1;
  if(desc1.indexOf(aToken)!=-1){
    return removeDelimitedValues(desc1, aToken);
  }else{
    return desc1;
  }
}

function setPopupHtml(desc1,title,useLegend) {
  loopcheck=0;
  desc1 = removeDelimitedValues(desc1, token_regi);
  desc1 = removeDelimitedValues(desc1, token_regi_stt);
  desc1 = removeDelimitedValues(desc1, token_wl);
  desc1 = removeDelimitedValues(desc1, token_wl_stt);
  desc1 = removeDelimitedValues(desc1, token_conflicted_timeslots);

  if (title) {
    fullTitle = "Timetable Preview" + title;
  } else {
    fullTitle = "Timetable Preview";
  }

  if (useLegend == "true") {
    legend = "<TR><TD class='tt-legend'><H6>Legend:</H6></TD></TR><TR><TD valign=top><TABLE id='ttpopup-display' cellspacing=1 border=0 cellpadding=0><TR><TD width=10% class='tt-worklist-mini'>&nbsp;</TD><TD width=40%>Worklist&nbsp;&nbsp;&nbsp;</TD><TD width=10% class='tt-selcourse-mini'>&nbsp;</TD><TD width=40%>Selected</TD></TR><TR><TD width=10% class='tt-registered-mini'>&nbsp;</TD><TD width=40%>Registered&nbsp;&nbsp;&nbsp;</TD><TD width=10% class='tt-conflict-mini'>&nbsp;</TD><TD width=40%>Selected Conflicts</TD></TR><TR><TD width='200px' colspan=4></TD></TR></TABLE></TD></TR>"
  } else {
    legend = "";
  }
  popupHtml = "<TABLE cellspacing=0 cellpadding=0 border=1><TR><TD class='tt-legend'><H6>" + fullTitle + "</H6></TD></TR><TR><TD>" + desc1 + "</TD></TR>" + legend + "</TABLE>";
}

function popup() {
  
  // alert(navigator.family);
  
  if (navigator.family == "gecko") {
    pad="0"; bord="1 bordercolor=black";
  } else {
    pad="1"; bord="0";
  }
  if(navigator.family =="nn4") {
	  document.object1.document.write(popupHtml);
	  document.object1.document.close();
    x = document.width - 15 - document.ttpopup.width;
	  document.ttpopup.left=x;
	  document.ttpopup.top=5;
	}  else {
    document.getElementById("ttpopup").innerHTML=popupHtml;
    
    // this is needed as ff6 does not support document.width
    var body = document.getElementsByTagName('body')[0];
    var ffwidth = window.getComputedStyle(body, null).width;
    x = parseInt(ffwidth) - 15 - document.getElementById("ttpopup").offsetWidth;

    y = document.body.scrollTop + ((document.body.clientHeight / 2) - (document.getElementById("ttpopup").offsetHeight / 2))
    document.getElementById("ttpopup").style.left=x;
    document.getElementById("ttpopup").style.top=y;
	}
}

/**
 *
 * @param elements an array of strings that specify where on the timetable popup grid this section is going to
 * show up on.
 *
 * It goes : TERM-TIMESLOT-DAY (e.g. t1-2-0 ,t1-3-0, t1-2-2, t1-3-2, t1-2-4, t1-3-4 where
 * t1-2-0 would be be term 1 - 8:00  (0 is 700, 1 is 730) - Monday (0 is Monday, 1 is Tuesday, etc)) 
 * @param cataNo
 */
function setColor(timeSlots, cataNo) {
  for (i=0; i<timeSlots.length; i++) {
  	cell = document.getElementById(timeSlots[i]);
  	if (cell) {
      name = cell.className;

      //these are all the conflicts we know about (those in regi, wl, stt, regi-stt, (i.e. those the student has 'saved' in someway))
      if(conflictedTimeSlots.indexOf(cell.id +", " )!=-1){
          cell.className = "tt-conflict-mini";
        //if this is a course they are just hovering over, and has not been saved/registered  by the student
        //AND it's in a cel that has, it's a conflict
        //if the time (elements[i]) being passed in conflicts with any of the times associated with the student already
        //(regCataNos, wlCataNos, etc), then it's also a conflict
      }else if (
                //we are not hovering over a course that's already registered, etc
                (((regCataNos_Timeslots.indexOf(", " + cataNo + ", ") == -1)
                 &&
                 (wlCataNos__Timeslots.indexOf(", " + cataNo + ", ") == -1)
                 &&
                 (wlStttCataNos_Timeslots.indexOf(", " + cataNo + ", ") == -1)
                 &&
                 (regiSttCataNos_Timeslots.indexOf(", " + cataNo + ", ") == -1))
                &&
                //this is a time table cell
                (name == "tt-worklist-mini"
                || name == "tt-reg-stt"
                || name == "tt-reg-stt-mini"
                || name == "tt-worklist"
                  || name == "tt-registered"
                  || name == "tt-registered-mini"
                ))
              ||
                (
                 ((regCataNos_Timeslots.indexOf(", " + timeSlots[i] + ", ") != -1)
                ||
                (wlCataNos__Timeslots.indexOf(", " + timeSlots[i] + ", ") != -1)
                ||
                (wlStttCataNos_Timeslots.indexOf(", " + timeSlots[i] + ", ") != -1)
                ||
                (regiSttCataNos_Timeslots.indexOf(", " + timeSlots[i] + ", ") != -1))
                  )
        ) {
        cell.className = "tt-conflict-mini";
      } else {
          cell.className = "tt-selcourse-mini";
      }
    }
  }
}


function hideLayer(){
  if (cancelHide == 0) {
    if (overdiv == "0") {
      if(navigator.family =="nn4") {
        eval(document.ttpopup.top="-500");
        document.ttpopup.document.write("&nbsp;");
        document.ttpopup.document.close();
        } else {
        document.getElementById("ttpopup").style.top="-1500";
        document.getElementById("ttpopup").innerHTML="&nbsp;"

      }
    }
  }
}

var cancelHide = 0;
//  End -->
