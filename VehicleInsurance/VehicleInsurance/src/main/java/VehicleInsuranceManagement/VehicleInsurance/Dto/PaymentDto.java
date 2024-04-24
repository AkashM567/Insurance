package VehicleInsuranceManagement.VehicleInsurance.Dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PaymentDto {
	
	@NotNull(message = "Agent ID cannot be empty")
	private Long policyId;
	
	@NotNull(message = "Premium must not be null")
	@Min(value = (long) 0.01, message = "Premium must be greater than zero")
	private BigDecimal amount;
	
	private LocalDate date;

	// Constructors
	public PaymentDto() {
	}

	public PaymentDto(Long policyId, BigDecimal amount, LocalDate date) {

		this.policyId = policyId;
		this.amount = amount;
		this.date = date;
	}

	// Getters and setters

	public Long getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
