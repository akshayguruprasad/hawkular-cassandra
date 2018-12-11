package com.indream.casandra.service;

import java.io.InputStream;
import java.util.List;

import com.indream.casandra.model.Pet;

public interface FIleObjectService {



	boolean createTable(InputStream stream);

	boolean insertPets(List<Pet> allPets);

	boolean insertPet(Pet pet);

	boolean updatePet(Pet pet, String licenseNumber);

}
