package util

import domain.Sku
import util.CheckoutUtils._
import org.scalatest.{Matchers, WordSpec}
import pricing.{MultiBuyPricingRule, SimplePricingRule}

import scala.util.{Success, Failure}

class CheckoutUtilsSpec extends WordSpec with Matchers {

  "Scanning a new Item" when {
    "the item scanned is valid" should {
      "result in a Success containing a list with the new item appended" in {
        val items = List(Sku("apple"), Sku("banana"))
        val newItem = Sku("carrot")
        val validItems = List(Sku("apple"), Sku("banana"), Sku("carrot"))
        scan(items, newItem, validItems) shouldEqual Success(List(Sku("apple"), Sku("banana"), Sku("carrot")))
      }
    }

    "the item scanned is invalid" should {
      "fail" in {
        val items = List(Sku("apple"), Sku("banana"))
        val newItem = Sku("carrot")
        val validItems = List(Sku("apple"), Sku("banana"))
        scan(items, newItem, validItems).isFailure shouldEqual true
      }
    }
  }

  "Calculating the cost of a list of items" should {
    "return 0 for an empty list" in {
      val items = List()
      val prices = Map(Sku("apples") -> SimplePricingRule(100))
      calculateTotal(items, prices) shouldEqual 0
    }

    "return the correct value for a list with a single item and simple pricing" in {
      val apple = Sku("apple")
      val simpleApplePrice = SimplePricingRule(100)
      val items = List(apple)
      val prices = Map(apple -> simpleApplePrice)
      calculateTotal(items, prices) shouldEqual 100
    }

    "return the correct value for a list with several identical items and simple pricing" in {
      val apple = Sku("apple")
      val simpleApplePrice = SimplePricingRule(100)
      val items = List(apple, apple, apple)
      val prices = Map(apple -> simpleApplePrice)
      calculateTotal(items, prices) shouldEqual 300
    }

    "return the correct value for a list with several distinct items with simple pricing" in {
      val apple = Sku("apple")
      val simpleApplePrice = SimplePricingRule(100)

      val banana = Sku("banana")
      val simpleBananaPricingRule = SimplePricingRule(50)

      val items = List(apple, apple, banana, apple)
      val prices = Map(apple -> simpleApplePrice, banana -> simpleBananaPricingRule)
      calculateTotal(items, prices) shouldEqual 350
    }

    "return the correct pricing for a list with several identical items with multibuy pricing" in {
      val carrot = Sku("carrot")
      val multibuyCarrotPrice = MultiBuyPricingRule(individualPrice = 25, quantity = 4, multibuyPrice = 75)

      val items = List(carrot, carrot, carrot, carrot)
      val prices = Map(carrot -> multibuyCarrotPrice)

      calculateTotal(items, prices) shouldEqual 75
    }

    "return the correct pricing for a list with multiple different items with different pricing rules" in {
      val apple = Sku("apple")
      val simpleApplePrice = SimplePricingRule(100)

      val banana = Sku("banana")
      val simpleBananaPricingRule = SimplePricingRule(50)

      val carrot = Sku("carrot")
      val multibuyCarrotPrice = MultiBuyPricingRule(individualPrice = 25, quantity = 4, multibuyPrice = 75)

      val items = List(apple, carrot, apple, banana, apple, carrot, carrot, carrot)
      val prices = Map(apple -> simpleApplePrice, banana -> simpleBananaPricingRule, carrot -> multibuyCarrotPrice)

      calculateTotal(items, prices) shouldEqual 425
    }
  }

}
