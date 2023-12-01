package com.qa.gorest.client;

import java.util.Map;
import java.util.Properties;
import static io.restassured.RestAssured.given;
import com.qa.gorest.frameworkexception.APIFrameworkException;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {

//	private static final String BASE_URI = "https://gorest.co.in";
//	private static final String BEARER_TOKEN = "05502a65605585e29e7624dea1ec3a9004c4843ba77d618330bceb69e28499e1";

//	private static RequestSpecBuilder specBuilder;

	private RequestSpecBuilder specBuilder;
	private Properties prop;
	private String baseURI;

	private boolean isAuthorizationHeaderAdded = false;

	/*
	 * static { specBuilder = new RequestSpecBuilder(); }
	 */

	public RestClient(Properties prop, String baseURI) {
		specBuilder = new RequestSpecBuilder();
		this.prop = prop;
		this.baseURI = baseURI;
	}

	public void addAuthroizationHeader() {
		if (!isAuthorizationHeaderAdded) {
			specBuilder.addHeader("Authorization", "Bearer " + prop.getProperty("tokenId"));
			isAuthorizationHeaderAdded = true;
		}
	}

	private void setRequestContentType(String contentType) {
		switch (contentType.toLowerCase()) {
		case "json":
			specBuilder.setContentType(ContentType.JSON);
			break;
		case "xml":
			specBuilder.setContentType(ContentType.XML);
			break;
		case "text":
			specBuilder.setContentType(ContentType.TEXT);
			break;
		case "multipart":
			specBuilder.setContentType(ContentType.MULTIPART);
			break;
		default:
			System.out.println("Please pass the valid content type");
			throw new APIFrameworkException("INVALIDCONTENTTYPE");
		}
	}

	private RequestSpecification createRequestSpec(boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);

		if (includeAuth) {
			addAuthroizationHeader();
		}
		return specBuilder.build();
	}

	private RequestSpecification createRequestSpec(Map<String, String> headersMap, boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if (includeAuth) {
			addAuthroizationHeader();
		}
		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		return specBuilder.build();
	}

	private RequestSpecification createRequestSpec(Map<String, String> headersMap, Map<String, Object> queryParams,
			boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if (includeAuth) {
			addAuthroizationHeader();
		}
		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		if (queryParams != null) {
			specBuilder.addQueryParams(queryParams);
		}
		return specBuilder.build();
	}

	private RequestSpecification createRequestSpec(Object requestBody, String contentType, boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if (includeAuth) {
			addAuthroizationHeader();
		}
		if (requestBody != null) {
			specBuilder.setBody(requestBody);
		}
		setRequestContentType(contentType);
		return specBuilder.build();
	}

	private RequestSpecification createRequestSpec(Object requestBody, String contentType,
			Map<String, String> headersMap, boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if (includeAuth) {
			addAuthroizationHeader();
		}
		setRequestContentType(contentType);
		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		if (requestBody != null) {
			specBuilder.setBody(requestBody);
		}
		return specBuilder.build();
	}

	// Http methods

	public Response get(String serviceUrl, boolean includeAuth, boolean log) {
		if (log) {
			return RestAssured.given(createRequestSpec(includeAuth)).log().all().when().get(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(includeAuth)).when().get(serviceUrl);
	}

	public Response get(String serviceUrl, Map<String, String> headerMaps, boolean includeAuth, boolean log) {
		if (log) {
			return RestAssured.given(createRequestSpec(headerMaps, includeAuth)).log().all().when().get(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(headerMaps, includeAuth)).when().get(serviceUrl);
	}

	public Response get(String serviceUrl, Map<String, String> headerMaps, Map<String, Object> queryParam,
			boolean includeAuth, boolean log) {
		if (log) {
			return RestAssured.given(createRequestSpec(headerMaps, queryParam, includeAuth)).log().all().when()
					.get(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(headerMaps, queryParam, includeAuth)).when().get(serviceUrl);
	}

	public Response post(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, boolean log) {
		if (log) {
			return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all().when()
					.post(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).when().post(serviceUrl);
	}

	public Response post(String serviceUrl, String contentType, Object requestBody, Map<String, String> headerMaps,
			boolean includeAuth, boolean log) {
		if (log) {
			return RestAssured.given(createRequestSpec(requestBody, contentType, headerMaps, includeAuth)).log().all()
					.when().post(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, headerMaps, includeAuth)).when()
				.post(serviceUrl);
	}

	public Response put(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, boolean log) {
		if (log) {
			return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all().when()
					.put(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).when().put(serviceUrl);
	}

	public Response put(String serviceUrl, String contentType, Object requestBody, Map<String, String> headerMaps,
			boolean includeAuth, boolean log) {
		if (log) {
			return RestAssured.given(createRequestSpec(requestBody, contentType, headerMaps, includeAuth)).log().all()
					.when().put(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, headerMaps, includeAuth)).when()
				.put(serviceUrl);
	}

	public Response patch(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, boolean log) {
		if (log) {
			return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all().when()
					.patch(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).when().patch(serviceUrl);
	}

	public Response patch(String serviceUrl, String contentType, Object requestBody, Map<String, String> headerMaps,
			boolean includeAuth, boolean log) {
		if (log) {
			return RestAssured.given(createRequestSpec(requestBody, contentType, headerMaps, includeAuth)).log().all()
					.when().patch(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, headerMaps, includeAuth)).when()
				.patch(serviceUrl);
	}

	public Response delete(String serviceUrl, boolean includeAuth, boolean log) {
		if (log) {
			return RestAssured.given(createRequestSpec(includeAuth)).log().all().when().delete(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(includeAuth)).when().delete(serviceUrl);
	}

	public String getAccessToken(String serviceURL, String grantType, String clientId, String clientSecret) {
		// 1. POST - get the access token
		RestAssured.baseURI = "https://test.api.amadeus.com";

		String accessToken = given().log().all().contentType(ContentType.URLENC).formParam("grant_type", grantType)
				.formParam("client_id", clientId).formParam("client_secret", clientSecret).when().post(serviceURL)
				.then().log().all().assertThat().statusCode(200).extract().path("access_token");

		System.out.println("access token: " + accessToken);
		return accessToken;

	}

}
