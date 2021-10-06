package chronoMod.powers;

import chronoMod.ChronoMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import java.util.Iterator;

public class ButterflyEffectPower extends RecallPower {
    public static final String POWER_ID = ChronoMod.makeID("ButterflyEffect");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static int block;
    private static int artifact;
    private static int idOffset;

    public ButterflyEffectPower(AbstractCreature owner, int blockAmt, int artifactAmt) {
        super(owner);
        this.name = NAME;
        this.ID = POWER_ID + idOffset;
        idOffset++;
        this.block = blockAmt;
        this.artifact = artifactAmt;
        this.updateDescription();
        this.loadRegion("phantasmal");
    }

    @Override
    public void recallEffect () {
        boolean hasAttack = false;
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        while(var1.hasNext()) {
            AbstractMonster mo = (AbstractMonster)var1.next();
            if (mo.getIntentBaseDmg() >= 0) {
                hasAttack = true;
                break;
            }
        }
        if (hasAttack) {
            this.addToBot(new GainBlockAction(this.owner, this.block));
        } else {
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new ArtifactPower(this.owner, this.artifact),
                    this.artifact));
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.triggerRecall();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.block + DESCRIPTIONS[1] + this.artifact + DESCRIPTIONS[2];
    }

    public AbstractPower makeCopy() {
        return new ButterflyEffectPower(this.owner, this.block, this.artifact);
    }
}
