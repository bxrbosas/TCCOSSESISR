package br.com.sistec.gestaoservicos.controller;

import br.com.sistec.gestaoservicos.model.Servico;
import br.com.sistec.gestaoservicos.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Array;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home(Model model){

        return "home/index";
    }

}