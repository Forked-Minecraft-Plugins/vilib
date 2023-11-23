package dev.efnilite.vilib.serialization;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class InventorySerializer {

    public static PlayerInventory deserialize64(Map<Integer, String> map) {
        PlayerInventory inventory = new PlayerInventory();
        map.keySet().forEach(slot -> {
            try {
                inventory.add(slot, ObjectSerializer.deserialize64(map.get(slot)));
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        return inventory;
    }

    public static Map<Integer, String> serialize64(PlayerInventory inventory) {
        return inventory.getItems().keySet().stream()
                .mapToInt(slot -> slot)
                .boxed()
                .collect(Collectors.toMap(slot -> slot, slot -> {
                    try {
                        return ObjectSerializer.serialize64(inventory.get(slot));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }, (a, b) -> b));
    }
}
