package com.trixtaro.justclicktest.JobTest;

import com.trixtaro.justclicktest.JobTest.configuration.GeneralConfiguration;
import com.trixtaro.justclicktest.JobTest.utilities.JsonUtilities;
import eu.bitwalker.useragentutils.UserAgent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

@Configuration
@SpringBootApplication
public class JobTestApplication {

	private static final int TIMER_INTERVAL = 60 * 60 * 1000; // 1 hour

	public static void main(String[] args) {
		SpringApplication.run(JobTestApplication.class, args);

		Calendar date = Calendar.getInstance();

		date.add(Calendar.HOUR, 1);
		date.set(Calendar.MINUTE, 5);
		date.set(Calendar.SECOND, 0);

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {

				JSONArray jsonArray = JsonUtilities.readJsonFile("click.json");
				JSONArray elasticSearchArray = new JSONArray();

				JSONObject jsonObject;

				for(int i = 0; i < jsonArray.size(); i++) {

					jsonObject = new JSONObject();

					UserAgent userAgent = UserAgent.parseUserAgentString(((JSONObject) jsonArray.get(i))
							.get("user-agent").toString());

					jsonObject.put("Browser", userAgent.getBrowser().toString());
					jsonObject.put("Browser Version", userAgent.getBrowserVersion().toString());
					jsonObject.put("Operation Sy Version", userAgent.getBrowserVersion().toString());
					jsonObject.put("Browser Version", userAgent.getBrowserVersion().toString());

					elasticSearchArray.add(jsonObject);

				}

				try {
					// Making POST request to send data to Elastic Search Instance
					URL url  = new URL("http://localhost:9200/link/_doc");
					HttpURLConnection con = (HttpURLConnection)url.openConnection();

					con.setRequestMethod("POST");
					con.setRequestProperty("Content-Type", "application/json; utf-8");
					con.setRequestProperty("Accept", "application/json");
					con.setDoOutput(true);

					String elasticSearchArrayString = "{\"data\":"+elasticSearchArray.toJSONString()+"}";

					OutputStream os = con.getOutputStream();
					byte[] input = elasticSearchArrayString.getBytes("utf-8");
					os.write(input, 0, input.length);

					try(BufferedReader br = new BufferedReader(
						new InputStreamReader(con.getInputStream(), "utf-8"))) {
						StringBuilder response = new StringBuilder();
						String responseLine = null;
						while ((responseLine = br.readLine()) != null) {
							response.append(responseLine.trim());
						}
						System.out.println(response.toString());
					}

				} catch (Exception ex){
					System.out.println(ex);
				}

			}

		}, date.getTime(), TIMER_INTERVAL);
	}

}
