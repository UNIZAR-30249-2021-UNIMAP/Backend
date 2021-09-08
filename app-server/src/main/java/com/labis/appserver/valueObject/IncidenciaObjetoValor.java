package com.labis.appserver.valueObject;

import javax.persistence.Embeddable;
import java.sql.Timestamp;

@Embeddable
public class IncidenciaObjetoValor {
    public String descripcion;
    public String email;
    public String imagen;
    public Timestamp reportadoTimeStamp;

    public IncidenciaObjetoValor() {}

    public IncidenciaObjetoValor(String descripcion, String email, String imagen, Timestamp reportadoTimeStamp) {
        this.descripcion = descripcion;
        this.email = email;
        this.imagen = imagen;
        this.reportadoTimeStamp = reportadoTimeStamp;
    }
}
