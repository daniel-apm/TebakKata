package sukses.penelitianilmiah.tebakkata

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import android.view.View

class KategoriActivity : AppCompatActivity() {

    private val a = Bundle()
    private val b = Bundle()
    private val c = Bundle()
    private var kumpulanKataPahlawan: Array<String>? = null
    private var kumpulanCluePahlawan: Array<String>? = null
    private var kumpulanKataKerajaan: Array<String>? = null
    private var kumpulanClueKerajaan: Array<String>? = null
    private var kumpulanKataKebudayaan: Array<String>? = null
    private var kumpulanClueKebudayaan: Array<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori)
        kumpulanKataPahlawan = resources.getStringArray(R.array.kumpulanpahlawan)
        kumpulanKataKerajaan = resources.getStringArray(R.array.kumpulankerajaan)
        kumpulanKataKebudayaan = resources.getStringArray(R.array.kumpulankebudayaan)
        kumpulanCluePahlawan = resources.getStringArray(R.array.kumpulancluepahlawan)
        kumpulanClueKerajaan = resources.getStringArray(R.array.kumpulancluekerajaan)
        kumpulanClueKebudayaan = resources.getStringArray(R.array.kumpulancluekebudayaan)
        val mainGrid = findViewById<View>(R.id.mainGrid) as android.support.v7.widget.GridLayout
        pilihKategori(mainGrid)
    }

    private fun pilihKategori(mainGrid: android.support.v7.widget.GridLayout) {
        for (i in 0 until mainGrid.childCount) {
            val cardView = mainGrid.getChildAt(i) as CardView
            cardView.setOnClickListener {
                if (i == 0) {
                    val pindah = Intent(this@KategoriActivity, GameActivity::class.java)
                    a.putStringArray("kataPahlawan", kumpulanKataPahlawan)
                    a.putStringArray("cluePahlawan", kumpulanCluePahlawan)
                    pindah.putExtras(a)
                    stopService(Intent(this, AudioMenuService::class.java))
                    startActivity(pindah)
                    finish()
                } else if (i == 1) {
                    val pindah = Intent(this@KategoriActivity, GameActivity::class.java)
                    b.putStringArray("kataKerajaan", kumpulanKataKerajaan)
                    b.putStringArray("clueKerajaan", kumpulanClueKerajaan)
                    pindah.putExtras(b)
                    stopService(Intent(this, AudioMenuService::class.java))
                    startActivity(pindah)
                    finish()
                } else if (i == 2) {
                    val pindah = Intent(this@KategoriActivity, GameActivity::class.java)
                    c.putStringArray("kataKebudayaan", kumpulanKataKebudayaan)
                    c.putStringArray("clueKebudayaan", kumpulanClueKebudayaan)
                    pindah.putExtras(c)
                    stopService(Intent(this, AudioMenuService::class.java))
                    startActivity(pindah)
                    finish()
                } else if (i == 3) {
                    finish()
                }
            }
        }
    }

    override fun onBackPressed() {

    }
}
