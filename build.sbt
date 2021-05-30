name := "Clip Launcher"
organization := "bazjacuzzi"
version := "1.0-SNAPSHOT"
scalaVersion := "2.12.7"

resolvers += "Bitwig" at "https://maven.bitwig.com"
libraryDependencies += "com.bitwig" % "extension-api" % "13"

assemblyJarName in assembly := "ClipLauncher.bwextension"
assemblyOutputPath in assembly := file("C:\\Users\\barry\\OneDrive\\Documents\\Bitwig Studio\\Extensions\\ClipLauncher.bwextension")