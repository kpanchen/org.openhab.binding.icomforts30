/**
 * Copyright (c) 2010-2022 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.openhab.binding.icomforts30.internal.api;

//ToDo Remove
// import java.io.BufferedWriter;
// import java.io.FileWriter;
// import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpResponseException;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpStatus;
import org.openhab.binding.icomforts30.internal.api.models.response.GsonDateDeserializer;
import org.openhab.binding.icomforts30.internal.api.models.response.GsonDurationDeserializer;
import org.openhab.binding.icomforts30.internal.api.models.response.GsonIntegerDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Provides access to (an optionally OAUTH based) API. Makes sure that all the necessary headers are set.
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

@NonNullByDefault
public class ApiAccess {
    private static final int REQUEST_TIMEOUT_MILLISECONDS = 5000;
    private int RequestTimeoutInMilliseconds = REQUEST_TIMEOUT_MILLISECONDS;
    private final Logger logger = LoggerFactory.getLogger(ApiAccess.class);
    private final HttpClient httpClient;
    private @Nullable String userCredentials = null;
    private final Gson gson;
    // May need to update as the version of API increases
    private final String USER_AGENT = "lx_ic3_mobile_appstore/3.75.218 (iPad; iOS 14.4.1; Scale/2.00)";

    public ApiAccess(HttpClient httpClient) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new GsonDateDeserializer());
        gsonBuilder.registerTypeAdapter(Integer.class, new GsonIntegerDeserializer());
        gsonBuilder.registerTypeAdapter(Duration.class, new GsonDurationDeserializer());
        gsonBuilder.serializeNulls(); // To accomodate null values
        this.gson = gsonBuilder.create();
        this.httpClient = httpClient;
    }

    public void setUserCredentials(@Nullable String userCredentials) {
        this.userCredentials = userCredentials;
    }

    public @Nullable String getUserCredentials() {
        return userCredentials;
    }

    /**
     * Set the connect and read timeout for HTTP requests.
     *
     * @param timeout timeout in milliseconds or 0 for indefinitely
     */
    public void setTimeout(int timeout) {
        RequestTimeoutInMilliseconds = timeout;
    }

    /**
     * Issues an HTTP request on the API's URL. Makes sure that the request is correctly formatted.
     *
     * @param method The HTTP method to use (POST, GET, ...)
     * @param url The URL to query
     * @param headers The optional additional headers to apply, can be null
     * @param requestData The optional request data to use, can be null
     * @param contentType The content type to use with the request data. Required when using requestData
     * @return The result of the request or null
     * @throws TimeoutException Thrown when a request times out
     */
    public @Nullable <TOut> TOut doRequest(HttpMethod method, String url, @Nullable Map<String, String> headers,
            @Nullable String requestData, String contentType, @Nullable Class<TOut> outClass) throws TimeoutException {

        @Nullable
        TOut retVal = null;
        logger.debug("Requesting: [{}]", url);

        try {
            Request request = httpClient.newRequest(url).method(method);

            request.agent(USER_AGENT);

            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    request.header(header.getKey(), header.getValue());
                }
            }

            if (requestData != null) {
                request.content(new StringContentProvider(requestData), contentType);
            }

            ContentResponse response = request.timeout(RequestTimeoutInMilliseconds, TimeUnit.MILLISECONDS).send();

            logger.debug("Response: {}", response);
            logger.debug("\n{}\n{}", response.getHeaders(), response.getContentAsString());

            if (outClass == Boolean.class) {
                if ((response.getStatus() == HttpStatus.NO_CONTENT_204)
                        || (response.getStatus() == HttpStatus.OK_200)) {
                    retVal = outClass.cast(true);
                } else {
                    retVal = outClass.cast(false);
                }
            } else if (outClass == String.class) {
                retVal = outClass.cast(response.getContentAsString());
            } else if (outClass != null) {
                if ((response.getStatus() == HttpStatus.OK_200) || (response.getStatus() == HttpStatus.ACCEPTED_202)) {
                    // For Testing
                    // ToDo Remove
                    // try {
                    // WritetoJSON(response.getContentAsString());
                    // } catch (Exception e) {
                    // }
                    retVal = this.gson.fromJson(response.getContentAsString(), outClass);
                }
            }
        } catch (HttpResponseException ex) {
            logger.debug("Error in handling request: {}, \nserver response was: {}", requestData, ex.getResponse());
        } catch (InterruptedException | ExecutionException ex) {
            logger.debug("Error in handling request: {}, \nexception was: {}", requestData, ex);
        }

        return retVal;
    }

    // Test code to kill
    // ToDo Remove
    // public void WritetoJSON(String sToWrite) throws IOException {

    // String fileName = "d:\\jtest.json";
    // BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
    // writer.append("\r\n----------New JSON-----------\r\n");
    // writer.append(sToWrite);
    // writer.close();
    // }

    /**
     * Issues an HTTP GET request on the API's URL, using an object that is serialized to JSON as input.
     * Makes sure that the request is correctly formatted.*
     *
     * @param url The URL to query
     * @param loginBearerToken Login Token for Cloud access (not used for local)
     * @param requestContainer The object to use as JSON data for the request
     * @param outClass The type of the requested result
     * @return The result of the request or null
     * @throws TimeoutException Thrown when a request times out
     */
    public @Nullable <TOut> TOut doAuthenticatedGet(String url, @Nullable String loginBearerToken,
            Object requestContainer, Class<TOut> outClass) throws TimeoutException {
        return doRequest(HttpMethod.GET, url, getHeaders(loginBearerToken), requestContainer, outClass);
    }

    /**
     * Issues an HTTP PUT request on the API's URL, using an object that is serialized to JSON as input.
     * Makes sure that the request is correctly formatted.*
     *
     * @param url The URL to query
     * @param requestContainer The object to use as JSON data for the request
     * @throws TimeoutException Thrown when a request times out
     */
    public void doAuthenticatedPut(String url, Object requestContainer) throws TimeoutException {
        doAuthenticatedRequest(HttpMethod.PUT, url, requestContainer, null);
    }

    /**
     * Issues an HTTP PUT request on the API's URL, using an object that is serialized to JSON as input.
     * Makes sure that the request is correctly formatted.*
     *
     * @param url The URL to query
     * @param requestContainer The object to use as JSON data for the request
     * @param outClass The type of the requested result
     * @throws TimeoutException Thrown when a request times out
     */
    public @Nullable <TOut> TOut doAuthenticatedPut(String url, Object requestContainer, Class<TOut> outClass)
            throws TimeoutException {
        return doAuthenticatedRequest(HttpMethod.PUT, url, requestContainer, outClass);
    }

    /**
     * Issues an HTTP POST request on the API's URL, using an object that is serialized to JSON as input.
     * Makes sure that the request is correctly formatted.*
     *
     * @param url The URL to query
     * @param loginBearerToken Login Token for Cloud access (not used for local)
     * @param requestContainer The object to use as JSON data for the request
     * @param outClass The type of the requested result
     * @throws TimeoutException Thrown when a request times out
     */
    public @Nullable <TOut> TOut doAuthenticatedPost(String url, @Nullable String loginBearerToken,
            Object requestContainer, Class<TOut> outClass) throws TimeoutException {
        return doRequest(HttpMethod.POST, url, getHeaders(loginBearerToken), requestContainer, outClass);
    }

    /**
     * Issues an HTTP POST request on the API's URL, using an object that is serialized to JSON as input.
     * Makes sure that the request is correctly formatted.*
     *
     * @param url The URL to query
     * @param requestContainer The object to use as JSON data for the request
     * @param outClass The type of the requested result
     * @throws TimeoutException Thrown when a request times out
     */
    public @Nullable <TOut> TOut doUnAuthenticatedPost(String url, @Nullable Object requestContainer,
            Class<TOut> outClass) throws TimeoutException {
        return doRequest(HttpMethod.POST, url, null, requestContainer, outClass);
    }

    /**
     * Issues an HTTP request on the API's URL, using an object that is serialized to JSON as input and
     * using the authentication applied to the type.
     * Makes sure that the request is correctly formatted.*
     *
     * @param method The HTTP method to use (POST, GET, ...)
     * @param url The URL to query
     * @param requestContainer The object to use as JSON data for the request
     * @param outClass The type of the requested result
     * @return The result of the request or null
     * @throws TimeoutException Thrown when a request times out
     */
    private @Nullable <TOut> TOut doAuthenticatedRequest(HttpMethod method, String url,
            @Nullable Object requestContainer, @Nullable Class<TOut> outClass) throws TimeoutException {
        Map<String, String> headers = null;
        String lUserCredentials = userCredentials;
        if (lUserCredentials != null) {
            headers = new HashMap<String, String>();
            headers.put("Authorization", lUserCredentials);
            headers.put("Accept",
                    "application/json, application/xml, text/json, text/x-json, text/javascript, text/xml");
            headers.put("Accept-Charset", "utf-8");
        }

        return doRequest(method, url, headers, requestContainer, outClass);
    }

    /**
     * Issues an HTTP request on the API's URL, using an object that is serialized to JSON as input.
     * Makes sure that the request is correctly formatted.*
     *
     * @param method The HTTP method to use (POST, GET, ...)
     * @param url The URL to query
     * @param headers The optional additional headers to apply, can be null
     * @param requestContainer The object to use as JSON data for the request
     * @param outClass The type of the requested result
     * @return The result of the request or null
     * @throws TimeoutException Thrown when a request times out
     */
    private @Nullable <TOut> TOut doRequest(HttpMethod method, String url, @Nullable Map<String, String> headers,
            @Nullable Object requestContainer, @Nullable Class<TOut> outClass) throws TimeoutException {

        String json = null;
        if (requestContainer != null) {
            json = this.gson.toJson(requestContainer);
        }

        return doRequest(method, url, headers, json, "application/json", outClass);
    }

    private Map<String, String> getHeaders(@Nullable String loginBearerToken) {
        Map<String, String> headers = new HashMap<>();

        if (loginBearerToken != null) {
            headers.put("Authorization", loginBearerToken);
        }

        headers.put("Content-Type", "application/json; charset=utf-8");
        // headers.put("User-Agent", USER_AGENT);
        headers.put("Accept", "*/*");
        headers.put("Accept-Language", "en-US;q=1");
        headers.put("Accept-Encoding", "gzip, deflate");

        return headers;
    }
}
