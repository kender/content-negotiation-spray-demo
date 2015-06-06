package me.enkode.demo.cn

import spray.routing.{HttpServiceActor, Route}

class Service(routes: Seq[Route]) extends HttpServiceActor {
  require(routes.nonEmpty, "routes must be nonEmpty")
  implicit val system = context.system

  val route: Route = routes.reduce(_ ~ _)

  override def receive = runRoute(route)
}