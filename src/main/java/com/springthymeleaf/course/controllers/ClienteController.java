package com.springthymeleaf.course.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springthymeleaf.course.models.entity.Cliente;
import com.springthymeleaf.course.models.service.IClienteService;

@Controller
public class ClienteController {

	@Autowired
	private IClienteService clienteService;
	
	@GetMapping(value="/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Model model, RedirectAttributes flash) {
		Cliente cliente = clienteService.findOne(id);
		
		if(cliente==null) {
			flash.addFlashAttribute("error", "No se encontro el cliente");
			return "redirect:/listar";
		}
		
		model.addAttribute("titulo", "Detalle del cliente " + cliente.getApellido());
		model.addAttribute("cliente", cliente);
		return "ver";
	}
	
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("clientes", clienteService.findAll());
		return "listar";
	}
	
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String crear(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Alta de Clientes");
		return "form";
	}
	
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Validated @ModelAttribute("cliente") Cliente cliente, RedirectAttributes flash, BindingResult result, @RequestParam("file") MultipartFile foto ) {
		if(result.hasErrors()) {
			return "form";
		}
		
		if(!foto.isEmpty()) {
			//Path directorioRecursos = Paths.get("src//main//resources//static//uploads");
			//String rootPath =  directorioRecursos.toFile().getAbsolutePath();
			String rootPath = "/home/juan/Documentos/Temp/uploads";
			try {
				byte[] bytes = foto.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				flash.addFlashAttribute("info", "Has subido correctamente el archivo '" + foto.getOriginalFilename() + "'");
				cliente.setFoto(foto.getOriginalFilename());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		clienteService.save(cliente);
		flash.addFlashAttribute("success", "Cliente creado con exito");
		return "redirect:listar";
	}
	
	@GetMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model) {
		Cliente cliente = null;
		
		if(id>0) {
			cliente = clienteService.findOne(id);
		} else {
			return "redirect:listar";
		}
			
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Editar Cliente");
		return "form";
	}
	
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id) {
		if(id > 0) {
			clienteService.delete(id);
		}
		return "redirect:/listar";
	}
}
