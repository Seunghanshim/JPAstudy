package domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class Delivery extends BaseEntity{
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    private String city;
    private String street;
    private String zipcode;
    private DeliveryStatus status;
}
