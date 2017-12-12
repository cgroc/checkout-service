import domain.Sku

trait Semigroup[A] {

  def append: (A, A) => A

}

trait Monoid[A] extends Semigroup[A] {

  def identity: A

}

sealed trait Basket {
  def +(other: Basket): Basket = Basket.append(this, other)
}
case object EmptyBasket extends Basket
case class NonEmptyBasket(list: List[Sku]) extends Basket

object Basket {
  def identity: Basket = EmptyBasket

  def append(t: Basket, o: Basket): Basket =
    (t, o) match {
      case (EmptyBasket, EmptyBasket) => EmptyBasket
      case (EmptyBasket, b @ NonEmptyBasket(l)) => b
      case (b @ NonEmptyBasket(l), EmptyBasket) => b
      case (NonEmptyBasket(l1), NonEmptyBasket(l2)) => NonEmptyBasket(l1 ++ l2)
    }
}

val basket1 = EmptyBasket
val basket2 = NonEmptyBasket(List(Sku("apple")))
val basket3 = NonEmptyBasket(List(Sku("banana")))

List(basket1, basket2).foldLeft(Basket.identity)(_ + _)
List(basket1, basket2).foldRight(Basket.identity)(_ + _)

List(basket1, basket3).foldLeft(Basket.identity)(_ + _)
List(basket1, basket3).foldRight(Basket.identity)(_ + _)

List(basket2, basket3).foldLeft(Basket.identity)(_ + _)
List(basket2, basket3).foldRight(Basket.identity)(_ + _)

val someMap: Map[Int, String] = Map(1 -> "one")

someMap + (2 -> "two")

// the original mapping is lost!
someMap + (1 -> "three")

val optionIntString = Some((1, "one"))
val optionInt = optionIntString.map(_._1)
val optionString = optionIntString.map(_._2)
val unzippedOptions = (optionIntString.map(_._1), optionIntString.map(_._2))

List(1,2,3).map(identity)


def myMap[A](list: List[Int], f: Int => A): List[A] = {
  list match {
    case head :: tail => List(f(head)) ++ myMap(tail, f)
    case Nil => Nil
  }
}

def myTotallyGenericMap[A, B](list: List[A], f: A => B): List[B] = {
  list match {
    case head :: tail => List(f(head)) ++ myTotallyGenericMap(tail, f)
    case Nil => Nil
  }
}

myMap(List(1, 2, 3, 4), x => x * 2)

myTotallyGenericMap[Int, Int](List(1, 2, 3, 4), x => x * 2)

myTotallyGenericMap[String, Int](List("one", "two", "three", "four"), str => str.length)

myTotallyGenericMap[String, String](List("one", "two", "three", "four"), str => str + "!")
