package net.habashi.barraquito.graphql.route

import org.scalatest.AsyncFunSpec
import spray.json.{JsArray, JsObject, JsString}

class GraphQlRouteTest extends AsyncFunSpec {

  describe("The GraphQL endpoint") {
    describe("executes") {
      it("returns failure for JSONs without query tag") {
        val jsonQuery = JsObject("foo" -> JsString("bar"))
        GraphQlRoute.execute(jsonQuery) match {
          case Left(_) => fail
          case Right(_) => succeed
        }
      }

      it("returns list of coffees for simple coffee-query") {
        val jsonQuery = JsObject("query" -> JsString("{coffees{name}}"))
        GraphQlRoute.execute(jsonQuery) match {
          case Left(response) =>
            val expected = JsObject("data" -> JsObject("coffees" -> JsArray(JsObject("name" -> JsString("Kazaar")))))
            response.map { actual => assert(expected == actual) }
          case Right(_) => succeed
        }
      }
    }

  }

}
