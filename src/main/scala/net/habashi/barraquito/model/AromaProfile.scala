package net.habashi.barraquito.model

sealed trait AromaProfile

object AromaProfile {

  case object Balanced extends AromaProfile

  case object Fruity extends AromaProfile

  case object Intense extends AromaProfile

}
