package net.habashi.barraquito

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer
import net.habashi.barraquito.graphql.route.GraphQlRoute
import spray.json._

object Server extends App {

  implicit val system = ActorSystem("server")
  implicit val materializer = ActorMaterializer()

  val route: Route =
    (post & path("graphql")) {
      entity(as[JsValue]) { requestJson =>
        GraphQlRoute.execute(requestJson)
      }
    } ~
      get {
        getFromResource("graphiql.html")
      }

  Http().bindAndHandle(route, "0.0.0.0", 8080)

}
