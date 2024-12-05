package br.com.sistec.gestaoservicos.controller;

import br.com.sistec.gestaoservicos.enums.Funcao;
import br.com.sistec.gestaoservicos.model.Funcionario;
import br.com.sistec.gestaoservicos.model.Pessoa;
import br.com.sistec.gestaoservicos.model.Role;
import br.com.sistec.gestaoservicos.repository.FuncionarioRepository;
import br.com.sistec.gestaoservicos.repository.RoleRepository;
import br.com.sistec.gestaoservicos.util.FileUploadUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/funcionario")
public class FuncionarioController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    private static final String ROLE_ADMINISTRADOR = "ADMINISTRADOR";
    private static final String ROLE_REQUISITANTE = "REQUISITANTE";
    private static final String ROLE_EXECUTOR = "EXECUTOR";

    @GetMapping
    public String listagem(Model model) {
        model.addAttribute("funcionarios", funcionarioRepository.findAll());
        return "funcionario/listagem";
    }

    @GetMapping("/form-inserir")
    public String formInserir(Model model) {

        model.addAttribute("funcionario", new Funcionario());

        return "funcionario/form-inserir";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Funcionario funcionario, BindingResult result,
                         RedirectAttributes attributes, @RequestParam("foto") MultipartFile multipartFile) throws IOException {

        if (result.hasErrors()) {
            return "funcionario/form-inserir";
        }

        // Foto
        String extensao = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());


        if (!funcionario.getUser().getPassword().isEmpty()) {
            funcionario.getUser().setFirstName(funcionario.getNome());
            funcionario.getUser().setEmail(funcionario.getEmail());
            funcionario.getUser().setUsername(funcionario.getEmail());
            funcionario.getUser().setPassword(bCryptPasswordEncoder.encode(funcionario.getUser().getPassword()));

            // Adiciona o Perfil
            if (funcionario.getFuncao().equals(Funcao.ADMINISTRADOR)) {
                Role role = roleRepository.findByName(ROLE_ADMINISTRADOR);
                funcionario.getUser().getRoles().add(role);
            } else if (funcionario.getFuncao().equals(Funcao.REQUISITANTE)) {
                Role role = roleRepository.findByName(ROLE_REQUISITANTE);
                funcionario.getUser().getRoles().add(role);
            } else if (funcionario.getFuncao().equals(Funcao.EXECUTOR)) {
                Role role = roleRepository.findByName(ROLE_EXECUTOR);
                funcionario.getUser().getRoles().add(role);
            }
        }

        funcionarioRepository.save(funcionario);

        // Foto
        String fileName = funcionario.getUser().getId() + "." + extensao;
        funcionario.getUser().setImage(fileName);

        funcionarioRepository.save(funcionario);

        // Foto
        String uploadPasta = "src/main/resources/static/assets/img/fotos-usuarios";
        FileUploadUtil.saveFile(uploadPasta, fileName, multipartFile);
        attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem", "Funcionário salvo com sucesso!");
        return "redirect:/funcionario";
    }

    /* Método que direciona para templates/alunos/alterar.html */
    @GetMapping("/form-alterar/{id}")
    public String formAlterar(@PathVariable("id") Long id, Model model) {

        // Busca o aluno no banco de dados
        Pessoa funcionario = funcionarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));

        // Adiciona o aluno no objeto model para ser carregado no formulário
        model.addAttribute("funcionario", funcionario);

        // Retorna o template aluno/alterar.html
        return "funcionario/form-alterar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id,
                          RedirectAttributes attributes) {

        // Busca o aluno no banco de dados
        Pessoa funcionario = funcionarioRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        // Exclui o aluno do banco de dados
        funcionarioRepository.delete(funcionario);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Funcionario excluído com sucesso!");

        // Redireciona para a página de listagem de alunos
        return "redirect:/funcionario";
    }


}
