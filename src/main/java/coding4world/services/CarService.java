package coding4world.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coding4world.domain.Car;
import coding4world.repositories.CarRepository;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    
    public List<Car> findAll() {
        return carRepository.findAll();
    }
    
    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);            
    }

    public Optional<Car> store(Car resource) {
        return carRepository.store(resource);      
    }

    public Optional<Car> update(Long id, Car resource) {
        return carRepository.update(id, resource);
    }

    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

    public void deleteAll() {            
        carRepository.deleteAll();
    }    
}
