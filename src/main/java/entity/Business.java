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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "business")
@NamedQueries({
    @NamedQuery(name = "Business.findAll", query = "SELECT b FROM Business b"),
    @NamedQuery(name = "Business.findById", query = "SELECT b FROM Business b WHERE b.id = :id"),
    @NamedQuery(name = "Business.findByBusinessName", query = "SELECT b FROM Business b WHERE b.businessName = :businessName"),
    @NamedQuery(name = "Business.findByAddress", query = "SELECT b FROM Business b WHERE b.address = :address"),
    @NamedQuery(name = "Business.findByCreatedAt", query = "SELECT b FROM Business b WHERE b.createdAt = :createdAt")})
public class Business implements Serializable {

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
    @Column(name = "business_name")
    @JsonbProperty("business_name")
    private String businessName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "address")
    @JsonbProperty("business_address")
    private String address;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonbProperty("created_at")
    private Date createdAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "businessId")
    @JsonbTransient
    private Collection<Delivery> deliveryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "businessId")
    @JsonbTransient
    private Collection<Product> productCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "businessId")
    @JsonbTransient
    private Collection<Society> societyCollection;
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonbTransient
    private Type typeId;
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonbProperty("owner")
    private User ownerId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "businessId")
    @JsonbTransient
    private Collection<Delivereditem> delivereditemCollection;
    @OneToMany(mappedBy = "businessId")
    @JsonbTransient
    private Collection<User> userCollection;

    public Business() {
    }

    public Business(Integer id) {
        this.id = id;
    }

    public Business(Integer id, String businessName, String address) {
        this.id = id;
        this.businessName = businessName;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Collection<Delivery> getDeliveryCollection() {
        return deliveryCollection;
    }

    public void setDeliveryCollection(Collection<Delivery> deliveryCollection) {
        this.deliveryCollection = deliveryCollection;
    }

    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
    }

    public Collection<Society> getSocietyCollection() {
        return societyCollection;
    }

    public void setSocietyCollection(Collection<Society> societyCollection) {
        this.societyCollection = societyCollection;
    }

    public Type getTypeId() {
        return typeId;
    }

    public void setTypeId(Type typeId) {
        this.typeId = typeId;
    }

    public User getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(User ownerId) {
        this.ownerId = ownerId;
    }

    public Collection<Delivereditem> getDelivereditemCollection() {
        return delivereditemCollection;
    }

    public void setDelivereditemCollection(Collection<Delivereditem> delivereditemCollection) {
        this.delivereditemCollection = delivereditemCollection;
    }

    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
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
        if (!(object instanceof Business)) {
            return false;
        }
        Business other = (Business) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Business[ id=" + id + " ]";
    }

}
