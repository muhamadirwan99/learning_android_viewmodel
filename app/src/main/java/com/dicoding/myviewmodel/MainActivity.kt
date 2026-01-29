package com.dicoding.myviewmodel

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.myviewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // View Binding untuk mengakses view dengan type-safe tanpa perlu findViewById()
    private lateinit var binding: ActivityMainBinding

    // Cara lama: late initialization menggunakan ViewModelProvider
//    private lateinit var viewModel: MainViewModel

    // Cara baru: menggunakan property delegate viewModels() dari ktx
    // ViewModel akan survive configuration changes (seperti rotasi layar)
    // sehingga data tidak hilang saat Activity di-recreate
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mengaktifkan tampilan edge-to-edge agar konten bisa meluas ke area status bar dan navigation bar
        enableEdgeToEdge()

        // Inisialisasi View Binding untuk menghindari findViewById() berulang dan mengurangi boilerplate code
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menangani window insets agar konten tidak tertutup oleh system bars (status bar & navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Memberikan padding pada view agar konten tidak tertimpa oleh system bars
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Cara lama inisialisasi ViewModel (sekarang diganti dengan viewModels() delegate)
//        viewModel = ViewModelProvider(this).get<MainViewModel>(MainViewModel::class.java)

        // Menampilkan hasil perhitungan yang tersimpan di ViewModel saat Activity dibuat/direcreate
        // Ini penting agar data tidak hilang saat configuration change (misal rotasi layar)
        displayResult()

        // Listener untuk menangani klik tombol hitung
        binding.btnCalculate.setOnClickListener {

            // Mengambil input dari user dan mengkonversi ke String
            val width = binding.edtWidth.text.toString()
            val height = binding.edtHeight.text.toString()
            val length = binding.edtLength.text.toString()

            // Validasi input: memastikan semua field terisi sebelum melakukan perhitungan
            // Urutan validasi dari bawah ke atas agar fokus diarahkan ke field yang kosong paling atas
            when {
                length.isEmpty() -> {
                    binding.edtLength.error = "Masih kosong"
                }
                width.isEmpty() -> {
                    binding.edtWidth.error = "Masih kosong"
                }
                height.isEmpty() -> {
                    binding.edtHeight.error = "Masih kosong"
                }
                else -> {
                    // Melakukan perhitungan volume melalui ViewModel (bukan di Activity)
                    // Ini mengikuti prinsip separation of concerns: UI logic terpisah dari business logic
                    viewModel.calculate(width, height, length)
                    // Menampilkan hasil perhitungan ke UI
                    displayResult()
                }
            }

        }
    }

    // Fungsi untuk menampilkan hasil perhitungan dari ViewModel ke TextView
    // Fungsi terpisah agar bisa dipanggil berkali-kali tanpa duplikasi kode
    private fun displayResult() {
        binding.tvResult.text = viewModel.result.toString()
    }
}