package ch.dubernet.matsimconsole.matsiminterface

class Coord( val x: Double, val y: Double , val z: Option[Double] )

object Coord {
  private type MatsimCoord = org.matsim.api.core.v01.Coord

  def apply( x: Double, y: Double ) = new Coord( x , y , None )
  def apply( x: Double, y: Double , z: Double ) = new Coord( x , y , Some( z ) )

  def apply( matsimCoord: MatsimCoord ): Coord = {
    if ( matsimCoord.hasZ ) Coord( matsimCoord.getX , matsimCoord.getY , matsimCoord.getZ )
    else Coord( matsimCoord.getX , matsimCoord.getY )
  }

  def unapplySeq( coord: Coord ): Option[Seq[Double]] = coord.z match {
    case Some( z ) => Some( List( coord.x , coord.y , z ) )
    case None => Some( List( coord.x , coord.y ) )
  }
}
