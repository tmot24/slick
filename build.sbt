name := "slick"

version := "0.1"

scalaVersion := "2.13.4"


libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick"           % "3.3.3",
  "com.h2database"      % "h2"              % "1.4.197",
  "ch.qos.logback"      % "logback-classic" % "1.2.3",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3"
)

/*
initialCommands in console := """
                                |import scala.concurrent.ExecutionContext.Implicits.global
                                |import scala.concurrent.Await
                                |import scala.concurrent.duration._
                                |val schema = new Schema(slick.jdbc.H2Profile)
                                |import schema._
                                |import profile.api._
                                |def exec[T](action: DBIO[T]): T = Await.result(db.run(action), 2 seconds)
                                |val db = Database.forConfig("chapter01")
""".trim.stripMargin
*/

