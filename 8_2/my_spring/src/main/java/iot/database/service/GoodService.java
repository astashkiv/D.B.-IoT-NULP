package iot.database.service;

import iot.database.domain.Good;
import iot.database.domain.Provider;
import iot.database.exceptions.ExistsProviderForGoodException;
import iot.database.exceptions.NoSuchGoodException;
import iot.database.exceptions.NoSuchProviderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import iot.database.repository.GoodRepository;
import iot.database.repository.ProviderRepository;

import java.util.List;
import java.util.Set;

@Service
public class GoodService {
    @Autowired
    GoodRepository goodRepository;

    @Autowired
    ProviderRepository providerRepository;

    public Set<Good> getGoodsByProviderId(Long provider_id) throws NoSuchProviderException {
//        Provider provider = providerRepository.findOne(provider_id);//1.5.9
        Provider provider = providerRepository.findById(provider_id).get();//2.0.0.M7
        if (provider == null) throw new NoSuchProviderException();
        return provider.getGoods();
    }

    public Good getGood(Long good_id) throws NoSuchGoodException {
//        Good good = goodRepository.findOne(good_id);//1.5.9
        Good good = goodRepository.findById(good_id).get();//2.0.0.M7
        if (good == null) throw new NoSuchGoodException();
        return good;
    }

    public List<Good> getAllGoods() {
        return goodRepository.findAll();
    }

    @Transactional
    public void createGood(Good good) {
        goodRepository.save(good);
    }

    @Transactional
    public void updateGood(Good uGood, Long good_id) throws NoSuchGoodException {
        Good good = goodRepository.findById(good_id).get();
        if (good == null) throw new NoSuchGoodException();
        //update
        good.setBrand(uGood.getBrand());
        good.setPrice(uGood.getPrice());
        good.setCount(uGood.getCount());
    }

    @Transactional
    public void deleteGood(Long good_id) throws NoSuchGoodException, ExistsProviderForGoodException {
        Good good = goodRepository.findById(good_id).get();//2.0.0.M7

        if (good == null) throw new NoSuchGoodException();
        if (good.getProviders().size() != 0) throw new ExistsProviderForGoodException();
        goodRepository.delete(good);
    }
}
