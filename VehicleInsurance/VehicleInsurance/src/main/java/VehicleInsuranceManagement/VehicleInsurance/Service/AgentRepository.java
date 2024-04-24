package VehicleInsuranceManagement.VehicleInsurance.Service;

import org.springframework.data.jpa.repository.JpaRepository;

import VehicleInsuranceManagement.VehicleInsurance.Entity.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long> {

}
