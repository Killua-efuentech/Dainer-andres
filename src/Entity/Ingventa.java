/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dainer
 */
@Entity
@Table(name = "ingventa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ingventa.findAll", query = "SELECT i FROM Ingventa i")
    , @NamedQuery(name = "Ingventa.findById", query = "SELECT i FROM Ingventa i WHERE i.id = :id")
    , @NamedQuery(name = "Ingventa.findByValor", query = "SELECT i FROM Ingventa i WHERE i.valor = :valor")
    , @NamedQuery(name = "Ingventa.findByDatetime", query = "SELECT i FROM Ingventa i WHERE i.datetime = :datetime")
    , @NamedQuery(name = "Ingventa.findByEstado", query = "SELECT i FROM Ingventa i WHERE i.estado = :estado")})
public class Ingventa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Lob
    @Column(name = "fecha")
    private String fecha;
    @Basic(optional = false)
    @Lob
    @Column(name = "habitacion")
    private String habitacion;
    @Basic(optional = false)
    @Column(name = "valor")
    private double valor;
    @Basic(optional = false)
    @Lob
    @Column(name = "tiempo")
    private String tiempo;
    @Basic(optional = false)
    @Lob
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
    @Basic(optional = false)
    @Column(name = "estado")
    private int estado;
    @JoinColumn(name = "idlog", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Userlog idlog;

    public Ingventa() {
    }

    public Ingventa(Integer id) {
        this.id = id;
    }

    public Ingventa(Integer id, String fecha, String habitacion, double valor, String tiempo, String tipo, Date datetime, int estado) {
        this.id = id;
        this.fecha = fecha;
        this.habitacion = habitacion;
        this.valor = valor;
        this.tiempo = tiempo;
        this.tipo = tipo;
        this.datetime = datetime;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(String habitacion) {
        this.habitacion = habitacion;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Userlog getIdlog() {
        return idlog;
    }

    public void setIdlog(Userlog idlog) {
        this.idlog = idlog;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ingventa)) {
            return false;
        }
        Ingventa other = (Ingventa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Ingventa[ id=" + id + " ]";
    }
    
}
