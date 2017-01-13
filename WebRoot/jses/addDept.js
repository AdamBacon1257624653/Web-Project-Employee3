$(function(){
	var $name=$("#name");
	$("#addBtn").click(function(){
		var name=$name.val();
		if(name==""){
			$(".namespan").text("Department name can not be empty");
		}else{
			$.post("DeptServlet", {
                method: "addDpet",
                newdata: name,
            }, function(data){
                if (data > 0) {
                    alert("Department added");
                }else{
					alert("Department add failed");
				}
            });
		}
	});
	$name.keyup(function(){
		if($name.val()!=""){
			$(".namespan").text("");
		}
	});
});
