package bazjacuzzi

import com.bitwig.extension.api.util.midi.ShortMidiMessage
import com.bitwig.extension.callback.{BooleanValueChangedCallback, ClipLauncherSlotBankPlaybackStateChangedCallback, ShortMidiMessageReceivedCallback}
import com.bitwig.extension.controller.api._
import com.bitwig.extension.controller.{ControllerExtension, ControllerExtensionDefinition}

import scala.collection.mutable


case class TrackLauncherExtension(definition: TrackLauncherExtensionDefinition, host: ControllerHost)
  extends ControllerExtension(definition, host)
    with ShortMidiMessageReceivedCallback
    with ClipLauncherSlotBankPlaybackStateChangedCallback
    with BooleanValueChangedCallback {

  override def exit(): Unit = {}
  override def flush(): Unit = {}
  override def getHost: ControllerHost = host
  override def getExtensionDefinition: ControllerExtensionDefinition = definition

  private val numScenes = 128
  private val numTracks = 16

  private lazy val transport = host.createTransport()
  private lazy val midiOut = host.getMidiOutPort(0)
  private lazy val midiIn = host.getMidiInPort(0)

  // The entire grid of clips
  private lazy val trackBank = host.createMainTrackBank(numTracks,0, numScenes)

  // The columns of clips (scenes)
  private lazy val sceneBank = host.createSceneBank(numScenes)

  // The tracks
  private lazy val tracks = (0 until numTracks).map { track => track -> trackBank.getItemAt(track).clipLauncherSlotBank }.toMap

  /**
   * each track has its own channel (0 to 15)
   * each clip has its own note (0 to 127)
   *
   * everything has to be initialized in here, hence the lazy vals
   */
  override def init(): Unit = {
    transport.isPlaying.addValueObserver(this)
    midiOut
    trackBank
    sceneBank
    (0 until numTracks).foreach { track =>
      tracks(track).addPlaybackStateObserver(this)
      tracks(track).stop()
      (0 until numScenes).foreach { scene =>
        tracks(track).getItemAt(scene).isPlaying.markInterested()
      }
    }
    midiIn.setMidiCallback(this)
    sceneBank.stop()
  }

  override def midiReceived(msg: ShortMidiMessage): Unit = {
    println("midi in", msg.getChannel, msg.getData1)
    if (msg.isNoteOn && msg.getData2 > 0) {
      val clipNum = msg.getData1
      val trackNum = msg.getChannel
      tracks(trackNum).launch(clipNum)
    }
  }

  override def valueChanged(newValue: Boolean): Unit = {
    // Empty
  }

  override def playbackStateChanged(slotIndex: Int, playbackState: Int, isQueued: Boolean): Unit = {
    // Empty
  }
}
