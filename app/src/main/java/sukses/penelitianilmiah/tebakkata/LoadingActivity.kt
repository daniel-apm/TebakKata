package sukses.penelitianilmiah.tebakkata

import android.content.Intent
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView

class LoadingActivity : AppCompatActivity() {

    private val splashInterval = 5000
    private val textInterval = 50
    private var progressBar: ProgressBar? = null
    private var progressStatus = 0
    private var textView: TextView? = null
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        progressBar = findViewById<View>(R.id.progressbar) as ProgressBar
        textView = findViewById<View>(R.id.loading) as TextView
        startService(Intent(this, AudioMenuService::class.java))
        Thread(Runnable {
            while (progressStatus < 100) {
                progressStatus += 1
                handler.post {
                    progressBar!!.progress = progressStatus
                    textView!!.text = progressStatus.toString() + "/" + progressBar!!.max
                }
                try {
                    Thread.sleep(textInterval.toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }).start()

        Handler().postDelayed({
            val i = Intent(this@LoadingActivity, MenuActivity::class.java)
            startActivity(i)
            this@LoadingActivity.finish()
        }, splashInterval.toLong())
    }

    override fun onBackPressed() {

    }

}
