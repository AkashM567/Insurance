package VehicleInsuranceManagement.VehicleInsurance.Service;

import org.springframework.data.jpa.repository.JpaRepository;

import VehicleInsuranceManagement.VehicleInsurance.Entity.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Long> {

}
