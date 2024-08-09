/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ritesh
 */
@Entity
@Table(name = "type")
@NamedQueries({
    @NamedQuery(name = "Type.findAll", query = "SELECT t FROM Type t"),
    @NamedQuery(name = "Type.findById", query = "SELECT t FROM Type t WHERE t.id = :id"),
    @NamedQuery(name = "Type.findByName", query = "SELECT t FROM Type t WHERE t.name = :name"),
    @NamedQuery(name = "Type.findByCreatedAt", query = "SELECT t FROM Type t WHERE t.createdAt = :createdAt")})
public class Type implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @JsonbProperty("id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    @JsonbProperty("name")
    private String name;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonbProperty("created_at")
    private Date createdAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeId")
    @JsonbTransient
    private Collection<Business> businessCollection;

    public Type() {
    }

    public Type(Integer id) {
        this.id = id;
    }

    public Type(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Collection<Business> getBusinessCollection() {
        return businessCollection;
    }

    public void setBusinessCollection(Collection<Business> businessCollection) {
        this.businessCollection = businessCollection;
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
        if (!(object instanceof Type)) {
            return false;
        }
        Type other = (Type) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Type[ id=" + id + " ]";
    }
    
}
