package me.enkode.demo.cn

import me.enkode.demo.cn.Database.Entity
import spray.http.ContentTypes.`application/json`
import spray.http.MediaTypes.`text/x-scriptsh`
import spray.http.{ContentType, HttpEntity, MediaType}
import spray.httpx.marshalling.Marshaller

import scala.concurrent.{ExecutionContext, Future}

object Database {
  case class Entity(key: String, values: Map[String, String])
  object Entity {
    import spray.json._
    import DefaultJsonProtocol._
    import spray.httpx.SprayJsonSupport._

    implicit val entityFormat = jsonFormat2(Entity.apply)

    val `text/x-scriptfish` = MediaType.custom("text", "x-scriptfish")

    implicit val marshaller = Marshaller.of[Database.Entity](
      `application/json`,
      ContentType(`text/x-scriptsh`),
      ContentType(`text/x-scriptfish`)) {
      case (entity, `application/json`, mCtx) ⇒
        sprayJsonMarshaller[Database.Entity].apply(entity, mCtx)

      case (entity, contentType@ContentType(`text/x-scriptsh`, _), mCtx) ⇒
        val script = new StringBuilder
        script.append(s"# ${entity.key}").append('\n')
        entity.values foreach { case (key, value) ⇒
          script.append(s"""export $key="$value"""").append('\n')
        }
        mCtx.marshalTo(HttpEntity(contentType, script.toString()))

      case (entity, contentType@ContentType(`text/x-scriptfish`, _), mCtx) ⇒
        val script = new StringBuilder
        script.append(s"# ${entity.key}").append('\n')
        entity.values foreach { case (key, value) ⇒
          script.append(s"""set -e $key "$value"""").append('\n')
        }
        mCtx.marshalTo(HttpEntity(contentType, script.toString()))
    }
  }
}

trait Database {
  def find(key: String)(implicit ec: ExecutionContext): Future[Option[Entity]]
}

object StaticDatabase extends Database {
  val data = Map(
    "kender" → Map("user" → "kender", "home" → "/home/kender")
  )

  override def find(key: String)(implicit ec: ExecutionContext) = Future {
    data.get(key).map(values ⇒ Entity(key, values))
  }
}


