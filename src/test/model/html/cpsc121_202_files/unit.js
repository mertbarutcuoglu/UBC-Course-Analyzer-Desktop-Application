$(function() {
    /* Adding active class to expanded accordian headings    */
    
    /* $('.collapse').live('show', function()     */
    $(document).on('show', '.collapse', function()
    {
        $(this).parent().find('a').addClass('active'); //add active state to button on open
    });

    /* $('.collapse').live('hide', function() */
    $(document).on('hide', '.collapse', function()
            {
        $(this).parent().find('a').removeClass('active'); //remove active state to button on close
    });
    
});
