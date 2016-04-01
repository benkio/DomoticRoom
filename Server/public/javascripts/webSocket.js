
//////////////////////////////////////////////////////////////////////////
// WebSocket Setup
// Require the Chart construction
/////////////////////////////////////////////////////////////////////////

var socketUrl = 'ws://localhost:9000/domoticRoom/status/getCurrentData';

// an observer for when the socket is open
var openObserver = Rx.Observer.create(function(e) {
  console.info('socket open');
});

// an observer for when the socket is about to close
var closingObserver = Rx.Observer.create(function() {
  console.log('socket is about to close');
});

// create a web socket subject
socket = Rx.DOM.fromWebSocket(
   /*'ws://echo.websocket.org', */socketUrl,
  "TCP",
  openObserver,
  closingObserver);

// subscribing creates the underlying socket and will emit a stream of incoming
// message events
socket
    .filter(function (x, idx, obs) {
        var json = JSON.parse(x.data);
        return json.type == 2 || json.type == 5;
     })
    .map(function (x, idx, obs) {
        var json = JSON.parse(JSON.stringify(x.data));
        var result = addNewDataToChart(json);
        return result;
     })
     .filter(function (x, idx, obs) {
        return previousData != x
     })
    .subscribe(
      function(e) {
        console.log(JSON.stringify(e));
        currentChart.flow(e);
        previousData = e;
      },
      function(e) {
        // errors and "unclean" closes land here
        console.error('error: %s', e);
      },
      function() {
        // the socket has been closed
        console.info('socket closed');
      });

////////////////////////////////////////////////
// Utitilies Functions Using the Web Socket
////////////////////////////////////////////////

function createNewIntervalStream(updateInterval){
    var source = Rx.Observable
        .interval(updateInterval /* ms */)
        .timeInterval();

    var subscription = source.subscribe(
        function (x) {
            socket.onNext(json);
        },
        function (err) {
            console.log('Error: ' + err);
        },
        function () {
            console.log('Completed');
        }
    );
    return source;
};
