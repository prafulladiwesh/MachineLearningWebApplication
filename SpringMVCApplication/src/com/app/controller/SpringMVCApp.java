package com.app.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.script.ScriptException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * author: Prafulla Diwesh
 * version: 1.0
 * 
 */

@Controller
public class SpringMVCApp {
	
	String flaskUrl = "http://127.0.0.1:5000/predict/";

	@RequestMapping("/home")
	public ModelAndView uploadHome() {
		System.out.println("Inside Home");
		return new ModelAndView("home", "string", new String());
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String readCsv(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file, ModelMap modelMap) throws IOException, ScriptException {
		
		// Create new connection with Flask server
		HttpURLConnection connection = null;
		List<String> result = new ArrayList<String>();

		// Read data from webpage
		byte[] bytes = file.getBytes();
		String completeData = new String(bytes);
		String[] rows = completeData.split("\n");
		System.out.println("completeDataNew: " + completeData);

		// Converting String array to JSON
		Gson gson = new GsonBuilder().create();
		String jsonArray = gson.toJson(rows);
		System.out.println(jsonArray);

		try {
			// Flask server URL
			URL url = new URL(flaskUrl);
			String[] inputData = { "{\"x\": " + jsonArray + "}" };
			for (String input : inputData) {
				connection = flaskServerConnection(modelMap, result, url, input);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return "display";
	}

	/**
	 * Connect to flask server and return data in Json format
	 * 
	 * @param modelMap
	 * @param result
	 * @param url
	 * @param input
	 * @return
	 * @throws IOException
	 * @throws ProtocolException
	 */
	private HttpURLConnection flaskServerConnection(ModelMap modelMap, List<String> result, URL url, String input)
			throws IOException, ProtocolException {
		HttpURLConnection connection;
		DataOutputStream os;
		String output;
		byte[] postData = input.getBytes(StandardCharsets.UTF_8);
		connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("charset", "utf-8");
		connection.setRequestProperty("Content-Length", Integer.toString(input.length()));
		os = new DataOutputStream(connection.getOutputStream());
		os.write(postData);
		os.flush();
		if (connection.getResponseCode() != 200) {
			throw new RuntimeException("Error Code: " + connection.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
		while ((output = br.readLine()) != null) {
			result.add(output);
		}
		connection.disconnect();
		modelMap.addAttribute("result", result);
		return connection;
	}

}
