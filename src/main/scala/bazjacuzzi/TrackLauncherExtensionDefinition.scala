package bazjacuzzi

import com.bitwig.extension.controller.ControllerExtensionDefinition
import com.bitwig.extension.controller.api.ControllerHost

import java.util.UUID

case class TrackLauncherExtensionDefinition() extends ControllerExtensionDefinition {
   private val DRIVER_ID = UUID.fromString("046a6b14-91ba-44a7-93d5-604fdbbf5977")

   override def getName = "TrackLauncher"
   override def getAuthor = "BazJacuzzi"
   override def getVersion = "0.1"
   override def getId: UUID = DRIVER_ID
   override def getHardwareVendor = "BazJacuzzi"
   override def getHardwareModel = "TrackLauncher"
   override def getRequiredAPIVersion = 12
   override def getNumMidiInPorts = 1
   override def getNumMidiOutPorts = 1

           import com.bitwig.extension.api.PlatformType
  import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList

   def listAutoDetectionMidiPortNames(list: AutoDetectionMidiPortNamesList, platformType: PlatformType): Unit = {
      if (platformType eq PlatformType.WINDOWS) list.add(Array[String]("architect"), Array[String]("architect"))
    else if (platformType eq PlatformType.MAC) list.add(Array[String]("architect"), Array[String]("architect"))
    else if (platformType eq PlatformType.LINUX) list.add(Array[String]("architect MIDI 1"), Array[String]("architect MIDI 1"))
   }

   override def createInstance(host: ControllerHost) = new TrackLauncherExtension(this, host)
}
