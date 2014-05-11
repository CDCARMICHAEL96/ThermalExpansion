package thermalexpansion.gui.client.machine;

import cofh.core.CoFHProps;
import cofh.gui.GuiBaseAdv;
import cofh.gui.element.ElementBase;
import cofh.gui.element.ElementDualScaled;
import cofh.gui.element.ElementEnergyStored;
import cofh.gui.element.ElementFluid;
import cofh.gui.element.ElementFluidTank;
import cofh.gui.element.TabConfiguration;
import cofh.gui.element.TabEnergy;
import cofh.gui.element.TabInfo;
import cofh.gui.element.TabRedstone;
import cofh.gui.element.TabTutorial;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import thermalexpansion.block.machine.TileCrucible;
import thermalexpansion.core.TEProps;
import thermalexpansion.gui.container.machine.ContainerCrucible;
import thermalexpansion.gui.element.ElementSlotOverlay;

public class GuiCrucible extends GuiBaseAdv {

	public static final ResourceLocation TEXTURE = new ResourceLocation(TEProps.PATH_GUI_MACHINE + "Crucible.png");
	static final String INFO = "Turns solid material into fluids!\n\nVery useful for building advanced devices.\n\nKeep away from small children and pets.";

	TileCrucible myTile;

	ElementBase slotInput;
	ElementBase slotOutput;
	ElementFluid progressFluid;
	ElementDualScaled progressOverlay;
	ElementDualScaled speed;

	public GuiCrucible(InventoryPlayer inventory, TileEntity theTile) {

		super(new ContainerCrucible(inventory, theTile), TEXTURE);
		myTile = (TileCrucible) theTile;
		name = myTile.getInventoryName();
	}

	@Override
	public void initGui() {

		super.initGui();

		slotInput = addElement(new ElementSlotOverlay(this, 56, 26).setSlotInfo(0, 0, 2));
		slotOutput = addElement(new ElementSlotOverlay(this, 152, 9).setSlotInfo(3, 3, 2));

		addElement(new ElementEnergyStored(this, 8, 8, myTile.getEnergyStorage()));
		addElement(new ElementFluidTank(this, 152, 9, myTile.getTank()).setGauge(1));
		progressFluid = (ElementFluid) addElement(new ElementFluid(this, 103, 34).setFluid(myTile.getTankFluid()).setSize(24, 16));
		progressOverlay = (ElementDualScaled) addElement(new ElementDualScaled(this, 103, 34).setMode(1).setBackground(false).setSize(24, 16)
				.setTexture(TEX_DROP_RIGHT, 48, 16));
		speed = (ElementDualScaled) addElement(new ElementDualScaled(this, 56, 44).setSize(16, 16).setTexture(TEX_FLAME, 32, 16));

		addTab(new TabEnergy(this, myTile, false));
		addTab(new TabRedstone(this, myTile));
		addTab(new TabConfiguration(this, myTile));
		addTab(new TabInfo(this, INFO, 1));
		addTab(new TabTutorial(this, CoFHProps.tutorialTabRedstone + "\n\n" + CoFHProps.tutorialTabConfiguration + "\n\n" + CoFHProps.tutorialTabFluxRequired));
	}

	@Override
	protected void updateElements() {

		slotInput.setVisible(myTile.hasSide(1));
		slotOutput.setVisible(myTile.hasSide(2));

		progressFluid.setFluid(myTile.getTankFluid());
		progressFluid.setSize(myTile.getScaledProgress(PROGRESS), 16);
		progressOverlay.setQuantity(myTile.getScaledProgress(PROGRESS));
		speed.setQuantity(myTile.getScaledSpeed(SPEED));
	}

}