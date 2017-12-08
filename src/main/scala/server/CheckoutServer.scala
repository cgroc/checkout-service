package server

import fs2.Task
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.util.StreamApp
import service.CheckoutSessionService

import scala.util.Properties.envOrNone

object CheckoutServer  extends StreamApp {
  val port: Int = envOrNone("HTTP_PORT").fold(8080)(_.toInt)

  def stream(args: List[String]): fs2.Stream[Task, Nothing] =
    BlazeBuilder
      .bindHttp(port)
      .mountService(CheckoutSessionService.service, "/checkoutSession/")
      .serve
}
