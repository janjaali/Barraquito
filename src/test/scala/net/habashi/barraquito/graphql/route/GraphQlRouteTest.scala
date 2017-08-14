package net.habashi.barraquito.graphql.route

import org.scalatest.AsyncWordSpec
import spray.json.{JsArray, JsObject, JsString}

class GraphQlRouteTest extends AsyncWordSpec {

  "The GraphQL endpoint" should {
    """return failure for JSONs without the "query" tag""" in {
      val jsonQuery = JsObject("foo" -> JsString("bar"))
      GraphQlRoute.execute(jsonQuery) match {
        case Left(_) => fail
        case Right(_) => succeed
      }
    }

    "return list of coffees for simple coffee-query" in {
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
