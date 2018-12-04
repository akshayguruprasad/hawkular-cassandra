package com.indream.casandra.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Util {

	public Map<String, Object> allocateDataType(String[] metaData, String[] data) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < data.length; i++) {
			if (data[i].startsWith("\"")) {// its a list

				String[] collectionData = data[i].substring(1, data[i].length() - 1).split(",");
				java.util.List<String> list = new ArrayList<String>();
				for (int j = 0; j < collectionData.length; j++) {

					list.add(collectionData[i].trim());

				}
				map.put(metaData[i], list);

			} else {

 Object type;
Comparable<?> type1 = findDataType(data[i]);
type=type1.getClass();
				try {
					System.out.println(type.getName());
					System.out.println(type.getClass());
					System.out.println(type.getPackage());
					System.out.println(type.getSigners());
					System.out.println(type.getSimpleName());
					Object x = type.newInstance();
				} catch (Exception e) {

					e.printStackTrace();
				}

			}

		}

		return null;

	}

	private Comparable<?> findDataType(String data) {

		if (data.matches("^[0-9]+$")) {
			System.out.println(data + " is of type int");
			return Integer.parseInt(data);
		} else if (data.matches("^[0-9]+(\\.)[0-9]+$")) {
			System.out.println(data + " is of type float");
			return Double.parseDouble(data);
		} else if (data.matches("(true|TRUE|True|false|FALSE|False)")) {
			System.out.println(data.concat(" boolean"));
			return Boolean.parseBoolean(data.toLowerCase());

		}
		System.out.println("string found |" + data + "|");
		return String.class;
	}

	private Class<?> getClass(String data) {

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
