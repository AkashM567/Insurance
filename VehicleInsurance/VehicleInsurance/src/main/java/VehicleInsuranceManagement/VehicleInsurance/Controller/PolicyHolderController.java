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

import VehicleInsuranceManagement.VehicleInsurance.Dto.PolicyHolderDto;
import VehicleInsuranceManagement.VehicleInsurance.Entity.PolicyHolder;
import VehicleInsuranceManagement.VehicleInsurance.Service.PolicyHolderRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/policyholders")
public class PolicyHolderController {

    @Autowired
    private PolicyHolderRepository repo;

    @GetMapping
    public String showPolicyHolderList(Model model) {
        List<PolicyHolder> policyholders = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("policyholders", policyholders);
        return "policyhold/policyholder";
    }

    @GetMapping("/creates")
    public String showCreatePage(Model model) {
        PolicyHolderDto policyHolderDto = new PolicyHolderDto();
        model.addAttribute("policyHolderDto", policyHolderDto);
        return "policyhold/createPolicyHolder";
    }

    @PostMapping("/creates")
    public String createPolicyHolder(@Valid @ModelAttribute PolicyHolderDto policyHolderDto, BindingResult result) {

        if (result.hasErrors()) {
            return "policyhold/createPolicyHolder";
        }

        PolicyHolder policyHolder = new PolicyHolder();
        policyHolder.setName(policyHolderDto.getName());
        policyHolder.setAddress(policyHolderDto.getAddress());
        policyHolder.setContactDetails(policyHolderDto.getContactDetails());
        
        repo.save(policyHolder);
        return "redirect:/policyholders";
    }
    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam Long id) {
        try {
            PolicyHolder policyHolder = repo.findById(id).get();
            model.addAttribute("policyHolder", policyHolder);
            
            PolicyHolderDto policyHolderDto = new PolicyHolderDto();
            policyHolderDto.setName(policyHolder.getName());
            policyHolderDto.setAddress(policyHolder.getAddress());
            policyHolderDto.setContactDetails(policyHolder.getContactDetails());
            
            model.addAttribute("policyHolderDto", policyHolderDto);
            
        } catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			return "redirect:/policyholders";
        }
        return "policyhold/EditPolicyHolder";
    }

    @PostMapping("/edit")
    public String updatePolicyHolder(Model model, @RequestParam Long id, @Valid @ModelAttribute PolicyHolderDto policyHolderDto,
                                     BindingResult result) {
        try {
        	PolicyHolder policyHolder = repo.findById(id).get();
			model.addAttribute("policyHolder", policyHolder);
        	
        	
            if (result.hasErrors()) {
                return "policyhold/EditPolicyHolder";
            }
            
            policyHolder.setName(policyHolderDto.getName());
            policyHolder.setAddress(policyHolderDto.getAddress());
            policyHolder.setContactDetails(policyHolderDto.getContactDetails());

            repo.save(policyHolder);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:/policyholders";
    }

    @GetMapping("/delete")
    public String deletePolicyHolder(@RequestParam Long id) {
        try {
            PolicyHolder policyHolder = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid policy holder ID"));
            repo.delete(policyHolder);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:/policyholders";
    }
}
