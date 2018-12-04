package com.indream.casandra.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class FileObjectServiceImpl implements FIleObjectService {

	@Override
	public List<?> displayObject(InputStream stream) {

		InputStreamReader inputStreamReader = new InputStreamReader(stream);
		BufferedReader br = new BufferedReader(inputStreamReader);

		try {
			String metaData = br.readLine();
			String[] dataSample = br.readLine().split("(?!,\\s),");
			Map<String, Object> map = new HashMap<String, Object>();

			
			
			
			
		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;
	}

}
