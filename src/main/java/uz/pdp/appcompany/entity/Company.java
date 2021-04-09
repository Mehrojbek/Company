package uz.pdp.appcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Company {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;
    @Column(nullable =false,unique = true)
    private String corpName;

    private String directorName;

    @OneToOne
    private Address address;


    private boolean active=true;

    public Company(String corpName, String directorName, Address address) {
        this.corpName = corpName;
        this.directorName = directorName;
        this.address = address;
    }
}
