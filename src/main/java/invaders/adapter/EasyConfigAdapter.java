package invaders.adapter;

public class EasyConfigAdapter implements ConfigAdapter{
    @Override
    public String getConfigPath() {
        return "src/main/resources/config_easy.json";
    }
}
