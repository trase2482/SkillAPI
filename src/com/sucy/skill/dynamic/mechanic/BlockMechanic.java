package com.sucy.skill.dynamic.mechanic;

import com.rit.sucy.region.Sphere;
import com.sucy.skill.dynamic.EffectComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import sun.security.provider.SHA;

import java.util.ArrayList;
import java.util.List;

/**
 * Mechanic that changes blocks for a duration before
 * returning them to what they were
 */
public class BlockMechanic extends EffectComponent {

    private static final String SHAPE   = "shape";
    private static final String SOLID   = "solid";
    private static final String RADIUS  = "radius";
    private static final String WIDTH   = "width";
    private static final String HEIGHT  = "height";
    private static final String DEPTH   = "depth";
    private static final String BLOCK   = "block";
    private static final String DATA    = "data";
    private static final String SECONDS = "seconds";

    /**
     * Executes the component
     *
     * @param caster  caster of the skill
     * @param level   level of the skill
     * @param targets targets to apply to
     *
     * @return true if applied to something, false otherwise
     */
    @Override
    public boolean execute(LivingEntity caster, int level, List<LivingEntity> targets)
    {
        if (targets.size() == 0) return false;

        Material block = Material.ICE;
        try
        {
            block = Material.valueOf(settings.getString(BLOCK, "ICE").toUpperCase().replace(' ', '_'));
        }
        catch (Exception ex)
        {
            // Use default
        }

        boolean sphere = settings.getString(SHAPE, "sphere").toLowerCase().equals("sphere");
        int ticks = (int)(20 * settings.getAttr(SECONDS, level, 5));
        byte data = (byte)settings.getInt(DATA, 0);
        boolean any = settings.getString(SOLID, "true").toLowerCase().equals("false");

        List<Block> blocks = new ArrayList<Block>();
        World w = caster.getWorld();

        // Grab blocks in a sphere
        if (sphere)
        {
            double radius = settings.getAttr(RADIUS, level, 3);
            double x, y, z, dx, dy, dz;
            double rSq = radius * radius;
            for (LivingEntity t : targets)
            {
                Location loc = t.getLocation();
                x = loc.getX();
                y = loc.getY();
                z = loc.getZ();
                for (int i = (int)(x - radius) + 1; i < x + radius; i++)
                {
                    for (int j = (int)(y - radius) + 1; j < y + radius; j++)
                    {
                        for (int k = (int)(z - radius) + 1; k < z + radius; k++)
                        {
                            dx = x - i;
                            dy = y - j;
                            dz = z - k;
                            if (dx * dx + dy * dy + dz * dz < rSq)
                            {
                                Block b = w.getBlockAt(i, j, k);
                                if (any || b.getType().isSolid())
                                {
                                    blocks.add(b);
                                }
                            }
                        }
                    }
                }
            }
        }

        // Grab blocks in a cuboid
        else
        {
            double width = settings.getAttr(WIDTH, level, 5) / 2;
            double height = settings.getAttr(HEIGHT, level, 5) / 2;
            double depth = settings.getAttr(DEPTH, level, 5) / 2;
            double x, y, z;

            for (LivingEntity t : targets)
            {
                Location loc = t.getLocation();
                x = loc.getX();
                y = loc.getY();
                z = loc.getZ();
                for (int i = (int)(x - width); i < x + width; i++)
                {
                    for (int j = (int)(y - height); j < y + height; j++)
                    {
                        for (int k = (int)(z - depth); k < z + depth; k++)
                        {
                            Block b = w.getBlockAt(i, j, k);
                            if (any || b.getType().isSolid())
                            {
                                blocks.add(b);
                            }
                        }
                    }
                }
            }
        }

        // Change blocks
        for (Block b : blocks)
        {
            BlockState state = b.getState();
            state.setType(block);
            state.setData(new MaterialData(block, data));
            state.update(true, false);
        }

        return true;

    }
}
