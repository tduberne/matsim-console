package ch.dubernet.matsimconsole.matsiminterface

trait HasTime[A] {
  def time(a: A): Double
}

object HasTime {
  def time[A: HasTime](a: A): Double = implicitly[HasTime[A]].time( a )

  implicit class HasTimeOps[A: HasTime](a: A) {
    def time: Double = implicitly[HasTime[A]].time( a )
  }
}
