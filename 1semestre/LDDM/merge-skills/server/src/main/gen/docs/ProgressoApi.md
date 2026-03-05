# ProgressoApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**progressHistoryUserIdGet**](ProgressoApi.md#progressHistoryUserIdGet) | **GET** /progress/history/{userId} | Obter histórico do usuário |
| [**progressResetPost**](ProgressoApi.md#progressResetPost) | **POST** /progress/reset | Reiniciar progresso |
| [**progressSubmitPost**](ProgressoApi.md#progressSubmitPost) | **POST** /progress/submit | Enviar resposta |


<a id="progressHistoryUserIdGet"></a>
# **progressHistoryUserIdGet**
> ProgressHistoryUserIdGet200Response progressHistoryUserIdGet(userId)

Obter histórico do usuário

Retorna uma lista de IDs de lições completadas por um usuário.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProgressoApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ProgressoApi apiInstance = new ProgressoApi(defaultClient);
    Integer userId = 56; // Integer | ID do Usuário
    try {
      ProgressHistoryUserIdGet200Response result = apiInstance.progressHistoryUserIdGet(userId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProgressoApi#progressHistoryUserIdGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **userId** | **Integer**| ID do Usuário | |

### Return type

[**ProgressHistoryUserIdGet200Response**](ProgressHistoryUserIdGet200Response.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Operação realizada com sucesso |  -  |
| **400** | ID de usuário inválido fornecido |  -  |

<a id="progressResetPost"></a>
# **progressResetPost**
> ProgressResetPost200Response progressResetPost(resetProgressRequest)

Reiniciar progresso

Reinicia o progresso de uma lição específica para um usuário.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProgressoApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ProgressoApi apiInstance = new ProgressoApi(defaultClient);
    ResetProgressRequest resetProgressRequest = new ResetProgressRequest(); // ResetProgressRequest | 
    try {
      ProgressResetPost200Response result = apiInstance.progressResetPost(resetProgressRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProgressoApi#progressResetPost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **resetProgressRequest** | [**ResetProgressRequest**](ResetProgressRequest.md)|  | |

### Return type

[**ProgressResetPost200Response**](ProgressResetPost200Response.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Progresso reiniciado com sucesso |  -  |

<a id="progressSubmitPost"></a>
# **progressSubmitPost**
> SubmitAnswerResponse progressSubmitPost(submitAnswerRequest)

Enviar resposta

Envia uma resposta para uma questão e registra o progresso.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProgressoApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ProgressoApi apiInstance = new ProgressoApi(defaultClient);
    SubmitAnswerRequest submitAnswerRequest = new SubmitAnswerRequest(); // SubmitAnswerRequest | 
    try {
      SubmitAnswerResponse result = apiInstance.progressSubmitPost(submitAnswerRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProgressoApi#progressSubmitPost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **submitAnswerRequest** | [**SubmitAnswerRequest**](SubmitAnswerRequest.md)|  | |

### Return type

[**SubmitAnswerResponse**](SubmitAnswerResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Resposta enviada com sucesso |  -  |
| **404** | Questão não encontrada |  -  |

