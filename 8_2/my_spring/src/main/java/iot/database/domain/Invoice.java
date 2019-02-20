package iot.database.domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.Random;

@Entity
@Table(name = "invoice")
public class Invoice {
    Invoice(){}

    Invoice(Date date, Provider provider) {
        this.date = date;
        this.providerByProvider = provider;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "number")
    private Long id;

    @Column(name = "data")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "provider_id", referencedColumnName = "id", nullable = false)
    private Provider providerByProvider;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Provider getProviderByProvider() {
        return providerByProvider;
    }

    public void setProviderByProvider(Provider providerByProvider) {
        this.providerByProvider = providerByProvider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Invoice that = (Invoice) o;

        if (date != that.date) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 123321;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
