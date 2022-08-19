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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.openhab.binding.icomforts30.internal.exceptions.iComfortS30ConnectionFailedException;
import org.openhab.core.io.net.http.TrustAllTrustManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the iComfortS30 SSL Utility to work with Lennox self signed certificate
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

public class iComfortS30SSLUtil {
    private final Logger logger = LoggerFactory.getLogger(iComfortS30SSLUtil.class);

    private final String iComfortLocalURL;
    private static final String BEGIN_CERT = "-----BEGIN CERTIFICATE-----";
    private static final String END_CERT = "-----END CERTIFICATE-----";

    /**
     * Constructor
     * 
     * @param iComfortLocalHost the ip address of the iComfort Thermostat
     */
    public iComfortS30SSLUtil(String iComfortLocalHost) {
        this.iComfortLocalURL = "https://" + iComfortLocalHost;
    }

    public SslContextFactory getSslContextFactory() throws iComfortS30ConnectionFailedException {

        // Host name verifier accepting all hosts
        HostnameVerifier trustAllHostnames = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true; // Just allow them all.
            }
        };

        // Geting certificate from iComfort Thermostat
        String pemCert = null;
        try {
            pemCert = getPEMCertificateFromServer(new URL(iComfortLocalURL));
            if (pemCert == null) {
                throw new Exception("null cert returned");
            }
        } catch (Exception e) {
            throw new iComfortS30ConnectionFailedException("Failed to get certificate from: " + iComfortLocalURL, e);
        }

        // Creating custom trust store
        KeyStore ks = null;
        if (!pemCert.isBlank() && pemCert.startsWith(BEGIN_CERT)) {
            try (InputStream certInputStream = new ByteArrayInputStream(pemCert.getBytes(StandardCharsets.UTF_8))) {
                X509Certificate trustedCert = (X509Certificate) CertificateFactory.getInstance("X.509")
                        .generateCertificate(certInputStream);
                ks = makeJavaKeyStore(trustedCert);
            } catch (Exception e) {
                throw new iComfortS30ConnectionFailedException("", e);
            }
        }

        else {
            throw new iComfortS30ConnectionFailedException("Failed to get certificate from: " + iComfortLocalURL);
        }

        TrustManagerFactory trustManagerFactory = null;
        try {
            // Create custom Trust Manager
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(ks);
        } catch (NoSuchAlgorithmException | KeyStoreException e) {
            throw new iComfortS30ConnectionFailedException("", e);
        }

        // Creating context factory
        SslContextFactory contextFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustManagerFactory.getTrustManagers(), null);
            contextFactory = new SslContextFactory.Client();
            contextFactory.setHostnameVerifier(trustAllHostnames);
            contextFactory.setSslContext(sc);
            contextFactory.setIncludeProtocols("TLSv1.2");
            contextFactory.setIncludeCipherSuites("TLS_RSA_WITH_AES_256_GCM_SHA384");
            contextFactory.setExcludeCipherSuites();
            contextFactory.setEndpointIdentificationAlgorithm(null);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new iComfortS30ConnectionFailedException("", e);
        }

        return contextFactory;
    }

    private KeyStore makeJavaKeyStore(X509Certificate trustedCert) {
        try {
            KeyStore ks = KeyStore.getInstance("pkcs12");
            ks.load(null, null);
            ks.setCertificateEntry("cert_0", trustedCert);

            return ks;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            throw new IllegalStateException(
                    "Can't create the internal keystore for certificate : " + trustedCert.toString(), e);
        }
    }

    private @Nullable String getPEMCertificateFromServer(URL url) throws CertificateException {
        HttpsURLConnection connection = null;
        try {
            TrustManager[] trustManagers = { TrustAllTrustManager.getInstance() };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManagers, new SecureRandom());
            connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(sslContext.getSocketFactory());
            connection.connect();

            Certificate[] certs = connection.getServerCertificates();

            byte[] bytes = ((X509Certificate) certs[0]).getEncoded();
            if (bytes.length != 0) {
                return BEGIN_CERT + "\r\n" + Base64.getEncoder().encodeToString(bytes) + "\r\n" + END_CERT;
            }
        } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
            logger.error("An unexpected exception occurred: ", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }
}
