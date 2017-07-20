package net.habashi.barraquito.graphql.route

import spray.json.JsValue

sealed trait GraphQlQueryFailure {
  val error: String
  val json: JsValue
}

case class InvalidJsonQuery(error: String, json: JsValue) extends GraphQlQueryFailure
case class GraphQlParseError(error: String, json: JsValue) extends GraphQlQueryFailure
