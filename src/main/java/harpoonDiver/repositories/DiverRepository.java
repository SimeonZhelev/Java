package harpoonDiver.repositories;

import harpoonDiver.models.diver.Diver;

import java.util.*;

public class DiverRepository implements Repository<Diver> {

    private Collection<Diver> divers;

    public DiverRepository() {
        divers = new ArrayList<>();
    }

    @Override
    public Collection<Diver> getCollection() {
        return Collections.unmodifiableCollection(divers);
    }

    @Override
    public void add(Diver entity) {
        divers.add(entity);
    }

    @Override
    public boolean remove(Diver entity) {
        return divers.remove(entity);
    }

    @Override
    public Diver byName(String name) {
        return divers.stream()
                .filter(d ->d.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
