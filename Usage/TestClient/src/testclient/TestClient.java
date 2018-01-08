/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import javax.net.ssl.HttpsURLConnection;

public class TestClient {

    static {
        //for localhost testing only
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {

            @Override
            public boolean verify(String hostname,
                    javax.net.ssl.SSLSession sslSession) {
                return hostname.equals("localhost");
            }
        });
    }

    private static PrintStream originalStream = System.out;
    private static PrintStream noOutputStream = new PrintStream(new OutputStream() {
        @Override
        public void write(int b) {
        }
    });

    public static void main(String[] args) {

        // Set TrustStore file
        System.setProperty("javax.net.ssl.trustStore", "myTrustStore.jts");
        System.setProperty("javax.net.ssl.trustStorePassword", "rhsecretpassword");
        System.setProperty("javax.net.debug", "all");

        try {

            // Authenticated Request Example
            String url = "https://localhost:8443/accounts/all";
            String email = "aaa";
            String password = "aa";
            String reqResponse = sendAuthenicatedRequest(url, email, password, "GET");
            System.out.println("Response from '" + url + "':\n" + reqResponse);

            // Simple Request Example
            url = "https://localhost:8443/";
            reqResponse = sendSimpleRequest(url, "GET");
            System.out.println("Response from '" + url + "':\n" + reqResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String sendAuthenicatedRequest(String link, String email, String password, String requestMethod) throws MalformedURLException, IOException {

        System.setOut(noOutputStream); // Silence all outputs

        String authString = email + ":" + password;
        String authStringEncrypted = new String(Base64.getEncoder().encode(authString.getBytes()));

        // Send request
        URL obj = new URL(link);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod(requestMethod);
        con.setRequestProperty("User-Agent", "RH-Client");
        con.setRequestProperty("Authorization", "Basic " + authStringEncrypted);

        // Get request response
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.setOut(originalStream); // Desilence all outputs   

        // Print Response Code
        //int responseCode = con.getResponseCode();
        //System.out.println("Response Code : " + responseCode);
        return response.toString();

    }

    private static String sendSimpleRequest(String link, String requestMethod) throws MalformedURLException, IOException {

        System.setOut(noOutputStream); // Silence all outputs

        // Send request
        URL obj = new URL(link);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod(requestMethod);
        con.setRequestProperty("User-Agent", "RH-Client");

        // Get request response
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.setOut(originalStream); // Desilence all outputs   

        // Print Response Code
        //int responseCode = con.getResponseCode();
        //System.out.println("Response Code : " + responseCode);
        return response.toString();

    }

}
