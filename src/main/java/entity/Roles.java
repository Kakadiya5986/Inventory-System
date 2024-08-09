/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ritesh
 */
@Entity
@Table(name = "roles")
@NamedQueries({
    @NamedQuery(name = "Roles.findAll", query = "SELECT r FROM Roles r"),
    @NamedQuery(name = "Roles.findByRolesId", query = "SELECT r FROM Roles r WHERE r.rolesId = :rolesId"),
    @NamedQuery(name = "Roles.findByGroupname", query = "SELECT r FROM Roles r WHERE r.groupname = :groupname"),
    @NamedQuery(name = "Roles.findByUsername", query = "SELECT r FROM Roles r WHERE r.username = :username")})
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "roles_id")
    @JsonbProperty("role_id")
    private Integer rolesId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "groupname")
    @JsonbProperty("groupname")
    private String groupname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "username")
    @JsonbProperty("username")
    private String username;

    public Roles() {
    }

    public Roles(Integer rolesId) {
        this.rolesId = rolesId;
    }

    public Roles(Integer rolesId, String groupname, String username) {
        this.rolesId = rolesId;
        this.groupname = groupname;
        this.username = username;
    }

    public Integer getRolesId() {
        return rolesId;
    }

    public void setRolesId(Integer rolesId) {
        this.rolesId = rolesId;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rolesId != null ? rolesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roles)) {
            return false;
        }
        Roles other = (Roles) object;
        if ((this.rolesId == null && other.rolesId != null) || (this.rolesId != null && !this.rolesId.equals(other.rolesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Roles[ rolesId=" + rolesId + " ]";
    }

}
