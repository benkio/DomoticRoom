package models.persistenceStore.savers

import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument
import scala.concurrent.ExecutionContext.Implicits.global

import scala.util.{Success, Failure}

/**
  * Created by parallels on 1/24/16.
  */
object StoreUtils {
  def store(dataCollection:BSONCollection, dataFormatted : BSONDocument): Unit ={
    val future = dataCollection.insert(dataFormatted)

    future.onComplete {
      case Failure(e) => println ("Error Insertion of the Data" + e.getMessage)
      case Success(lastError) => {
        println("successfully inserted document with lastError = " + lastError)
      }
    }
  }
}
