package br.com.sistec.gestaoservicos.controller;

import br.com.sistec.gestaoservicos.enums.EnumPrioridade;
import br.com.sistec.gestaoservicos.enums.EnumStatus;
import br.com.sistec.gestaoservicos.model.*;
import br.com.sistec.gestaoservicos.repository.AmbienteRepository;
import br.com.sistec.gestaoservicos.repository.FuncionarioRepository;
import br.com.sistec.gestaoservicos.repository.ServicoRepository;
import br.com.sistec.gestaoservicos.util.FileUploadUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/servico")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private AmbienteRepository ambienteRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping
    public String listagem(Model model) {

        // recupera o usuário logado getPrincipal
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        List<Role> roles = (List<Role>) user.getRoles();
        Role Role = roles.get(0);

        model.addAttribute("servicos", servicoRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));

        if (!Objects.equals(Role.getName(), "ADMINISTRADOR")) {
            Long idUsuario = user.getId();
            Funcionario funcionario = funcionarioRepository.findByUserId(idUsuario);
            List<Servico> listServico = servicoRepository.findByFuncionario(funcionario);
            model.addAttribute("servicos", listServico);
        }


        return "servico/listagem-servico";
    }

    @GetMapping("/form-inserir-servico")
    public String formInserir(Model model) {

        model.addAttribute("ambientes", ambienteRepository.findAll());

        model.addAttribute("servico", new Servico());

        return "servico/form-inserir-servico";
    }

    @PostMapping("/salvar")
    public String salvar(
            @Valid Servico servico,
            BindingResult result,
            RedirectAttributes attributes,
            @RequestParam("foto") MultipartFile multipartFile,
            @RequestParam(name = "imgHistorico", required = false) MultipartFile[] imgsHistorico
    ) throws IOException
    {

        if (result.hasErrors()) {
            return "servico/form-inserir-servico";
        }



        // recupera o usuário logado getPrincipal
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Long idUsuario = user.getId();
        Funcionario funcionario = funcionarioRepository.findByUserId(idUsuario);

        servico.setFuncionario(funcionario);





        String extensao = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());

        // date sql


        if(servico.getId() == null){

            servico.setDataCadastro(new Date());

        }

        servicoRepository.save(servico);
        // Adiciona uma mensagem que será exibida no template

        // fileName = user.getId() + "." + extensao;
        String fileName = servico.getId() + "." + extensao;

        // Se tem histórico, percorre as imagens imgHistorico

        // Lógica para salvar as imagens do histórico com o id do histórico
        if(!servico.getHistoricos().isEmpty()){
            for (int i = 0; i < servico.getHistoricos().size(); i++) {
                String extensaoHistorico = StringUtils.getFilenameExtension(imgsHistorico[i].getOriginalFilename());
                Long idHistorico = servico.getHistoricos().get(i).getId();
                String fileNameHistorico = idHistorico + "." + extensaoHistorico;
                servico.getHistoricos().get(i).setImgHistorico(fileNameHistorico);
                String uploadPastaHistorico = "src/main/resources/static/assets/img/historicos-servico/"+servico.getId();
                FileUploadUtil.saveFile(uploadPastaHistorico, fileNameHistorico, imgsHistorico[i]);
            }
        }

        servico.setImage(fileName);
        // seta o status aberto
        servico.setStatus(EnumStatus.ABERTA);
        servico.setPrioridade(EnumPrioridade.BAIXA);

        servicoRepository.save(servico);





        String uploadPasta =  "src/main/resources/static/assets/img/imagens-os";

        FileUploadUtil.saveFile(uploadPasta, fileName, multipartFile);


        attributes.addFlashAttribute("mensagem", "Serviço salvo com sucesso!");
        return "redirect:/servico";
    }

    /* Método que direciona para templates/alunos/alterar.html */
    @GetMapping("/form-alterar-servico/{id}")
    public String formAlterar(@PathVariable("id") Long id, Model model) {

        model.addAttribute("ambientes", ambienteRepository.findAll());

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



    @PostMapping("/addHistorico")
    public String addHistorico(Servico servico) {
        servico.getHistoricos().add(new Historico());
        return "servico/form-inserir-servico :: historicos";
    }

    @PostMapping("/removeHistorico")
    public String removeHistorico(Servico servico, @RequestParam("removeDynamicRow") Integer historicoIndex) {
        servico.getHistoricos().remove(historicoIndex.intValue());
        return "servico/form-inserir-servico :: historicos";
    }


}