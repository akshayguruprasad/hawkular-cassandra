package com.indream.casandra.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.datastax.driver.core.Session;
import com.indream.casandra.model.Pet;
import com.indream.casandra.service.FIleObjectService;

@RestController
public class Controller {
	@Autowired
	private FIleObjectService service;

	@Autowired
	private Session session;

	final static private String TABLE_NAME = "demo.pets";

	@PostMapping("/upload")
	public boolean createTable(@RequestParam("file") MultipartFile file) throws IOException {
		return service.createTable(file.getInputStream());

	}

	@PostMapping("/insert")
	public boolean insertPet(@RequestBody Pet pet) {

		return service.insertPet(pet);

	}

}
