$(function(){
    var pointerCursor = "pointer";
    changeCursor($(".delete"));
	
    /**
     * 点击删除
     */
    $(".delete").click(function(){
        $tbody = $("tbody");
        $tr = $(this).parent();
        var id = $tr.attr("id");
        if (window.confirm("Are your sure to delete this record?")) {
            $.post("SalServlet", {
                method: "delete",
                id: id,
            }, function(data){
                if (data > 0) {
                    $tr.remove();
                    alert("Deleted");
                }
            });
        }
    });
    
    //更新薪水级别名称的名字
    $(".name").click(function(){
        updateColumn("name", $(this));
    });
    
    /**
     * 输入框修改
     * @param {Object} columnName
     * @param {Object} $td
     */
    function updateColumn(columnName, $td){
        var txt = $td.text();
        var id = $td.parent().attr("id");
        $input = $("<input>").attr("type", "text");
        $td.text("");
        $td.append($input);
        $input.focus().val(txt);
        inputBlur($input, $td, columnName, id);
    }
    
    /**
     * 输入框失去焦点时，恢复表格原状
     * @param {Object} $input 输入框jquery对象
     * @param {Object} $td 表格的一格
     */
    function inputBlur($input, $td, columnName, id){
        $input.blur(function(){
            $td.text($input.val());
			var losal= $td.siblings(".losal").text();
			var hisal= $td.siblings(".hisal").text();
            $td.remove($input);
            $.post("SalServlet", {
                method: "updateSal",
                salname: $input.val(),
                salno: id,
                losal: losal,
                hisal: hisal,
            }, function(data){
                if (data > 0) {
                    $td.text($this.val());
                    $td.remove($this);
                }
            });
        });
    }
    
    /**
     * 改变某组件的鼠标悬停样式
     * @param {Object} $view jquery组件
     */
    function changeCursor($view){
        $view.hover(function(){
            $view.css("cursor", pointerCursor);
        });
    }
});
