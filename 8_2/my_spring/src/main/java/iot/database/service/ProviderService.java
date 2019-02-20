package iot.database.service;

import iot.database.domain.Good;
import iot.database.domain.Provider;
import iot.database.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import iot.database.repository.GoodRepository;
import iot.database.repository.InvoiceRepository;
import iot.database.repository.ProviderRepository;

import java.util.List;
import java.util.Set;

@Service
public class ProviderService {
    @Autowired
    ProviderRepository providerRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    GoodRepository goodRepository;

    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    public Provider getProvider(Long provider_id) throws NoSuchProviderException {
        Provider provider = providerRepository.findById(provider_id).get();
        if (provider == null) throw new NoSuchProviderException();
        return provider;
    }

    @Transactional
    public void createProvider(Provider provider) {
        providerRepository.save(provider);
    }


    @Transactional
    public void updateProvider(Provider uProvider, Long provider_id) throws NoSuchInvoiceException {
        Provider provider = providerRepository.findById(provider_id).get();
        if (provider == null) throw new NoSuchInvoiceException();
        provider.setName(uProvider.getName());
        provider.setDirector(uProvider.getDirector());
        provider.setPhone(uProvider.getPhone());
        providerRepository.save(provider);
    }

    @Transactional
    public void deleteProvider(Long provider_id) throws NoSuchProviderException, ExistsGoodForProviderException {
//        Provider provider = providerRepository.findOne(provider_id);//1.5.9
        Provider provider = providerRepository.findById(provider_id).get();//2.0.0.M7
        if (provider == null) throw new NoSuchProviderException();
        if (provider.getGoods().size() != 0) throw new ExistsGoodForProviderException();
        providerRepository.delete(provider);
    }

    public Set<Provider> getProvidersByGoodId(Long good_id) throws NoSuchGoodException {
//        Good good = goodRepository.findOne(good_id);//1.5.9
        Good good = goodRepository.findById(good_id).get();//2.0.0.M7
        if (good == null) throw new NoSuchGoodException();
        return good.getProviders();
    }

    @Transactional
    public void addGoodForProvider(Long provider_id, Long good_id)
            throws NoSuchProviderException, NoSuchGoodException, AlreadyExistsGoodInProviderException, GoodAbsentException {
        Provider provider = providerRepository.findById(provider_id).get();
        if (provider == null) throw new NoSuchProviderException();
        Good good = goodRepository.findById(good_id).get();
        if (good == null) throw new NoSuchGoodException();
        if (provider.getGoods().contains(good) == true) throw new AlreadyExistsGoodInProviderException();
        //if (good.getAmount() <= good.getProviders().size()) throw new GoodAbsentException();
        provider.getGoods().add(good);
        providerRepository.save(provider);
    }

    @Transactional
    public void removeGoodForProvider(Long provider_id, Long good_id)
            throws NoSuchProviderException, NoSuchGoodException, ProviderHasNoGoodException {
//        Provider provider = providerRepository.findOne(provider_id);//1.5.9
        Provider provider = providerRepository.findById(provider_id).get();//2.0.0.M7
        if (provider == null) throw new NoSuchProviderException();
//        Good good = goodRepository.findOne(good_id);//1.5.9
        Good good = goodRepository.findById(good_id).get();//2.0.0.M7
        if (good == null) throw new NoSuchGoodException();
        if (provider.getGoods().contains(good) == false) throw new ProviderHasNoGoodException();
        provider.getGoods().remove(good);
        providerRepository.save(provider);
    }
}
