package pricing

import domain.Sku
import org.scalatest.{Matchers, WordSpec}

class PricingRulesSpec extends WordSpec with Matchers {
  "A simple pricing rule" when {
    "calculating the cost of an empty list" should {
      "return 0" in {
        val pricingRule: PricingRule = SimplePricingRule(100)
        pricingRule.calculateCost(List()) shouldEqual 0
      }
    }
    "calculating the cost of a list with one item" should {
      "return the correct value" in {
        val pricingRule: PricingRule = SimplePricingRule(100)
        pricingRule.calculateCost(List(Sku("oven-chips"))) shouldEqual 100
      }
    }
    "calculating the cost of a list with several items" should {
      "return the correct value" in {
        val pricingRule: PricingRule = SimplePricingRule(100)
        pricingRule.calculateCost(List(Sku("oven-chips"), Sku("oven-chips"), Sku("oven-chips"))) shouldEqual 300
      }
    }
  }


  "A multibuy pricing rule" when {
    "calculating the cost of an empty list" should {
      "return 0" in {
        val pricingRule: PricingRule = MultiBuyPricingRule(individualPrice = 100, quantity = 3, multibuyPrice = 200)
        pricingRule.calculateCost(List()) shouldEqual 0
      }
    }

    "calculating the cost of a single item" should {
      "return the correct value" in {
        val pricingRule: PricingRule = MultiBuyPricingRule(individualPrice = 100, quantity = 3, multibuyPrice = 200)
        pricingRule.calculateCost(List(Sku("frozen-pizza"))) shouldEqual 100
      }
    }

    "calculating the cost of multiple items which don't qualify for a discount" should {
      "return the correct value" in {
        val pricingRule: PricingRule = MultiBuyPricingRule(individualPrice = 100, quantity = 3, multibuyPrice = 200)
        pricingRule.calculateCost(List(Sku("frozen-pizza"), Sku("frozen-pizza"))) shouldEqual 200
      }
    }

    "calculating the cost of items which qualify exactly for a multibuy discount" should {
      "return the correct value" in {
        val pricingRule: PricingRule = MultiBuyPricingRule(individualPrice = 100, quantity = 3, multibuyPrice = 200)
        pricingRule.calculateCost(List(Sku("frozen-pizza"), Sku("frozen-pizza"), Sku("frozen-pizza"))) shouldEqual 200
      }
    }

    "calculating the cost of multiple items greater than the multibuy quantuty" should {
      "return the correct value" in {
        val pricingRule: PricingRule = MultiBuyPricingRule(individualPrice = 100, quantity = 3, multibuyPrice = 200)
        pricingRule.calculateCost(List(Sku("frozen-pizza"), Sku("frozen-pizza"), Sku("frozen-pizza"), Sku("frozen-pizza"))) shouldEqual 300
      }
    }

    "calculating the cost of two multibuys worth of items" should {
      "return the correct value" in {
        val pricingRule: PricingRule = MultiBuyPricingRule(individualPrice = 100, quantity = 3, multibuyPrice = 200)
        pricingRule.calculateCost(List(Sku("frozen-pizza"), Sku("frozen-pizza"), Sku("frozen-pizza"), Sku("frozen-pizza"), Sku("frozen-pizza"), Sku("frozen-pizza"))) shouldEqual 400
      }
    }
  }

}
