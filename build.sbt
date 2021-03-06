name := "matsim-console"

version := "0.1"

scalaVersion := "2.12.4"

resolvers += "osgeo" at "http://download.osgeo.org/webdav/geotools"
resolvers += "matsim" at "https://dl.bintray.com/matsim/matsim"
libraryDependencies += "org.matsim" % "matsim" % "0.9.0"

libraryDependencies += "junit" % "junit" % "4.10" % Test
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

libraryDependencies += "org.processing" % "core" % "2.2.1"

// for FRP
libraryDependencies += "com.lihaoyi" %% "scalarx" % "0.3.2"

// compiler checks. see www.wartremover.org
//wartremoverErrors ++= Warts.unsafe