package ch.dubernet.matsimconsole.matsiminterface

case class Coord( x: Double, y: Double , z: Option[Double] )

object Coord {
  private type MatsimCoord = org.matsim.api.core.v01.Coord

  def apply( x: Double, y: Double ) = new Coord( x , y , None )
  def apply( x: Double, y: Double , z: Double ) = new Coord( x , y , Some( z ) )

  def apply( matsimCoord: MatsimCoord ): Coord = {
    if ( matsimCoord.hasZ ) Coord( matsimCoord.getX , matsimCoord.getY , matsimCoord.getZ )
    else Coord( matsimCoord.getX , matsimCoord.getY )
  }
}

object Coord2D {
  def unapply( coord: Coord ): Option[(Double,Double)] = Some( (coord.x, coord.y) )
}

object Coord3D {
  def unapply( coord: Coord ): Option[(Double,Double,Double)] = coord.z match {
    case Some( z ) => Some( ( coord.x, coord.y, z ) )
    case None => None
  }
}
