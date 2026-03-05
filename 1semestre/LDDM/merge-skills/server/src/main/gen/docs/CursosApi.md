# CursosApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**coursesGet**](CursosApi.md#coursesGet) | **GET** /courses | Listar todos os cursos |


<a id="coursesGet"></a>
# **coursesGet**
> List&lt;Course&gt; coursesGet()

Listar todos os cursos

Retorna uma lista com todos os cursos disponíveis.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CursosApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    CursosApi apiInstance = new CursosApi(defaultClient);
    try {
      List<Course> result = apiInstance.coursesGet();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CursosApi#coursesGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;Course&gt;**](Course.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Operação realizada com sucesso |  -  |

