package ch.dubernet.matsimconsole.processing

import java.awt.event.MouseEvent

import ch.dubernet.matsimconsole.matsiminterface.{Link, Network}
import org.matsim.api.core.v01.Id
import processing.core.PApplet

class MatsimVizApplet extends PApplet {
  var network: Option[Network] = None

  var zoom: Float = 1
  override def mouseWheel(event: MouseEvent) = {
    zoom = Math.max( 0 , zoom - (event.getCount / 10f ) )
  }

  override def draw() = {
    scale( zoom )
    background( 255 , 255 , 255 );

    for {n <- network
         (_, l) <- n.links } drawLink( l )
  }

  def drawLink(l: Link) = {
    line( l.start.coord.x.asInstanceOf[Float] ,
      l.start.coord.y.asInstanceOf[Float] ,
      l.end.coord.x.asInstanceOf[Float],
      l.end.coord.y.asInstanceOf[Float] )
  }

  override def setup() = {
    size( 400 , 400 );
    stroke( 0 );
  }
}
