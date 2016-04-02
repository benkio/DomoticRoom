function convertSensorTypeIdToString(id){
    var result;
    var expression = String(id);
    switch(expression) {
        case "1":
            result = "Gas";
            break;
        case "2":
            result = "Humidity";
            break;
        case "3":
            result = "Light";
            break;
        case "4":
            result = "Movement";
            break;
        case "5":
            result = "Temperature";
            break;
    }
    return result;
}