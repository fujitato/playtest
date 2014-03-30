package models
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class Corp_bss(global_id: String,corp_frml_name_vch: String, country_id: String, splr: Int)

object Corp_bss {

  def globalIdsBySplrRegion(country_id: String, splr: Int): List[Corp_bss] = DB.withConnection("common") { implicit c =>
    SQL(
      """
SELECT global_id,corp_frml_name_vch,country_id,splr_id FROM corp_bss
WHERE splr_id={a}
AND country_id={b}
          """).on("a" -> splr, "b" -> country_id).as(corp_bss *)
  }
  val corp_bss = {
    get[String]("global_id") ~
    get[String]("corp_frml_name_vch")~
      get[String]("country_id") ~
      get[Int]("splr_id") map {
        case global_id ~ corp_frml_name_vch~country_id ~ splr_id => Corp_bss(global_id, corp_frml_name_vch,country_id, splr_id)
      }
  }
}
