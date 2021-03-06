package chronoMod.powers;

import chronoMod.ChronoMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.PressurePointEffect;

public class MysticFlarePower extends RecallPower {
    public static final String POWER_ID = ChronoMod.makeID("MysticFlare");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static int damage;
    private DamageInfo.DamageType damageType;
    private static int hitCount;
    private AbstractMonster target;
    private static int idOffset;

    public MysticFlarePower(AbstractCreature owner, int damage, DamageInfo.DamageType damageType, int hitCount,
                            AbstractMonster monster) {
        super(owner);
        this.name = NAME;
        this.ID = POWER_ID + idOffset;
        idOffset++;
        this.damage = damage;
        this.damageType = damageType;
        this.hitCount = hitCount;
        this.target = monster;
        this.updateDescription();
        this.loadRegion("flameBarrier");
    }

    @Override
    public void recallEffect () {
        for (int i=0; i < this.hitCount; i++) {
            this.addToBot(new DamageAction(this.target, new DamageInfo(this.owner, this.damage, this.damageType),
                    AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            this.addToBot(new DamageAction(this.target, new DamageInfo(this.owner, this.damage, this.damageType),
                    AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void updateDescription() {
        if (this.hitCount == 1) {
            this.description = DESCRIPTIONS[0] + this.damage + DESCRIPTIONS[1] + this.hitCount + DESCRIPTIONS[2] +
                    this.target.name + DESCRIPTIONS[4];
        } else {
            this.description = DESCRIPTIONS[0] + this.damage + DESCRIPTIONS[1] + this.hitCount + DESCRIPTIONS[3] +
                    this.target.name + DESCRIPTIONS[4];
        }
    }

    public AbstractPower makeCopy() {
        return new MysticFlarePower(this.owner, this.damage, this.damageType, this.hitCount, this.target);
    }
}
