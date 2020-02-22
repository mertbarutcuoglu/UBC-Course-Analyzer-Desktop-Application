
function displayHelpText(text,w,h,event,title) {
    document.getElementById("helpContent").innerHTML = text;
    document.getElementById("dwindowTitle").innerHTML = "<b>" + title + "</b>";
    dwind = document.getElementById("dwindow");
    dwind.style.display='';
    var agt=navigator.userAgent.toLowerCase();
    if (agt.indexOf("safari") != -1) {
      x = event.clientX - dwind.offsetWidth - 20;
      y = event.clientY - dwind.offsetHeight;
    } else if (window.pageXOffset) {
      x = event.clientX - dwind.offsetWidth - 20 + window.pageXOffset;
      y = event.clientY - dwind.offsetHeight + window.pageYOffset;
    } else {
      x = event.clientX - dwind.offsetWidth - 20 + document.body.scrollLeft;
      y = event.clientY - dwind.offsetHeight + document.body.scrollTop;
    }
    if (x < 0) {
      x = x + w + 20;
    }
    dwind.style.left=x+"px";
    dwind.style.top=y+"px";
}

function closePopup() {
	document.getElementById("dwindow").style.display='none';
}