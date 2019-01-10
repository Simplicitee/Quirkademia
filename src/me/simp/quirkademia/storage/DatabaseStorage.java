package me.simp.quirkademia.storage;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.scheduler.BukkitRunnable;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkUser;

public class DatabaseStorage implements Storage {
	
	public QuirkPlugin plugin;
	public DatabaseType type;
	public DatabaseConnection connection;
	
	public DatabaseStorage(QuirkPlugin plugin, DatabaseType type) {
		this.plugin = plugin;
		this.type = type;
		this.connection = new DatabaseConnection(plugin, type);
		
		if (!connection.tableExists("players")) {
			String query = databaseDatatypes("CREATE TABLE `players` (`uuid` %text%(36) NOT NULL, `quirk` %text%(255));");
			connection.modifyQuery(query, false);
		}
		
		if (plugin.getConfigs().getConfiguration(ConfigType.PROPERTIES).getBoolean("Storage.SaveCooldowns")) {
			if (!connection.tableExists("cooldown_ids")) {
				String query = databaseDatatypes("CREATE TABLE `cooldown_ids` (`id` INTEGER PRIMARY KEY %auto%, `cooldown_name` %text%(256) NOT NULL);");
				connection.modifyQuery(query, false);
			}
			
			if (!connection.tableExists("cooldowns")) {
				String query = databaseDatatypes("CREATE TABLE `cooldowns` (`uuid` %text%(36) NOT NULL, `cooldown_id` INTEGER NOT NULL, `remaining` BIGINT, PRIMARY KEY (uuid, cooldown_id));");
				connection.modifyQuery(query, false);
			}
		}
	}

	@Override
	public void store(QuirkUser user) {
		if (user.getQuirk() == null) {
			return;
		}
		
		String uuid = user.getUniqueId().toString();
		String quirk = user.getQuirk().getName();
		
		connection.modifyQuery("UPDATE players SET quirk = '" + quirk + "' WHERE uuid = '" + uuid + "'", false);
		
		if (plugin.getConfigs().getConfiguration(ConfigType.PROPERTIES).getBoolean("Storage.SaveCooldowns")) {
			for (String name : user.getCooldowns().keySet()) {
				int id = getCooldownID(name);
				
				connection.modifyQuery("INSERT INTO cooldowns (uuid, cooldown_id, remaining) VALUES ('" + uuid + "', '" + id + "', '" + user.getCooldowns().get(name).getRemaining() + "')" , false);
			}
		}
	}

	@Override
	public Map<String, String> load(UUID uuid) {
		Map<String, String> storage = new HashMap<>();
		ResultSet rs = connection.readQuery("SELECT * FROM players WHERE uuid = '" + uuid.toString() + "'");
		
		try {
			if (!rs.next()) {
				connection.modifyQuery("INSERT INTO players (uuid, quirk) VALUES ('" + uuid.toString() + "', 'null')", true);
			} else {
				storage.put("quirk", rs.getString("quirk"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (plugin.getConfigs().getConfiguration(ConfigType.PROPERTIES).getBoolean("Storage.SaveCooldowns")) {
			ResultSet rs2 = connection.readQuery("SELECT * FROM cooldowns WHERE uuid = '" + uuid.toString() + "'");
			
			try {
				while (rs2.next()) {
					int id = rs2.getInt("cooldown_id");
					String cooldown = "unknown cooldown" + id;
					ResultSet rs3 = connection.readQuery("SELECT * FROM cooldown_ids WHERE id = '" + id + "'");
					if (rs3.next()) {
						cooldown = rs3.getString("cooldown_name");
					}
					
					storage.put("cooldown." + cooldown, "" + rs2.getLong("remaining"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return storage;
	}
	
	public int getCooldownID(String name) {
		ResultSet rs = connection.readQuery("SELECT id FROM cooldown_ids WHERE cooldown_name = '" + name + "'");
		try {
			if (rs.next()) {
				return rs.getInt("id");
			} else {
				connection.modifyQuery("INSERT INTO cooldown_ids (name) VALUES ('" + name + "')", true);
				return getCooldownID(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public String databaseDatatypes(String query) {
		switch (type) {
		case MYSQL: return query.replace("%text%", "VARCHAR").replace("%auto%", "AUTO_INCREMENT");
		case SQLITE: return query.replace("%text%", "TEXT").replace("%auto%", "AUTOINCREMENT");
		default: return query;
		}
	}

	public static enum DatabaseType {
		SQLITE("[SQLite]"), MYSQL("[MySQL]");
		
		private String prefix;
		
		private DatabaseType(String prefix) {
			this.prefix = prefix;
		}
		
		public String getPrefix() {
			return prefix;
		}
	}
	
	public class DatabaseConnection {
		
		private DatabaseType type;
		private Connection connection;
		private String host, db, user, pass;
		private int port;
		
		private DatabaseConnection(QuirkPlugin plugin, DatabaseType type) {
			this.type = type;
			this.host = plugin.getConfigs().getConfiguration(ConfigType.PROPERTIES).getString("Storage.MySQL.host");
			this.db = plugin.getConfigs().getConfiguration(ConfigType.PROPERTIES).getString("Storage.MySQL.db");
			this.user = plugin.getConfigs().getConfiguration(ConfigType.PROPERTIES).getString("Storage.MySQL.user");
			this.pass = plugin.getConfigs().getConfiguration(ConfigType.PROPERTIES).getString("Storage.MySQL.pass");
			this.port = plugin.getConfigs().getConfiguration(ConfigType.PROPERTIES).getInt("Storage.MySQL.port");
			this.connection = open();
		}
		
		public Connection get()  {
			return connection;
		}
		
		private Connection open() {
			Connection connection = null;
			try {
				if (type == DatabaseType.MYSQL) {
					Class.forName("com.mysql.jdbc.Driver");

					final String url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.db;

					connection = DriverManager.getConnection(url, this.user, this.pass);
				} else {
					Class.forName("org.sqlite.JDBC");

					connection = DriverManager.getConnection("jdbc:sqlite:" + new File(plugin.getDataFolder(), "storage.db").getAbsolutePath());
				}
			} catch (final ClassNotFoundException e) {
				plugin.getLogger().severe("JDBC driver not found!");
				return null;
			} catch (final SQLException e) {
				e.printStackTrace();
				plugin.getLogger().severe(type.getPrefix() + "exception during connection!");
				return null;
			}
			
			plugin.getLogger().info(type.getPrefix() + " Connection established!");
			
			return connection;
		}
		
		public void close() {
			if (this.connection != null) {
				try {
					this.connection.close();
				}
				catch (final SQLException e) {
					e.printStackTrace();
				}
			} else {
				plugin.getLogger().warning("There was no SQL connection open");
			}
		}

		public void modifyQuery(final String query, final boolean async) {
			if (async) {
				new BukkitRunnable() {
					@Override
					public void run() {
						doQuery(query);
					}
				}.runTaskAsynchronously(plugin);
			} else {
				this.doQuery(query);
			}
		}
		
		public ResultSet readQuery(final String query) {
			try {
				if (this.connection == null || this.connection.isClosed()) {
					this.open();
				}
				
				final PreparedStatement stmt = this.connection.prepareStatement(query);
				final ResultSet rs = stmt.executeQuery();

				return rs;
			}
			catch (final SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public boolean tableExists(final String table) {
			try {
				if (this.connection == null || this.connection.isClosed()) {
					this.open();
				}
				final DatabaseMetaData dmd = this.connection.getMetaData();
				final ResultSet rs = dmd.getTables(null, null, table, null);

				return rs.next();
			}
			catch (final Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		private synchronized void doQuery(final String query) {
			try {
				if (this.connection == null || this.connection.isClosed()) {
					this.open();
				}
				
				final PreparedStatement stmt = this.connection.prepareStatement(query);
				stmt.execute();
				stmt.close();
			}
			catch (final SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void close() {
		connection.close();
	}
}
