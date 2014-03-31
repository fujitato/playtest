package models
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import models.AnormImplicits.RichSQL

case class Corp_acc_smry(year:Long,period: Long, count: Long)

object Corp_acc_smry {

  def allPeriodBySplrRegionFY2011On( global_ids: List[String]): List[Corp_acc_smry] = DB.withConnection("zaimu") { implicit c =>
    RichSQL(
      """
select acc_sttl_year,acc_sttl_period_num,count(*) count  from corp_acc_smry
where global_id IN({a})
AND acc_sttl_period_num >201101
GROUP BY acc_sttl_period_num
          """).onList("a" -> global_ids).toSQL.as(corp_acc_smry *)            
  }
  val corp_acc_smry = {
    get[Long]("acc_sttl_year")~
    get[Long]("acc_sttl_period_num") ~
      get[Long]("count") map {
        case acc_sttl_year~acc_sttl_period_num ~ count => Corp_acc_smry(acc_sttl_year,acc_sttl_period_num, count)
      }
  }
  def onList[A](args: (String, Iterable[A])*)(implicit toParameterValue: (A) => ParameterValue[A]) = {
    val condensed = args.map {
      case (name, values) =>
        val search = "{" + name + "}"
        val valueNames = values.zipWithIndex.map { case (value, index) => name + "_" + index }
    }
  }
}

