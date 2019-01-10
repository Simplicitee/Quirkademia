package me.simp.quirkademia.storage;

import java.util.Map;
import java.util.UUID;

import me.simp.quirkademia.quirk.QuirkUser;

public interface Storage {

	public void store(QuirkUser user);
	public Map<String, String> load(UUID uuid);
	public void close();
}
