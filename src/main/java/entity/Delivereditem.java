/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "delivereditem")
@NamedQueries({
    @NamedQuery(name = "Delivereditem.findAll", query = "SELECT d FROM Delivereditem d"),
    @NamedQuery(name = "Delivereditem.findById", query = "SELECT d FROM Delivereditem d WHERE d.id = :id"),
    @NamedQuery(name = "Delivereditem.findByName", query = "SELECT d FROM Delivereditem d WHERE d.name = :name"),
    @NamedQuery(name = "Delivereditem.findByPrice", query = "SELECT d FROM Delivereditem d WHERE d.price = :price"),
    @NamedQuery(name = "Delivereditem.findByQuantity", query = "SELECT d FROM Delivereditem d WHERE d.quantity = :quantity"),
    @NamedQuery(name = "Delivereditem.findByDeliveryId", query = "SELECT d FROM Delivereditem d WHERE d.deliveryId = :delivery_id"),
    @NamedQuery(name = "Delivereditem.findByCreatedAt", query = "SELECT d FROM Delivereditem d WHERE d.createdAt = :createdAt")})
public class Delivereditem implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    @JsonbProperty("price")
    private int price;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    @JsonbProperty("quantity")
    private int quantity;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonbProperty("created_at")
    private Date createdAt;
    @JoinColumn(name = "business_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonbTransient
    private Business businessId;
    @JoinColumn(name = "delivery_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonbTransient
    private Delivery deliveryId;
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonbProperty("product")
    private Product productId;

    public Delivereditem() {
    }

    public Delivereditem(Integer id) {
        this.id = id;
    }

    public Delivereditem(Integer id, String name, int price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Business getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Business businessId) {
        this.businessId = businessId;
    }

    public Delivery getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Delivery deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
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
        if (!(object instanceof Delivereditem)) {
            return false;
        }
        Delivereditem other = (Delivereditem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Delivereditem[ id=" + id + " ]";
    }

}
