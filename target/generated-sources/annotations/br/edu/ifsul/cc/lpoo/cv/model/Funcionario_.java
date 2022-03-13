package br.edu.ifsul.cc.lpoo.cv.model;

import br.edu.ifsul.cc.lpoo.cv.model.Cargo;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-02-16T19:36:05", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(Funcionario.class)
public class Funcionario_ extends Pessoa_ {

    public static volatile SingularAttribute<Funcionario, String> numero_pis;
    public static volatile SingularAttribute<Funcionario, Cargo> cargo;
    public static volatile SingularAttribute<Funcionario, String> numero_ctps;

}