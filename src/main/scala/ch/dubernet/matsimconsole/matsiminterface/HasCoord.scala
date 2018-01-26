package ch.dubernet.matsimconsole.matsiminterface

/**
  * Type class to allow dynamically identifying classes that can provide a coordinate.
  * Might be a bit of overengineering here, but hey, this project is also there to experiment.
  * The idea is that the visualizer should operate on object that are members of general type classes
  * (HasCoord, HasTime...), such that one should be able to easily visualize streams of anything,
  * including things I did not think about.
  */
trait HasCoord[A] {
  def coord(a: A): Coord
}

object HasCoord {
  def coord[A](a: A)(implicit hc: HasCoord[A]): Coord = hc.coord( a )

  implicit class HasCoordOps[A: HasCoord](a: A) {
    def coord: Coord = implicitly[HasCoord[A]].coord( a )
  }

  implicit val coordHasCoord: HasCoord[Coord] = (c: Coord) => c
}

