package pricing

import domain.Sku

sealed trait PricingRule {
  def calculateCost(items: List[Sku]): Int
}
case class SimplePricingRule(price: Int) extends PricingRule {
  override def calculateCost(items: List[Sku]): Int = -1
}
case class MultiBuyPricingRule(individualPrice: Int, quantity: Int, multibuyPrice: Int) extends PricingRule {
  override def calculateCost(items: List[Sku]): Int = -1
}

