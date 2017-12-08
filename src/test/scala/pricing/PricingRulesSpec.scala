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
      "calculating the cost of a list with one item" should {
        "return the correct value" in {
          val pricingRule: PricingRule = SimplePricingRule(100)
          pricingRule.calculateCost(List(Sku("oven-chips"))) shouldBe 100
        }
      }
    }
  }
}
