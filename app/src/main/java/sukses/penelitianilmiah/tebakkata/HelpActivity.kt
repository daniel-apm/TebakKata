package sukses.penelitianilmiah.tebakkata

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class HelpActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        val home = findViewById<View>(R.id.home) as ImageView
        home.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        finish()
    }

    override fun onBackPressed() {

    }
}
