package net.habashi.barraquito.model

sealed trait CupSize

object CupSize {

  case object Ristretto extends CupSize

  case object Espresso extends CupSize

  case object Lungo extends CupSize

}
