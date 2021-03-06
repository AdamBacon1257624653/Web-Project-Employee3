$(function(){
	var linkClr="#0000FF";
	var grayClr="#888888";
	var notPickedClr="#000000";
	var keyword="";
	var pageCount=0;
	var currentpage=0;
	/**
	 * 模糊查询按钮的点击
	 */
	$("#QueryBtn").click(function(){
		currentpage=1;
		keyword = $("#NameKeyWord").val();
		if(keyword==""){
			$("#keywordError").text("Please type");
		}else{
			ajaxChangePage();
		}
	});
	
	/**
	 * 关键字输入事件
	 */
	$("#NameKeyWord").keyup(function(){
		var $this=$($this);
		var keyword = $this.val();
		if(keyword!=""){
			$("#keywordError").text("");
		}
	});
   
	/**
	 * 重新设置表格
	 * @param {Object} data ajax返回的数据
	 */
	function updateTable(data){
		var $body=$("body");
		if($body.is(":has(table)")){
			$("body table").remove();
		}
		if(":has(#pageIndexDiv)"){
			$("#pageIndexDiv").remove();
		}
		if(data==0){
			$body.append($("<label/>").attr("align","center")
				.css("color","#888888").text("No result"));
			return;
		}
		var $table=$("<table/>").attr("align","center").attr("cellspacing","0")
			.attr("cellpadding","12").attr("border","1")
			.css("margin-top","30px");
		var $thead=$("<thead/>");
		var theads=["Name","Gender","Age","Address","Telephone No.","Word Days","Leave Days","Salary","Department","Salary rank"];
		for(var i in theads){
			var $td=$("<td/>").attr("align","center").text(theads[i]);
			$thead.append($td);
		}
		var $tbody=$("<tbody/>");
		var jsonObject = $.parseJSON(data);
		for(var i in jsonObject){
			var jobj=jsonObject[i];
			var $tr=$("<tr/>").attr("id",jobj.id);
			var $nameTd=$("<td/>").attr("cellname","name").attr("align","center").text(jobj.name);
			var $sexTd=$("<td/>").attr("cellname","sex").attr("align","center").text(jobj.sex);
			var $ageTd=$("<td/>").attr("cellname","age").attr("align","center").text(jobj.age);
			var $placeTd=$("<td/>").attr("cellname","place").attr("align","center").text(jobj.province+jobj.city);
			var $phoneTd=$("<td/>").attr("cellname","phone").attr("align","center").text(jobj.phone);
			var $dutytimeTd=$("<td/>").attr("cellname","dutytime").attr("align","center").text(jobj.dutytime);
			var $offtimeTd=$("<td/>").attr("cellname","offtime").attr("align","center").text(jobj.offtime);
			var $salaryTd=$("<td/>").attr("cellname","salary").attr("align","center").text(jobj.salary);
			var dname ="";
			if(jobj.department==null){
				dname="No Result";
			}else{
				dname=jobj.department.name;
			}
			var $departmentTd=$("<td/>").attr("cellname","department").attr("align","center").text(dname);
			var $salnameTd=null;
			if(jobj.sal==null){
				$salnameTd=$("<td/>").attr("cellname","salname").attr("align","center").text("No result");
			}else{
				$salnameTd=$("<td/>").attr("cellname","salname").attr("align","center").text(jobj.sal.salname);
			}
			$tr.append($nameTd).append($sexTd).append($ageTd)//
				.append($placeTd).append($phoneTd).append($dutytimeTd)//
				.append($offtimeTd).append($salaryTd).append($departmentTd).append($salnameTd);//
			$tbody.append($tr);
		}
		$table.append($thead).append($tbody);
		$body.append($table);
		var $div=$("<div/>").attr("id","pageIndexDiv").attr("align","center")
					.css("margin-top","10px");
		var $previousSpan=$("<span/>").attr("class","pagechange").attr("id","previousspan")
			.css("color",linkClr).css("font-size","20").css("text-decoration","underline")
			.css("margin-right","20px")
			.text("Previous page");
		var $nextSpan=$("<span/>").attr("class","pagechange").attr("id","nextspan")
			.css("color",linkClr).css("font-size","20").css("text-decoration","underline")
			.text("Next page");
		$div.append($previousSpan);
		if(pageCount>1){
			for(var i=1;i<=pageCount;i++){
				var clr=notPickedClr;
				if(i==1){
					clr=linkClr;
				}
				var $pageLable=$("<label/>").attr("class","pagechange")//
					.attr("pagename","pagename").attr("id","page"+i)//
					.css("font-size","20").css("text-decoration","underline")
					.css("margin-right","20px").css("color",clr).text(i);
				$div.append($pageLable);
			}
			$div.append($nextSpan);
			$body.append($div);
		}
		tableChangeEvent();
	}
	
	/**
	 * ajax切换页面
	 * @param {Object} currentpage 当前要显示页
	 */
	function ajaxChangePage(){
		$.post("jqueryServlet",{
			method:"QueryLike",
			currentpage:currentpage,
			keyword:keyword,
		},function(data){
			var arr=data.split("//");
			pageCount=arr[1];
			updateTable(arr[0]);
		});
	}
	
	/**
	 * 改变上一页/下一页的颜色
	 */
	function tableChangeEvent(){
		var grayClr="#888888";
		if(currentpage==pageCount){
			$("#nextspan").css("color",grayClr);
		}else{
			$("#nextspan").css("color",linkClr);
		}
		if(currentpage==1){
			$("#previousspan").css("color",grayClr);
		}else{
			$("#previousspan").css("color",linkClr);
		}
		$("[pagename='pagename']").each(function(){
			var $this=$(this);
			if($this.attr("id")==("page"+currentpage)){
				$("#page"+currentpage).css("color",linkClr);
			}else{
				$this.css("color",notPickedClr);
			}
		});
		//改变cursor的样式
	    $(".pagechange").hover(function(){
	        $view = $(this);
	        $view.css("cursor", "pointer");
	    });
		//页面切换事件
		changePageEvent();
	}
	
	/**
     * 页面切换事件
     */
	function changePageEvent(){
		/**
		 * 上一页
		 */
	    $("#previousspan").click(function(){
	        if (currentpage > 1) {
			   currentpage--;
	           ajaxChangePage();
	        }
	    });
	    /**
	     * 下一页
	     */
	    $("#nextspan").click(function(){
	        if (currentpage < pageCount) {
				currentpage++;
	            ajaxChangePage();
	        }
	    });
		/**
		 * page改变到指定页的点击事件
		 */
		$("[pagename='pagename']").click(function(){
			var $this=$(this);
			currentpage=$this.text();
			ajaxChangePage();
		});
	}
});
