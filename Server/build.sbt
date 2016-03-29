import play.routes.compiler.InjectedRoutesGenerator
import play.sbt.PlayImport._
import play.sbt.PlayScala

name := """Domotic Room Server"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

resolvers ++= Seq(
  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "Millhouse Bintray"  at "http://dl.bintray.com/themillhousegroup/maven"
)

libraryDependencies ++= Seq(
  /*"com.typesafe.play" %% "play-cache" % "2.4.6",
  "org.specs2" %% "specs2-core" % "3.6" % "test",
  "org.specs2" %% "specs2-junit" % "3.6" % "test",
  "org.specs2" %% "specs2-scalacheck" % "3.6" % "test",*/
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.10" exclude("org.specs2", "*"),
  "com.themillhousegroup" %% "play2-reactivemongo-mocks" % "0.11.9_0.4.26" exclude("org.specs2", "*"),
  cache,
  specs2 % Test,
  "org.specs2" %% "specs2-scalacheck" % "3.6" % Test
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
scalacOptions in Test ++= Seq("-Yrangepos")

fork in run := true
