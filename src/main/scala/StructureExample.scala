import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import slick.jdbc.JdbcProfile

trait Profile {
  val profile: JdbcProfile
}

trait TablesH2 {

  this: Profile =>

  import profile.api._

  case class Message(sender: String, content: String, id: Long = 0L)

  final class MessageTable(tag: Tag) extends Table[Message](tag, "message") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def sender = column[String]("sender")

    def content = column[String]("content")

    def * = (sender, content, id).mapTo[Message]
//    def * = (sender, content, id).<>(Message.tupled, Message.unapply)
  }

  object messages extends TableQuery(new MessageTable(_)) {
    def messagesFrom(name: String): Query[MessageTable, Message, Seq] = this.filter(_.sender === name)

    val numContent = this.map(_.content).length
  }

}


object StructureExample extends App {

  class Schema(val profile: JdbcProfile) extends TablesH2 with Profile

  val schema = new Schema(slick.jdbc.H2Profile)

  import schema._, profile.api._

  val db = Database.forConfig("h2mem1")

  val freshData = Seq(
    Message("Dave", "Hello, HAL. Do you read me, HAL?"),
    Message("HAL", "Affirmative, Dave. I read you."),
    Message("Dave", "Open the pod bay doors, HAL."),
    Message("HAL", "I'm sorry, Dave. I'm afraid I can't do that.")
  )

  val start = for {
    _ <- messages.schema.create
    _ <- messages ++= freshData
  } yield ()

  Await.result(db.run(start), 1.seconds)

  def check2: Seq[schema.Message] = Await.result(db.run(messages.result), 1.seconds)
  println(check2.foreach(println))


}
