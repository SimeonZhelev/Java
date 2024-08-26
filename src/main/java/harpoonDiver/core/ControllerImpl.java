package harpoonDiver.core;

import harpoonDiver.models.diver.*;
import harpoonDiver.models.diving.Diving;
import harpoonDiver.models.diving.DivingImpl;
import harpoonDiver.models.divingSite.DivingSite;
import harpoonDiver.models.divingSite.DivingSiteImpl;
import harpoonDiver.repositories.DiverRepository;
import harpoonDiver.repositories.DivingSiteRepository;


import java.util.List;
import java.util.stream.Collectors;

import static harpoonDiver.common.ConstantMessages.*;
import static harpoonDiver.common.ExceptionMessages.*;

public class ControllerImpl implements Controller{

    private DiverRepository diverRepository;
    private DivingSiteRepository divingSiteRepository;
    private int sitesCount;

    public ControllerImpl() {
        this.diverRepository = new DiverRepository();
        this.divingSiteRepository = new DivingSiteRepository();
        this.sitesCount = 0;
    }

    @Override
    public String addDiver(String kind, String diverName) {
        Diver diver;
        switch (kind){
            case "OpenWaterDiver":
                diver = new OpenWaterDiver(diverName);
                break;
            case "DeepWaterDiver":
                diver = new DeepWaterDiver(diverName);
                break;
            case "WreckDiver":
                diver = new WreckDiver(diverName);
                break;
            default:
                throw new IllegalArgumentException(DIVER_INVALID_KIND);
        }
        diverRepository.add(diver);
        return String.format(DIVER_ADDED, kind, diverName);
    }

    @Override
    public String addDivingSite(String siteName, String... seaCreatures) {
        DivingSiteImpl divingSite = new DivingSiteImpl(siteName);
        for (String seaCreature : seaCreatures) {
            divingSite.getSeaCreatures().add(seaCreature);
        }
        divingSiteRepository.add(divingSite);
        return String.format(DIVING_SITE_ADDED,siteName);
    }

    @Override
    public String removeDiver(String diverName) {
        Diver diverToRemove = diverRepository.byName(diverName);
        if (diverToRemove == null){
            throw new IllegalArgumentException(String.format(DIVER_DOES_NOT_EXIST,diverName));
        }
        diverRepository.remove(diverToRemove);
        return String.format(DIVER_REMOVE,diverName);
    }

    @Override
    public String startDiving(String siteName) {
        List<Diver> validDivers = diverRepository
                .getCollection()
                .stream()
                .filter(d -> d.getOxygen() > 30)
                .collect(Collectors.toList());
        if (validDivers.isEmpty()){
            throw new IllegalArgumentException(SITE_DIVERS_DOES_NOT_EXISTS);
        }
        DivingSite divingSite = divingSiteRepository.byName(siteName);
        Diving diving = new DivingImpl();
        diving.searching(divingSite, validDivers);
        long countOfTiredDivers = validDivers.stream().filter(d -> d.getOxygen() == 0).count();
        sitesCount++;
        return String.format(SITE_DIVING,siteName,countOfTiredDivers);
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(FINAL_DIVING_SITES,sitesCount));
        sb.append(System.lineSeparator());
        sb.append(FINAL_DIVERS_STATISTICS);
        sb.append(System.lineSeparator());

        for (Diver diver : this.diverRepository.getCollection()) {
            sb.append(String.format(FINAL_DIVER_NAME, diver.getName())).append(System.lineSeparator());
            sb.append(String.format(FINAL_DIVER_OXYGEN, diver.getOxygen())).append(System.lineSeparator());
            if (diver.getSeaCatch().getSeaCreatures().isEmpty()) {
                sb.append(String.format(FINAL_DIVER_CATCH, "None")).append(System.lineSeparator());
            } else {
                sb.append(String.format(FINAL_DIVER_CATCH, String.join(FINAL_DIVER_CATCH_DELIMITER, diver.getSeaCatch().getSeaCreatures()))).append(System.lineSeparator());
            }
        }
        return sb.toString().trim();
    }
}

