/*
 * Copyright (C) 2014-2016 OpenKeeper
 *
 * OpenKeeper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenKeeper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenKeeper.  If not, see <http://www.gnu.org/licenses/>.
 */
package toniarts.openkeeper.game.task.worker;

import com.jme3.math.Vector2f;
import toniarts.openkeeper.game.controller.IGameWorldController;
import toniarts.openkeeper.game.controller.IMapController;
import toniarts.openkeeper.game.controller.ai.ICreatureController;
import toniarts.openkeeper.game.task.AbstractTileTask;
import toniarts.openkeeper.tools.convert.map.ArtResource;
import toniarts.openkeeper.utils.WorldUtils;

/**
 * Claim a tile task, for workers
 *
 * @author Toni Helenius <helenius.toni@gmail.com>
 */
public class ClaimTileTask extends AbstractTileTask {

    public ClaimTileTask(final IGameWorldController gameWorldController, final IMapController mapController, int x, int y, short playerId) {
        super(gameWorldController, mapController, x, y, playerId);
    }

    @Override
    public Vector2f getTarget(ICreatureController creature) {
        return WorldUtils.pointToVector2f(getTaskLocation()); // FIXME 0.5f not needed?
    }

    @Override
    public boolean isValid(ICreatureController creature) {
        return mapController.isClaimableTile(getTaskLocation().x, getTaskLocation().y, playerId);
    }

    @Override
    public String toString() {
        return "Claim tile at " + getTaskLocation();
    }

    @Override
    protected String getStringId() {
        return "2601";
    }

    @Override
    public void executeTask(ICreatureController creature) {
        //worldState.applyClaimTile(getTaskLocation(), playerId);
    }

    @Override
    public ArtResource getTaskAnimation(ICreatureController creature) {
        //return creature.getCreature().getAnimEatResource();
        return null;
    }

    @Override
    public String getTaskIcon() {
        return "Textures/GUI/moods/SJ-Claim.png";
    }

}
