package chronoMod.powers;

import chronoMod.ChronoMod;
import chronoMod.actions.EssenceFluxAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EssenceFluxPower extends RecallPower {
    public static final String POWER_ID = ChronoMod.makeID("EssenceFlux");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static int damage;
    private DamageInfo.DamageType damageType;
    private static int hitCount;
    private static int idOffset;

    public EssenceFluxPower(AbstractCreature owner, int damage, DamageInfo.DamageType damageType, int hitCount) {
        super(owner);
        this.name = NAME;
        this.ID = POWER_ID + idOffset;
        idOffset++;
        this.damage = damage;
        this.damageType = damageType;
        this.hitCount = hitCount;
        this.updateDescription();
        this.loadRegion("static_discharge");
    }

    @Override
    public void recallEffect () {
        for (int i=0; i < this.hitCount; i++) {
            this.addToBot(new EssenceFluxAction(new DamageInfo(this.owner, damage, damageType)));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.damage + DESCRIPTIONS[1] + this.hitCount + DESCRIPTIONS[2];
    }

    public AbstractPower makeCopy() {
        return new EssenceFluxPower(this.owner, this.damage, this.damageType, this.hitCount);
    }
}
