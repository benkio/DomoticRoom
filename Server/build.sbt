name := """DomoticRoom Server"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  //"io.reactivex" % "rxjava" % "1.1.0"
  //"com.netflix.rxjava" % "rxjava-scala" % "0.20.7"
  "com.typesafe.play" %% "play-streams_2.11" % "2.5.0-M1"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


fork in run := true
