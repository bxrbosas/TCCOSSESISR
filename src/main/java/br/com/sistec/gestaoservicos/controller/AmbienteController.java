package br.com.sistec.gestaoservicos.controller;

import br.com.sistec.gestaoservicos.model.Ambiente;
import br.com.sistec.gestaoservicos.repository.AmbienteRepository;
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
@RequestMapping("/ambiente")
public class AmbienteController {

    @Autowired
    private AmbienteRepository ambienteRepository;

    @GetMapping
    public String listagem(Model model) {
        model.addAttribute("ambiente", ambienteRepository.findAll());
        return "ambiente/listagem-ambiente";
    }

    @GetMapping("/form-inserir-ambiente")
    public String formInserir(Model model){

        model.addAttribute("ambiente", new Ambiente());

        return "ambiente/form-inserir-ambiente";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Ambiente ambiente, BindingResult result,
                         RedirectAttributes attributes, @RequestParam("foto") MultipartFile multipartFile) throws IOException {

        if (result.hasErrors()){
            return "ambiente/form-inserir-ambiente";
        }

        // Foto
        String extensao = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());



        ambienteRepository.save(ambiente);

        // Foto
        String fileName = ambiente.getId() + "." + extensao;
        ambiente.setImage(fileName);

        ambienteRepository.save(ambiente);

        // Foto
        String uploadPasta =  "src/main/resources/static/assets/img/fotos-ambiente";
        FileUploadUtil.saveFile(uploadPasta, fileName, multipartFile);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem", "Ambiente salvo com sucesso!");
        return "redirect:/ambiente";
    }

    /* Método que direciona para templates/alunos/alterar.html */
    @GetMapping("/form-alterar-ambiente/{id}")
    public String formAlterar(@PathVariable("id") Long id, Model model) {

        // Busca o aluno no banco de dados
        Ambiente ambiente = ambienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));

        // Adiciona o aluno no objeto model para ser carregado no formulário
        model.addAttribute("ambiente", ambiente);

        // Retorna o template aluno/alterar.html
        return "ambiente/form-alterar-ambiente";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id,
                          RedirectAttributes attributes) {

        // Busca o aluno no banco de dados
        Ambiente ambiente = ambienteRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        // Exclui o aluno do banco de dados
        ambienteRepository.delete(ambiente);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Ambiente excluído com sucesso!");

        // Redireciona para a página de listagem de alunos
        return "redirect:/ambiente";
    }

}
