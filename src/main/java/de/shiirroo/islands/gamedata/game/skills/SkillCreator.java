package de.shiirroo.islands.gamedata.game.skills;

import javax.swing.text.LabelView;
import javax.xml.stream.Location;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class SkillCreator implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public abstract String skillName();

    public abstract String skillDescription();

    public abstract SkillUnlock skillUnlock();

    public abstract List<SkillCreator> requiredSkill();

    public abstract void skillEffect();

    public abstract boolean startSkill();


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SkillCreator that)) return false;
        if(this.skillName() == null || that.skillName() == null) return false;
        return (this.skillName().equalsIgnoreCase(that.skillName())
                && ((this.skillUnlock() == null && that.skillUnlock() == null) || (this.skillUnlock().equals(that.skillUnlock())))
                && ((this.skillDescription() == null && that.skillDescription() == null) || this.skillDescription().equalsIgnoreCase(that.skillDescription())));
    }
}
