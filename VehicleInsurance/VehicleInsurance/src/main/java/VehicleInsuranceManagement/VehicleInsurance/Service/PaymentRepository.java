package VehicleInsuranceManagement.VehicleInsurance.Service;

import org.springframework.data.jpa.repository.JpaRepository;

import VehicleInsuranceManagement.VehicleInsurance.Entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
