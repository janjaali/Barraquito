package net.habashi.barraquito.graphql.route

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import net.habashi.barraquito.repo.CoffeeRepo
import net.habashi.barraquito.graphql.schema.CoffeeGraphQlSchema
import sangria.ast.Document
import sangria.execution.{ErrorWithResolver, Executor, QueryAnalysisError}
import sangria.marshalling.sprayJson._
import sangria.parser.QueryParser
import spray.json._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object GraphQlRoute {

  def execute(requestJson: JsValue): StandardRoute = {
    val JsObject(fields) = requestJson
    val JsString(query) = fields("query")

    val operation = fields.get("operationName") collect {
      case JsString(op) ⇒ op
    }

    val vars = fields.get("variables") match {
      case Some(obj: JsObject) ⇒ obj
      case _ ⇒ JsObject.empty
    }

    QueryParser.parse(query) match {
      case Success(queryAst) =>
        complete(executeQuery(queryAst, operation, vars))
      case Failure(error) =>
        complete(BadRequest, JsObject("error" -> JsString(error.getMessage)))
    }
  }

  private def executeQuery(query: Document, op: Option[String], vars: JsObject) =
    Executor.execute(CoffeeGraphQlSchema.schema, query, new CoffeeRepo, variables = vars, operationName = op)
      .map(OK → _)
      .recover {
        case error: QueryAnalysisError ⇒ BadRequest → error.resolveError
        case error: ErrorWithResolver ⇒ InternalServerError → error.resolveError
      }

}
