# Belajar microservice dengan SpringBoot

Aplikasi microservice

Biasanya aplikasi microservice menggunakan konesep _one service per database_ dengan gambaran sebagai berikut:

![konsep microservice](imgs/arsitketur-aplikasi.png)

## Flow microservice as per database

1. `user agent (browser)` request akses **token** ke module `auth server`
2. `auth server` check ke database `user management` user dan password valid atau invalid
3. `auth server` response ke service `registratoin` dan mengirimkan `access_token` kemudian di simpan oleh `user agent (browser)`
4. `user agent (browser)` request api `registration` yang include data user dari `resource management` service
5. `registration service` membaca ke `registration database` kemudian mengirimkan request ke `resource management service` lalu service terserbut (`resource management`) membaca ke `resource management database`
6. setelah mendapatkan hasil kirim response ke `registration service` kemudian service tersebut mengirimkan response kembail ke `user agent (browser)`

## System Requirement

- JDK 1.8
- maven
- MySQL database server 5.7
- docker (optional)

## Run application

- setup MySQL database
    - on docker : `docker-compose up`
    - manualy : 
        - mysql version 5.7
            - user: root
            - password: root
            - database registration
            - port: 3301
        - mysql version 5.7
            - user: root
            - password: root
            - database: usermanagement
            - port: 3302
- install dependecy: `mvn clean install`
    - `authserver-oauth2` module
    - `service-resource-management` module
    - `service-registration` module
- run as spring-boot: `mvn clean spring-boot:run`
    - `authserver-oauth2` module
    - `service-resource-management` module
    - `service-registration` module

## Test example

1. login / minta token

```bash 
curl -X POST -vu example_client:example_password http://localhost:10000/login/oauth/token -H "Accept: application/json" -d "client_id=example_client&grant_type=password&username=dimmaryanto@gmail.com&password=password"

Note: Unnecessary use of -X or --request, POST is already inferred.
*   Trying ::1...
* TCP_NODELAY set
* Connected to localhost (::1) port 10000 (#0)
* Server auth using Basic with user 'example_client'
> POST /login/oauth/token HTTP/1.1
> Host: localhost:10000
> Authorization: Basic ZXhhbXBsZV9jbGllbnQ6ZXhhbXBsZV9wYXNzd29yZA==
> User-Agent: curl/7.54.0
> Accept: application/json
> Content-Length: 93
> Content-Type: application/x-www-form-urlencoded
>
* upload completely sent off: 93 out of 93 bytes
< HTTP/1.1 200
< Cache-Control: no-store
< Pragma: no-cache
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 1; mode=block
< X-Frame-Options: DENY
< Content-Type: application/json;charset=UTF-8
< Transfer-Encoding: chunked
< Date: Sun, 03 Jun 2018 23:37:07 GMT
<
* Connection #0 to host localhost left intact
{"access_token":"57da7983-41fb-4be2-8fc9-8b86ba7d5463","token_type":"bearer","refresh_token":"1f41dea5-6e7c-49db-b357-b3b226f80a2a","expires_in":43199,"scope":"read write trust"}➜  springboot-microservice-example git:(master) ✗
```

2. Request ke `registration service` dengan mengirimkan ke data sebagai berikut beserta `access_token` yang didapatkan dari request token sebelumnya:

```bash
 curl -H "Authorization: Bearer 57da7983-41fb-4be2-8fc9-8b86ba7d5463" http://localhost:11000/registration/api/registration/general

{
    "id":1,
    "user":{   
        "id":1,
        "email":"dimmaryanto@gmail",
        "password":"password"
    },
    "createdDate":"2018-10-10"
}
```