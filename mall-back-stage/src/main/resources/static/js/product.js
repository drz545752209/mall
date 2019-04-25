function checkOrCancelAll(){
    var isAllSelect=document.getElementById("select-all").checked;
    console.log(isAllSelect)
    var allCheck=document.getElementsByName("product-name");
    if(isAllSelect){
        for(var i=0;i<allCheck.length;i++){
            allCheck[i].checked=true;
        }
    }else{
        for(var i=0;i<allCheck.length;i++){
            allCheck[i].checked=false;
        }
    }
}

function getCheckBoxId() {
    var allCheck=document.getElementsByName("product-name");
    var checkBoxIds=new Array();
    for (var i=0;i<allCheck.length;i++){
        if (allCheck[i].checked){
            checkBoxIds.push(allCheck[i].id);
        }
    }
    var idStr=checkBoxIds.join(',');
    return idStr;
}

function batchDelete() {
    var idStr=getCheckBoxId();
    $.post("/product/batchDelete",{"idStr":idStr});
}

function del(id) {
    $.post("/product/delete",{"deleteId":id});
}

function downShelf() {
    var idStr=getCheckBoxId();
    $.post("/product/downShelf",{"idStr":idStr})
        .success(function () {
            alert("成功下架");
            window.location.href='http://localhost:8080/product/productList';
        });
}

function upShelf() {
    var  idStr=getCheckBoxId();
    $.post("/product/upShelf",{"idStr":idStr})
        .success(function () {
            alert("成功上架");
            window.location.href='http://localhost:8080/product/productList';
        });
}

function  create() {
    $('#insert_modal').modal('show');
}

function save(id) {
    $.get("/product/getProductForSave",{"saveId":id},function (data) {
        document.getElementById("saveProductId").value=data.id;
        document.getElementById("saveProductName").value=data.name;
        document.getElementById("saveStoreName").value=data.storeName;
        document.getElementById("saveType").value=data.type;
        document.getElementById("savePrice").value=data.price;
        document.getElementById("saveDescription").value=data.description;
        $('#modify_modal').modal('show');
    })
}



function checkFileExt(filename) {
    var flag = false; //状态
    var arr = [ "jpg", "png", "gif" ];
    //取出上传文件的扩展名
    var index = filename.lastIndexOf(".");
    var ext = filename.substr(index + 1);
    //循环比较
    for (var i = 0; i < arr.length; i++) {
        if (ext == arr[i]) {
            flag = true; //一旦找到合适的，立即退出循环
            break;
        }
    }
    //条件判断
    if (flag) {
        document.write("文件名合法");
    } else {
        document.write("文件名不合法");
    }
}

function toAddImgs(id,url) {
    var imgsLength = $('#productImgs')[0].files.length;
    var imgs=$('#productImgs')[0].files;
    var formData = new FormData();

    for (var i=0;i<imgsLength;i++){
        formData.append("file[]",imgs[i]);
    }

    var fileNames = document.getElementById(id).value;

    if (fileNames.length == 0) {
        alert("请选择文件");
        return;
    }

    $.ajax({
        url : url,
        type : 'POST',
        cache : false,
        data : formData,
        //这个参数是jquery特有的，不进行序列化，因为我们不是json格式的字符串，而是要传文件
        dataType : "json",
        processData : false,
        //注意这里一定要设置contentType:false，不然会默认为传的是字符串，这样文件就传不过去了
        contentType : false,
        success : function(data) {
            alert("sucess");
            var path="";
            for(var i in data){
                alert(data[i]);
                if (i==data.length-1){
                    path+=data[i];
                } else {
                    path+=data[i]+",";
                }
            }
            $('#img').val(path);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.textStatus);
            alert(XMLHttpRequest.readyState);
            alert(XMLHttpRequest.status);
        }
    });
}

$(document).ready(
    function () {
        $("#mod_form").submit(
            function () {
                alert("修改成功");
            }
        );
        $("#create_form").submit(
            function () {
                alert("添加成功");
            }
        );
    }
);