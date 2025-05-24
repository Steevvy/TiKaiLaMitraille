package com.TiKaiLaMitraille;

import net.runelite.client.config.*;

@ConfigGroup("example")
public interface ExampleConfig extends Config
{
	@ConfigItem(
			keyName = "enableTradeAccept",
			name = "Trade Accept",
			description = "Jouer un son lorsqu'un échange est accepté"
	)
	default boolean enableTradeAccept() { return true; }

	@ConfigItem(
			keyName = "enableTradeDecline",
			name = "Trade Decline",
			description = "Jouer un son lorsqu'un échange est refusé"
	)
	default boolean enableTradeDecline() { return true; }

	@ConfigItem(
			keyName = "enableLevelUp",
			name = "Level-Up",
			description = "Jouer un son lorsqu'un niveau est atteint"
	)
	default boolean enableLevelUp() { return true; }

	@ConfigItem(
			keyName = "enablePetDrop",
			name = "Pet Drop",
			description = "Jouer un son lorsqu'un familier est obtenu"
	)
	default boolean enablePetDrop() { return true; }

	@ConfigItem(
			keyName = "enableCombatAchievement",
			name = "Combat Achievement",
			description = "Jouer un son lors d’un Combat Achievement"
	)
	default boolean enableCombatAchievement() { return true; }

	@ConfigItem(
			keyName = "enableCollectionLog",
			name = "Collection Log",
			description = "Jouer un son lorsqu’un item est ajouté au Collection Log"
	)
	default boolean enableCollectionLog() { return true; }

	@ConfigItem(
			keyName = "enableNoPrayer",
			name = "No Prayer",
			description = "Jouer un son lorsque la prière tombe à zéro"
	)
	default boolean enableNoPrayer() { return true; }

	@ConfigItem(
			keyName = "enableDeath",
			name = "Death",
			description = "Jouer un son lors de la mort du joueur"
	)
	default boolean enableDeath() { return true; }
}
