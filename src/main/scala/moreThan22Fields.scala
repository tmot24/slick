import slick.collection.heterogeneous.{HList, HCons, HNil}
import slick.collection.heterogeneous.syntax._
import scala.language.postfixOps
import slick.jdbc.H2Profile.api._
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


object moreThan22Fields extends App {

  case class Test22Fields(id: Long, productId: Long,
                          name1: String, value1: Int, name2: String, value2: Int, name3: String, value3: Int,
                          name4: String, value4: Int, name5: String, value5: Int, name6: String, value6: Int,
                          name7: String, value7: Int, name8: String, value8: Int, name9: String, value9: Int,
                          name10: String, value10: Int, name11: String, value11: Int, name12: String, value12: Int)

  class AttrTable(tag: Tag) extends Table[Test22Fields](tag, "attrs") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def productId = column[Long]("product_id")

    def name1 = column[String]("name1")

    def value1 = column[Int]("value1")

    def name2 = column[String]("name2")

    def value2 = column[Int]("value2")

    def name3 = column[String]("name3")

    def value3 = column[Int]("value3")

    def name4 = column[String]("name4")

    def value4 = column[Int]("value4")

    def name5 = column[String]("name5")

    def value5 = column[Int]("value5")

    def name6 = column[String]("name6")

    def value6 = column[Int]("value6")

    def name7 = column[String]("name7")

    def value7 = column[Int]("value7")

    def name8 = column[String]("name8")

    def value8 = column[Int]("value8")

    def name9 = column[String]("name9")

    def value9 = column[Int]("value9")

    def name10 = column[String]("name10")

    def value10 = column[Int]("value10")

    def name11 = column[String]("name11")

    def value11 = column[Int]("value11")

    def name12 = column[String]("name12")

    def value12 = column[Int]("value12")

    def * = (id :: productId ::
      name1 :: value1 :: name2 :: value2 :: name3 :: value3 ::
      name4 :: value4 :: name5 :: value5 :: name6 :: value6 ::
      name7 :: value7 :: name8 :: value8 :: name9 :: value9 ::
      name10 :: value10 :: name11 :: value11 :: name12 :: value12 ::
      HNil).mapTo[Test22Fields]
  }

  val attributes = TableQuery[AttrTable]

  val db = Database.forConfig("h2mem1")

  val program = for {
    _ <- attributes.schema.create
    _ <- attributes += Test22Fields(0L, 100L, "name1", 1, "name2", 2, "name3", 3,
      "name4", 4, "name5", 5, "name6", 6,
      "name7", 7, "name8", 8, "name9", 9,
      "name10", 10, "name11", 11, "name12", 12)
  } yield ()

  Await.result(db.run(program), 1.seconds)

  def check = Await.result(db.run(attributes.result), 1.seconds)

  println(check.foreach(println))


}