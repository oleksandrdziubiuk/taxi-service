package com.internet.shop.service.impl;

import com.internet.shop.dao.ManufactureDao;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.Manufacturer;
import com.internet.shop.service.ManufactureService;
import java.util.List;

@Service
public class ManufactureServiceImpl implements ManufactureService {
    @Inject
    private ManufactureDao manufactureDao;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        return manufactureDao.create(manufacturer);
    }

    @Override
    public Manufacturer get(Long id) {
        return manufactureDao.get(id).orElse(null);
    }

    @Override
    public List<Manufacturer> getAll() {
        return manufactureDao.getAll();
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return manufactureDao.update(manufacturer);
    }

    @Override
    public boolean delete(Long id) {
        return manufactureDao.delete(id);
    }
}
