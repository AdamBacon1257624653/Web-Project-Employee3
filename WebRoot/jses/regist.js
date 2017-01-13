$(function(){
    $("#province").change(function(){
        var $province = $(this);
        $.post("../../jqueryServlet", {
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
     * 控制年龄字段只能输入数字
     */
    onlyNum($("#age"));
	onlyNum($("#phone"));
	/**
	 * 只能输入数字(Number only)
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
     * 控制密码字段禁止输入法
     */
    $("#password").css("ime-mode", "disabled");
    
    /**
     * 验证码的实时验证
     */
    $("#veriryCode").keyup(function(){
        var $this = $(this);
        var val = $this.val();
        if (val.length >= 4) {
            $.post("../../jqueryServlet", {
                method: "validateCode",
                verifycode: val,
            }, function(data){
                if (data == 1) {
                	$("#verifyimage").attr("src","../../image/rightcode.png");
					$("#verifycodespan").text("");
                }else{
					$("#verifyimage").attr("src","../../image/wrongcode.png");
					$("#verifycodespan").text("");
				}
            });
        }
    });
});
