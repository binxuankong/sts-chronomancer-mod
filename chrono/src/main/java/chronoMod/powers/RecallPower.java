package chronoMod.powers;

import chronoMod.patches.RecallEnum;
import chronoMod.relics.FeatherQuill;
import chronoMod.relics.Winder;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public abstract class RecallPower extends AbstractPower {

    AbstractPlayer p;
    private static int idOffset;

    public RecallPower(AbstractCreature owner) {
        super();
        this.owner = owner;
        this.p = AbstractDungeon.player;
        this.type = RecallEnum.RECALL;
        this.priority += idOffset;
        idOffset++;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.triggerRecall();
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
        AbstractRelic winder = this.p.getRelic(Winder.ID);
        if (winder != null) {
            if (!winder.grayscale) {
                winder.onTrigger();
                this.recallEffect();
            }
        }

        // Feather Quill
        if (this.p.hasRelic(FeatherQuill.ID)) {
            this.p.getRelic(FeatherQuill.ID).onTrigger();
        }

        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

}
