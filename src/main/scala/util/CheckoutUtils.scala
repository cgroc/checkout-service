package util

import domain.Sku
import pricing.PricingRule

object CheckoutUtils {

  def scan(items: List[Sku], newItem: Sku): List[Sku] = items ++ List(newItem)

  def calculateTotal(items: List[Sku], prices: Map[Sku, PricingRule]): Int = {
    items.groupBy(identity).values.map(skuList => prices(skuList.head).calculateCost(skuList)).sum
  }

}
