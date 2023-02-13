package com.github.reviversmc.unsaddle;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;

public class MinecraftVersionHelper {
	public static final Version MC_VERSION;
	public static final boolean IS_1_15;
	public static final boolean IS_1_16_OR_GREATER;

	static {
		MC_VERSION = FabricLoader.getInstance()
			.getModContainer("minecraft")
			.get()
			.getMetadata()
			.getVersion();

		boolean is115Temp = false;
		try {
			is115Temp = MC_VERSION.compareTo(Version.parse("1.15")) >= 0
					&& MC_VERSION.compareTo(Version.parse("1.15.2")) <= 0;
		} catch (VersionParsingException e) {
			// ignored
		}
		IS_1_15 = is115Temp;

		boolean is116OrGreaterTemp = false;
		try {
			is116OrGreaterTemp = MC_VERSION.compareTo(Version.parse("1.16")) >= 0;
		} catch (VersionParsingException e) {
			// ignored
		}
		IS_1_16_OR_GREATER = is116OrGreaterTemp;
	}
}
