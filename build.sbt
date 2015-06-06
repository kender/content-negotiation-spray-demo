organization := "me.enkode.demos"
name := "spray-content-negotiation"
scalaVersion := "2.11.6"

Revolver.settings

libraryDependencies ++= modules.all

lazy val modules = new {
  def akka(name: String, version: String = "2.3.11") = "com.typesafe.akka" %% s"akka-$name" % version
  def spray(name: String, version: String = "1.3.3") = "io.spray" %% s"spray-$name" % version

  val all = Seq(
    akka("actor"),
    spray("can"),
    spray("routing"),
    spray("httpx"),
    spray("json", "1.3.2")
  )
}