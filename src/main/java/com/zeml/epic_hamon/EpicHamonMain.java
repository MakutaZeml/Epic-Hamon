package com.zeml.epic_hamon;

import com.zeml.epic_hamon.capability.CapabilityHandler;
import com.zeml.epic_hamon.network.ModNetwork;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// Your addon's main file

@Mod(EpicHamonMain.MOD_ID)
public class EpicHamonMain {
    public static final String MOD_ID = "epic_hamon";
    public static final Logger LOGGER = LogManager.getLogger();

    public EpicHamonMain() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::preInit);

    }

    private void preInit(FMLCommonSetupEvent event){
        CapabilityHandler.commonSetupRegister();
        ModNetwork.init();
    }
}
