package ch.dubernet.matsimconsole.matsiminterface

import org.scalatest.FunSuite

class CoordTest extends FunSuite {
  test( "pattern match 2D coord" ) {
    Coord( 1 , 2 ) match {
      case Coord2D( 1 , 2 ) =>
      case _ => fail()
    }
  }

  test( "pattern match 3D coord" ) {
    Coord( 1 , 2 , 3 ) match {
      case Coord3D( 1 , 2 , 3 ) =>
      case _ => fail()
    }
  }

  test( "2D coord pattern matches 3D coord" ) {
    Coord( 1 , 2 , 3 ) match {
      case Coord2D( 1 , 2 ) =>
      case _ => fail()
    }
  }
}
