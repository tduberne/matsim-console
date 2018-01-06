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

  private def fromMatsimNetwork( network: MatsimNetwork ): Network = {
    // I am sure there is a nicer solution using lazy data structures and monads instead of a mutable map...
    def createNode( matsimNode: MatsimNode , knownNodes: mutable.Map[Id[Node],Node] = mutable.HashMap()): Node = {
      val id: Id[Node] = Id.create( matsimNode.getId , classOf[Node] )

      knownNodes.get( id ) match {
        case Some( n ) => n
        case None =>
          Node(
            id ,
            Coord( matsimNode.getCoord ),
            ( matsimNode.getInLinks.values map {createLink( _ , knownNodes ) } ).toList,
            ( matsimNode.getOutLinks.values map {createLink( _ , knownNodes ) } ).toList )
      }
    }

    def createLink( matsimLink: MatsimLink , knownNodes: mutable.Map[Id[Node],Node] ): Link = {
      val id: Id[Link] = Id.create( matsimLink.getId , classOf[Link] )
      val idStart: Id[Node] = Id.create( matsimLink.getFromNode.getId , classOf[Node] )
      val idEnd: Id[Node] = Id.create( matsimLink.getToNode.getId , classOf[Node] )
      val start = knownNodes.getOrElse( idStart , createNode( matsimLink.getFromNode , knownNodes ) )
      val end = knownNodes.
        getOrElse(
          idEnd,
          createNode(
            matsimLink.getToNode,
            knownNodes
                .updated( idStart , start )) )
      Link( id , start , end )
    }

    ???
  }
}
