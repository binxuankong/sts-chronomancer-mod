package chronoMod.powers;

import chronoMod.ChronoMod;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ArcaneBlessingPower extends AbstractPower {
    public static final String POWER_ID = ChronoMod.makeID("ArcaneBlessing");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ArcaneBlessingPower(AbstractCreature owner, int numBuff) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = numBuff;
        this.updateDescription();
        this.loadRegion("fasting");
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

    public AbstractPower makeCopy() {
        return new ArcaneBlessingPower(this.owner, this.amount);
    }
}
