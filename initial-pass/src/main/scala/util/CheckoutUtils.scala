package util

import domain.Sku
import pricing.PricingRule

object CheckoutUtils {

  def scan(items: List[Sku], newItem: Sku, validItems: List[Sku]): ScanResult =
    if (validItems.contains(newItem)) {
      ScanSuccess(items :+ newItem)
    } else {
      ScanFailure(items)
    }

  def calculateTotal(items: List[Sku], prices: Map[Sku, PricingRule]): Int =
    items.groupBy(identity).values.map(skuList => prices(skuList.head).calculateCost(skuList)).sum

}

sealed trait ScanResult
case class ScanSuccess(items: List[Sku]) extends ScanResult
case class ScanFailure(items: List[Sku]) extends ScanResult
