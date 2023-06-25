package me.elephant1214.waylandiconfix.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.InputStream;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
	private static final Logger LOGGER = LogManager.getLogger("waylandiconfix");

	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;setIcon(Ljava/io/InputStream;Ljava/io/InputStream;)V"))
	private void fixSetIcon_WaylandIconFix(Window instance, InputStream icon16, InputStream icon32) {
		if (GLFW.glfwGetPlatform() == GLFW.GLFW_PLATFORM_WAYLAND) {
			LOGGER.info("Running on Wayland, not setting the window icon");
		} else {
			LOGGER.fatal("This mod is meant for users running Wayland who are unable to load into the game. If you " +
					"don't have this issue or don't even know what Wayland is, remove this mod.");
			instance.setIcon(icon16, icon32);
		}
	}
}