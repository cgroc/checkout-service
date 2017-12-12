name := "checkout-service"

version := "0.1"

scalaVersion := "2.12.4"

val Http4sVersion = "0.17.5"
val LogbackVersion = "1.2.3"
val ScalatestVersion = "3.0.4"
val CirceVersion = "0.8.0"

libraryDependencies ++= Seq(
  "org.http4s"     %% "http4s-blaze-server"  % Http4sVersion,
  "org.http4s"     %% "http4s-circe"         % Http4sVersion,
  "org.http4s"     %% "http4s-dsl"           % Http4sVersion,
  "io.circe"       %% "circe-core"           % CirceVersion,
  "io.circe"       %% "circe-generic"        % CirceVersion,
  "io.circe"       %% "circe-parser"         % CirceVersion,
  "io.circe"       %% "circe-literal"        % CirceVersion,  "org.scalactic" %% "scalactic" % ScalatestVersion,
  "org.scalatest" %% "scalatest" % ScalatestVersion % "test",
  "ch.qos.logback" %  "logback-classic"      % LogbackVersion
)
