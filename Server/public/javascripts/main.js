var socketUrl = "ws://localhost:9000/domoticRoom/status/getCurrentData";

var myWebSocket;
function connectToWS(_callback) {
    if (myWebSocket !== undefined) {
        myWebSocket.close()
    }
    myWebSocket = new WebSocket(socketUrl);
    myWebSocket.onmessage = function(event) {
        console.log("onmessage. content: " + event.data);
    }
    myWebSocket.onopen = function(evt) {
        console.log("onopen.");
    };
    myWebSocket.onclose = function(evt) {
        console.log("onclose.");
    };
    myWebSocket.onerror = function(evt) {
        console.log("Error!");
    };
    setTimeout(function() {
        _callback();
      }, 1000);
}
function sendMsg(message) {
    myWebSocket.send(message);
}
function closeConn() {
    myWebSocket.close();
}
