package chronoMod.powers;

import chronoMod.DefaultMod;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TidesOfTimePower extends AbstractPower {
    public static final String POWER_ID = DefaultMod.makeID("TidesOfTime");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public TidesOfTimePower(AbstractCreature owner, int numWeak) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = numWeak;
        this.updateDescription();
        this.loadRegion("wave_of_the_hand");
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public AbstractPower makeCopy() {
        return new TidesOfTimePower(this.owner, this.amount);
    }
}
