package sukses.penelitianilmiah.tebakkata

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.CardView
import android.view.View

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val mainGrid = findViewById<View>(R.id.mainGrid) as android.support.v7.widget.GridLayout
        pilihMenu(mainGrid)
    }

    private fun pilihMenu(mainGrid: android.support.v7.widget.GridLayout) {
        for (i in 0 until mainGrid.childCount) {
            val cardView = mainGrid.getChildAt(i) as CardView
            cardView.setOnClickListener {
                if (i == 0) {
                    val x = Intent(this@MenuActivity, KategoriActivity::class.java)
                    startActivity(x)
                } else if (i == 1) {
                    val x = Intent(this@MenuActivity, HelpActivity::class.java)
                    startActivity(x)
                } else if (i == 2) {
                    val x = Intent(this@MenuActivity, AboutActivity::class.java)
                    startActivity(x)
                } else if (i == 3) {
                    AlertDialog.Builder(this)
                            .setTitle("KELUAR")
                            .setMessage("Kamu ingin keluar?")
                            .setNegativeButton("Iya") { dialogInterface, a ->
                                stopService(Intent(this, AudioMenuService::class.java))
                                finish()
                            }
                            .setPositiveButton("Tidak", null)
                            .create().show()
                }
            }
        }
    }

    override fun onBackPressed() {

    }
}
