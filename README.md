# Belajar microservice dengan SpringBoot

Aplikasi microservice: service per database

Biasanya aplikasi microservice menggunakan konesep _one service per database_ dengan gambaran sebagai berikut:

![konsep microservice](imgs/konsep-microservice.png)

## Flow microservice as per database

1. `user agent (browser)` request akses **token** ke module `auth server`
2. `auth server` check ke database `user management` user dan password valid atau invalid
3. `auth server` response ke service `registratoin` dan mengirimkan `access_token` kemudian di simpan oleh `user agent (browser)`
4. `user agent (browser)` request api `registration` yang include data user dari `resource management` service
5. `registration service` membaca ke `registration database` kemudian mengirimkan request ke `resource management service` lalu service terserbut (`resource management`) membaca ke `resource management database`
6. setelah mendapatkan hasil kirim response ke `registration service` kemudian service tersebut mengirimkan response kembail ke `user agent (browser)`