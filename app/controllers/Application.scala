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

  def index = Action {
    Ok(views.html.index())
  }

  def nri = Action {
    val countryis: List[String] = Corp_bss.countrylistBySplrId(Nri.splr_id)

    val buffer = ListBuffer[Tuple2[String, List[Corp_acc_smry]]]()
    for (i: String <- countryis) {
      val global_ids: List[String] = Corp_bss.globalIdsBySplrRegion(i, Nri.splr_id).map(p => p.global_id)
      if (global_ids.size > 0) {
        val ret: List[Corp_acc_smry] = Corp_acc_smry.allPeriodBySplrRegionFY2011On(global_ids)
        val t = (i, ret)
        buffer.+=(t)
      }
    }
    Ok(views.html.singleCoverage(buffer.toList, countryis, Nri.supplierName))

  }

  def wvb = Action {
    val countryis: List[String] = Corp_bss.countrylistBySplrId(Wvb.splr_id)

    val buffer = ListBuffer[Tuple2[String, List[Corp_acc_smry]]]()
    for (i: String <- countryis) {
      val global_ids: List[String] = Corp_bss.globalIdsBySplrRegion(i, Wvb.splr_id).map(p => p.global_id)
      if (global_ids.size > 0) {
        val ret: List[Corp_acc_smry] = Corp_acc_smry.allPeriodBySplrRegionFY2011On(global_ids)
        val t = (i, ret)
        buffer.+=(t)
      }
    }
    Ok(views.html.multiCoverage(buffer.toList, countryis, Wvb.supplierName))
  }

  def nice = Action {
    val countryis: List[String] = Corp_bss.countrylistBySplrId(Nice.splr_id)

    val buffer = ListBuffer[Tuple2[String, List[Corp_acc_smry]]]()
    for (i: String <- countryis) {
      val global_ids: List[String] = Corp_bss.globalIdsBySplrRegion(i, Nice.splr_id).map(p => p.global_id)
      if (global_ids.size > 0) {
        val ret: List[Corp_acc_smry] = Corp_acc_smry.allPeriodBySplrRegionFY2011On(global_ids)
        val t = (i, ret)
        buffer.+=(t)
      }
    }
    Ok(views.html.singleCoverage(buffer.toList, countryis, Nice.supplierName))
  }
  def tej = Action {
    val countryis: List[String] = Corp_bss.countrylistBySplrId(Tej.splr_id)

    val buffer = ListBuffer[Tuple2[String, List[Corp_acc_smry]]]()
    for (i: String <- countryis) {
      val global_ids: List[String] = Corp_bss.globalIdsBySplrRegion(i, Tej.splr_id).map(p => p.global_id)
      if (global_ids.size > 0) {
        val ret: List[Corp_acc_smry] = Corp_acc_smry.allPeriodBySplrRegionFY2011On(global_ids)
        val t = (i, ret)
        buffer.+=(t)
      }
    }
    Ok(views.html.multiCoverage(buffer.toList, countryis, Tej.supplierName))
  }
}

trait supplier {
  val supplierName: String
  val splr_id: Integer

}

case object Nice extends supplier {
  val supplierName: String = "NICE"
  val splr_id: Integer = 3
}

case object Nri extends supplier {
  val supplierName: String = "NRI"
  val splr_id: Integer = 1
}

case object Wvb extends supplier {
  val supplierName: String = "Wvb"
  val splr_id: Integer = 9
}

case object Tej extends supplier {
  val supplierName: String = "Tej"
  val splr_id: Integer = 4
}