# trackProsto
trackProsto adalah sebuah aplikasi berbasis Java yang memanfaatkan Spring Boot dan PostgreSQL untuk memantau proses transaksi dan gerakan stok produk daging. Aplikasi ini ditujukan untuk para Meat Retailer.

## Arsitektur Aplikasi

Aplikasi ini terdiri dari beberapa komponen utama, antara lain:

1. **Controller**: Bertindak sebagai penghubung antara pengguna dan sistem, berfungsi untuk menerima dan merespon permintaan pengguna.

2. **Exception**: Digunakan untuk menangani pengecualian atau kesalahan yang mungkin terjadi selama eksekusi program.

3. **Model**: Menyimpan semua model yang digunakan dalam aplikasi. Terdiri dari empat sub-komponen:
    - **DTO (Data Transfer Object)**: Objek yang digunakan untuk mengirim dan menerima data antara client dan server.
    - **Entity**: Mewakili tabel dalam database.
    - **Request**: Digunakan untuk menerima data masuk dari pengguna.
    - **Response**: Digunakan untuk mengirim data kembali ke pengguna.

4. **Repository**: Menyediakan mekanisme untuk penyimpanan, pengambilan, pencarian, update, dan penghapusan objek.

5. **Service**: Berisi logika bisnis aplikasi dan memanggil metode dari repository.


## Dependensi

Aplikasi ini menggunakan beberapa dependensi sebagai berikut:

1. **Spring Boot Starter Data JDBC**: Modul Spring Boot yang menyediakan fitur-fitur pendukung JDBC seperti pembuatan DataSource dan JdbcTemplate secara otomatis.

2. **Spring Boot Starter Data JPA**: Modul Spring Boot yang menyederhanakan penggunaan teknologi akses data JPA, termasuk pembuatan EntityManagerFactory dan TransactionManager.

3. **PostgreSQL JDBC Driver**: Driver yang memungkinkan aplikasi untuk terhubung ke database PostgreSQL.

4. **Lombok**: Library yang membantu otomatisasi boilerplate code seperti getter, setter, dan constructor.

5. **Spring Boot Starter Validation**: Starter untuk melakukan validasi data input dengan Hibernate Validator.

## NativeQuery

Contoh Penggunaan Native Query pada CompanyRepository

public interface CompanyRepository extends JpaRepository<Company, String> {

    List<Company> findByCompanyName(String companyName);
    @Query(value = "SELECT * FROM companies", nativeQuery = true)
    List<Company> findAll();

    @Query(value = "SELECT * FROM companies WHERE id = :id", nativeQuery = true)
    Optional<Company> findById(@Param("id") String id);

    @Query(value = "INSERT INTO companies (id, name, address) VALUES (:id, :name, :address)", nativeQuery = true)
    Company save(@Param("id") String id, @Param("name") String name, @Param("address") String address);

    @Query(value = "DELETE FROM companies WHERE id = :id", nativeQuery = true)
    void deleteById(@Param("id") String id);
}

