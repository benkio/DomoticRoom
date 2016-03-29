package models.persistenceStore.savers

import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.bson.BSONDocument

import scala.concurrent.ExecutionContext.Implicits.global
import reactivemongo.play.json._

import scala.util.{Failure, Success}

/**
  * Created by parallels on 1/24/16.
  */
object StoreUtils {
  def store(dataCollection:JSONCollection, dataFormatted : BSONDocument): Unit ={
    val future = dataCollection.insert(dataFormatted)

    future.onComplete {
      case Failure(e) => println ("Error Insertion of the Data" + e.getMessage)
      case Success(lastError) => {
        println("successfully inserted document with lastError = " + lastError)
      }
    }
  }
}
