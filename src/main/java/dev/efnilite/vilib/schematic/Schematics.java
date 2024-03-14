package dev.efnilite.vilib.schematic;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class Schematics {

    private static final Map<Plugin, Map<String, Schematic>> cache = new HashMap<>();

    public static void addFromFiles(@NotNull Plugin plugin, @NotNull File... files) throws ExecutionException, InterruptedException {
        Map<String, Schematic> current = cache.getOrDefault(plugin, new HashMap<>());

        for (File file : files) {
            Schematic schematic = Schematic.load(file, plugin);

            if (!schematic.isSupported()) {
                plugin.getLogger().warning("Schematic %s is not supported.".formatted(file.getName()));
                continue;
            }

            current.put(file.getName(), schematic);
        }

        cache.put(plugin, current);

        plugin.getLogger().warning("Found %d unsupported schematic(s).".formatted(files.length - current.keySet().size()));
        plugin.getLogger().info("Loaded all schematics!");
    }

    public static Schematic getSchematic(@NotNull Plugin plugin, @NotNull String schematicName) {
        return cache.get(plugin).get(schematicName);
    }

    public static Set<String> getSchematicNames(@NotNull Plugin plugin) {
        return cache.get(plugin).keySet();
    }

    public static Collection<Schematic> getSchematics(@NotNull Plugin plugin) {
        return cache.get(plugin).values();
    }
}