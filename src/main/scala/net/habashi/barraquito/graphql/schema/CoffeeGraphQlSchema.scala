package net.habashi.barraquito.graphql.schema

import net.habashi.barraquito.model.{AromaProfile, Coffee, CupSize}
import net.habashi.barraquito.repo.CoffeeRepo
import sangria.schema._

object CoffeeGraphQlSchema {

  val CupSizeType = EnumType(
    "CupSize",
    values = List(
      EnumValue(CupSize.Ristretto.toString, value = CupSize.Ristretto),
      EnumValue(CupSize.Espresso.toString, value = CupSize.Espresso),
      EnumValue(CupSize.Lungo.toString, value = CupSize.Lungo)
    )
  )

  val AromaProfileType = EnumType(
    "AromaProfile",
    values = List(
      EnumValue(AromaProfile.Balanced.toString, value = AromaProfile.Balanced),
      EnumValue(AromaProfile.Intense.toString, value = AromaProfile.Intense),
      EnumValue(AromaProfile.Fruity.toString, value = AromaProfile.Fruity)
    )
  )

  val CoffeeType = ObjectType(
    "Coffee",
    fields[Unit, Coffee](
      Field("name", StringType, resolve = _.value.name),
      Field("intensity", IntType, resolve = _.value.intensity),
      Field("cupSize", ListType(CupSizeType), resolve = _.value.cupSize),
      Field("aromaProfile", AromaProfileType, resolve = _.value.aromaProfile)
    )
  )

  val CoffeeQueryType = ObjectType(
    name = "Query",
    fields[CoffeeRepo, Unit](
      Field("coffees", ListType(CoffeeType), resolve = _.ctx.coffees)
    )
  )

  val schema = Schema(CoffeeQueryType)

}
