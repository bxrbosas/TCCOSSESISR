package br.com.sistec.gestaoservicos.controller;

import br.com.sistec.gestaoservicos.model.Funcionario;
import br.com.sistec.gestaoservicos.model.Pessoa;
import br.com.sistec.gestaoservicos.repository.FuncionarioRepository;
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
 @RequestMapping("/funcionario")
  public class FuncionarioController {

  @Autowired
  private FuncionarioRepository funcionarioRepository;

  @GetMapping
  public String listagem(Model model) {
   model.addAttribute("funcionarios", funcionarioRepository.findAll());
   return "funcionario/listagem";
  }

  @GetMapping("/form-inserir")
  public String formInserir(Model model){

   model.addAttribute("funcionario", new Funcionario());

   return "funcionario/form-inserir";
  }

 @PostMapping("/salvar")
 public String salvar(@Valid Funcionario funcionario, BindingResult result,
                      RedirectAttributes attributes){

  if (result.hasErrors()){
   return "funcionario/form-inserir";
  }

  funcionarioRepository.save(funcionario);
  // Adiciona uma mensagem que será exibida no template
  attributes.addFlashAttribute("mensagem", "Funcionário salvo com sucesso!");
  return "redirect:/funcionario";
 }

 /*
  * Método que direciona para templates/alunos/alterar.html
  */
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
