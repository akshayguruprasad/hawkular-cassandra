package com.indream.casandra.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import com.indream.casandra.model.Pet;

public class Util {

	public static Map<String, Object> allocateDataType(String[] metaData, String[] data) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < data.length; i++) {
			if (data[i].startsWith("\"")) {// its a list
				String[] collectionData = data[i].substring(1, data[i].length() - 1).split(",");
				List<String> list = Arrays.asList(collectionData);
				map.put(metaData[i], list);
			} else {
				map.put(metaData[i], data[i]);
			}
		}
		return map;
	}

	public static String dataTypeFinder(String data, String key) {
		if (data == null) {
			return key.concat(" ").concat(DataType.ASCII.name()).concat(" , ");
		}
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

	public static StringBuffer findAllDataTypes(Map<String, Object> mappedData) {
		StringBuffer subQuery = new StringBuffer();
		BiConsumer<? super String, ? super Object> action = (key, value) -> {
			String partQuery = null;
			String searchValue = value.toString();
			if (value instanceof List) {
				// its a complex element
				Object valueFromList = ((List) value).size() > 0 ? ((List) value).get(0) : null;

				partQuery = key.concat(" list<").concat("text").concat(">,");

			} else {

				partQuery = dataTypeFinder(searchValue, key);

			}

			subQuery.append(partQuery);
		};
		mappedData.forEach(action);
		return subQuery;

	}

	public static boolean validatePetData(Pet pet) {
		if (pet == null) {
			return false;
		}

		Field[] petFields = pet.getClass().getDeclaredFields();
		for (int i = 0; i < petFields.length; i++) {
			petFields[i].setAccessible(true);

			try {
				Object value = petFields[i].get(pet);
				if (value instanceof String) {
					String stringValue = (String) value;
					if (stringValue == null) {
						return false;
					}
					if (stringValue.trim().isEmpty()) {
						return false;
					}
					return true;

				}
				petFields[i].setAccessible(false);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return false;
	}

	public static Map<String, Object> insertPetDetailsQuery(Pet pet) {

		Field[] fields = pet.getClass().getDeclaredFields();

		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);

			try {
				map.put(fields[i].getName(), fields[i].get(pet));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return map;
	}

	public static String mapToInsertQuertString(Map<String, Object> mapInsert) {

		StringBuffer sb = new StringBuffer("Insert into demo.pets112 (");
		String keys = mapInsert.keySet().toString().substring(1, mapInsert.keySet().toString().length() - 1);
		sb.append(keys).append(" ) values (");

		String[] allKeys = keys.split(",");

		System.out.println();
		for (int i = 0; i < allKeys.length; i++) {
			System.out.println("|" + allKeys[i] + "|");
			System.out.println(mapInsert.get(allKeys[i]));

		}
		System.out.println();

		Iterator<String> it = mapInsert.keySet().iterator();

		while (it.hasNext()) {
			String string = (String) it.next();
			System.out.println("|" + string + "|");
		}

		for (int i = 0; i < allKeys.length; i++) {

			sb.append(mapInsert.get(allKeys[i].trim()) + " ,");

		}
		return sb.toString().substring(0, sb.toString().length() - 1).concat(")");

	}
}
