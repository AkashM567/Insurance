package VehicleInsuranceManagement.VehicleInsurance.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class AgentDto {
	@NotEmpty(message = "The name is required")
	private String name;
	@NotEmpty(message = "The email is required")
	private String email;
	@NotEmpty(message = "The contactDetails is required")
	private String contactDetails;
	@Min(0)
	private double commissionPercentage;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

	public double getCommissionPercentage() {
		return commissionPercentage;
	}

	public void setCommissionPercentage(double commissionPercentage) {
		this.commissionPercentage = commissionPercentage;
	}

}
