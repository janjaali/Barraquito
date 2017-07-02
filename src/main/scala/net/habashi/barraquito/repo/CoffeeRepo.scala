package net.habashi.barraquito.repo

import net.habashi.barraquito.model.AromaProfile.Intense
import net.habashi.barraquito.model.Coffee
import net.habashi.barraquito.model.CupSize.{Espresso, Ristretto}

class CoffeeRepo {

  private val _coffees = List(
    Coffee("Kazaar", 12, List(Ristretto, Espresso), Intense, 57)
  )

  def coffees: List[Coffee] = _coffees

}
