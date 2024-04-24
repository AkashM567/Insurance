package VehicleInsuranceManagement.VehicleInsurance.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PolicyHolderDto {
	@NotEmpty(message = "The name is required")
	private String name;
	
	@Size(min = 10, message = "The description should be at least 10 characters")
	@Size(max = 200, message = "The description cannot exceed 2000 characters")
	private String address;
	
	@NotEmpty(message = "The contactDetails is required")
	private String contactDetails;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

}
