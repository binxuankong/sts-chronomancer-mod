package chronoMod.powers;

import chronoMod.patches.RecallEnum;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class RecallPower extends AbstractPower {
    public RecallPower(AbstractCreature owner) {
        super();
        this.owner = owner;
        this.type = RecallEnum.RECALL;
    }

    public void recallEffect() {}

    public void triggerRecall() {
        this.flash();
        this.recallEffect();
        // Tides of Time
        if (this.owner.hasPower(TidesOfTimePower.POWER_ID)) {
            this.owner.getPower(TidesOfTimePower.POWER_ID).onSpecificTrigger();
        }
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }
}
