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
import toniarts.openkeeper.game.controller.room.AbstractRoomController.ObjectType;
import toniarts.openkeeper.game.controller.room.IRoomController;
import toniarts.openkeeper.game.task.AbstractCapacityCriticalRoomTask;
import toniarts.openkeeper.game.task.TaskManager;
import toniarts.openkeeper.tools.convert.map.ArtResource;
import toniarts.openkeeper.utils.WorldUtils;

/**
 * A task for creatures to haul the captured enemy to prison
 *
 * @author Toni Helenius <helenius.toni@gmail.com>
 */
public class CarryEnemyCreatureToPrison extends AbstractCapacityCriticalRoomTask {

    private boolean executed = false;

    public CarryEnemyCreatureToPrison(final IGameWorldController gameWorldController, final IMapController mapController, int x, int y, short playerId, IRoomController room, TaskManager taskManager) {
        super(gameWorldController, mapController, x, y, playerId, room, taskManager);
    }

    @Override
    public Vector2f getTarget(ICreatureController creature) {
        return WorldUtils.pointToVector2f(getTaskLocation()); // FIXME 0.5f not needed?
    }

    @Override
    public boolean isValid(ICreatureController creature) {
        if (!executed) {
            return super.isValid(creature);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Carrying enemy creature to prison at " + getTaskLocation();
    }

    @Override
    protected String getStringId() {
        return "2619";
    }

    @Override
    public void executeTask(ICreatureController creature) {

        // A bit dirty but it is always a creature we are hauling
        //((CreatureControl) creature.getHaulable()).imprison();
        executed = true;
    }

    @Override
    protected ObjectType getRoomObjectType() {
        return ObjectType.PRISONER;
    }

    @Override
    public ArtResource getTaskAnimation(ICreatureController creature) {
        return null;
    }

    @Override
    public String getTaskIcon() {
        return "Textures/GUI/moods/SJ-Take_Crate.png";
    }

    @Override
    public void unassign(ICreatureController creature) {
        super.unassign(creature);

        // Set the dragged state
        //creature.setHaulable(null);
    }

}
