package VehicleInsuranceManagement.VehicleInsurance.Service;

import org.springframework.data.jpa.repository.JpaRepository;

import VehicleInsuranceManagement.VehicleInsurance.Entity.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Long> {

}
