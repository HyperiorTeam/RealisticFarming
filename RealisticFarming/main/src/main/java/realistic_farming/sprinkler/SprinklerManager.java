package realistic_farming.sprinkler;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

import realistic_farming.config.SprinklerData;

public class SprinklerManager {
	
	private static HashMap<String, Sprinkler> sprinklers = new HashMap<String, Sprinkler>();
	
	public static void createSprinkler(String id, Location l) {
		
		sprinklers.put(id, new Sprinkler(id, l));
		
		SprinklerUtils.SpawnSprinkler(id, l);
		SprinklerData.addSprinkler(id, l);
		
	}
	
	public static void destroySprinkler(String id) {
		//Bukkit.getConsoleSender().sendMessage(SprinklerData.getConfig().getKeys(false).toArray()[0].toString());
		Location l = (Location) SprinklerData.getConfig().get(id + ".location");
		
		ArmorStand sprinkler = null, rotator = null;
		
		for(Entity e : l.getWorld().getNearbyEntities(l, 1, 1, 1)) {
			
			if(e.getCustomName() != null && e.getCustomName().startsWith("Sprinkler ID: ")) {
				
				if(((ArmorStand) e).getHelmet().getType().equals(Material.IRON_BLOCK)) {
					
					sprinkler = (ArmorStand) e;
					
				}else if(((ArmorStand) e).getHelmet().getType().equals(Material.LEVER)) {
					
					rotator = (ArmorStand) e;
					
				}
				
			}
			
		}
		
		SprinklerUtils.DestroySprinkler(sprinkler, rotator);
		SprinklerData.removeSprinkler(id);
		
		sprinklers.remove(id);
		
	}
	
	public static void loadSprinklers() {
		
		for(String id : SprinklerData.getSprinklers()) {
			
			Location l = (Location) SprinklerData.getConfig().get(id + ".location");
			
			sprinklers.put(id, new Sprinkler(id, l));
			
			ArmorStand sprinkler = null, rotator = null;
			
			for(Entity e : l.getWorld().getNearbyEntities(l, 1, 1, 1)) {
				
				if(e.getCustomName() != null && e.getCustomName().startsWith("Sprinkler ID:")) {
					
					if(((ArmorStand) e).getHelmet().getType().equals(Material.IRON_BLOCK)) {
						
						sprinkler = (ArmorStand) e;
						
					}else if(((ArmorStand) e).getHelmet().getType().equals(Material.LEVER)) {
						
						rotator = (ArmorStand) e;
						
					}
					
				}
				
			}
			
			SprinklerUtils.anim(sprinkler, rotator, l);
			
		}
		
	}
	
	public static Sprinkler getSprinklerById(String id) {
		
		return sprinklers.get(id);
		
	}
	
}
