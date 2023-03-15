package com.lqt.flightspringbootproject.dto;

import com.lqt.flightspringbootproject.model.Airport;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;

@Getter
@Setter
@SqlResultSetMapping(
        name = "StatisticResponseMapping",
        classes = @ConstructorResult(
                targetClass = StatisticResponse.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "departure", type = Airport.class),
                        @ColumnResult(name = "destination", type = Airport.class),
                        @ColumnResult(name = "is_deleted", type = Boolean.class),
                        @ColumnResult(name = "is_activated", type = Boolean.class),
                        @ColumnResult(name = "qty", type = Integer.class),
                        @ColumnResult(name = "total", type = Double.class),
                }
        )
)
public class StatisticResponse {
    private Long id;
    private String name;
    private Airport departure;
    private Airport destination;
    private boolean is_deleted;
    private boolean is_activated;
    private int qty;
    private double total;

    public StatisticResponse(Long id, String name, Airport departure, Airport destination,
                             boolean is_deleted, boolean is_activated, int qty, double total) {
        this.id = id;
        this.name = name;
        this.departure = departure;
        this.destination = destination;
        this.is_deleted = is_deleted;
        this.is_activated = is_activated;
        this.qty = qty;
        this.total = total;
    }
}
