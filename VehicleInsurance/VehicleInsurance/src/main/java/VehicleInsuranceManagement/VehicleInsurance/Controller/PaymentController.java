package VehicleInsuranceManagement.VehicleInsurance.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import VehicleInsuranceManagement.VehicleInsurance.Dto.PaymentDto;
import VehicleInsuranceManagement.VehicleInsurance.Entity.Payment;
import VehicleInsuranceManagement.VehicleInsurance.Entity.Policy;
import VehicleInsuranceManagement.VehicleInsurance.Service.PaymentRepository;
import VehicleInsuranceManagement.VehicleInsurance.Service.PolicyRepository;
import jakarta.validation.Valid;
@Controller
@RequestMapping("/payments")
public class PaymentController {
	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private PolicyRepository policyRepository;

	@GetMapping
	public String showPaymentList(Model model) {
		List<Payment> payments = paymentRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		model.addAttribute("payments", payments);
		return "payment/payment";
	}

	@GetMapping("/create")
	public String showCreatePage(Model model) {
		PaymentDto paymentDto = new PaymentDto();
		List<Policy> policies = policyRepository.findAll();
		model.addAttribute("paymentDto", paymentDto);
		model.addAttribute("policies", policies);
		return "payment/CreatePayment";
	}

	@PostMapping("/create")
	public String createPayment(@Valid @ModelAttribute PaymentDto paymentDto, BindingResult result) {
		if (result.hasErrors()) {
			return "payment/CreatePayment";
		}

		Payment payment = new Payment();
		payment.setAmount(paymentDto.getAmount());
		payment.setDate(paymentDto.getDate());

		// Set policy
		Policy policy = policyRepository.findById(paymentDto.getPolicyId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid policy ID"));
		payment.setPolicy(policy);

		paymentRepository.save(payment);
		return "redirect:/payments";
	}

	@GetMapping("/edit")
	public String showEditPage(@RequestParam Long id, Model model) {
		Payment payment = paymentRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid payment ID"));
		PaymentDto paymentDto = new PaymentDto();
		paymentDto.setAmount(payment.getAmount());
		paymentDto.setDate(payment.getDate());
		paymentDto.setPolicyId(payment.getPolicy().getId());

		List<Policy> policies = policyRepository.findAll();
		model.addAttribute("paymentDto", paymentDto);
		model.addAttribute("policies", policies);
		return "payment/editPayment";
	}

	@PostMapping("/edit")
	public String updatePayment(@RequestParam Long id, @Valid @ModelAttribute PaymentDto paymentDto,
			BindingResult result) {
		if (result.hasErrors()) {
			return "payment/editPayment";
		}

		Payment payment = paymentRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid payment ID"));
		payment.setAmount(paymentDto.getAmount());
		payment.setDate(paymentDto.getDate());

		Policy policy = policyRepository.findById(paymentDto.getPolicyId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid policy ID"));
		payment.setPolicy(policy);

		paymentRepository.save(payment);
		return "redirect:/payments";
	}

	@GetMapping("/delete")
	public String deletePayment(@RequestParam Long id) {
		Payment payment = paymentRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid payment ID"));
		paymentRepository.delete(payment);
		return "redirect:/payments";
	}
}