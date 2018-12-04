package com.indream.casandra.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.datastax.driver.core.Session;
import com.indream.casandra.service.FIleObjectService;
import com.indream.casandra.util.DataType;

@RestController
public class Controller {
	@Autowired
	private FIleObjectService service;

	@Autowired
	private Session session;
	final static private String TABLE_NAME = "demo.pets";

	@PostMapping("/upload")
	public String dataAnalysis(@RequestParam("file") MultipartFile file) throws IOException {

		List<?> x = service.displayObject(file.getInputStream());

		return "success";
	}

	private Map<String, ?> getAttributes(InputStream inputStream) throws IOException {
		InputStreamReader reader = new InputStreamReader(inputStream);
		BufferedReader buffReader = new BufferedReader(reader);
		String metaData = buffReader.readLine();
		String sampleData = buffReader.readLine();
		String[] headers = metaData.split(",");
		String[] sampleDataSet = sampleData.split("\"");
		Object[] mainSampledata = new Object[headers.length];
		int tracker = 0;

		for (int i = 0; i < sampleDataSet.length; i++) {
			if (sampleDataSet[i].startsWith(",") || sampleDataSet[i].endsWith(",")) {

				// it belongs to single data
				sampleDataSet[i].replace(",", "");
				mainSampledata[tracker++] = sampleDataSet[i].trim();
			} else {
				// belongs to a collection

				String[] collectionSample = sampleDataSet[i].split(",");

				mainSampledata[tracker++] = Arrays.asList(collectionSample);

			}

		}

		return mapDataToAttr(mainSampledata, headers);

	}

	private Map<String, ?> mapDataToAttr(Object[] mainSampledata, String[] headers) {

		if (mainSampledata.length != headers.length) {
			throw new RuntimeException("invalid operation failed to parse the csv");
		}

		Map map = new HashMap<>();
		for (int i = 0; i < headers.length; i++) {

			map.put(headers[i].toString().trim(), mainSampledata[i]);

		}

		return null;
	}

	private Object fineTuneData(String sample) {
		if (sample.startsWith("\"")) {
			// further break it down
			return Arrays.asList(sample.substring(1, sample.length() - 1).split(","));
		} else {
			return Arrays.asList(sample.split(","));
		}

	}

	private void createTable(Map<String, String> attributesWithSample, String primaryKey) {
		StringBuffer subQuery = new StringBuffer();
		BiConsumer<String, String> queryBuilder = (key, value) -> {
			System.out.println("|" + value + "|" + " |" + key + "|");
			subQuery.append(dataTypeFinder(value, key));
		};
		attributesWithSample.forEach(queryBuilder);
		String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
				+ subQuery.toString().substring(0, subQuery.length() - 1) + " , PRIMARY KEY (" + primaryKey + "));";
		System.out.println(query);
		session.execute(query);
	}

	private String dataTypeFinder(String data, String key) {

		if (data.matches("^[0-9]+$")) {
			System.out.println(data + " is of type int");
			return key.concat(" ").concat(DataType.INT.name()).concat(" , ");

		} else if (data.matches("^[0-9]+(\\.)[0-9]+$")) {
			System.out.println(data + " is of type float");
			return key.concat(" ").concat(DataType.FLOAT.name()).concat(" , ");

		} else if (data.matches("(true|TRUE|True|false|FALSE|False)")) {
			System.out.println(data.concat(" boolean"));
			return key.concat(" ").concat(DataType.BOOLEAN.name()).concat(" , ");
		}
		System.out.println("string found |" + data + "|");
		return key.concat(" ").concat(DataType.ASCII.name()).concat(" , ");
	}

}
