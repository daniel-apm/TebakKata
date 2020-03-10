package sukses.penelitianilmiah.tebakkata

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class AudioMenuService: Service() {

    private var backgroundplayer: MediaPlayer?=null

    override fun onBind(p0: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        backgroundplayer = MediaPlayer.create(this, R.raw.bgmindonesiaraya)
        backgroundplayer!!.isLooping = true
        backgroundplayer!!.start()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        backgroundplayer!!.stop()
    }
}