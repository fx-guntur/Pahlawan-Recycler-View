package com.example.pahlawanrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvPahlawan: RecyclerView
    private val list = ArrayList<Pahlawan>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvPahlawan = findViewById(R.id.rv_heroes)
        rvPahlawan.setHasFixedSize(true)
        /*
        Baris di atas menjelaskan bahwa bila fixed size bernilai true, RecyclerView dapat melakukan optimasi ukuran lebar dan tinggi secara
        otomatis. Nilai lebar dan tinggi RecyclerView menjadi konstan. Terlebih jika kita memiliki koleksi data yang dinamis untuk proses
        penambahan, perpindahan, dan pengurangan item dari koleksi data.
        */

        list.addAll(getListHeroes())
        showRecyclerList()
    }
    private fun getListHeroes(): ArrayList<Pahlawan> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listHero = ArrayList<Pahlawan>()
        for (i in dataName.indices) {
            val hero = Pahlawan(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1))
            listHero.add(hero)
        }
        return listHero
    }

    private fun showRecyclerList() {
        rvPahlawan.layoutManager = LinearLayoutManager(this)
        val listPahlawanAdapter = ListPahlawanAdapter(list)
        rvPahlawan.adapter = listPahlawanAdapter
        listPahlawanAdapter.setOnItemClickCallback(object : ListPahlawanAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Pahlawan) {
                showSelectedHero(data)
            }
        })
        /* Apabila terdapat halaman detail dan Anda ingin mengirimkan data dari MainActivity ke DetailActivity, Anda bisa menggunakan kode
        berikut pada MainActivity untuk mengirimkan data Parcelable.
        listHeroAdapter.setOnItemClickCallback(object : ListHeroAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Hero) {
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetail.putExtra("DATA", data)
                startActivity(intentToDetail)
            }
        })
        Kemudian untuk menerimanya, cukup menggunakan kode berikut pada halaman detail.
        val data = intent.getParcelableExtra<Hero>("DATA")
        Log.d("Detail Data", data?.name.toString())
        Log hanya kode tambahan yang digunakan untuk memeriksa apakah data yang dikirim benar-benar terkirim atau tidak.
        */
    }

    private fun showSelectedHero(pahlawan: Pahlawan) {
        Toast.makeText(this, "Kamu memilih " + pahlawan.name, Toast.LENGTH_SHORT).show()
        /*
        Untuk mendapatkan context pada Adapter, kita bisa menggunakan itemView.getContext(), berbeda dengan saat di Activity, kita bisa
        mendapatkan context dengan cara this@MainActivity/MainActivity.this. Context ini akan sangat bermanfaat untuk menampilkan toast,
        mengambil resource, berpindah pada activity lain. Sebagai contoh, jika Anda ingin berpindah ke Activity lain dari dalam Adapter,
        kita bisa menggunakan kode berikut.

        itemView.setOnClickListener {
            val intent = Intent(itemView.context, DetailActivity::class.java)
            itemView.context.startActivity(intent)
        }
        */
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)

        /* Kode di atas digunakan untuk menampilkan menu pada Activity tertentu. Layout menu diambil dari menu_main.xml yang terdapat di
        folder menu. */
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_list -> {
                rvPahlawan.layoutManager = LinearLayoutManager(this)
            }
            R.id.action_grid -> {
                rvPahlawan.layoutManager = GridLayoutManager(this, 3)
            }
            /*
            Paramater pertama merupakan context dan parameter kedua adalah jumlah kolom yang ingin dibuat.
            Anda juga bisa mengatur tampilan RecyclerView ketika landscape dan portrait.
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                rvHeroes.layoutManager = GridLayoutManager(this, 2)
            } else {
                rvHeroes.layoutManager = LinearLayoutManager(this)
            }

            Setelah menampilkan item menggunakan metode onCreateOptionsMenu(), kita tinggal memasang event listener untuk dijalankan ketika
            item tersebut dipilih. Listener click pada menu action bar dapat memanfaatkan onOptionsItemSelected().

            Dengan melakukan percabangan menggunakan switch kita dapat memberikan listener pada setiap item. Seperti contoh di atas, ketika
            id-nya adalah R.id.list maka yang terjadi adalah kita mengatur layout manager menggunakan LinearLayoutManager. Ketika R.id.grid
            diklik, kita ganti menjadi GridLayoutManager.
            */
        }
        return super.onOptionsItemSelected(item)
    }
}
