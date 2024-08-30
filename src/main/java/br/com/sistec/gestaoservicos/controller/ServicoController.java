package br.com.sistec.gestaoservicos.controller;

import br.com.sistec.gestaoservicos.model.Servico;
import br.com.sistec.gestaoservicos.model.Pessoa;
import br.com.sistec.gestaoservicos.repository.ServicoRepository;
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
@RequestMapping("/servico")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @GetMapping
    public String listagem(Model model) {
        model.addAttribute("servico", servicoRepository.findAll());
        return "servico/listagem-servico";
    }

    @GetMapping("/form-inserir-servico")
    public String formInserir(Model model){

        model.addAttribute("servico", new Servico());

        return "servico/form-inserir-servico";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Servico servico, BindingResult result,
                         RedirectAttributes attributes){

        if (result.hasErrors()){
            return "servico/form-inserir-servico";
        }

        servicoRepository.save(servico);
        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem", "Serviço salvo com sucesso!");
        return "redirect:/servico";
    }

    /* Método que direciona para templates/alunos/alterar.html */
    @GetMapping("/form-alterar-servico/{id}")
    public String formAlterar(@PathVariable("id") Long id, Model model) {

        // Busca o aluno no banco de dados
        Servico servico = servicoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));

        // Adiciona o aluno no objeto model para ser carregado no formulário
        model.addAttribute("servico", servico);

        // Retorna o template aluno/alterar.html
        return "servico/form-alterar-servico";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id,
                          RedirectAttributes attributes) {

        // Busca o aluno no banco de dados
        Servico servico = servicoRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        // Exclui o aluno do banco de dados
        servicoRepository.delete(servico);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Servico excluído com sucesso!");

        // Redireciona para a página de listagem de alunos
        return "redirect:/servico";
    }
}
