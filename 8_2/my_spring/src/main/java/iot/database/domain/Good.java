package iot.database.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "good")
public class Good {
    Good(){}

    Good(String brand, double price, int count) {
        this.brand = brand;
        this.price = price;
        this.count = count;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "price")
    private double price;

    @Column(name = "count")
    private int count;

    @ManyToMany
    @JoinTable(name = "good_provider", joinColumns = @JoinColumn(name = "good_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "provider_id", referencedColumnName = "id", nullable = false))
    private Set<Provider> providers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Provider> getProviders() {
        return providers;
    }

    public void setProviders(Set<Provider> providers) {
        this.providers = providers;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Good that = (Good) o;

        if (id != that.id) return false;
        if (price != that.price) return false;
        if (count != that.count) return false;
        if (brand != null ? !brand.equals(that.brand) : that.brand != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) price;
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        return result;
    }
}
