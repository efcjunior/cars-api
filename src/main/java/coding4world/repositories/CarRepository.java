package coding4world.repositories;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import coding4world.domain.Car;
import static com.google.common.collect.Lists.newArrayList;

@Repository
public class CarRepository {

    private List<Car> data;

    public CarRepository() {
        
        data = newArrayList(
            new Car(1l, "Toyota", "Sedan", "Camry", 2018),
            new Car(2l, "Toyota", "Sedan", "Etios", 2020),
            new Car(3l, "Fiat", "Sedan", "Siena", 2021));
    }

    public List<Car> findAll() {
        return data;
    }

    public Optional<Car> findById(Long id) {
        return data.stream().filter(car -> car.getId().equals(id)).findFirst();
    }

    public void deleteById(Long id) {
        data = data.stream().filter(car -> !car.getId().equals(id)).collect(Collectors.toList());
    }

    public void deleteAll() {
        data = newArrayList();
    }

    public Optional<Car> store(Car car) {
        Long nextId = nextId();
        car.setId(nextId);
        data.add(car);
        return findById(nextId);
    }

    public Optional<Car> update(Long id, Car car) {
        deleteById(id);
        car.setId(id);
        data.add(car);
        return findById(id);
    }

    private Long nextId() {
        OptionalLong nextId = data.stream()
        .mapToLong(Car::getId)
        .max();
        return new AtomicLong(nextId.orElse(0)).incrementAndGet();
    }
}
