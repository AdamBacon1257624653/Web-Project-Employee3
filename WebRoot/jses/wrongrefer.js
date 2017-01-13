$(function(){
	var pointerCursor="pointer";
	registerTableEvent();
	/**
	 * 更新列的状态:编辑和非编辑状态
	 * @param {Object} columnName 列名
	 * @param {Object} $td 列的jquery对象
	 */
	function updateColumn(columnName, $td){
        var txt = $td.text();
        var id = $td.parent().attr("id");
		if(columnName=='age'||columnName=='salary'||     
			columnName=='dutytime'||columnName=='offtime'
			||columnName=='phone'){//输入框修改
			$input = $("<input>").attr("type", "text");
	        $td.text("");
	        $td.append($input);
	        $input.focus().val(txt);
			onlyNum($input);
       		inputBlur($input,columnName,id,$td,txt);
		}else{//select修改
			if(!$td.is(":has(select)")){
				$td.text("");
				$select = $("<select/>").attr("name", "sex");
				if(columnName=='sex'){
					var $maleOption=$("<option/>").val("Male").text("Male");
					var $femaleOption=$("<option/>").val("Female").text("Female");
					if(txt=='Male'){
						$maleOption.attr("selected","selected");
					}else if(txt='女'){
						$femaleOption.attr("selected","selected");
					}
					$select.append($maleOption).append($femaleOption);
					selectAjaxChange($select,columnName,id,$td);
				}else if(columnName=='department'){
					var depts=['Financial Department','Programmer Department','Phone Department','Sales Department','Marketing Department','IT Department'];
					for(i in depts){
						var $deptOption=$("<option/>").val(i).text(depts[i]);
						if(txt==depts[i]){
							$deptOption.attr("selected","selected");
						}
						$select.append($deptOption);
					}
					selectAjaxChange($select,"department",id,$td);
				}else if(columnName=='place'){
					var jsonObj= $.parseJSON(provincesStr);
					var $proSelect=$("<select/>").append($("<option/>").val("")
								.text("Options"));
					var $citySelect=$("<select/>");
					var province=$td.attr("pro");
					var city=$td.attr("city");
					for(var i in jsonObj){
						var $provinceOption=$("<option/>").val(jsonObj[i].name)
								.text(jsonObj[i].name);
						if(province==jsonObj[i].name){
							$provinceOption.attr("selected","selected");
						}
						$proSelect.append($provinceOption);						
					}
					updateCities(province,$citySelect,city);
					$td.append($proSelect).append($citySelect);
					provinceChange($proSelect,$citySelect);
					cityChange($proSelect,$citySelect,id,$td);
				}
			}
		}
    };
	
	/**
	 * 城市改变事件
	 * @param {Object} $citySelect
	 */
	function cityChange($proSelect,$citySelect,id,$td){
		$citySelect.change(function(){
			var province=$proSelect.val();
			var city=$citySelect.val();
			if(city==""){
				alert("Please select your city");
			}else{
				$.post("jqueryServlet",{
					method: "updateWrong",
		            newdata: province+":"+city,
		            columnName: "place",
		            id: id,
				},function(data){
					$td.text(province+city);
	           		$td.remove($select);
				});
			}
		});
	}
	
	/**
	 * 当省份的select发生了改变时
	 * @param {Object} $proSelect
	 */
	function provinceChange($proSelect,$citySelect){
		$proSelect.change(function(){
		    updateCities($proSelect.val(),$citySelect,"");
		});
	 };
	 
	 /**
	  * 城市的更新
	  * @param {Object} province 需要更新的省份名称
	  * @param {Object} $citySelect 
	  * @param {Object} city 原本城市名 <li> 不为""时,是第一次加载<li>否则，是第一次加载
	  */
	 function updateCities(province,$citySelect,city){
	 	$.post("jqueryServlet", {
		        method: "changeProvince",
		        provincename: province,
		    }, function(data){
		    if (data != "") {
		        var jsonObject = $.parseJSON(data);
				$citySelect.html("<option value=''>Options</option>");
		        for (var i in jsonObject) {
					var $cityOption=$("<option/>").val(jsonObject[i].name).text(jsonObject[i].name);
					if(city!=""&&city==jsonObject[i].name){
						$cityOption.attr("selected","selected");
					}
		            $citySelect.append($cityOption);
		     	   }
		   		}
			});
	 }
	/**
	 * 将select添加进表格,并且添加select改变事件
	 * @param {Object} $select
	 * @param {Object} columnName
	 * @param {Object} id
	 * @param {Object} $td
	 * @param {Object} value 如果为0：则没什么用，否则用于回显td
	 */
	function selectAjaxChange($select,columnName,id,$td){
		$td.append($select);
		$select.change(function(){
			$.post("jqueryServlet",{
				method: "updateWrong",
	            newdata: $select.val(),
	            columnName: columnName,
	            id: id,
			},function(data){
				var value=$select.val();
				if(columnName=='department'){
					value=$("option:selected").text();
				}
				$td.text(value);
           		$td.remove($select);
			});
		});
	}
	/**
	 * 输入框失去焦点事件
	 * @param {Object} $input
	 * @param {Object} columnName
	 * @param {Object} id
	 * @param {Object} $td
	 */
	function inputBlur($input,columnName,id,$td,txt){
		 $input.blur(function(){
	        var $this = $(this);
			//判断电话的合法性
			if(columnName=='phone'){
				if($input.val().length!=11){
					recoverTd($input,$td,txt);
					alert("Please type correct Tel no!");
					return;
				}
			}
			//判断年龄的合法性
			if(columnName=='age'){
				if($input.val()>200||$input.val()<1){
					recoverTd($input,$td,txt);
					alert("Please type correct age!");
					return;
				}
			}
	        $.post("jqueryServlet", {
	            method: "updateWrong",
	            newdata: $input.val(),
	            columnName: columnName,
	            id: id,
	        }, function(data){
	            $td.text($this.val());
	            $td.remove($this);
	        });
	    });
	}
	
	/**
	 * 修改内容失败后恢复表格原状
	 * @param {Object} $input
	 * @param {Object} $td
	 * @param [Object] txt
	 */
	function recoverTd($input,$td,txt){
		$td.text(txt);
        $td.remove($input);
	}
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
	 * 注册表格事件
	 */
	function registerTableEvent(){
		/**
		 * 更新操作
		 */
		//更新非数字
	  	$(".sex").click(function(){
       		updateColumn("sex", $(this));
	    });
	  	$("td[cellname='department']").click(function(){
       		updateColumn("department", $(this));
	    });
	  	$("td[cellname='place']").click(function(){
       		updateColumn("place", $(this));
	    });
		//更新数字
	    $("td[cellname='age']").click(function(){
	        updateColumn("age", $(this));
	    });
	    $("td[cellname='dutytime']").click(function(){
	        updateColumn("dutytime", $(this));
	    });
	    $("td[cellname='offtime']").click(function(){
	        updateColumn("offtime", $(this));
	    });
	    $("td[cellname='salary']").click(function(){
	        updateColumn("salary", $(this));
	    });
	    $("td[cellname='phone']").click(function(){
	        updateColumn("phone", $(this));
	    });
	}
});
