$(document).ready(
    function () {
        $("#mod_stock").click(
            function () {
                var id=$("#stock_id").attr('text');
                var count=$("#stock_count").attr('text');
                var stockInDate=$("#stock_inDate").attr('text');
                var stockOutDate=$("#stock_outDate").attr('text');
                if (count==null){
                    alert("count not can null");
                } else if (stockInDate==null) {
                    alert("stockInDate not can null");
                }else if (stockOutDate==null) {
                    alert("stockOutDate not can null");
                }
                $.post("/save",{"id":id,"count":count,"stockInDate":stockInDate,"stockOutDate":stockOutDate})
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