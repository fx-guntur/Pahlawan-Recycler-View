package com.example.pahlawanrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ListPahlawanAdapter (private val listPahlawan: ArrayList<Pahlawan>) : RecyclerView.Adapter<ListPahlawanAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_pahlawan, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listPahlawan.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, description, photo) = listPahlawan[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvName.text = name
        holder.tvDescription.text = description
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listPahlawan[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Pahlawan)
    }
    /*
       Di sini kita menambahkan interface supaya ketika memanggil fungsi setOnItemClickCallback, terdapat data yang dikembalikan.
       Untuk memasukkan data ke interface, kita cukup memasukkan data ke dalam fungsi onClicked di dalam onBindViewHolder ketika itemView
       ditekan. Kita menggunakan holder.itemView karena kita ingin memberikan aksi ketika keseluruhan item ditekan. Apabila Anda ingin
       menempatkan aksi pada view tertentu saja seperti foto, gunakan kode seperti holder.imgPhoto.setOnClickListener.

       Setelah itu, data dapat dikonsumsi dari Activity dengan memanggil fungsi setOnItemClickCallback seperti berikut.
       listHeroAdapter.setOnItemClickCallback(object : ListHeroAdapter.OnItemClickCallback {
           override fun onItemClicked(data: Hero) {
             ...
          }
       })

       Selain menggunakan interface, untuk pengguna Kotlin sebenarnya juga bisa membuat callback dengan lebih simpel menggunakan lambda.
       Caranya tambahkan lambda pada parameter seperti berikut.

       class ListHeroAdapter(
            private val listHero: ArrayList<Hero>,
            private val onClick: (Hero) -> Unit
        ) : RecyclerView.Adapter<ListHeroAdapter.ListViewHolder>()
        ...
        override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
            val hero = listHero[position]
            holder.bind(hero)
        }
        ...
        inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(hero: Hero) {
                ...
                itemView.setOnClickListener {
                    onClick(hero)
                   }
            }
        }
        }

        Kemudian, untuk memanggilnya, cukup gunakan kurung kurawal seperti ini.
        val listHeroAdapter = ListHeroAdapter(list, onClick = {
            //do something when item click
        })
       */
}
/*
Fungsi onCreateViewHolder()
    digunakan untuk membuat ViewHolder baru yang berisi layout item yang digunakan, dalam hal ini yaitu R.layout.item_row_hero. Metode ini
    membuat serta menginisialisasi ViewHolder dan View yang akan diatribusikan. Namun, pada fungsi ini tidak bertujuan mengisi konten tampilan
    sehingga belum terikat dengan data tertentu.
Fungsi onBindViewHolder()
    digunakan untuk menetapkan data yang ada ke dalam ViewHolder sesuai dengan posisinya dengan menggunakan listHero[position]. Misalnya, jika
    RecyclerView yang dibuat bertujuan untuk menampilkan daftar nama, metode tersebut mungkin menemukan nama yang sesuai, kemudian menetapkannya
    pada widget TextView yang ada dalam ViewHolder.
Fungsi getItemCount()
    digunakan untuk menetapkan ukuran dari list data yang ingin ditampilkan. Karena kita ingin menampilkan semua data, maka kita menggunakan
    listHero.size untuk mengetahui jumlah data pada list.
Kelas ListViewHolder
    digunakan sebagai ViewHolder dalam RecyclerView. ViewHolder adalah wrapper seperti View yang berisi layout untuk setiap item dalam daftar
    RecyclerView. Di sinilah tempat untuk menginisialisasi setiap komponen pada layout item dengan menggunakan itemView.findViewById. Adapter
    akan membuat objek ViewHolder seperlunya dan menetapkan data untuk tampilan tersebut. Proses mengatribusikan tampilan ke datanya disebut
    binding.
Kemudian, hubungan antara satu adapter dengan ViewHolder adalah satu ke banyak. Artinya, satu kelas adapter bisa memiliki lebih dari satu
ViewHolder.
*/