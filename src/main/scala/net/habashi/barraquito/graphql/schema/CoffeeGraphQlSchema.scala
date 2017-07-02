package net.habashi.barraquito.graphql.schema

import net.habashi.barraquito.model.Coffee
import net.habashi.barraquito.repo.CoffeeRepo
import sangria.schema._

object CoffeeGraphQlSchema {

  val CoffeeType = ObjectType(
    "Coffee",
    fields[Unit, Coffee](
      Field("name", StringType, resolve = _.value.name),
      Field("intensity", IntType, resolve = _.value.intensity)
    )
  )

  val coffeeQueryType = ObjectType(
    name = "Query",
    fields[CoffeeRepo, Unit](
      Field("coffees", ListType(CoffeeType), resolve = _.ctx.coffees)
    )
  )

  val schema = Schema(coffeeQueryType)

}
