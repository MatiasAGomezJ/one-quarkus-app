package edu.poniperro.onequarkusapp.dominio;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "t_items")
public class Item extends PanacheEntityBase {

    @Id
    @Column(name="item_nom")
    private String nombre = "";

    @Column(name = "item_prop")
    private int quality = 0;

    @Column(name = "item_tipo")
    private String tipo = "";

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nom) {
        this.nombre = nom;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getTipo() {
        return tipo;
    }
}
