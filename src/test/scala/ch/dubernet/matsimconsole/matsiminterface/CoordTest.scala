package ch.dubernet.matsimconsole.matsiminterface

import org.scalatest.FunSuite

class CoordTest extends FunSuite {
  test( "pattern match coord" ) {
    new Coord( 1 , 2 ) match {
      case Coord( x , y ) => {}
      case _ => fail()
    }
  }

  test( "pattern match coordZ" ) {
    new CoordZ( 1 , 2 , 3 ) match {
      case CoordZ( x , y , z ) => {}
      case _ => fail()
    }
  }

  test( "coord pattern matches coordZ" ) {
    new CoordZ( 1 , 2 , 3 ) match {
      case Coord( x , y ) => {}
      case _ => fail()
    }
  }
}
