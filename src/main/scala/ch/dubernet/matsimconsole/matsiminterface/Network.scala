package ch.dubernet.matsimconsole.matsiminterface

import org.matsim.api.core.v01.Id

class Network( links: Map[Id[Link],Link], nodes: Map[Id[Node],Node] ) {

}

case class Coord( x: Double, y: Double, z: Double )
case class Link( id: Id[Link], start: Node, end: Node )
case class Node( id: Id[Node], coord: Coord, inLinks: List[Link], outLinks: List[Link] )

object Coord {
  private type MatsimCoord = org.matsim.api.core.v01.Coord
  def apply( matsimCoord: MatsimCoord ): Coord =
    Coord( matsimCoord.getX , matsimCoord.getY , matsimCoord.getY )
}
object Network {
  private type MatsimNetwork = org.matsim.api.core.v01.network.Network
  private type MatsimNode = org.matsim.api.core.v01.network.Node
  private type MatsimLink = org.matsim.api.core.v01.network.Link

  private def fromMatsimNetwork( network: MatsimNetwork ): Network = {
    // I am sure there is a nicer solution using lazy data structures...
    def createNode( matsimNode: MatsimNode , knownNodes: Map[Id[Node],Node] = Map()): Node = {
      val id: Id[Node] = Id.create( matsimNode.getId , classOf[Node] )
      val coord = matsimNode.getCoord

      ???
    }

    def createLink( matsimLink: MatsimLink , knownNodes: Map[Id[Node],Node] ): Link = {
      val id: Id[Link] = Id.create( matsimLink.getId , classOf[Link] )
      val idStart: Id[Node] = Id.create( matsimLink.getFromNode.getId , classOf[Node] )
      val idEnd: Id[Node] = Id.create( matsimLink.getToNode.getId , classOf[Node] )
      val start = knownNodes.getOrElse( idStart , createNode( matsimLink.getFromNode , knownNodes ) )
      val end = knownNodes.
        getOrElse(
          idStart,
          createNode(
            matsimLink.getFromNode,
            knownNodes
                .updated( idStart , start )) )
      Link( id , start , end )
    }
  }
}
