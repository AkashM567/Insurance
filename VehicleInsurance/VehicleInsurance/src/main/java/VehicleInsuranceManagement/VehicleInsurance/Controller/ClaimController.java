package VehicleInsuranceManagement.VehicleInsurance.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import VehicleInsuranceManagement.VehicleInsurance.Dto.ClaimDto;
import VehicleInsuranceManagement.VehicleInsurance.Entity.Claim;
import VehicleInsuranceManagement.VehicleInsurance.Entity.Policy;
import VehicleInsuranceManagement.VehicleInsurance.Service.ClaimRepository;
import VehicleInsuranceManagement.VehicleInsurance.Service.PolicyRepository;

@Controller
@RequestMapping("/claims")
public class ClaimController {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private PolicyRepository policyRepository;

    @GetMapping
    public String showClaimList(Model model) {
        List<Claim> claims = claimRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("claims", claims);
        return "agents/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        ClaimDto claimDto = new ClaimDto();
        List<Policy> policies = policyRepository.findAll();
        model.addAttribute("claimDto", claimDto);
        model.addAttribute("policies", policies);
        return "agents/createClaim";
    }

    @PostMapping("/create")
    public String createClaim(@ModelAttribute ClaimDto claimDto, BindingResult result) {
        if (result.hasErrors()) {
            return "agents/createClaim";
        }

        Claim claim = new Claim();
        claim.setPolicy(policyRepository.findById(claimDto.getPolicyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid policy ID")));
        claim.setReason(claimDto.getReason());
        claim.setAmount(claimDto.getAmount());

        claimRepository.save(claim);
        return "redirect:/claims";
    }

    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable("id") Long id, Model model) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid claim ID"));
        ClaimDto claimDto = new ClaimDto();
        claimDto.setPolicyId(claim.getPolicy().getId());
        claimDto.setReason(claim.getReason());
        claimDto.setAmount(claim.getAmount());
        List<Policy> policies = policyRepository.findAll();
        model.addAttribute("claimDto", claimDto);
        model.addAttribute("policies", policies);
        return "agents/editClaim";
    }

    @PostMapping("/edit/{id}")
    public String updateClaim(@PathVariable("id") Long id, @ModelAttribute ClaimDto claimDto, BindingResult result) {
        if (result.hasErrors()) {
            return "agents/editClaim";
        }

        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid claim ID"));
        claim.setPolicy(policyRepository.findById(claimDto.getPolicyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid policy ID")));
        claim.setReason(claimDto.getReason());
        claim.setAmount(claimDto.getAmount());

        claimRepository.save(claim);
        return "redirect:/claims";
    }

    @GetMapping("/delete/{id}")
    public String deleteClaim(@PathVariable("id") Long id) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid claim ID"));
        claimRepository.delete(claim);
        return "redirect:/claims";
    }
}

