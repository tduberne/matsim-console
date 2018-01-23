package ch.dubernet.matsimconsole.matsiminterface

import org.scalatest.FunSuite

class CoordTest extends FunSuite {
  test( "pattern match coord" ) {
    Coord( 1 , 2 ) match {
      case Coord2D( 1 , 2 ) =>
      case _ => fail()
    }
  }

  test( "pattern match coordZ" ) {
    Coord( 1 , 2 , 3 ) match {
      case Coord3D( 1 , 2 , 3 ) =>
      case _ => fail()
    }
  }

  test( "coord pattern matches coordZ" ) {
    Coord( 1 , 2 , 3 ) match {
      case Coord2D( 1 , 2 ) =>
      case _ => fail()
    }
  }
}
