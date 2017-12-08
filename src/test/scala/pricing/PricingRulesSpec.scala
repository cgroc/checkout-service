package pricing

import org.scalatest.{Matchers, WordSpec}

class PricingRulesSpec extends WordSpec with Matchers {
  "A simple pricing rule" when {
    "calculating the cost of an empty list" should {
      "return 0" in {
        val pricingRule: PricingRule = SimplePricingRule(100)
        pricingRule.calculateCost(List()) shouldEqual 0
      }
    }
  }
}
