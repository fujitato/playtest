package controllers

import play.api._
import play.api.mvc._
import play.api.db._
import play.api.data._
import play.api.data.Forms._
import models.Corp_acc_smry
import models.Corp_bss
import scala.collection.mutable.ListBuffer

object Application extends Controller {

  def index = Action{
    Ok(views.html.index())
  }

  def nri = Action {
    val global_ids: List[String] = Corp_bss.globalIdsBySplrRegion("JPN", 1).map(p => p.global_id)
    val b = Corp_acc_smry.allPeriodBySplrRegionFY2011On(global_ids)
    Ok(views.html.nri(b))
  }

  def wvb = Action {
    val countryis: List[String] = Corp_bss.countrylistBySplrId(9)

    val buffer = ListBuffer[Tuple2[String, List[Corp_acc_smry]]]()
    for (i: String <- countryis) {
      val global_ids: List[String] = Corp_bss.globalIdsBySplrRegion(i, 9).map(p => p.global_id)
      if (global_ids.size > 0) {
        val ret: List[Corp_acc_smry] = Corp_acc_smry.allPeriodBySplrRegionFY2011On(global_ids)
        val t = (i, ret)
        buffer.+=(t)
      }
    }
    Ok(views.html.wvb(buffer.toList, countryis))
  }
}
