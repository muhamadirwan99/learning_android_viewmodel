package com.dicoding.myviewmodel

import androidx.lifecycle.ViewModel

// ViewModel adalah komponen Android Architecture yang dirancang untuk menyimpan dan mengelola
// data terkait UI dengan lifecycle-aware manner
// ViewModel akan bertahan saat configuration changes (rotasi layar, perubahan bahasa, dll)
class MainViewModel : ViewModel(){

    // Property untuk menyimpan hasil perhitungan volume
    // Data ini akan tetap ada meskipun Activity di-destroy dan di-recreate
    // karena ViewModel tidak ikut di-destroy saat configuration change
    var result = 0

    // Fungsi untuk menghitung volume (panjang x lebar x tinggi)
    // Business logic diletakkan di ViewModel, bukan di Activity/Fragment
    // Ini memisahkan tanggung jawab: UI (Activity) hanya menampilkan, logic ada di ViewModel
    fun calculate(width: String, height: String, length: String){
        // Konversi String ke Int dan kalikan untuk mendapat volume
        result = width.toInt() * height.toInt() * length.toInt()
    }
}