package me.simp.quirkademia.configuration;

public enum ConfigType {

	QUIRKS("config_quirks.yml"),
	ABILITIES("config_abilities.yml"),
	CHAT("config_chat.yml");
	
	private String path;
	
	private ConfigType(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
}
