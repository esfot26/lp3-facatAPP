package unae.lp3.FacatAPP.model;


import java.sql.Date;
import java.time.LocalDateTime;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;




@Entity
@Table(name = "actas")
public class Acta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "acta_codi",
            length = 12,
            nullable = false,
            unique = true)
    private String codigo;
    @Column(name = "acta_archivo",
            length = 120,
            nullable = true)
    private String file;
    @Column(name = "acta_planilla",
            length = 120,
            nullable = true)
    private String sheet;
    @Column(name = "acta_fecha")
    private Date date;
    @Column(name = "acta_recibido")
    private Date rdate;
    @CreationTimestamp
    private LocalDateTime created_at;
    @UpdateTimestamp
    private LocalDateTime updated_at;
    @OneToOne
    @JoinColumn(name = "user_id")
    private Usuario usuario;
    @OneToOne
    @JoinColumn(name = "mate_id")
    private Materia materia;
    @OneToOne
    @JoinColumn(name = "oport_id")
    private Oportunidad oportunidad;

    public Acta() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getSheet() {
        return sheet;
    }

    public void setSheet(String sheet) {
        this.sheet = sheet;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getRdate() {
        return rdate;
    }

    public void setRdate(Date rdate) {
        this.rdate = rdate;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Oportunidad getOportunidad() {
        return oportunidad;
    }

    public void setOportunidad(Oportunidad oportunidad) {
        this.oportunidad = oportunidad;
    }

    @Override
    public String toString() {
        return "Acta [id=" + id + ", codigo=" + codigo + ", file=" + file + ", sheet=" + sheet + ", date=" + date
                + ", rdate=" + rdate + ", created_at=" + created_at + ", updated_at=" + updated_at + ", usuario="
                + usuario + ", materia=" + materia + ", oportunidad=" + oportunidad + ", getId()=" + getId()
                + ", getCodigo()=" + getCodigo() + ", getFile()=" + getFile() + ", getSheet()=" + getSheet()
                + ", getDate()=" + getDate() + ", getRdate()=" + getRdate() + ", getCreated_at()=" + getCreated_at()
                + ", getUpdated_at()=" + getUpdated_at() + ", getUsuario()=" + getUsuario() + ", getMateria()="
                + getMateria() + ", getOportunidad()=" + getOportunidad() + ", getClass()=" + getClass()
                + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }
}
