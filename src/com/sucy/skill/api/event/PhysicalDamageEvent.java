package com.sucy.skill.api.event;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * An event for when an entity is damaged by another entity without the
 * use of skills such as melee attacks or projectiles.
 */
public class PhysicalDamageEvent extends Event implements Cancellable
{
    private static final HandlerList handlers = new HandlerList();
    private LivingEntity damager;
    private LivingEntity target;
    private double       damage;
    private boolean      projectile;
    private boolean      cancelled;

    /**
     * Initializes a new event
     *
     * @param damager    entity dealing the damage
     * @param target     entity receiving the damage
     * @param damage     the amount of damage dealt
     * @param projectile whether or not it was a projectile attack
     */
    public PhysicalDamageEvent(LivingEntity damager, LivingEntity target, double damage, boolean projectile)
    {
        this.damager = damager;
        this.target = target;
        this.damage = damage;
        this.projectile = projectile;
        this.cancelled = false;
    }

    /**
     * Retrieves the entity that dealt the damage
     *
     * @return entity that dealt the damage
     */
    public LivingEntity getDamager()
    {
        return damager;
    }

    /**
     * Retrieves the entity that received the damage
     *
     * @return entity that received the damage
     */
    public LivingEntity getTarget()
    {
        return target;
    }

    /**
     * Retrieves the amount of damage dealt
     *
     * @return amount of damage dealt
     */
    public double getDamage()
    {
        return damage;
    }

    /**
     * Sets the amount of damage dealt
     *
     * @param amount amount of damage dealt
     */
    public void setDamage(double amount)
    {
        damage = amount;
    }

    /**
     * Checks whether or not it was a projectile attack
     *
     * @return true if a projectile attack, false otherwise
     */
    public boolean isProjectile()
    {
        return projectile;
    }

    /**
     * Checks whether or not the event is cancelled
     *
     * @return true if cancelled, false otherwise
     */
    @Override
    public boolean isCancelled()
    {
        return cancelled;
    }

    /**
     * Sets the cancelled state of the event
     *
     * @param cancelled the cancelled state of the event
     */
    @Override
    public void setCancelled(boolean cancelled)
    {
        this.cancelled = cancelled;
    }

    /**
     * Retrieves the handlers for the event
     *
     * @return list of event handlers
     */
    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    /**
     * Retrieves the handlers for the event
     *
     * @return list of event handlers
     */
    public static HandlerList getHandlerList()
    {
        return handlers;
    }
}
