package com.TiKaiLaMitraille;

import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.StatChanged;
import net.runelite.api.ChatMessageType;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;

@Slf4j
@PluginDescriptor(name = "Ti-Kai la mitraille")
public class ExamplePlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ExampleConfig config;

	private final Map<Skill, Integer> lastKnownLevels = new EnumMap<>(Skill.class);
	private int lastPrayerLevel = -1;

	@Provides
	ExampleConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ExampleConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		log.info("🔊 Plugin Ti-Kai la mitraille activé.");
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		if (event.getType() == ChatMessageType.GAMEMESSAGE)
		{
			String msg = event.getMessage().toLowerCase();

			if (msg.contains("accepted trade") && config.enableTradeAccept())
			{
				log.info("✅ Trade accepté → jouer tradeAccept.wav");
				playSound("tradeAccept.wav");
			}

			if (msg.contains("declined trade") && config.enableTradeDecline())
			{
				log.info("❌ Trade refusé → jouer tradeDecline.wav");
				playSound("tradeDecline.wav");
			}

			if ((msg.contains("funny feeling") || msg.contains("étrange impression")) && config.enablePetDrop())
			{
				log.info("🐾 Pet drop détecté → jouer petDrop.wav");
				playSound("petDrop.wav");
			}

			if (msg.contains("combat achievement") && config.enableCombatAchievement())
			{
				log.info("🏆 Combat Achievement détecté → jouer combatAchievement.wav");
				playSound("combatAchievement.wav");
			}

			if (msg.contains("new item added to your collection log") && config.enableCollectionLog())
			{
				log.info("📘 Nouvel item dans Collection Log → jouer collectionLog.wav");
				playSound("collectionLog.wav");
			}

			if (msg.contains("oh dear, you are dead") && config.enableDeath())
			{
				log.info("☠️ Mort détectée → jouer death.wav");
				playSound("death.wav");
			}
		}
	}

	@Subscribe
	public void onStatChanged(StatChanged event)
	{
		if (event.getSkill() == Skill.PRAYER && config.enableNoPrayer())
		{
			int current = event.getBoostedLevel();
			if (current == 0 && lastPrayerLevel > 0)
			{
				log.info("🙏 Prière tombée à 0 → jouer noPrayer.wav");
				playSound("noPrayer.wav");
			}
			lastPrayerLevel = current;
		}

		if (config.enableLevelUp())
		{
			Skill skill = event.getSkill();
			int newLevel = event.getLevel();
			Integer previousLevel = lastKnownLevels.get(skill);
			if (previousLevel != null && newLevel > previousLevel)
			{
				log.info("⬆️ Level up dans " + skill + " : " + previousLevel + " → " + newLevel);
				playSound("levelUp.wav");
			}
			lastKnownLevels.put(skill, newLevel);
		}
	}

	private void playSound(String fileName)
	{
		new Thread(() -> {
			try
			{
				InputStream soundStream = getClass().getResourceAsStream("/" + fileName);
				if (soundStream == null)
				{
					log.warn("⚠️ Fichier " + fileName + " introuvable !");
					return;
				}
				AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundStream);
				Clip clip = AudioSystem.getClip();
				clip.open(audioStream);
				clip.start();
				log.info("🔊 Son " + fileName + " joué !");
			}
			catch (Exception e)
			{
				log.error("Erreur de lecture du son :", e);
			}
		}).start();
	}
}
