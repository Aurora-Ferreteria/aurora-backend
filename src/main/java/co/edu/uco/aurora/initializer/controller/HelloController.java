package co.edu.uco.aurora.initializer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "¡Hola Mundo! El backend de Aurora está vivo y funcionando en la nube con despliegue automatico en Render.";
    }

}