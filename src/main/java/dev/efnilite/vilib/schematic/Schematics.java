package dev.efnilite.vilib.schematic;

import dev.efnilite.vilib.ViMain;
import dev.efnilite.vilib.util.Time;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Schematics {

    private static final Map<Plugin, Map<String, Schematic>> cache = new HashMap<>();

    public static void addFromFiles(@NotNull Plugin plugin, @NotNull File... files) {
        Map<String, Schematic> current = cache.getOrDefault(plugin, new HashMap<>());

        for (File file : files) {
            Schematic schematic = Schematic.create().load(file);

            if (!schematic.isSupported()) {
                plugin.getLogger().info("Schematic %s is not supported.".formatted(file.getName()));
                continue;
            }

            current.put(file.getName(), schematic);
        }

        cache.put(plugin, current);

        plugin.getLogger().info("Found %d unsupported schematic(s).".formatted(files.length - current.keySet().size()));
        plugin.getLogger().info("Loaded all schematics!");
    }

    public static Schematic getSchematic(@NotNull Plugin plugin, @NotNull String schematicName) {
        return cache.get(plugin).get(schematicName);
    }

}
