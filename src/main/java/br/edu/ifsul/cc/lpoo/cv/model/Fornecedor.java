package br.edu.ifsul.cc.lpoo.cv.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_fornecedor")
@DiscriminatorValue("P")
public class Fornecedor extends Pessoa {

    @Column(nullable = false)
    private String cnpj;

    @Column(nullable = false)
    private String ie;

    public Fornecedor() {

    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getIe() {
        return ie;
    }

    public void setIe(String ie) {
        this.ie = ie;
    }
}
