package com.trixtaro.justclicktest.JobTest;

import com.trixtaro.justclicktest.JobTest.utilities.JsonUtilities;
import eu.bitwalker.useragentutils.UserAgent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
public class JobTestApplication {

	private static final int TIMER_INTERVAL = 5 * 60 * 1000; // 5 minutes

	public static void main(String[] args) {
		SpringApplication.run(JobTestApplication.class, args);

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {

				JSONArray jsonArray = JsonUtilities.readJsonFile("click.json");
				JSONArray elasticSearchArray = new JSONArray();

				JSONObject jsonObject = new JSONObject();

				for(int i = 0; i < jsonArray.size(); i++) {

					UserAgent userAgent = UserAgent.parseUserAgentString(((JSONObject) jsonArray.get(i))
							.get("user-agent").toString());

					System.out.println("Browser: " + userAgent.getBrowser() + " Version: " + userAgent.getBrowserVersion());
					System.out.println("OS: " + userAgent.getOperatingSystem());
					System.out.println("OS version: " + ((JSONObject) jsonArray.get(i))
							.get("user-agent").toString().split(" ")[3].replace(";",""));

					jsonObject.put("Browser", userAgent.getBrowser());
					jsonObject.put("Browser Version", userAgent.getBrowserVersion());
					jsonObject.put("Operation Sy Version", userAgent.getBrowserVersion());
					jsonObject.put("Browser Version", userAgent.getBrowserVersion());

				}

			}

		}, 0, TIMER_INTERVAL);
	}

}
