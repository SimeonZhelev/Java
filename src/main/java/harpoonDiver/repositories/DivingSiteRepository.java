package harpoonDiver.repositories;

import harpoonDiver.models.divingSite.DivingSite;
import harpoonDiver.models.divingSite.DivingSiteImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DivingSiteRepository implements Repository<DivingSite> {

    private Collection<DivingSite> divingSites;

    public DivingSiteRepository() {
        divingSites = new ArrayList<>();
    }

    @Override
    public Collection<DivingSite> getCollection() {
        return Collections.unmodifiableCollection(divingSites);
    }

    @Override
    public void add(DivingSite entity) {
        divingSites.add(entity);
    }

    @Override
    public boolean remove(DivingSite entity) {
        return divingSites.remove(entity);
    }

    @Override
    public DivingSite byName(String name) {
        return divingSites.stream()
                .filter(d -> d.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
