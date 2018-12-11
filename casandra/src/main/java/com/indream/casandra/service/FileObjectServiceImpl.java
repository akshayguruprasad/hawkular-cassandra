package com.indream.casandra.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.Session;
import com.indream.casandra.model.Pet;
import com.indream.casandra.util.Util;

@Service
public class FileObjectServiceImpl implements FIleObjectService {

	@Autowired
	private Session session;

	final static private String TABLE_NAME = "demo.pets112";

	private String primary = "license_number";

	@Override
	public boolean createTable(InputStream stream) {
		StringBuffer subQuery = new StringBuffer();
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(stream);
			BufferedReader br = new BufferedReader(inputStreamReader);
			String metaData = br.readLine();
			String[] dataSample = br.readLine().split("(?!,\\s),");
			Map<String, Object> mappedData = Util.allocateDataType(metaData.split(","), dataSample);

			subQuery = Util.findAllDataTypes(mappedData);
			String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
					+ subQuery.toString().substring(0, subQuery.length() - 1) + "  PRIMARY KEY (" + primary + "));";
			System.out.println(query);
			session.execute(query);
			return true;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			throw new RuntimeException("invalid create table operation");
		}

	}

	@Override
	public boolean insertPets(List<Pet> allPets) {

		return false;
	}

	@Override
	public boolean insertPet(Pet pet) {

		try {
			boolean isValidPet = Util.validatePetData(pet) ? true : false;

			if (!isValidPet) {
				throw new RuntimeException("Invalid pet details");
			}
			Map<String, Object> mapInsert = Util.insertPetDetailsQuery(pet);

			String insertQuery = Util.mapToInsertQuertString(mapInsert);
			System.out.println(insertQuery);
			session.execute(insertQuery);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return false;
	}

	@Override
	public boolean updatePet(Pet pet, String licenseNumber) {
		// TODO Auto-generated method stub
		return false;
	}

}
