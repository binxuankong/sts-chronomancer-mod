package chronoMod.powers;

import chronoMod.ChronoMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TimeWarpPower extends RecallPower {
    public static final String POWER_ID = ChronoMod.makeID("TimeWarp");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public TimeWarpPower(AbstractCreature owner, int energyDrawAmt) {
        super(owner);
        this.name = NAME;
        this.ID = POWER_ID;
        this.amount = energyDrawAmt;
        this.updateDescription();
        this.loadRegion("time");
    }

    @Override
    public void recallEffect () {
        this.addToBot(new GainEnergyAction(this.amount));
        this.addToBot(new DrawCardAction(this.amount));
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3];
        }
    }

    public AbstractPower makeCopy() {
        return new TimeWarpPower(this.owner, this.amount);
    }
}
