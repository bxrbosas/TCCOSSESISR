package br.com.sistec.gestaoservicos.controller;

import br.com.sistec.gestaoservicos.model.Servico;
import br.com.sistec.gestaoservicos.model.Pessoa;
import br.com.sistec.gestaoservicos.repository.ServicoRepository;
import br.com.sistec.gestaoservicos.util.FileUploadUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

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
    public String formInserir(Model model) {

        model.addAttribute("servico", new Servico());

        return "servico/form-inserir-servico";
    }

    @PostMapping("/salvar")
    public String salvar(
            @Valid Servico servico,
            BindingResult result,
            RedirectAttributes attributes,
            @RequestParam("foto") MultipartFile multipartFile
    ) throws IOException
    {

        if (result.hasErrors()) {
            return "servico/form-inserir-servico";
        }

        String extensao = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());

        servicoRepository.save(servico);
        // Adiciona uma mensagem que será exibida no template

        // fileName = user.getId() + "." + extensao;
        String fileName = servico.getId() + "." + extensao;

        servico.setImage(fileName);

        servicoRepository.save(servico);

        String uploadPasta =  "src/main/resources/static/assets/img/imagens-os";

        FileUploadUtil.saveFile(uploadPasta, fileName, multipartFile);


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





    @PostMapping("/salvarr")
    public String salvarr(
            @Valid Servico servico,
            BindingResult result,
            RedirectAttributes attributes,
            @RequestParam("foto") MultipartFile multipartFile
    ) throws IOException {

        if (result.hasErrors()) {
            return "servico/form-inserir-servico";
        }


        String extensao = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());

        servicoRepository.save(servico);


        // fileName = servico.getId() + "." + extensao;
        String fileName = servico.getId() + "." + extensao;

        servico.setImage(fileName);

        servicoRepository.save(servico);


        String uploadPasta = "src/main/resources/static/assets/img/fotos-usuarios";

        FileUploadUtil.saveFile(uploadPasta, fileName, multipartFile);

        attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");

        return "redirect:/servico";
    }
}