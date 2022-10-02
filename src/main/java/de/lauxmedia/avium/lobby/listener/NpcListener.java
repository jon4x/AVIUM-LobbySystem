package de.lauxmedia.avium.lobby.listener;

import com.github.juliarn.npc.NPC;
import com.github.juliarn.npc.event.PlayerNPCInteractEvent;
import com.github.juliarn.npc.event.PlayerNPCShowEvent;
import com.github.juliarn.npc.modifier.MetadataModifier;
import de.lauxmedia.avium.lobby.inventories.Rewards;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class NpcListener implements Listener {

    @EventHandler
    public void handleNPCShow(PlayerNPCShowEvent event) {
        NPC npc = event.getNPC();
        // sending the data only to the player from the event
        event.send(npc.metadata().queue(MetadataModifier.EntityMetadata.SKIN_LAYERS, true));
    }

    @EventHandler
    public void handleNPCInteract(PlayerNPCInteractEvent event) {
        Player player = event.getPlayer();
        NPC npc = event.getNPC();
        // checking if the player interact at the NPC
        if (npc.getProfile().getName().contains("REWARD")) {
            if (event.getUseAction() == PlayerNPCInteractEvent.EntityUseAction.INTERACT_AT) {
                if (event.getHand() == PlayerNPCInteractEvent.Hand.MAIN_HAND) {
                    Rewards.openRewardsInventory(player);
                }
            }
        }
    }

}
