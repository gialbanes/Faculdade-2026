# ContedoApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**coursesIdLessonsGet**](ContedoApi.md#coursesIdLessonsGet) | **GET** /courses/{id}/lessons | Listar lições de um curso |
| [**lessonsIdQuestionsGet**](ContedoApi.md#lessonsIdQuestionsGet) | **GET** /lessons/{id}/questions | Listar questões de uma lição |
| [**questionsIdGet**](ContedoApi.md#questionsIdGet) | **GET** /questions/{id} | Obter detalhes de uma questão |


<a id="coursesIdLessonsGet"></a>
# **coursesIdLessonsGet**
> List&lt;Lesson&gt; coursesIdLessonsGet(id)

Listar lições de um curso

Retorna todas as lições associadas a um ID de curso específico.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ContedoApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ContedoApi apiInstance = new ContedoApi(defaultClient);
    Integer id = 56; // Integer | ID do Curso
    try {
      List<Lesson> result = apiInstance.coursesIdLessonsGet(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContedoApi#coursesIdLessonsGet");
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
| **id** | **Integer**| ID do Curso | |

### Return type

[**List&lt;Lesson&gt;**](Lesson.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Operação realizada com sucesso |  -  |
| **400** | ID inválido fornecido |  -  |

<a id="lessonsIdQuestionsGet"></a>
# **lessonsIdQuestionsGet**
> List&lt;Question&gt; lessonsIdQuestionsGet(id)

Listar questões de uma lição

Retorna todas as questões associadas a um ID de lição específico.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ContedoApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ContedoApi apiInstance = new ContedoApi(defaultClient);
    Integer id = 56; // Integer | ID da Lição
    try {
      List<Question> result = apiInstance.lessonsIdQuestionsGet(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContedoApi#lessonsIdQuestionsGet");
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
| **id** | **Integer**| ID da Lição | |

### Return type

[**List&lt;Question&gt;**](Question.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Operação realizada com sucesso |  -  |
| **400** | ID inválido fornecido |  -  |

<a id="questionsIdGet"></a>
# **questionsIdGet**
> Question questionsIdGet(id)

Obter detalhes de uma questão

Retorna os detalhes de uma questão específica.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ContedoApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ContedoApi apiInstance = new ContedoApi(defaultClient);
    Integer id = 56; // Integer | ID da Questão
    try {
      Question result = apiInstance.questionsIdGet(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ContedoApi#questionsIdGet");
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
| **id** | **Integer**| ID da Questão | |

### Return type

[**Question**](Question.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Operação realizada com sucesso |  -  |
| **404** | Questão não encontrada |  -  |
| **400** | ID inválido fornecido |  -  |

