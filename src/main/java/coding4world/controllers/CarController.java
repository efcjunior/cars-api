package coding4world.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import coding4world.controllers.exception.ResourceInternalServerErrorException;
import coding4world.controllers.exception.ResourceNotFoundException;
import coding4world.domain.Car;
import coding4world.services.CarService;

@RestController
@RequestMapping("/api")
class CarController {

    @Autowired
    private CarService carService;

    @GetMapping(value = "/cars")
    public List<Car> findAll() {
        try{
            return carService.findAll();
        }catch(Exception ex) {
            throw new ResourceInternalServerErrorException();
        }        
    }

    @GetMapping(value = "/cars/{id}")
    public Car findById(@PathVariable("id") Long id) {
        try {
            return carService.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        }catch(ResourceNotFoundException ex){
            throw ex;
        }catch(Exception ex) {
            throw new ResourceInternalServerErrorException();
        }
    }

    @PostMapping(value = "/cars")
    @ResponseStatus(HttpStatus.CREATED)
    public Car create(@RequestBody Car resource) {
        try{
            return carService.store(resource).orElseThrow(() -> new ResourceInternalServerErrorException());
        }catch(Exception ex) {
            throw new ResourceInternalServerErrorException();
        }
    }

    @PutMapping(value = "/cars/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Car update(@PathVariable( "id" ) Long id, @RequestBody Car resource) {
      try{
            carService.findById(id).orElseThrow(() -> new ResourceNotFoundException());
            return carService.update(id, resource).orElseThrow(() -> new ResourceInternalServerErrorException());
        }catch(ResourceNotFoundException ex){
            throw ex;
        }catch(Exception ex) {
            throw new ResourceInternalServerErrorException();
        }
    }

    @DeleteMapping(value = "/cars/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {            
        try{  
            carService.findById(id).orElseThrow(() -> new ResourceNotFoundException());
            carService.deleteById(id);
        }catch(ResourceNotFoundException ex){
            throw ex;
        }catch(Exception ex) {
            throw new ResourceInternalServerErrorException();
        }
    }

    @DeleteMapping(value = "/cars/")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll() {            
        try{
            carService.deleteAll();
        }catch(Exception ex) {
            throw new ResourceInternalServerErrorException();
        }
    }    
}