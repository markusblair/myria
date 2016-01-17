package org.adventure.monster;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.adventure.character.PlayerState;
import org.adventure.random.RandomCollection;
import org.adventure.rooms.Room;

public class MonsterSpawner {
	protected static Timer timer = new Timer();
	private int cycleTime = 60000;
	private int cyclesToRegenerate;
	private List<Monster> spawnedMonsters = new ArrayList<Monster>();
	private IMonsterFactory monsterFactory;
	private int maxMonsterCount;
	private RandomCollection<Room> rooms = new RandomCollection<Room>();
	
	
	private TimerTask task = new TimerTask() {
		@Override
		public void run() {
			List<Monster> monstersToRemove = new ArrayList<Monster>();
			for (Monster monster : spawnedMonsters) {
				if (PlayerState.DEAD.equals(monster.getPlayerState())) {
					monster.setCurrentRoom(null);
					monstersToRemove.add(monster);
				}
			}
			for (Monster monster : monstersToRemove) {
				spawnedMonsters.remove(monster);
			}
			int currentMonsterCount = spawnedMonsters.size();
			
			int availableMonsters = maxMonsterCount - currentMonsterCount;
			
			if (availableMonsters > 0) {
				int monstersToSpawn = (availableMonsters / getCyclesToRegenerate()) + 1;
				while (monstersToSpawn-- > 0) {
					Room room = rooms.next();
					if (room != null) {
						Monster monster = monsterFactory.createMonster();					
						monster.gotoRoom(room);
						spawnedMonsters.add(monster);
					}
				}				
			}
		}
	};
	
	public MonsterSpawner(IMonsterFactory monsterFactory, int cycleTime, int cyclesToRegenerate) {
		super();
		this.monsterFactory = monsterFactory;
		this.cycleTime = cycleTime;
		this.cyclesToRegenerate = cyclesToRegenerate;
		timer.scheduleAtFixedRate(task, 0, getCycleTime());
	}

	public void addRoom(Room room) {
		this.rooms.add(1d, room);
	}
	
	public int getCycleTime() {
		return cycleTime;
	}

	public void setCycleTime(int cycleTime) {
		this.cycleTime = cycleTime;
	}

	public int getCyclesToRegenerate() {
		return cyclesToRegenerate;
	}

	public void setCyclesToRegenerate(int cyclesToRegenerate) {
		this.cyclesToRegenerate = cyclesToRegenerate;
	}

	public int getMaxMonsterCount() {
		return maxMonsterCount;
	}

	public void setMaxMonsterCount(int maxMonsterCount) {
		this.maxMonsterCount = maxMonsterCount;
	}
	
	
	
	
}
