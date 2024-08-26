package harpoonDiver.models.diver;

public class OpenWaterDiver extends BaseDiver {

    private static final double OXYGEN = 30;


    public OpenWaterDiver(String name) {
        super(name, OXYGEN);
    }
}
