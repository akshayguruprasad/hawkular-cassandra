package com.indream.casandra.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Pet implements Serializable {
	private static final long serialVersionUID = 1L;

	public Pet() {
	}

	private String animalName;
	private Date licenseIssuedOn;
	private String licenseNumber;
	private List<String> primaryBreed;
	private List<String> secondaryBreed;
	private String species;
	private String zipCode;

	public String getAnimalName() {
		return animalName;
	}

	public void setAnimalName(String animalName) {
		this.animalName = animalName;
	}

	public Date getLicenseIssuedOn() {
		return licenseIssuedOn;
	}

	public void setLicenseIssuedOn(Date licenseIssuedOn) {
		this.licenseIssuedOn = licenseIssuedOn;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public List<String> getPrimaryBreed() {
		return primaryBreed;
	}

	public void setPrimaryBreed(List<String> primaryBreed) {
		this.primaryBreed = primaryBreed;
	}

	public List<String> getSecondaryBreed() {
		return secondaryBreed;
	}

	public void setSecondaryBreed(List<String> secondaryBreed) {
		this.secondaryBreed = secondaryBreed;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return "PetStore [animalName=" + animalName + ", licenseIssuedOn=" + licenseIssuedOn + ", licenseNumber="
				+ licenseNumber + ", primaryBreed=" + primaryBreed + ", secondaryBreed=" + secondaryBreed + ", species="
				+ species + ", zipCode=" + zipCode + "]";
	}

}
