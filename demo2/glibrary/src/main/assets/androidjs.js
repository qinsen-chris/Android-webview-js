//js调用java
function showToast(){
    window.myTosat.showMessage();
}

function showToast2(){
    console.log("testjs.html");

}

//js回调
function sdk_java_callback(obj){
    var jsonStr = JSON.stringify(obj );
    window.myTosat.sdk_java_callback(jsonStr);
}

function sdk_java_log(obj){
    var jsonStr = JSON.stringify(obj );
    window.myTosat.sdk_java_log(jsonStr);
}

function sdk_java_notification(obj){
    var jsonStr = JSON.stringify(obj );
    window.myTosat.sdk_java_notification(jsonStr);
    /*console.log(obj);*/
}


//函数返回值  btn1
function test_js(){
    var t = {
        name:"小明",
        age:18,
        last:[999,888,777],
        ok:{
            p:112,
            b:"哈哈"
        }
    };
    return t;
}

//测试传参 btn2
function tConect(name,age){
    //alert(name);
    var user= {
        name :name,
        age : age
    }
    sdk_java_notification(user);
    //console.log(user);
}

// btn3
function requestHQ(){
    var table = {
    info:{row:"2",totalRows:99},
    data:[1,2,3,4]
    }

    var result = {
        id:10,
        data:table
    }
    sdk_java_callback(result);
    //console.log(result);
}


function websocket_request(){
    let socket = new WebSocket("ws://139.217.187.50:8080/gangtise/websocket", "gangtise");
    socket.binaryType = "arraybuffer";
    socket.onmessage = function(event) {
        console.log(event)
    };
    socket.onopen = function(event) {
        console.log("连接成功!")
        socket.send(' {"type":"login","gid":{"pg":"1","idx":"-1"},"account":"525049543@qq.com", "password":"9fe2a42650c1d8a9e720a12f070f7c82"}')

        var result = {
                id:11,
                mes:"连接成功!"
            }
        sdk_java_notification(result)
    };
    socket.onclose = function(event) {
        console.log("连接断开!")
    }
}

function http_request(){
    var httpRequest = new XMLHttpRequest();
    // url
    httpRequest.open("POST", 'http://yhty.gangtise.com.cn:9000/auth/oauth/token?username=525049543@qq.com&password=9fe2a42650c1d8a9e720a12f070f7c82&grant_type=password&lang=ch-zn');
    // header
    httpRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
    httpRequest.setRequestHeader("Authorization", "Basic WUhfQ0xJRU5UOmdhbmd0aXNl")
    httpRequest.setRequestHeader("usertype", "1")
    httpRequest.setRequestHeader("tenantId", "2")
    httpRequest.send();

    httpRequest.onreadystatechange = function() {
        if (httpRequest.readyState == 4) {
            var text = "Connect server fail! The server responded with a status " + httpRequest.status;
            if ( httpRequest.status == 200){
                if (httpRequest.responseText != undefined) {
                    text = httpRequest.responseText
                    console.log(text)

                    sdk_java_callback(text);
                }
            }
        }
    }
}

GSDK_HOOK.start()

