package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class XrayIntegration {

    public static void main(String[] args) throws Exception {
        // Jira Xray API'ye test sonuçlarını aktarmak için gerekli bilgiler
        String xrayUrl = "https://myprojecthepsiburada.atlassian.net/rest/raven/1.0/import/execution/cucumber";
        String apiKey = "your-api-key"; // Jira API Anahtarı
        String testExecutionKey = "SUP-6"; // Jira'daki test yürütme anahtarı
        String cucumberJsonPath = "path/to/cucumber.json"; // Cucumber sonuçlarının dosya yolu

        // Cucumber sonuçlarını oku
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                XrayIntegration.class.getClassLoader().getResourceAsStream(cucumberJsonPath)));
        StringBuilder cucumberJson = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            cucumberJson.append(line);
        }
        reader.close();

        // Test sonuçlarını Jira Xray'e gönder
        sendResultsToXray(xrayUrl, apiKey, testExecutionKey, cucumberJson.toString());
    }

    private static void sendResultsToXray(String xrayUrl, String apiKey, String testExecutionKey, String cucumberJson)
            throws Exception {
        // Jira API Anahtarını Base64 kodla
        String encodedApiKey = Base64.getEncoder().encodeToString(apiKey.getBytes());

        URL url = new URL(xrayUrl + "?testExecKey=" + testExecutionKey);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Basic " + encodedApiKey);
        connection.setDoOutput(true);

        // Cucumber sonuçlarını JSON olarak gönder
        connection.getOutputStream().write(cucumberJson.getBytes());

        // Yanıtı oku
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Yanıtı yazdır
        System.out.println(response.toString());
    }
}
