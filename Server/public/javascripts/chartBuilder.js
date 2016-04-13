////////////////////////////////////
// Chart Configuration and Initialization
///////////////////////////////////

var pubnub = PUBNUB.init({
  publish_key: 'demo',
  subscribe_key: 'demo'
});
var channel = "c3-spline" + Math.random();
eon.chart({
  channel: channel,
  history: true,
  flow: true,
  pubnub: pubnub,
  generate: {
    bindto: '#chart',
    data: {
      labels: false
    },
    grid: {
          y: {
              lines: [{value: temperatureHighBound, class: 'temperatureBoundary'}, {value: temperatureLowerBound, class: 'temperatureBoundary'},{value: humidityHighBound, class: 'humidityBoundary'},{value: humidityLowerBound, class: 'humidityBoundary'}]
          }
      }
  }
});

////////////////////////////////////////////
//  Function to update the chart
////////////////////////////////////////////

function addNewDataToChart(data){
  var json = JSON.parse(data);
  var sensorName = JSON.stringify(json.sensorName);
  var sensorValue = JSON.stringify(json.value);
  var data = { };
  data[sensorName] = sensorValue;
  console.log(data);
  pubnub.publish({
    channel: channel,
    message: {
      eon: data
    }
  });
}
