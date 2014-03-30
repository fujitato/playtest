package models
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class Task(id: String, label: String)

object Task {

  def all(): List[Task] = DB.withConnection("common") { implicit c =>
    SQL("select * from currency").as(task *)
  }

  def create(label: String) {
    DB.withConnection { implicit c =>
      SQL("insert into task (label) values ({label})").on(
        'label -> label).executeUpdate()
    }
  }

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from task where id = {id}").on(
        'id -> id).executeUpdate()
    }
  }

//  val task = {
//    get[Long]("id") ~
//      get[String]("label") map {
//        case id ~ label => Task(id, label)
//      }
//  }
  
    val task = {
    get[String]("currency_id") ~
      get[String]("local_cd") map {
        case currency_id ~ local_cd => Task(currency_id, local_cd)
      }
  }
}