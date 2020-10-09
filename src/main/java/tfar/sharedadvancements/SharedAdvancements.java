package tfar.sharedadvancements;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.Mod;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SharedAdvancements.MODID)
public class SharedAdvancements {

    public static final String MODID = "sharedadvancements";

    public SharedAdvancements() {
        MinecraftForge.EVENT_BUS.addListener(this::onAdvancement);
    }

    private void onAdvancement(AdvancementEvent e) {
        MinecraftServer server = e.getPlayer().getServer();
        for (ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            if (player instanceof FakePlayer) {
                continue;
            }
            applyToAdvancement(player,e.getAdvancement());
        }
    }

    private static void applyToAdvancement(ServerPlayerEntity player, Advancement advancementIn) {
        AdvancementProgress advancementprogress = player.getAdvancements().getProgress(advancementIn);
        if (!advancementprogress.isDone()) {
            for(String s : advancementprogress.getRemaningCriteria()) {
                player.getAdvancements().grantCriterion(advancementIn, s);
            }
        }
    }
}
