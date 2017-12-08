package service

import domain.Sku
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.HttpService
import org.http4s.circe._
import org.http4s.dsl._
import pricing.{MultiBuyPricingRule, SimplePricingRule}
import util.CheckoutUtils._
import util.{ScanFailure, ScanSuccess}

object CheckoutSessionService {

  var skuList: List[Sku] = List()
  val pricingRules = Map(
    Sku("apple") -> SimplePricingRule(100),
    Sku("banana") -> SimplePricingRule(120),
    Sku("carrot") -> MultiBuyPricingRule(individualPrice = 25, quantity = 4, multibuyPrice = 75)
  )

  val service = HttpService {
    //    case GET -> Root / "new" => Ok("new_id")
    //    case GET -> Root / "items" => Ok("items")
    case request@POST -> Root / "scan" =>
      val scannedItem: Sku = request.as(jsonOf[Sku]).unsafeRun //TODO handle failure to deserialise
      scan(skuList, scannedItem, pricingRules.keySet.toList) match {
        case ScanSuccess(updatedList) =>
          skuList = updatedList
          Ok(updatedList.asJson)
        case ScanFailure(items) =>
          BadRequest(items.asJson)
      }

    case GET -> Root / "total" => Ok(calculateTotal(skuList, pricingRules).toString)
  }
}
