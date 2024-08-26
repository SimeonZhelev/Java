package harpoonDiver.models.diving;

import harpoonDiver.models.diver.Diver;
import harpoonDiver.models.divingSite.DivingSite;

import java.util.Collection;

public class DivingImpl implements Diving{
    @Override
    public void searching(DivingSite divingSite, Collection<Diver> divers) {

        Collection<String> seaCreatures = divingSite.getSeaCreatures();

        for (Diver diver : divers) {
            while(diver.canDive() && seaCreatures.iterator().hasNext()){
                diver.shoot();
                String currentSeaCreature = seaCreatures.iterator().next();
                diver.getSeaCatch().getSeaCreatures().add(currentSeaCreature);
                seaCreatures.remove(currentSeaCreature);
            }
        }

    }
}
