////////////////////////////////////
// Chart Configuration and Initialization
///////////////////////////////////

var graph;

////////////////////////////////////////////
//  Function to update the chart
////////////////////////////////////////////

function addNewDataToChart(data){
  var json = JSON.parse(data);
  var sensorName = json.sensorName;
    var sensorValue = Number(JSON.stringify(json.value));
    console.log({
	columns: [
	    [sensorName, sensorValue]
	]
    });
    if (graph == undefined)
    {
	graph = c3.generate({
	    bindto: '#chart',
	    data: {
		labels: false,
		columns: [
		    [sensorName, sensorValue]
		],
		type: 'spline'
	    },
	    grid: {
		y: {
		    lines: [{value: temperatureHighBound, class: 'temperatureBoundary'}, {value: temperatureLowerBound, class: 'temperatureBoundary'},{value: humidityHighBound, class: 'humidityBoundary'},{value: humidityLowerBound, class: 'humidityBoundary'}]
		}
	    }
	});
    }
    else{
	graph.flow({
	    columns: [
		[sensorName, sensorValue]
	    ]
	});
    }
}
