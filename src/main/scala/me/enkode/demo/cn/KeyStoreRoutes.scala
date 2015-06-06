package me.enkode.demo.cn

import spray.http._
import spray.routing.{Directives, Route}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class KeyStoreRoutes(database: Database)(implicit ec: ExecutionContext)
  extends Routeable with Directives {

  val getKey: Route = {
    (get & path("key" / Segment)) { (key) ⇒
      detach() {
        onComplete(database.find(key)) {
          case Success(Some(entity)) ⇒ complete(entity)
          case Success(None) ⇒ complete(StatusCodes.NotFound)
          case Failure(t) ⇒ complete(StatusCodes.InternalServerError, t.getMessage)
        }
      }
    }
  }

  override def route = getKey
}
