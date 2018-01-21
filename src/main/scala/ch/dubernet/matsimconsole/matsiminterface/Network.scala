package ch.dubernet.matsimconsole.matsiminterface

import org.matsim.api.core.v01.Id

import scala.collection.mutable
import scala.collection.JavaConversions._


class Network( val links: Map[Id[Link],Link], val nodes: Map[Id[Node],Node] ) {

}

case class Coord( val x: Double, val y: Double, val z: Double )
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

object Coord {
  private type MatsimCoord = org.matsim.api.core.v01.Coord
  def apply( matsimCoord: MatsimCoord ): Coord =
    Coord( matsimCoord.getX , matsimCoord.getY , matsimCoord.getZ )
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

      knownNodes.get( id ) match {
        case Some( n ) => n
        case None =>
          new Node(
            id ,
            Coord( node.getCoord ),
            ( node.getInLinks.values map {this( _ ) } ).toList,
            ( node.getOutLinks.values map {this( _ ) } ).toList )
      }
    }

    def apply( link: MatsimLink ): Link = {
      val id: Id[Link] = Id.create( link.getId , classOf[Link] )

      knownLinks.get( id ) match {
        case Some( n ) => n
        case None =>
          new Link(
            id ,
            this( link.getFromNode ),
            this( link.getToNode ) )
      }
    }
  }

  def apply( network: MatsimNetwork ): Network = {
    val convert = new Converter

    new Network(
      network.getLinks.values map { convert( _ ) } map { l => (l.id, l) } toMap,
      network.getNodes.values map { convert( _ ) } map { n => (n.id, n) } toMap )
  }
}
