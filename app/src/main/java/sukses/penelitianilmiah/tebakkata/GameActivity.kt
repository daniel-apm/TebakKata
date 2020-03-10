package sukses.penelitianilmiah.tebakkata

import android.content.Intent
import android.graphics.Color
import android.media.AudioManager
import android.media.SoundPool
import android.net.Uri
import android.os.Build
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import java.util.ArrayList
import java.util.Random

class GameActivity : AppCompatActivity(), View.OnClickListener {

    private var mySound: SoundPool? = null
    private var alreadyguessId: Int = 0
    private var correctanswerId: Int = 0
    private var correctguessId: Int = 0
    private var gamewinId: Int = 0
    private var wronganswerId: Int = 0
    private var wrongguessId: Int = 0
    private var tebakKata: TextView? = null
    private var clueKata: TextView? = null
    private var skorKata: TextView? = null
    private var inputKata: EditText? = null
    private var jawabKata: Button? = null
    private var kataBaru: Button? = null
    private var gameBaru: Button? = null
    private var pilihKategori: Button? = null
    private var menuUtama: Button? = null
    private var nyawa1: ImageView? = null
    private var nyawa2: ImageView? = null
    private var nyawa3: ImageView? = null
    private var count: Int = 0
    private var skor: Int = 0
    private var kataterpilih: String? = null
    private var clueterpilih: String? = null
    private var totalskor: String? = null
    private var katatersembunyi: CharArray? = null
    private var bundle: Bundle? = null
    private val katatersimpan = ArrayList<Char>()
    private val dataKata = ArrayList<String>()
    private val dataClue = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        bundle = intent.extras
        tebakKata = findViewById<View>(R.id.tebakkata) as TextView
        clueKata = findViewById<View>(R.id.cluekata) as TextView
        skorKata = findViewById<View>(R.id.skorkata) as TextView
        jawabKata = findViewById<View>(R.id.jawabkata) as Button
        kataBaru = findViewById<View>(R.id.kataBaru) as Button
        gameBaru = findViewById<View>(R.id.gameBaru) as Button
        pilihKategori = findViewById<View>(R.id.pilihKategori) as Button
        menuUtama = findViewById<View>(R.id.menuUtama) as Button
        jawabKata!!.setOnClickListener(this)
        kataBaru!!.setOnClickListener(this)
        gameBaru!!.setOnClickListener(this)
        pilihKategori!!.setOnClickListener(this)
        menuUtama!!.setOnClickListener(this)
        inputKata = findViewById<View>(R.id.inputkata) as EditText
        inputKata!!.filters = arrayOf<InputFilter>(InputFilter.AllCaps())
        inputKata!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable?) {
                if (inputKata!!.text.toString().length == 0) {
                    jawabKata!!.isEnabled = false
                } else {
                    jawabKata!!.isEnabled = true
                }

                if (editable != null && editable.length > 1) {
                    inputKata!!.setText(editable.subSequence(1, editable.length))
                    inputKata!!.setSelection(1)
                }

                if (editable != null && editable.toString().length > 0) {
                    inputKata!!.gravity = Gravity.CENTER
                } else {
                    inputKata!!.gravity = Gravity.START
                }
            }
        })
        jawabKata!!.isEnabled = false
        nyawa1 = findViewById<View>(R.id.nyawa1) as ImageView
        nyawa2 = findViewById<View>(R.id.nyawa2) as ImageView
        nyawa3 = findViewById<View>(R.id.nyawa3) as ImageView
        inputSound()
        isiDataArray()
        mulaiGame()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.jawabkata -> cekJawaban()
            R.id.kataBaru -> {
                resetGame()
                kataBaru!!.visibility = View.INVISIBLE
                jawabKata!!.visibility = View.VISIBLE
                mulaiGame()
            }
            R.id.gameBaru -> {
                resetGame()
                skor = 0
                totalskor = Integer.toString(skor)
                skorKata!!.text = "Score = " + totalskor!!
                gameBaru!!.visibility = View.INVISIBLE
                jawabKata!!.visibility = View.VISIBLE
                isiDataArray()
                mulaiGame()
            }
            R.id.pilihKategori -> pilihKategori()
            R.id.menuUtama -> mainMenu()
        }
        }

    override fun onBackPressed() {

    }

    private fun inputSound () {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mySound = SoundPool.Builder()
                    .setMaxStreams(10)
                    .build()
        }
        else {
            mySound = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
        }
        alreadyguessId = mySound!!.load(this, R.raw.alreadyguess, 1)
        correctanswerId = mySound!!.load(this, R.raw.correctanswer, 1)
        correctguessId = mySound!!.load(this, R.raw.correctguess, 1)
        gamewinId = mySound!!.load(this, R.raw.gamewin, 1)
        wronganswerId = mySound!!.load(this, R.raw.wronganswer, 1)
        wrongguessId = mySound!!.load(this, R.raw.wrongguess, 1)
    }

    private fun resetGame() {
        katatersimpan.clear()
        count = 0
        nyawa1!!.visibility = View.VISIBLE
        nyawa2!!.visibility = View.VISIBLE
        nyawa3!!.visibility = View.VISIBLE
        inputKata!!.isEnabled = true
    }

    private fun isiDataArray() {
        val kataPahlawan = bundle!!.getStringArray("kataPahlawan")
        val cluePahlawan = bundle!!.getStringArray("cluePahlawan")
        val kataKerajaan = bundle!!.getStringArray("kataKerajaan")
        val clueKerajaan = bundle!!.getStringArray("clueKerajaan")
        val kataKebudayaan = bundle!!.getStringArray("kataKebudayaan")
        val clueKebudayaan = bundle!!.getStringArray("clueKebudayaan")

        if (bundle!!.containsKey("kataPahlawan") && bundle!!.containsKey("cluePahlawan")) {
            for (i in kataPahlawan!!.indices) {
                dataKata.add(kataPahlawan[i].toString())
                dataClue.add(cluePahlawan!![i].toString())
            }
        } else if (bundle!!.containsKey("kataKerajaan") && bundle!!.containsKey("clueKerajaan")) {
            for (i in kataKerajaan!!.indices) {
                dataKata.add(kataKerajaan[i].toString())
                dataClue.add(clueKerajaan!![i].toString())
            }
        } else if (bundle!!.containsKey("kataKebudayaan") && bundle!!.containsKey("clueKebudayaan")) {
            for (i in kataKebudayaan!!.indices) {
                dataKata.add(kataKebudayaan[i].toString())
                dataClue.add(clueKebudayaan!![i].toString())
            }
        }
    }

    private fun hapusDataArray() {
        dataKata.clear()
        dataClue.clear()
    }

    private fun mulaiGame() {
        startService(Intent(this, AudioGameService::class.java))
        var i: Int
        val wadahArrayBaru = wadahArray(dataKata, dataClue)
        kataterpilih = wadahArrayBaru[0]
        clueterpilih = wadahArrayBaru[1]
        katatersembunyi = CharArray(kataterpilih!!.length)
        i = 0
        while (i < kataterpilih!!.length) {
            katatersembunyi!![i] = '-'
            if (kataterpilih!![i] == ' ') {
                katatersembunyi!![i] = ' '
            }
            i++
        }
        tebakKata!!.setText(katatersembunyi, 0, i)
        tebakKata!!.setTextColor(Color.WHITE)
        clueKata!!.text = clueterpilih
    }

    private fun hitungSkor() {
        when (count) {
            0 -> {
                skor += 5
                totalskor = Integer.toString(skor)
                skorKata!!.text = "Score = " + totalskor!!
            }
            1 -> {
                skor += 4
                totalskor = Integer.toString(skor)
                skorKata!!.text = "Score = " + totalskor!!
            }
            2 -> {
                skor += 3
                totalskor = Integer.toString(skor)
                skorKata!!.text = "Score = " + totalskor!!
            }
        }
    }

    private fun cekJawaban() {
        var k: Int
        val x = inputKata!!.text[0]
        inputKata!!.setText("")
        if (kataterpilih!!.contains(x)) {
            k = 0
            while (k < kataterpilih!!.length) {
                if (kataterpilih!![k] == x) {
                    katatersembunyi!![k] = x
                }
                k++
            }
            tebakKata!!.setText(katatersembunyi, 0, k)
            if (katatersimpan.contains(x)) {
                mySound!!.play(alreadyguessId, 1f, 1f, 1, 0, 1f)
                Toast.makeText(this, "Kamu sudah pernah memasukkan huruf $x", Toast.LENGTH_SHORT).show()
            } else {
                mySound!!.play(correctguessId, 1f, 1f, 1, 0, 1f)
                katatersimpan.add(x)
            }
        } else {
            if (katatersimpan.contains(x)) {
                mySound!!.play(alreadyguessId, 1f, 1f, 1, 0, 1f)
                Toast.makeText(this, "Kamu sudah pernah memasukkan huruf $x", Toast.LENGTH_SHORT).show()
            } else {
                katatersimpan.add(x)
                count += 1
                if (count == 1) {
                    mySound!!.play(wrongguessId, 1f, 1f, 1, 0, 1f)
                    nyawa1!!.visibility = View.INVISIBLE
                    Toast.makeText(this, "Coba huruf yang lain !", Toast.LENGTH_SHORT).show()
                } else if (count == 2) {
                    mySound!!.play(wrongguessId, 1f, 1f, 1, 0, 1f)
                    nyawa2!!.visibility = View.INVISIBLE
                    Toast.makeText(this, "Kesempatan kamu tinggal sekali lagi !", Toast.LENGTH_SHORT).show()
                } else {
                    stopService(Intent(this, AudioGameService::class.java))
                    mySound!!.play(wronganswerId, 1f, 1f, 1, 0, 1f)
                    nyawa3!!.visibility = View.INVISIBLE
                    inputKata!!.isEnabled = false
                    jawabKata!!.visibility = View.INVISIBLE
                    gameBaru!!.visibility = View.VISIBLE
                    hapusDataArray()
                    CobaLagi()
                }
            }
        }

        if (kataTertebak(katatersembunyi!!)) {
            if (dataKata.isEmpty()) {
                stopService(Intent(this, AudioGameService::class.java))
                mySound!!.play(gamewinId, 1f, 1f, 1, 0, 1f)
                tebakKata!!.text = kataterpilih
                tebakKata!!.setTextColor(Color.GREEN)
                clueKata!!.text = "PERMAINAN SELESAI !!!"
                inputKata!!.isEnabled = false
                jawabKata!!.visibility = View.INVISIBLE
                gameBaru!!.visibility = View.VISIBLE
                hitungSkor()
                GameSelesai()
            } else {
                stopService(Intent(this, AudioGameService::class.java))
                mySound!!.play(correctanswerId, 1f, 1f, 1, 0, 1f)
                Toast.makeText(this, "Wah hebat, kamu berhasil menebaknya !", Toast.LENGTH_SHORT).show()
                tebakKata!!.text = kataterpilih
                tebakKata!!.setTextColor(Color.GREEN)
                hitungSkor()
                inputKata!!.isEnabled = false
                jawabKata!!.visibility = View.INVISIBLE
                kataBaru!!.visibility = View.VISIBLE
            }
        }
    }

    private fun CobaLagi() {
        tebakKata!!.setTextColor(Color.RED)
        AlertDialog.Builder(this)
                .setTitle("GAME OVER")
                .setMessage("Sayang sekali, kamu gagal menebak kata $kataterpilih dengan total skor $skor")
                .setNegativeButton("OK", null)
                .setPositiveButton("Cari tahu") {dialogInterface, a ->
                    if (bundle!!.containsKey("kataKerajaan") && bundle!!.containsKey("clueKerajaan")) {
                        val uri = Uri.parse("http://www.google.com/#q=KERAJAAN " + kataterpilih)
                        val pindah = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(pindah)
                    }
                    else {
                        val uri = Uri.parse("http://www.google.com/#q=" + kataterpilih)
                        val pindah = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(pindah)
                    }
                }.create().show()
    }

    private fun GameSelesai() {
        AlertDialog.Builder(this)
                .setTitle("SELAMAT !!!")
                .setMessage("Kamu telah berhasil menyelesaikan permainan ini dengan total skor $skor")
                .setPositiveButton("OK", null).create().show()
    }

    private fun mainMenu() {
        AlertDialog.Builder(this)
                .setTitle("MENU")
                .setMessage("Ingin kembali ke Menu ?")
                .setNegativeButton("Iya") { dialogInterface, a ->
                    stopService(Intent(this, AudioGameService::class.java))
                    startService(Intent(this, AudioMenuService::class.java))
                    finish()
                }
                .setPositiveButton("Tidak", null)
                .create().show()
    }

    private fun pilihKategori() {
        AlertDialog.Builder(this)
                .setTitle("KATEGORI")
                .setMessage("Ingin ganti Kategori?")
                .setNegativeButton("Iya") { dialogInterface, a ->
                    val pindah = Intent(this@GameActivity, KategoriActivity::class.java)
                    stopService(Intent(this, AudioGameService::class.java))
                    startService(Intent(this, AudioMenuService::class.java))
                    startActivity(pindah)
                    finish()
                }
                .setPositiveButton("Tidak", null)
                .create().show()
    }

    companion object {
        private val random = Random()

        private fun wadahArray(wadahArrayKata: ArrayList<*>, wadahArrayClue: ArrayList<*>): Array<String> {
            val index = random.nextInt(wadahArrayKata.size)

            val tmpkata = wadahArrayKata[index].toString()
            val tmpclue = wadahArrayClue[index].toString()

            wadahArrayKata.removeAt(index)
            wadahArrayClue.removeAt(index)

            return arrayOf(tmpkata, tmpclue)
        }

        private fun kataTertebak(array: CharArray): Boolean {
            for (i in array.indices) {
                if (array[i] == '-') return false
            }
            return true
        }
    }
}