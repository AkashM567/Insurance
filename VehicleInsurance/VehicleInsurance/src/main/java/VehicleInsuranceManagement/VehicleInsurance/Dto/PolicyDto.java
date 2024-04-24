package VehicleInsuranceManagement.VehicleInsurance.Dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class PolicyDto {

	@NotEmpty(message = "The policyNumber is required")
	private String policyNumber;

	@NotEmpty(message = "The policyType is required")
	private String policyType;

	@NotNull(message = "Start date cannot be empty")
	// private Date startDate;
	private LocalDate startDate;

	@NotNull(message = "End date cannot be empty")
	// private Date endDate;
	private LocalDate endDate;

	@NotNull(message = "Premium must not be null")
	@Min(value = (long) 0.01, message = "Premium must be greater than zero")
	private BigDecimal amount;

	@NotNull(message = "Policyholder ID must not be null")
	private Long policyholderId; // Assuming you only need the policyholder's ID in the DTO

	@NotNull(message = "Agent ID cannot be empty")
	private Long agentId; // Assuming you only need the agent's ID in the DTO

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public @NotNull(message = "Start date cannot be empty") @NotNull(message = "Start date cannot be empty") LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(
			@NotNull(message = "Start date cannot be empty") @NotNull(message = "Start date cannot be empty") LocalDate startDate) {
		this.startDate = startDate;
	}

	public @NotNull(message = "End date cannot be empty") @NotNull(message = "End date cannot be empty") LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(
			@NotNull(message = "End date cannot be empty") @NotNull(message = "End date cannot be empty") LocalDate endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getPolicyholderId() {
		return policyholderId;
	}

	public void setPolicyholderId(Long policyholderId) {
		this.policyholderId = policyholderId;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}
}
