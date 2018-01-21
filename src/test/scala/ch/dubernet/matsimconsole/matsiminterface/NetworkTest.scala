package ch.dubernet.matsimconsole.matsiminterface

import org.junit.runner.RunWith
import org.matsim.api.core.v01.Id
import org.matsim.core.config.ConfigUtils
import org.matsim.core.scenario.ScenarioUtils
import org.matsim.api.core.v01.{network => matsimnetwork}
import org.matsim.api.core.v01.{Coord => MatsimCoord}
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import scala.collection.JavaConversions._

@RunWith( classOf[JUnitRunner] )
class NetworkTest extends FunSuite {
  private object TestNetwork {
    def apply(): org.matsim.api.core.v01.network.Network = {
      val matsimNet: matsimnetwork.Network =
        ScenarioUtils.createMutableScenario(
          ConfigUtils.createConfig() ).getNetwork

      // complete network with 50 nodes
      for ( node <- (1 to 50)
                    map {id => matsimNet.getFactory.createNode(
                          Id.create( id , classOf[matsimnetwork.Node]),
                          new MatsimCoord(id,id) )} ) {
        matsimNet.addNode( node )
      }

      for { n1 <- matsimNet.getNodes.values
            n2 <- matsimNet.getNodes.values filter {_ != n1} } {
        val id = Id.createLinkId( n1.getId + "-" + n2.getId )
        val link = matsimNet.getFactory.createLink( id , n1 , n2 )
        matsimNet.addLink( link )
      }

      matsimNet
    }
  }

  test( "MatsimNetwork conversion size" ) {
    val matsimNet = TestNetwork()

    val network = Network( matsimNet )

    assert( matsimNet.getNodes.size() === network.nodes.size )
    assert( matsimNet.getLinks.size() === network.links.size )
  }

  test( "MatsimNetwork conversion unique links" ) {
    val matsimNet = TestNetwork()

    val network = Network( matsimNet )

    for { node <- network.nodes.values
          link <- node.inLinks ++ node.outLinks } {
      network.links.get( link.id ) match {
        case Some( l ) => assert( l === link )
        case None => fail( "no link with id "+link.id )
      }
    }
  }
}
