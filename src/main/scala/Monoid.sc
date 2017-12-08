
trait Semigroup[A] {

  def append: (A, A) => A

}

trait Monoid[A] extends Semigroup[A] {

  def identity: A

}

List(1,2,3).foldLeft(0)((a, b) => a + b)

sealed trait MyResults {
  def +(other: MyResults): MyResults =
    MyResults.append(this, other)
}
case object AllsWell extends MyResults
case class ArghErrors(errors: List[String]) extends MyResults

object MyResults {
  def identity: MyResults = AllsWell

  def append(a: MyResults, b: MyResults): MyResults =
    (a, b) match {
      case (AllsWell, AllsWell) =>
        AllsWell

      case (AllsWell, e @ ArghErrors(_)) =>
        e

      case (e @ ArghErrors(_), AllsWell) =>
        e

      case (ArghErrors(e1), ArghErrors(e2)) =>
        ArghErrors(e1 ++ e2)
    }
}

val l: List[MyResults] = List(AllsWell, AllsWell, ArghErrors(List("a")), AllsWell, ArghErrors(List("b")))

l.foldLeft(MyResults.identity)(_ + _)
