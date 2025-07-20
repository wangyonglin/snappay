# 使用WebFlow发送https请求


```
java的WebClient请求中通过SslContext和InsecureTrustManagerFactory禁用安全证书检查，以实现对HTTPS请求的支持
SslContext sslContext = SslContextBuilder
    .forClient()
    .trustManager(InsecureTrustManagerFactory.INSTANCE)
    .build();
			
HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));

WebClient webClient = WebClient.builder().baseUrl(baseUrl)
    .clientConnector(new ReactorClientHttpConnector(httpClient))
    .build();
```

