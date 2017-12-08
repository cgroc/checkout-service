package service

import org.http4s.HttpService
import org.http4s.dsl._

object CheckoutSessionService {
  val service = HttpService {
    case GET -> Root / "new" => Ok("new_id")
    case GET -> Root / "items" => Ok("items")
    case POST -> Root / "scan" => Ok("items")

  }
}
