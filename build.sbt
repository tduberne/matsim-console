name := "matsim-console"

version := "0.1"

scalaVersion := "2.11.8"

resolvers += "osgeo" at "http://download.osgeo.org/webdav/geotools"
resolvers += "matsim" at "https://dl.bintray.com/matsim/matsim"
libraryDependencies += "org.matsim" % "matsim" % "0.9.0"

libraryDependencies += "junit" % "junit" % "4.10" % Test
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"