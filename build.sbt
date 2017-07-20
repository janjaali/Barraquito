organization := "net.habashi"

name := "Barraquito"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.12.1"

assemblyOutputPath in assembly := file("target/Barraquito.jar")

libraryDependencies ++= Seq(
  "org.sangria-graphql" %% "sangria" % "1.2.2",
  "org.sangria-graphql" %% "sangria-spray-json" % "1.0.0",

  "com.typesafe.akka" %% "akka-http" % "10.0.9",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.9",

  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)
