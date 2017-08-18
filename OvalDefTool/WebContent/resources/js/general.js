var General = General || {};
General.initialize = function(){
 // $(".nav li").click(function() {
  //  $(".nav li").removeClass('active');
 //   $(this).addClass('active');    
  //});
 $('.navbar-nav li a').click(function(){
	 $("body").spin("modal"); 
 });
	
  $('.navbar-brand').click(function(){
	  $("body").spin("modal");
	  window.location = "/TravelOverview/my_travel.do";
  });
	
  $("textarea").keyup(function (e) {
	  General.autoheight(this);
  });
  
  
  var interval = setInterval(function() {
      var momentNow = moment();
      $("input[name='productTimestamp']").val(moment().format());
  }, 500);
  
 
};
General.autoheight = function(a) {
    if (!$(a).prop('scrollTop')) {
        do {
            var b = $(a).prop('scrollHeight');
            var h = $(a).height();
            $(a).height(h - 5);
        }
        while (b && (b != $(a).prop('scrollHeight')));
    };
    $(a).height($(a).prop('scrollHeight') + 20);
};


$(document).ready(function(){
	General.initialize();
});