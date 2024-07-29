package com.FWRP.utils;

import java.io.FileInputStream;
import java.io.IOException;
import kong.unirest.HttpResponse;
import java.util.Properties;
import kong.unirest.Unirest;
import kong.unirest.json.JSONException;

import org.json.JSONObject;

import jakarta.servlet.ServletException;

public class Mail {
    private static String domainName;
    private static String apiKey;

    static {
        System.out.println("Current Working Directory: " + System.getProperty("user.dir"));
        // try (FileInputStream input = new
        // FileInputStream("/etc/myapp/mailgun.properties")) {
        try (FileInputStream input = new FileInputStream("./mailgun.properties")) {
            // try (FileInputStream input = new FileInputStream("/opt/tomcat/mailgun.properties")) {


            Properties properties = new Properties();
            properties.load(input);
            domainName = properties.getProperty("MAILGUN_DOMAIN_NAME");
            apiKey = properties.getProperty("MAILGUN_API_KEY");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendVerificationEmail(String email, String verificationLink) throws ServletException {
        HttpResponse<String> response = Unirest.post("https://api.mailgun.net/v3/" + domainName + "/messages")
                .basicAuth("api", apiKey)
                .field("from", "Food Management System <noreply@" + domainName + ">")
                .field("to", email)
                .field("subject", "Email Verification")
                .field("text", "Please verify your email address by clicking the link below." +
                        "\n" + verificationLink)
                .asString();

        // Check the status code and response body
        int statusCode = response.getStatus();
        String responseBody = response.getBody();

        if (statusCode != 200) {
            // Log the status code and response body for debugging
            System.err.println("Mailgun API request failed with status code: " + statusCode);
            System.err.println("Response body: " + responseBody);
            throw new ServletException("Error sending verification email. Status code: " + statusCode);
        }

        // Attempt to parse the response body as JSON
        try {
            JSONObject responseObject = new JSONObject(responseBody);
            // Process the JSON response if needed
        } catch (JSONException e) {
            // Log and handle JSON parsing error
            System.err.println("Failed to parse Mailgun API response as JSON.");
            System.err.println("Response body: " + responseBody);
            throw new ServletException("Error parsing verification email response.", e);
        }

    }
}
