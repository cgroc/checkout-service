//import scala.language.implicitConversions


// Typeclass
trait ToCsv[A] {
  def toCsv(a: A): Csv
}
object ToCsv {
  def create[A](f: A => Csv): ToCsv[A] =
    new ToCsv[A] {
      override def toCsv(a: A) =
        f(a)
    }
}

case class Foo(id: String, count: Int)
object Foo {
  // Typeclass implementation for Foo
  implicit val toCsv: ToCsv[Foo] = {
    ToCsv.create {
      (a: Foo) => Csv(a.id + a.count)
    }
  }
}

case class Csv(s: String)

def writeCsv[A](a: A)(implicit toCsv: ToCsv[A]): Csv = {
  toCsv.toCsv(a)
}

writeCsv(Foo("hello", 123))

case class Bar(isThing: Boolean)
object Bar {
  // putting implicit in scope of companion object means it's easy for users to override
  implicit val toCsv: ToCsv[Bar] = {
    ToCsv.create {
      (a: Bar) => Csv(a.isThing.toString)
    }
  }
}

// see? easy!
implicit val toCsv: ToCsv[Bar] = {
  ToCsv.create {
    (a: Bar) => if (a.isThing) Csv("Affirmative") else Csv("Negatory")
  }
}

writeCsv(Bar(true))
writeCsv(Bar(true))(Bar.toCsv)
writeCsv(Bar(false))
writeCsv(Bar(false))(Bar.toCsv)

implicit def toOption[A](a: A): Option[A] = Option(a)

val a: Option[String] = "hi"

