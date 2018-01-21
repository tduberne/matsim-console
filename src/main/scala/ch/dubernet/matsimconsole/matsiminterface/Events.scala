package ch.dubernet.matsimconsole.matsiminterface

import org.matsim.api.core.v01.Id
import org.matsim.api.core.v01.population.Person
import org.matsim.facilities.ActivityFacility

trait HasCoord {
  def coord: Coord

  def x: Double = coord.x
  def y: Double = coord.y
  def z: Double = coord.z
}

trait Event {
  val time: Double
}

// TODO: not ids, but objects (person, facility...)
// TODO: coordinate? or done on the fly when needed?
case class ActivityStartEvent( time: Double,
                               personId : Id[Person], // or "scalaized" person?
                               linkId: Id[Link],
                               facilityId: Id[ActivityFacility], // or scalaized facility?
                               actType: String ) extends Event
case class ActivityEndEvent( time: Double,
                             personId : Id[Person], // or "scalaized" person?
                             linkId: Id[Link],
                             facilityId: Id[ActivityFacility], // or scalaized facility?
                             actType: String ) extends Event
