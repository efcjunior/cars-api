package coding4world.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Data
public class Car {

    private Long id;
    private String make;
    private String bodyStyle;
    private String model;
    private Integer modelYear;    
}
