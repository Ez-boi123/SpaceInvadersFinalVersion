package invaders.adapter;

public class HardConfigAdapter implements ConfigAdapter{
    @Override
    public String getConfigPath() {
        return "src/main/resources/config_hard.json";
    }
}
