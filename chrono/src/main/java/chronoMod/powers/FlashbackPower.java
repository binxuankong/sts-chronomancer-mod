package chronoMod.powers;

import chronoMod.ChronoMod;
import chronoMod.actions.GainJadeAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FlashbackPower extends RecallPower {
    public static final String POWER_ID = ChronoMod.makeID("Flashback");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FlashbackPower(AbstractCreature owner, int jadeAmt) {
        super(owner);
        this.name = NAME;
        this.ID = POWER_ID;
        this.amount = jadeAmt;
        this.updateDescription();
        this.loadRegion("bias");
    }

    @Override
    public void recallEffect () {
        this.addToBot(new GainJadeAction(this.amount));
    }

    @Override
    public void atStartOfTurn() {
        this.triggerRecall();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public AbstractPower makeCopy() {
        return new FlashbackPower(this.owner, this.amount);
    }
}
