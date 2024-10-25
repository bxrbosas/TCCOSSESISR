package br.com.sistec.gestaoservicos.controller;

import br.com.sistec.gestaoservicos.model.Role;
import br.com.sistec.gestaoservicos.repository.RoleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public String listagem(Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        return "role/listagem-role";
    }

    @GetMapping("/form-inserir-role")
    public String formInserir(Model model){

        model.addAttribute("role", new Role());

        return "role/form-inserir-role";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Role role, BindingResult result,
                         RedirectAttributes attributes){

        if (result.hasErrors()){
            return "role/form-inserir-role";
        }

        roleRepository.save(role);
        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem", "Ambiente salvo com sucesso!");
        return "redirect:/role";
    }

    /* Método que direciona para templates/alunos/alterar.html */
    @GetMapping("/form-alterar-role/{id}")
    public String formAlterar(@PathVariable("id") Long id, Model model) {

        // Busca o aluno no banco de dados
        Role role = roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));

        // Adiciona o aluno no objeto model para ser carregado no formulário
        model.addAttribute("role", role);

        // Retorna o template aluno/alterar.html
        return "role/form-alterar-role";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id,
                          RedirectAttributes attributes) {

        // Busca o aluno no banco de dados
        Role role = roleRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        // Exclui o aluno do banco de dados
        roleRepository.delete(role);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Role excluído com sucesso!");

        // Redireciona para a página de listagem de alunos
        return "redirect:/role";
    }
}
