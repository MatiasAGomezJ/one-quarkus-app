package edu.poniperro.onequarkusapp.dominio;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_ordenes")
public class Orden extends PanacheEntityBase {

    @Id @GeneratedValue( strategy=GenerationType.IDENTITY)
    @Column(name = "ord_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "ord_user")
    private Usuaria user;

    @OneToOne
    @JoinColumn(name = "ord_item")
    private Item item;

    public Orden() {

    }

    public Orden(Usuaria user, Item item) {
        this.user = user;
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuaria getUser() {
        return user;
    }

    public void setUser(Usuaria user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public static List<Orden> listByUsername(String nombre) {
        List<Orden> ordenes = Orden.listAll();
        List<Orden> ordenesByUserame = ordenes.stream()
                .filter(o -> o.getUser().getNombre().equalsIgnoreCase(nombre))
                .toList();
        return ordenesByUserame;
    }
}
