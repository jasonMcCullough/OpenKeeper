/*
 * Copyright (C) 2014-2015 OpenKeeper
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
package toniarts.openkeeper.game.trigger;

import java.util.Map;
import toniarts.openkeeper.tools.convert.map.KwdFile;
import toniarts.openkeeper.tools.convert.map.Trigger;
import toniarts.openkeeper.tools.convert.map.TriggerAction;
import toniarts.openkeeper.tools.convert.map.TriggerGeneric;

/**
 *
 * @author ArchDemon
 */


public class TriggerControl {

    private final KwdFile kwdFile;
    private TriggerGenericData root;

    public TriggerControl(KwdFile kwdFile, int startId) {
        this.kwdFile = kwdFile;
        root = new TriggerGenericData();
        initialize(root, startId);
    }

    private void initialize(TriggerGenericData parent, int id) {
        Map<Integer, Trigger> triggers = kwdFile.getTriggers();
        while (true) {
            Trigger trigger = triggers.get(id);
            TriggerData t;

            if (trigger instanceof TriggerGeneric) {
                t = new TriggerGenericData(id, trigger.getRepeatTimes());
                ((TriggerGenericData) t).setType(((TriggerGeneric) trigger).getType());
                ((TriggerGenericData) t).setComparison(((TriggerGeneric) trigger).getTargetValueComparison());

                for (String key : trigger.getUserDataKeys()) {
                    t.setUserData(key, trigger.getUserData(key));
                }
                
                parent.attachChild(id, t);

                if (trigger.hasChildren()) {
                    initialize(((TriggerGenericData) t), trigger.getIdChild());
                }

            } else if (trigger instanceof TriggerAction) {
                t = new TriggerActionData(id);
                ((TriggerActionData) t).setType(((TriggerAction) trigger).getType());

                for (String key : trigger.getUserDataKeys()) {
                    t.setUserData(key, trigger.getUserData(key));
                }

                parent.attachChild(id, t);

            } else {
                throw new RuntimeException("Unexpected class");
            }

            if (trigger.hasNext()) {
                id = trigger.getIdNext();
            } else {
                break;
            }
        }
    }

    public TriggerGenericData getTriggers() {
        return root;
    }
}