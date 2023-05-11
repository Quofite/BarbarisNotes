package org.barbaris.notesandroid;

import android.content.Intent;

import com.google.gson.Gson;

import org.barbaris.notesandroid.models.APIDataModel;
import org.barbaris.notesandroid.models.UserModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class LoginRequest extends Thread {
    private final String login;
    private final String password;
    private MainActivity activity;

    public LoginRequest(String login, String password, MainActivity activity) {
        super();
        this.login = login;
        this.password = password;
        this.activity = activity;
    }

    @Override
    public void run() {
        this.request();
    }

    private void request() {
        try {
            URL url = new URL(APIDataModel.getHost() + "/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            UserModel user = new UserModel();
            user.setLogin(login);
            user.setPassword(password);
            Gson gson = new Gson();

            OutputStreamWriter sw = new OutputStreamWriter(connection.getOutputStream());
            sw.write(gson.toJson(user));
            sw.flush();
            sw.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            reader.close();

            int response = connection.getResponseCode();

            if (response == 302) {
                Intent intent = new Intent(activity, NotesActivity.class);
                intent.putExtra("login", login);
                intent.putExtra("password", password);
                activity.startActivity(intent);
            } else {
                System.out.println(response);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
