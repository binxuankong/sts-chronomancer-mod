package chronoMod.powers;

import chronoMod.ChronoMod;
import chronoMod.patches.ChronoEnum;
import chronoMod.relics.FeatherQuill;
import chronoMod.relics.Winder;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public abstract class RecallPower extends AbstractPower {

    AbstractPlayer p;
    // private static int idOffset;

    public RecallPower(AbstractCreature owner) {
        super();
        this.owner = owner;
        this.p = (AbstractPlayer)owner;
        this.type = ChronoEnum.RECALL;
        // this.priority += idOffset;
        // idOffset++;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.triggerRecall(true);
        ChronoMod.logger.info("Trigger Recall effect: " + this.name);
    }

    public void recallEffect() {}

    public void triggerRecall(boolean remove) {
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

        if (remove) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

}
