var stompClient = null;

function connect() {
    var socket = new SockJS('/softmedia');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        // setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/confreload', function (greeting) {
            // showGreeting(JSON.parse(greeting.body).content);
            console.log('window reolad' + greeting);
            window.location.reload(true);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    // setConnected(false);
    console.log("Disconnected");
}

/*
$(function () {
   connect();
});*/
