name := "spamager"

version := "1.0"

scalaVersion := "2.11.4"

scalacOptions := Seq( "-deprecation" )

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.7"
libraryDependencies += "io.spray" %% "spray-can" % "1.3.2"
libraryDependencies += "io.spray" %% "spray-routing" % "1.3.2"
libraryDependencies += "io.spray" %% "spray-client" % "1.3.1"

lazy val root = (project in file(".")).enablePlugins(SbtTwirl)
