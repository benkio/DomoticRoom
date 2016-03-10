import play.routes.compiler.InjectedRoutesGenerator
import play.sbt.PlayScala

name := """Domotic Room Server"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

scalaVersion := "2.11.7"

resolvers ++= Seq(
  //"scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "Millhouse Bintray"  at "http://dl.bintray.com/themillhousegroup/maven"
)

resolvers += Resolver.url("Typesafe Ivy releases", url("https://repo.typesafe.com/typesafe/ivy-releases"))(Resolver.ivyStylePatterns)

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.specs2" %% "specs2-core" % "3.0" % "test",
  "org.specs2" %% "specs2-scalacheck" % "3.0",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.9",
  "com.themillhousegroup" %% "play2-reactivemongo-mocks" % "0.11.9_0.4.26"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
scalacOptions in Test ++= Seq("-Yrangepos")


fork in run := true