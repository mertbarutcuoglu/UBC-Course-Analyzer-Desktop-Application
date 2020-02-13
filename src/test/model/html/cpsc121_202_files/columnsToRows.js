document.write('<style type="text/css">body{display:none}</style>');

$(document).ready(function()
{
	
	temp();
	$('body').css('display','block');
	
});


$(window).resize(function() { temp() });


function temp(){
	//if ($(window).width() < 767) {
	// be sure to also update unit-responsive.css if you adjust this.
	if ($(window).width() < 600) {
		
		$('.section-summary').each(function(index) {
			$(this).hide();
			
			if($('.mobile-content:eq(' + index + ') tr').length === 0){
				buildRowsFromColumns(index);
			}
			$('.mobile-content').eq(index).show();
		
		})
		
	} else {
		$('.section-summary').show();
		$('.mobile-content').hide();
	}	
}

function buildRowsFromColumns(number){
	
	var row = 0;
	var cssClass = "one";
	var columns = new Array();
	$('.section-summary:eq(' + number + ') tr').each(function() {
		
		if ( $(this).attr('class') !== "section" && $(this.cells[0]).attr('class') !== "full" ){
			if(row%2 === 1){
				cssClass = "one";
			} else {
				cssClass = "two";
			}
			
			row++;
		}
		var cell = 0;
		$.each(this.cells, function(){
			
			if($(this).is('th')){ 
				columns[cell] = $.trim(this.innerHTML);
				
			} else { 
			
				var cellHTML = $.trim(this.innerHTML);
				
				if ( cellHTML !== "&nbsp;" && cellHTML !== "") {
					
					if($(this).attr('class') === "full"  ){
						var temp = "<tr class='"+ cssClass +"'>";
						temp += "<td colspan='2'>" + cellHTML + "</td></tr>" ;
					} else if( $(this).attr('class') === "section-comments" ) {
						
						// can't have 2 elements on page with same id
						var aID = $('.accordion-body', this).attr('id') + "alt";
						$('.accordion-body', this).attr('id', aID);
						$('.accordion-toggle', this).attr('href', "#"+aID);
						
						var temp = "<tr class='"+ cssClass +"'>";
						temp += "<td colspan='2'>" + cellHTML + "</td></tr>" ;
						
					} else {
						var temp = "<tr class='"+ cssClass +"'>";
						temp += "<th>" + columns[cell] + "</th>";	
						temp += "<td>" + cellHTML + "</td></tr>" ;
						
					}
				$('.mobile-content').eq(number).append(temp);
				} // end content check
				
				
			} // end 
			cell++;
			
		}); // end row
	
	});
}