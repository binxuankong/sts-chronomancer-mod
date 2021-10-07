package chronoMod.powers;

import chronoMod.patches.RecallEnum;
import chronoMod.relics.Winder;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class RecallPower extends AbstractPower {
    AbstractPlayer p;

    public RecallPower(AbstractCreature owner) {
        super();
        this.owner = owner;
        this.p = AbstractDungeon.player;
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

        // Winder
        if (this.p.hasRelic(Winder.ID)) {
            this.p.getRelic(Winder.ID).onTrigger();
        }

        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }
}
