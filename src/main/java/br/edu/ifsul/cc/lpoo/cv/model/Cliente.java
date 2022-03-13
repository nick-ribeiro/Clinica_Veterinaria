package br.edu.ifsul.cc.lpoo.cv.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.List;

@Entity
@Table(name = "tb_cliente")
@DiscriminatorValue("C")
public class Cliente extends Pessoa {

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_ultima_visita;

    @OneToMany(mappedBy = "cliente")
    private List<Pet> pets;

    public Cliente() {

    }

    public Calendar getData_Ultima_Visita() {
        return data_ultima_visita;
    }

    public void setData_Ultima_Visita(Calendar data_ultima_visita) {
        this.data_ultima_visita = data_ultima_visita;
    }

    public List<Pet> getPet() {
        return pets;
    }

    public void setPet(List<Pet> pets) {
        this.pets = pets;
    }

}
