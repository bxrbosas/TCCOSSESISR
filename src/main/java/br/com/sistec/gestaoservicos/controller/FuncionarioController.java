package br.com.sistec.gestaoservicos.controller;

import br.com.sistec.gestaoservicos.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
  public String formInserir(){
   return "funcionario/form-inserir";
  }


 }
