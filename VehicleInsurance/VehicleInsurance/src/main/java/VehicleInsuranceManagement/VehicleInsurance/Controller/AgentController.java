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

import VehicleInsuranceManagement.VehicleInsurance.Dto.AgentDto;
import VehicleInsuranceManagement.VehicleInsurance.Entity.Agent;
import VehicleInsuranceManagement.VehicleInsurance.Service.AgentRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/agents")
public class AgentController {

	@Autowired
	private AgentRepository repo;

	@GetMapping
	public String showProductList(Model model) {
		List<Agent> agents = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
		model.addAttribute("agents", agents);
		return "agents/agent";
	}

	@GetMapping("/create")
	public String showCreatePage(Model model) {
		AgentDto agentDto = new AgentDto();
		model.addAttribute("agentDto", agentDto);
		return "agents/CreateAgent";
	}

	@PostMapping("/create")
	public String createAgent(@Valid @ModelAttribute AgentDto agentDto, BindingResult result) {

		if (result.hasErrors()) {
			return "agents/CreateAgent";
		}
		Agent agent = new Agent();
		agent.setName(agentDto.getName());
		agent.setEmail(agentDto.getEmail());
		agent.setContactDetails(agentDto.getContactDetails());
		agent.setCommissionPercentage(agentDto.getCommissionPercentage());
		repo.save(agent);
		return "redirect:/agents";
	}

	@GetMapping("/edit")
	public String showEditPage(Model model, @RequestParam Long id) {
		try {
			Agent agent = repo.findById(id).get();
			model.addAttribute("agent", agent);

			AgentDto agentDto = new AgentDto();
			agentDto.setName(agent.getName());
			agentDto.setEmail(agent.getEmail());
			agentDto.setContactDetails(agent.getContactDetails());
			agentDto.setCommissionPercentage(agent.getCommissionPercentage());

			model.addAttribute("agentDto", agentDto);

		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			return "redirect:/agents";
		}
		return "agents/EditAgent";
	}

	@PostMapping("/edit")
	public String updateProduct(Model model, @RequestParam Long id, @Valid @ModelAttribute AgentDto agentDto,
			BindingResult result) {
		try {
			Agent agent = repo.findById(id).get();
			model.addAttribute("agent", agent);

			if (result.hasErrors()) {
				return "agents/EditAgent";
			}

			agent.setName(agentDto.getName());
			agent.setEmail(agentDto.getEmail());
			agent.setContactDetails(agentDto.getContactDetails());
			agent.setCommissionPercentage(agentDto.getCommissionPercentage());

			repo.save(agent);

		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
		return "redirect:/agents";
	}

	@GetMapping("/delete")
	public String deleteProduct(@RequestParam Long id) {
		try {
			Agent agent = repo.findById(id).get();
			repo.delete(agent);

		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
		return "redirect:/agents";
	}

}
