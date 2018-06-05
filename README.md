# Belajar microservice dengan SpringBoot

Berikut arsitektur aplikasi dengan menggunakan konsep microservice:

![konsep microservice](imgs/arsitketur-aplikasi.png)

## Flow microservice

1. Jika user belum login, maka browser jika mengakses _resource_ atau _service_ `registration` haruslah meminta _request token_ ke `auth server`. 
2. Setelah di _check username_ dan _password_ valid maka `auth server` akan mengembalikan sebuah _token_ yang dikonversi ke format _JWT (JSON Web Token)_ yang kemudian di kirimkan ke `browser`. 
3. Setelah mendapatkan `access_token` makan `browser` akan mengirimkan _request_ ke _service_ `registration`, kemudian _service_ melakukan operasi ke `database` dengan mengembalikan data json seperti berikut contohnya:

```json
{
    "id": 1,
    "projectName": "mandiri-mits",
    "projectOwenerId" : 1
}
```
Dari json tersebut terdapat _property_ `projectOwner.id` jika anda perhatikan itu objectnya tidak lengkap karena mungkin secara database memang satu database tapi secara entity tidak contoh implementasinya seperti berikut:

```java
// module registration

public class Registration{
    private Integer id;
    private String projectName;
    private Integer projectOwnerId;

    // setter & getter
}

public class User{
    private Integer id;
    private String email;

    // setter & getter
}
```

```java
// module resource management

public class User{
    private Integer id;
    private String email;
    private String password;
    private List<Role> roles;

    // setter & getter
}
```


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