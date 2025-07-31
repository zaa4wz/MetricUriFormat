# Project created for Issue [35264](https://github.com/spring-projects/spring-framework/issues/35264)

To show how URI formatting exposed by `http.client.requests` metric looks, run the project and read the standard output.

Output from Standard Output:
```
RestTemplate
Registered URIs:
/hello-word
/hello-word-variable/{id}
/hello-word-param?id={id}

RestClient
Registered URIs:
/hello-word
/hello-word-variable/{id}
/hello-word-param?id={id}

HTTP Interface
Registered URIs:
/hello-word
/hello-word-variable/{id}
/hello-word-param?{queryParam-id}={queryParam-id[0]}
```
