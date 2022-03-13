package br.edu.ifsul.cc.lpoo.cv.model;

import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.Pet;
import br.edu.ifsul.cc.lpoo.cv.model.Receita;
import java.util.Calendar;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-02-16T19:36:05", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(Consulta.class)
public class Consulta_ { 

    public static volatile SingularAttribute<Consulta, String> observacao;
    public static volatile SingularAttribute<Consulta, Calendar> data;
    public static volatile SingularAttribute<Consulta, Calendar> data_retorno;
    public static volatile SingularAttribute<Consulta, Medico> medico;
    public static volatile SingularAttribute<Consulta, Float> valor;
    public static volatile ListAttribute<Consulta, Receita> receitas;
    public static volatile SingularAttribute<Consulta, Integer> id;
    public static volatile SingularAttribute<Consulta, Pet> pet;

}