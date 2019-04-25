function modify(tr){
    var id=$(tr).parent().parent().children("td").get(0).innerHTML;
    var count=$(tr).parent().parent().children().get(2).firstChild.value;
    var stockInDate=$(tr).parent().parent().children().get(3).firstChild.value;
    var stockOutDate=$(tr).parent().parent().children().get(4).firstChild.value;
    if (isNaN(count)) {
        alert("count must is numberType");
        return;
    }
    $.post("/stock/save",{"id":id,"count":count,"stockInDate":stockInDate,"stockOutDate":stockOutDate})
        .success(
            function () {
                alert("修改成功");
                window.location.href="http://localhost:8080/stock/stockList";
            }
        )
}

function reset(tr){
    var count=$(this).parent().parent().children().get(2).firstChild;
    var stockInDate=$(this).parent().parent().children().get(3).firstChild;
    var stockOutDate=$(this).parent().parent().children().get(4).firstChild;
    count.val("");
    stockInDate.val("");
    stockOutDate.val("");
}
