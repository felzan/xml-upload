# Getting Started

### Running locally

Run with Gradle
```
./gradlew bootRun
```
By default the port is **8080**

You need to have a running MongoDB locally with default port

### Running with docker compose

By default the port is **8888**
#### 1. Build app
```
./gradlew build
```


#### 2. Build docker image
```
docker build --tag=iapps:latest .
```


#### 3. Run docker-compose
```
docker-compose up
```
### Features

Upload XML file available at `POST v1/files`

A test XML file can be found at `/src/test/resources/xml`
```shell
curl --location --request POST 'http://localhost:8080/v1/files' \
--header 'Accept: application/xml' \
--form 'file=@"/path/to/file"'
```

Search files available at `GET v1/files`

Search all files
```shell
curl --location --request GET 'http://localhost:8080/v1/files' \
--header 'Accept: application/xml'
```

Search files with pagination using `page` and `size`
```shell
curl --location --request GET 'http://localhost:8080/v1/files?page=0&size=2' \
--header 'Accept: application/xml'
```

Search files with example of payload. By any of this properties `id` `createdAt` `filename` `newspaperName` `width` `height` `dpi`.

#### It will match any of the properties

By `id`
```shell
curl --location --request GET 'http://localhost:8080/v1/files' \
--header 'Accept: application/xml' \
--header 'Content-Type: application/xml' \
--data-raw '       
<content>
    <id>639276670f2d05116daba03c</id>
</content>'
```

By `filename`
```shell
curl --location --request GET 'http://localhost:8080/v1/files' \
--header 'Accept: application/xml' \
--header 'Content-Type: application/xml' \
--data-raw '       
<content>
    <filename>Request.xml</filename>
</content>'
```

By `width` and `dpi`
```shell
curl --location --request GET 'http://localhost:8080/v1/files' \
--header 'Accept: application/xml' \
--header 'Content-Type: application/xml' \
--data-raw '       
<content>
    <width>1280</width>
    <dpi>300</dpi>
</content>'
```