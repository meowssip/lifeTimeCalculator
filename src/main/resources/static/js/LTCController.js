$(function(){
  $('.dots').fadeIn();
  
  $(".countDot").click(function(){
   $("#countit").show();
  });
  
  $("#flip").click(function(){
   $("#flip").fadeOut(function () {
   	$("#flip").text(($("#flip").text() == 'Read More') ? '^' : 'Read More').fadeIn();
    $(".body").slideToggle("fast");
   });
  });
});