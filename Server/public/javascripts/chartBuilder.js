var chartBaseStructure = {
    bindto: '#chart',
    data: {
        x: 'x',
        xFormat : '%Y-%m-%d %H:%M:%S',
//        xFormat: '%Y%m%d', // 'xFormat' can be used as custom format of 'x'
        columns: [
            ['x']//, '2013-01-01', '2013-01-02', '2013-01-03', '2013-01-04', '2013-01-05', '2013-01-06'],
//            ['x', '20130101', '20130102', '20130103', '20130104', '20130105', '20130106'],
            // ['data1', 30, 200, 100, 400, 250],
            //['data2', 130, 340, 200, 500, 250, 350]
        ]
    },
    axis: {
        x: {
            type: 'timeseries',
            tick: {
                format: '%H:%M:%S'
            }
        }
    }
};

var currentChart;
function resetChart(){ currentChart = chartBaseStructure; }
resetChart();

function existReturnIndex(sensorName, outerArray){
    var result = 0;
    outerArray.forEach(function(array){
        if (array[0] == sensorName)
            return result;
        result = result + 1;
    });
    return -1;
}

function addNewDataToChart(data){
    var json = JSON.parse(data);
    if (json.type == 5 || json.type == 2){
        currentChart.data.columns[0].push(json.dateCreation);
        var index = existReturnIndex(json.sensorName,currentChart.data.columns);
        if (index == -1){
            var newArray = [ json.sensorName ];
            for (i = 1; i < (currentChart.data.columns[0].length -1); i++) {
                newArray.push(0);
            }
            newArray.push(json.value);

            currentChart.data.columns.push(newArray);
        }else{
            currentChart.data.columns[index].push(json.value);
        }
    }
}

//////////////////////////////////////////////////
// chart stream generator
/////////////////////////////////////////////////

var source = Rx.Observable
        .interval(1000 /* ms */)
        .timeInterval();

var subscription = source.subscribe(
    function (x) {
        c3.generate(currentChart);
        resetChart();
    },
    function (err) {
        console.log('Error: ' + err);
    },
    function () {
        console.log('Completed');
    }
);