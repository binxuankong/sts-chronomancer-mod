package chronoMod.powers;

import chronoMod.ChronoMod;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class RingSingularityPower extends RecallPower {
    public static final String POWER_ID = ChronoMod.makeID("RingSingularity");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public RingSingularityPower(AbstractCreature owner, int energyAmt) {
        super(owner);
        this.name = NAME;
        this.ID = POWER_ID;
        this.amount = energyAmt;
        this.updateDescription();
        this.loadRegion("energized_blue");
    }

    @Override
    public void recallEffect () {
        this.addToBot(new GainEnergyAction(this.amount));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public AbstractPower makeCopy() {
        return new RingSingularityPower(this.owner, this.amount);
    }
}
