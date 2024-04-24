package VehicleInsuranceManagement.VehicleInsurance.Service;

import org.springframework.data.jpa.repository.JpaRepository;

import VehicleInsuranceManagement.VehicleInsurance.Entity.PolicyHolder;

public interface PolicyHolderRepository extends JpaRepository<PolicyHolder, Long> {

}
