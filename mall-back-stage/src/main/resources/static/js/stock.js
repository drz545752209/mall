$(document).ready(
    function () {
        $("#mod_stock").click(
            function () {
                var id=$(this).parent().parent().children("td").get(0).innerHTML;
                var count=$(this).parent().parent().children().get(2).firstChild.value;
                var stockInDate=$(this).parent().parent().children().get(3).firstChild.value;
                var stockOutDate=$(this).parent().parent().children().get(4).firstChild.value;
                if (!isNaN(count)) {
                    alert("count must is numberType");
                }
                if (count==null){
                    alert("count not can null");
                } else if (stockInDate==null) {
                    alert("stockInDate not can null");
                }else if (stockOutDate==null) {
                    alert("stockOutDate not can null");
                }
                $.post("/stock/save",{"id":id,"count":count,"stockInDate":stockInDate,"stockOutDate":stockOutDate})
                    .success(
                        function () {
                            alert("修改成功");
                            window.location.href="http://localhost:8080/stock/stockList";
                        }
                    )
            }
        );
        $("#reset_stock").click(
            function () {
                $("#stock_count").val("");
                $("#stock_inDate").val("");
                $("#stock_outDate").val("");
            }
        )
    }
);