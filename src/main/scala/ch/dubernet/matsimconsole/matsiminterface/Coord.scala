package ch.dubernet.matsimconsole.matsiminterface

case class Coord( val x: Double, val y: Double )
case class CoordZ( override val x: Double, override val y: Double, z: Double ) extends Coord(x,y)

object Coord {
  private type MatsimCoord = org.matsim.api.core.v01.Coord
  def apply( matsimCoord: MatsimCoord ): Coord = {
    if ( matsimCoord.hasZ ) CoordZ( matsimCoord.getX , matsimCoord.getY , matsimCoord.getZ )
    else Coord( matsimCoord.getX , matsimCoord.getY )
  }
}
