package ch.dubernet.matsimconsole.matsiminterface

import org.matsim.api.core.v01.{Id, Scenario}
import org.matsim.core.config.ConfigUtils
import org.matsim.core.network.io.MatsimNetworkReader
import org.matsim.core.scenario.ScenarioUtils

import scala.collection.mutable
import scala.collection.JavaConversions._


class Network( val links: Map[Id[Link],Link], val nodes: Map[Id[Node],Node] ) {

}


class Link( val id: Id[Link], startByName: => Node, endByName: => Node ) {
  // gets evaluated once, the first time it is accessed
  lazy val start = startByName
  lazy val end = endByName
}
class Node( val id: Id[Node], val coord: Coord, inLinksByName: => List[Link], outLinksByName: => List[Link] ) {
  // gets evaluated once, the first time it is accessed
  lazy val inLinks = inLinksByName
  lazy val outLinks = outLinksByName
}



object Network {
  private type MatsimNetwork = org.matsim.api.core.v01.network.Network
  private type MatsimNode = org.matsim.api.core.v01.network.Node
  private type MatsimLink = org.matsim.api.core.v01.network.Link

  // I am sure there is a nicer solution using lazy data structures and monads instead of a mutable map...
  private class Converter {
    val knownNodes: mutable.Map[Id[Node],Node] = mutable.HashMap()
    val knownLinks: mutable.Map[Id[Link],Link] = mutable.HashMap()

    def apply( node: MatsimNode ): Node = {
      val id: Id[Node] = Id.create( node.getId , classOf[Node] )

      knownNodes.getOrElseUpdate( id,
          new Node(
            id ,
            Coord( node.getCoord ),
            ( node.getInLinks.values map {this( _ ) } ).toList,
            ( node.getOutLinks.values map {this( _ ) } ).toList ) )
    }

    def apply( link: MatsimLink ): Link = {
      val id: Id[Link] = Id.create( link.getId , classOf[Link] )

      knownLinks.getOrElseUpdate( id ,
          new Link(
            id ,
            this( link.getFromNode ),
            this( link.getToNode ) ) )
    }
  }

  def apply( network: MatsimNetwork ): Network = {
    val convert = new Converter

    new Network(
      network.getLinks.values map { convert( _ ) } map { l => (l.id, l) } toMap,
      network.getNodes.values map { convert( _ ) } map { n => (n.id, n) } toMap )
  }

  def apply( file: String ): Network = {
    val sc: Scenario = ScenarioUtils.createScenario( ConfigUtils.createConfig() );
    new MatsimNetworkReader( sc.getNetwork ).readFile( file );
    Network( sc.getNetwork )
  }
}
