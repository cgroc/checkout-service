package pricing

import domain.Sku

//assumption for now is that the list contains only one kind of Sku
sealed trait PricingRule {
  def calculateCost(items: List[Sku]): Int
}
case class SimplePricingRule(price: Int) extends PricingRule {
  def calculateCost(items: List[Sku]): Int = items.size * price
}
case class MultiBuyPricingRule(individualPrice: Int, quantity: Int, multibuyPrice: Int) extends PricingRule {
  def calculateCost(items: List[Sku]): Int = (items.size / quantity * multibuyPrice) + (items.size % quantity * individualPrice)
}

