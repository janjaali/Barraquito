package net.habashi.barraquito

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer
import net.habashi.barraquito.graphql.route.GraphQlRoute
import spray.json._

object Server extends App {

  implicit val system = ActorSystem("server")
  implicit val materializer = ActorMaterializer()

  val (host,port) = ("0.0.0.0", 8080)

  val route: Route =
    (post & path("graphql")) {
      entity(as[JsValue]) { requestJson =>
        GraphQlRoute.execute(requestJson) match {
          case Left(responseJsonFuture) => complete(responseJsonFuture)
          case Right(failure) => complete(BadRequest, JsObject("error" -> JsString(failure.error), "json" -> failure.json))
        }
      }
    } ~
      get {
        getFromResource("graphiql.html")
      }

  Http().bindAndHandle(route, host, port)

}
