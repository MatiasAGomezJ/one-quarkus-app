package edu.poniperro.onequarkusapp;

import edu.poniperro.onequarkusapp.dominio.Item;
import edu.poniperro.onequarkusapp.dominio.Orden;
import edu.poniperro.onequarkusapp.dominio.Usuaria;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ServiceOlli {

    @PersistenceContext
    EntityManager em;

    public Usuaria cargaUsuaria(String nombre){
        Optional<Usuaria> user = Usuaria.findByIdOptional(nombre);
        return user.orElse(new Usuaria());
    }

    public Item cargaItem(String nombre){
        Optional<Item> item = Item.findByIdOptional(nombre);
        return item.orElse(new Item());
    }

    public List<Orden> cargaOrden(String nombre){
        return Orden.listByUsername(nombre);
    }

    @Transactional
    public Orden comanda(String nombreUsuaria, String nombreItem) {
        Usuaria usuaria = cargaUsuaria(nombreUsuaria);
        Item item = cargaItem(nombreItem);

        if (usuaria.getNombre().isEmpty() ||
            item.getNombre().isEmpty() ||
            usuaria.getDestreza() < item.getQuality()) {
            return null;
        }

        Orden orden = new Orden(usuaria, item);
        orden.persist();
        return orden;
    }

    @Transactional
    public List<Orden> comandaMultiple(String nombreUsuaria, List<String> nombreItems) {
        Usuaria usuaria = cargaUsuaria(nombreUsuaria);

        List<Orden> ordenes = Arrays.asList();

        if (usuaria.getNombre().isEmpty()) {
            return ordenes;
        }

        for (String nombreItem :
                nombreItems) {
            Item item = cargaItem(nombreItem);
            Orden orden = comanda(nombreUsuaria, nombreItem);
            if (orden != null) {

                ordenes.add(orden);
            }
        }
        return ordenes;
    }

}
