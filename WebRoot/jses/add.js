$(function(){
	var isNameRight=true;
	var isPasswordRight=true;
	var isAgeRight=true;
	var isPhoneRight=true;
	var isFormatRight=true;
	
    function check($input, name, data){
        $.post("jqueryServlet", {
            method: "check" + name,
            data: data,
        }, function(data){
			validateInputRight(name,data);
            $("." + name).text(data);
        });
    };
    $("input[name='name']").blur(function(){
        check($(this), "name", $(this).val());
    });
    $("input[name='password']").blur(function(){
        check($(this), "password", $(this).val());
    });
    $("input[name='age']").blur(function(){
        check($(this), "age", $(this).val());
    });
    $(".btn").click(function(){
		  var isRightArr=[isNameRight,isAgeRight,isPasswordRight,isPhoneRight];
		  for(var i in isRightArr){
		  	if(!isRightArr[i]){
				isFormatRight=false;
				break;
			}
			isFormatRight=true;
		  }
		  var isNotEmpty = validateIsEmpty();
		  if(isNotEmpty && isFormatRight){
		        $.post("jqueryServlet", {
		            method: "addEmp",
		            name: $("#name").val(),
		            password: $("#password").val(),
		            sex: $("input[name='sex']").val(),
		            age: $("#age").val(),
					province:$("#province").val(),
					city:$("#city").val(),
					dno:$("#department").val(),
					phone:$("#phone").val(),
		        }, function(data){
		            if (data > 0) {
		                alert("Congratulations! Employee Added!");
		            }
		            else {
		                alert("Employee add failed!");
		            }
		        });
		  }else{
		  		alert("Add failed, Please check your form!");
		  }
    });
	
	/**
	 * 省份改变,实现省市联动()
	 */
	$("#province").change(function(){
        var $province = $(this);
        $.post("jqueryServlet", {
            method: "changeProvince",
            provincename: $province.val(),
        }, function(data){
            $("#city").html("<option value=''>==Options==</option>");
            if (data != "") {
                var jsonObject = $.parseJSON(data);
                for (var i in jsonObject) {
                    $("#city").append($("<option/>").val(jsonObject[i].name).text(jsonObject[i].name));
                }
            }
        });
    });
	
	/**
     * 控制年龄字段只能输入数字(Number only for age)
     */
    onlyNum($("#age"));
	onlyNum($("#phone"));
	/**
	 * 只能输入数字
	 * @param {Object} $num 控制只能输入数字的jqury对象
	 */  
	function onlyNum($num){

		$num.keyup(function(){//不能输入非0~9的字符
	        $(this).val($(this).val().replace(/\D|^0/g, ''));
		    }).bind("paste", function(){//禁止粘贴非0~9的字符
		        $(this).val($(this).val().replace(/\D|^0/g, ''));
		    }).css("ime-mode", "disabled");
	}
	
	/**
	 * 验证表单是否都正确(Check if form is correct)
	 * @param {Object} name
	 * @param {Object} data
	 */
	function validateInputRight(name,data){
		if(name=='name'){
				if(data!="" ){
					isNameRight=false;
				}else{
					isNameRight=true;
				}
			}else if(name='password'){
				if(data!="" ){
					isPasswordRight=false;
				}else{
					isPasswordRight=true;
				}
			}else if(name='age'){
				if(data!="" ){
					isAgeRight=false;
				}else{
					isAgeRight=true;
				}
			}else if(name='phone'){
				if(data!="" ){
					isPhoneRight=false;
				}else{
					isPhoneRight=true;
				}
			}
	}
	
	/**
	 * 判断各大输入框是否为""(Check all input box not empty)
	 */
	function validateIsEmpty(){
		var isEmptyRight=false;
		if($("#name").val()==""){
			$(".name").text("Employee can not be empty");
			if(isEmptyRight){
				isEmptyRight=false;
			}
		}else{
			$(".name").text("");
			isEmptyRight=true;
		}
		if($("#password").val()==""){
			$(".password").text("Password can not be empty");
			if(isEmptyRight){
				isEmptyRight=false;
			}
		}else{
			$(".password").text("");
			isEmptyRight=true;
		}
		if($("#age").val()==""){
			$(".age").text("Age can not be empty");
			if(isEmptyRight){
				isEmptyRight=false;
			}
		}else{
			$(".age").text("");
			isEmptyRight=true;
		}
		if($("#city").val()==""){
			$(".city").text("Province and City can not be empty");
			if(isEmptyRight){
				isEmptyRight=false;
			}
		}else{
			$(".city").text("");
			isEmptyRight=true;
		}
		if($("#phone").val()==""){
			$(".phone").text("Tel no can not be empty");
			if(isEmptyRight){
				isEmptyRight=false;
			}
		}else{
			$(".phone").text("");
			isEmptyRight=true;
		}
		if($("#phone").val().length!=11){
			$(".phone").text("Please type 11 digits");
			if(isEmptyRight){
				isEmptyRight=false;
			}
		}else{
			isEmptyRight=true;
		}
		return isEmptyRight;
	};
});
