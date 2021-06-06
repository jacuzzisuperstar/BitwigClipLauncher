package bazjacuzzi

import com.bitwig.extension.controller.ControllerExtensionDefinition
import com.bitwig.extension.controller.api.ControllerHost

import java.util.UUID

case class ClipLauncherExtensionDefinition() extends ControllerExtensionDefinition {
   private val DRIVER_ID = UUID.fromString("551cce95-e2ea-4b9f-b40a-971c0d11bf3e")

   override def getName = "ClipLauncher"
   override def getAuthor = "BazJacuzzi"
   override def getVersion = "0.1"
   override def getId: UUID = DRIVER_ID
   override def getHardwareVendor = "BazJacuzzi"
   override def getHardwareModel = "ClipLauncher"
   override def getRequiredAPIVersion = 12
   override def getNumMidiInPorts = 1
   override def getNumMidiOutPorts = 1

           import com.bitwig.extension.api.PlatformType
  import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList

   def listAutoDetectionMidiPortNames(list: AutoDetectionMidiPortNamesList, platformType: PlatformType): Unit = {
      if (platformType eq PlatformType.WINDOWS) list.add(Array[String]("cliplauncher"), Array[String]("cliplauncher"))
    else if (platformType eq PlatformType.MAC) list.add(Array[String]("cliplauncher"), Array[String]("cliplauncher"))
    else if (platformType eq PlatformType.LINUX) list.add(Array[String]("cliplauncher MIDI 1"), Array[String]("cliplauncher MIDI 1"))
   }

   override def createInstance(host: ControllerHost) = new ClipLauncherExtension(this, host)
}
