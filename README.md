# up42 interstellar backend challenge
## Requirements
This project is not tracked via ticket system. All acceptance criteria can be found in [UP42_backend_challenge
.pdf](UP42_backend_challenge.pdf)

Technical:
- java 8 as foundation for kotlin 1.3.x
- gradle (versioned via ./gradlew gradle wrapper)

## start the application
as a spring boot application you start the main of `com.up42.interstellar.InterstellarApplication` or run

```shell script
gradle bootRun
```

## testing
```shell script
gradle clean test
```

## problems and assumptions
- the `source-data.json` is given once and changed with little frequency. Therefore we load the content into memory
. This is most likely not suitable for production environment.
- considering the missing schema description we trust the `source-data.json` and assume a few unclean traits such as
    - ever `id` is unique
    - `uid` is ignored in favour of `id`
    - `timestamp` and `timeStamp` are the same
    - format of timstamp (assumed to be epoch ms)
    - /features returns an array. this is not compliant to best practice
- as loading the json is lazy it will slow down the first request, worsening p99 performance metrics. Fair enough for
 an assessment, for production we are interested in a different setup
 
 This setup contains a github actions pipeline, pull request and contribution template. On a production service I'd
  add a openapi description as well.