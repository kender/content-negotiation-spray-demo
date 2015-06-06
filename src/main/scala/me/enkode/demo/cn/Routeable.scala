package me.enkode.demo.cn

import spray.routing.Route

trait Routeable {
  def route: Route
}
