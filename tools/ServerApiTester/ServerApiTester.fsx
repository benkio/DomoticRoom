// ===============================
// Description:
//
// Simple script used to test the API of the server.
// We want to send here a json clone of the json sent by the embedded System
// and see if the server works well without the needed of all the sensors
// and stuff
//
// Json samples:
(*
 [
  {
    "sensorName": "nomeSensore",
    "sensorType": 1,
    "value": true,
    "date": "2012-04-23T18:25:43.511Z"
  },
  {
    "sensorName": "nomeSensore",
    "sensorType": 2,
    "value": 100,
    "date": "2013-04-23T18:25:43.511Z"
  },
  {
    "sensorName": "nomeSensore",
    "sensorType": 2,
    "value": 100,
    "date": "2013-04-23T18:25:43.511Z"
  }
]
 *)
// ================================

#r "bin/FSharp.Data.dll"
open FSharp.Data
open System
open System.Threading

printfn "Welcom to the Server Api Tester"
printfn "==============================="

let sleepTimeMillis = 5000
let recursionTimes = 10

let booleanFromRandom rnd = if (rnd <= 50) then true else false

let jsonBooleanToSend (sensorName: string) (sensorType: int) (value: bool) (date: string)  = sprintf "{ \sensorName\: %s, \"sensorType\": %d, \"value\": %b, \"date\": %s } " sensorName sensorType value date

let jsonToSend (sensorName: string) (sensorType: int) (value: int) (date: string)  = sprintf "{ \sensorName\: %s, \"sensorType\": %d, \"value\": %d, \"date\": %s } " sensorName sensorType value date



let randomBuildBodyRequest () =
    let rnd = new Random()
    match rnd.Next(100) with
        | x when x < 80  ->
            printfn "Send Boolean Sensor Json"
            jsonBooleanToSend "booleanSensorName" (rnd.Next(1,4)) (booleanFromRandom (rnd.Next(100))) (DateTime.UtcNow.ToString "yyyy-MM-ddTHH:mm:ss.fffZ")
        | _ ->
            printfn "Send Non-Boolean Sensor Json"
            jsonToSend "nonBooleanSensorName" 5 (rnd.Next(100)) (DateTime.UtcNow.ToString "yyyy-MM-ddTHH:mm:ss.fffZ")

let rec send recursionTime =
    match recursionTime with
        | 0 -> "Program Finished"
        | _ ->  printfn "Sleep for %d milliseconds" sleepTimeMillis
                Thread.Sleep(sleepTimeMillis)
                let bodyRequest = randomBuildBodyRequest()
                printfn "%s" bodyRequest
                send (recursionTime-1)

send recursionTimes
