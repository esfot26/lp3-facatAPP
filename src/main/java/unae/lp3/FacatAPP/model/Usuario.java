package unae.lp3.FacatAPP.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

//import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;




@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_nombre", length = 100, nullable = false)
    @NotEmpty(message = "El nombre no puede estar vacío")
    private String name;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    @NotEmpty(message = "El nombre de usuario no puede estar vacío")
    private String username;

    @Column(name = "password", length = 160)
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @Column(name = "user_activo")
    private Boolean active = false;

    @Column(name = "user_mail", length = 100, nullable = false, unique = true)
    @Email(message = "Debe ser una dirección de correo electrónico válida")
    @NotEmpty(message = "El correo electrónico no puede estar vacío")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usr_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Collection<Rol> roles = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String email, String encode) {
		
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public Collection<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Rol> roles) {
		this.roles = roles;
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

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password
                + ", active=" + active + ", email=" + email + ", roles=" + roles + ", created_at=" + created_at
                + ", updated_at=" + updated_at + ", getId()=" + getId() + ", getName()=" + getName()
                + ", getUsername()=" + getUsername() + ", getPassword()=" + getPassword() + ", getActive()="
                + getActive() + ", getEmail()=" + getEmail() + ", getRoles()=" + getRoles() + ", getCreated_at()="
                + getCreated_at() + ", getUpdated_at()=" + getUpdated_at() + ", getClass()=" + getClass()
                + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }
}