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

import VehicleInsuranceManagement.VehicleInsurance.Dto.PolicyDto;
import VehicleInsuranceManagement.VehicleInsurance.Entity.Agent;
import VehicleInsuranceManagement.VehicleInsurance.Entity.Policy;
import VehicleInsuranceManagement.VehicleInsurance.Entity.PolicyHolder;
import VehicleInsuranceManagement.VehicleInsurance.Service.AgentRepository;
import VehicleInsuranceManagement.VehicleInsurance.Service.PolicyHolderRepository;
import VehicleInsuranceManagement.VehicleInsurance.Service.PolicyRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/policies")
public class PolicyController {

	@Autowired
	private PolicyRepository policyRepository;

	@Autowired
	private PolicyHolderRepository policyHolderRepository;
	
	@Autowired
	private AgentRepository agentrepository;

	@GetMapping
	public String showPolicyList(Model model) {
		List<Policy> policies = policyRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		model.addAttribute("policies", policies);
		return "policy/policy";
	}

	@GetMapping("/create")
	public String showCreatePage(Model model) {
		PolicyDto policyDto = new PolicyDto();
		
		List<PolicyHolder> policyHolders = policyHolderRepository.findAll();
		List<Agent> agents = agentrepository.findAll();
		
		model.addAttribute("policyDto", policyDto);
		model.addAttribute("policyHolders", policyHolders);
		model.addAttribute("agents", agents);
		return "policy/CreatePolicy";
	}

	@PostMapping("/create")
	public String createPolicy(@Valid @ModelAttribute PolicyDto policyDto, BindingResult result) {
	    if (result.hasErrors()) {
	        return "policy/CreatePolicy";
	    }

	    Policy policy = new Policy();
	    policy.setPolicyNumber(policyDto.getPolicyNumber());
	    policy.setPolicyType(policyDto.getPolicyType());
	    policy.setStartDate(policyDto.getStartDate());
	    policy.setEndDate(policyDto.getEndDate());
	    policy.setAmount(policyDto.getAmount());

	    // Set policy holder
	    PolicyHolder policyHolder = policyHolderRepository.findById(policyDto.getPolicyholderId())
	            .orElseThrow(() -> new IllegalArgumentException("Invalid policy holder ID"));
	    policy.setPolicyholder(policyHolder);

	    Agent agent = agentrepository.findById(policyDto.getAgentId()).orElseThrow(() -> new IllegalArgumentException("Invalid Agent ID"));
	    policy.setAgent(agent);

	    policyRepository.save(policy);
	    return "redirect:/policies";
	}

	@GetMapping("/edit")
	public String showEditPage(Model model, @RequestParam Long id) {
	    try {
	        Policy policy = policyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid policy ID"));
	        model.addAttribute("policy", policy);

	        PolicyDto policyDto = new PolicyDto();
	        List<PolicyHolder> policyHolders = policyHolderRepository.findAll();
	        List<Agent> agents = agentrepository.findAll(); // Corrected variable name
	        model.addAttribute("policyDto", policyDto);
	        model.addAttribute("policyHolders", policyHolders);
	        model.addAttribute("agents", agents); // Corrected attribute name
	    } catch (Exception ex) {
	        ex.printStackTrace(); // Log the exception
	        return "redirect:/policies";
	    }
	    return "policy/EditPolicy";
	}

	@PostMapping("/edit")
	public String updatePolicy(@RequestParam Long id, @Valid @ModelAttribute PolicyDto policyDto,
	        BindingResult result) {
	    try {
	        if (result.hasErrors()) {
	            return "policy/EditPolicy";
	        }

	        Policy policy = policyRepository.findById(id)
	                .orElseThrow(() -> new IllegalArgumentException("Invalid policy ID"));

	        PolicyHolder policyHolder = policyHolderRepository.findById(policyDto.getPolicyholderId())
	                .orElseThrow(() -> new IllegalArgumentException("Invalid policy holder ID"));
	        policy.setPolicyholder(policyHolder);

	        if (policyDto.getAgentId() != null) { // Check if Agent ID is not null
	            Agent agent = agentrepository.findById(policyDto.getAgentId())
	                    .orElseThrow(() -> new IllegalArgumentException("Invalid Agent ID"));
	            policy.setAgent(agent);
	        }

	        policy.setPolicyNumber(policyDto.getPolicyNumber());
	        policy.setPolicyType(policyDto.getPolicyType());
	        policy.setStartDate(policyDto.getStartDate());
	        policy.setEndDate(policyDto.getEndDate());
	        policy.setAmount(policyDto.getAmount());

	        policyRepository.save(policy);
	    } catch (Exception ex) {
	        ex.printStackTrace(); // Log the exception
	        return "policy/EditPolicy"; // Return to edit page on error
	    }
	    return "redirect:/policies";
	}


	@GetMapping("/delete")
	public String deletePolicy(@RequestParam Long id) {
		try {
			Policy policy = policyRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid policy ID"));
			policyRepository.delete(policy);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "redirect:/policies";
	}
}
