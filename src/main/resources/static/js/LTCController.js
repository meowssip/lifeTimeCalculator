$(document).ready(function(){
  $('.dots').fadeIn();
  $('.body').fadeIn();
  $("#flip").click(function(){
   $("#flip").fadeOut(function () {
   	$("#flip").text(($("#flip").text() == 'Read More') ? '^' : 'Read More').fadeIn();
    $("#detailDiv").slideToggle("fast");
   });
  });
});