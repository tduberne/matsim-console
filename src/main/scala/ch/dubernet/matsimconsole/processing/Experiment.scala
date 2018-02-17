package ch.dubernet.matsimconsole.processing

import processing.core.PApplet

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
  override def setup() = {
    size( 400 , 400 );
    stroke( 0 );
  }

  override def draw() = {
    background( 255 , 255 , 255 );
    line( 150 , 25 , mouseX , mouseY );
  }
}
