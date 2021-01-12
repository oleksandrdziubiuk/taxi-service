package com.internet.shop.dao;

import com.internet.shop.db.Storage;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Manufacturer;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class ManufactureDaoImpl implements ManufactureDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        Storage.addManufacturer(manufacturer);
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        return Storage.manufacturers.stream()
                .filter(m -> Objects.equals(m.getId(), id))
                .findFirst();
    }

    @Override
    public List<Manufacturer> getAll() {
        return Storage.manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        int oldIndex = IntStream.range(0, Storage.manufacturers.size())
                .filter(index -> Objects.equals(Storage.manufacturers.get(index).getId(),
                        manufacturer.getId()))
                .findFirst()
                .getAsInt();
        Storage.manufacturers.set(oldIndex, manufacturer);
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.manufacturers.remove(get(id).orElse(null));
    }
}
