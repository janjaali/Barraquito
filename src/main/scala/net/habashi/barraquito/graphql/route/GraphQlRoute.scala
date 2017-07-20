package net.habashi.barraquito.graphql.route

import net.habashi.barraquito.graphql.schema.CoffeeGraphQlSchema
import net.habashi.barraquito.repo.CoffeeRepo
import sangria.execution.Executor
import sangria.marshalling.sprayJson._
import sangria.parser.QueryParser
import spray.json._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object GraphQlRoute {

  def execute(requestJson: JsValue): Either[Future[JsValue], GraphQlQueryFailure] = {
    requestJson match {
      case JsObject(fields) =>
        fields.get("query") match {
          case Some(JsString(query)) =>
            QueryParser.parse(query) match {
              case Success(parsedQueryAst) =>
                val responseJsValue = Executor.execute(CoffeeGraphQlSchema.schema, parsedQueryAst, new CoffeeRepo)
                Left(responseJsValue)
              case Failure(error) => Right(GraphQlParseError(error.getMessage, requestJson))
            }
          case _ => Right(InvalidJsonQuery("Json does not contain query tag", requestJson))
        }
      case _ => Right(InvalidJsonQuery("Json does not match Json-Object", requestJson))
    }
  }

}
