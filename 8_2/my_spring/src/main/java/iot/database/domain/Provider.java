package iot.database.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "provider")
public class Provider {
    Provider(){}

    Provider(String name, String director, String phone) {
        this.name = name;
        this.director = director;
        this.phone = phone;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "director")
    private String director;

    @Column(name = "phone")
    private String phone;

    @ManyToMany(mappedBy = "providers")
    private Set<Good> goods;

    @OneToMany(mappedBy = "providerByProvider")
    private Set<Invoice> invoices;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Good> getGoods() {
        return goods;
    }

    public void setGoods(Set<Good> goods) {
        this.goods = goods;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Provider that = (Provider) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (director != null ? !director.equals(that.director) : that.director != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 261;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (director != null ? director.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }
}
