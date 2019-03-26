$(document).ready(
        function() {
            $("button[name='sendGoods']").click(
                function () {
                    var id=$(this).attr("id");
                    $.post(
                        "sendGoods",
                        {orderId:id},
                        function (data) {
                            alert("发货成功");
                            window.location.href='http://localhost:8080/order/orderList';
                        }
                    )
                }
            );
            $("button[name='backGoods']").click(
                function () {
                    var id=$(this).attr("id");
                    $.post(
                        "backGoods",
                        {orderId:id},
                        function (data) {
                            alert("退款成功");
                            window.location.href='http://localhost:8080/order/orderList';
                        }
                    )
                }
            )
        }
)