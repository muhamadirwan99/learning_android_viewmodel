# MyViewModel - Aplikasi Kalkulator Volume

Aplikasi Android sederhana untuk mendemonstrasikan penggunaan **ViewModel** dalam arsitektur aplikasi Android modern.

## 📋 Deskripsi

Aplikasi ini adalah kalkulator volume yang menghitung hasil perkalian dari panjang, lebar, dan tinggi. Aplikasi ini dibuat untuk memahami konsep **ViewModel** dalam Android Architecture Components dan bagaimana data dapat bertahan saat terjadi configuration changes (seperti rotasi layar).

## 🎯 Tujuan Pembelajaran

1. **ViewModel Pattern**: Memahami cara kerja ViewModel dalam memisahkan business logic dari UI
2. **View Binding**: Menggunakan View Binding untuk akses view yang type-safe
3. **Configuration Changes**: Memahami bagaimana ViewModel mempertahankan data saat rotasi layar
4. **Separation of Concerns**: Memisahkan tanggung jawab antara Activity (UI) dan ViewModel (Logic)

## 🏗️ Arsitektur

Aplikasi ini menggunakan komponen-komponen berikut:

### 1. **MainActivity.kt**
- **Peran**: UI Controller (menangani interaksi pengguna)
- **Tanggung Jawab**:
  - Menampilkan UI (layout)
  - Menangkap input dari pengguna
  - Validasi input (memastikan field tidak kosong)
  - Menampilkan hasil perhitungan dari ViewModel
  
### 2. **MainViewModel.kt**
- **Peran**: Business Logic Holder
- **Tanggung Jawab**:
  - Menyimpan data hasil perhitungan
  - Melakukan perhitungan volume (width × height × length)
  - Mempertahankan data saat configuration changes

## 🔑 Konsep Penting

### Mengapa Menggunakan ViewModel?

#### ❌ **Tanpa ViewModel:**
```kotlin
class MainActivity : AppCompatActivity() {
    var result = 0 // Data akan HILANG saat rotasi layar!
    
    fun calculate(w: String, h: String, l: String) {
        result = w.toInt() * h.toInt() * l.toInt()
    }
}
```
**Masalah**: Saat rotasi layar, Activity di-destroy dan di-recreate, sehingga variabel `result` akan di-reset ke 0.

#### ✅ **Dengan ViewModel:**
```kotlin
class MainViewModel : ViewModel() {
    var result = 0 // Data TETAP ADA saat rotasi layar!
}

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    // viewModel akan survive configuration changes
}
```
**Solusi**: ViewModel memiliki lifecycle yang lebih panjang dari Activity, sehingga data tetap ada saat configuration change.

### Lifecycle Comparison

```
Activity Lifecycle (dengan rotasi layar):
onCreate() → onStart() → onResume() → [ROTASI] → onPause() → onStop() → onDestroy()
  ↓ Activity baru dibuat
onCreate() → onStart() → onResume()

ViewModel Lifecycle:
Created → [ROTASI (Activity di-recreate)] → Tetap hidup → onCleared() (saat Activity benar-benar finish)
```

## 🛠️ Teknologi yang Digunakan

- **Kotlin**: Bahasa pemrograman
- **View Binding**: Untuk binding view yang type-safe
- **ViewModel**: Android Architecture Component untuk manage UI-related data
- **AppCompat**: Backward compatibility
- **Material Design**: Design system dari Google

## 📦 Dependencies

```gradle
// ViewModel KTX
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.x.x"
implementation "androidx.activity:activity-ktx:1.x.x"

// View Binding (enabled di build.gradle)
viewBinding { enabled = true }
```

## 🚀 Cara Kerja Aplikasi

1. **Input Data**: User memasukkan nilai width, height, dan length
2. **Validasi**: Sistem memastikan semua field terisi
3. **Perhitungan**: ViewModel menghitung volume (width × height × length)
4. **Display**: Hasil ditampilkan di TextView
5. **Rotasi**: Jika layar dirotasi, hasil tetap ada karena disimpan di ViewModel

## 📝 Fitur Utama

✅ Kalkulator volume sederhana  
✅ Validasi input  
✅ Data persists saat rotasi layar  
✅ Menggunakan View Binding (no `findViewById()`)  
✅ Separation of Concerns (UI terpisah dari Logic)  

## 🎓 Poin Pembelajaran

### 1. **View Binding**
```kotlin
// Menghindari findViewById() berulang-ulang
private lateinit var binding: ActivityMainBinding
binding = ActivityMainBinding.inflate(layoutInflater)
setContentView(binding.root)

// Akses view dengan type-safe
binding.btnCalculate.setOnClickListener { ... }
binding.tvResult.text = "..."
```

### 2. **ViewModel Initialization**

**Cara Lama:**
```kotlin
private lateinit var viewModel: MainViewModel
viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
```

**Cara Baru (Recommended):**
```kotlin
private val viewModel: MainViewModel by viewModels()
```

### 3. **Validasi Input**
```kotlin
when {
    length.isEmpty() -> { /* error */ }
    width.isEmpty() -> { /* error */ }
    height.isEmpty() -> { /* error */ }
    else -> { /* proses data */ }
}
```
Validasi dari bawah ke atas agar fokus diarahkan ke field kosong paling atas.

### 4. **Separation of Concerns**
- **Activity**: Handle UI dan user interaction
- **ViewModel**: Handle business logic dan data

## 🧪 Testing

### Manual Testing - Configuration Change:
1. Jalankan aplikasi
2. Masukkan nilai (contoh: 5, 10, 3)
3. Klik tombol "Calculate"
4. Lihat hasilnya (150)
5. **Rotasi layar** (Ctrl+F11 di emulator)
6. ✅ Hasil tetap 150 (tidak hilang!)

## 📚 Referensi

- [Android ViewModel Documentation](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [View Binding Documentation](https://developer.android.com/topic/libraries/view-binding)
- [Android Architecture Components](https://developer.android.com/topic/architecture)

## 👨‍💻 Catatan Developer

Proyek ini adalah implementasi dasar dari ViewModel. Untuk aplikasi production, pertimbangkan:

- **LiveData/StateFlow**: Untuk observable data
- **Repository Pattern**: Untuk data layer
- **Dependency Injection**: Menggunakan Hilt/Koin
- **Error Handling**: Try-catch untuk konversi String ke Int
- **Unit Testing**: Test untuk ViewModel logic

## 📄 Lisensi

Proyek pembelajaran ini dibuat untuk tujuan edukasi.

---

**Happy Learning! 🎉**

Jika ada pertanyaan atau ingin mengembangkan fitur lebih lanjut, jangan ragu untuk eksplorasi dan bereksperimen dengan kode ini!
