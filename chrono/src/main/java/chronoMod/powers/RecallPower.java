package chronoMod.powers;

import chronoMod.patches.AbstractPowerEnum;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class RecallPower extends AbstractPower {
    public RecallPower(AbstractCreature owner) {
        super();
        this.owner = owner;
        this.type = AbstractPowerEnum.RECALL;
    }

    public void recallEffect() {}

    public void triggerRecall() {
        this.flash();
        this.recallEffect();
        // Tides of Time
        AbstractPower tidesOfTime = this.owner.getPower(TidesOfTimePower.POWER_ID);
        if (tidesOfTime != null) {
            tidesOfTime.priority++;
        }
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }
}
