package net.habashi.barraquito.model

case class Coffee(name: String, intensity: Int, cupSize: List[CupSize], aromaProfile: AromaProfile, price: Int)