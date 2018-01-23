object EventsPrototype {

  trait Event {
    val time: Double
  }

  case class Event1(time: Double) extends Event
  case class Event2(time: Double) extends Event
  case class Event3(time: Double) extends Event

  // all events have time
  Stream(Event1(0), Event2(0), Event2(1), Event3(1)) takeWhile {
    _.time < 1
  } toList

  // no need for special event handlers: partial functions filter out uninteresting events
  // need to use collect instead of map to use this feature
  List(Event1(0), Event2(0), Event2(1), Event3(1)) collect { case Event2(t) => t }
}
