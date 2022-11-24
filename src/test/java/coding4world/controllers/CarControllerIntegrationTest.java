package coding4world.controllers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import coding4world.domain.Car;
import coding4world.controllers.exception.ResourceNotFoundException;
import coding4world.services.CarService;

import java.util.List;
import java.util.Optional;

@WebMvcTest(CarController.class)
public class CarControllerIntegrationTest {

    @MockBean
    private CarService carService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenCars_whenGetCars_thenStatus200() throws Exception {
        List<Car> cars = newArrayList(
                new Car(1l, "Toyota", "Sedan", "Camry", 2018),
                new Car(2l, "Toyota", "Sedan", "Etios", 2020),
                new Car(3l, "Fiat", "Sedan", "Siena", 2021));


        when(carService.findAll()).thenReturn(cars);

        mvc.perform(get("/api/cars")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content()
        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].make", is("Toyota")));
    }

    @Test
    public void givenCarsId_whenGetCarsId_thenStatus200() throws Exception {
        long id = 1l;

        Car car = new Car(1l, "Toyota", "Sedan", "Camry", 2018);

        when(carService.findById(id)).thenReturn(Optional.of(car));

        mvc.perform(get("/api/cars/{id}", id)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content()
        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.make").value(car.getMake()));
    }

    @Test
    public void givenCarsId_whenPutCarsId_thenStatus200() throws Exception {
        long id = 1l;

        Car car = new Car(1l, "Toyota", "Sedan", "Camry", 2018);
        Car carUpdated = new Car(1l, "Toyota", "Sedan", "Camry", 2019);

        when(carService.findById(id)).thenReturn(Optional.of(car));

        mvc.perform(put("/api/cars/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(carUpdated)))
        .andExpect(status().isOk());
    }

    @Test
    public void givenCarsId_whenDeleteCarsId_thenStatus200() throws Exception {
        long id = 1l;

        Car car = new Car(1l, "Toyota", "Sedan", "Camry", 2018);
        when(carService.findById(id)).thenReturn(Optional.of(car));
        mvc.perform(delete("/api/cars/{id}", id)).andExpect(status().isOk());
    }

    @Test
    public void givenCars_whenDeleteCars_thenStatus200() throws Exception {
        mvc.perform(delete("/api/cars/")).andExpect(status().isOk());
    }

    @Test
    public void givenCars_whenPostCars_thenStatus201() throws Exception {
        Car newCar = new Car(1l, "Toyota", "Sedan", "Camry", 2018);

        mvc.perform(post("/api/cars")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper
        .writeValueAsString(newCar)))
        .andExpect(status().isCreated()); 
    }

    @Test
    public void givenCarsId_whenGetCarsId_thenStatus400() throws Exception {
        long id = 1l;

        mvc.perform(get("/api/cars/id", id)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    
    @Test
    public void givenCarsId_whenPutCarsId_thenStatus404() throws Exception {
        long id = 5l;

        Car carUpdated = new Car(1l, "Toyota", "Sedan", "Camry", 2019);

        when(carService.findById(id)).thenThrow(new ResourceNotFoundException());

        mvc.perform(put("/api/cars/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(carUpdated)))
        .andExpect(status().isNotFound());
    }

    @Test
    public void givenCarsId_whenPatchCarsId_thenStatus405() throws Exception {
        long id = 1l;
        mvc.perform(patch("/api/cars/{id}", id)).andExpect(status().isMethodNotAllowed());
    }
}
