package org.valkyrienskies.addon.control.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.joml.Vector3dc;
import org.valkyrienskies.addon.control.tileentity.TileEntityGyroscopeDampener;
import org.valkyrienskies.addon.control.util.BaseBlock;
import org.valkyrienskies.mod.common.block.IBlockTorqueProvider;
import org.valkyrienskies.mod.common.physics.PhysicsCalculations;
import org.valkyrienskies.mod.common.util.ValkyrienUtils;

import javax.annotation.Nullable;
import java.util.List;

public class BlockGyroscopeDampener extends BaseBlock implements ITileEntityProvider,
    IBlockTorqueProvider {

    public BlockGyroscopeDampener() {
        super("gyroscope_dampener", Material.IRON, 0.0F, true);
        this.setHardness(6.0F);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player,
        List<String> itemInformation,
        ITooltipFlag advanced) {
        itemInformation
            .add(TextFormatting.BLUE + I18n.format("tooltip.vs_control.gyroscope_dampener"));
    }

    @Override
    public Vector3dc getTorqueInGlobal(PhysicsCalculations physicsCalculations, BlockPos pos) {
        TileEntity thisTile = ValkyrienUtils.getTileEntitySafe(physicsCalculations.getParent().getWorld(), pos);
        if (thisTile instanceof TileEntityGyroscopeDampener) {
            TileEntityGyroscopeDampener tileGyroscope = (TileEntityGyroscopeDampener) thisTile;
            return tileGyroscope.getTorqueInGlobal(physicsCalculations, pos);
        }
        return null;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityGyroscopeDampener();
    }

    @Override
    public int getBlockSortingIndex() {
        // Since we're damping angular velocity, we want this to run at the very end, so
        // we give it a large sorting value to put it at the end.
        return 5;
    }

}
