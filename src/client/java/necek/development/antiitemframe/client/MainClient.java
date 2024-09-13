package necek.development.antiitemframe.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;

import java.util.Set;

public class MainClient implements ClientModInitializer {

    @Environment(EnvType.CLIENT)
    @Override
    public void onInitializeClient() {

        Set<Item> czarneNyggery = Set.of(Items.NETHERITE_SWORD, Items.DIAMOND_SWORD, Items.WATER_BUCKET, Items.LAVA_BUCKET, Items.BUCKET, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.DIAMOND_PICKAXE, Items.NETHERITE_PICKAXE, Items.DIAMOND_AXE, Items.NETHERITE_AXE, Items.DIAMOND_SHOVEL, Items.DIAMOND_HOE, Items.NETHERITE_HOE, Items.SHEARS, Items.ENDER_PEARL, Items.STICK, Items.ELYTRA);

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (entity instanceof ItemFrameEntity) {
                Item item = player.getStackInHand(hand).getItem();

                if (item == Items.ENDER_PEARL) {
                    MinecraftClient client = MinecraftClient.getInstance();
                    ItemFrameEntity itemFrame = (ItemFrameEntity) entity;
                    if (itemFrame.isAlive()) {
                        client.interactionManager.attackEntity(client.player, itemFrame);
                    }
                    if (itemFrame.getHeldItemStack() != null && itemFrame.isAttackable()) {
                        client.interactionManager.attackEntity(client.player, itemFrame);
                    }

                    MinecraftClient.getInstance().gameRenderer.firstPersonRenderer.resetEquipProgress(hand);

                    MinecraftClient.getInstance().interactionManager.interactItem(player, hand);

                    return ActionResult.SUCCESS;
                }

                if (!czarneNyggery.contains(item)) return ActionResult.PASS;
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
    }
}
