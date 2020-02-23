(function(){
 $(document).ready(function() {
        $('#list').click(function(event){event.preventDefault();$('#products .item').addClass('list-group-item');});
        $('#grid').click(function(event){event.preventDefault();$('#products .item').removeClass('list-group-item');$('#products .item').addClass('grid-group-item');});

        $.get("http://localhost/product", function(data, status){
            alert("Data: " + data + "\nStatus: " + status);
          });
  });
  $("#cart").on("click", function() {
    $(".shopping-cart").fadeToggle( "fast");
  });


})();