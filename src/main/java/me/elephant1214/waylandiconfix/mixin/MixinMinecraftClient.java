package me.elephant1214.waylandiconfix.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Icons;
import net.minecraft.client.util.Window;
import net.minecraft.resource.ResourcePack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
	private static final Logger LOGGER = LogManager.getLogger("waylandiconfix");

	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;setIcon(Lnet/minecraft/resource/ResourcePack;Lnet/minecraft/client/util/Icons;)V"))
	private void fixSetIcon_WaylandIconFix(Window instance, ResourcePack resourcePack, Icons icons) {
		if (GLFW.glfwGetPlatform() == GLFW.GLFW_PLATFORM_WAYLAND) {
			LOGGER.info("Running on Wayland, not setting the window icon");
		} else {
			LOGGER.fatal("This mod is meant for users running Wayland who are unable to load into the game. If you " +
					"don't have this issue or don't even know what Wayland is, remove this mod.");
			try {
				instance.setIcon(resourcePack, icons);
			} catch (IOException e) {
				LOGGER.error("Couldn't set icon", e);
			}
		}
	}
}