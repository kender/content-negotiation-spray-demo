package me.enkode.demo.cn

import akka.actor.{ActorLogging, Actor, Props, ActorSystem}
import akka.io.IO
import spray.can.Http
import spray.routing._

object ContentNegotiationDemoMain extends App {
  implicit val actorSystem = ActorSystem()

  val rootRef = actorSystem.actorOf(Props(new Actor with ActorLogging {
    override def receive = Actor.emptyBehavior

    override def preStart() = {
      import context.dispatcher
      val routes = Vector(
        new KeyStoreRoutes(StaticDatabase)
      ).map(_.route)
      val service = context.actorOf(Props(new Service(routes)))
      IO(Http) ! Http.Bind(service, "0.0.0.0", port = 8080)
    }
  }))
}
