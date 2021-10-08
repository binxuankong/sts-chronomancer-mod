package chronoMod.powers;

import chronoMod.ChronoMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MagicChantFallPower extends AbstractPower {
    public static final String POWER_ID = ChronoMod.makeID("MagicChantFall");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public MagicChantFallPower(AbstractCreature owner, int energyDrawAmount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = energyDrawAmount;
        this.updateDescription();
        this.loadRegion("malleable");
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.addToBot(new GainEnergyAction(this.amount));
        this.addToBot(new DrawCardAction(this.amount));
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[4];
        }
    }

    public AbstractPower makeCopy() {
        return new MagicChantFallPower(this.owner, this.amount);
    }
}
