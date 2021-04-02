package breakthenexus.game.details.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ListenerWorld implements Listener {

    @EventHandler
    private void onWeatherChange(WeatherChangeEvent weatherChangeEvent) {
        weatherChangeEvent.setCancelled(true);
    }

    @EventHandler
    private void onCreatureSpawn(CreatureSpawnEvent creatureSpawnEvent) {
        creatureSpawnEvent.setCancelled(true);
    }

}
