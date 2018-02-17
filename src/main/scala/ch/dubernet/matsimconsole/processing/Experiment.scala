package ch.dubernet.matsimconsole.processing

import processing.core.PApplet
import processing.event.MouseEvent

// not needed? can run class directly in Intellij...
object Experiment extends App {
  val frame = new javax.swing.JFrame("frame");
  val applet = new Experiment

  frame.getContentPane add applet

  applet.init()

  frame.pack()
  frame.setVisible(true)

}

class Experiment extends PApplet {
  var zoom: Float = 1

  override def setup() = {
    size( 400 , 400 );
    stroke( 0 );
  }

  override def draw() = {
    scale( zoom )
    background( 255 , 255 , 255 );
    //line( 150 , 25 , mouseX , mouseY );
    for { x <- 0 until width by 50
          y <- 0 until height by 50 } point( x , y )
  }

  override def mouseWheel(event: MouseEvent) = {
    zoom = Math.max( 0 , zoom - (event.getCount / 10f ) )
  }
}
