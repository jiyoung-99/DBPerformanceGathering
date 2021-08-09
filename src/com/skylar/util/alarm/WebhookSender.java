package com.exem.util.alarm;

import com.exem.util.alarm.form.ConnectInfo;
import com.exem.util.alarm.form.JandiMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebhookSender implements Webhook {

    private String urlText;

    public WebhookSender(String urlText) {
        this.urlText = urlText;
    }

    @Override
    public boolean sendMessage(JandiMessage message) {

        try {
            URL url = new URL(urlText.toString());
            String param = "{\"body\" : \"" + message.getBody() + "\",\n" +
                    "\"connectColor\" : \"" + message.getConnectColor() + "\", \n" +
                    "\"connectInfo\" : [{\n" +
                    "                \"title\": \"" + message.getConnectInfo().getTitle() + "\",\n" +
                    "                \"description\": \"" + message.getConnectInfo().getDescription() + "\"\n" +
                    "                }]  \n" +
                    "}";
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/vnd.tosslab.jandi-v2+json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            byte[] input = param.getBytes("utf-8");
            os.write(input, 0, input.length);

//            try (BufferedReader br = new BufferedReader(
//                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
//                StringBuilder response = new StringBuilder();
//                String responseLine = null;
//                while ((responseLine = br.readLine()) != null) {
//                    response.append(responseLine.trim());
//                }
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }

    @Override
    public boolean sendMessage(String body, String connectColor, ConnectInfo connectInfos) {

        try {
            URL url = new URL(urlText.toString());
            String param = "{\"body\" : \"" + body + "\",\n" +
                    "\"connectColor\" : \"" + connectColor + "\", \n" +
                    "\"connectInfo\" : [{\n" +
                    "                \"title\": \"" + connectInfos.getTitle() + "\",\n" +
                    "                \"description\": \"" + connectInfos.getDescription() + "\"\n" +
                    "                }]  \n" +
                    "}";
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/vnd.tosslab.jandi-v2+json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            byte[] input = param.getBytes("utf-8");
            os.write(input, 0, input.length);

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }


}
