
var trueImg = "http://www.clker.com/cliparts/0/e/t/g/O/k/bubble-green-md.png";
var falseImg = "http://worldartsme.com/images/red-bubble-clipart-1.jpg";
var inserted = 0;

function BooleanGridContent(id, value, type, imgUrl){
    return "<div class='booleanSensorNameText' id='"+ id +"Text'> Name: <strong>"+ id +"</strong></br> Value: <strong>"+ value +"</strong> </br> Type: <strong>"+ convertSensorTypeIdToString(type) +"</strong> </div> <div><img class='booleanSensorStatusImg' align='middle' id='"+ id +"Img' src='"+ imgUrl +"' />:\<div>";
}

function BuildBooleanGridContent(jsonData){
    var value, imgUrl;
    var json = JSON.parse(jsonData);
    if (json.rangeViolation){
        imgUrl = falseImg;
        value = "False";
    }
    else{
        imgUrl = trueImg;
        value = "True";
    }
     return BooleanGridContent(json.sensorName, value, json.type, imgUrl);
}

function AddOrUpdateBooleanSensorContent(jsonData){
    var json = JSON.parse(jsonData);
    var elementToUpdateText = $('#' + json.sensorName + 'Text');
    var elementToUpdateImg = $('#' + json.sensorName + 'Img');
    if (elementToUpdateText.length && elementToUpdateImg.length) {
      if (json.rangeViolation){
        elementToUpdateText.html(" Name: <strong>"+ json.sensorName +"</strong></br> Value: <strong>False</strong> </br> Type: <strong>"+ convertSensorTypeIdToString(json.type) +"</strong> ");
        elementToUpdateImg.attr("src", falseImg);
      }
      else {
        elementToUpdateText.html(" Name: <strong>"+ json.sensorName +"</strong></br> Value: <strong>True</strong> </br> Type: <strong>"+ convertSensorTypeIdToString(json.type) +"</strong> ");
        elementToUpdateImg.attr("src", trueImg);
      }
    } else {
        var content = BuildBooleanGridContent(jsonData);
        if(inserted == 0){
            $("#BooleanSensorGridBody").append("<tr>");
            $("#BooleanSensorGridBody").append("<td>" + content + "</td>");
            $("#BooleanSensorGridBody").append("</tr>");
        }
        else{
            $("#BooleanSensorGridBody").append("<td>" + content + "</td>");
        }
        inserted++;
    }
    inserted %= 2;
}